package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.renderer.GameRenderer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "processBlurEffect(F)V",at=@At("HEAD"),cancellable = true)
    private void processBlurEffect(float partialTick, CallbackInfo ci) {
        if(World2ScreenWidgetLayer.INSTANCE.activeScreen !=null){
            ci.cancel();
        }
    }
}
