package org.hiedacamellia.immersiveui.client.gui.component.widget.bar.delay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseTexBarWidget;

public class DelayTexBarWidget extends BaseTexBarWidget {


    protected ResourceLocation delayIncreaseTex = null;
    protected ResourceLocation delayDecreaseTex = null;
    protected long delay = 500;

    protected float startProgress = 0.0f;
    protected float targetProgress = 0.0f;

    protected long delayStartTime = 0;

    public DelayTexBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }


    @Override
    public void setTex(ResourceLocation tex) {
        super.setTex(tex);
        String namespace = tex.getNamespace();
        String path = tex.getPath();
        String replace = path.replace(".png", "");
        setDelayIncreaseTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar_increase.png"));
        setDelayDecreaseTex(ResourceLocation.fromNamespaceAndPath(namespace, replace + "_bar_decrease.png"));
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setDelayIncreaseTex(ResourceLocation tex) {
        this.delayIncreaseTex = tex;
    }

    public void setDelayDecreaseTex(ResourceLocation tex) {
        this.delayDecreaseTex = tex;
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

    protected void runDelay() {
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
        if (delayIncreaseTex != null && isIncrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), (getY() + height - (height * targetProgress)), width, (height * targetProgress), 0, 0, getBarTexWidth(), (getBarTexHeight() * targetProgress), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), getY(), width, (height * targetProgress), 0, 0, getBarTexWidth(), (getBarTexHeight() * targetProgress), getBarTexWidth(), getBarTexHeight());
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, (getX() + width - (width * targetProgress)), getY(), (width * targetProgress), height, width - (width * targetProgress), 0, (getBarTexWidth() * targetProgress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayIncreaseTex, getX(), getY(), (width * targetProgress), height, 0, 0, (getBarTexWidth() * targetProgress), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                }
            }
        }
    }

    @Override
    public void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (delayDecreaseTex != null && isDecrease()) {
            if (isVertical) {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, getX(), (getY() + height - (height * progress)), width, (height * (progress - targetProgress)), 0, 0, getBarTexWidth(), (getBarTexHeight() * (progress - targetProgress)), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, getX(), getY() + (height * targetProgress), width, (height * (progress - targetProgress)), 0, 0, getBarTexWidth(), (getBarTexHeight() * (progress - targetProgress)), getBarTexWidth(), getBarTexHeight());
                }
            } else {
                if (isReverse) {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, (getX() + width - (width * progress)), getY(), (width * (progress - targetProgress)), height, width - (width * targetProgress), 0, (getBarTexWidth() * (progress - targetProgress)), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                } else {
                    IUIGuiUtils.blit(guiGraphics, delayDecreaseTex, (getX() + (width * targetProgress)), getY(), (width * (progress - targetProgress)), height, 0, 0, (getBarTexWidth() * (progress - targetProgress)), getBarTexHeight(), getBarTexWidth(), getBarTexHeight());
                }
            }
        }
    }
}
