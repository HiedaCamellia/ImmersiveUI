package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

/**
 * AbstractBarWidget 是一个抽象的进度条组件，继承自 AbstractWidget 并实现了 IBarWidget 接口。
 * 它提供了进度条的基本功能，包括进度设置、获取以及渲染逻辑。
 */
public abstract class AbstractBarWidget extends AbstractWidget implements IBarWidget {

    /**
     * 进度值，范围为 0 到 1。
     */
    protected float progress = 0f;

    /**
     * 设置进度值。
     * 如果传入的值小于 0，则设置为 0；如果大于 1，则设置为 1。
     *
     * @param progress 进度值（范围 0 到 1）
     */
    public void setProgress(float progress) {
        this.progress = Math.max(0f, Math.min(progress, 1f));
    }

    /**
     * 获取当前的进度值。
     *
     * @return 当前进度值（范围 0 到 1）
     */
    public float getProgress() {
        return progress;
    }

    /**
     * 构造一个 AbstractBarWidget 实例。
     *
     * @param x       组件的 X 坐标
     * @param y       组件的 Y 坐标
     * @param width   组件的宽度
     * @param height  组件的高度
     * @param message 组件的文本信息
     */
    public AbstractBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 渲染组件的逻辑。
     * 包括背景、进度条前后的效果、进度条本身以及边框的渲染。
     *
     * @param guiGraphics 渲染上下文
     * @param i           鼠标的 X 坐标
     * @param i1          鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        renderBack(guiGraphics, i, i1, v);         // 渲染背景
        renderBeforeBar(guiGraphics, i, i1, v);   // 渲染进度条之前的效果
        renderBar(guiGraphics, i, i1, v);         // 渲染进度条
        renderAfterBar(guiGraphics, i, i1, v);    // 渲染进度条之后的效果
        renderBorder(guiGraphics, i, i1, v);      // 渲染边框
    }
}