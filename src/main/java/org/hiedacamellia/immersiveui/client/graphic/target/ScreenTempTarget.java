package org.hiedacamellia.immersiveui.client.graphic.target;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.immersiveui.ImmersiveUI;

import static net.minecraft.client.Minecraft.ON_OSX;

public class ScreenTempTarget extends RenderTarget {

    protected int width, height;
    private static PostChain blurEffect;

    public boolean use;

    private static final ResourceLocation BLUR_LOCATION = ResourceLocation.withDefaultNamespace("shaders/post/blur.json");

    public static ScreenTempTarget SCREEN_INSTANCE;
    public static ScreenTempTarget BLUR_INSTANCE;

    public ScreenTempTarget(int width, int height) {
        super(true);
        this.createBuffers(width, height,ON_OSX);
        this.width = width;
        this.height = height;
        this.use=false;
        try {
            Minecraft minecraft = Minecraft.getInstance();
            blurEffect = new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), this, BLUR_LOCATION);
            blurEffect.resize(this.width, this.height);
        }
        catch (Exception e){
            ImmersiveUI.LOGGER.error("Failed to create blur effect", e);
        }
    }

    public static PostChain getBlurEffect() {
        return blurEffect;
    }
}
