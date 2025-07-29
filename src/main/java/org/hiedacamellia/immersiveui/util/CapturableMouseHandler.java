package org.hiedacamellia.immersiveui.util;

public interface CapturableMouseHandler {

	boolean isMouseCaptured();

	void setCaptureMouse(boolean captureMouse);

	double getAndResetCapturedX();

	double getAndResetCapturedY();

}