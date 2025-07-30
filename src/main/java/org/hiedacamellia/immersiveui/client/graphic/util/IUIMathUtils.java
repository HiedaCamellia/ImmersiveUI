package org.hiedacamellia.immersiveui.client.graphic.util;

import org.joml.Vector4f;

/**
 * IUIMathUtils 是一个数学工具类
 */
public class IUIMathUtils {

    /**
     * 使用 smoothstep 算法对给定的浮点值进行平滑插值。
     *
     * @param start 插值的起始值
     * @param end   插值的结束值
     * @param v     当前值
     * @return 平滑插值后的值
     */
    public static float smoothStep(float start, float end, float v) {
        v = Math.max(0, Math.min(1, (v - start) / (end - start)));
        return v * v * (3 - 2 * v);
    }

    /**
     * 使用 smoothstep 算法对给定的双精度值进行平滑插值。
     *
     * @param start 插值的起始值
     * @param end   插值的结束值
     * @param v     当前值
     * @return 平滑插值后的值
     */
    public static double smoothStep(double start, double end, double v) {
        v = Math.max(0, Math.min(1, (v - start) / (end - start)));
        return v * v * (3 - 2 * v);
    }

    /**
     * 将整数颜色值转换为 RGBA 格式的向量。
     *
     * @param color 整数颜色值
     * @return 表示 RGBA 值的 Vector4f 对象
     */
    public static Vector4f int2RGBA(int color) {
        return new Vector4f(
                (color >> 16 & 255) / 255.0F,
                (color >> 8 & 255) / 255.0F,
                (color & 255) / 255.0F,
                (color >> 24 & 255) / 255.0F
        );
    }

    /**
     * 将整数颜色值转换为 ARGB 格式的向量。
     *
     * @param color 整数颜色值
     * @return 表示 ARGB 值的 Vector4f 对象
     */
    public static Vector4f int2ARGB(int color) {
        return new Vector4f(
                (color >> 24 & 255) / 255.0F,
                (color >> 16 & 255) / 255.0F,
                (color >> 8 & 255) / 255.0F,
                (color & 255) / 255.0F
        );
    }
}