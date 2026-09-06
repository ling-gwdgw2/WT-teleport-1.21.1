package dev.codex.gtaliketeleport.client;

import dev.codex.gtaliketeleport.GtaLikeTeleport;
import dev.codex.gtaliketeleport.GtaLikeTeleportConfigScreen;
import dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.StartServerTeleportPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientModEvents {
    private ClientModEvents() {
    }

    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(ClientModEvents::clientSetup);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> new GtaLikeTeleportConfigScreen(parent));
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        GtaLikeTeleport.LOGGER.info("Grand Teleport Client Setup");
        GtaLikeTeleportClient.init();
        NeoForge.EVENT_BUS.register(GtaLikeTeleportClient.class);
    }

    public static void handleStartTeleport(final StartServerTeleportPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            GtaLikeTeleportClient.handleServerTeleportRequest(payload);
        });
    }
}
