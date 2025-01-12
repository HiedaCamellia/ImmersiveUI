package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
public class TestEvent {

    @SubscribeEvent
    public static void onKey(InputEvent.Key event){
        if(event.getKey()== GLFW.GLFW_KEY_J){
            Minecraft.getInstance().setScreen(new TestScreen());
        }
    }
}
