package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.delay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseBarWidget;

public class DelayBarWidget extends BaseBarWidget {

    protected int delayIncreaseColor = 0xFFFF1111;
    protected int delayDecreaseColor = 0xFF1111FF;
    protected long delay = 500;

    protected float startProgress = 0.0f;
    protected float targetProgress = 0.0f;

    protected long delayStartTime = 0;

    public DelayBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setDelayIncreaseColor(int color) {
        this.delayIncreaseColor = color;
    }

    public void setDelayDecreaseColor(int color) {
        this.delayDecreaseColor = color;
    }

    protected boolean isIncrease() {
        return targetProgress > progress;
    }

    protected boolean isDecrease() {
        return targetProgress < progress;
    }

    @Override
    public void setProgress(float progress) {
        targetProgress = Mth.clamp(progress, 0, 1);
        startProgress = this.progress;
        delayStartTime = System.currentTimeMillis();
    }

    protected void runDelay(){
        long currentTime = System.currentTimeMillis();
        if (delayStartTime == 0) {
            return; // No delay in progress change
        }
        long elapsedTime = currentTime - delayStartTime;
        float progressDelta = (elapsedTime / (float) delay);
        if (isIncrease()) {
            progress = Mth.lerp(progressDelta, startProgress, targetProgress);
            if (progress >= targetProgress) {
                progress = targetProgress;
                delayStartTime = 0; // Reset delay
            }
        } else if (isDecrease()) {
            progress = Mth.lerp(progressDelta, startProgress, targetProgress);
            if (progress <= targetProgress) {
                progress = targetProgress;
                delayStartTime = 0; // Reset delay
            }
        }
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        runDelay();
        super.renderWidget(guiGraphics, i, i1, v);
    }

    @Override
    public void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(isIncrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * targetProgress), width, (height * targetProgress), delayIncreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY(), width, (height * targetProgress), delayIncreaseColor);
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX() + width - (width * targetProgress), getY(), (width * targetProgress), height, delayIncreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY(), (width * targetProgress), height, delayIncreaseColor);
                }
            }
        }
    }

    @Override
    public void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(isDecrease()){
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY() + height - (height * (progress-targetProgress)), width, (height * (progress-targetProgress)), delayDecreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX(), getY()+(height * targetProgress), width, (height * (progress-targetProgress)), delayDecreaseColor);
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.fill(guiGraphics, getX() + width - (width * (progress-targetProgress)), getY(), (width * (progress-targetProgress)), height, delayDecreaseColor);
                } else {
                    IUIGuiUtils.fill(guiGraphics, getX()+(width * targetProgress), getY(), (width * (progress-targetProgress)), height, delayDecreaseColor);
                }
            }
        }
    }
}
