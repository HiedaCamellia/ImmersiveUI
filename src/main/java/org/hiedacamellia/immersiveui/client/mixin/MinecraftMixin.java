package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "setScreen",at = @At("HEAD"))
    public void setScreen(Screen guiScreen,CallbackInfo ci){
        if(guiScreen==null&&World2ScreenWidgetLayer.INSTANCE.activeScreen !=null){
            World2ScreenWidgetLayer.INSTANCE.remove(World2ScreenWidgetLayer.INSTANCE.activeScreen.getUuid());
        }
    }
}
