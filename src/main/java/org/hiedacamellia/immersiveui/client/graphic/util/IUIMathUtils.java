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
     * 生成一个平滑的脉冲曲线。
     * <p>
     * 曲线从 0 开始，在上升阶段平滑升至 1，在保持阶段维持 1，
     * 在下降阶段平滑降至 0。内部使用 {@link #smoothStep(float, float, float)} 实现平滑过渡。
     * </p>
     *
     * @param time         当前时间（>= 0）
     * @param duration     脉冲总时长（> 0）
     * @param riseFraction 上升阶段占总时长的比例（0 ~ 1）
     * @param fallFraction 下降阶段占总时长的比例（0 ~ 1）
     * @return 0~1 之间的曲线值
     */
    public static float smoothPulse(float time, float duration, float riseFraction, float fallFraction) {
        if (time < 0 || duration <= 0) return 0f;

        float riseEnd = duration * riseFraction;
        float fallStart = duration * (1f - fallFraction);

        if (time < riseEnd) {
            return smoothStep(0f, riseEnd, time);
        } else if (time > fallStart) {
            return 1f - smoothStep(fallStart, duration, time);
        } else {
            return 1f;
        }
    }

    /**
     * 生成一个平滑的脉冲曲线。
     * <p>
     * 曲线从 0 开始，在上升阶段平滑升至 1，在保持阶段维持 1，
     * 在下降阶段平滑降至 0。内部使用{@link #smoothStep(double, double, double)}  实现平滑过渡。
     * </p>
     *
     * @param time         当前时间（>= 0）
     * @param duration     脉冲总时长（> 0）
     * @param riseFraction 上升阶段占总时长的比例（0 ~ 1）
     * @param fallFraction 下降阶段占总时长的比例（0 ~ 1）
     * @return 0~1 之间的曲线值
     */
    public static double smoothPulse(double time, double duration, double riseFraction, double fallFraction) {
        if (time < 0 || duration <= 0) return 0d;

        double riseEnd = duration * riseFraction;
        double fallStart = duration * (1d - fallFraction);

        if (time < riseEnd) {
            return smoothStep(0d, riseEnd, time);
        } else if (time > fallStart) {
            return 1d - smoothStep(fallStart, duration, time);
        } else {
            return 1d;
        }
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