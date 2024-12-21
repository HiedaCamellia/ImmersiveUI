package org.hiedacamellia.immersiveui.client.gui.component.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.util.RenderUtils;

public class ShopItemWidget extends ClickableWidget{

    private ItemStack itemStack;
    private boolean highlight;

    public ShopItemWidget() {
        super(40, 40);
        this.itemStack = ItemStack.EMPTY;
        this.scale = 1.0f;
    }

    public ShopItemWidget(ItemStack itemStack) {
        super(40, 40);
        this.itemStack = itemStack;
        this.scale = 1.0f;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    @Override
    public void click(boolean click) {
        highlight = click;
    }

    @Override
    public void render(GuiGraphics guiGraphics, float x, float y) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(getX()+x-centerX, getY()+y-centerY, 0);
        pose.scale(this.renderScale,this.renderScale,1.0f);
        RenderSystem.enableBlend();
        if(alpha>0f)
            if(highlight){
                RenderUtils.borderCenteredRoundRect(
                        guiGraphics,
                        width+2, height+2,
                        0.1f, 0x80ffffff,
                        0.05f, 0xffffffff
                );
            }else {
                RenderUtils.fillCenteredRoundRect(
                        guiGraphics,
                        width, height,
                        0.1f, 0x80ffffff
                );
            }
        RenderSystem.disableBlend();
        pose.scale(2.0f,2.0f,1.0f);
        if(alpha>0.2f)
            guiGraphics.renderItem(itemStack,-8,-8);
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
