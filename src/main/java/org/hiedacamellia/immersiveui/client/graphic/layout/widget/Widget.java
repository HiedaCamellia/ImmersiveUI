package org.hiedacamellia.immersiveui.client.graphic.layout.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.layout.Layout;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ICacheable;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.IWidget;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;

@SuppressWarnings("unused")
public class Widget extends Layout implements IWidget, ICacheable {

    protected int widgetWidth;
    protected int widgetHeight;
    protected float x;
    protected float y;
    protected float widgetX;
    protected float widgetY;


    protected boolean cached;

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
    public void cache(float x, float y) {
        this.x = x;
        this.y = y;
        //log max/min width/height
        ImmersiveUI.LOGGER.info("maxWidth: "+getMaxWidth());
        ImmersiveUI.LOGGER.info("maxHeight: "+getMaxHeight());
        ImmersiveUI.LOGGER.info("minWidth: "+getMinWidth());
        ImmersiveUI.LOGGER.info("minHeight: "+getMinHeight());

        LayoutElements.Padding padding = this.getPadding();
        LayoutElements.Border border = this.getBorder();
        LayoutElements.Margin margin = this.getMargin();
        int widgetWidth = getMaxWidth()-padding.left()-padding.right()-border.left()-border.right()-margin.left()-margin.right();
        ImmersiveUI.LOGGER.info("widgetWidth: "+widgetWidth);
        this.widgetWidth = Math.clamp(widgetWidth,this.getMinWidth(),this.getMaxWidth());
        int widgetHeight = getMaxHeight()-padding.top()-padding.bottom()-border.top()-border.bottom()-margin.top()-margin.bottom();
        ImmersiveUI.LOGGER.info("widgetHeight: "+widgetHeight);
        this.widgetHeight = Math.clamp(widgetHeight,this.getMinHeight(),this.getMaxHeight());

        this.widgetX = (int) (x + padding.left() + border.left() + margin.left());
        this.widgetY = (int) (y + padding.top() + border.top() + margin.top());
        ImmersiveUI.LOGGER.info("widgetX: "+widgetX);
        ImmersiveUI.LOGGER.info("widgetY: "+widgetY);

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

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //RenderUtils.fill(guiGraphics, widgetX, widgetY, widgetWidth, widgetHeight, 0x10FFFFFF);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
