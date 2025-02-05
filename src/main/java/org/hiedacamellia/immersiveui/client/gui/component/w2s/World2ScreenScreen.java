package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.joml.Vector3f;

public class World2ScreenScreen extends World2ScreenWidget{

    protected Screen screen;
    protected Player player;
    protected int w;
    protected int h;
    private BlockPos pos;

    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.init(Minecraft.getInstance(), w, h);
    }

    public World2ScreenScreen(Screen screen, Player player) {
        this.screen = screen;
        this.player = player;
        this.w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        this.pos = player.getOnPos();
        this.screen.init(Minecraft.getInstance(), w, h);
        this.scale = 1;
    }

    @Override
    public boolean click(int button) {
        int mX = (int)(((float) w /2-x)/scale);
        int mY = (int)(((float) h /2-y)/scale);
        World2ScreenWidgetLayer.INSTANCE.screen = this;
        return screen.mouseClicked(mX, mY, button);
    }

    @Override
    public void getWorldPos(Vector3f out) {
        out.set(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void render(GuiGraphics guiGraphics, boolean highlight, float value, float deltaTicks) {
        RenderSystem.enableBlend();
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        guiGraphics.fill(0,0,0,0,0xFFFFFFFF);
        pose.translate(x ,y, 0);
        pose.scale(scale, scale, 1);
        int mX = (int)(((float) w /2-x)/scale);
        int mY = (int)(((float) h /2-y)/scale);
        screen.render(guiGraphics, mX, mY, deltaTicks);
        //guiGraphics.fill(mX,mY,mX+5,mY+5,0xFFFFFFFF);
        pose.popPose();
    }

    @Override
    public void calculateRenderScale(float distanceSqr) {
        if(distanceSqr>16)
            this.scale = 16/distanceSqr;
        else
            this.scale = 1;
    }
}
