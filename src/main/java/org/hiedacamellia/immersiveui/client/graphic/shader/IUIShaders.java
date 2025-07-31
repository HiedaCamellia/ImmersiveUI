package org.hiedacamellia.immersiveui.client.graphic.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderProgram;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * IUIShaders 是一个管理着色器实例的类。
 * 它负责注册和提供各种着色器实例，用于渲染不同的图形效果。
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = ImmersiveUI.MODID)
public class IUIShaders {

    /** 圆角矩形着色器实例。 */
    private static final ShaderProgram roundRect = new ShaderProgram(
            ImmersiveUI.rl("core/round_rect"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 带边框的圆角矩形着色器实例。 */
    private static final ShaderProgram borderRoundRect= new ShaderProgram(
            ImmersiveUI.rl("core/border_round_rect"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 位置和纹理着色器实例。 */
    private static final ShaderProgram positionTexShader= new ShaderProgram(
            ImmersiveUI.rl("core/position_tex"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 模糊效果着色器实例。 */
    private static final ShaderProgram blurShader= new ShaderProgram(
            ImmersiveUI.rl("core/blur"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 环形着色器实例。 */
    private static final ShaderProgram ringShader= new ShaderProgram(
            ImmersiveUI.rl("core/ring"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 圆形着色器实例。 */
    private static final ShaderProgram roundShader= new ShaderProgram(
            ImmersiveUI.rl("core/round"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /** 带边框的矩形着色器实例。 */
    private static final ShaderProgram borderRect= new ShaderProgram(
            ImmersiveUI.rl("core/border_rect"),
            DefaultVertexFormat.POSITION_TEX_COLOR,
            ShaderDefines.EMPTY
    );

    /**
     * 获取圆角矩形着色器实例。
     *
     * @return 圆角矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getRoundRectShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(roundRect), "Round Rect Shader not loaded");
    }

    /**
     * 获取带边框的圆角矩形着色器实例。
     *
     * @return 带边框的圆角矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getBorderRoundRectShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(borderRoundRect), "Border Round Rect Shader not loaded");
    }

    /**
     * 获取位置和纹理着色器实例。
     *
     * @return 位置和纹理着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getPositionTexShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(positionTexShader), "Position Tex Shader not loaded");
    }

    /**
     * 获取模糊效果着色器实例。
     *
     * @return 模糊效果着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getBlurShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(blurShader), "Blur Shader not loaded");
    }

    /**
     * 获取环形着色器实例。
     *
     * @return 环形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getRingShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(ringShader), "Ring Shader not loaded");
    }

    /**
     * 获取圆形着色器实例。
     *
     * @return 圆形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getRoundShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(roundShader), "Round Shader not loaded");
    }

    /**
     * 获取带边框的矩形着色器实例。
     *
     * @return 带边框的矩形着色器实例
     * @throws NullPointerException 如果着色器尚未加载完成
     */
    public static CompiledShaderProgram getBorderRectShader() {
        return Objects.requireNonNull(IUIMinecraftUtil.getShaderProgram(borderRect), "Border Rect Shader not loaded");
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
        event.registerShader(roundRect);
        event.registerShader(borderRoundRect);
        event.registerShader(positionTexShader );
        event.registerShader(blurShader);
        event.registerShader(ringShader);
        event.registerShader(roundShader );
        event.registerShader(borderRect);
    }
}