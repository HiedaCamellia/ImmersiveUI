package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.AbstractBarWidget;

/**
 * BaseBarWidget 是一个基础的进度条组件，继承自 AbstractBarWidget。
 * 它支持自定义颜色、边框、方向（水平或垂直）以及反向显示等功能。
 */
public class BaseBarWidget extends AbstractBarWidget {

    // 边框的水平宽度
    protected float borderX = 1f;
    // 边框的垂直宽度
    protected float borderY = 1f;
    // 是否为垂直方向
    protected boolean isVertical = false;
    // 是否反向显示
    protected boolean isReverse = false;
    // 是否显示边框
    protected boolean border = true;

    // 进度条的颜色
    protected int barColor = 0xFFFFFFFF;
    // 背景的颜色
    protected int backColor = 0xFFFFFFFF;
    // 边框的颜色
    protected int borderColor = 0xFFFF0000;

    /**
     * 设置进度条的颜色。
     *
     * @param color 进度条的颜色（ARGB 格式）
     */
    public void setBarColor(int color) {
        this.barColor = color;
    }

    /**
     * 设置背景的颜色。
     *
     * @param color 背景的颜色（ARGB 格式）
     */
    public void setBackColor(int color) {
        this.backColor = color;
    }

    /**
     * 设置边框的颜色。
     *
     * @param color 边框的颜色（ARGB 格式）
     */
    public void setBorderColor(int color) {
        this.borderColor = color;
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
     * 禁用边框显示。
     */
    public void noBorder() {
        this.border = false;
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
     * 构造一个 BaseBarWidget 实例。
     *
     * @param x       组件的 X 坐标
     * @param y       组件的 Y 坐标
     * @param width   组件的宽度
     * @param height  组件的高度
     * @param message 组件的文本信息
     */
    public BaseBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 渲染边框。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    public void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (border) {
            IUIGuiUtils.fillBorderRect(guiGraphics, getX(), getY(), width, height, borderX / width / 2, borderY / height / 2, borderColor);
        }
    }

    /**
     * 渲染背景。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    public void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        IUIGuiUtils.fill(guiGraphics, getX(), getY(), width, height, backColor);
    }

    /**
     * 渲染进度条。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    public void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isVertical) {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * progress), width, (height * progress), barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics, getX(), getY(), width, (height * progress), barColor);
            }
        } else {
            if (isReverse) {
                IUIGuiUtils.fill(guiGraphics, getX() + width - (width * progress), getY(), (width * progress), height, barColor);
            } else {
                IUIGuiUtils.fill(guiGraphics, getX(), getY(), (width * progress), height, barColor);
            }
        }
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