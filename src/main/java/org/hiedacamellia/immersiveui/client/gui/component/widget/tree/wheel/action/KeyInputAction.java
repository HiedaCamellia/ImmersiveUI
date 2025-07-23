package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

public class KeyInputAction {

    public static void execute(CompoundTag tag) {
        InputConstants.Key key = InputConstants.getKey(tag.getString("key"));
        InputConstants.Key modifierKey = InputConstants.getKey(tag.getString("modifierKey"));
        NeoForge.EVENT_BUS.post(new InputEvent.Key(key.getValue(), key.getValue(), InputConstants.PRESS, modifierKey.getValue()));
        NeoForge.EVENT_BUS.post(new InputEvent.Key(key.getValue(), key.getValue(), InputConstants.RELEASE, modifierKey.getValue()));
    }
}
