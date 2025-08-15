package org.hiedacamellia.immersiveui.client.animate;

import it.unimi.dsi.fastutil.ints.Int2LongArrayMap;

/**
 * 多动画容器，支持通过id管理多个动画的起始时间和持续时间。
 */
public class MultiAnimateContainer implements IMultiAnimatable {

    private final Int2LongArrayMap animationStartTimes = new Int2LongArrayMap();
    private final Int2LongArrayMap animationDurations = new Int2LongArrayMap();

    /**
     * 获取指定id动画的起始时间。
     */
    @Override
    public long getAnimationStartTime(int id) {
        return animationStartTimes.getOrDefault(id, 0L);
    }

    /**
     * 设置指定id动画的起始时间。
     */
    @Override
    public void setAnimationStartTime(int id, long time) {
        animationStartTimes.put(id, time);
    }

    /**
     * 获取指定id动画的持续时间。
     */
    @Override
    public long getAnimationDuration(int id) {
        return animationDurations.getOrDefault(id, 0L);
    }

    /**
     * 设置指定id动画的持续时间。
     */
    @Override
    public void setAnimationDuration(int id, long duration) {
        animationDurations.put(id, duration);
    }
}
