package org.hiedacamellia.immersiveui;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.hiedacamellia.immersiveui.client.config.ClientConfig;
import org.hiedacamellia.immersiveui.client.event.IUIClientGameEvent;
import org.hiedacamellia.immersiveui.test.TestEvent;

@Mod(value = ImmersiveUI.MODID,dist = Dist.CLIENT)

public class ImmersiveUIClient {

    public ImmersiveUIClient(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(IUIClientGameEvent::onClientSetup);


        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }


}
