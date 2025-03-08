package org.hiedacamellia.immersiveui.client.debug;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.hiedacamellia.immersiveui.client.config.IUIClientConfig;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
public class IUIClientDebugEvent {


    @SubscribeEvent
    public static void onKey(final InputEvent.Key event) {

        if(IUIClientConfig.DEBUG.isFalse()) return;

        if(event.getKey() == GLFW.GLFW_KEY_C){
            Minecraft.getInstance().setScreen(new DebugScreen());
        }

    }
}
