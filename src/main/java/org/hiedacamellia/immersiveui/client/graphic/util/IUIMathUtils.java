package org.hiedacamellia.immersiveui.client.graphic.util;

public class IUIMathUtils {

    public static float smoothStep(float start,float end,float v){
        v = Math.max(0,Math.min(1,(v-start)/(end-start)));
        return v*v*(3-2*v);
    }

    public static double smoothStep(double start,double end,double v){
        v = Math.max(0,Math.min(1,(v-start)/(end-start)));
        return v*v*(3-2*v);
    }
}
