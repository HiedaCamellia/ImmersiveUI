package org.hiedacamellia.immersiveui.util;

/**
 * CapturableMouseHandler 是一个接口，用于处理可捕获的鼠标操作。
 * 它提供了检查鼠标捕获状态、设置鼠标捕获以及获取捕获的鼠标位移的方法。
 */
public interface CapturableMouseHandler {

	/**
	 * 检查鼠标是否被捕获。
	 *
	 * @return 如果鼠标被捕获，则返回 true；否则返回 false
	 */
	boolean isMouseCaptured();

	/**
	 * 设置鼠标捕获状态。
	 *
	 * @param captureMouse 如果为 true，则捕获鼠标；否则释放鼠标
	 */
	void setCaptureMouse(boolean captureMouse);

	/**
	 * 获取并重置捕获的鼠标 X 轴位移。
	 *
	 * @return 捕获的鼠标 X 轴位移
	 */
	double getAndResetCapturedX();

	/**
	 * 获取并重置捕获的鼠标 Y 轴位移。
	 *
	 * @return 捕获的鼠标 Y 轴位移
	 */
	double getAndResetCapturedY();

}