package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public final class LinkLockButton extends AbstractWidget {
    private final Runnable onPress;
    private boolean locked;

    public LinkLockButton(int x, int y, int width, int height, Runnable onPress) {
        super(x, y, width, height, Component.empty());
        this.onPress = onPress;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float tickProgress) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int background = !this.active ? 0xAA303030 : isHoveredOrFocused() ? 0xCC555555 : 0xAA3A3A3A;
        int border = !this.active ? 0xFF777777 : 0xFFFFFFFF;
        int textColor = !this.active ? 0xFF777777 : 0xFFFFFFFF;
        context.fill(x, y, x + width, y + height, background);
        context.renderOutline(x, y, width, height, border);

        Component icon = Component.literal(this.locked ? "🔒" : "🔓");
        Font font = Minecraft.getInstance().font;
        int iconWidth = Math.max(1, font.width(icon));
        int iconHeight = Math.max(1, font.lineHeight);
        double scale = Math.min((width - 4) / (double) iconWidth, (height - 4) / (double) iconHeight);
        scale = Math.max(0.25D, Math.min(4.0D, scale));
        int textHeight = Math.max(1, (int) Math.round(iconHeight * scale));
        int textY = y + (height - textHeight) / 2;
        GtaLikeTeleportUiUtil.drawScaledCenteredText(context, font, icon, x + width / 2, textY, textColor, scale);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible || !isMouseOver(mouseX, mouseY)) {
            return false;
        }
        this.onPress.run();
        playDownSound(Minecraft.getInstance().getSoundManager());
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, getMessage());
    }
}
