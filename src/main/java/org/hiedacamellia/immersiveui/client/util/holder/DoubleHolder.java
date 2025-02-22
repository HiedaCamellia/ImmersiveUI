package org.hiedacamellia.immersiveui.client.util.holder;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DoubleHolder implements IValueHolder<Double> {
    private double value;

    public DoubleHolder(double value) {
        this.value = value;
    }

    @Override
    public void set(Double value) {
        this.value = value;
    }

    @Override
    public Double get() {
        return value;
    }
}
