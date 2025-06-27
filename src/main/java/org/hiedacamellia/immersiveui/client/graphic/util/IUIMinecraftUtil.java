package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderBuffers;

public class IUIMinecraftUtil {

    public static Font getFont() {
        return Minecraft.getInstance().font;
    }
    public static RenderTarget getMainRenderTarget(){
        return Minecraft.getInstance().getMainRenderTarget();
    }
    public static RenderBuffers getRenderBuffers() {
        return Minecraft.getInstance().renderBuffers();
    }
}
