package dev.codex.gtaliketeleport.client;

import com.mojang.logging.LogUtils;
import dev.codex.gtaliketeleport.GtaLikeTeleport;
import dev.codex.gtaliketeleport.TeleportTransitionController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

/**
 * Manages the satellite-camera GLSL post-processing chain during teleport transitions.
 * Uniforms must be applied immediately before {@link PostChain#process(float)} each frame.
 */
public final class SatelliteCameraEffectManager {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation POST_EFFECT_ID = ResourceLocation.fromNamespaceAndPath(
            GtaLikeTeleport.MOD_ID,
            "shaders/post/satellite_camera.json"
    );

    public enum State {
        IDLE,
        ACTIVE,
        FAILED
    }

    private static State state = State.IDLE;

    private SatelliteCameraEffectManager() {
    }

    public static State getState() {
        return state;
    }

    public static boolean isShaderActive() {
        return state == State.ACTIVE;
    }

    public static void begin(Minecraft client) {
        state = State.FAILED;
        if (client == null || client.gameRenderer == null) {
            return;
        }

        client.gameRenderer.shutdownEffect();

        if (!TeleportTransitionController.isSatellitePostEffectSupported()) {
            LOGGER.info("[Grand Teleport] Satellite post-effect skipped (renderer incompatible, using GUI fallback)");
            return;
        }

        try {
            client.gameRenderer.loadEffect(POST_EFFECT_ID);
            PostChain chain = client.gameRenderer.currentEffect();
            if (chain != null) {
                state = State.ACTIVE;
                LOGGER.info("[Grand Teleport] Satellite post-effect loaded ({})", POST_EFFECT_ID);
            } else {
                LOGGER.warn("[Grand Teleport] Satellite post-effect chain is null after load ({})", POST_EFFECT_ID);
            }
        } catch (Exception exception) {
            LOGGER.warn("[Grand Teleport] Failed to load satellite post-effect ({})", POST_EFFECT_ID, exception);
            client.gameRenderer.shutdownEffect();
        }
    }

    public static void end(Minecraft client) {
        state = State.IDLE;
        if (client != null && client.gameRenderer != null) {
            client.gameRenderer.shutdownEffect();
        }
    }

    public static void updateUniforms(Minecraft client, float tickProgress) {
        if (!TeleportTransitionController.isRunning() || client == null || client.gameRenderer == null || state != State.ACTIVE) {
            return;
        }

        PostChain chain = client.gameRenderer.currentEffect();
        if (chain == null) {
            state = State.FAILED;
            return;
        }

        float[] intensities = TeleportTransitionController.computeShaderIntensities(tickProgress);
        chain.setUniform("ExposureIntensity", intensities[0]);
        chain.setUniform("ColorGradeStrength", intensities[1]);
    }

    public static float[] computeIntensities(float tickProgress) {
        return TeleportTransitionController.computeShaderIntensities(tickProgress);
    }
}