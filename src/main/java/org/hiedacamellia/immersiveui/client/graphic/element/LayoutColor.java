package org.hiedacamellia.immersiveui.client.graphic.element;

import org.joml.Vector4f;
import org.joml.Vector4i;

public class LayoutColor extends Vector4i {

    public int r() {
        return x;
    }

    public int g() {
        return y;
    }

    public int b() {
        return z;
    }

    public int a() {
        return w;
    }

    public void r(int red) {
        x = red;
    }

    public void g(int green) {
        y = green;
    }

    public void b(int blue) {
        z = blue;
    }

    public void a(int alpha) {
        w = alpha;
    }

    public LayoutColor(int red, int green, int blue, int alpha) {
        super(red, green, blue, alpha);
    }

    public LayoutColor(int value) {
        super(value, value, value, value);
    }

    public LayoutColor() {
        super(0, 0, 0, 0);
    }

    public static LayoutColor fromRGB(int red, int green, int blue) {
        return new LayoutColor(red, green, blue, 255);
    }

    public static LayoutColor fromRGBA(int red, int green, int blue, int alpha) {
        return new LayoutColor(red, green, blue, alpha);
    }

    public Vector4f toVector4f() {
        return new Vector4f(x / 255.0f, y / 255.0f, z / 255.0f, w / 255.0f);
    }

    public int toRGBInt() {
        return (x << 16) | (y << 8) | z;
    }

    public int toRGBAInt() {
        return (x << 24) | (y << 16) | (z << 8) | w;
    }

}
