package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.UUID;

import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_BEGIN_DISTANCE;
import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_DISTANCE;

@OnlyIn(Dist.CLIENT)
public abstract class World2ScreenWidget implements IW2SWidget {
    protected static final ResourceLocation TEXTURE_NORMAL = ImmersiveUI.rl("textures/gui/w2s_button_normal.png");
    protected static final ResourceLocation TEXTURE_HIGHLIGHT = ImmersiveUI.rl("textures/gui/w2s_button_highlight.png");
    protected final Vector3f worldPos = new Vector3f();
    public float xO;
    public float x;
    public float yO;
    public float y;
    public float scale;
    public boolean selectable = false;
    protected boolean limitInScreen = false;
    protected boolean smoothPosition = false;
    private boolean computed = false;
    private boolean inScreen = false;
    protected boolean shouldRemove = false;
    protected float alpha = 0;
    protected UUID uuid;

    protected World2ScreenWidget(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setScreenPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void calculateRenderScale(float distance) {
        this.scale = (float) AnimateUtils.Lerp.smooth(0, 1, 1.0f - Math.max(distance - FADE_BEGIN_DISTANCE, 0) / FADE_DISTANCE);
    }

    public void resize() {}

    public void invoke() {}

    @Override
    public void updateAlpha() {
        if(shouldRemove)
            alpha -= 0.05f;
        else if(alpha < 1.0f)
            alpha += 0.05f;
    }

    @Override
    public boolean shouldBeRemoved() {
        return alpha <= 0.0f;
    }

    @Override
    public boolean shouldRemove() {
        return this.shouldRemove;
    }

    @Override
    public void setRemoved() {
        this.shouldRemove = true;
    }

    public boolean collided(World2ScreenWidget onGrid) {
        return Vector2f.distanceSquared(this.x, this.y, onGrid.x, onGrid.y) < 32 * 32;
    }

    public void moveIfOverlapped(World2ScreenWidget other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        float distance = Mth.sqrt(dx * dx + dy * dy);

        if (distance < 32) {
            float moveDistance = 32 - distance;
            float moveX = (moveDistance / distance) * dx;
            float moveY = (moveDistance / distance) * dy;

            this.x += moveX;
            this.y += moveY;
        }
    }

    @Override
    public void setComputed() {
        this.computed = true;
    }

    @Override
    public boolean isComputed() {
        return computed;
    }

    @Override
    public boolean limitInScreen() {
        return this.limitInScreen;
    }

    @Override
    public boolean shouldRender() {
        return this.inScreen && this.scale > 0f;
    }

    @Override
    public boolean shouldSmoothPosition() {
        return this.smoothPosition;
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    @Override
    public void setInScreen(boolean inScreen) {
        this.inScreen = inScreen;
    }

    @Override
    public boolean isInScreen() {
        return inScreen;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getXO() {
        return xO;
    }

    @Override
    public float getYO() {
        return yO;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setXO(float xO) {
        this.xO = xO;
    }

    @Override
    public void setYO(float yO) {
        this.yO = yO;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }
}
