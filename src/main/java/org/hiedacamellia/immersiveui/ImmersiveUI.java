package org.hiedacamellia.immersiveui;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.hiedacamellia.immersiveui.client.config.ClientConfig;
import org.slf4j.Logger;

@Mod(ImmersiveUI.MODID)
public class ImmersiveUI {
    public static final String MODID = "immersiveui";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ImmersiveUI(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ClientConfig.SPEC);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
