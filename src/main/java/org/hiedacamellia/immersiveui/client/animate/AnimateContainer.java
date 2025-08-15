package org.hiedacamellia.immersiveui.client.animate;

/**
 * 动画容器，封装动画的起始时间和持续时间，便于统一管理动画状态。
 */
public class AnimateContainer implements IAnimatable {

    private long animationStartTime;
    private long animationDuration;

    /**
     * 构造一个空动画容器，起始时间和持续时间均为0。
     */
    public AnimateContainer(){
        this.animationStartTime = 0;
        this.animationDuration = 0;
    }

    /**
     * 构造一个指定持续时间的动画容器。
     * @param duration 动画持续时间（毫秒）
     */
    public AnimateContainer(long duration) {
        this.animationStartTime = 0;
        this.animationDuration = duration;
    }

    /**
     * 获取动画起始时间。
     */
    @Override
    public long getAnimationStartTime() {
        return animationStartTime;
    }

    /**
     * 设置动画起始时间。
     */
    @Override
    public void setAnimationStartTime(long time) {
        animationStartTime = time;
    }

    /**
     * 获取动画持续时间。
     */
    @Override
    public long getAnimationDuration() {
        return animationDuration;
    }

    /**
     * 设置动画持续时间。
     */
    @Override
    public void setAnimationDuration(long duration) {
        animationDuration = duration;
    }
}
