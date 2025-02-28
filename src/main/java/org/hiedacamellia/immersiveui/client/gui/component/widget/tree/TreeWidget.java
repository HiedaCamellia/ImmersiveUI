package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.gui.IUIGuiUtils;

import java.util.ArrayList;
import java.util.List;

public class TreeWidget extends AbstractContainerWidget {

    protected Font font;

    protected List<AbstractWidget> children = new ArrayList<>();

    protected boolean fold = true;

    protected AbstractWidget onDrag;

    private double dragStartX, dragStartY;
    private double dragOriginX, dragOriginY;

    private TreeWidget parent;

    public void fold() {
        fold = true;
        updateWidget();
    }

    public void unfold() {
        fold = false;
        updateWidget();
    }

    public int getChildrenCount() {
        return children.size();
    }

    public void addChild(AbstractWidget child) {
        if(child==this)return;
        if(child instanceof TreeWidget treeWidget){
            treeWidget.parent = this;
        }
        children.add(child);
        updateWidget();
    }

    public void moveChildren(int indexFrom, int indexTo) {
        AbstractWidget child = children.get(indexFrom);
        children.remove(indexFrom);
        children.add(indexTo, child);
    }

    public void removeChild(int index) {
        children.remove(index);
        updateWidget();
    }

    private void updateWidget() {
        int X = this.getX() + font.width("▶ ");
        int Y = this.getY() + font.lineHeight;
        for (AbstractWidget child : children) {
            child.setX(X);
            child.setY(Y);
            if(child instanceof TreeWidget && child!=this){
                ((TreeWidget) child).updateWidget();
            }
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
        if (fold){
            this.width = Math.max(width+font.width("▶ "), font.width("▶ " + getMessage().getString()));
            return;
        }
        for (AbstractWidget child : children) {
            width = Math.max(width, child.getWidth());
        }
        this.width = Math.max(width+font.width("▶ "), font.width("▶ " + getMessage().getString()));
    }

    public TreeWidget(int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        updateWidget();
    }

    public AbstractWidget getWidgetAt(double mouseX,double mouseY){
        int y = this.getY() + font.lineHeight;
        for (AbstractWidget child : children) {
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

    public AbstractWidget getAt(double mouseX, double mouseY){
        AbstractWidget widget = getWidgetAt(mouseX, mouseY);
        if(widget instanceof TreeWidget){
            return ((TreeWidget) widget).getAt(mouseX, mouseY);
        }
        return widget;
    }

    public boolean shouldAccept(double mouseX, double mouseY){
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + font.width("▶ ");
        int y1 = y0 + font.lineHeight;
        int x2 = x0 + font.width("▶ " + getMessage().getString());
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
                return false;
            }
        }
        if (mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1) {
            if (button == 0) {
                return true;
            }
        }
        AbstractWidget child = getWidgetAt(mouseX, mouseY);
        if(child != null){
            boolean v = child.mouseClicked(mouseX, mouseY, button);
            updateWidget();
            if(v){
                this.setDragging(true);
                this.onDrag = child;

                if(onDrag instanceof TreeWidget) {
                    if(!((TreeWidget) onDrag).isDragging()) {
                        ((TreeWidget) onDrag).fold();
                        updateWidget();
                    }
                }
                this.dragStartX = mouseX;
                this.dragStartY = mouseY;
                this.dragOriginX = child.getX();
                this.dragOriginY = child.getY();
            }
            return v;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean injectAt(double mouseX, double mouseY, AbstractWidget child){
        AbstractWidget target = getWidgetAt(mouseX, mouseY);
        if(target != null){
            if(target instanceof TreeWidget treeWidget){
                if(treeWidget == this) return false;

                if(treeWidget.shouldAccept(mouseX, mouseY)){
                    ImmersiveUI.LOGGER.info("shouldAccept 2");
                    if(children.contains(treeWidget)) {

                        ImmersiveUI.LOGGER.info("injectAt 2");
                        return treeWidget.injectAt(mouseX, mouseY, child);
                    }

                    if(treeWidget == onDrag){
                        onDrag = null;
                        return true;
                    }
                    treeWidget.addChild(onDrag);
                    children.remove(onDrag);
                    onDrag = null;
                    updateWidget();
                    return true;
                }
                return treeWidget.injectAt(mouseX, mouseY, child);
            }
            if(!children.contains(child)){
                int index = children.indexOf(target);
                children.add(index, child);
                updateWidget();
                return true;
            }
        }else {
            ImmersiveUI.LOGGER.info("injectAt 3");
            if(shouldAccept(mouseX, mouseY)){
                addChild(child);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        if(onDrag != null){
            if(onDrag instanceof TreeWidget treeWidget){
                if(treeWidget.isDragging()){
                    return treeWidget.mouseReleased(mouseX, mouseY, button);
                }
            }
            AbstractWidget child = getAt(mouseX, mouseY);
            if(child != null){
                if(!children.contains(child)){
                    if(getWidgetAt(mouseX,mouseY) instanceof TreeWidget treeWidget){
                        if(treeWidget.isDragging()){
                            return treeWidget.mouseReleased(mouseX, mouseY, button);
                        }
                        boolean v = treeWidget.injectAt(mouseX, mouseY, onDrag);
                        if(v){
                            children.remove(onDrag);
                            onDrag = null;
                            updateWidget();
                        }
                        return v;
                    }

                }else {
                    if(child instanceof TreeWidget treeWidget){
                        if(treeWidget.isDragging()){
                            return treeWidget.mouseReleased(mouseX, mouseY, button);
                        }
                    }

                    int indexFrom = children.indexOf(onDrag);
                    int indexTo = children.indexOf(child);
                    if (indexFrom != indexTo) {
                        moveChildren(indexFrom, indexTo);
                    }
                    updateWidget();
                }
            }else {

                if(parent!=null){
                    ImmersiveUI.LOGGER.info("injectAt");
                    children.remove(onDrag);
                    boolean v = parent.injectAt(mouseX, mouseY, onDrag);
                    if(!v){
                        children.add(onDrag);
                    }
                    onDrag = null;
                    parent.updateWidget();
                    return v;
                }
                if(getWidgetAt(mouseX,mouseY) instanceof TreeWidget treeWidget){
                    ImmersiveUI.LOGGER.info("shouldAccept");
                    if(treeWidget.shouldAccept(mouseX, mouseY)){
                        if(treeWidget == onDrag){
                            onDrag = null;
                            updateWidget();
                            return true;
                        }
                        treeWidget.addChild(onDrag);
                        children.remove(onDrag);
                        onDrag = null;
                        updateWidget();
                        return true;
                    }
                }
            }
            onDrag = null;
            updateWidget();
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(isDragging()){
            if(onDrag != null){
                if(onDrag instanceof TreeWidget) {
                    if(((TreeWidget) onDrag).isDragging())
                        return onDrag.mouseDragged(mouseX, mouseY, button, dragX, dragY);
                }

                int xOff = (int) (mouseX - dragStartX);
                int yOff = (int) (mouseY - dragStartY);
                onDrag.setX((int) (dragOriginX + xOff));
                onDrag.setY((int) (dragOriginY + yOff));
            }
            return true;
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFDDDDDD);

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        guiGraphics.drawString(font, fold ? "▶ "+getMessage().getString() : "▼ "+getMessage().getString(), this.getX(), this.getY(), 0xFFFFFF);
        if(fold) return;

        for (AbstractWidget child : children) {
            child.render(guiGraphics, i, i1, v);
        }
        boolean shouldRender = true;

        if(isDragging()){
            if(onDrag instanceof TreeWidget) {
                if (((TreeWidget) onDrag).isDragging()){
                    shouldRender = false;
                }
            }

            if(shouldRender) {
                AbstractWidget child = getWidgetAt(i, i1);
                if (child != null) {
                    if(child!=onDrag)
                        guiGraphics.fill(child.getX() - 2, child.getY() - 1, this.getX() + this.width, child.getY() + 2, 0x55FF0000);
                }
            }
        }

        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public List<AbstractWidget> children() {
        return children;
    }
}
