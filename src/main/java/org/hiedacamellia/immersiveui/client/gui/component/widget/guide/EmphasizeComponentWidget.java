package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

public class EmphasizeComponentWidget extends EmphasizeWidget {

    public EmphasizeComponentWidget(int x, int y,Component message) {
        super(x, y, IUIMinecraftUtils.getFont().width(message)+2, IUIMinecraftUtils.getFont().lineHeight+2, message);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        IUIGuiUtils.drawString(guiGraphics,getMessage(),getX()+1,getY()+1,0xFFFFFFFF,false);
    }
}
