package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

public class UnderLineComponentWidget extends ComponentWidget{

    private int lineColor = 0xFF523629;
    private int lineHeight = 4;

    public UnderLineComponentWidget(Component component) {
        this(0, 0, component);
    }

    public UnderLineComponentWidget(int x, int y, Component component) {
        super(x, y, component);
    }

    public UnderLineComponentWidget setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public UnderLineComponentWidget setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, this.getX(), this.getY()+this.height-lineHeight, this.width, lineHeight, 0.02f, lineColor);
        RenderSystem.disableBlend();
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }


}
