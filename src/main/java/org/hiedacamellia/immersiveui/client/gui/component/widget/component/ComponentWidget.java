package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

/**
 * ComponentWidget 是一个自定义的 GUI 组件，继承自 AbstractStringWidget。
 * 它用于显示文本组件，并提供了额外的功能，如阴影设置和多格式文本支持。
 */
public class ComponentWidget extends AbstractStringWidget {

    // 静态字体实例，用于渲染文本
    protected static final Font font = IUIMinecraftUtils.getFont();
    // 要显示的文本组件
    protected Component component;
    // 是否启用文本阴影
    protected boolean shadow = true;

    /**
     * 使用默认位置 (0, 0) 创建一个 ComponentWidget。
     *
     * @param component 要显示的文本组件
     */
    public ComponentWidget(Component component) {
        this(0, 0, component);
    }

    /**
     * 创建一个指定位置的 ComponentWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 要显示的文本组件
     */
    public ComponentWidget(int x, int y, Component component) {
        super(x, y, font.width(component), font.lineHeight, component, font);
        this.component = component;
    }

    /**
     * 创建一个指定位置和大小的 ComponentWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param w         组件的宽度
     * @param h         组件的高度
     * @param component 要显示的文本组件
     */
    public ComponentWidget(int x, int y, int w, int h, Component component) {
        super(x, y, w, h, component, font);
    }

    /**
     * 设置是否启用文本阴影。
     *
     * @param shadow 如果为 true，则启用阴影；否则禁用
     * @return 当前的 ComponentWidget 实例
     */
    public ComponentWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * 处理鼠标滚动事件。
     * 当前实现始终返回 false，表示不处理滚动事件。
     *
     * @param mouseX  鼠标的 X 坐标
     * @param mouseY  鼠标的 Y 坐标
     * @param scrollX 滚动的 X 方向值
     * @param scrollY 滚动的 Y 方向值
     * @return 始终返回 false
     */
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }

    /**
     * 渲染组件的内容。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(font, component, this.getX(), this.getY(), getColor(), shadow);
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

    /**
     * 将当前组件转换为一个支持多格式文本的组件。
     *
     * @param width 文本的最大宽度
     * @return 一个新的 MultiFormattedCharSequenceWidget 实例
     */
    public MultiFormattedCharSequenceWidget toMultiFormatted(int width) {
        return new MultiFormattedCharSequenceWidget(this.getX(), this.getY(), width, font.split(component, width)).setShadow(this.shadow);
    }

}