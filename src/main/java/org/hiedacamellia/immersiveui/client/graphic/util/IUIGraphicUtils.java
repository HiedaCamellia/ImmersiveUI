package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.shader.IUIShaders;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class IUIGraphicUtils {

    public static void fillSquareCentered(PoseStack poseStack, float x, float y, float sideLength, int color) {
        fillCentered(poseStack, x, y, sideLength, sideLength, color);
    }

    public static void fillSquare(PoseStack poseStack, float x, float y, float sideLength, int color) {
        fill(poseStack, x, y, sideLength, sideLength, color);
    }

    public static void fillCentered(PoseStack poseStack, float x, float y, float width, float height, int color) {
        fill(poseStack, x - width / 2, y - height / 2, width, height, color);
    }

    public static void fill(PoseStack poseStack, float x, float y, float width, float height, int color) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        poseFill(poseStack, bufferbuilder, x, y, width, height, color);
    }

    public static void fillRoundRectCentered(PoseStack poseStack,float x, float y, float width, float height, float radius, int color) {
        fillRoundRect(poseStack, x-width / 2, y-height / 2, width, height, radius, color);
    }
    public static void fillRoundRectCentered(PoseStack poseStack, float width, float height, float radius, int color) {
        fillRoundRect(poseStack, -width / 2, -height / 2, width, height, radius, color);
    }
    public static void fillRoundRect(PoseStack poseStack, float x, float y, float width, float height, int radius, int color) {
        fillRoundRect(poseStack, x, y, width, height, width > height ? (float) radius / width : (float) radius / height, color);
    }
    public static void fillRoundRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color) {
        float x2 = x + width;
        float y2 = y + height;

        final float ratio =  height /  width;

        RenderSystem.setShader(IUIShaders.getRoundRectShader());
        CompiledShaderProgram shader = IUIShaders.getRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);

        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        poseDraw(poseStack, bufferbuilder, x, y, x2, y2, color);
    }

    public static void fillBorderRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color) {
        fillBorderRect(poseStack, x, y, width, height, radius, radius, color);
    }
    public static void fillBorderRect(PoseStack poseStack, float x, float y, float width, float height, float radiusX, float radiusY, int color) {
        float x1 = x - width * radiusX;
        float y1 = y - height * radiusY;
        float x2 = x + width + width * radiusX;
        float y2 = y + height + height * radiusY;
        
        RenderSystem.setShader(IUIShaders.getBorderRectShader());
        CompiledShaderProgram shader = IUIShaders.getBorderRectShader();
        shader.safeGetUniform("Radius").set(radiusX,radiusY);

        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2, color);
    }

    public static void borderRoundRectCentered(PoseStack poseStack, float x, float y,float width, float height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(poseStack, x-width / 2, y-height / 2, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRectCentered(PoseStack poseStack, float width, float height, float radius, int color, float borderThickness, int borderColor) {
        borderRoundRect(poseStack, -width / 2, -height / 2, width, height, radius, color, borderThickness, borderColor);
    }
    public static void borderRoundRect(PoseStack poseStack, float x, float y, float width, float height, float radius, int color, float borderThickness, int borderColor) {
        _borderRoundRect(poseStack, x, y, x+width, y+height, radius, color, borderThickness, borderColor);
    }
    public static void _borderRoundRect(PoseStack poseStack, float x1, float y1, float x2, float y2,float radius, int color, float borderThickness, int borderColor) {
        final float ratio =  (y2 - y1) / (x2 - x1);
        RenderSystem.setShader(IUIShaders.getBorderRoundRectShader());
        CompiledShaderProgram shader = IUIShaders.getBorderRoundRectShader();
        shader.safeGetUniform("Ratio").set(ratio);
        shader.safeGetUniform("Radius").set(radius);
        shader.safeGetUniform("BorderThickness").set(borderThickness);
        shader.safeGetUniform("BorderColor").set(IUIMathUtils.int2ARGB(borderColor));
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        poseDraw(poseStack,bufferbuilder, x1, y1, x2, y2,  color);
    }


    public static void blit(PoseStack poseStack ,ResourceLocation location, float x, float y, float width, float height, float uOffset, float vOffset, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        _blit(poseStack,location, x,  y,x + width, y + height, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }
    public static void blit(PoseStack poseStack, ResourceLocation location, int x, int y, int width, int height) {
        blit(poseStack, location, (float) x, (float) y, (float) (x + width), (float) (y + height));
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
    public static void _blit(PoseStack poseStack,ResourceLocation location, float x1, float y1, float x2, float y2,float uWidth, float vHeight, float uOffset, float vOffset, float textureWidth, float textureHeight) {
        blit(poseStack,location, x1, y1, x2, y2, (uOffset + 0.0F) / textureWidth, (vOffset + 0.0F) / textureHeight,(uOffset + uWidth) / textureWidth,  (vOffset + vHeight) / textureHeight);
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
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2, u0, v0, u1, v1);
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
        RenderSystem.setShader(IUIShaders.getPositionTexShader());
        CompiledShaderProgram shaderInstance = IUIShaders.getPositionTexShader();
        shaderInstance.safeGetUniform("uvCoords").set(u0, v0, u1, v1);
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2, 0,1,1,0);
    }

    public static void blur(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float radius) {
        blur(poseStack, textureId, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }
    public static void blur(PoseStack poseStack, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius) {
        blur(poseStack, atlasLocation, x1, y1, x2, y2, radius, 0, 0, 1, 1);
    }
    public static void blur(PoseStack poseStack, ResourceLocation atlasLocation, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        _blur(poseStack, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }
    public static void blur(PoseStack poseStack, int textureId, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShaderTexture(0, textureId);
        _blur(poseStack, x1, y1, x2, y2, radius, u0, v0, u1, v1);
    }
    public static void _blur(PoseStack poseStack, float x1, float y1, float x2, float y2, float radius, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(IUIShaders.getBlurShader());
        CompiledShaderProgram shaderInstance = IUIShaders.getBlurShader();
        shaderInstance.safeGetUniform("Radius").set(radius);
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2, u0, v0, u1, v1);
    }

    public static void drawCenteredString(PoseStack poseStack, String text, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(poseStack, IUIMinecraftUtils.getFont(), text, x, y, color, dropShadow);
    }
    public static void drawCenteredString(PoseStack poseStack,Font font, String text, float x, float y, int color,boolean dropShadow) {
        drawString(poseStack,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(PoseStack poseStack, Component component, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(poseStack, IUIMinecraftUtils.getFont(), component, x, y, color, dropShadow);
    }
    public static void drawCenteredString(PoseStack poseStack,Font font, Component component, float x, float y, int color,boolean dropShadow) {
        drawString(poseStack,font, component, x - (float) font.width(component) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static void drawCenteredString(PoseStack poseStack, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        drawCenteredString(poseStack, IUIMinecraftUtils.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static void drawCenteredString(PoseStack poseStack,Font font, FormattedCharSequence text, float x, float y, int color,boolean dropShadow) {
        drawString(poseStack,font, text, x - (float) font.width(text) / 2, y- (float) font.lineHeight /2, color,dropShadow);
    }
    public static int drawString(PoseStack poseStack, FormattedCharSequence charSequence, float x, float y, int color,boolean dropShadow) {
        return drawString(poseStack, IUIMinecraftUtils.getFont(), charSequence, x, y, color, dropShadow);
    }
    public static int drawString(PoseStack poseStack,Font font, @Nullable String text, float x, float y, int color,boolean dropShadow) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return font.drawInBatch(text, x, y, color, dropShadow, poseStack.last().pose(), IUIMinecraftUtils.getRenderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }
    public static int drawString(PoseStack poseStack, Component component, float x, float y, int color,boolean dropShadow) {
        return drawString(poseStack, IUIMinecraftUtils.getFont(), component, x, y, color, dropShadow);
    }
    public static int drawString(PoseStack poseStack, Font font, FormattedCharSequence charSequence, float x, float y, int color, boolean dropShadow) {
        return font.drawInBatch(charSequence, x, y, color, dropShadow, poseStack.last().pose(), IUIMinecraftUtils.getRenderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }
    public static int drawString(PoseStack poseStack, @Nullable String text, float x, float y, int color, boolean dropShadow) {
        return drawString(poseStack, IUIMinecraftUtils.getFont(), text, x, y, color, dropShadow);
    }
    public static int drawString(PoseStack poseStack, Font font, Component component, float x, float y, int color, boolean dropShadow) {
        return font.drawInBatch(component, x, y, color, dropShadow, poseStack.last().pose(), IUIMinecraftUtils.getRenderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }

    public static void drawRing(PoseStack poseStack, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color) {
        drawRing(poseStack, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color);
    }

    public static void drawRing(PoseStack poseStack, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int color,float smooth) {
        drawRing(poseStack, x, y, innerRadius, outerRadius, startAngle, endAngle, color, color,smooth);
    }

    public static void drawRing(PoseStack poseStack, float x, float y, float innerRadius, float outerRadius,float startAngle,float endAngle, int innerColor,int outerColor) {
        drawRing(poseStack, x, y, innerRadius, outerRadius, startAngle, endAngle, innerColor, outerColor, 0.5f/outerRadius);
    }

    public static void drawRing(PoseStack poseStack, float x, float y, float innerRadius, float outerRadius, float startAngle, float endAngle, int innerColor, int outerColor, float smooth) {
        float x2 =  (x + outerRadius);
        float y2 =  (y + outerRadius);
        float x1 =  (x - outerRadius);
        float y1 =  (y - outerRadius);


        RenderSystem.setShader(IUIShaders.getRingShader());
        CompiledShaderProgram shader = IUIShaders.getRingShader();
        shader.safeGetUniform("innerRadius").set(innerRadius/outerRadius/2);
        shader.safeGetUniform("outerRadius").set(0.5f);
        shader.safeGetUniform("innerColor").set(IUIMathUtils.int2RGBA(innerColor));
        shader.safeGetUniform("outerColor").set(IUIMathUtils.int2RGBA(outerColor));
        shader.safeGetUniform("startAngle").set(startAngle);
        shader.safeGetUniform("endAngle").set(endAngle);
        shader.safeGetUniform("Smooth").set(smooth);

        RenderSystem.enableBlend();
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2);
    }

    public static void blitRoundCentered(PoseStack poseStack, ResourceLocation resourceLocation, float x, float y, int radius, float smooth) {
        float x1 = x - radius;
        float y1 = y - radius;
        float x2 = x + radius;
        float y2 = y + radius;
        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.setShader(IUIShaders.getRoundShader());
        CompiledShaderProgram shader = IUIShaders.getRoundShader();
        shader.safeGetUniform("Smooth").set(smooth);

        RenderSystem.enableBlend();
        BufferBuilder bufferbuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        poseDraw(poseStack, bufferbuilder, x1, y1, x2, y2);
    }

    public static void hLine(PoseStack poseStack, float minX, float maxX, float minY, float maxY, int color) {
        if (maxX < minX) {
            float i = minX;
            minX = maxX;
            maxX = i;
        }
        if (maxY < minY) {
            float i = minY;
            minY = maxY;
            maxY = i;
        }
        Matrix4f matrix4f = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.addVertex(matrix4f, minX, minY, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, maxX, maxY, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, maxX + 1, maxY, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, minX+1, minY, 0).setColor(color);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    public static void vLine(PoseStack poseStack, float minX, float maxX, float minY, float maxY, int color) {
        if (maxX < minX) {
            float i = minX;
            minX = maxX;
            maxX = i;
        }
        if (maxY < minY) {
            float i = minY;
            minY = maxY;
            maxY = i;
        }
        Matrix4f matrix4f = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = getBufferBuilder(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.addVertex(matrix4f, minX, minY + 1, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, maxX, maxY + 1, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, maxX, maxY, 0).setColor(color);
        bufferBuilder.addVertex(matrix4f, minX, minY, 0).setColor(color);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    public static void hLine(PoseStack poseStack, float minX, float maxX, float y, int color) {
        if (maxX < minX) {
            float i = minX;
            minX = maxX;
            maxX = i;
        }
        fill(poseStack, minX, y, maxX - minX, 1, color);
    }

    public static void vLine(PoseStack poseStack, float x, float minY, float maxY, int color) {
        if (maxY < minY) {
            float i = minY;
            minY = maxY;
            maxY = i;
        }
        fill(poseStack, x, minY, 1, maxY - minY, color);
    }

    public static void poseDraw(PoseStack poseStack,BufferBuilder bufferbuilder, float x1, float y1, float x2, float y2){
        poseDraw(poseStack,bufferbuilder, x1, y1, x2, y2, 0, 0, 1, 1);
    }
    public static void poseDraw(PoseStack poseStack,BufferBuilder bufferbuilder, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1){
        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }
    public static void poseDraw(PoseStack poseStack,BufferBuilder bufferbuilder, float x1, float y1, float x2, float y2,int color){
        poseDraw(poseStack,bufferbuilder, x1, y1, x2, y2, 0, 0, 1, 1,color);
    }
    public static void poseDraw(PoseStack poseStack,BufferBuilder bufferbuilder, float x1, float y1, float x2, float y2, float u0, float v0, float u1, float v1,int color){
        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(u0, v0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setUv(u0, v1).setColor(color);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setUv(u1, v1).setColor(color);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setUv(u1, v0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }
    public static void poseFill(PoseStack poseStack,BufferBuilder bufferbuilder, float x1, float y1, float x2, float y2,int color){
        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.addVertex(matrix4f, x1, y1, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x1, y2, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x2, y2, 0).setColor(color);
        bufferbuilder.addVertex(matrix4f, x2, y1, 0).setColor(color);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }


    private static BufferBuilder getBufferBuilder(VertexFormat.Mode mode, VertexFormat format) {
        return Tesselator.getInstance().begin(mode, format);
    }

}
