package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;
import org.hiedacamellia.immersiveui.client.gui.layout.Layout;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_BEGIN_DISTANCE;
import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_DISTANCE;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenComponent {
    protected final Vector3f worldPos = new Vector3f();
    public float x;
    public float y;
    public float scale;
    public boolean selectable = false;
    protected boolean shouldRemove = false;
    protected float alpha = 0;
    protected Layout layout;

    protected ScreenComponent() {
    }

    public abstract void render(GuiGraphics guiGraphics, float deltaTicks);

    public void setScreenPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void updateAlpha() {
        if(shouldRemove)
            alpha -= 0.05f;
        else if(alpha < 1.0f)
            alpha += 0.05f;
    }

    public boolean shouldBeRemoved() {
        return alpha <= 0.0f;
    }

    public boolean shouldRemove() {
        return this.shouldRemove;
    }

    public void setRemoved() {
        this.shouldRemove = true;
    }

    public boolean shouldSkip() {
        return false;
    }

    public abstract boolean click();
}
