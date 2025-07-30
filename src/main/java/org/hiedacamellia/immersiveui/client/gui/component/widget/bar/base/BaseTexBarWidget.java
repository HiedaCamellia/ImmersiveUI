package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.AbstractBarWidget;

/**
 * BaseTexBarWidget 是一个基础的纹理进度条组件，继承自 AbstractBarWidget。
 * 它支持自定义纹理、方向（水平或垂直）以及反向显示等功能。
 */
public class BaseTexBarWidget extends AbstractBarWidget {

    // 边框的水平宽度
    protected float borderX = 1f;
    // 边框的垂直宽度
    protected float borderY = 1f;
    // 是否为垂直方向
    protected boolean isVertical = false;
    // 是否反向显示
    protected boolean isReverse = false;

    // 进度条的纹理
    protected ResourceLocation barTex = null;
    // 背景的纹理
    protected ResourceLocation backTex = null;
    // 边框的纹理
    protected ResourceLocation borderTex = null;

    /**
     * 设置进度条、背景和边框的纹理。
     * 根据传入的纹理路径自动生成对应的纹理路径。
     *
     * @param tex 基础纹理路径
     */
    public void setTex(ResourceLocation tex) {
        String namespace = tex.getNamespace();
        String path = tex.getPath();
        String replace = path.replace(".png", "");
        setBackTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_back.png"));
        setBarTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar.png"));
        setBorderTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_border.png"));
    }

    /**
     * 设置进度条的纹理。
     *
     * @param barTex 进度条的纹理
     */
    public void setBarTex(ResourceLocation barTex) {
        this.barTex = barTex;
    }

    /**
     * 设置背景的纹理。
     *
     * @param backTex 背景的纹理
     */
    public void setBackTex(ResourceLocation backTex) {
        this.backTex = backTex;
    }

    /**
     * 设置边框的纹理。
     *
     * @param borderTex 边框的纹理
     */
    public void setBorderTex(ResourceLocation borderTex) {
        this.borderTex = borderTex;
    }

    /**
     * 将进度条设置为垂直方向。
     */
    public void vertical() {
        this.isVertical = true;
    }

    /**
     * 将进度条设置为反向显示。
     */
    public void reverse() {
        this.isReverse = true;
    }

    /**
     * 设置边框的宽度。
     *
     * @param borderX 边框的水平宽度
     * @param borderY 边框的垂直宽度
     */
    public void setBorderWidth(float borderX, float borderY) {
        this.borderX = borderX;
        this.borderY = borderY;
    }

    /**
     * 构造一个 BaseTexBarWidget 实例。
     *
     * @param x       组件的 X 坐标
     * @param y       组件的 Y 坐标
     * @param width   组件的宽度
     * @param height  组件的高度
     * @param message 组件的文本信息
     */
    public BaseTexBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 渲染边框。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    public void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (borderTex != null) {
            IUIGuiUtils.blit(guiGraphics, borderTex, getX(), getY(), getBorderTexWidth(), getBorderTexHeight());
        }
    }

    /**
     * 渲染背景。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    public void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (backTex != null) {
            IUIGuiUtils.blit(guiGraphics, backTex, getX(), getY(), getBackTexWidth(), getBackTexHeight());
        }
    }

    /**
     * 渲染进度条。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
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
                    IUIGuiUtils.blit(guiGraphics, barTex, (getX() + width - (width * progress)), getY(), (width * progress), height, width - (width * progress), 0, (getBarTexWidth() * progress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, barTex, getX(), getY(), (width * progress), height, 0, 0, (getBarTexWidth() * progress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                }
            }
        }
    }

    /**
     * 获取进度条纹理的高度。
     *
     * @return 进度条纹理的高度
     */
    protected int getBarTexHeight() {
        return height;
    }

    /**
     * 获取进度条纹理的宽度。
     *
     * @return 进度条纹理的宽度
     */
    protected int getBarTexWidth() {
        return width;
    }

    /**
     * 获取背景纹理的高度。
     *
     * @return 背景纹理的高度
     */
    protected int getBackTexHeight() {
        return height;
    }

    /**
     * 获取背景纹理的宽度。
     *
     * @return 背景纹理的宽度
     */
    protected int getBackTexWidth() {
        return width;
    }

    /**
     * 获取边框纹理的高度。
     *
     * @return 边框纹理的高度
     */
    protected int getBorderTexHeight() {
        return height;
    }

    /**
     * 获取边框纹理的宽度。
     *
     * @return 边框纹理的宽度
     */
    protected int getBorderTexWidth() {
        return width;
    }

    /**
     * 更新组件的叙述信息。
     * 当前实现为空。
     *
     * @param narrationElementOutput 叙述输出对象
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}