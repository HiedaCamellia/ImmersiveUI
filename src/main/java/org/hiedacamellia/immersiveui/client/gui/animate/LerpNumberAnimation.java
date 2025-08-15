package org.hiedacamellia.immersiveui.client.gui.animate;

import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.util.holder.IValueHolder;

/**
 * 用于对数值进行插值动画的类，支持自定义插值函数。
 */
@OnlyIn(Dist.CLIENT)
public class LerpNumberAnimation extends AbstractScreenAnimation {
    private final IValueHolder<Float> holder;
    private final LerpFunction lerp;
    private final boolean cancelable;
    private double src;
    private double dest;

    /**
     * 构造一个可取消的插值动画。
     * @param holder 数值持有者
     * @param lerp 插值函数
     * @param src 起始值
     * @param dest 目标值
     * @param length 动画时长
     */
    public LerpNumberAnimation(IValueHolder<Float> holder, LerpFunction lerp, double src, double dest, float length) {
        super(length);
        this.holder = holder;
        this.lerp = lerp;
        this.src = src;
        this.dest = dest;
        this.cancelable = true;
    }

    /**
     * 构造一个插值动画，可指定是否可取消。
     * @param holder 数值持有者
     * @param lerp 插值函数
     * @param src 起始值
     * @param dest 目标值
     * @param length 动画时长
     * @param cancelable 是否可取消
     */
    public LerpNumberAnimation(IValueHolder<Float> holder, LerpFunction lerp, double src, double dest, float length, boolean cancelable) {
        super(length);
        this.holder = holder;
        this.lerp = lerp;
        this.src = src;
        this.dest = dest;
        this.cancelable = cancelable;
    }

    /**
     * 重置起始值和目标值。
     */
    public void reset(double src, double dest) {
        this.src = src;
        this.dest = dest;
    }

    /**
     * 动画帧逻辑，更新数值。
     */
    @Override
    protected void run(float time) {
        this.holder.set((float)lerp.apply(this.src, this.dest, Mth.clamp(time / getLength(), 0, 1)));
    }

    /**
     * 是否可取消。
     */
    @Override
    public boolean cancelable() {
        return this.cancelable;
    }

    /**
     * 获取起始值。
     */
    public double getSrc() {
        return this.src;
    }

    /**
     * 获取目标值。
     */
    public double getDest() {
        return this.dest;
    }

    /**
     * 插值函数接口。
     */
    @FunctionalInterface
    public interface LerpFunction {
        double apply(double src, double dest, float time);
    }
}
