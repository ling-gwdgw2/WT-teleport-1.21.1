package dev.codex.gtaliketeleport;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;

import java.lang.reflect.Method;

final class SodiumCompat {
    private static boolean active;

    private SodiumCompat() {
    }

    static boolean isLoaded() {
        return ModList.get().isLoaded("sodium")
                || ModList.get().isLoaded("rubidium")
                || ModList.get().isLoaded("embeddium");
    }

    static void beginTransition(Minecraft client, boolean fallbackTerrainMode) {
        if (active) {
            return;
        }

        active = true;
        scheduleTerrainUpdate();
    }

    static void endTransition() {
        if (!active) {
            return;
        }

        active = false;
        scheduleTerrainUpdate();
    }


    static void scheduleTerrainUpdate() {
        try {
            Class<?> rendererClass = null;
            try {
                rendererClass = Class.forName("net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer");
            } catch (ClassNotFoundException e) {
                try {
                    rendererClass = Class.forName("me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer");
                } catch (ClassNotFoundException ignored) {
                    return;
                }
            }

            Method instanceNullable = rendererClass.getMethod("instanceNullable");
            Object renderer = instanceNullable.invoke(null);
            if (renderer == null) {
                return;
            }

            rendererClass.getMethod("scheduleTerrainUpdate").invoke(renderer);
        } catch (ReflectiveOperationException | LinkageError ignored) {
        }
    }
}
