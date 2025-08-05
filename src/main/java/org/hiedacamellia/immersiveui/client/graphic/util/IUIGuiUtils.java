package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

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
        IUIGraphicUtils.fillRoundRect(guiGraphics.pose(), x, y, width, height, radius, color);
    }

    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radius, int color) {
        fillBorderRect(guiGraphics, x, y, width, height, radius, radius, color);
    }
    public static void fillBorderRect(GuiGraphics guiGraphics, float x, float y, float width, float height, float radiusX,float radiusY, int color) {
        IUIGraphicUtils.fillBorderRect(guiGraphics.pose(), x, y, width, height, radiusX, radiusY, color);
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
        IUIGraphicUtils._borderRoundRect(guiGraphics.pose(), x1, y1, x2, y2, radius, color, borderThickness, borderColor);
    }

    public static void blit(GuiGraphics guiGraphics,ResourceLocation location, float x, float y, float width, float height, float uOffset, float vOffset, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        _blit(guiGraphics,location, x,  y,x + width, y + height, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
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
    public static void _blit(GuiGraphics guiGraphics,ResourceLocation location, float x1, float y1, float x2, float y2,float uWidth, float vHeight, float uOffset, float vOffset, float textureWidth, float textureHeight) {
        blit(guiGraphics,location, x1, y1, x2, y2, (uOffset + 0.0F) / textureWidth, (vOffset + 0.0F) / textureHeight,(uOffset + uWidth) / textureWidth,  (vOffset + vHeight) / textureHeight);
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
        IUIGraphicUtils._blit(guiGraphics.pose(), x1, y1, x2, y2, u0, v0, u1, v1);
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
        IUIGraphicUtils._blitInUv(guiGraphics.pose(), x1, y1, x2, y2, u0, v0, u1, v1);
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
        IUIGraphicUtils._blur(guiGraphics.pose(), x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }

    public static void drawCenteredString(GuiGraphics guiGraphics, String text, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtils.getFont(), text, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, String text, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics, Component component, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtils.getFont(), component, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, Component component, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, component, x - (float) font.width(component) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(guiGraphics, IUIMinecraftUtils.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static void drawCenteredString(GuiGraphics guiGraphics,Font font, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        drawString(guiGraphics,font, charSequence, x - (float) font.width(charSequence) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics, IUIMinecraftUtils.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        return guiGraphics.drawString(font, charSequence, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics, Component component, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics, IUIMinecraftUtils.getFont(), component, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, Component component, float x, float y, int color,boolean dropShadow) {
        AtomicInteger width = new AtomicInteger();
        guiGraphics.drawSpecial(multiBufferSource -> width.set(font.drawInBatch(component, x, y, color, dropShadow, guiGraphics.pose().last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, 15728880)));
        return width.get();
    }
    public static int drawString(GuiGraphics guiGraphics, @Nullable String text, float x, float y, int color,boolean dropShadow) {
        return drawString(guiGraphics, IUIMinecraftUtils.getFont(), text, x, y, color, dropShadow);
    }
    public static int drawString(GuiGraphics guiGraphics,Font font, @Nullable String text, float x, float y, int color,boolean dropShadow) {
        return guiGraphics.drawString(font, text, x, y, color, dropShadow);
    }

    public static void drawRing(GuiGraphics guiGraphics, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color);
    }
    public static void drawRing(GuiGraphics guiGraphics, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color,float smooth) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color,smooth);
    }
    public static void drawRing(GuiGraphics guiGraphics, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int innerColor,int outerColor) {
        drawRing(guiGraphics, x, y, innerRadius, outerRadius, startAngle, endAngle, innerColor, outerColor, 0.5f/outerRadius);
    }
    public static void drawRing(GuiGraphics guiGraphics, float x, float y, float innerRadius, float outerRadius, float startAngle, float endAngle, int innerColor, int outerColor, float smooth) {
        IUIGraphicUtils.drawRing(guiGraphics.pose(), x, y, innerRadius, outerRadius, startAngle, endAngle, innerColor, outerColor, smooth);
    }

    public static void blitRoundCentered(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float x, float y, int radius, float smooth) {
        IUIGraphicUtils.blitRoundCentered(guiGraphics.pose(), resourceLocation, x, y, radius, smooth);
    }

    public static void guiFill(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2,int color){
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        guiGraphics.drawSpecial(multiBufferSource -> {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.gui());
            vertexConsumer.addVertex(matrix4f, x1, y1, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x1, y2, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x2, y2, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x2, y1, 0).setColor(color);
        });
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
