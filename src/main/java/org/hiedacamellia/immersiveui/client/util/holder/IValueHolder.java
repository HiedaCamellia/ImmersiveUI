package org.hiedacamellia.immersiveui.client.util.holder;

import java.util.function.Supplier;

public interface IValueHolder<T> extends Supplier<T> {

    void set(T value);

}
