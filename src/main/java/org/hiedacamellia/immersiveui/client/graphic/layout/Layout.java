package org.hiedacamellia.immersiveui.client.graphic.layout;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutColor;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ILayoutElement;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public abstract class Layout extends AbstractWidget implements ILayoutElement {

    private LayoutElements.Margin margin;
    private LayoutElements.Padding padding;
    private LayoutElements.Border border;
    private LayoutColor color;
    private LayoutColor backgroundColor;
    private LayoutColor borderColor;
    private Vector3f translate;
    private int minWidth;
    private int minHeight;
    private int maxWidth;
    private int maxHeight;
    private LayoutElements.Display display;
    private LayoutElements.Align align;
    private LayoutElements.Cursor cursor;

    public Layout(){
        super(0,0,0,0, Component.empty());
        this.minHeight = 0;
        this.minWidth = 0;
        this.maxHeight = Integer.MAX_VALUE;
        this.maxWidth = Integer.MAX_VALUE;
        this.margin = new LayoutElements.Margin();
        this.padding = new LayoutElements.Padding();
        this.border = new LayoutElements.Border();
        this.color = new LayoutColor();
        this.backgroundColor = new LayoutColor();
        this.borderColor = new LayoutColor();
        this.translate = new Vector3f();
        this.display = LayoutElements.Display.BLOCK;
        this.align = LayoutElements.Align.LEFT;
        this.cursor = LayoutElements.Cursor.AUTO;
    }

    public void setMargin(LayoutElements.Margin margin) {
        this.margin = margin;
    }

    public void setPadding(LayoutElements.Padding padding) {
        this.padding = padding;
    }

    public void setBorder(LayoutElements.Border border) {
        this.border = border;
    }

    public void setColor(LayoutColor color) {
        this.color = color;
    }

    public void setBackgroundColor(LayoutColor color) {
        this.backgroundColor = color;
    }

    public void setBorderColor(LayoutColor color) {
        this.borderColor = color;
    }

    public void setTranslate(Vector3f translate) {
        this.translate = translate;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setDisplay(LayoutElements.Display display) {
        this.display = display;
    }

    public void setAlign(LayoutElements.Align align) {
        this.align = align;
    }

    public void setCursor(LayoutElements.Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public LayoutElements.Margin getMargin() {
        return margin;
    }

    @Override
    public LayoutElements.Padding getPadding() {
        return padding;
    }

    @Override
    public LayoutElements.Border getBorder() {
        return border;
    }

    @Override
    public LayoutColor getColor() {
        return color;
    }

    @Override
    public LayoutColor getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public LayoutColor getBorderColor() {
        return borderColor;
    }

    @Override
    public Vector3f getTranslate() {
        return translate;
    }

    @Override
    public int getMinWidth() {
        return minWidth;
    }

    @Override
    public int getMinHeight() {
        return minHeight;
    }

    @Override
    public int getMaxWidth() {
        return maxWidth;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public LayoutElements.Display getDisplay() {
        return display;
    }

    @Override
    public LayoutElements.Align getAlign() {
        return align;
    }

    @Override
    public LayoutElements.Cursor getCursor() {
        return cursor;
    }


}
