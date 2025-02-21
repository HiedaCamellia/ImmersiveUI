package org.hiedacamellia.immersiveui.client.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.Screen;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "renderBlurredBackground", at = @At(target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", value = "INVOKE"), cancellable = true)
    private void onBindWrite(float partialTick, CallbackInfo ci){
        if(ScreenTempTarget.INSTANCE.use) {
            RenderSystem.enableDepthTest();
            ci.cancel();
        }
    }
}
