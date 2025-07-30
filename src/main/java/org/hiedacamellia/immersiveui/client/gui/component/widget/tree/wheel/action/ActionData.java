package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * ActionData 是一个记录类，用于存储与行为相关的数据。
 * 它包含一个资源位置和一个复合标签，用于描述动作的详细信息。
 *
 * @param resourceLocation 行为的资源位置，用于标识行为
 * @param compoundTag      行为的附加数据，以 NBT 格式存储
 */
public record ActionData(ResourceLocation resourceLocation, CompoundTag compoundTag) {
}