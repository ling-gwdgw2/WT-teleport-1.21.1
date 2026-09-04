package dev.codex.gtaliketeleport.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public final class IconButton extends Button {
    private final ItemStack icon;
    private final Supplier<Boolean> activeStateSupplier;
    private final Supplier<Integer> customColorBoxSupplier;

    public IconButton(int x, int y, int width, int height, Component message, ItemStack icon,
                      Supplier<Boolean> activeStateSupplier, Supplier<Integer> customColorBoxSupplier,
                      OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.icon = icon.copy();
        this.activeStateSupplier = activeStateSupplier;
        this.customColorBoxSupplier = customColorBoxSupplier;
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float tickProgress) {
        Component label = getMessage();
        setMessage(Component.empty());
        super.renderWidget(context, mouseX, mouseY, tickProgress);
        setMessage(label);

        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        int boxSize = 12;
        int boxX = x + w - boxSize - 6;
        int boxY = y + (h - boxSize) / 2;

        if (this.icon != null && !this.icon.isEmpty()) {
            context.renderItem(this.icon, x + 4, y + (h - 16) / 2);
        }

        int textX = x + 24;
        int textY = y + (h - 9) / 2;
        int textColor = isHoveredOrFocused() ? 0xFFFFFFFF : 0xFFE0E0E0;
        int maxTextWidth = Math.max(0, boxX - textX - 4);
        var font = Minecraft.getInstance().font;
        String rendered = maxTextWidth > 0
                ? font.plainSubstrByWidth(label.getString(), maxTextWidth)
                : label.getString();
        context.drawString(font, rendered, textX, textY, textColor, false);

        if (this.customColorBoxSupplier != null && this.customColorBoxSupplier.get() != null) {
            int color = this.customColorBoxSupplier.get();
            context.fill(boxX, boxY, boxX + boxSize, boxY + boxSize, color | 0xFF000000);
            context.renderOutline(boxX - 1, boxY - 1, boxSize + 2, boxSize + 2, 0xFFFFFFFF);
        } else if (this.activeStateSupplier != null) {
            boolean active = this.activeStateSupplier.get();
            int boxColor = active ? 0xFF00FF00 : 0xFFFF0000;
            context.fill(boxX, boxY, boxX + boxSize, boxY + boxSize, boxColor);
            context.renderOutline(boxX - 1, boxY - 1, boxSize + 2, boxSize + 2, 0xFF000000);
        }
    }
}
