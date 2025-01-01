package org.hiedacamellia.immersiveui.client.graphic.util;

import org.joml.Vector4i;

public class Vec4 extends Vector4i {

    public Vec4(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public Vec4(int value) {
        super(value, value, value, value);
    }

    public Vec4() {
        super(0, 0, 0, 0);
    }

    public int left() {
        return x;
    }

    public int top() {
        return y;
    }

    public int right() {
        return z;
    }

    public int bottom() {
        return w;
    }

    public void left(int left) {
        x = left;
    }

    public void top(int top) {
        y = top;
    }

    public void right(int right) {
        z = right;
    }

    public void bottom(int bottom) {
        w = bottom;
    }




}