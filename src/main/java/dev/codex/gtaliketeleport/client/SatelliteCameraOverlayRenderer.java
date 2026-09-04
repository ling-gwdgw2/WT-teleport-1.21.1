package dev.codex.gtaliketeleport.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.codex.gtaliketeleport.GtaLikeTeleportConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

/**
 * Full-screen satellite-camera overlays for environments where vanilla {@code loadEffect}
 * is unavailable. Exposure follows GTA-style zoom-step gamma pulses (no rectangular hotspots).
 */
public final class SatelliteCameraOverlayRenderer {
    private static final float SHADER_SUPPLEMENT_SCALE = 0.4F;

    private SatelliteCameraOverlayRenderer() {
    }

    public static void render(GuiGraphics context, float colorGradeStrength, float exposureIntensity, boolean shaderSupplement) {
        if (!GtaLikeTeleportConfig.isSatelliteCameraFxEnabled()) {
            return;
        }

        float scale = shaderSupplement ? SHADER_SUPPLEMENT_SCALE : 1.0F;
        float grade = Mth.clamp(colorGradeStrength * scale, 0.0F, 1.0F);
        float exposure = Mth.clamp(exposureIntensity * scale, 0.0F, 1.0F);

        if (grade > 0.01F) {
            renderColorGrade(context, grade);
        }
        if (exposure > 0.01F) {
            renderGammaPulse(context, exposure);
        }
    }

    private static void renderColorGrade(GuiGraphics context, float strength) {
        int width = context.guiWidth();
        int height = context.guiHeight();

        RenderSystem.enableBlend();

        RenderSystem.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ZERO);
        int multiplyAlpha = (int) (255.0F * strength * 0.55F);
        context.fill(0, 0, width, height, argb(multiplyAlpha, 168, 178, 128));

        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int pushAlpha = (int) (255.0F * strength * 0.12F);
        context.fill(0, 0, width, height, argb(pushAlpha, 72, 92, 38));

        RenderSystem.defaultBlendFunc();
    }

    private static void renderGammaPulse(GuiGraphics context, float intensity) {
        int width = context.guiWidth();
        int height = context.guiHeight();
        float lift = (float) GtaLikeTeleportConfig.getSatelliteGammaOverlayLift();
        float blowoutGate = (float) GtaLikeTeleportConfig.getSatelliteGammaBlowoutStrength();
        float pulse = intensity * intensity;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

        int liftAlpha = (int) (255.0F * intensity * lift);
        context.fill(0, 0, width, height, argb(liftAlpha, 255, 250, 238));

        if (intensity > blowoutGate) {
            float blowout = (intensity - blowoutGate) / Math.max(0.001F, 1.0F - blowoutGate);
            int blowAlpha = (int) (255.0F * blowout * blowout * 0.42F * pulse);
            context.fill(0, 0, width, height, argb(blowAlpha, 255, 255, 255));
        }

        RenderSystem.defaultBlendFunc();
    }

    private static int argb(int alpha, int red, int green, int blue) {
        return (clamp(alpha) << 24) | (clamp(red) << 16) | (clamp(green) << 8) | clamp(blue);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}