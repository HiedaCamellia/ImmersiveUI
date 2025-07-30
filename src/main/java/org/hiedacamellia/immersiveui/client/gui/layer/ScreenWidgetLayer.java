package org.hiedacamellia.immersiveui.client.gui.layer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGraphicUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;
import org.hiedacamellia.immersiveui.util.MouseCaptureUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.Minecraft.ON_OSX;

@OnlyIn(Dist.CLIENT)
public class ScreenWidgetLayer implements LayeredDraw.Layer{


    public static final ScreenWidgetLayer INSTANCE = new ScreenWidgetLayer();
    public static final ResourceLocation LOCATION = ImmersiveUI.rl("screen_layer");

    @Nullable
    protected Screen screen;

    public @Nullable Screen getScreen() {
        return screen;
    }

    public void setScreen(@NotNull Screen screen) {
        this.screen = screen;
        this.screen.init(Minecraft.getInstance(), IUIMinecraftUtil.screenWidth(), IUIMinecraftUtil.screenHeight());
        mouseX = IUIMinecraftUtil.screenCenterX();
        mouseY = IUIMinecraftUtil.screenCenterY();
        MouseCaptureUtil.startMouseCapture();
    }

    public void removeScreen() {
        this.screen = null;
        MouseCaptureUtil.stopMouseCapture();
    }

    public boolean hasScreen() {
        return screen != null;
    }

    protected boolean enableOffset = true;
    protected float offsetFactor = 8.0f;

    public void setEnableOffset(boolean enableOffset) {
        this.enableOffset = enableOffset;
    }
    public void setOffsetFactor(float offsetFactor) {
        this.offsetFactor = offsetFactor;
    }

    protected double mouseX;
    protected double mouseY;

    public void addPos(double mouseX, double mouseY) {
        this.mouseX = Mth.clamp(this.mouseX+mouseX,0, IUIMinecraftUtil.getGuiScaledWidth());
        this.mouseY = Mth.clamp(this.mouseY+mouseY,0, IUIMinecraftUtil.getGuiScaledHeight());
    }

    public boolean click(int button,int action) {
        if (screen != null) {
            if( action == GLFW.GLFW_PRESS) {
                return screen.mouseClicked(mouseX, mouseY, button);
            } else if (action == GLFW.GLFW_RELEASE) {
                return screen.mouseReleased(mouseX, mouseY, button);
            }
        }
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (screen != null) {
            return screen.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().player == null)
            return;
        if (Minecraft.getInstance().gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        if (Minecraft.getInstance().screen != null) {
            removeScreen();
            MouseCaptureUtil.stopMouseCapture();
        }

        if(screen != null){

            int w = IUIMinecraftUtil.screenWidth();
            int h = IUIMinecraftUtil.screenHeight();

            double andResetCapturedDeltaX = MouseCaptureUtil.getAndResetCapturedDeltaX();
            double andResetCapturedDeltaY = MouseCaptureUtil.getAndResetCapturedDeltaY();
            addPos(andResetCapturedDeltaX, andResetCapturedDeltaY);

            double offsetX = enableOffset ? -andResetCapturedDeltaX / offsetFactor : 0;
            double offsetY = enableOffset ? -andResetCapturedDeltaY / offsetFactor : 0;

            PoseStack pose = guiGraphics.pose();
            pose.pushPose();


            ScreenTempTarget.SCREEN_INSTANCE.setClearColor(0, 0, 0, 0);
            ScreenTempTarget.SCREEN_INSTANCE.clear(ON_OSX);
            ScreenTempTarget.BLUR_INSTANCE.setClearColor(0, 0, 0, 0);
            ScreenTempTarget.BLUR_INSTANCE.clear(ON_OSX);

            RenderTarget mainRenderTarget = IUIMinecraftUtil.getMainRenderTarget();

            mainRenderTarget.unbindWrite();
            ScreenTempTarget.BLUR_INSTANCE.bindWrite(true);
            ScreenTempTarget.BLUR_INSTANCE.use = true;
            ScreenTempTarget.SCREEN_INSTANCE.use = true;


            //将屏幕渲染到临时纹理
            IUIGraphicUtils.blit(pose, mainRenderTarget.getColorTextureId(), 0, 0, w,h, 0, 1, 1, 0);

            //渲染屏幕
            screen.render(guiGraphics, (int) mouseX, (int) mouseY, deltaTracker.getGameTimeDeltaTicks());
            guiGraphics.flush();
            pose.popPose();

            ScreenTempTarget.SCREEN_INSTANCE.unbindWrite();
            ScreenTempTarget.BLUR_INSTANCE.use = false;
            ScreenTempTarget.SCREEN_INSTANCE.use = false;
            mainRenderTarget.bindWrite(true);

            //将blur纹理渲染到屏幕
            pose.pushPose();
            RenderSystem.enableBlend();
            IUIGraphicUtils.blit(pose, ScreenTempTarget.BLUR_INSTANCE.getColorTextureId(), (float)0,  (float)0, (float) (w), (float) (h), 0, 1, 1, 0);
            pose.popPose();

            //渲染屏幕组件
            pose.pushPose();
            IUIGraphicUtils.blit(pose, ScreenTempTarget.SCREEN_INSTANCE.getColorTextureId(), (float)offsetX,  (float)offsetY, (float) (offsetX + w), (float) (offsetY + h), 0, 1, 1, 0);
            pose.popPose();

            if(drawVirtualMouse)
                IUIGuiUtils.drawRing(guiGraphics, (float) mouseX, (float) mouseY, virtualMouseInnerRadius, virtualMouseOuterRadius, 0, 360,0xFFFFFFFF, 0.1f);

        }
    }

    protected boolean drawVirtualMouse = true;

    public void setDrawVirtualMouse(boolean drawVirtualMouse) {
        this.drawVirtualMouse = drawVirtualMouse;
    }

    protected float virtualMouseInnerRadius = 3.0f;
    protected float virtualMouseOuterRadius = 7.0f;

    public void setVirtualMouseRadius(float innerRadius, float outerRadius) {
        this.virtualMouseInnerRadius = innerRadius;
        this.virtualMouseOuterRadius = outerRadius;
    }
}
