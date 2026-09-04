package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class DimensionIconButton extends AbstractWidget {
    private final ItemStack icon;
    private final ResourceLocation texture;
    private final Runnable onPress;
    private boolean selected;

    public DimensionIconButton(int x, int y, int width, int height, ItemStack icon, Component label, ResourceLocation texture, Runnable onPress) {
        super(x, y, width, height, label);
        this.icon = icon.copy();
        this.texture = texture;
        this.onPress = onPress;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float tickProgress) {
        int x = getX();
        int y = getY();
        int right = x + getWidth();
        int bottom = y + getHeight();
        int borderColor = this.selected ? 0xFFFFFFFF : isHoveredOrFocused() ? 0xFFAAAAAA : 0xFF555555;
        context.fill(x, y, right, bottom, this.selected ? 0xAA202020 : 0xAA101010);
        context.renderOutline(x, y, getWidth(), getHeight(), borderColor);

        double scale = Math.max(0.5D, Math.min(4.0D, Math.min((getWidth() - 4) / 16.0D, (getHeight() - 4) / 16.0D)));
        int iconX = x + (int) Math.round((getWidth() - 16.0D * scale) / 2.0D);
        int iconY = y + (int) Math.round((getHeight() - 16.0D * scale) / 2.0D);
        if (!this.icon.isEmpty()) {
            context.pose().pushPose();
            context.pose().translate((float) iconX, (float) iconY, 0.0F);
            context.pose().scale((float) scale, (float) scale, 1.0F);
            context.renderItem(this.icon, 0, 0);
            context.pose().popPose();
        } else {
            drawTextureIcon(context, iconX, iconY, scale, this.texture);
        }

        if (!this.selected) {
            context.fill(x + 1, y + 1, Math.max(x + 1, right - 1), Math.max(y + 1, bottom - 1), 0x99000000);
        }
    }

    private static void drawTextureIcon(GuiGraphics context, int x, int y, double scale, ResourceLocation texture) {
        int size = Math.max(4, (int) Math.round(16.0D * scale));
        context.blit(texture, x, y, 0.0F, 0.0F, size, size, 16, 16);
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
