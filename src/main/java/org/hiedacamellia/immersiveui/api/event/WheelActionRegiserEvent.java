package org.hiedacamellia.immersiveui.api.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.WheelActionRegistries;

import java.util.function.Consumer;

public class WheelActionRegiserEvent extends Event {

    public Consumer<CompoundTag> register(ResourceLocation id, Consumer<CompoundTag> consumer){
        return WheelActionRegistries.register(id, consumer);
    }
}
