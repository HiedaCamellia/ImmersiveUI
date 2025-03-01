package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.gui.IUIGuiUtils;

import java.util.ArrayList;
import java.util.List;

public class TreeWidget<T> extends AbstractContainerWidget {

    protected Font font;

    protected TreeEntryWidget<T> root;

    protected TreeEntryWidget<T> onDrag;

    private double dragStartX, dragStartY;
    private double dragOriginX, dragOriginY;

    public boolean isDrag(TreeEntryWidget<T> widget){
        return onDrag == widget;
    }

    public void updateWidget() {
        int x = this.getX();
        int y = this.getY();
        root.setY(y + font.lineHeight);
        root.setX(x);
        root.updateWidget();
        this.height = root.getHeight()+font.lineHeight;
        this.width = root.getWidth();
    }

    public TreeWidget(TreeEntryWidget<T> root,int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        this.root = root;
        this.root.tree(this);
        updateWidget();
    }

    public static <T> TreeWidget<T> of(TreeEntryWidget<T> root,int x, int y, Component component, Font font){
        return new TreeWidget<>(root,x, y, component, font);
    }

    public TreeEntryWidget<T> getAt(double mouseX, double mouseY){
        return root.getAt(mouseX, mouseY);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(root);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TreeEntryWidget<T>  child = getAt(mouseX, mouseY);
        if(child != null && child!=root){
            ImmersiveUI.LOGGER.info(child.getMessage().getString());
            boolean v = child.mouseClicked(mouseX, mouseY, button);
            ImmersiveUI.LOGGER.info("Clicked: "+v);
            updateWidget();
            if(!v){
                this.setDragging(true);
                child.fold();
                this.onDrag = child;
                this.dragStartX = mouseX;
                this.dragStartY = mouseY;
                this.dragOriginX = child.getX();
                this.dragOriginY = child.getY();
            }
            return true;
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
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFDDDDDD);
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + font.lineHeight, 0xFFAAAAAA);
        guiGraphics.drawString(font, this.getMessage(), this.getX(), this.getY(), 0x000000,false);
        root.render(guiGraphics, mouseX, mouseY, v);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
