package org.hiedacamellia.immersiveui.client.gui.component.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;
import org.joml.Quaternionf;

public class ArrowWidget extends ClickableWidget{

    private final float[] vertex = {-60,-5, 0,5, 60,-5};
    private final Direction direction;

    public ArrowWidget(Direction direction) {
        super(120, 10);
        this.scale = 1.0f;
        this.direction = direction;
    }

    @Override
    public void click(boolean click) {

    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, float x, float y) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(getX()+x-centerX, getY()+y-centerY, 0);
        switch (direction){
            case UP:
                pose.mulPose(new Quaternionf().rotateZ((float) Math.PI));
                break;
        }
        pose.scale(this.renderScale,this.renderScale,1.0f);
        RenderSystem.enableBlend();
        RenderUtils.fillTriangle(guiGraphics, vertex, x, y, 0x80ffffff);
        RenderSystem.disableBlend();
        pose.popPose();
    }


    @Override
    public void hover(boolean hover) {

        if(hover){
            this.renderScale = Math.min(1.2f,this.renderScale+0.01f);
        }else {
            this.renderScale = Math.max(1.0f,this.renderScale-0.01f);
        }
    }
}
