package org.hiedacamellia.immersiveui.client.graphic.util;

import org.joml.Vector4f;

public class IUIMathUtils {

    public static float smoothStep(float start,float end,float v){
        v = Math.max(0,Math.min(1,(v-start)/(end-start)));
        return v*v*(3-2*v);
    }

    public static double smoothStep(double start,double end,double v){
        v = Math.max(0,Math.min(1,(v-start)/(end-start)));
        return v*v*(3-2*v);
    }

    public static Vector4f int2RGBA(int color) {
        return new Vector4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >> 24 & 255) / 255.0F);
    }

    public static Vector4f int2ARGB(int color) {
        return new Vector4f((color >> 24 & 255) / 255.0F, (color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F);
    }
}
