package org.hiedacamellia.immersiveui.client.animate;

import net.minecraft.util.Mth;

/**
 * 动画对象接口，提供动画时间管理和状态判断的通用方法。
 */
public interface IAnimatable {

    /**
     * 获取动画起始时间（毫秒）。
     */
    long getAnimationStartTime();

    /**
     * 设置动画起始时间（毫秒）。
     */
    void setAnimationStartTime(long time);

    /**
     * 获取动画持续时间（毫秒）。
     */
    long getAnimationDuration();

    /**
     * 设置动画持续时间（毫秒）。
     */
    void setAnimationDuration(long duration);

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
     * 获取动画结束时间（毫秒）。
     */
    default long getEndTimeMillis(){
        return getAnimationStartTime() + getAnimationDuration();
    }

    /**
     * 获取动画剩余时间（毫秒）。
     */
    default long getRemainingTime(){
        return Mth.clamp( getEndTimeMillis() - getCurrentTimeMillis(),0, getAnimationDuration());
    }

    /**
     * 获取动画剩余进度比例（0~1）。
     */
    default float getRemainingRatio(){
        long remaining = getRemainingTime();
        return remaining == 0 ? 0 : (float)remaining / getAnimationDuration();
    }

    /**
     * 获取动画已用时间（毫秒）。
     */
    default long getElapsedTime(){
        return Mth.clamp(getCurrentTimeMillis() - getAnimationStartTime(),0, getAnimationDuration());
    }

    /**
     * 获取动画已用进度比例（0~1）。
     */
    default float getElapsedRatio(){
        long elapsed = getElapsedTime();
        return elapsed == 0 ? 0 : (float)elapsed / getAnimationDuration();
    }

    /**
     * 判断动画是否正在进行。
     */
    default boolean isAnimating(){
        return getCurrentTimeMillis() < getEndTimeMillis();
    }

    /**
     * 判断动画是否已结束。
     */
    default boolean isAnimationEnd(){
        return getCurrentTimeMillis() >= getEndTimeMillis();
    }

    /**
     * 以指定持续时间启动动画。
     */
    default void startAnimationDuring(long duration){
        setAnimationStartTime(getCurrentTimeMillis());
        setAnimationDuration(duration);
    }

    /**
     * 延迟指定时间后启动动画。
     */
    default void startAnimationAfter(long after){
        setAnimationStartTime(getCurrentTimeMillis()+after);
    }

    /**
     * 立即启动动画。
     */
    default void startAnimation(){
        setAnimationStartTime(getCurrentTimeMillis());
    }
}
