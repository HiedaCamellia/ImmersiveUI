package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;

public class DebugTreeEntryWidget extends TreeEntryWidget<DebugEntry> {
    public DebugTreeEntryWidget(Component message, Font font) {
        super(message, font);
        this.foldWidth = 20;
        this.selfHeight = 20;
        this.selfWidth += 40;
    }

    public static DebugTreeEntryWidget create(DebugEntry data, Component component, Font font){
        DebugTreeEntryWidget widget = new DebugTreeEntryWidget(component, font);
        widget.setData(data);
        widget.updateWidget();
        return widget;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(hasChild()) {
            IUIGuiUtils.drawCenteredString(guiGraphics,font,fold?foldComponent:unfoldComponent,this.getX()+foldWidth/2,this.getY()+selfHeight/2,0xFFFFFF,false);
            guiGraphics.drawString(font, getMessage().getVisualOrderText(), (float) this.getX()+foldWidth, (float)this.getY()+(float)selfHeight/2-(float)font.lineHeight/2, 0xFFFFFF,false);
        }
        else
            guiGraphics.drawString(font, getMessage().getVisualOrderText(), (float) this.getX(), (float)this.getY()+(float)selfHeight/2-(float)font.lineHeight/2, 0xFFFFFF,false);

        if(fold) return;
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    @Override
    public boolean shouldAccept(double mouseX, double mouseY){
        return false;
    }
}
