package org.hiedacamellia.immersiveui.client.animate;

public interface IMultiAnimatable {

    long getAnimationStartTime(int id);

    void setAnimationStartTime(int id,long time);

    long getAnimationDuration(int id);

    void setAnimationDuration(int id,long duration);

    default void runningTime(){

    }

    default long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    default long getEndTimeMillis(int id){
        return getAnimationStartTime(id) + getAnimationDuration(id);
    }

    default long getRemainingTime(int id){
        return Math.max(0, getEndTimeMillis(id) - getCurrentTimeMillis());
    }

    default long getElapsedTime(int id){
        return Math.max(0, getCurrentTimeMillis() - getAnimationStartTime(id));
    }

    default boolean isAnimating(int id){
        return getCurrentTimeMillis() < getEndTimeMillis(id);
    }

    default boolean isAnimationEnd(int id){
        return getCurrentTimeMillis() >= getEndTimeMillis(id);
    }

    default void startAnimation(int id,long duration){
        setAnimationStartTime(id,getCurrentTimeMillis());
        setAnimationDuration(id,duration);
    }

    default void startAnimation(int id){
        setAnimationStartTime(id,getCurrentTimeMillis());
    }
}
