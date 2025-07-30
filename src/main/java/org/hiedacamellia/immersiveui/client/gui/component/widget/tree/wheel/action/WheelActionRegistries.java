package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * WheelActionRegistries 是一个用于管理轮盘行为的注册表类。
 * 它提供了注册和获取行为的方法，通过资源位置标识每个行为。
 */
public class WheelActionRegistries {

    /**
     * 存储已注册行为的映射表。
     * 键为资源位置，值为与该资源位置关联的行为 Consumer。
     */
    private static final Map<ResourceLocation, Consumer<CompoundTag>> REGISTRY = new HashMap<>();

    /**
     * 注册一个新的轮盘行为。
     *
     * @param id       行为的资源位置，用于唯一标识该行为
     * @param consumer 行为的 Consumer，定义了行为的具体行为
     * @return 返回注册的 Consumer 以便进一步使用
     */
    public static Consumer<CompoundTag> register(ResourceLocation id, Consumer<CompoundTag> consumer) {
        REGISTRY.put(id, consumer);
        return consumer;
    }

    /**
     * 根据资源位置获取已注册的轮盘行为。
     *
     * @param id 行为的资源位置
     * @return 返回与资源位置关联的 Consumer，如果未找到则返回 null
     */
    public static Consumer<CompoundTag> get(ResourceLocation id) {
        return REGISTRY.get(id);
    }

}