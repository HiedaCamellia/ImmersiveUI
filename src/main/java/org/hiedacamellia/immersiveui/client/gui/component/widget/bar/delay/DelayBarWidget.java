package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.delay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.animate.IAnimatable;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseBarWidget;

/**
 * DelayBarWidget 是一个带有延迟效果的进度条组件，继承自 BaseBarWidget。
 * 它支持在进度变化时添加延迟动画，并根据进度增加或减少显示不同的颜色。
 */
public class DelayBarWidget extends BaseBarWidget implements IAnimatable {

    // 进度增加时的颜色
    protected int delayIncreaseColor = 0xFFFF1111;
    // 进度减少时的颜色
    protected int delayDecreaseColor = 0xFF1111FF;
    // 进度变化的延迟时间（毫秒）
    protected long delay = 500;

    // 起始进度值
    protected float startProgress = 0.0f;
    // 目标进度值
    protected float targetProgress = 0.0f;

    // 延迟开始的时间戳
    protected long delayStartTime = 0;

    /**
     * 构造一个 DelayBarWidget 实例。
     *
     * @param x       组件的 X 坐标
     * @param y       组件的 Y 坐标
     * @param width   组件的宽度
     * @param height  组件的高度
     * @param message 组件的文本信息
     */
    public DelayBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 设置进度变化的延迟时间。
     *
     * @param delay 延迟时间（毫秒）
     */
    public void setDelay(long delay) {
        setAnimationDuration(delay);
    }

    /**
     * 设置进度增加时的颜色。
     *
     * @param color 颜色值（ARGB 格式）
     */
    public void setDelayIncreaseColor(int color) {
        this.delayIncreaseColor = color;
    }

    /**
     * 设置进度减少时的颜色。
     *
     * @param color 颜色值（ARGB 格式）
     */
    public void setDelayDecreaseColor(int color) {
        this.delayDecreaseColor = color;
    }

    /**
     * 检查当前进度是否为增加状态。
     *
     * @return 如果目标进度大于当前进度，则返回 true
     */
    protected boolean isIncrease() {
        return targetProgress > progress;
    }

    /**
     * 检查当前进度是否为减少状态。
     *
     * @return 如果目标进度小于当前进度，则返回 true
     */
    protected boolean isDecrease() {
        return targetProgress < progress;
    }

    /**
     * 设置目标进度值，并初始化延迟动画的相关参数。
     *
     * @param progress 目标进度值（范围 0 到 1）
     */
    @Override
    public void setProgress(float progress) {
        targetProgress = Mth.clamp(progress, 0, 1);
        startProgress = this.progress;
        startAnimation();
    }

    /**
     * 渲染组件，包括延迟动画的逻辑。
     *
     * @param guiGraphics 渲染上下文
     * @param i           鼠标的 X 坐标
     * @param i1          鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        runningTime();
        super.renderWidget(guiGraphics, i, i1, v);
    }

    /**
     * 在进度条之前渲染进度增加的颜色效果。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    public void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (isIncrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * targetProgress), width, (height * targetProgress), delayIncreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY(), width, (height * targetProgress), delayIncreaseColor);
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX() + width - (width * targetProgress), getY(), (width * targetProgress), height, delayIncreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY(), (width * targetProgress), height, delayIncreaseColor);
                }
            }
        }
    }

    /**
     * 在进度条之后渲染进度减少的颜色效果。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    public void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (isDecrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * (progress - targetProgress)), width, (height * (progress - targetProgress)), delayDecreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY() + (height * targetProgress), width, (height * (progress - targetProgress)), delayDecreaseColor);
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX() + width - (width * (progress - targetProgress)), getY(), (width * (progress - targetProgress)), height, delayDecreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX() + (width * targetProgress), getY(), (width * (progress - targetProgress)), height, delayDecreaseColor);
                }
            }
        }
    }

    /**
     * 获取动画起始时间。
     */
    @Override
    public long getAnimationStartTime() {
        return this.delayStartTime;
    }

    /**
     * 设置动画起始时间。
     */
    @Override
    public void setAnimationStartTime(long time) {
        this.delayStartTime = time;
    }

    /**
     * 获取动画持续时间。
     */
    @Override
    public long getAnimationDuration() {
        return this.delay;
    }

    /**
     * 设置动画持续时间。
     */
    @Override
    public void setAnimationDuration(long duration) {
        this.delay = duration;
    }

    /**
     * 处理进度动画的时间推进。
     */
    @Override
    public void runningTime() {
        if (isAnimationEnd()) {
            progress = targetProgress;
        }
        float progressDelta = getElapsedRatio();
        if (isIncrease()) {
            progress = Mth.lerp(progressDelta, startProgress, targetProgress);
            if (progress >= targetProgress) {
                progress = targetProgress;
            }
        } else if (isDecrease()) {
            progress = Mth.lerp(progressDelta, startProgress, targetProgress);
            if (progress <= targetProgress) {
                progress = targetProgress;
            }
        }
    }
}