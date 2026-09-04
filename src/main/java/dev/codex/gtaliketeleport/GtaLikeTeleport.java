package dev.codex.gtaliketeleport;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GtaLikeTeleport.MOD_ID)
public final class GtaLikeTeleport {
    public static final String MOD_ID = "wt_teleport";
    public static final Logger LOGGER = LogManager.getLogger("WT Teleport");

    public GtaLikeTeleport(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Initializing WT Teleport for NeoForge 1.21.1");

        // Register the common setup method
        modEventBus.addListener(this::commonSetup);

        // Register the network payload registration
        modEventBus.addListener(this::registerNetwork);

        // Register server-side event listener on NeoForge event bus
        NeoForge.EVENT_BUS.register(GtaLikeTeleportServer.class);

        // Register client setup if we are on the physical client side
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> new GtaLikeTeleportConfigScreen(parent));
        }
    }

    private void registerNetwork(final net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent event) {
        final net.neoforged.neoforge.network.registration.PayloadRegistrar registrar = event.registrar(MOD_ID).versioned("1.0.0");

        registrar.playToClient(
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.StartServerTeleportPayload.TYPE,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.StartServerTeleportPayload.CODEC,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportClientNetworking::handleStartTeleport
        );

        registrar.playToServer(
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.ServerTeleportAckPayload.TYPE,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.ServerTeleportAckPayload.CODEC,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportServerNetworking::handleTeleportAck
        );

        registrar.playToServer(
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.BypassNextServerTeleportPayload.TYPE,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.BypassNextServerTeleportPayload.CODEC,
                dev.codex.gtaliketeleport.network.GtaLikeTeleportServerNetworking::handleBypassNextServerTeleport
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Grand Teleport Common Setup");
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            GtaLikeTeleportConfig.load();
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Grand Teleport Client Setup");
        dev.codex.gtaliketeleport.client.GtaLikeTeleportClient.init();
        NeoForge.EVENT_BUS.register(dev.codex.gtaliketeleport.client.GtaLikeTeleportClient.class);
    }

}
