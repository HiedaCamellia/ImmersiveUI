package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Final
    @Shadow
    Minecraft minecraft;

    @Inject(method = "processBlurEffect(F)V",at = @At("HEAD"),cancellable = true)
    private void processBlurEffect(float partialTick, CallbackInfo ci){
        float f = (float)this.minecraft.options.getMenuBackgroundBlurriness();
        if(ScreenTempTarget.INSTANCE.use){
            if (ScreenTempTarget.INSTANCE.getBlurEffect() != null && f >= 1.0F) {
                ScreenTempTarget.INSTANCE.getBlurEffect().setUniform("Radius", f);
                ScreenTempTarget.INSTANCE.getBlurEffect().process(partialTick);
                ci.cancel();
            }
        }
    }

}
