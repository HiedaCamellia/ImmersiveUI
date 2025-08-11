package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMathUtils;

/**
 * AbstractToastWidget 是一个抽象类，扩展自 AbstractWidget，并实现了 IToastWidget 接口。
 * 它提供了基本的 Toast 组件功能，包括超时处理、淡入淡出效果以及自定义渲染逻辑。
 */
public abstract class AbstractToastWidget extends AbstractWidget implements IToastWidget {

    protected boolean fadeIn; // 是否启用淡入淡出效果

    protected long startTime = 0; // 动画开始时间
    protected long duration = 500; // 动画持续时间

    @Override
    public long getAnimationStartTime() {
        return this.startTime;
    }

    @Override
    public void setAnimationStartTime(long time) {
        this.startTime = time;
    }

    @Override
    public long getAnimationDuration() {
        return this.duration;
    }

    @Override
    public void setAnimationDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 构造一个 AbstractToastWidget 实例。
     *
     * @param x       Toast 的 X 坐标
     * @param y       Toast 的 Y 坐标
     * @param width   Toast 的宽度
     * @param height  Toast 的高度
     * @param message Toast 显示的消息
     */
    public AbstractToastWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.fadeIn = true;
    }

    /**
     * 获取当前 Toast 的透明度。
     * 根据计数器和超时时间计算淡入淡出的透明度值。
     *
     * @return 当前透明度值（范围为 0.0 到 1.0）
     */
    protected float getAlpha() {
        float alpha = 1.0f;
        if (fadeIn) {
            alpha = IUIMathUtils.smoothPulse(getElapsedTime(), duration, 0.1f, 0.1f);
        }
        return alpha;
    }

    /**
     * 渲染 Toast 组件。
     * 包括透明度处理、消息检查以及调用具体的渲染逻辑。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isAnimationEnd()) return;
        if (getMessage().getString().isEmpty()) return;


        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 1000.0);

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, getAlpha());

        renderToast(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        pose.popPose();
    }

    /**
     * 更新组件的旁白信息。
     * 当前实现未定义任何旁白逻辑。
     *
     * @param narrationElementOutput 旁白输出对象
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        // 未实现旁白更新逻辑
    }

}