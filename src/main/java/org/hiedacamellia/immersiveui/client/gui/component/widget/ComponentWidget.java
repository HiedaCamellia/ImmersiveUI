package org.hiedacamellia.immersiveui.client.gui.component.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ComponentWidget extends AbstractWidget {

    private static final Font font = Minecraft.getInstance().font;
    protected Component component;
    private boolean shadow = true;

    public ComponentWidget(Component component) {
        this(0, 0, component);
    }

    public ComponentWidget(int x, int y, Component component) {
        super(x, y, font.width(component), font.lineHeight, component);
        this.component = component;
    }

    public ComponentWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(font,component, this.getX(), this.getY(), 0xFFFFFF,shadow);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
