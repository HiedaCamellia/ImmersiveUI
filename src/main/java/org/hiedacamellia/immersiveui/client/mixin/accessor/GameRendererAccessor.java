package org.hiedacamellia.immersiveui.client.mixin.accessor;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Invoker("getFov")
    double tsi$getFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting);

    @Accessor("shaders")
    Map<String, ShaderInstance> getShaders();
}
