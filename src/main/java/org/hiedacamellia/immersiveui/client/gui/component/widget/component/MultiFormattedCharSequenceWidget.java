package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

/**
 * MultiFormattedCharSequenceWidget 是一个自定义的 GUI 组件，继承自 AbstractStringWidget。
 * 它支持多格式文本的显示，并提供了阴影设置功能。
 */
public class MultiFormattedCharSequenceWidget extends AbstractStringWidget {

    // 静态字体实例，用于渲染文本
    private static final Font font = Minecraft.getInstance().font;
    // 要显示的多格式文本数组
    protected FormattedCharSequence[] component;
    // 是否启用文本阴影
    private boolean shadow = true;

    /**
     * 从一个 Component 创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param component 要显示的文本组件
     * @param width     文本的最大宽度
     * @return 一个新的 MultiFormattedCharSequenceWidget 实例
     */
    public static MultiFormattedCharSequenceWidget from(Component component, int width) {
        return new ComponentWidget(component).toMultiFormatted(width);
    }

    /**
     * 使用多格式文本数组创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param component 要显示的多格式文本数组
     */
    public MultiFormattedCharSequenceWidget(FormattedCharSequence... component) {
        this(0, 0, component);
    }

    /**
     * 使用指定位置和多格式文本列表创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param width     组件的宽度
     * @param component 要显示的多格式文本列表
     */
    public MultiFormattedCharSequenceWidget(int x, int y, int width, List<FormattedCharSequence> component) {
        this(x, y, width, component.toArray(new FormattedCharSequence[0]));
    }

    /**
     * 使用指定位置和多格式文本列表创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 要显示的多格式文本列表
     */
    public MultiFormattedCharSequenceWidget(int x, int y, List<FormattedCharSequence> component) {
        this(x, y, component.toArray(new FormattedCharSequence[0]));
    }

    /**
     * 使用指定位置和多格式文本数组创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 要显示的多格式文本数组
     */
    public MultiFormattedCharSequenceWidget(int x, int y, FormattedCharSequence... component) {
        this(x, y, componentWidth(component), component);
    }

    /**
     * 使用指定位置、宽度和多格式文本数组创建一个 MultiFormattedCharSequenceWidget。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param width     组件的宽度
     * @param component 要显示的多格式文本数组
     */
    public MultiFormattedCharSequenceWidget(int x, int y, int width, FormattedCharSequence... component) {
        super(x, y, width, font.lineHeight * component.length, Component.empty(), font);
        this.component = component;
    }

    /**
     * 设置是否启用文本阴影。
     *
     * @param shadow 如果为 true，则启用阴影；否则禁用
     * @return 当前的 MultiFormattedCharSequenceWidget 实例
     */
    public MultiFormattedCharSequenceWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * 计算多格式文本数组的最大宽度。
     *
     * @param component 多格式文本数组
     * @return 最大宽度
     */
    private static int componentWidth(FormattedCharSequence[] component) {
        int width = 0;
        for (FormattedCharSequence c : component) {
            if (font.width(c) > width) {
                width = font.width(c);
            }
        }
        return width;
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
        int y = this.getY();
        for (FormattedCharSequence c : component) {
            guiGraphics.drawString(font, c, this.getX(), y, getColor(), shadow);
            y += font.lineHeight;
        }
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
}