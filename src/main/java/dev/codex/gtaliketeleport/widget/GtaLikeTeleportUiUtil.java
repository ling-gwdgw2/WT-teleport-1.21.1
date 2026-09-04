package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class GtaLikeTeleportUiUtil {
    public static final int TEXT_COLOR = 0xFFFFFFFF;
    public static final int MUTED_TEXT_COLOR = 0xFFA0A0A0;

    private GtaLikeTeleportUiUtil() {
    }

    public static void drawScaledText(GuiGraphics context, Font font, Component text, int x, int y, int color, double scale) {
        if (Math.abs(scale - 1.0D) < 0.005D) {
            context.drawString(font, text, x, y, color);
            return;
        }

        context.pose().pushPose();
        context.pose().translate((float) x, (float) y, 0.0F);
        context.pose().scale((float) scale, (float) scale, 1.0F);
        context.drawString(font, text, 0, 0, color, false);
        context.pose().popPose();
    }

    public static void drawScaledCenteredText(GuiGraphics context, Font font, Component text, int centerX, int y, int color, double scale) {
        if (Math.abs(scale - 1.0D) < 0.005D) {
            context.drawCenteredString(font, text, centerX, y, color);
            return;
        }

        int x = centerX - (int) Math.round(font.width(text) * scale / 2.0D);
        drawScaledText(context, font, text, x, y, color, scale);
    }
}
