package org.hiedacamellia.immersiveui.util.holder;

import java.util.function.Supplier;

/**
 * IValueHolder 是一个通用接口，用于持有和提供值。
 * 它扩展了 {@link Supplier} 接口，允许获取值，并提供了设置值的方法。
 *
 * @param <T> 持有的值的类型
 */
public interface IValueHolder<T> extends Supplier<T> {

    /**
     * 设置持有的值。
     *
     * @param value 要设置的值
     */
    void set(T value);

}