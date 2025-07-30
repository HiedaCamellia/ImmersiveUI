package org.hiedacamellia.immersiveui.client.debug;

import net.minecraft.client.gui.screens.Screen;

/**
 * DebugEntry 是一个记录类，用于存储调试信息。
 * 它包含一个模组 ID 和一个子屏幕对象。
 *
 * @param modid     模组的唯一标识符
 * @param subScreen 子屏幕对象，用于显示调试界面
 */
public record DebugEntry(String modid, Screen subScreen) {
}