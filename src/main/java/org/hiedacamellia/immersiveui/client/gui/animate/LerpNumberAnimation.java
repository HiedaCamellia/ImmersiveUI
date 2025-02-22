package org.hiedacamellia.immersiveui.client.gui.animate;

import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.util.holder.IValueHolder;

@OnlyIn(Dist.CLIENT)
public class LerpNumberAnimation extends AbstractScreenAnimation {
    private final IValueHolder<Float> holder;
    private final LerpFunction lerp;
    private final boolean cancelable;
    private double src;
    private double dest;

    public LerpNumberAnimation(IValueHolder<Float> holder, LerpFunction lerp, double src, double dest, float length) {
        super(length);
        this.holder = holder;
        this.lerp = lerp;
        this.src = src;
        this.dest = dest;
        this.cancelable = true;
    }

    public LerpNumberAnimation(IValueHolder<Float> holder, LerpFunction lerp, double src, double dest, float length, boolean cancelable) {
        super(length);
        this.holder = holder;
        this.lerp = lerp;
        this.src = src;
        this.dest = dest;
        this.cancelable = cancelable;
    }

    public void reset(double src, double dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    protected void run(float time) {
        this.holder.set((float)lerp.apply(this.src, this.dest, Mth.clamp(time / getLength(), 0, 1)));
    }

    @Override
    public boolean cancelable() {
        return this.cancelable;
    }

    public double getSrc() {
        return this.src;
    }

    public double getDest() {
        return this.dest;
    }

    @FunctionalInterface
    public interface LerpFunction {
        double apply(double src, double dest, float time);
    }
}
