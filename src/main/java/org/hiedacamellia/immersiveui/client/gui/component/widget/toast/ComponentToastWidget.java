package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

public class ComponentToastWidget extends AbstractToastWidget {

    public ComponentToastWidget(int x, int y, int width, int height, float time, Component message) {
        super(x-width/2, y-height/2, width, height, message);
        this.setTimeout(time);
    }

    public void reset(Component message){
        if(count>timeout)
            count = 0;
        this.setMessage(message);
    }

    @Override
    public void renderToast(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        PoseStack pose = guiGraphics.pose();
        pose.translate(getX()+ (double) getWidth() /2,getY()+ (double) getHeight() /2,0);
        pose.scale(getAlpha(),getAlpha(),0);

        IUIGuiUtils.fillRoundRectCentered(guiGraphics,width,height,0.05f,0x80000000);
        IUIGuiUtils.drawCenteredString(guiGraphics, Minecraft.getInstance().font, getMessage(),0,0,0xFFFFFFFF,false);

    }
}
