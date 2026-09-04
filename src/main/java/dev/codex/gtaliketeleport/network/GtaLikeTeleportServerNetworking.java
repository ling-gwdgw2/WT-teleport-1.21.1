package dev.codex.gtaliketeleport.network;

import dev.codex.gtaliketeleport.DimensionIds;
import dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class GtaLikeTeleportServerNetworking {
    private GtaLikeTeleportServerNetworking() {
    }

    public static void handleTeleportAck(final ServerTeleportAckPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                dev.codex.gtaliketeleport.GtaLikeTeleportServer.handleTeleportAck(player, payload.requestId());
            }
        });
    }

    public static void handleBypassNextServerTeleport(final BypassNextServerTeleportPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                dev.codex.gtaliketeleport.GtaLikeTeleportServer.markNextServerTeleportBypassed(player);
            }
        });
    }

    public static boolean canSendStart(ServerPlayer player) {
        return player != null && player.connection != null;
    }

    public static void sendStart(ServerPlayer player, long requestId, int source, Vec3 targetFeet, ResourceKey<Level> targetDimension) {
        if (!canSendStart(player)) {
            return;
        }
        PacketDistributor.sendToPlayer(player, new StartServerTeleportPayload(
                requestId,
                source,
                targetFeet.x(),
                targetFeet.y(),
                targetFeet.z(),
                dimensionId(targetDimension)
        ));
    }

    private static String dimensionId(ResourceKey<Level> targetDimension) {
        String dimension = DimensionIds.fromResourceKey(targetDimension);
        return dimension == null ? "" : dimension;
    }
}
