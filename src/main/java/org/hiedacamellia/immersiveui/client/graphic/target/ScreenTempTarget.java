package org.hiedacamellia.immersiveui.client.graphic.target;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.joml.Matrix4f;

import static net.minecraft.client.Minecraft.ON_OSX;

public class ScreenTempTarget extends RenderTarget {

    protected int width, height;
    private PoseStack pose;
    private float u0, v0, u1, v1;
    private float x0, y0, x1, y1;
    private PostChain blurEffect;
    private Minecraft minecraft = Minecraft.getInstance();

    public boolean use;

    private static final ResourceLocation BLUR_LOCATION = ResourceLocation.withDefaultNamespace("shaders/post/blur.json");

    public static ScreenTempTarget INSTANCE;

    public ScreenTempTarget(int width, int height) {
        super(true);
        this.createBuffers(width, height,ON_OSX);
        this.width = width;
        this.height = height;
        this.use=false;
        try {
            this.blurEffect = new PostChain(this.minecraft.getTextureManager(), minecraft.getResourceManager(), this, BLUR_LOCATION);
            this.blurEffect.resize(this.width, this.height);
        }
        catch (Exception e){
            ImmersiveUI.LOGGER.error("Failed to create blur effect", e);
        }
    }

    public void setTextureUV(float u0, float v0, float u1, float v1){
        this.u0 = u0;
        this.v0 = v0;
        this.u1 = u1;
        this.v1 = v1;
    }

    public void size(float x0, float y0, float x1, float y1){
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public void pose(PoseStack pose){
        this.pose = pose;
    }

    public void blitToScreen(){

        RenderSystem.setShaderTexture(0, colorTextureId);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = pose.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x0*width, y0*height, 0).setUv(u0, v0);
        bufferbuilder.addVertex(matrix4f, x0*width, y1*height, 0).setUv(u0, v1);
        bufferbuilder.addVertex(matrix4f, x1*width, y1*height, 0).setUv(u1, v1);
        bufferbuilder.addVertex(matrix4f, x1*width, y0*height, 0).setUv(u1, v0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public PostChain getBlurEffect() {
        return blurEffect;
    }
}
