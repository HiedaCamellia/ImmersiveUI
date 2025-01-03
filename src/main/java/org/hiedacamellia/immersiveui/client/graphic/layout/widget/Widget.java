package org.hiedacamellia.immersiveui.client.graphic.layout.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.layout.Layout;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ICacheable;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.IWidget;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;

@SuppressWarnings("unused")
public class Widget extends Layout implements IWidget, ICacheable {

    private int widgetWidth;
    private int widgetHeight;
    private float x;
    private float y;
    private float widgetX;
    private float widgetY;


    private boolean cached;

    @Override
    public int width() {
        return Math.clamp(widgetWidth,this.getMinWidth(),this.getMaxWidth());
    }

    @Override
    public int height() {
        return Math.clamp(widgetHeight,this.getMinHeight(),this.getMaxHeight());
    }

    @Override
    public int x() {
        return (int) x;
    }

    @Override
    public int y() {
        return (int)y;
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
    public void render(GuiGraphics guiGraphics, float deltaTicks) {
        RenderUtils.fill(guiGraphics, widgetX, widgetY, widgetX + widgetWidth, widgetY + widgetHeight, this.getBackgroundColor().toRGBInt());
    }

    @Override
    public void cache(float x, float y) {
        this.x = x;
        this.y = y;

        LayoutElements.Padding padding = this.getPadding();
        LayoutElements.Border border = this.getBorder();
        LayoutElements.Margin margin = this.getMargin();
        int widgetWidth = getMaxWidth()-padding.left()-padding.right()-border.left()-border.right()-margin.left()-margin.right();
        this.widgetWidth = Math.clamp(widgetWidth,this.getMinWidth(),this.getMaxWidth());
        int widgetHeight = getMaxHeight()-padding.top()-padding.bottom()-border.top()-border.bottom()-margin.top()-margin.bottom();
        this.widgetWidth = Math.clamp(widgetWidth,this.getMinHeight(),this.getMaxHeight());

        this.widgetX = (int) (x + padding.left() + border.left() + margin.left());
        this.widgetY = (int) (y + padding.top() + border.top() + margin.top());

        cached = true;
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    public boolean isCached() {
        return cached;
    }
}
