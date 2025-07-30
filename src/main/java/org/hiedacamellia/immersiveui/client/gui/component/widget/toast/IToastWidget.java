package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import net.minecraft.client.gui.GuiGraphics;

/**
 * IToastWidget 是一个接口，定义了用于显示和管理自定义 Toast 组件的基本方法。
 */
public interface IToastWidget {

    /**
     * 设置 Toast 的超时时间。
     *
     * @param timeout 超时时间（以秒为单位）
     */
    void setTimeout(float timeout);

    /**
     * 渲染 Toast 组件。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    void renderToast(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}