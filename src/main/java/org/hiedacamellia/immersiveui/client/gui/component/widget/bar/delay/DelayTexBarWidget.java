package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.delay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.animate.IAnimatable;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseTexBarWidget;

/**
 * DelayTexBarWidget 是一个带有延迟效果的纹理进度条组件，继承自 BaseTexBarWidget。
 * 它支持在进度变化时添加延迟动画，并根据进度增加或减少显示不同的纹理。
 */
public class DelayTexBarWidget extends BaseTexBarWidget implements IAnimatable {

    // 进度增加时的纹理
    protected ResourceLocation delayIncreaseTex = null;
    // 进度减少时的纹理
    protected ResourceLocation delayDecreaseTex = null;
    // 进度变化的延迟时间（毫秒）
    protected long delay = 500;

    // 起始进度值
    protected float startProgress = 0.0f;
    // 目标进度值
    protected float targetProgress = 0.0f;

    // 延迟开始的时间戳
    protected long delayStartTime = 0;

    /**
     * 构造一个 DelayTexBarWidget 实例。
     *
     * @param x       组件的 X 坐标
     * @param y       组件的 Y 坐标
     * @param width   组件的宽度
     * @param height  组件的高度
     * @param message 组件的文本信息
     */
    public DelayTexBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 设置进度条、背景和边框的纹理，并自动生成进度增加和减少的纹理路径。
     *
     * @param tex 基础纹理路径
     */
    @Override
    public void setTex(ResourceLocation tex) {
        super.setTex(tex);
        String namespace = tex.getNamespace();
        String path = tex.getPath();
        String replace = path.replace(".png", "");
        setDelayIncreaseTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar_increase.png"));
        setDelayDecreaseTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar_decrease.png"));
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
     * 设置进度增加时的纹理。
     *
     * @param tex 纹理路径
     */
    public void setDelayIncreaseTex(ResourceLocation tex) {
        this.delayIncreaseTex = tex;
    }

    /**
     * 设置进度减少时的纹理。
     *
     * @param tex 纹理路径
     */
    public void setDelayDecreaseTex(ResourceLocation tex) {
        this.delayDecreaseTex = tex;
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
     * 在进度条之前渲染进度增加的纹理效果。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    public void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (delayIncreaseTex != null && isIncrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), (getY() + height - (height * targetProgress)), width, (height * targetProgress), 0, height - (height * targetProgress), getBarTexWidth(), (getBarTexHeight() * targetProgress), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), getY(), width, (height * targetProgress), 0, 0, getBarTexWidth(), (getBarTexHeight() * targetProgress), getBarTexWidth(), getBarTexHeight());
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, (getX() + width - (width * targetProgress)), getY(), (width * targetProgress), height, width - (width * targetProgress), 0, (getBarTexWidth() * targetProgress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), getY(), (width * targetProgress), height, 0, 0, (getBarTexWidth() * targetProgress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                }
            }
        }
    }

    /**
     * 在进度条之后渲染进度减少的纹理效果。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    public void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (delayDecreaseTex != null && isDecrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, getX(), (getY() + height - (height * progress)), width, (height * (progress - targetProgress)), 0, height - (height * targetProgress), getBarTexWidth(), (getBarTexHeight() * (progress - targetProgress)), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, getX(), getY() + (height * targetProgress), width, (height * (progress - targetProgress)), 0, 0, getBarTexWidth(), (getBarTexHeight() * (progress - targetProgress)), getBarTexWidth(), getBarTexHeight());
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, (getX() + width - (width * progress)), getY(), (width * (progress - targetProgress)), height, width - (width * targetProgress), 0, (getBarTexWidth() * (progress - targetProgress)), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, (getX() + (width * targetProgress)), getY(), (width * (progress - targetProgress)), height, 0, 0, (getBarTexWidth() * (progress - targetProgress)), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
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