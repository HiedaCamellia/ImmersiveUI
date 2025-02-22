/*
 * Code from https://github.com/LouisQuepierts/ThatSkyInteractions
 * net.quepierts.thatskyinteractions.client.util
 * RenderUtils.java
 *
 * This code is under the MIT License.
 */

package org.hiedacamellia.immersiveui.client.graphic.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.shader.IUIShaders;
import org.joml.Matrix4f;
import org.joml.Vector4f;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class IUIGuiUtils {

    //Triangle
    public static void fillTriangle(GuiGraphics guiGraphics, float[] vertex, int color) {
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.addVertex(matrix4f, vertex[0], vertex[1], 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, vertex[2], vertex[3], 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, vertex[4], vertex[5], 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void fillSquareCentered(GuiGraphics guiGraphics, float x, float y, float sideLength, int color) {
        fillCentered(guiGraphics, x, y, sideLength, sideLength, color);
    }

    public static void fillSquare(GuiGraphics guiGraphics, float x, float y, float sideLength, int color) {
        fill(guiGraphics, x, y, sideLength, sideLength, color);
    }

    public static void fillCentered(GuiGraphics guiGraphics, float x, float y, float width, float height, int color) {
        fill(guiGraphics, x - width / 2, y - height / 2, width, height, color);
    }

    public static void fill(GuiGraphics guiGraphics, float x, float y, float width, float height, int color) {
        _fill(guiGraphics.pose(), x, y, width, height, color);
    }

    public static void _fill(PoseStack poseStack, float x, float y, float width, float height, int color) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.addVertex(matrix4f, x, y, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x, y + height, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x + width, y + height, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x + width, y, 0).setColor(color);
    }

    public static void fillRoundRectCentered(GuiGraphics guiGraphics, int width, int height, float radius, int color) {
        fillRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color);
    }

    public static void fillRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int color) {
        fillRoundRect(guiGraphics.pose(), x, y, width, height, width > height ? (float) radius / width : (float) radius / height, color);
    }

    public static void fillRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, float radius, int color) {
        fillRoundRect(guiGraphics.pose(), x, y, width, height, radius, color);
    }

    public static void fillRoundRect(PoseStack poseStack, int x, int y, int width, int height, float radius, int color) {
        int x2 = x + width;
        int y2 = y + height;

        final float ratio = (float) height / (float) width;

        RenderSystem.setShader(IUIShaders::getRoundRectShader);
        ShaderInstance shader = IUIShaders.getRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float) x, (float) y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x, (float) y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void borderRoundRectCentered(GuiGraphics guiGraphics, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color, borderThickness, borderColor);
    }

    public static void borderRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics.pose(), x, y, width, height, radius, color, borderThickness, borderColor);
    }

    public static void borderRoundRect(PoseStack poseStack, int x, int y, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        int x2 = x + width;
        int y2 = y + height;

        final float ratio = (float) height / (float) width;

        RenderSystem.setShader(IUIShaders::getBorderRoundRectShader);
        ShaderInstance shader = IUIShaders.getBorderRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);
        shader.safeGetUniform("BorderThickness").set(borderThickness);
        Vector4f border = new Vector4f(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), FastColor.ARGB32.alpha(color));
        shader.safeGetUniform("BorderColor").set(border);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float) x, (float) y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x, (float) y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }


    public static void blit(GuiGraphics guiGraphics, ResourceLocation location, int x, int y, int width, int height) {
        blit(guiGraphics.pose(), location, x, y, width, height);
    }

    public static void blit(PoseStack poseStack, ResourceLocation location, int x, int y, int width, int height) {
        blit(poseStack, location, (float) x, (float) y, (float) (x + width), (float) (y + height));
    }

    public static void blit(GuiGraphics guiGraphics, int textureId, int x, int y, int width, int height) {
        blit(guiGraphics.pose(), textureId, x, y, width, height);
    }

    public static void blit(PoseStack poseStack, int textureId, int x, int y, int width, int height) {
        blit(poseStack, textureId, (float) x, (float) y, (float) (x + width), (float) (y + height));
    }

    public static void blit(PoseStack poseStack, ResourceLocation location, float x1, float y1, float x2, float y2) {
        blit(poseStack, location, x1, y1, x2, y2, 0, 0, 1, 1);
    }

    public static void blit(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2) {
        blit(poseStack, textureId, x1, y1, x2, y2, 0, 0, 1, 1);
    }

    public static void blit(PoseStack poseStack, ResourceLocation location, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, location);
        _blit(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void blit(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blit(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void _blit(PoseStack poseStack, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blitInUv(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        RenderSystem.setShader(IUIShaders::getPositionTexShader);
        ShaderInstance shaderInstance = IUIShaders.getPositionTexShader();
        shaderInstance.safeGetUniform("uvCoords").set(u0, v0, u1, v1);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(1, 0);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(1, 1);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blur(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float radius) {
        blur(poseStack, textureId, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }

    public static void blur(PoseStack poseStack, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius) {
        blur(poseStack, atlasLocation, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }

    public static void blur(GuiGraphics guiGraphics, int textureId, float x1, float y1, float x2, float y2, float radius) {
        blur(guiGraphics.pose(), textureId, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }

    public static void blur(GuiGraphics guiGraphics, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius) {
        blur(guiGraphics.pose(), atlasLocation, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }

    public static void blur(PoseStack poseStack, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        _blur(poseStack, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }

    public static void blur(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blur(poseStack, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }

    private static void _blur(PoseStack poseStack, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(IUIShaders::getBlurShader);
        ShaderInstance shaderInstance = IUIShaders.getBlurShader();
        shaderInstance.safeGetUniform("Radius").set(radius);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }
}
