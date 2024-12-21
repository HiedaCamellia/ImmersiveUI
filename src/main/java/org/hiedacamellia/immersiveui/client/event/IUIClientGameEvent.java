package org.hiedacamellia.immersiveui.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

@EventBusSubscriber(modid = ImmersiveUI.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class IUIClientGameEvent {
    @SubscribeEvent
    public static void onMouse(final InputEvent.MouseButton.Pre event) {
        if (event.getButton()==1) {
            if(event.getAction()== InputConstants.PRESS) {
                boolean consumed = World2ScreenWidgetLayer.INSTANCE.click();
                event.setCanceled(consumed);
            }
        }
    }
}
