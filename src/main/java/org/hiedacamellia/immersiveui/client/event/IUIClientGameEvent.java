package org.hiedacamellia.immersiveui.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.test.TestEvent;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@EventBusSubscriber(modid = ImmersiveUI.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class IUIClientGameEvent {

    public static void onClientSetup(final FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            ScreenTempTarget.SCREEN_INSTANCE = new ScreenTempTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
            ScreenTempTarget.BLUR_INSTANCE = new ScreenTempTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
        });

        TestEvent.registerTestScreen();
    }



    @SubscribeEvent
    public static void onKey(final InputEvent.Key event) {

    }

    @SubscribeEvent
    public static void onMouse(final InputEvent.MouseButton.Pre event) {
        if(Minecraft.getInstance().level==null)return;

        if (event.getButton()== GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if(event.getAction()== InputConstants.PRESS) {
                boolean consumed = World2ScreenWidgetLayer.INSTANCE.click(event.getButton());
                event.setCanceled(consumed);
            }
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(final InputEvent.MouseScrollingEvent event) {
        if(Minecraft.getInstance().level==null)return;


        event.setCanceled(World2ScreenWidgetLayer.INSTANCE.scroll(event.getMouseX(), event.getMouseY(), event.getScrollDeltaX(), event.getScrollDeltaY()));


    }

    @SubscribeEvent
    public static void onScreen(ScreenEvent.Opening event) {

//        if(World2ScreenWidgetLayer.INSTANCE.activeScreen !=null){
//            World2ScreenWidgetLayer.INSTANCE.activeScreen.setScreen(event.getNewScreen());
//            event.setCanceled(true);
//        }else {
//            if(event.getNewScreen() instanceof PauseScreen pauseScreen){
//                UUID uuid = UUID.randomUUID();
//                World2ScreenScreen screenScreen = new World2ScreenScreen(uuid, pauseScreen, Minecraft.getInstance().player);
//                World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, screenScreen);
//                World2ScreenWidgetLayer.INSTANCE.setActiveScreen(screenScreen);
//                event.setCanceled(true);
//            }
//        }
    }
}
