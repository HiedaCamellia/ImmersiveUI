package org.hiedacamellia.immersiveui.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = ImmersiveUI.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class IUIClientGameEvent {
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
    public static void onScreen(ScreenEvent.Opening event) {
        if(World2ScreenWidgetLayer.INSTANCE.screen!=null){
            World2ScreenWidgetLayer.INSTANCE.screen.setScreen(event.getNewScreen());
            event.setCanceled(true);
        }
    }
}
