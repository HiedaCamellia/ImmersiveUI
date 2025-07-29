package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.phys.HitResult;

public class IUIMinecraftUtil {

    public static Font getFont() {
        return Minecraft.getInstance().font;
    }
    public static HitResult getHit() {
        return Minecraft.getInstance().hitResult;
    }
    public static RenderTarget getMainRenderTarget(){
        return Minecraft.getInstance().getMainRenderTarget();
    }
    public static RenderBuffers getRenderBuffers() {
        return Minecraft.getInstance().renderBuffers();
    }
    public static Window getWindow() {
        return Minecraft.getInstance().getWindow();
    }
    public static double getGuiScale() {
        return getWindow().getGuiScale();
    }
    public static int getGuiScaledWidth() {
        return getWindow().getGuiScaledWidth();
    }
    public static int getGuiScaledHeight() {
        return getWindow().getGuiScaledHeight();
    }

    public static int screenWidth() {
        return getWindow().getGuiScaledWidth();
    }

    public static int screenHeight() {
        return getWindow().getGuiScaledHeight();
    }

    public static int screenCenterX() {
        return screenWidth() / 2;
    }

    public static int screenCenterY() {
        return screenHeight() / 2;
    }
}
