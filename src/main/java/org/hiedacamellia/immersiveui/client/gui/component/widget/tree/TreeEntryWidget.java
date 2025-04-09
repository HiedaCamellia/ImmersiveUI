package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TreeEntryWidget<T> extends AbstractWidget {

    protected Font font;

    protected boolean fold = true;

    protected TreeEntryWidget<T> parent;

    protected List<TreeEntryWidget<T>> children = new ArrayList<>();

    protected T data;

    protected boolean isRoot = false;

    protected TreeWidget<T,TreeEntryWidget<T>> tree;

    protected int selfWidth,selfHeight,foldWidth;

    protected final Component foldComponent = Component.literal("▶");
    protected final Component unfoldComponent = Component.literal("▼");

    public void tree(TreeWidget<T,TreeEntryWidget<T>> tree) {
        isRoot = true;
        this.tree = tree;
    }

    public TreeWidget<T,TreeEntryWidget<T>> getTree(){
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
        if(widget == null) {
            return isHovered(mouseX, mouseY) ? this : null;
        }
        return widget.getAt(mouseX, mouseY);
    }

    public TreeEntryWidget<T> getWidgetAt(double mouseX,double mouseY){
        if(fold) return null;
        for (TreeEntryWidget<T> child : children) {
            if (child.isHovered(mouseX,mouseY)) {
                return child;
            }
        }
        return null;
    }

    public boolean shouldAccept(double mouseX, double mouseY){
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + foldWidth;
        int y1 = y0 + selfHeight;
        int x2 = x0 + foldWidth+selfWidth;
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1;
    }

    public void accept(TreeEntryWidget<T> widget){
        widget.parent.removeChild(widget);
        addChild(widget);
    }

    public TreeEntryWidget(Component message, Font font) {
        super(0, 0, 0, 0, message);
        this.font = font;
        this.selfHeight = font.lineHeight;
        this.selfWidth = font.width(message);
        this.foldWidth = font.width(foldComponent);
    }

    public static <T> TreeEntryWidget<T> of(T data,Component component, Font font){
        TreeEntryWidget<T> widget = new TreeEntryWidget<>(component, font);
        widget.setData(data);
        widget.width = widget.selfWidth;
        widget.height = widget.selfHeight;
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
        int X = this.getX() + foldWidth;
        int Y = this.getY() + selfHeight;
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
        int height = selfHeight;
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
                this.width = Math.max(width + foldWidth, foldWidth+selfWidth);
                return;
            }
            for (AbstractWidget child : children) {
                width = Math.max(width, child.getWidth());
            }
            this.width = Math.max(width + foldWidth, foldWidth+selfWidth);
        }else {
            this.width = selfWidth;
        }
    }

    public boolean shouldChangeFold(double mouseX, double mouseY, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            int x0 = this.getX();
            int y0 = this.getY();
            int x1 = x0 + foldWidth;
            int y1 = y0 + selfHeight;
            return mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1;
        }
        return false;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(hasChild()) {
            if (shouldChangeFold(mouseX, mouseY, button)) {
                //ImmersiveUI.LOGGER.info("fold："+fold);
                if (fold) {
                    unfold();
                } else {
                    fold();
                }
                return true;
            }
            if (isHovered(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                return false;
            }
            if(!fold) {
                TreeEntryWidget<T> child = getWidgetAt(mouseX, mouseY);
                if (child != null) {
                    boolean v = child.mouseClicked(mouseX, mouseY, button);
                    updateWidget();
                    return v;
                }
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
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    protected void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        for (TreeEntryWidget<T> child : children) {
            child.renderWidget(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
