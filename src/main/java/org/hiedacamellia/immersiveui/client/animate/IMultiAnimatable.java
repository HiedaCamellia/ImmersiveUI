package org.hiedacamellia.immersiveui.client.animate;

/**
 * 多动画通道接口，支持通过id管理多个动画的时间与状态。
 */
public interface IMultiAnimatable {

    /**
     * 获取指定id动画的起始时间（毫秒）。
     */
    long getAnimationStartTime(int id);

    /**
     * 设置指定id动画的起始时间（毫秒）。
     */
    void setAnimationStartTime(int id,long time);

    /**
     * 获取指定id动画的持续时间（毫秒）。
     */
    long getAnimationDuration(int id);

    /**
     * 设置指定id动画的持续时间（毫秒）。
     */
    void setAnimationDuration(int id,long duration);

    /**
     * 动画推进时的回调（可选实现）。
     */
    default void runningTime(){

    }

    /**
     * 获取当前系统时间（毫秒）。
     */
    default long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 获取指定id动画的结束时间（毫秒）。
     */
    default long getEndTimeMillis(int id){
        return getAnimationStartTime(id) + getAnimationDuration(id);
    }

    /**
     * 获取指定id动画的剩余时间（毫秒）。
     */
    default long getRemainingTime(int id){
        return Math.max(0, getEndTimeMillis(id) - getCurrentTimeMillis());
    }

    /**
     * 获取指定id动画的已用时间（毫秒）。
     */
    default long getElapsedTime(int id){
        return Math.max(0, getCurrentTimeMillis() - getAnimationStartTime(id));
    }

    /**
     * 判断指定id动画是否正在进行。
     */
    default boolean isAnimating(int id){
        return getCurrentTimeMillis() < getEndTimeMillis(id);
    }

    /**
     * 判断指定id动画是否已结束。
     */
    default boolean isAnimationEnd(int id){
        return getCurrentTimeMillis() >= getEndTimeMillis(id);
    }

    /**
     * 启动指定id的动画，并设置持续时间。
     */
    default void startAnimation(int id,long duration){
        setAnimationStartTime(id,getCurrentTimeMillis());
        setAnimationDuration(id,duration);
    }

    /**
     * 立即启动指定id的动画。
     */
    default void startAnimation(int id){
        setAnimationStartTime(id,getCurrentTimeMillis());
    }
}
