package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * KeyInputAction 是一个用于模拟键盘输入的行为类。
 * 它提供了一个静态方法 execute，用于根据提供的键和修饰键触发按键事件。
 */
public class KeyInputAction {

    /**
     * 执行键盘输入行为。
     * 根据提供的键和修饰键，依次触发按下和释放事件。
     *
     * @param tag 一个 CompoundTag 对象，包含以下键值：
     *            - "key"：表示要触发的按键（字符串格式）。
     *            - "modifierKey"：表示修饰键（字符串格式）。
     */
    public static void execute(CompoundTag tag) {
        InputConstants.Key key = InputConstants.getKey(tag.getString("key"));
        InputConstants.Key modifierKey = InputConstants.getKey(tag.getString("modifierKey"));
        NeoForge.EVENT_BUS.post(new InputEvent.Key(key.getValue(), key.getValue(), InputConstants.PRESS, modifierKey.getValue()));
        NeoForge.EVENT_BUS.post(new InputEvent.Key(key.getValue(), key.getValue(), InputConstants.RELEASE, modifierKey.getValue()));
    }
}