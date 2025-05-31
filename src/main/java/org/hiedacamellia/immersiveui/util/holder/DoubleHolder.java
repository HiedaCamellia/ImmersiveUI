package org.hiedacamellia.immersiveui.util.holder;

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
