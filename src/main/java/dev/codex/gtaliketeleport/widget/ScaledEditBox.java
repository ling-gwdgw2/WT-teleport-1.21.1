package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public final class ScaledEditBox extends EditBox {
    private final Font font;
    private double textScale;
    private boolean selectingText;
    private int selectionAnchor;

    public ScaledEditBox(Font font, int x, int y, int width, int height, Component message, double textScale) {
        super(font, x, y, width, height, message);
        this.font = font;
        setTextScale(textScale);
    }

    public void setTextScale(double textScale) {
        this.textScale = Math.max(0.25D, Math.min(4.0D, textScale));
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float tickProgress) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int right = x + width;
        int bottom = y + height;
        int borderColor = isFocused() ? 0xFFFFFFFF : 0xFFAAAAAA;
        int textColor = this.active ? GtaLikeTeleportUiUtil.TEXT_COLOR : GtaLikeTeleportUiUtil.MUTED_TEXT_COLOR;

        context.fill(x, y, right, bottom, 0xFF000000);
        context.renderOutline(x, y, width, height, borderColor);

        String rawValue = getValue();
        Component value = Component.literal(rawValue);
        double scale = getTextScale(value);
        int textHeight = getTextHeight(scale);
        int textX = getTextX(scale);
        int textY = getTextY(textHeight);
        drawSelection(context, rawValue, textX, textY, textHeight, scale);
        GtaLikeTeleportUiUtil.drawScaledText(context, this.font, value, textX, textY, textColor, scale);

        if (isFocused()) {
            int cursorPosition = Math.max(0, Math.min(getCursorPosition(), rawValue.length()));
            int cursorX = getCursorX(rawValue, cursorPosition, textX, scale);
            int cursorWidth = Math.max(1, (int) Math.round(scale));
            if (cursorX < right - 3) {
                context.fill(cursorX, textY - 1, cursorX + cursorWidth, textY + textHeight + 1, textColor);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible || button != 0 || !isMouseOver(mouseX, mouseY)) {
            return false;
        }

        int cursor = getCursorPositionForMouse(mouseX);
        setFocused(true);
        setCursorPosition(cursor);
        setHighlightPos(cursor);
        this.selectionAnchor = cursor;
        this.selectingText = true;
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.selectingText || button != 0) {
            return false;
        }

        int cursor = getCursorPositionForMouse(mouseX);
        setCursorPosition(cursor);
        setHighlightPos(this.selectionAnchor);
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!this.selectingText || button != 0) {
            return false;
        }

        this.selectingText = false;
        return true;
    }

    private double getTextScale(Component text) {
        double scale = Math.max(0.45D, Math.min(4.0D, this.textScale));
        int textWidth = this.font.width(text);
        int maxTextWidth = Math.max(1, getWidth() - 8);
        if (textWidth > 0 && textWidth * scale > maxTextWidth) {
            scale = maxTextWidth / (double) textWidth;
        }
        return Math.max(0.45D, Math.min(4.0D, scale));
    }

    private int getTextHeight(double scale) {
        return Math.max(1, (int) Math.round(this.font.lineHeight * scale));
    }

    private int getTextX(double scale) {
        return getX() + Math.max(4, (int) Math.round(4 * scale));
    }

    private int getTextY(int textHeight) {
        return getY() + (getHeight() - textHeight) / 2;
    }

    private int getCursorX(String value, int cursorPosition, int textX, double scale) {
        int cursor = Math.max(0, Math.min(cursorPosition, value.length()));
        return textX + (int) Math.round(this.font.width(value.substring(0, cursor)) * scale);
    }

    private int getCursorPositionForMouse(double mouseX) {
        String value = getValue();
        double scale = getTextScale(Component.literal(value));
        double localX = (mouseX - getTextX(scale)) / scale;
        if (localX <= 0.0D) {
            return 0;
        }

        for (int i = 1; i <= value.length(); i++) {
            int previous = this.font.width(value.substring(0, i - 1));
            int current = this.font.width(value.substring(0, i));
            if (localX < (previous + current) / 2.0D) {
                return i - 1;
            }
        }
        return value.length();
    }

    private void drawSelection(GuiGraphics context, String value, int textX, int textY, int textHeight, double scale) {
        String selected = getHighlighted();
        if (selected == null || selected.isEmpty()) {
            return;
        }

        int cursor = Math.max(0, Math.min(getCursorPosition(), value.length()));
        int selectedLength = selected.length();
        int start = cursor - selectedLength;
        int end = cursor;
        if (!matchesSelection(value, selected, start, end)) {
            start = cursor;
            end = cursor + selectedLength;
        }
        if (!matchesSelection(value, selected, start, end)) {
            return;
        }

        int left = getCursorX(value, start, textX, scale);
        int right = getCursorX(value, end, textX, scale);
        context.fill(Math.min(left, right), textY - 1, Math.max(left, right), textY + textHeight + 1, 0xAA2F5BBA);
    }

    private boolean matchesSelection(String value, String selected, int start, int end) {
        return start >= 0 && end <= value.length() && start <= end && value.substring(start, end).equals(selected);
    }
}
