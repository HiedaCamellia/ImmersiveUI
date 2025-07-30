package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

/**
 * UnderLineComponentWidget 是一个自定义的 GUI 组件，继承自 ComponentWidget。
 * 它在文本组件下方绘制一条带有颜色和高度的下划线。
 */
public class UnderLineComponentWidget extends ComponentWidget {

    // 下划线的颜色，默认为棕色 (0xFF523629)
    private int lineColor = 0xFF523629;
    // 下划线的高度，默认为 4 像素
    private int lineHeight = 4;

    /**
     * 使用默认位置 (0, 0) 创建一个 UnderLineComponentWidget。
     *
     * @param component 要显示的文本组件
     */
    public UnderLineComponentWidget(Component component) {
        this(0, 0, component);
    }

    /**
     * 创建一个指定位置的 UnderLineComponentWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 要显示的文本组件
     */
    public UnderLineComponentWidget(int x, int y, Component component) {
        super(x, y, component);
    }

    /**
     * 设置下划线的颜色。
     *
     * @param lineColor 下划线的颜色 (ARGB 格式)
     * @return 当前的 UnderLineComponentWidget 实例
     */
    public UnderLineComponentWidget setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    /**
     * 设置下划线的高度。
     *
     * @param lineHeight 下划线的高度 (像素)
     * @return 当前的 UnderLineComponentWidget 实例
     */
    public UnderLineComponentWidget setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    /**
     * 渲染组件的内容，包括下划线和文本。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 启用混合模式以支持透明度
        RenderSystem.enableBlend();
        // 绘制带圆角的下划线
        IUIGuiUtils.fillRoundRect(guiGraphics, this.getX(), this.getY() + this.height - lineHeight, this.width, lineHeight, 0.02f, lineColor);
        // 禁用混合模式
        RenderSystem.disableBlend();
        // 渲染父类的文本内容
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }
}