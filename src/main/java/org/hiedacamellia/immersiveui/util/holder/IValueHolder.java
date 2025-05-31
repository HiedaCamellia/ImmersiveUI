package org.hiedacamellia.immersiveui.util.holder;

import java.util.function.Supplier;

public interface IValueHolder<T> extends Supplier<T> {

    void set(T value);

}
