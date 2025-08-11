package org.hiedacamellia.immersiveui.client.animate;

public class AnimateContainer implements IAnimatable {

    private long animationStartTime;
    private long animationDuration;

    public AnimateContainer(){
        this.animationStartTime = 0;
        this.animationDuration = 0;
    }
    public AnimateContainer(long duration) {
        this.animationStartTime = 0;
        this.animationDuration = duration;
    }

    @Override
    public long getAnimationStartTime() {
        return animationStartTime;
    }

    @Override
    public void setAnimationStartTime(long time) {
        animationStartTime = time;
    }

    @Override
    public long getAnimationDuration() {
        return animationDuration;
    }

    @Override
    public void setAnimationDuration(long duration) {
        animationDuration = duration;
    }
}
