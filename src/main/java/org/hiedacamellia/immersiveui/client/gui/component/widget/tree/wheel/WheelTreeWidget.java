package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.ActionData;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.WheelActionRegistries;

import java.util.List;

public class WheelTreeWidget extends TreeWidget<ActionData,WheelTreeEntryWidget> {

    protected int centerX = 0;
    protected int centerY = 0;
    protected float angleOffset = 0.0f;

    public void setAngleOffset(float angleOffset) {
        this.angleOffset = angleOffset;
        updateWidget();
    }

    public void resize(){
        this.centerX = Minecraft.getInstance().getWindow().getGuiScaledWidth()/2;
        this.centerY = Minecraft.getInstance().getWindow().getGuiScaledHeight()/2;
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        for(WheelTreeEntryWidget child : root){
            child.resize();
        }
    }

    public WheelTreeWidget(List<? extends WheelTreeEntryWidget> root, int x, int y, Component component, Font font) {
        super(root, x, y, component, font);
        resize();
    }

    public static WheelTreeWidget create(List<? extends WheelTreeEntryWidget> root, int x, int y, Component component, Font font){
        return new WheelTreeWidget(root,x, y, component, font);
    }

    @Override
    public void updateWidget() {
        int i=0;int size = root.size();
        for(TreeEntryWidget<ActionData> child : root){
            if(child instanceof WheelTreeEntryWidget widget){
                widget.setAngle(i * 360.0f/size +angleOffset, (i + 1) * 360.0f/size+angleOffset);
                widget.setRadius(40, 75);
                widget.setLayer(0);
                widget.updateWidget();
            }
            i++;
        }
    }

    @Override
    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        IUIGuiUtils.drawCenteredString(guiGraphics,this.font,this.getMessage(),this.centerX,this.centerY,0xFFFFFFFF,false);
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean v = super.mouseClicked(mouseX, mouseY, button);
        if(select==null){
            return false;
        }
        if(select.getData()!=null)
            WheelActionRegistries.get(select.getData().resourceLocation()).accept(select.getData().compoundTag());
        return v;
    }

    @Override
    public TreeEntryWidget<ActionData> getAt(double mouseX, double mouseY) {
        for(TreeEntryWidget<ActionData> child : this.root) {
            TreeEntryWidget<ActionData> widget = child.getAt(mouseX, mouseY);
            if (widget != null && widget.isHovered(mouseX, mouseY)) {
                return widget;
            }
        }
        return null;
    }
}
