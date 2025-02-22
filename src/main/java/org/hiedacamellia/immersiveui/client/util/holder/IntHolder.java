package org.hiedacamellia.immersiveui.client.util.holder;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IntHolder implements IValueHolder<Integer> {
    private int value;

    public IntHolder(int value) {
        this.value = value;
    }

    @Override
    public void set(Integer value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return value;
    }
}