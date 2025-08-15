package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

/**
 * 支持多行文本显示和滚动的组件控件。
 */
public class AreaComponentWidget extends ComponentWidget {

    private double y_offset = 0;
    private int textHeight;

    /**
     * 构造一个指定宽高和内容的AreaComponentWidget。
     * @param width 组件宽度
     * @param height 组件高度
     * @param component 显示的文本内容
     */
    public AreaComponentWidget(int width, int height, Component component) {
        this(0, 0, width, height, component);
    }

    /**
     * 构造一个指定位置、宽高和内容的AreaComponentWidget。
     * @param x X坐标
     * @param y Y坐标
     * @param width 组件宽度
     * @param height 组件高度
     * @param component 显示的文本内容
     */
    public AreaComponentWidget(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
        this.textHeight = font.lineHeight * font.split(component, width).size();
    }

    /**
     * 渲染多行文本内容，支持垂直滚动。
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int yOff = (int) y_offset;
        for (FormattedCharSequence charSequence : font.split(component, width)) {
            if (yOff < 0) continue;
            if (yOff + font.lineHeight > height) return;
            guiGraphics.drawString(font, charSequence, this.getX(), this.getY() + yOff, getColor(), shadow);
            yOff += font.lineHeight;
        }
    }

    /**
     * 处理鼠标滚轮事件，实现文本滚动。
     * @return 是否消费了滚轮事件
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (isHovered) {
            if (y_offset + scrollY < 0) {
                y_offset = 0;
            } else if (y_offset + scrollY + textHeight > height) {
                y_offset = height - textHeight;
            } else {
                y_offset += scrollY;
            }
            return true;
        }

        return false;
    }
}
