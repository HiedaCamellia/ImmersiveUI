package org.hiedacamellia.immersiveui.registries;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class IUILayer {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerBelow(VanillaGuiLayers.DEBUG_OVERLAY, World2ScreenWidgetLayer.LOCATION, World2ScreenWidgetLayer.INSTANCE);
        event.registerBelow(VanillaGuiLayers.DEBUG_OVERLAY, ScreenWidgetLayer.LOCATION, ScreenWidgetLayer.INSTANCE);
    }
}
