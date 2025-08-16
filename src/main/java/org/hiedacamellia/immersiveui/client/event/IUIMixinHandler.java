package org.hiedacamellia.immersiveui.client.event;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.common.NeoForge;
import org.hiedacamellia.immersiveui.api.event.CustomScreenEvent;
import org.hiedacamellia.immersiveui.api.event.ResizeDisplayEvent;
import org.hiedacamellia.immersiveui.api.event.SetScreenEvent;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class IUIMixinHandler {

    public static void keyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci){
        if(Minecraft.getInstance().level==null)return;
        if(World2ScreenWidgetLayer.INSTANCE.hasScreen()){
            if(key== GLFW.GLFW_KEY_ESCAPE) {
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

    public static void setScreen(Screen guiScreen, CallbackInfo ci){
        NeoForge.EVENT_BUS.post(new SetScreenEvent(guiScreen));
        NeoForge.EVENT_BUS.post(new CustomScreenEvent(guiScreen));
    }

    public static void resizeDisplay(CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new ResizeDisplayEvent());
    }
}
