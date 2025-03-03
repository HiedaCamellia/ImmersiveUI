package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.camellialib.client.debug.DebugEntry;
import org.hiedacamellia.camellialib.client.debug.DebugRegistries;
import org.hiedacamellia.camellialib.client.gui.tree.DebugTreeEntryWidget;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
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

    public static void registerTestScreen(){
        DebugTreeEntryWidget testScreen = DebugTreeEntryWidget.create(new DebugEntry(ImmersiveUI.MODID, new TestScreen()), Component.literal("Test Screen"), Minecraft.getInstance().font);
        DebugTreeEntryWidget parent = DebugTreeEntryWidget.create(null, Component.literal(ImmersiveUI.MODID), Minecraft.getInstance().font);
        parent.addChild(testScreen);
        DebugRegistries.registerRoot(parent);
    }

}
