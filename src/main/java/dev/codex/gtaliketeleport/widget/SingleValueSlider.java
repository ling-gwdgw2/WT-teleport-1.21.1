package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.function.Consumer;

public final class SingleValueSlider extends AbstractWidget {
    public static final int HEIGHT = 44;
    private static final int FRAME_INSET_Y = 2;
    private static final int HANDLE_WIDTH = 7;
    private static final int TRACK_MARGIN = 14;
    private static final int TRACK_Y = 29;
    private static final int TRACK_HEIGHT = 3;

    private final Consumer<Double> onChanged;
    private final double min;
    private final double max;
    private final double step;
    private final boolean integerValue;
    private final String suffix;
    private double value;
    private boolean dragging;

    public SingleValueSlider(int x, int y, int width, Component label, double value, double min, double max, double step, boolean integerValue, String suffix, Consumer<Double> onChanged) {
        super(x, y, width, HEIGHT, label);
        this.min = min;
        this.max = max;
        this.step = step;
        this.integerValue = integerValue;
        this.suffix = suffix;
        this.onChanged = onChanged;
        this.value = sanitize(value);
    }

    public void setValue(double value) {
        this.value = sanitize(value);
    }

    public double getValue() {
        return this.value;
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
        Component formatted = Component.literal(formatValue());
        double textScale = fitTopTextScale(scale, getMessage(), formatted);
        GtaLikeTeleportUiUtil.drawScaledText(context, Minecraft.getInstance().font, getMessage(), x + scaled(8, textScale), y + scaled(7, textScale), textColor, textScale);
        int valueWidth = (int) Math.round(Minecraft.getInstance().font.width(formatted) * textScale);
        GtaLikeTeleportUiUtil.drawScaledText(context, Minecraft.getInstance().font, formatted, right - valueWidth - scaled(8, textScale), y + scaled(7, textScale), valueColor, textScale);

        int trackLeft = x + getTrackMargin();
        int trackRight = right - getTrackMargin();
        int trackY = y + getTrackY();
        int trackHeight = Math.max(1, scaled(TRACK_HEIGHT, scale));
        context.fill(trackLeft, trackY, trackRight, trackY + trackHeight, trackColor);

        int handleWidth = Math.max(3, scaled(HANDLE_WIDTH, scale));
        int handleX = valueToX(this.value);
        int handleLeft = handleX - handleWidth / 2;
        int handleRight = handleLeft + handleWidth;
        int handleColor = !this.active ? 0xFF777777 : this.dragging ? 0xFFFFFFFF : 0xFFCCCCCC;
        context.fill(handleLeft, trackY - scaled(8, scale), handleRight, trackY + scaled(12, scale), handleColor);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible || !isMouseOver(mouseX, mouseY)) {
            return false;
        }

        this.dragging = true;
        updateValueFromMouse(mouseX);
        playDownSound(Minecraft.getInstance().getSoundManager());
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.dragging) {
            return false;
        }

        updateValueFromMouse(mouseX);
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!this.dragging) {
            return false;
        }

        this.dragging = false;
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, Component.literal(getMessage().getString() + ": " + formatValue()));
    }

    private void updateValueFromMouse(double mouseX) {
        double nextValue = sanitize(xToValue(mouseX));
        if (Math.abs(nextValue - this.value) < 0.0001D) {
            return;
        }
        this.value = nextValue;
        this.onChanged.accept(this.value);
    }

    private int valueToX(double value) {
        double progress = (value - this.min) / Math.max(0.0001D, this.max - this.min);
        int trackLeft = getX() + getTrackMargin();
        int trackWidth = getWidth() - getTrackMargin() * 2;
        return trackLeft + (int) Math.round(clamp(progress, 0.0D, 1.0D) * trackWidth);
    }

    private double xToValue(double mouseX) {
        int trackLeft = getX() + getTrackMargin();
        int trackWidth = getWidth() - getTrackMargin() * 2;
        double progress = (mouseX - trackLeft) / Math.max(1.0D, trackWidth);
        return this.min + clamp(progress, 0.0D, 1.0D) * (this.max - this.min);
    }

    private double sanitize(double rawValue) {
        double clamped = clamp(rawValue, this.min, this.max);
        if (this.step > 0.0D) {
            clamped = Math.round(clamped / this.step) * this.step;
        }
        if (this.integerValue) {
            clamped = Math.round(clamped);
        }
        return clamp(clamped, this.min, this.max);
    }

    private String formatValue() {
        if (this.integerValue) {
            return Integer.toString((int) Math.round(this.value)) + this.suffix;
        }
        return String.format(Locale.ROOT, "%.1f%s", this.value, this.suffix);
    }

    private double getVisualScale() {
        return Math.max(0.45D, Math.min(4.0D, getHeight() / (double) HEIGHT));
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
        int minY = Math.max(10, getHeight() / 2);
        int maxY = Math.max(minY, getHeight() - 8);
        return Math.max(minY, Math.min(maxY, trackY));
    }

    private static int scaled(int value, double scale) {
        return Math.max(1, (int) Math.round(value * scale));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
