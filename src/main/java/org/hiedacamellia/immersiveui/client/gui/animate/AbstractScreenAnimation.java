package org.hiedacamellia.immersiveui.client.gui.animate;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 屏幕动画的抽象基类，提供动画时长、启动、tick等通用逻辑。
 */
@OnlyIn(Dist.CLIENT)
public abstract class AbstractScreenAnimation {
    private float length;
    private float begin;
    private boolean running = false;

    /**
     * 构造动画对象。
     * @param length 动画时长
     */
    protected AbstractScreenAnimation(float length) {
        this.length = length;
    }

    /**
     * 启动动画。
     * @param begin 动画起始时间
     */
    public void play(float begin) {
        this.begin = begin;
    }

    /**
     * 每帧调用，驱动动画进度。
     * @param time 当前时间
     * @return 动画是否仍在运行
     */
    public boolean tick(float time) {
        if (time < this.begin)
            return true;

        float delta = time - begin;
        this.run(delta);
        return (this.running = this.length == Float.POSITIVE_INFINITY || !(delta > this.length));
    }

    /**
     * 动画是否可被取消。
     */
    public boolean cancelable() {
        return true;
    }

    /**
     * 动画是否正在运行。
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * 获取动画时长。
     */
    public float getLength() {
        return this.length;
    }

    /**
     * 设置动画时长。
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * 动画帧逻辑，由子类实现。
     * @param time 已经经过的时间
     */
    protected abstract void run(float time);
}
