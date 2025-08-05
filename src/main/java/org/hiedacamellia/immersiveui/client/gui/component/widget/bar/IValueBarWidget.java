package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

import java.text.DecimalFormat;

/**
 * IValueBarWidget 接口扩展了 IBarWidget，表示一个带有数值显示的进度条控件。
 * 提供了获取和设置数值、格式化文本以及渲染文本的功能。
 */
public interface IValueBarWidget extends IBarWidget {

    /**
     * 获取进度条的最大值。
     *
     * @return 最大值
     */
    float getMaxValue();

    /**
     * 获取当前的数值。
     *
     * @return 当前数值
     */
    float getValue();

    /**
     * 设置当前的数值，并更新进度条的进度。
     * 如果新值与当前值的差异小于 0.1，则不会更新。
     *
     * @param value 要设置的数值
     */
    default void setValue(float value) {
        if (Mth.abs(value - getValue()) < 0.1f) return; // 避免不必要的更新
        setProgress(value / getMaxValue());
    }

    /**
     * 设置进度条的最大值。
     * 默认实现为空。
     *
     * @param maxValue 要设置的最大值
     */
    default void setMaxValue(float maxValue) {}

    /**
     * 判断是否需要渲染文本。
     *
     * @return 如果需要渲染文本，则返回 true；否则返回 false
     */
    default boolean shouldRenderText() {
        return true;
    }

    /**
     * 获取用于渲染文本的字体。
     *
     * @return 字体对象
     */
    default Font getFont() {
        return IUIMinecraftUtils.getFont();
    }

    /**
     * 格式化数值为字符串。
     *
     * @param value 要格式化的数值
     * @return 格式化后的字符串
     */
    default String format(float value) {
        return new DecimalFormat("##").format(value);
    }

    /**
     * 获取显示在进度条上的文本。
     * 文本格式为 "当前值/最大值"。
     *
     * @return 显示的文本
     */
    default String getText() {
        return format(getValue()) + "/" + format(getMaxValue());
    }

    /**
     * 渲染进度条上的文本。
     * 文本会居中显示，并根据进度条高度自动调整缩放。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    default void renderText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!shouldRenderText()) return;
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        float centerX = getX() + (float) getWidth() / 2;
        float centerY = getY() + (float) getHeight() / 2;
        if (getHeight() < getFont().lineHeight) {
            float s = (float) getHeight() / getFont().lineHeight;
            pose.translate(centerX, centerY, 0);
            pose.scale(s, s, 1.0F);
        }

        IUIGuiUtils.drawCenteredString(guiGraphics, getFont(), getText(), 0, 0, 0xFFFFFFFF, false);
        pose.popPose();
    }
}