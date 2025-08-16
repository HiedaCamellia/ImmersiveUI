package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.hiedacamellia.immersiveui.client.event.IUIMixinHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "setScreen",at = @At("HEAD"))
    public void setScreen(Screen guiScreen,CallbackInfo ci){
        IUIMixinHandler.setScreen(guiScreen, ci);
    }

    @Inject(method = "resizeDisplay",at = @At("TAIL"))
    public void resizeDisplay(CallbackInfo ci){
        IUIMixinHandler.resizeDisplay(ci);
    }
}
