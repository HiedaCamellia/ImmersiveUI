package org.hiedacamellia.immersiveui.client.util.holder;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FloatHolder implements IValueHolder<Float> {
    private float value;

    public FloatHolder(float value) {
        this.value = value;
    }

    @Override
    public void set(Float value) {
        this.value = value;
    }

    @Override
    public Float get() {
        return value;
    }
}