package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

public class BaseBarWidget extends AbstractWidget implements IBarWidget{

    protected float borderX = 1f;
    protected float borderY = 1f;
    protected boolean isVertical = false;
    protected boolean isReverse = false;
    /**
     * Progress from 0 to 1
     */
    protected float progress = 0f;
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
    public void setProgress(float progress) {
        this.progress = Mth.clamp(progress, 0f, 1f);
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

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        int x = getX();
        int y = getY();

        IUIGuiUtils.fill(guiGraphics,x, y, width, height, backColor);
        if (isVertical) {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics,x, y + height - (height * progress), width, (height * progress), barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics,x, y, width, (height * progress), barColor);
            }
        } else {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics,x +width-  (width * progress), y, (width * progress), height, barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics,x, y,   (width * progress), height, barColor);
            }
        }
        if (border) {
            IUIGuiUtils.fillBorderRect(guiGraphics, x, y, width, height, borderX/width/2,borderY/height/2, borderColor);
        }

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
