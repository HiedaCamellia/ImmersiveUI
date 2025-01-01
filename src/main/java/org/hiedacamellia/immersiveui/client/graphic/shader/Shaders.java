/*
 * Code from https://github.com/LouisQuepierts/ThatSkyInteractions
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

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = ImmersiveUI.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Shaders {
    @Nullable
    private static ShaderInstance roundRect;
    @Nullable
    private static ShaderInstance borderRoundRect;

    
    public static ShaderInstance getRoundRectShader() {
        return Objects.requireNonNull(roundRect, "Attempted to call getRoundRectShader before shaders have finished loading.");
    }

    public static ShaderInstance getBorderRoundRectShader() {
        return Objects.requireNonNull(borderRoundRect, "Attempted to call getBorderRoundRectShader before shaders have finished loading.");
    }


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
    }
}
