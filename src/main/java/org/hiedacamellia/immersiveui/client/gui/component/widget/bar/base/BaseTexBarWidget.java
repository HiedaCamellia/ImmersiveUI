package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.AbstractBarWidget;

public class BaseTexBarWidget extends AbstractBarWidget {

    protected float borderX = 1f;
    protected float borderY = 1f;
    protected boolean isVertical = false;
    protected boolean isReverse = false;

    protected ResourceLocation barTex = null;
    protected ResourceLocation backTex = null;
    protected ResourceLocation borderTex = null;

    public void setTex(ResourceLocation tex) {
        String namespace = tex.getNamespace();
        String path = tex.getPath();
        String replace = path.replace(".png", "");
        setBackTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_back.png"));
        setBarTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar.png"));
        setBorderTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_border.png"));
    }

    public void setBarTex(ResourceLocation barTex) {
        this.barTex = barTex;
    }

    public void setBackTex(ResourceLocation backTex) {
        this.backTex = backTex;
    }

    public void setBorderTex(ResourceLocation borderTex) {
        this.borderTex = borderTex;
    }

    public void vertical() {
        this.isVertical = true;
    }

    public void reverse() {
        this.isReverse = true;
    }

    public void setBorderWidth(float borderX, float borderY) {
        this.borderX = borderX;
        this.borderY = borderY;
    }

    public BaseTexBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (borderTex != null) {
            IUIGuiUtils.blit(guiGraphics, borderTex, getX(), getY(), getBorderTexWidth(), getBorderTexHeight());
        }
    }

    public void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (backTex != null) {
            IUIGuiUtils.blit(guiGraphics, backTex, getX(), getY(), getBackTexWidth(), getBackTexHeight());
        }
    }

    public void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (barTex != null) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, barTex, getX(), (getY() + height - (height * progress)), width, (height * progress), 0, height - (height * progress), getBarTexWidth(), (getBarTexHeight() * progress), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, barTex, getX(), getY(), width, (height * progress), 0, 0, getBarTexWidth(), (getBarTexHeight() * progress), getBarTexWidth(), getBarTexHeight());
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, barTex, (getX() + width - (width * progress)),  getY(), (width * progress),  height, width - (width * progress),  0, (getBarTexWidth() * progress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, barTex, getX(), getY(), (width * progress), height, 0, 0, (getBarTexWidth() * progress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                }
            }
        }
    }

    protected int getBarTexHeight() {
        return height;
    }

    protected int getBarTexWidth() {
        return width;
    }

    protected int getBackTexHeight() {
        return height;
    }

    protected int getBackTexWidth() {
        return width;
    }

    protected int getBorderTexHeight() {
        return height;
    }

    protected int getBorderTexWidth() {
        return width;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
