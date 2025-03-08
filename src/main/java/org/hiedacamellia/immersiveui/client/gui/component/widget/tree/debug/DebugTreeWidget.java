package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.graphic.gui.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;

import java.util.List;

public class DebugTreeWidget extends TreeWidget<DebugEntry, DebugTreeEntryWidget> {
    public DebugTreeWidget(List<DebugTreeEntryWidget> root, int x, int y, Component component, Font font) {
        super(root, x, y, component, font);
        this.dragable = false;
        this.titleHeight=20;
        this.titleWidth+=40;
        updateWidget();
    }

    public static DebugTreeWidget create(List<DebugTreeEntryWidget> root, int x, int y, Component component, Font font){
        return new DebugTreeWidget(root,x, y, component, font);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean v = super.mouseClicked(mouseX, mouseY, button);
        if(select!=null&&!select.hasChild()){
            DebugEntry data = select.getData();
            Minecraft.getInstance().setScreen(data.subScreen());
            return true;
        }
        return v;
    }

    @Override
    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v){
        RenderSystem.enableBlend();
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + titleHeight, 0x80AAAAAA);
        IUIGuiUtils.drawCenteredString(guiGraphics,font, this.getMessage(), this.getX()+this.width/2, this.getY()+titleHeight/2, 0x000000,false);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v){
        RenderSystem.enableBlend();
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0x80DDDDDD);
        RenderSystem.disableBlend();
    }
}
