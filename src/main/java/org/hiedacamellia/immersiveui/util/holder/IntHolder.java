package org.hiedacamellia.immersiveui.util.holder;

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