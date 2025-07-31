package org.hiedacamellia.immersiveui.client.graphic.target;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;

import java.util.Set;

import static net.minecraft.client.Minecraft.ON_OSX;

/**
 * ScreenTempTarget 是一个扩展自 RenderTarget 的类。
 * 它用于管理屏幕的临时渲染目标，并支持模糊效果的后处理。
 */
public class ScreenTempTarget extends RenderTarget {

    /** 渲染目标的宽度。 */
    protected int width;

    /** 渲染目标的高度。 */
    protected int height;

    /** 是否启用当前渲染目标。 */
    public boolean use;

    /** 屏幕实例，用于普通渲染。 */
    public static ScreenTempTarget SCREEN_INSTANCE;

    /** 屏幕实例，用于模糊渲染。 */
    public static ScreenTempTarget BLUR_INSTANCE;

    private static final ResourceLocation BLUR_INSTANCE_ID = ImmersiveUI.rl("screen_temp_target_blur");

    /**
     * 构造函数，初始化 ScreenTempTarget 实例。
     *
     * @param width  渲染目标的宽度
     * @param height 渲染目标的高度
     */
    public ScreenTempTarget(int width, int height) {
        super(true);
        this.createBuffers(width, height);
        this.width = width;
        this.height = height;
        this.use = false;
    }

    /**
     * 获取模糊效果的后处理链。
     *
     * @return 模糊效果的 PostChain 实例
     */
    public static PostChain getBlurEffect() {
        return IUIMinecraftUtil.getShaderManager().getPostChain(ResourceLocation.withDefaultNamespace("blur"), Set.of(BLUR_INSTANCE_ID));
    }
}