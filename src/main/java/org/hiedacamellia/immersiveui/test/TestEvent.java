package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
public class TestEvent {

    @SubscribeEvent
    public static void onScreen(ScreenEvent.Opening event) {

        if(World2ScreenWidgetLayer.INSTANCE.activeScreen ==null){
            if(event.getNewScreen() instanceof PauseScreen pauseScreen){
                UUID uuid = UUID.randomUUID();
                World2ScreenScreen screenScreen = new World2ScreenScreen(uuid, pauseScreen, Minecraft.getInstance().player);
                World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, screenScreen);
//                World2ScreenWidgetLayer.INSTANCE.activeScreen = screenScreen;
//                World2ScreenWidgetLayer.INSTANCE.screenUUID = uuid;
                event.setCanceled(true);
            }
        }
    }
}
