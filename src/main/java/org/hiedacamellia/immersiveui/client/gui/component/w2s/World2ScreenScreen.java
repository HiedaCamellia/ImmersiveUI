package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.joml.Vector3f;

import java.util.UUID;

import static net.minecraft.client.Minecraft.ON_OSX;

public class World2ScreenScreen extends World2ScreenWidget{

    protected Minecraft minecraft = Minecraft.getInstance();
    protected Screen screen;
    protected Player player;
    protected int w;
    protected int h;
    private Vec3 pos;
    private final RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();

    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.init(minecraft, w, h);
    }

    public boolean isSameScreen(Screen screen){
        return this.screen == screen;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers){
        return this.screen.keyPressed(keyCode, scanCode, modifiers);
    }

    public World2ScreenScreen(UUID uuid, Screen screen, Player player) {
        this(uuid,screen, player, getPlayerEye(player));
    }

    private static Vec3 getPlayerEye(Player player){
        Vec3 eyePosition = player.getEyePosition();
        Vec3 lookAngle = player.getLookAngle();
        Vec3 normalizedLookAngle = lookAngle.normalize();
        return eyePosition.add(normalizedLookAngle);
    }

    public World2ScreenScreen(UUID uuid,Screen screen, Player player,Vec3 pos) {
        super(uuid);
        this.w = minecraft.getWindow().getGuiScaledWidth();
        this.h = minecraft.getWindow().getGuiScaledHeight();
        this.screen = screen;
        this.screen.init(minecraft, w, h);
        this.player = player;
        this.pos = pos;
        this.scale = 1;
    }

    @Override
    public boolean click(int button) {
        int mX = (int)(((float) w - x)/scale);
        int mY = (int)(((float) h - y)/scale);
        World2ScreenWidgetLayer.INSTANCE.activeScreen = this;
        World2ScreenWidgetLayer.INSTANCE.screenUUID = uuid;
        return screen.mouseClicked(mX, mY, button);
    }

    @Override
    public void getWorldPos(Vector3f out) {
        out.set(pos.toVector3f());
    }

    @Override
    public void render(GuiGraphics guiGraphics, boolean highlight, float value, float deltaTicks) {

        ScreenTempTarget.INSTANCE.setClearColor(0,0,0,0);
        ScreenTempTarget.INSTANCE.clear(ON_OSX);

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();


        float x1 = x - (float) w / 2;
        float y1 = y - (float) h / 2;
        float x2 = scale * w + x1;
        float y2 = scale * h + y1;
        int mX = (int)(((float) w - x)/scale);
        int mY = (int)(((float) h - y)/scale);

        mainRenderTarget.unbindWrite();
        ScreenTempTarget.INSTANCE.bindWrite(true);
        ScreenTempTarget.INSTANCE.use=true;
        RenderSystem.enableDepthTest();
        pose.pushPose();
        pose.translate(0 ,0, -10);
        RenderUtils.blit(pose, mainRenderTarget.getColorTextureId(), 0, 0, w, h);
        pose.popPose();


        RenderSystem.enableBlend();


        pose.translate(x- (float) w /2 ,y- (float) h /2, 100);
        pose.scale(scale, scale, 1);
        screen.render(guiGraphics, mX, mY, deltaTicks);
        guiGraphics.flush();


        ScreenTempTarget.INSTANCE.unbindWrite();
        ScreenTempTarget.INSTANCE.use=false;
        mainRenderTarget.bindWrite(true);


        RenderUtils.blitWithUv(pose, ScreenTempTarget.INSTANCE.getColorTextureId(), 0, 0, w, h, x1/w, y1/h, x2/w, y2/h);

        pose.popPose();
    }

    @Override
    public void calculateRenderScale(float distanceSqr) {
        this.scale = (float) (2 * Math.atan2(1.0, Math.sqrt(distanceSqr)));
        if(this.scale>1){
            this.scale = 1;
        }

        if(distanceSqr>64 && !shouldRemove){
            World2ScreenWidgetLayer.INSTANCE.remove(uuid);
        }
    }
}
