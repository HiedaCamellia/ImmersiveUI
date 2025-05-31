package org.hiedacamellia.immersiveui.util.holder;

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