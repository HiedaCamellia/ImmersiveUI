package org.hiedacamellia.immersiveui;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value = ImmersiveUI.MODID,dist = Dist.DEDICATED_SERVER)
public class ImmersiveUI {
    public static final String MODID = "immersiveui";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ImmersiveUI(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
