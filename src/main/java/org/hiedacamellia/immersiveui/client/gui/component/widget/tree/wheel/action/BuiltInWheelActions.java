package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.NeoForge;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.api.event.WheelActionRegiserEvent;

import java.util.function.Consumer;

/**
 * BuiltInWheelActions 是一个包含内置轮盘动作的类。
 * 它提供了预定义的动作，例如键盘输入和关闭屏幕，并将这些动作注册到动作注册表中。
 */
public class BuiltInWheelActions {

    /**
     * 一个 Consumer，用于处理键盘输入动作。
     * 该动作通过调用 KeyInputAction 的 execute 方法实现。
     */
    public static final Consumer<CompoundTag> KEY_INPUT = WheelActionRegistries.register(
            ImmersiveUI.rl("key_input"), KeyInputAction::execute
    );

    /**
     * 一个 Consumer，用于处理关闭屏幕动作。
     * 该动作通过调用 CloseScreenAction 的 execute 方法实现。
     */
    public static final Consumer<CompoundTag> CLOSE_SCREEN = WheelActionRegistries.register(
            ImmersiveUI.rl("close_screen"), CloseScreenAction::execute
    );

    /**
     * 初始化方法，用于触发 WheelActionRegiserEvent 事件。
     * 该事件允许其他模块注册自定义的轮盘动作。
     */
    public static void init() {
        NeoForge.EVENT_BUS.post(new WheelActionRegiserEvent());
    }
}