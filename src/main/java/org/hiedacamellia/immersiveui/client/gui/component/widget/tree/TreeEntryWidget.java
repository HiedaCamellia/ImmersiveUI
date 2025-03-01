package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TreeEntryWidget<T> extends AbstractWidget {

    protected Font font;

    protected boolean fold = true;

    private TreeEntryWidget<T> parent;

    private List<TreeEntryWidget<T>> children = new ArrayList<>();

    private T data;

    private boolean isRoot = false;

    private TreeWidget<T> tree;

    protected final Component foldComponent = Component.literal("▶ ");
    protected final Component unfoldComponent = Component.literal("▼ ");

    public void tree(TreeWidget<T> tree) {
        isRoot = true;
        this.tree = tree;
    }

    public TreeWidget<T> getTree(){
        if(isRoot) return tree;
        return parent.getTree();
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void insert(TreeEntryWidget<T> widget){
        if(widget.isRoot()) return;
        if(parent==null)return;
        if(parent==widget.parent){
            parent.moveChild(widget,parent.children.indexOf(this));
            return;
        }
        int i = parent.children.indexOf(this);
        if(i==-1) return;
        widget.parent.removeChild(widget);
        parent.addChild(i,widget);
    }

    public TreeEntryWidget<T> getAt(double mouseX, double mouseY){
        TreeEntryWidget<T> widget = getWidgetAt(mouseX, mouseY);
        if(widget == null) return this;
        return widget.getAt(mouseX, mouseY);
    }

    public TreeEntryWidget<T> getWidgetAt(double mouseX,double mouseY){
        int y = this.getY() + font.lineHeight;
        for (TreeEntryWidget<T> child : children) {
            int x0 = this.getX();
            int y0 = y;
            int x1 = x0 + this.width;
            int y1 = y0 + child.getHeight();
            if (mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1) {
                return child;
            }
            y += child.getHeight();
        }
        return null;
    }

    public boolean shouldAccept(double mouseX, double mouseY){
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + font.width("▶ ");
        int y1 = y0 + font.lineHeight;
        int x2 = x0 + font.width("▶ " + getMessage().getString());
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1;
    }

    public void accept(TreeEntryWidget<T> widget){
        widget.parent.removeChild(widget);
        addChild(widget);
    }

    public TreeEntryWidget(Component message, Font font) {
        super(0, 0, 0, 0, message);
        this.font = font;
    }

    public static <T> TreeEntryWidget<T> of(T data,Component component, Font font){
        TreeEntryWidget<T> widget = new TreeEntryWidget<>(component, font);
        widget.setData(data);
        widget.width = font.width(component);
        widget.height = font.lineHeight;
        return widget;
    }

    public void addChild(int index,TreeEntryWidget<T> child) {
        children.add(index,child);
        child.parent = this;
        updateWidget();
    }

    public void addChild(TreeEntryWidget<T> child) {
        children.add(child);
        child.parent = this;
        updateWidget();
    }

    public void removeChild(TreeEntryWidget<T> child) {
        children.remove(child);
        child.parent = null;
        updateWidget();
    }

    public void moveChild(TreeEntryWidget<T> child,int index){
        children.remove(child);
        children.add(index,child);
        updateWidget();
    }

    public boolean hasChild(){
        return !children.isEmpty();
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void fold() {
        fold = true;
        getTree().updateWidget();
    }

    public void unfold() {
        fold = false;
        getTree().updateWidget();
    }

    public void updateWidget() {
        int X = this.getX() + font.width(foldComponent);
        int Y = this.getY() + font.lineHeight;
        for (TreeEntryWidget<T> child : children) {
            child.setX(X);
            child.setY(Y);
            child.updateWidget();
            Y += child.getHeight();
        }
        updateHeight();
        updateWidth();
    }

    private void updateHeight() {
        int height = font.lineHeight;
        if (fold) {
            this.height = height;
            return;
        }
        for (AbstractWidget child : children) {
            height += child.getHeight();
        }
        this.height = height;
    }

    private void updateWidth() {
        int width = 0;
        if(hasChild()) {
            if (fold) {
                this.width = Math.max(width + font.width(foldComponent), font.width(foldComponent.copy().append(getMessage()).getString()));
                return;
            }
            for (AbstractWidget child : children) {
                width = Math.max(width, child.getWidth());
            }
            this.width = Math.max(width + font.width(foldComponent), font.width(foldComponent.copy().append(getMessage()).getString()));
        }else {
            this.width = font.width(getMessage());
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(hasChild()) {
            int x0 = this.getX();
            int y0 = this.getY();
            int x1 = x0 + font.width("▶ ");
            int y1 = y0 + font.lineHeight;
            int x2 = x0 + font.width("▶ " + getMessage().getString());
            if (mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1) {
                if (button == 0) {
                    if (fold) {
                        unfold();
                    } else {
                        fold();
                    }
                    return true;
                }
            }
            if (mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    return false;
                }
            }
            TreeEntryWidget<T> child = getWidgetAt(mouseX, mouseY);
            if (child != null) {
                boolean v = child.mouseClicked(mouseX, mouseY, button);
                updateWidget();
                return v;
            }
        }else {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                return false;
            }
        }
        return true;
    }

    public boolean isHovered(double mouseX,double mouseY) {
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + this.width;
        int y1 = y0 + this.height;
        return mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1;
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(hasChild())
            guiGraphics.drawString(font, fold ? foldComponent.copy().append(getMessage()) : unfoldComponent.copy().append(getMessage()), this.getX(), this.getY(), 0xFFFFFF);
        else
            guiGraphics.drawString(font, getMessage(), this.getX(), this.getY(), 0xFFFFFF);

        if(!getTree().isDrag(this)) {
            if (isHovered(mouseX, mouseY) && getTree().isDragging() && getWidgetAt(mouseX, mouseY) == null && !shouldAccept(mouseX, mouseY)) {
                guiGraphics.fill(this.getX(), this.getY() - 1, getTree().getX() + getTree().getWidth(), this.getY() + 2, 0x80FF0000);
            }
            if (shouldAccept(mouseX, mouseY) && getTree().isDragging() && getWidgetAt(mouseX, mouseY) == null) {
                guiGraphics.fill(this.getX()+4, this.getY() + 2, getTree().getX() + getTree().getWidth(), this.getY() + 7, 0x800000FF);
            }
        }
        if(fold) return;
        for (TreeEntryWidget<T> child : children) {
            child.renderWidget(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
