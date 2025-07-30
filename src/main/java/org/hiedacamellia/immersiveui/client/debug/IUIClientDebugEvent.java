package org.hiedacamellia.immersiveui.client.debug;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.hiedacamellia.immersiveui.client.config.IUIClientConfig;
import org.lwjgl.glfw.GLFW;

/**
 * IUIClientDebugEvent 是一个客户端调试事件类。
 * 它监听键盘输入事件，并在特定按键被按下时打开调试屏幕。
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class IUIClientDebugEvent {

    /**
     * 键盘输入事件的处理方法。
     * 当调试模式启用且按下指定按键时，打开调试屏幕。
     *
     * @param event 键盘输入事件
     */
    @SubscribeEvent
    public static void onKey(final InputEvent.Key event) {

        // 如果调试模式未启用，则直接返回
        if (IUIClientConfig.DEBUG.isFalse()) return;

        // 如果按下的键是 C 键，则打开调试屏幕
        if (event.getKey() == GLFW.GLFW_KEY_C) {
            Minecraft.getInstance().setScreen(new DebugScreen());
        }
    }
}