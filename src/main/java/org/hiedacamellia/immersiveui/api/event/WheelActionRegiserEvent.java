package org.hiedacamellia.immersiveui.api.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.WheelActionRegistries;

import java.util.function.Consumer;

/**
 * WheelActionRegiserEvent 是一个事件类，用于注册新的轮盘操作。
 * 它通过提供的注册方法将操作与唯一的资源标识符绑定。
 */
public class WheelActionRegiserEvent extends Event implements IModBusEvent {

    /**
     * 注册一个新的轮盘操作。
     *
     * @param id       资源标识符，用于唯一标识该操作
     * @param consumer 操作的实现逻辑，接受一个 CompoundTag 作为输入
     * @return 返回注册的 Consumer，用于后续操作
     */
    public Consumer<CompoundTag> register(ResourceLocation id, Consumer<CompoundTag> consumer) {
        return WheelActionRegistries.register(id, consumer);
    }
}