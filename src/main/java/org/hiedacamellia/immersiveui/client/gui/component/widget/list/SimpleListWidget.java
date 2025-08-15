package org.hiedacamellia.immersiveui.client.gui.component.widget.list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 一个简单的列表控件，支持在每一行中放置多个自定义Widget。
 */
public class SimpleListWidget extends ContainerObjectSelectionList<SimpleListWidget.WidgetEntry> {

    /**
     * 构造一个SimpleListWidget。
     * @param client Minecraft客户端实例
     * @param width 列表宽度
     * @param height 列表高度
     * @param l 列表顶部Y坐标
     * @param m 列表底部Y坐标
     */
    public SimpleListWidget(Minecraft client, int width, int height, int l, int m) {
        super(client, width, height, l, m);
        this.centerListVertically = false;
    }

    /**
     * 获取每一行的宽度。
     * @return 行宽
     */
    public int getRowWidth() {
        return 400;
    }

    /**
     * 获取滚动条的X坐标。
     * @return 滚动条位置
     */
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 32;
    }

    /**
     * 表示列表中的一行，每行可包含多个Widget。
     */
    protected static class WidgetEntry extends Entry<WidgetEntry> {

        final List<AbstractWidget> widgets;

        /**
         * 构造一个WidgetEntry。
         * @param widgets 此行包含的所有Widget
         */
        private WidgetEntry(@NotNull List<AbstractWidget> widgets) {

            this.widgets = widgets;
        }

        /**
         * 渲染此行的所有Widget。
         * @param context 渲染上下文
         * @param index 行索引
         * @param y 行的Y坐标
         * @param x 行的X坐标
         * @param entryWidth 行宽
         * @param entryHeight 行高
         * @param mouseX 鼠标X
         * @param mouseY 鼠标Y
         * @param hovered 是否悬停
         * @param tickDelta 渲染帧间隔
         */
        @Override
        public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.widgets.forEach((widget) -> {
                widget.setY(y);
                widget.render(context, mouseX, mouseY, tickDelta);
            });
        }

        /**
         * 返回此行的所有事件监听器。
         */
        @Override
        public List<? extends GuiEventListener> children() {
            return this.widgets;
        }

        /**
         * 返回此行的所有可叙述元素。
         */
        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.widgets;
        }
    }
}
