package org.hiedacamellia.immersiveui.client.graphic.util;

import org.hiedacamellia.immersiveui.client.config.IUIClientConfig;

public class IUIRenderUtils {


    /**
     * 获取 Render 的缩放比例。
     *
     * @return Render 缩放比例
     */
    public static double getRenderScale() {
        return IUIMinecraftUtils.getGuiScale() * IUIClientConfig.RENDER_SCALE.get();
    }

    /**
     * 设置 Render 的缩放比例。
     *
     * @param scaleFactor 缩放比例
     */
    public static void setRenderScale(double scaleFactor) {
        IUIClientConfig.RENDER_SCALE.set(scaleFactor);
        IUIClientConfig.SPEC.save();
    }

    /**
     * 获取 Render 缩放后的宽度。
     *
     * @return Render 缩放后的宽度
     */
    public static int getRenderScaledWidth() {
        return (int) (IUIMinecraftUtils.getGuiScaledWidth() * IUIClientConfig.RENDER_SCALE.get());
    }

    /**
     * 获取 Render 缩放后的高度。
     *
     * @return Render 缩放后的高度
     */
    public static int getRenderScaledHeight() {
        return (int) (IUIMinecraftUtils.getGuiScaledHeight() * IUIClientConfig.RENDER_SCALE.get());
    }

    /**
     * 获取 Render 缩放后的中心 X 坐标。
     *
     * @return Render 缩放后的中心 X 坐标
     */
    public static int getRenderScaledCenterX() {
        return getRenderScaledWidth() / 2;
    }

    /**
     * 获取 Render 缩放后的中心 Y 坐标。
     *
     * @return Render 缩放后的中心 Y 坐标
     */
    public static int getRenderScaledCenterY() {
        return getRenderScaledHeight() / 2;
    }
}
