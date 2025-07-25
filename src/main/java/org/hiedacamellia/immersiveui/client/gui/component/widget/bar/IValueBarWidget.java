package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;

import java.text.DecimalFormat;

public interface IValueBarWidget extends IBarWidget {

    float getMaxValue();
    float getValue();

    default void setValue(float value){
        if (Mth.abs(value - getValue()) < 0.1f) return; // Avoid unnecessary updates
        setProgress(value/getMaxValue());
    }
    default void setMaxValue(float maxValue){}

    default boolean shouldRenderText(){
        return true;
    }

    default Font getFont(){
        return IUIMinecraftUtil.getFont();
    }

    default String format(float value){
        return new DecimalFormat("##").format(value);
    }

    default String getText() {
        return format(getValue()) + "/" + format(getMaxValue());
    }

    default void renderText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        if(!shouldRenderText()) return;
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        float centerX = getX() + (float) getWidth() / 2;
        float centerY = getY() + (float) getHeight() / 2;
        if(getHeight()<getFont().lineHeight){
            float s = (float)getHeight()/ getFont().lineHeight;
            pose.translate(centerX, centerY, 0);
            pose.scale(s, s, 1.0F);
        }

        IUIGuiUtils.drawCenteredString(guiGraphics,getFont(), getText(), 0, 0, 0xFFFFFFFF,false);
        pose.popPose();
    }
}
