package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMathUtils;

public abstract class AbstractToastWidget extends AbstractWidget implements IToastWidget{

    protected float timeout;
    protected float count=0;

    protected boolean fadeIn;

    public AbstractToastWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.fadeIn = true;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if (count > timeout) return;
        if(getMessage().getString().isEmpty())return;

        count+=partialTick;
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0,0,1000.0);

        float alpha = 1.0f;
        if(fadeIn) {
            if (count < timeout * 0.5f) {
                alpha = IUIMathUtils.smoothStep(0, timeout * 0.1f, count);
            } else {
                alpha = 1 - IUIMathUtils.smoothStep(timeout * 0.9f, timeout, count);
            }
        }
        pose.scale(alpha,alpha,0);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,alpha);

        renderToast(guiGraphics, mouseX,mouseY,partialTick);

        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.disableBlend();
        pose.popPose();
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public void setTimeout(float timeout) {
        this.timeout = timeout;
    }
}
