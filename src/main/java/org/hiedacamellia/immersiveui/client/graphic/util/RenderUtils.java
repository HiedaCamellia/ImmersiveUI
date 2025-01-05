/*
 * Code from https://github.com/LouisQuepierts/ThatSkyInteractions
 * net.quepierts.thatskyinteractions.client.util
 * RenderUtils.java
 *
 * This code is under the MIT License.
 */

package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.shader.Shaders;
import org.joml.Matrix4f;
import org.joml.Vector4f;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    //Triangle
    public static void fillTriangle(GuiGraphics guiGraphics, float[] vertex, float ratio, float radius, int color) {


        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, vertex[0], vertex[1], 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, vertex[2], vertex[3], 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, vertex[4], vertex[5], 0).setUv(1, 1).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void fill(GuiGraphics guiGraphics,float x,float y ,float width,float height,int color){
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, x, y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x, y+height, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, x+width, y+height, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, x+width, y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void fillCenteredRoundRect(GuiGraphics guiGraphics, int width, int height, float radius, int color) {
        fillRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color);
    }

    public static void fillRoundRect(PoseStack poseStack, int x, int y, int width, int height, float radius, int color) {
        int x2 = x + width;
        int y2 = y + height;

        final float ratio = (float) height / (float) width;

        RenderSystem.setShader(Shaders::getRoundRectShader);
        ShaderInstance shader = Shaders.getRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void fillRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, float radius, int color) {
        fillRoundRect(guiGraphics.pose(), x, y, width, height, radius, color);
    }

    public static void borderCenteredRoundRect(GuiGraphics guiGraphics, int width, int height, float radius, int color ,float borderThickness,int borderColor) {
        borderRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, float radius, int color ,float borderThickness,int borderColor) {
        borderRoundRect(guiGraphics.pose(), x, y, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRect(PoseStack poseStack, int x, int y, int width, int height, float radius, int color ,float borderThickness,int borderColor) {
        int x2 = x + width;
        int y2 = y + height;

        final float ratio = (float) height / (float) width;

        RenderSystem.setShader(Shaders::getBorderRoundRectShader);
        ShaderInstance shader = Shaders.getBorderRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);
        shader.safeGetUniform("BorderThickness").set(borderThickness);
        Vector4f border = new Vector4f(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), FastColor.ARGB32.alpha(color));
        shader.safeGetUniform("BorderColor").set(border);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blit(PoseStack poseStack, ResourceLocation location, int x, int y, int width, int height) {
        int x2 = x + width;
        int y2 = y + height;

        Matrix4f matrix4f = poseStack.last().pose();
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y, 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y2, 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, 0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y, 0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blit(GuiGraphics guiGraphics, ResourceLocation atlasLocation, int x, int y, int width, int height) {
        int x2 = x + width;
        int y2 = y + height;

        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y, 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, (float)x, (float)y2, 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, 0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y, 0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }
}
