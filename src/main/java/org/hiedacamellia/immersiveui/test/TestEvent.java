package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
public class TestEvent {

    @SubscribeEvent
    public static void onKey(InputEvent.Key event){
        if(event.getKey()== GLFW.GLFW_KEY_J){
            ImmersiveUI.LOGGER.info("Key J pressed");
            World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(UUID.randomUUID(),new World2ScreenScreen(new OptionsScreen(new TestScreen(),Minecraft.getInstance().options),Minecraft.getInstance().player));
        }
    }
}
