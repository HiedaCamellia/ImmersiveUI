package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

/**
 * CloseScreenAction 是一个用于关闭当前屏幕的行为类。
 * 它提供了一个静态方法 execute，用于根据当前屏幕状态执行关闭操作。
 */
public class CloseScreenAction {

    /**
     * 执行关闭屏幕的行为。
     * 如果 World2ScreenWidgetLayer 中有活动屏幕，则移除该屏幕；
     * 否则，如果 Minecraft 当前有打开的屏幕，则调用其 onClose 方法关闭屏幕。
     *
     * @param tag 一个 CompoundTag 对象，包含与行为相关的附加数据（当前未使用）。
     */
    public static void execute(CompoundTag tag) {
        if (World2ScreenWidgetLayer.INSTANCE.hasScreen()) {
            // 移除 World2ScreenWidgetLayer 中的活动屏幕
            World2ScreenWidgetLayer.INSTANCE.remove(World2ScreenWidgetLayer.INSTANCE.activeScreen.getId());
        } else if (Minecraft.getInstance().screen != null) {
            // 关闭 Minecraft 当前的屏幕
            Minecraft.getInstance().screen.onClose();
        } else if (ScreenWidgetLayer.INSTANCE.hasScreen()){
            // 如果 ScreenWidgetLayer 中有屏幕，则移除它
            ScreenWidgetLayer.INSTANCE.removeScreen();
        }
    }
}