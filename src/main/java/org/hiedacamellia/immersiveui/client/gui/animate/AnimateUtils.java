package org.hiedacamellia.immersiveui.client.gui.animate;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 动画插值与时间函数工具类。
 */
@OnlyIn(Dist.CLIENT)
public class AnimateUtils {
    /**
     * 常用插值函数集合。
     */
    public static class Lerp {
        /**
         * 线性插值。
         */
        public static double linear(double src, double dest, float time) {
            return src * (1.0F - time) + dest * time;
        }

        /**
         * 平滑插值（加速-减速）。
         */
        public static double smooth(double src, double dest, float time) {
            time = 2 * time - time * time;
            return src * (1.0F - time) + dest * time;
        }

        /**
         * S曲线插值。
         */
        public static double sCurve(double src, double dest, float time) {
            float sqr = time * time;
            time = 3 * sqr - 2 * sqr * time;
            return src * (1.0F - time) + dest * time;
        }

        /**
         * 弹跳插值。
         */
        public static double bounce(double src, double dest, float time) {
            time = 2 * (time - time * time);
            return src * (1.0F - time) + dest * time;
        }
    }

    /**
     * 常用时间变换函数集合。
     */
    public static class Time {
        /**
         * 平滑时间函数。
         */
        public static float smooth(float time) {
            return 2 * time - time * time;
        }

        /**
         * 弹跳时间函数。
         */
        public static float bounce(float time) {
            return 4 * (time - time * time);
        }
    }
}
