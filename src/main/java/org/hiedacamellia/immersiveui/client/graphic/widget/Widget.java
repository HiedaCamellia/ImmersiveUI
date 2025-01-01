package org.hiedacamellia.immersiveui.client.graphic.widget;

import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.layout.ILayoutElement;
import org.hiedacamellia.immersiveui.client.graphic.layout.Layout;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class Widget implements IWidget {

    @NotNull
    private Layout layout = new Layout();
    private int widgetWidth;
    private int widgetHeight;
    private int x;
    private int y;
    @Nullable
    private IWidget parent;

    @NotNull
    @Override
    public ILayoutElement layout() {
        return layout;
    }

    @Override
    public int width() {
        LayoutElements.Padding padding = layout.getPadding();
        LayoutElements.Border border = layout.getBorder();
        LayoutElements.Margin margin = layout.getMargin();
        int width = widgetWidth+padding.left()+padding.right()+border.left()+border.right()+margin.left()+margin.right();
        return Math.clamp(width,layout.getMinWidth(),layout.getMaxWidth());
    }

    @Override
    public int height() {
        LayoutElements.Padding padding = layout.getPadding();
        LayoutElements.Border border = layout.getBorder();
        LayoutElements.Margin margin = layout.getMargin();
        int height = widgetHeight+padding.top()+padding.bottom()+border.top()+border.bottom()+margin.top()+margin.bottom();
        return Math.clamp(height,layout.getMinHeight(),layout.getMaxHeight());
    }

    @Override
    public int x() {
        return switch (layout.getAlign()) {
            case LEFT -> x;
            case CENTER -> x - width() / 2;
            case RIGHT -> x - width();
            default -> x;
        };
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public void x(int x) {
        this.x = x;
    }

    @Override
    public void y(int y) {
        this.y = y;
    }

    @Override
    public void position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    @Nullable
    public IWidget parent() {
        return parent;
    }

    public void setParent(IWidget parent) {
        this.parent = parent;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
