package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.layouts.LayoutElement;

/**
 * IBarWidget 接口表示一个进度条控件，支持渲染和布局功能，
 * 并允许自定义其外观和进度。
 */
public interface IBarWidget extends Renderable, LayoutElement {

    /**
     * 设置进度条为垂直显示。
     */
    void vertical();

    /**
     * 反转进度条的进度方向。
     */
    void reverse();

    /**
     * 设置进度条的进度值。
     *
     * @param progress 进度值（0.0 到 1.0）
     */
    void setProgress(float progress);

    /**
     * 获取当前进度条的进度值。
     *
     * @return 当前进度值（0.0 到 1.0）
     */
    float getProgress();

    /**
     * 在进度条本体渲染之前渲染额外内容。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    default void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    /**
     * 在进度条本体渲染之后渲染额外内容。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    default void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    /**
     * 渲染进度条的边框。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    /**
     * 渲染进度条本体（包括进度）。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    /**
     * 渲染进度条的背景。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

}