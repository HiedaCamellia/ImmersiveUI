package org.hiedacamellia.immersiveui.client.animate;

import net.minecraft.util.Mth;

public interface IAnimatable {

    long getAnimationStartTime();

    void setAnimationStartTime(long time);

    long getAnimationDuration();

    void setAnimationDuration(long duration);

    default void runningTime(){
    }

    default long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    default long getEndTimeMillis(){
        return getAnimationStartTime() + getAnimationDuration();
    }

    default long getRemainingTime(){
        return Mth.clamp( getEndTimeMillis() - getCurrentTimeMillis(),0, getAnimationDuration());
    }

    default float getRemainingRatio(){
        long remaining = getRemainingTime();
        return remaining == 0 ? 0 : (float)remaining / getAnimationDuration();
    }

    default long getElapsedTime(){
        return Mth.clamp(getCurrentTimeMillis() - getAnimationStartTime(),0, getAnimationDuration());
    }

    default float getElapsedRatio(){
        long elapsed = getElapsedTime();
        return elapsed == 0 ? 0 : (float)elapsed / getAnimationDuration();
    }

    default boolean isAnimating(){
        return getCurrentTimeMillis() < getEndTimeMillis();
    }

    default boolean isAnimationEnd(){
        return getCurrentTimeMillis() >= getEndTimeMillis();
    }

    default void startAnimationDuring(long duration){
        setAnimationStartTime(getCurrentTimeMillis());
        setAnimationDuration(duration);
    }

    default void startAnimationAfter(long after){
        setAnimationStartTime(getCurrentTimeMillis()+after);
    }

    default void startAnimation(){
        setAnimationStartTime(getCurrentTimeMillis());
    }
}
