/*
 * Code from https://github.com/LouisQuepierts/ThatSkyInteractions
 * net.quepierts.thatskyinteractions.client.util
 * RenderUtils.java
 *
 * This code is under the MIT License.
 */

package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.shader.IUIShaders;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class IUIGuiUtils{


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
        guiFill(guiGraphics, x, y, x + width, y + height, color);
    }

    public static void fillRoundRectCentered(GuiGraphics guiGraphics,float x, float y, float width, float height, float radius, int color) {
        fillRoundRect(guiGraphics, x-width / 2, y-height / 2, width, height, radius, color);
    }
    public static void fillRoundRectCentered(GuiGraphics guiGraphics, float width, float height, float radius, int color) {
        fillRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color);
    }
    public static void fillRoundRect(GuiGraphics guiGraphics, float x, float y, float width, float height, int radius, int color) {
        fillRoundRect(guiGraphics, x, y, width, height, width > height ? (float) radius / width : (float) radius / height, color);
    }
    public static void fillRoundRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radius, int color) {
        float x2 = x + width;
        float y2 = y + height;

        final float ratio =  height /  width;

        RenderSystem.setShader(IUIShaders::getRoundRectShader);
        ShaderInstance shader = IUIShaders.getRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);

        guiDraw(guiGraphics, x, y, x2, y2, color);
    }

    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radius, int color) {
        fillBorderRect(guiGraphics, x, y, width, height, radius, radius, color);
    }
    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radiusX,float radiusY, int color) {
        float x1 = x - width * radiusX;
        float y1 = y - height * radiusY;
        float x2 = x + width + width * radiusX;
        float y2 = y + height + height * radiusY;

        RenderSystem.setShader(IUIShaders::getBorderRectShader);
        ShaderInstance shader = IUIShaders.getBorderRectShader();
        shader.safeGetUniform("Radius").set(radiusX,radiusY);

        guiDraw(guiGraphics, x1, y1, x2, y2, color);
    }

    public static void borderRoundRectCentered(GuiGraphics guiGraphics, float x, float y,float width, float height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics, x-width / 2, y-height / 2, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRectCentered(GuiGraphics guiGraphics, float width, float height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(guiGraphics, -width / 2, -height / 2, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radius, int color, float borderThickness, int borderColor) {
        _borderRoundRect(guiGraphics, x, y, x+width, y+height, radius, color, borderThickness, borderColor);
    }
    public static void _borderRoundRect(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float radius, int color, float borderThickness, int borderColor) {
        float ratio =  (y2 - y1) / (x2 - x1);
        RenderSystem.setShader(IUIShaders::getBorderRoundRectShader);
        ShaderInstance shader = IUIShaders.getBorderRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);
        shader.safeGetUniform("BorderThickness").set(borderThickness);
        shader.safeGetUniform("BorderColor").set(IUIMathUtils.int2ARGB(borderColor));

        guiDraw(guiGraphics, x1, y1, x2, y2, color);
    }


    public static void blit(GuiGraphics guiGraphics, ResourceLocation location, int x, int y, int width, int height) {
        blit(guiGraphics, location, (float) x, (float) y, (float) (x + width), (float) (y + height));
    }
    public static void blit(GuiGraphics guiGraphics, int textureId, int x, int y, int width, int height) {
        blit(guiGraphics, textureId, (float) x, (float) y, (float) (x + width), (float) (y + height));
    }
    public static void blit(GuiGraphics guiGraphics, ResourceLocation location, float x1, float y1, float x2, float y2) {
        blit(guiGraphics, location, x1, y1, x2, y2, 0, 0, 1, 1);
    }
    public static void blit(GuiGraphics guiGraphics, int textureId, float x1, float y1, float x2, float y2) {
        blit(guiGraphics, textureId, x1, y1, x2, y2, 0, 0, 1, 1);
    }
    public static void blit(GuiGraphics guiGraphics,ResourceLocation location, float x1, float y1, float x2, float y2,int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        blit(guiGraphics,location, x1, y1, x2, y2, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }
    public static void blit(GuiGraphics guiGraphics, ResourceLocation location, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, location);
        _blit(guiGraphics, x1, y1, x2, y2, u0, v0, u1, v1);
    }
    public static void blit(GuiGraphics guiGraphics, int textureId, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blit(guiGraphics, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void _blit(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        guiDraw(guiGraphics, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void blitInUv(GuiGraphics poseStack, ResourceLocation location, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, location);
        _blitInUv(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }
    public static void blitInUv(GuiGraphics poseStack, int textureId, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blitInUv(poseStack, x1, y1, x2, y2, u0, v0, u1, v1);
    }
    public static void _blitInUv(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(IUIShaders::getPositionTexShader);
        ShaderInstance shaderInstance = IUIShaders.getPositionTexShader();
        shaderInstance.safeGetUniform("uvCoords").set(u0, v0, u1, v1);
        guiDraw(guiGraphics, x1, y1, x2, y2,0,1,1,0);
    }

    public static void blur(GuiGraphics guiGraphics, int textureId, float x1, float y1, float x2, float y2, float radius) {
        blur(guiGraphics, textureId, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }
    public static void blur(GuiGraphics guiGraphics, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius) {
        blur(guiGraphics, atlasLocation, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }
    public static void blur(GuiGraphics guiGraphics, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        _blur(guiGraphics, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }
    public static void blur(GuiGraphics guiGraphics, int textureId, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blur(guiGraphics, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }
    private static void _blur(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(IUIShaders::getBlurShader);
        ShaderInstance shaderInstance = IUIShaders.getBlurShader();
        shaderInstance.safeGetUniform("Radius").set(radius);
        guiDraw(guiGraphics, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void drawCenteredString(GuiGraphics guiGraphics, String text, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtil.getFont(), text, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, String text, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics, Component component, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtil.getFont(), component, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, Component component, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, component, x - (float) font.width(component) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtil.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, charSequence, x - (float) font.width(charSequence) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics,IUIMinecraftUtil.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        return guiGraphics.drawString(font, charSequence, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics, Component component, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics,IUIMinecraftUtil.getFont(), component, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, Component component, float x, float y, int color,boolean dropShadow) {
        return font.drawInBatch(component, x, y, color, dropShadow, guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }
    public static int drawString(GuiGraphics guiGraphics, @Nullable String text, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics,IUIMinecraftUtil.getFont(), text, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, @Nullable String text, float x, float y, int color,boolean dropShadow) {
        return guiGraphics.drawString(font, text, x, y, color, dropShadow);
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
    public static void drawRing(GuiGraphics guiGraphics, int x, int y, float innerRadius, float outerRadius, float startAngle, float endAngle, int innerColor, int outerColor, float smooth) {
        float x2 =  (x + outerRadius);
        float y2 =  (y + outerRadius);
        float x1 =  (x - outerRadius);
        float y1 =  (y - outerRadius);

        RenderSystem.setShader(IUIShaders::getRingShader);
        ShaderInstance shader = IUIShaders.getRingShader();
        shader.safeGetUniform("innerRadius").set(innerRadius/outerRadius/2);
        shader.safeGetUniform("outerRadius").set(0.5f);
        shader.safeGetUniform("innerColor").set(IUIMathUtils.int2RGBA(innerColor));
        shader.safeGetUniform("outerColor").set(IUIMathUtils.int2RGBA(outerColor));
        shader.safeGetUniform("startAngle").set(startAngle);
        shader.safeGetUniform("endAngle").set(endAngle);
        shader.safeGetUniform("Smooth").set(smooth);

        RenderSystem.enableBlend();
        guiDraw(guiGraphics, x1, y1, x2, y2);
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
        guiDraw(guiGraphics, x1, y1, x2, y2);
    }

    public static void guiDraw(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2){
        guiDraw(guiGraphics, x1, y1, x2, y2, 0, 0, 1, 1);
    }
    public static void guiDraw(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1){
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        VertexConsumer vertexConsumer = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        vertexConsumer.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0);
        vertexConsumer.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1);
        vertexConsumer.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1);
        vertexConsumer.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0);
    }
    public static void guiDraw(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2,int color){
        guiDraw(guiGraphics, x1, y1, x2, y2, 0, 0, 1, 1,color);
    }
    public static void guiDraw(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1,int color){
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        VertexConsumer vertexConsumer = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        vertexConsumer.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0).setColor(color);
        vertexConsumer.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1).setColor(color);
        vertexConsumer.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1).setColor(color);
        vertexConsumer.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0).setColor(color);
    }
    public static void guiFill(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2,int color){
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        VertexConsumer vertexConsumer = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        vertexConsumer.addVertex(matrix4f, x1, y1, 0).setColor(color);
        vertexConsumer.addVertex(matrix4f, x1, y2, 0).setColor(color);
        vertexConsumer.addVertex(matrix4f, x2, y2, 0).setColor(color);
        vertexConsumer.addVertex(matrix4f, x2, y1, 0).setColor(color);
    }


    public static void renderSlotBackground(GuiGraphics guiGraphics, int x, int y) {
        renderSlotBackground(guiGraphics, x, y, 0xFFf0e0b0, 0xFF8B4513);
    }
    public static void renderSlotBackground(GuiGraphics guiGraphics, int x, int y,int bg_color,int border_color) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, x - 1, y - 1, 18, 18, 0.05f, border_color);
        IUIGuiUtils.fillRoundRect(guiGraphics, x, y, 16, 16, 0.05f, bg_color);
        RenderSystem.disableBlend();
    }

}
