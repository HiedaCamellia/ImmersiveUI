package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

/**
 * ComponentToastWidget 是一个自定义的 Toast 组件，用于显示带有消息的弹出式提示框。
 * 它支持设置显示时间、重置消息以及自定义渲染效果。
 */
public class ComponentToastWidget extends AbstractToastWidget {

    /**
     * 构造一个 ComponentToastWidget 实例。
     *
     * @param x       Toast 的中心 X 坐标
     * @param y       Toast 的中心 Y 坐标
     * @param width   Toast 的宽度
     * @param height  Toast 的高度
     * @param time    Toast 的显示时间（以秒为单位）
     * @param message Toast 显示的消息
     */
    public ComponentToastWidget(int x, int y, int width, int height, long time, Component message) {
        super(x - width / 2, y - height / 2, width, height, message);
        this.setTimeout(time);
    }

    /**
     * 等待或更新 Toast 的消息。
     * 如果当前时间超过了设置的超时时间，则重置时间。
     *
     * @param message 新的消息内容
     */
    public void waitOrUpdate(Component message) {
        waitOrUpdate();
        this.setMessage(message);
    }

    /**
     * 渲染 Toast 组件。
     * 包括背景的圆角矩形和居中的消息文本。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    @Override
    public void renderToast(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        PoseStack pose = guiGraphics.pose();
        pose.translate(getX() + (double) getWidth() / 2, getY() + (double) getHeight() / 2, 0);
        pose.scale(getAlpha(), getAlpha(), 0);

        IUIGuiUtils.fillRoundRectCentered(guiGraphics, width, height, 0.05f, 0x80000000);
        IUIGuiUtils.drawCenteredString(guiGraphics, Minecraft.getInstance().font, getMessage(), 0, 0, 0xFFFFFFFF, false);

    }

}