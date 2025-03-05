package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.ImmersiveUI;

import java.util.List;

public class TreeWidget<T,V extends TreeEntryWidget<T>> extends AbstractContainerWidget {

    protected Font font;

    protected List<V> root;

    protected TreeEntryWidget<T> onDrag;
    protected TreeEntryWidget<T> select;

    private double dragStartX, dragStartY;
    private double dragOriginX, dragOriginY;

    private boolean showTitle = true;

    protected boolean dragable = true;
    protected int titleWidth,titleHeight;

    public TreeEntryWidget<T> getSelect() {
        return select;
    }

    public boolean isDrag(V widget){
        return onDrag == widget;
    }

    public void hideTitle(){
        showTitle = false;
    }

    public void updateWidget() {
        int x = this.getX();
        int y = this.getY();
        int offset = showTitle?titleHeight:0;
        int width = 0;
        for (V treeEntryWidget : root) {
            treeEntryWidget.setX(x);
            treeEntryWidget.setY(y + offset);
            treeEntryWidget.updateWidget();
            offset += treeEntryWidget.getHeight();
            width = Math.max(width, treeEntryWidget.getWidth());
        }
        this.height = offset;
        this.width = showTitle?Math.max(width,titleWidth):width;
    }

    public TreeWidget(V root,int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        this.root = List.of(root);
        root.tree((TreeWidget<T, TreeEntryWidget<T>>) this);
        this.titleHeight = font.lineHeight;
        this.titleWidth = font.width(component);
        updateWidget();
    }

    public TreeWidget(List<V> root,int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        this.root = root;
        for (V treeEntryWidget : root) {
            treeEntryWidget.tree((TreeWidget<T, TreeEntryWidget<T>>) this);
        }
        this.titleHeight = font.lineHeight;
        this.titleWidth = font.width(component);
        updateWidget();
    }

    public static <T,V extends TreeEntryWidget<T>> TreeWidget<T,V> of(V root,int x, int y, Component component, Font font){
        return new TreeWidget<>(root,x, y, component, font);
    }

    public static <T,V extends TreeEntryWidget<T>> TreeWidget<T,V> of(List<V> root,int x, int y, Component component, Font font){
        return new TreeWidget<>(root,x, y, component, font);
    }

    public TreeEntryWidget<T> getAt(double mouseX, double mouseY){
        TreeEntryWidget<T> widget;
        for(TreeEntryWidget<T> child : root){
            widget = child.getAt(mouseX, mouseY);
            if(widget != null) return widget;
        }
        return null;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return root;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TreeEntryWidget<T>  child = getAt(mouseX, mouseY);
        select = child;
        if(child != null && !child.isRoot()){
            ImmersiveUI.LOGGER.info(child.getMessage().getString());
            boolean v = child.mouseClicked(mouseX, mouseY, button);
            ImmersiveUI.LOGGER.info("Clicked: "+v);
            updateWidget();
            if(!v&&dragable){
                this.setDragging(true);
                child.fold();
                this.onDrag = child;
                this.dragStartX = mouseX;
                this.dragStartY = mouseY;
                this.dragOriginX = child.getX();
                this.dragOriginY = child.getY();
            }
            return true;
        }else {
            updateWidget();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        if(onDrag != null){
            TreeEntryWidget<T> child = getAt(mouseX, mouseY);
            ImmersiveUI.LOGGER.info(child.getMessage().getString());
            if(onDrag!=child){
                if (child.shouldAccept(mouseX, mouseY)) {
                    child.accept(onDrag);
                }else {
                    child.insert(onDrag);
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
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        renderBg(guiGraphics, mouseX, mouseY, v);
        if(showTitle) {
            renderTitle(guiGraphics, mouseX, mouseY, v);
        }
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v){
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFDDDDDD);
    }

    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v){
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + titleHeight, 0xFFAAAAAA);
        guiGraphics.drawString(font, this.getMessage(), this.getX(), this.getY(), 0x000000, false);
    }

    protected void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float v){
        for (V treeEntryWidget : root) {
            treeEntryWidget.render(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
