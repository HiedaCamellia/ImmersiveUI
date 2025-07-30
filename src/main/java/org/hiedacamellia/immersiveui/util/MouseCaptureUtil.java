package org.hiedacamellia.immersiveui.util;

import net.minecraft.client.Minecraft;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;

/**
 * MouseCaptureUtil 是一个便捷的工具类，用于处理没有屏幕的鼠标捕获操作。
 * 该类通常是线程安全的。
 */
public class MouseCaptureUtil {

	/**
	 * 私有构造函数，防止实例化该工具类。
	 */
	private MouseCaptureUtil() {

	}

	/**
	 * 获取当前的鼠标捕获处理器。
	 *
	 * @return 当前的 {@link CapturableMouseHandler} 实例
	 */
	private static CapturableMouseHandler getHandler() {
		return (CapturableMouseHandler) IUIMinecraftUtil.getMouseHandler();
	}

	/**
	 * 检查鼠标是否被捕获。
	 *
	 * @return 如果鼠标被捕获，则返回 true；否则返回 false
	 */
	public static boolean isMouseCaptured() {
		return getHandler().isMouseCaptured();
	}

	/**
	 * 开始鼠标捕获操作。
	 * 鼠标移动将不再影响玩家的相机视角。
	 */
	public static void startMouseCapture() {
		getHandler().setCaptureMouse(true);
	}

	/**
	 * 停止鼠标捕获操作。
	 */
	public static void stopMouseCapture() {
		getHandler().setCaptureMouse(false);
	}

	/**
	 * 获取并重置捕获的鼠标 X 轴位移。
	 * 获取当前和上一次位置之间的 X 轴位移，并将 X 轴位移重置为 0，以便进行下一次捕获。
	 *
	 * @return 捕获的鼠标 X 轴位移
	 */
	public static double getAndResetCapturedDeltaX() {
		return getHandler().getAndResetCapturedX();
	}

	/**
	 * 获取并重置捕获的鼠标 Y 轴位移。
	 * 获取当前和上一次位置之间的 Y 轴位移，并将 Y 轴位移重置为 0，以便进行下一次捕获。
	 *
	 * @return 捕获的鼠标 Y 轴位移
	 */
	public static double getAndResetCapturedDeltaY() {
		return getHandler().getAndResetCapturedY();
	}
}