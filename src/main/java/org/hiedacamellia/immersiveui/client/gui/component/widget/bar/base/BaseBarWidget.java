package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.AbstractBarWidget;

public class BaseBarWidget extends AbstractBarWidget {

    protected float borderX = 1f;
    protected float borderY = 1f;
    protected boolean isVertical = false;
    protected boolean isReverse = false;
    protected boolean border = true;

    protected int barColor = 0xFFFFFFFF;
    protected int backColor = 0xFFFFFFFF;
    protected int borderColor = 0xFFFF0000;

    public void setBarColor(int color) {
        this.barColor = color;
    }
    public void setBackColor(int color) {
        this.backColor = color;
    }
    public void setBorderColor(int color) {
        this.borderColor = color;
    }
    public void vertical() {
        this.isVertical = true;
    }
    public void reverse() {
        this.isReverse = true;
    }
    public void noBorder() {
        this.border = false;
    }
    public void setBorderWidth(float borderX, float borderY) {
        this.borderX = borderX;
        this.borderY = borderY;
    }

    public BaseBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (border) {
            IUIGuiUtils.fillBorderRect(guiGraphics,getX(), getY(), width, height, borderX/width/2,borderY/height/2, borderColor);
        }
    }

    public void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        IUIGuiUtils.fill(guiGraphics,getX(), getY(), width, height, backColor);
    }

    public void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isVertical) {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * progress), width, (height * progress), barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics, getX(), getY(), width, (height * progress), barColor);
            }
        } else {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics, getX() +width-  (width * progress), getY(), (width * progress), height, barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics, getX(), getY(),   (width * progress), height, barColor);
            }
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
