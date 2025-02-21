package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private PostChain blurEffect;

    @Final
    @Shadow
    Minecraft minecraft;


    /**
     * @author Yuqi154
     * @reason bad blur effect
     */
    @Overwrite
    public void processBlurEffect(float partialTick) {
        float f = (float)this.minecraft.options.getMenuBackgroundBlurriness();
        if(ScreenTempTarget.INSTANCE.use){
            if (ScreenTempTarget.INSTANCE.getBlurEffect() != null && f >= 1.0F) {
                ScreenTempTarget.INSTANCE.getBlurEffect().setUniform("Radius", f);
                ScreenTempTarget.INSTANCE.getBlurEffect().process(partialTick);
                return;
            }
        }
        if (this.blurEffect != null && f >= 1.0F) {
            this.blurEffect.setUniform("Radius", f);
            this.blurEffect.process(partialTick);
        }
    }

}
