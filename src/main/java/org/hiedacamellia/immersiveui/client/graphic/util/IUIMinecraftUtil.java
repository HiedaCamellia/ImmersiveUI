package org.hiedacamellia.immersiveui.client.graphic.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.world.phys.HitResult;

/**
 * IUIMinecraftUtil 是一个工具类，提供了对 Minecraft 客户端实例的常用方法封装。
 * 这些方法用于获取字体、渲染目标、窗口信息以及鼠标处理器等。
 */
public class IUIMinecraftUtil {

    /**
     * 获取当前的着色器程序。
     *
     * @param program 着色器程序实例
     * @return 编译后的着色器程序
     */
    public static CompiledShaderProgram getShaderProgram(ShaderProgram program) {
        return getShaderManager().getProgram(program);
    }

    /**
     * 获取当前的着色器管理器。
     *
     * @return 着色器管理器实例
     */
    public static ShaderManager getShaderManager() {
        return getMinecraft().getShaderManager();
    }
    
    /**
     * 获取当前的 Minecraft 客户端实例。
     *
     * @return Minecraft 实例
     */
    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    /**
     * 获取 Minecraft 客户端的字体渲染器。
     *
     * @return 字体渲染器实例
     */
    public static Font getFont() {
        return getMinecraft().font;
    }

    /**
     * 获取当前的碰撞检测结果。
     *
     * @return 碰撞检测结果
     */
    public static HitResult getHit() {
        return getMinecraft().hitResult;
    }

    /**
     * 获取主渲染目标。
     *
     * @return 主渲染目标实例
     */
    public static RenderTarget getMainRenderTarget() {
        return getMinecraft().getMainRenderTarget();
    }

    /**
     * 获取渲染缓冲区。
     *
     * @return 渲染缓冲区实例
     */
    public static RenderBuffers getRenderBuffers() {
        return getMinecraft().renderBuffers();
    }

    /**
     * 获取窗口实例。
     *
     * @return 窗口实例
     */
    public static Window getWindow() {
        return getMinecraft().getWindow();
    }

    /**
     * 获取鼠标处理器实例。
     *
     * @return 鼠标处理器实例
     */
    public static MouseHandler getMouseHandler() {
        return getMinecraft().mouseHandler;
    }

    /**
     * 获取 GUI 的缩放比例。
     *
     * @return GUI 缩放比例
     */
    public static double getGuiScale() {
        return getWindow().getGuiScale();
    }

    /**
     * 获取 GUI 缩放后的宽度。
     *
     * @return GUI 缩放后的宽度
     */
    public static int getGuiScaledWidth() {
        return getWindow().getGuiScaledWidth();
    }

    /**
     * 获取 GUI 缩放后的高度。
     *
     * @return GUI 缩放后的高度
     */
    public static int getGuiScaledHeight() {
        return getWindow().getGuiScaledHeight();
    }

    /**
     * 获取 GUI 缩放后的中心 X 坐标。
     *
     * @return GUI 缩放后的中心 X 坐标
     */
    public static int getGuiScaledCenterX() {
        return getGuiScaledWidth() / 2;
    }

    /**
     * 获取 GUI 缩放后的中心 Y 坐标。
     *
     * @return GUI 缩放后的中心 Y 坐标
     */
    public static int getGuiScaledCenterY() {
        return getGuiScaledHeight() / 2;
    }

    /**
     * 获取屏幕的宽度。
     *
     * @return 屏幕宽度
     */
    public static int getWidth() {
        return getWindow().getWidth();
    }

    /**
     * 获取屏幕的高度。
     *
     * @return 屏幕高度
     */
    public static int getHeight() {
        return getWindow().getHeight();
    }

    /**
     * 获取屏幕的中心 X 坐标。
     *
     * @return 屏幕中心 X 坐标
     */
    public static int getCenterX() {
        return getWidth() / 2;
    }

    /**
     * 获取屏幕的中心 Y 坐标。
     *
     * @return 屏幕中心 Y 坐标
     */
    public static int getCenterY() {
        return getHeight() / 2;
    }
}