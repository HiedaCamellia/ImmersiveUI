package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class AreaComponentWidget extends ComponentWidget {

    private double y_offset = 0;
    private int textHeight;

    public AreaComponentWidget(int width, int height, Component component) {
        this(0, 0, width, height, component);
    }

    public AreaComponentWidget(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
        this.textHeight = font.lineHeight * font.split(component, width).size();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int yOff = (int) y_offset;
        for (FormattedCharSequence charSequence : font.split(component, width)) {
            if (yOff < 0) continue;
            if (yOff + font.lineHeight > height) return;
            guiGraphics.drawString(font, charSequence, this.getX(), this.getY() + yOff, getColor(), shadow);
            yOff += font.lineHeight;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (isHovered) {
            if (y_offset + scrollY < 0) {
                y_offset = 0;
            } else if (y_offset + scrollY + textHeight > height) {
                y_offset = height - textHeight;
            } else {
                y_offset += scrollY;
            }
            return true;
        }

        return false;
    }
}
