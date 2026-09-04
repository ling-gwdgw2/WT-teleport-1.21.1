package dev.codex.gtaliketeleport.network;

import dev.codex.gtaliketeleport.GtaLikeTeleport;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class GtaLikeTeleportNetworkPayloads {
    public static final int SOURCE_EXTERNAL = 1;
    public static final int SOURCE_WARP_PLATE = 2;

    private GtaLikeTeleportNetworkPayloads() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(GtaLikeTeleport.MOD_ID, path);
    }

    public record StartServerTeleportPayload(long requestId, int source, double x, double y, double z, String dimension) implements CustomPacketPayload {
        public static final Type<StartServerTeleportPayload> TYPE = new Type<>(id("start_server_teleport"));
        public static final StreamCodec<RegistryFriendlyByteBuf, StartServerTeleportPayload> CODEC = StreamCodec.ofMember(
                StartServerTeleportPayload::write,
                StartServerTeleportPayload::read
        );

        private void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeLong(this.requestId);
            buffer.writeInt(this.source);
            buffer.writeDouble(this.x);
            buffer.writeDouble(this.y);
            buffer.writeDouble(this.z);
            buffer.writeUtf(this.dimension);
        }

        private static StartServerTeleportPayload read(RegistryFriendlyByteBuf buffer) {
            return new StartServerTeleportPayload(
                    buffer.readLong(),
                    buffer.readInt(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readUtf()
            );
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ServerTeleportAckPayload(long requestId) implements CustomPacketPayload {
        public static final Type<ServerTeleportAckPayload> TYPE = new Type<>(id("server_teleport_ack"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ServerTeleportAckPayload> CODEC = StreamCodec.ofMember(
                ServerTeleportAckPayload::write,
                ServerTeleportAckPayload::read
        );

        private void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeLong(this.requestId);
        }

        private static ServerTeleportAckPayload read(RegistryFriendlyByteBuf buffer) {
            return new ServerTeleportAckPayload(buffer.readLong());
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record BypassNextServerTeleportPayload() implements CustomPacketPayload {
        public static final Type<BypassNextServerTeleportPayload> TYPE = new Type<>(id("bypass_next_server_teleport"));
        public static final StreamCodec<RegistryFriendlyByteBuf, BypassNextServerTeleportPayload> CODEC = StreamCodec.unit(
                new BypassNextServerTeleportPayload()
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
