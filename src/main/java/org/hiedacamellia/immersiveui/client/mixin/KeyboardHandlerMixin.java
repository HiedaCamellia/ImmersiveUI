package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress(JIIII)V", at = @At("HEAD"), cancellable = true)
    private void keyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if(Minecraft.getInstance().level==null)return;
        if(World2ScreenWidgetLayer.INSTANCE.hasScreen()){
            if(key==GLFW.GLFW_KEY_ESCAPE) {
                if (action == GLFW.GLFW_PRESS) {
                    if (World2ScreenWidgetLayer.INSTANCE.activeScreen.keyPressed(key, scanCode, modifiers)) {
                        ci.cancel();
                    }
                }
                return;
            }

            if (World2ScreenWidgetLayer.INSTANCE.activeScreen.keyPressed(key, scanCode, modifiers)) {
                ci.cancel();
            }
        }
        if(ScreenWidgetLayer.INSTANCE.hasScreen()){
            if(key==GLFW.GLFW_KEY_ESCAPE) {
                if (action == GLFW.GLFW_PRESS) {
                    if (ScreenWidgetLayer.INSTANCE.keyPressed(key, scanCode, modifiers)) {
                        ci.cancel();
                    }
                }
                return;
            }

            if (ScreenWidgetLayer.INSTANCE.keyPressed(key, scanCode, modifiers)) {
                ci.cancel();
            }
        }
    }
}
