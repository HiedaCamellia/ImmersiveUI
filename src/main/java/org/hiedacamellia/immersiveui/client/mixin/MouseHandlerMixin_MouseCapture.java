package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.hiedacamellia.immersiveui.util.CapturableMouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin_MouseCapture implements CapturableMouseHandler {
	@Final
	@Shadow
	private Minecraft minecraft;
	@Shadow
	private double xpos;
	@Shadow
	private double ypos;
	@Shadow
	private double accumulatedDX;
	@Shadow
	private double accumulatedDY;

	private boolean captureMouse;
	private double capturedX;
	private double capturedY;
	@Override
	public boolean isMouseCaptured() {
		return captureMouse;
	}
	@Override
	public void setCaptureMouse(boolean captureMouse) {
		this.captureMouse = captureMouse;
		capturedX=0;
		capturedY=0;
	}

	
	@Override
	public double getAndResetCapturedX() {
		double cx=capturedX;
		capturedX-=cx;
		return cx;
	}
	@Override
	public double getAndResetCapturedY() {
		double cx=capturedY;
		capturedY-=cx;
		return cx;
	}
	public MouseHandlerMixin_MouseCapture() {

	}
	@Shadow
	public abstract boolean isMouseGrabbed();

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;turnPlayer(D)V"), method = "handleAccumulatedMovement()V", require = 1)
	private void fh$captureMouse(CallbackInfo ci) {
		if(isMouseCaptured()) {
			if (this.isMouseGrabbed() && this.minecraft.isWindowActive()) {
				this.capturedX+=this.accumulatedDX;
				this.capturedY+=this.accumulatedDY;
				this.accumulatedDX=0;
				this.accumulatedDY=0;
			}
		}
	}
}
