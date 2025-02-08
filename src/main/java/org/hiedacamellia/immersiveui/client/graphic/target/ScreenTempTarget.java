package org.hiedacamellia.immersiveui.client.graphic.target;

import com.mojang.blaze3d.pipeline.RenderTarget;

import static net.minecraft.client.Minecraft.ON_OSX;

public class ScreenTempTarget extends RenderTarget {

    protected int width, height;

    public static ScreenTempTarget INSTANCE;

    public ScreenTempTarget(int width, int height) {
        super(true);
        this.createBuffers(width, height,ON_OSX);
        this.width = width;
        this.height = height;
    }

    public void blitToScreen(){
        this.blitToScreen(width,height,false);
    }
}
