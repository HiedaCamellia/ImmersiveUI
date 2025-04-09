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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.shader.IUIShaders;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import javax.annotation.Nullable;

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
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        VertexConsumer bufferbuilder = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        bufferbuilder.addVertex(matrix4f, x, y, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x, y + height, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x + width, y + height, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x + width, y, 0).setColor(color);
    }

    public static void fillRoundRectCentered(GuiGraphics guiGraphics,int x, int y, int width, int height, float radius, int color) {
        fillRoundRect(guiGraphics, x-width / 2, y-height / 2, width, height, radius, color);
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
        _fillRoundRect(poseStack,  x,  y,  width,  height, radius, color);
    }

    public static void _fillRoundRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color) {
        float x2 = x + width;
        float y2 = y + height;

        final float ratio =  height /  width;

        RenderSystem.setShader(IUIShaders::getRoundRectShader);
        ShaderInstance shader = IUIShaders.getRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f,  x,  y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x,  y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radius, int color) {
        _fillBorderRect(guiGraphics.pose(), x, y, width, height, radius, radius, color);
    }
    public static void fillBorderRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color) {
        _fillBorderRect(poseStack, x, y, width, height, radius, radius, color);
    }
    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radiusX,float radiusY, int color) {
        _fillBorderRect(guiGraphics.pose(), x, y, width, height, radiusX, radiusY, color);
    }
    public static void _fillBorderRect(PoseStack poseStack, float x, float y, float width, float height, float radiusX,float radiusY, int color) {
        float x1 = x - width * radiusX;
        float y1 = y - height * radiusY;
        float x2 = x + width + width * radiusX;
        float y2 = y + height + height * radiusY;


        RenderSystem.setShader(IUIShaders::getBorderRectShader);
        ShaderInstance shader = IUIShaders.getBorderRectShader();
        shader.safeGetUniform("Radius").set(radiusX,radiusY);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f,  x1,  y1, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x1,  y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y1, 0).setUv(1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void borderRoundRectCentered(GuiGraphics guiGraphics, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color, borderThickness, borderColor);
    }

    public static void borderRoundRect(GuiGraphics guiGraphics, int x, int y, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics.pose(), x, y, width, height, radius, color, borderThickness, borderColor);
    }

    public static void borderRoundRect(PoseStack poseStack, int x, int y, int width, int height, float radius, int color, float borderThickness, int borderColor) {
        _borderRoundRect(poseStack, x, y, width, height, radius, color, borderThickness, borderColor);
    }
    public static void _borderRoundRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color, float borderThickness, int borderColor) {
        float x2 = x + width;
        float y2 = y + height;

        final float ratio =  height /  width;

        RenderSystem.setShader(IUIShaders::getBorderRoundRectShader);
        ShaderInstance shader = IUIShaders.getBorderRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);
        shader.safeGetUniform("BorderThickness").set(borderThickness);
        Vector4f border = new Vector4f(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), FastColor.ARGB32.alpha(color));
        shader.safeGetUniform("BorderColor").set(border);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f,  x,  y, 0).setUv(0, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x,  y2, 0).setUv(0, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y2, 0).setUv(1, 1).setColor(color);
        bufferbuilder.addVertex(matrix4f,  x2,  y, 0).setUv(1, 0).setColor(color);
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

    public static void blit(GuiGraphics guiGraphics, ResourceLocation location, float x1, float y1, float x2, float y2) {
        blit(guiGraphics.pose(), location, x1, y1, x2, y2, 0, 0, 1, 1);
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

    public static void blitInUv(PoseStack poseStack, ResourceLocation location, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, location);
        _blitInUv(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }
    public static void blitInUv(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blitInUv(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }
    public static void _blitInUv(PoseStack poseStack, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
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

    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, String text, int x, int y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }

    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, Component text, int x, int y, int color,boolean dropShadow) {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        drawString(guiGraphics,font, formattedcharsequence, x - (float) font.width(formattedcharsequence) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }

    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, FormattedCharSequence text, int x, int y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }

    public static int drawString(GuiGraphics guiGraphics,Font font, FormattedCharSequence text, float x, float y, int color,boolean dropShadow) {
        return guiGraphics.drawString(font, text, x, y, color, dropShadow);
    }

    public static int drawString(GuiGraphics guiGraphics,Font font, @Nullable String text, float x, float y, int color,boolean s) {
        return guiGraphics.drawString(font, text, x, y, color, s);
    }

    public static void drawRing(GuiGraphics guiGraphics, int x, int y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color);
    }

    public static void drawRing(GuiGraphics guiGraphics, int x, int y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color,float smooth) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color,smooth);
    }

    public static void drawRing(GuiGraphics guiGraphics, int x, int y, float innerRadius, float outerRadius,float startAngle,float endAngle, int innerColor,int outerColor) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, innerColor, outerColor, 0.5f/outerRadius);
    }

    public static void drawRing(GuiGraphics guiGraphics, int x, int y, float innerRadius, float outerRadius,float startAngle,float endAngle, int innerColor,int outerColor,float smooth) {
        float x2 =  (x + outerRadius);
        float y2 =  (y + outerRadius);
        float x1 =  (x - outerRadius);
        float y1 =  (y - outerRadius);


        RenderSystem.setShader(IUIShaders::getRingShader);
        ShaderInstance shader = IUIShaders.getRingShader();
        shader.safeGetUniform("innerRadius").set(innerRadius/outerRadius/2);
        shader.safeGetUniform("outerRadius").set(0.5f);

        shader.safeGetUniform("innerColor").set(int2vec4(innerColor));
        shader.safeGetUniform("outerColor").set(int2vec4(outerColor));

        shader.safeGetUniform("startAngle").set(startAngle);
        shader.safeGetUniform("endAngle").set(endAngle);

        shader.safeGetUniform("Smooth").set(smooth);

        RenderSystem.enableBlend();
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blitRoundCentered(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float x, float y, int radius, float smooth) {

        float x1 = x - radius;
        float y1 = y - radius;
        float x2 = x + radius;
        float y2 = y + radius;

        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.setShader(IUIShaders::getRoundShader);
        ShaderInstance shader = IUIShaders.getRoundShader();
        shader.safeGetUniform("Smooth").set(smooth);

        RenderSystem.enableBlend();
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }



    private static Vector4f int2vec4(int color) {
        return new Vector4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >> 24 & 255) / 255.0F);
    }
}
