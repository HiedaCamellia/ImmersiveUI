package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;


import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.NeoForge;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.api.event.WheelActionRegiserEvent;

import java.util.function.Consumer;

public class BuiltInWheelActions {

    public static final Consumer<CompoundTag> KEY_INPUT = WheelActionRegistries.register(ImmersiveUI.rl("key_input"), KeyInputAction::execute);
    public static final Consumer<CompoundTag> CLOSE_SCREEN = WheelActionRegistries.register(ImmersiveUI.rl("close_screen"), CloseScreenAction::execute);

    public static void init() {
        NeoForge.EVENT_BUS.post(new WheelActionRegiserEvent());
    }
}
