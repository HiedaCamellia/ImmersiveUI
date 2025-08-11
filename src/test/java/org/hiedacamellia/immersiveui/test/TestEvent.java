package org.hiedacamellia.immersiveui.test;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ScreenEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class TestEvent {

    @SubscribeEvent
    public static void onScreen(ScreenEvent.Opening event) {

        if(FMLEnvironment.production) return;

//        if(World2ScreenWidgetLayer.INSTANCE.activeScreen ==null){
//            if(event.getNewScreen() instanceof PauseScreen pauseScreen){
//                UUID uuid = UUID.randomUUID();
//                World2ScreenScreen screenScreen = new World2ScreenScreen(uuid, pauseScreen, Minecraft.getInstance().player);
//                World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, screenScreen);
//                event.setCanceled(true);
//            }
//        }
    }


}
