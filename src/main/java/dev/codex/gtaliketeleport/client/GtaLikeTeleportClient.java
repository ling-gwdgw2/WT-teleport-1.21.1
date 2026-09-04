package dev.codex.gtaliketeleport.client;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContextBuilder;
import dev.codex.gtaliketeleport.*;
import dev.codex.gtaliketeleport.network.GtaLikeTeleportClientNetworking;
import dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public final class GtaLikeTeleportClient {
    private static final String[] COMMAND_ALIASES = {"wtp", "wt_teleport", "wtteleport", "gtp", "grandtp"};
    private static final String USAGE_MESSAGE = "Usage: /wtp or /gtp on|off|status|player_freeze <on|off|status>|config|sound <gta|default|off>";

    private static boolean bypassNextCommand;
    private static boolean bypassNextPacket;
    private static boolean bypassNextJourneyMapTeleport;

    private GtaLikeTeleportClient() {
    }

    public static void init() {
        GtaLikeTeleportConfig.load();
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith("/")) {
            String command = message.substring(1);
            if (!interceptOutgoingCommand(command)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (TeleportTransitionController.shouldBlockPlayerInput()) {
            event.getInput().leftImpulse = 0.0F;
            event.getInput().forwardImpulse = 0.0F;
            event.getInput().up = false;
            event.getInput().down = false;
            event.getInput().left = false;
            event.getInput().right = false;
            event.getInput().jumping = false;
            event.getInput().shiftKeyDown = false;
        }
    }

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null && client.player != null) {
            TeleportStepEffectRenderer.render(event.getGuiGraphics(), client.getTimer());
        }
    }

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            TeleportTransitionController.tick(client);
        }
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        for (String commandName : COMMAND_ALIASES) {
            event.getDispatcher().register(Commands.literal(commandName)
                    .executes(context -> sendStatusFeedback(context.getSource()))
                    .then(Commands.literal("on").executes(context -> setEffectEnabled(context.getSource(), true)))
                    .then(Commands.literal("off").executes(context -> setEffectEnabled(context.getSource(), false)))
                    .then(Commands.literal("status").executes(context -> sendStatusFeedback(context.getSource())))
                    .then(Commands.literal("player_freeze")
                            .executes(context -> sendPlayerFreezeStatusFeedback(context.getSource()))
                            .then(Commands.literal("on").executes(context -> setPlayerFreezeEnabled(context.getSource(), true)))
                            .then(Commands.literal("off").executes(context -> setPlayerFreezeEnabled(context.getSource(), false)))
                            .then(Commands.literal("status").executes(context -> sendPlayerFreezeStatusFeedback(context.getSource()))))
                    .then(Commands.literal("config").executes(context -> openConfigScreen(context.getSource())))
                    .then(Commands.literal("sound")
                            .executes(context -> sendSoundPackFeedback(context.getSource()))
                            .then(Commands.literal("gta").executes(context -> setSoundPack(context.getSource(), GtaLikeTeleportConfig.SOUND_PACK_GTA)))
                            .then(Commands.literal("default").executes(context -> setSoundPack(context.getSource(), GtaLikeTeleportConfig.SOUND_PACK_DEFAULT)))
                            .then(Commands.literal("off").executes(context -> setSoundPack(context.getSource(), GtaLikeTeleportConfig.SOUND_PACK_OFF))))
                    .then(Commands.argument("value", StringArgumentType.word()).executes(context -> handleCommandArgument(
                            context.getSource(),
                            StringArgumentType.getString(context, "value")
                    )))
            );
        }
    }

    public static boolean interceptOutgoingCommand(String command) {
        if (bypassNextCommand) {
            return true;
        }

        Minecraft client = Minecraft.getInstance();
        if (handleGtaTeleportCommand(client, command)) {
            return false;
        }

        if (!GtaLikeTeleportConfig.isEffectEnabled()) {
            return true;
        }

        if (!TeleportCommandMatcher.isTeleportCommand(command) || client.player == null || client.getConnection() == null) {
            return true;
        }

        if (!GtaLikeTeleportConfig.isVanillaTpEnabled()) {
            GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
            return true;
        }

        if (!canExecuteServerCommand(client, command)) {
            return true;
        }

        if (TeleportTransitionController.isRunning()) {
            return true;
        }

        TeleportTransitionController.start(client, command);
        return false;
    }

    public static boolean interceptOutgoingPacket(Connection connection, Packet<?> packet, PacketSendListener listener) {
        if (bypassNextPacket) {
            return true;
        }

        PacketTeleportTarget teleportTarget = getTeleportPacketTarget(packet);
        if (teleportTarget == null) {
            return true;
        }

        Minecraft client = Minecraft.getInstance();
        if (!GtaLikeTeleportConfig.isEffectEnabled() || client.player == null || client.level == null || client.getConnection() == null) {
            return true;
        }

        if (teleportTarget.isWaystones() && !GtaLikeTeleportConfig.isWaystonesEnabled()) {
            GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
            return true;
        }

        if (!teleportTarget.isWaystones() && !GtaLikeTeleportConfig.isJourneyMapEnabled()) {
            GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
            return true;
        }

        if (TeleportTransitionController.isRunning()) {
            return true;
        }

        TeleportTransitionController.start(
                client,
                teleportTarget.targetFeet(),
                teleportTarget.targetDimensionId(),
                () -> sendDeferredPacket(connection, packet, listener),
                !teleportTarget.keepMenuOpen()
        );
        return false;
    }

    public static boolean interceptJourneyMapTeleport(Vec3 targetFeet, Runnable action) {
        return interceptJourneyMapTeleport(targetFeet, null, action);
    }

    public static boolean interceptJourneyMapTeleport(Vec3 targetFeet, String targetDimensionId, Runnable action) {
        if (bypassNextJourneyMapTeleport) {
            return true;
        }

        Minecraft client = Minecraft.getInstance();
        if (!GtaLikeTeleportConfig.isEffectEnabled() || client.player == null || client.level == null || client.getConnection() == null) {
            return true;
        }

        if (!GtaLikeTeleportConfig.isJourneyMapEnabled()) {
            GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
            return true;
        }

        if (TeleportTransitionController.isRunning()) {
            return true;
        }

        TeleportTransitionController.start(client, targetFeet, targetDimensionId, () -> sendDeferredJourneyMapTeleport(action));
        return false;
    }

    public static void handleServerTeleportRequest(GtaLikeTeleportNetworkPayloads.StartServerTeleportPayload payload) {
        Minecraft client = Minecraft.getInstance();
        if (!shouldPlayServerTeleportTransition(client, payload.source())) {
            GtaLikeTeleportClientNetworking.sendServerTeleportAck(payload.requestId());
            return;
        }

        TeleportTransitionController.start(
                client,
                GtaLikeTeleportClientNetworking.targetFeet(payload),
                GtaLikeTeleportClientNetworking.targetDimensionId(payload),
                () -> GtaLikeTeleportClientNetworking.sendServerTeleportAck(payload.requestId())
        );
    }

    private static boolean shouldPlayServerTeleportTransition(Minecraft client, int source) {
        if (!GtaLikeTeleportConfig.isEffectEnabled() || client.player == null || client.level == null || client.getConnection() == null) {
            return false;
        }
        if (TeleportTransitionController.isRunning()) {
            return false;
        }
        if (source == GtaLikeTeleportNetworkPayloads.SOURCE_WARP_PLATE) {
            return GtaLikeTeleportConfig.isWarpPlateTransitionsEnabled();
        }
        return GtaLikeTeleportConfig.isExternalTeleportTransitionsEnabled();
    }

    public static void sendDeferredCommand(String command) {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return;
        }

        GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
        bypassNextCommand = true;

        try {
            client.getConnection().sendCommand(command);
        } finally {
            bypassNextCommand = false;
        }
    }

    private static void sendDeferredPacket(Connection connection, Packet<?> packet, PacketSendListener listener) {
        GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
        bypassNextPacket = true;

        try {
            if (listener == null) {
                connection.send(packet);
            } else {
                connection.send(packet, listener);
            }
        } finally {
            bypassNextPacket = false;
        }
    }

    private static void sendDeferredJourneyMapTeleport(Runnable action) {
        GtaLikeTeleportClientNetworking.sendBypassNextServerTeleport();
        bypassNextJourneyMapTeleport = true;

        try {
            action.run();
        } finally {
            bypassNextJourneyMapTeleport = false;
        }
    }

    private static PacketTeleportTarget getTeleportPacketTarget(Packet<?> packet) {
        if (!(packet instanceof ServerboundCustomPayloadPacket customPayloadPacket)) {
            return null;
        }

        CustomPacketPayload payload = customPayloadPacket.payload();
        ResourceLocation id = payload.type().id();
        PacketTeleportTarget journeyMapTarget = getJourneyMapTeleportTarget(payload, id);
        if (journeyMapTarget != null) {
            return journeyMapTarget;
        }

        return getWaystonesTeleportTarget(payload, id);
    }

    private static PacketTeleportTarget getJourneyMapTeleportTarget(CustomPacketPayload payload, ResourceLocation id) {
        if (!id.getNamespace().equals("journeymap") || !id.getPath().equals("teleport_req")) {
            return null;
        }

        try {
            Vec3 targetFeet = new Vec3(
                    readDouble(payload, "getX"),
                    readDouble(payload, "getY"),
                    readDouble(payload, "getZ")
            );
            return new PacketTeleportTarget(targetFeet, readOptionalDimensionId(payload, "getDimension"), false, false);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassCastException ignored) {
            return null;
        }
    }

    private static PacketTeleportTarget getWaystonesTeleportTarget(CustomPacketPayload payload, ResourceLocation id) {
        if (!id.getNamespace().equals("waystones")) {
            return null;
        }

        if (id.getPath().equals("select_waystone")) {
            WaystoneTarget target = getWaystonesSelectedTarget(payload);
            return target == null ? null : new PacketTeleportTarget(target.targetFeet(), target.targetDimensionId(), true, true);
        }

        if (id.getPath().equals("inventory_button")) {
            WaystoneTarget target = getWaystonesInventoryButtonTarget();
            return target == null ? null : new PacketTeleportTarget(target.targetFeet(), target.targetDimensionId(), false, true);
        }

        return null;
    }

    private static WaystoneTarget getWaystonesSelectedTarget(CustomPacketPayload payload) {
        try {
            UUID waystoneUid = readUuid(payload, "waystoneUid");
            Minecraft client = Minecraft.getInstance();
            Object menu = client.player == null ? null : client.player.containerMenu;
            WaystoneTarget menuTarget = findWaystoneTargetInMenu(menu, waystoneUid);
            return menuTarget != null ? menuTarget : findWaystoneTargetInStore(waystoneUid);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | ClassCastException ignored) {
            return null;
        }
    }

    private static WaystoneTarget getWaystonesInventoryButtonTarget() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return null;
        }

        try {
            Class<?> managerClass = Class.forName("net.blay09.mods.waystones.core.PlayerWaystoneManager");
            Method method = managerClass.getMethod("getInventoryButtonTarget", Player.class);
            Object result = method.invoke(null, client.player);
            if (!(result instanceof Optional<?> optional) || optional.isEmpty()) {
                return null;
            }

            return getWaystoneTarget(optional.get());
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassCastException ignored) {
            return null;
        }
    }

    private static WaystoneTarget findWaystoneTargetInMenu(Object menu, UUID waystoneUid)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (menu == null) {
            return null;
        }

        Method method = menu.getClass().getMethod("getWaystones");
        Object result = method.invoke(menu);
        if (!(result instanceof Collection<?> waystones)) {
            return null;
        }

        for (Object waystone : waystones) {
            if (waystoneUid.equals(readUuid(waystone, "getWaystoneUid"))) {
                return getWaystoneTarget(waystone);
            }
        }

        return null;
    }

    private static WaystoneTarget findWaystoneTargetInStore(UUID waystoneUid)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clientClass = Class.forName("net.blay09.mods.waystones.client.WaystonesClient");
        Object store = clientClass.getMethod("getWaystonesStore").invoke(null);
        Object result = store.getClass().getMethod("getWaystoneById", UUID.class).invoke(store, waystoneUid);
        if (!(result instanceof Optional<?> optional) || optional.isEmpty()) {
            return null;
        }

        return getWaystoneTarget(optional.get());
    }

    private static WaystoneTarget getWaystoneTarget(Object waystone)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object result = waystone.getClass().getMethod("getPos").invoke(waystone);
        if (!(result instanceof BlockPos pos)) {
            return null;
        }

        Vec3 targetFeet = new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        return new WaystoneTarget(targetFeet, readOptionalDimensionId(waystone, "getDimension"));
    }

    private static UUID readUuid(Object target, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Method method = target.getClass().getMethod(methodName);
            return (UUID) method.invoke(target);
        } catch (NoSuchMethodException noGetter) {
            try {
                Field field = target.getClass().getDeclaredField(methodName);
                field.setAccessible(true);
                return (UUID) field.get(target);
            } catch (NoSuchFieldException noField) {
                throw noGetter;
            }
        }
    }

    private static double readDouble(Object target, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = target.getClass().getMethod(methodName);
        return ((Number) method.invoke(target)).doubleValue();
    }

    private static String readOptionalDimensionId(Object target, String methodName) {
        try {
            Method method = target.getClass().getMethod(methodName);
            Object result = method.invoke(target);
            if (result instanceof ResourceKey<?> key) {
                return DimensionIds.fromResourceKey(key);
            }
            return DimensionIds.normalize(result == null ? null : result.toString());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassCastException ignored) {
            return null;
        }
    }

    private static int handleCommandArgument(CommandSourceStack source, String argument) {
        String lowerArgument = argument.toLowerCase(Locale.ROOT);
        if (lowerArgument.equals("on")) {
            return setEffectEnabled(source, true);
        }

        if (lowerArgument.equals("off")) {
            return setEffectEnabled(source, false);
        }

        if (lowerArgument.equals("status")) {
            return sendStatusFeedback(source);
        }

        if (lowerArgument.equals("player_freeze")) {
            return sendPlayerFreezeStatusFeedback(source);
        }

        source.sendFailure(Component.literal(USAGE_MESSAGE));
        return 0;
    }

    private static int setEffectEnabled(CommandSourceStack source, boolean enabled) {
        boolean saved = GtaLikeTeleportConfig.setEffectEnabled(enabled);
        source.sendSuccess(() -> createStateFeedback(enabled, saved, saved ? ChatFormatting.GREEN : ChatFormatting.YELLOW), false);
        return 1;
    }

    private static int sendStatusFeedback(CommandSourceStack source) {
        source.sendSuccess(() -> createStateFeedback(GtaLikeTeleportConfig.isEffectEnabled(), true, ChatFormatting.GRAY), false);
        return 1;
    }

    private static int setPlayerFreezeEnabled(CommandSourceStack source, boolean enabled) {
        boolean saved = GtaLikeTeleportConfig.setPlayerFreezeEnabled(enabled);
        source.sendSuccess(() -> createPlayerFreezeStateFeedback(enabled, saved, saved ? ChatFormatting.GREEN : ChatFormatting.YELLOW), false);
        return 1;
    }

    private static int sendPlayerFreezeStatusFeedback(CommandSourceStack source) {
        source.sendSuccess(() -> createPlayerFreezeStateFeedback(
                GtaLikeTeleportConfig.isPlayerFreezeEnabled(),
                true,
                ChatFormatting.GRAY
        ), false);
        return 1;
    }

    private static int openConfigScreen(CommandSourceStack source) {
        Minecraft client = Minecraft.getInstance();
        client.setScreen(new GtaLikeTeleportConfigScreen(client.screen));
        source.sendSuccess(() -> Component.literal("WT Teleport settings opened."), false);
        return 1;
    }

    private static int sendSoundPackFeedback(CommandSourceStack source) {
        source.sendSuccess(() -> Component.translatable(
                "gtalike_teleport.feedback.sound_pack_status",
                getSoundPackLabel(GtaLikeTeleportConfig.getSoundPack())
        ), false);
        return 1;
    }

    private static int setSoundPack(CommandSourceStack source, String pack) {
        boolean saved = GtaLikeTeleportConfig.setSoundPack(pack);
        Component label = getSoundPackLabel(GtaLikeTeleportConfig.getSoundPack());
        if (saved) {
            source.sendSuccess(() -> Component.translatable("gtalike_teleport.feedback.sound_pack_changed", label), false);
        } else {
            source.sendFailure(Component.translatable("gtalike_teleport.feedback.sound_pack_save_failed", label));
        }
        return saved ? 1 : 0;
    }

    private static Component getSoundPackLabel(String pack) {
        return switch (pack) {
            case GtaLikeTeleportConfig.SOUND_PACK_DEFAULT -> Component.translatable("gtalike_teleport.option.sound_mode.default");
            case GtaLikeTeleportConfig.SOUND_PACK_OFF -> Component.translatable("gtalike_teleport.option.sound_mode.off");
            default -> Component.translatable("gtalike_teleport.option.sound_mode.gta");
        };
    }

    private static boolean canExecuteServerCommand(Minecraft client, String command) {
        ClientPacketListener networkHandler = client.getConnection();
        if (networkHandler == null) {
            return false;
        }

        String normalized = normalizeCommand(command);
        if (normalized.isEmpty()) {
            return false;
        }

        ParseResults<SharedSuggestionProvider> parseResults = networkHandler.getCommands().parse(
                normalized,
                networkHandler.getSuggestionsProvider()
        );
        return !parseResults.getReader().canRead() && hasExecutableCommand(parseResults.getContext());
    }

    private static boolean hasExecutableCommand(CommandContextBuilder<?> context) {
        CommandContextBuilder<?> current = context;
        while (current != null) {
            if (current.getCommand() != null) {
                return true;
            }

            current = current.getChild();
        }

        return false;
    }

    private static String normalizeCommand(String command) {
        String normalized = command.strip();
        if (normalized.startsWith("/")) {
            normalized = normalized.substring(1).stripLeading();
        }

        return normalized;
    }

    private static String getLocalCommandName(String normalized) {
        int end = 0;
        while (end < normalized.length() && !Character.isWhitespace(normalized.charAt(end))) {
            end++;
        }

        String commandName = normalized.substring(0, end).toLowerCase(Locale.ROOT);
        for (String alias : COMMAND_ALIASES) {
            if (commandName.equals(alias)) {
                return normalized.substring(0, end);
            }
        }

        return null;
    }

    private static boolean handleGtaTeleportCommand(Minecraft client, String command) {
        String normalized = command.stripLeading();
        if (normalized.startsWith("/")) {
            normalized = normalized.substring(1).stripLeading();
        }

        String commandName = getLocalCommandName(normalized);
        if (commandName == null) {
            return false;
        }

        String argument = normalized.length() == commandName.length()
                ? ""
                : normalized.substring(commandName.length()).strip();
        String lowerArgument = argument.toLowerCase(Locale.ROOT);

        if (lowerArgument.equals("on")) {
            boolean saved = GtaLikeTeleportConfig.setEffectEnabled(true);
            sendCommandFeedback(client, true, saved);
            return true;
        }

        if (lowerArgument.equals("off")) {
            boolean saved = GtaLikeTeleportConfig.setEffectEnabled(false);
            sendCommandFeedback(client, false, saved);
            return true;
        }

        if (lowerArgument.equals("status") || lowerArgument.isEmpty()) {
            sendFeedback(client, createStateFeedback(GtaLikeTeleportConfig.isEffectEnabled(), true, ChatFormatting.GRAY));
            return true;
        }

        if (lowerArgument.equals("player_freeze") || lowerArgument.equals("player_freeze status")) {
            sendFeedback(client, createPlayerFreezeStateFeedback(
                    GtaLikeTeleportConfig.isPlayerFreezeEnabled(),
                    true,
                    ChatFormatting.GRAY
            ));
            return true;
        }

        if (lowerArgument.equals("player_freeze on")) {
            boolean saved = GtaLikeTeleportConfig.setPlayerFreezeEnabled(true);
            sendFeedback(client, createPlayerFreezeStateFeedback(true, saved, saved ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
            return true;
        }

        if (lowerArgument.equals("player_freeze off")) {
            boolean saved = GtaLikeTeleportConfig.setPlayerFreezeEnabled(false);
            sendFeedback(client, createPlayerFreezeStateFeedback(false, saved, saved ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
            return true;
        }

        if (lowerArgument.equals("config") || lowerArgument.equals("settings")) {
            client.setScreen(new GtaLikeTeleportConfigScreen(client.screen));
            sendFeedback(client, Component.literal("Grand Teleport settings opened.").withStyle(ChatFormatting.GRAY));
            return true;
        }

        sendFeedback(client, Component.literal(USAGE_MESSAGE).withStyle(ChatFormatting.RED));
        return true;
    }

    private static void sendCommandFeedback(Minecraft client, boolean enabled, boolean saved) {
        sendFeedback(client, createStateFeedback(enabled, saved, saved ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
    }

    private static Component createStateFeedback(boolean enabled, boolean saved, ChatFormatting formatting) {
        String state = enabled ? "ON" : "OFF";
        String message = "Grand Teleport:" + state + (saved ? "" : " (save failed)");
        return Component.literal(message).withStyle(formatting);
    }

    private static Component createPlayerFreezeStateFeedback(boolean enabled, boolean saved, ChatFormatting formatting) {
        String state = enabled ? "ON" : "OFF";
        String message = "Grand Teleport player_freeze:" + state + (saved ? "" : " (save failed)");
        return Component.literal(message).withStyle(formatting);
    }

    private static void sendFeedback(Minecraft client, Component message) {
        if (client.player != null) {
            client.player.sendSystemMessage(message);
        }
    }

    private record WaystoneTarget(Vec3 targetFeet, String targetDimensionId) {
    }

    private record PacketTeleportTarget(Vec3 targetFeet, String targetDimensionId, boolean keepMenuOpen, boolean isWaystones) {
    }
}
