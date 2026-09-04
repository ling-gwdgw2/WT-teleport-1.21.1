package dev.codex.gtaliketeleport.widget;

import dev.codex.gtaliketeleport.GtaLikeTeleportConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.function.Consumer;

public final class StageHeightSlider extends AbstractWidget {
    public static final int HEIGHT = 44;
    private static final int FRAME_INSET_Y = 2;
    private static final int BASE_WIDTH = 368;
    private static final int HANDLE_WIDTH = 7;
    private static final int TRACK_MARGIN = 14;
    private static final int TRACK_Y = 29;
    private static final int TRACK_HEIGHT = 3;

    private final Consumer<double[]> onChanged;
    private double[] values;
    private int activeHandle = -1;

    public StageHeightSlider(int x, int y, int width, Component label, double[] values, Consumer<double[]> onChanged) {
        super(x, y, width, HEIGHT, label);
        this.values = GtaLikeTeleportConfig.sanitizeStageHeights(values);
        this.onChanged = onChanged;
    }

    public void setValues(double[] values) {
        this.values = GtaLikeTeleportConfig.sanitizeStageHeights(values);
        this.activeHandle = -1;
    }

    public void setEditable(boolean editable) {
        this.active = editable;
        if (!editable) {
            this.activeHandle = -1;
        }
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float tickProgress) {
        int x = getX();
        int y = getY();
        int right = x + getWidth();
        int bottom = y + getHeight();
        double scale = getVisualScale();
        int borderColor = !this.active ? 0xFF555555 : isHoveredOrFocused() ? 0xFFFFFFFF : 0xFF777777;
        int textColor = this.active ? 0xFFFFFFFF : 0xFF888888;
        int valueColor = this.active ? 0xFFE0E0E0 : 0xFF777777;
        int trackColor = this.active ? 0xFF777777 : 0xFF4D4D4D;

        int frameY = getFrameY();
        int frameHeight = getFrameHeight();
        context.fill(x, frameY, right, frameY + frameHeight, this.active ? 0xAA151515 : 0x88101010);
        context.renderOutline(x, frameY, getWidth(), frameHeight, borderColor);
        Component formatted = formatValues();
        double textScale = fitTopTextScale(scale, getMessage(), formatted);
        GtaLikeTeleportUiUtil.drawScaledText(context, Minecraft.getInstance().font, getMessage(), x + scaled(8, textScale), y + scaled(7, textScale), textColor, textScale);
        int valuesWidth = (int) Math.round(Minecraft.getInstance().font.width(formatted) * textScale);
        GtaLikeTeleportUiUtil.drawScaledText(context, Minecraft.getInstance().font, formatted, right - valuesWidth - scaled(8, textScale), y + scaled(7, textScale), valueColor, textScale);

        int trackLeft = x + getTrackMargin();
        int trackRight = right - getTrackMargin();
        int trackY = y + getTrackY();
        int trackHeight = Math.max(1, scaled(TRACK_HEIGHT, scale));
        context.fill(trackLeft, trackY, trackRight, trackY + trackHeight, trackColor);

        int handleWidth = Math.max(3, scaled(HANDLE_WIDTH, scale));
        int handleTop = scaled(8, scale);
        int handleBottom = scaled(12, scale);
        for (int i = 0; i < values.length; i++) {
            int handleX = valueToX(values[i]);
            int handleColor = !this.active ? 0xFF777777 : i == activeHandle ? 0xFFFFFFFF : 0xFFCCCCCC;
            int handleLeft = handleX - handleWidth / 2;
            int handleRight = handleLeft + handleWidth;
            context.fill(handleLeft, trackY - handleTop, handleRight, trackY + handleBottom, handleColor);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible || !isMouseOver(mouseX, mouseY)) {
            return false;
        }

        this.activeHandle = nearestHandle(mouseX);
        updateValueFromMouse(mouseX);
        playDownSound(Minecraft.getInstance().getSoundManager());
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.activeHandle < 0) {
            return false;
        }

        updateValueFromMouse(mouseX);
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.activeHandle < 0) {
            return false;
        }

        this.activeHandle = -1;
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, Component.literal(getMessage().getString() + ": " + formatValues().getString()));
    }

    private void updateValueFromMouse(double mouseX) {
        if (this.activeHandle < 0) {
            return;
        }

        double value = xToValue(mouseX);
        double min = this.activeHandle == 0
                ? GtaLikeTeleportConfig.getMinStageHeight()
                : this.values[this.activeHandle - 1] + GtaLikeTeleportConfig.getMinStageGap();
        double max = this.activeHandle == this.values.length - 1
                ? GtaLikeTeleportConfig.getMaxStageHeight()
                : this.values[this.activeHandle + 1] - GtaLikeTeleportConfig.getMinStageGap();
        this.values[this.activeHandle] = clamp(Math.rint(value), min, max);
        this.onChanged.accept(this.values.clone());
    }

    private int nearestHandle(double mouseX) {
        int nearest = 0;
        double nearestDistance = Double.MAX_VALUE;
        for (int i = 0; i < this.values.length; i++) {
            double distance = Math.abs(mouseX - valueToX(this.values[i]));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = i;
            }
        }
        return nearest;
    }

    private int valueToX(double value) {
        double min = GtaLikeTeleportConfig.getMinStageHeight();
        double max = GtaLikeTeleportConfig.getMaxStageHeight();
        double progress = (value - min) / Math.max(1.0D, max - min);
        int trackLeft = getX() + getTrackMargin();
        int trackWidth = getWidth() - getTrackMargin() * 2;
        return trackLeft + (int) Math.round(clamp(progress, 0.0D, 1.0D) * trackWidth);
    }

    private double xToValue(double mouseX) {
        int trackLeft = getX() + getTrackMargin();
        int trackWidth = getWidth() - getTrackMargin() * 2;
        double progress = (mouseX - trackLeft) / Math.max(1.0D, trackWidth);
        return GtaLikeTeleportConfig.getMinStageHeight()
                + clamp(progress, 0.0D, 1.0D)
                * (GtaLikeTeleportConfig.getMaxStageHeight() - GtaLikeTeleportConfig.getMinStageHeight());
    }

    private double getVisualScale() {
        double heightScale = getHeight() / (double) HEIGHT;
        double widthScale = getWidth() / (double) BASE_WIDTH;
        return Math.max(0.25D, Math.min(1.0D, Math.min(heightScale, widthScale)));
    }

    private int getFrameY() {
        return getY() + Math.min(FRAME_INSET_Y, Math.max(0, (getHeight() - 1) / 2));
    }

    private int getFrameHeight() {
        int inset = Math.min(FRAME_INSET_Y, Math.max(0, (getHeight() - 1) / 2));
        return Math.max(1, getHeight() - inset * 2);
    }

    private double fitTopTextScale(double scale, Component label, Component value) {
        int labelWidth = Minecraft.getInstance().font.width(label);
        int valueWidth = Minecraft.getInstance().font.width(value);
        int rawWidth = labelWidth + valueWidth + 28;
        int availableWidth = Math.max(1, getWidth() - 16);
        if (rawWidth > 0 && rawWidth * scale > availableWidth) {
            scale = availableWidth / (double) rawWidth;
        }
        return Math.max(0.25D, Math.min(1.0D, scale));
    }

    private int getTrackMargin() {
        return scaled(TRACK_MARGIN, getVisualScale());
    }

    private int getTrackY() {
        int trackY = scaled(TRACK_Y, getVisualScale());
        int min = Math.max(10, getHeight() / 2);
        int max = Math.max(min, getHeight() - 8);
        return Math.max(min, Math.min(max, trackY));
    }

    private static int scaled(int value, double scale) {
        return Math.max(1, (int) Math.round(value * scale));
    }

    private Component formatValues() {
        return Component.literal((int) values[0] + " / " + (int) values[1] + " / " + (int) values[2]);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public String toString() {
        return "StageHeightSlider{" + Arrays.toString(this.values) + '}';
    }
}
