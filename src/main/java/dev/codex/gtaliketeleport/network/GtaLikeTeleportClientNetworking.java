package dev.codex.gtaliketeleport.network;

import dev.codex.gtaliketeleport.DimensionIds;
import dev.codex.gtaliketeleport.network.GtaLikeTeleportNetworkPayloads.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class GtaLikeTeleportClientNetworking {
    private GtaLikeTeleportClientNetworking() {
    }

    public static void handleStartTeleport(final StartServerTeleportPayload payload, final IPayloadContext context) {
        dev.codex.gtaliketeleport.client.ClientModEvents.handleStartTeleport(payload, context);
    }

    public static void sendServerTeleportAck(long requestId) {
        PacketDistributor.sendToServer(new ServerTeleportAckPayload(requestId));
    }

    public static void sendBypassNextServerTeleport() {
        PacketDistributor.sendToServer(new BypassNextServerTeleportPayload());
    }

    public static Vec3 targetFeet(StartServerTeleportPayload payload) {
        return new Vec3(payload.x(), payload.y(), payload.z());
    }

    public static String targetDimensionId(StartServerTeleportPayload payload) {
        String dimension = payload.dimension();
        return DimensionIds.normalize(dimension);
    }
}
