package org.hiedacamellia.immersiveui.client.animate;

import it.unimi.dsi.fastutil.ints.Int2LongArrayMap;

public class MultiAnimateContainer implements IMultiAnimatable {

    private final Int2LongArrayMap animationStartTimes = new Int2LongArrayMap();
    private final Int2LongArrayMap animationDurations = new Int2LongArrayMap();

    @Override
    public long getAnimationStartTime(int id) {
        return animationStartTimes.getOrDefault(id, 0L);
    }

    @Override
    public void setAnimationStartTime(int id, long time) {
        animationStartTimes.put(id, time);
    }

    @Override
    public long getAnimationDuration(int id) {
        return animationDurations.getOrDefault(id, 0L);
    }

    @Override
    public void setAnimationDuration(int id, long duration) {
        animationDurations.put(id, duration);
    }
}
