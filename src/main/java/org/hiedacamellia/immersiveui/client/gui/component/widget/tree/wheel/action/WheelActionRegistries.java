package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WheelActionRegistries {

    private static final Map<ResourceLocation, Consumer<CompoundTag>> REGISTRY = new HashMap<>();

    public static Consumer<CompoundTag> register(ResourceLocation id, Consumer<CompoundTag> consumer){
        REGISTRY.put(id, consumer);
        return consumer;
    }

    public static Consumer<CompoundTag> get(ResourceLocation id){
        return REGISTRY.get(id);
    }

}
