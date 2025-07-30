/*
 * Code partly from https://github.com/LouisQuepierts/ThatSkyInteractions
 * net.quepierts.thatskyinteractions.client.registry
 * Shaders.java
 *
 * This code is under the MIT License.
 */

package org.hiedacamellia.immersiveui.client.graphic.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;

/**
 * IUIShaders 是一个管理着色器实例的类。
 * 它负责注册和提供各种着色器实例，用于渲染不同的图形效果。
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = ImmersiveUI.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IUIShaders {

    /** 圆角矩形着色器实例。 */
    @Nullable
    private static ShaderInstance roundRect;

    /** 带边框的圆角矩形着色器实例。 */
    @Nullable
    private static ShaderInstance borderRoundRect;

    /** 位置和纹理着色器实例。 */
    @Nullable
    private static ShaderInstance positionTexShader;

    /** 模糊效果着色器实例。 */
    @Nullable
    private static ShaderInstance blurShader;

    /** 环形着色器实例。 */
    @Nullable
    private static ShaderInstance ringShader;

    /** 圆形着色器实例。 */
    @Nullable
    private static ShaderInstance roundShader;

    /** 带边框的矩形着色器实例。 */
    @Nullable
    private static ShaderInstance borderRect;

    /**
     * 获取圆角矩形着色器实例。
     *
     * @return 圆角矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getRoundRectShader() {
        return Objects.requireNonNull(roundRect, "Attempted to call getRoundRectShader before shaders have finished loading.");
    }

    /**
     * 获取带边框的圆角矩形着色器实例。
     *
     * @return 带边框的圆角矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getBorderRoundRectShader() {
        return Objects.requireNonNull(borderRoundRect, "Attempted to call getBorderRoundRectShader before shaders have finished loading.");
    }

    /**
     * 获取位置和纹理着色器实例。
     *
     * @return 位置和纹理着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getPositionTexShader() {
        return Objects.requireNonNull(positionTexShader, "Attempted to call getPositionTexShader before shaders have finished loading.");
    }

    /**
     * 获取模糊效果着色器实例。
     *
     * @return 模糊效果着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getBlurShader() {
        return Objects.requireNonNull(blurShader, "Attempted to call getBlurShader before shaders have finished loading.");
    }

    /**
     * 获取环形着色器实例。
     *
     * @return 环形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getRingShader() {
        return Objects.requireNonNull(ringShader, "Attempted to call getRingShader before shaders have finished loading.");
    }

    /**
     * 获取圆形着色器实例。
     *
     * @return 圆形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getRoundShader() {
        return Objects.requireNonNull(roundShader, "Attempted to call getRoundShader before shaders have finished loading.");
    }

    /**
     * 获取带边框的矩形着色器实例。
     *
     * @return 带边框的矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static ShaderInstance getBorderRectShader() {
        return Objects.requireNonNull(borderRect, "Attempted to call getBorderRectShader before shaders have finished loading.");
    }

    /**
     * 注册着色器事件处理方法。
     * 在游戏启动时加载并注册所有需要的着色器实例。
     *
     * @param event 注册着色器事件
     * @throws IOException 如果加载着色器时发生 I/O 错误
     */
    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider provider = event.getResourceProvider();
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("round_rect"),
                        DefaultVertexFormat.POSITION_TEX_COLOR
                ),
                (shader) -> roundRect = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("border_round_rect"),
                        DefaultVertexFormat.POSITION_TEX_COLOR
                ),
                (shader) -> borderRoundRect = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("position_tex"),
                        DefaultVertexFormat.POSITION_TEX
                ),
                (shader) -> positionTexShader = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("blur"),
                        DefaultVertexFormat.POSITION_TEX
                ),
                (shader) -> blurShader = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("ring"),
                        DefaultVertexFormat.POSITION_TEX
                ),
                (shader) -> ringShader = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("round"),
                        DefaultVertexFormat.POSITION_TEX
                ),
                (shader) -> roundShader = shader
        );
        event.registerShader(
                new ShaderInstance(
                        provider,
                        ImmersiveUI.rl("border_rect"),
                        DefaultVertexFormat.POSITION_TEX_COLOR
                ),
                (shader) -> borderRect = shader
        );
    }
}