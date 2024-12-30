package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;
import org.hiedacamellia.immersiveui.client.gui.layout.ILayoutElement;
import org.hiedacamellia.immersiveui.client.gui.layout.Layout;
import org.hiedacamellia.immersiveui.client.gui.layout.LayoutRenderer;
import org.hiedacamellia.immersiveui.client.gui.layout.WidgetLayout;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Map;

import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_BEGIN_DISTANCE;
import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_DISTANCE;

@OnlyIn(Dist.CLIENT)
public class ScreenComponent {
    protected final Vector3f worldPos = new Vector3f();
    public float x;
    public float y;
    public float scale;
    public boolean selectable = false;
    protected boolean shouldRemove = false;
    protected float alpha = 0;
    protected ILayoutElement layoutElement;
    protected LayoutRenderer layoutRenderer;

    public void render(GuiGraphics guiGraphics, float deltaTicks){
        if(layoutRenderer != null)
            layoutRenderer.render(guiGraphics, deltaTicks);
    }

    public void setLayoutElement(ILayoutElement layoutElement) {
        this.layoutElement = layoutElement;
        this.layoutElement.bind(this);
        this.layoutRenderer = new LayoutRenderer((int)x, (int)y, layoutElement);
    }

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

    public boolean click(float x,float y){
        int collides = layoutElement.collides(x, y);
        Map<Integer, ILayoutElement> objects = layoutElement.getObjects();
        if(objects.get(collides)instanceof WidgetLayout widgetLayout){
            return widgetLayout.click();
        }
        return false;
    }
}
