/*
 * Code from https://github.com/LouisQuepierts/ThatSkyInteractions
 * net.quepierts.thatskyinteractions.client.gui.layer
 * World2ScreenWidgetLayer.java
 *
 * This code is under the MIT License.
 */

package org.hiedacamellia.immersiveui.client.gui.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;
import org.hiedacamellia.immersiveui.client.gui.animate.LerpNumberAnimation;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.W2SWidget;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenWidget;
import org.hiedacamellia.immersiveui.client.mixin.accessor.GameRendererAccessor;
import org.hiedacamellia.immersiveui.client.util.holder.FloatHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class World2ScreenWidgetLayer implements LayeredDraw.Layer {
    public static final World2ScreenWidgetLayer INSTANCE = new World2ScreenWidgetLayer();
    public static final ResourceLocation LOCATION = ImmersiveUI.rl("world_screen_grid");
    public static final int FADE_BEGIN_DISTANCE = 32 * 32;
    public static final int FADE_DISTANCE = 8;

    private final Minecraft minecraft = Minecraft.getInstance();
    private final Map<UUID, W2SWidget> objects = new Object2ObjectOpenHashMap<>();
    private final Map<UUID, W2SWidget> onRemoving = new Object2ObjectOpenHashMap<>();
    private final Set<UUID> toRemove = new ObjectOpenHashSet<>();
    private final List<W2SWidget> inRange = new ObjectArrayList<>();
    //private final World2ScreenButton[] grid = new World2ScreenButton[64 * 64];
    private final FloatHolder click = new FloatHolder(0.0f);
    private final LerpNumberAnimation animation = new LerpNumberAnimation(this.click, AnimateUtils.Lerp::smooth, 0, 1, 0.5f);

    private W2SWidget highlight;
    private W2SWidget locked;

    public World2ScreenScreen activeScreen;
    public UUID screenUUID;

    private double scroll = 0;
    World2ScreenWidgetLayer() {
        reset();
    }

    public void reset() {
        objects.clear();
        inRange.clear();
        highlight = null;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;

        float deltaTicks = deltaTracker.getGameTimeDeltaTicks();
        update(deltaTicks);

        //Arrays.fill(grid, null);
        for (Iterator<W2SWidget> iterator = objects.values().iterator(); iterator.hasNext(); ) {
            W2SWidget object = iterator.next();
            if (!object.isComputed())
                continue;

            if (!object.shouldRender())
                continue;

            object.updateAlpha();
            boolean highlight1 = locked != null ? object == locked : object == highlight;
            boolean shouldRemove = object.shouldRemove();
            if (shouldRemove && (!highlight1 || !this.animation.isRunning())) {
                if (object == locked) {
                    locked = null;
                }
                iterator.remove();
                continue;
            }
            float d0 = object.getX();
            float d1 = object.getY();

            if (object.shouldSmoothPosition()) {
                d0 = Mth.lerp(deltaTicks, object.getXO(), object.getX());
                d1 = Mth.lerp(deltaTicks, object.getYO(), object.getY());
            }

            object.setXO(d0);
            object.setYO(d1);

            if (highlight1) {
                object.render(guiGraphics, true, this.click.getValue(), deltaTicks);
            } else {
                object.render(guiGraphics, false, 0, deltaTicks);
            }
        }

        for (Iterator<W2SWidget> iterator = onRemoving.values().iterator(); iterator.hasNext(); ) {
            W2SWidget object = iterator.next();
            object.updateAlpha();
            if (object.shouldBeRemoved()) {
                iterator.remove();
                continue;
            }

            if (!object.isComputed())
                continue;

            if (!object.shouldRender())
                continue;


            boolean highlight1 = locked != null ? object == locked : object == highlight;
            float d0 = object.getX();
            float d1 = object.getY();

            if (object.shouldSmoothPosition()) {
                d0 = Mth.lerp(deltaTicks, object.getXO(), object.getX());
                d1 = Mth.lerp(deltaTicks, object.getYO(), object.getY());
            }

            object.setXO(d0);
            object.setYO(d1);

            if (highlight1) {
                object.render(guiGraphics, true, this.click.getValue(), deltaTicks);
            } else {
                object.render(guiGraphics, false, 0, deltaTicks);
            }
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }

    public void update(float deltaTicks) {
        if (this.minecraft.level == null)
            return;

        if (!this.toRemove.isEmpty()) {
            this.toRemove.forEach(e->{
                W2SWidget widget = this.objects.get(e);
                if (widget == null)
                    return;
                widget.setRemoved();
                this.onRemoving.put(e, widget);
            });
            this.toRemove.clear();
        }

        final GameRenderer gameRenderer = this.minecraft.gameRenderer;
        final Camera camera = gameRenderer.getMainCamera();
        final Vec3 cameraPos = camera.getPosition();

        final double fov = ((GameRendererAccessor) gameRenderer).tsi$getFov(camera, deltaTicks, true);
        final Matrix4f projectionMatrix = gameRenderer.getProjectionMatrix(fov);

        final Matrix4f viewMatrix = new Matrix4f()
                .rotation(camera.rotation().conjugate(new Quaternionf()))
                .translate((float) -cameraPos.x, (float) -cameraPos.y, (float) -cameraPos.z);

        final Matrix4f mat = new Matrix4f().mul(projectionMatrix).mul(viewMatrix);
        final int screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        final int screenHeight = this.minecraft.getWindow().getGuiScaledHeight();

        final float half = screenWidth / 2f;
        final float left = half - 32;
        final float right = half + 32;

        inRange.clear();
        //Arrays.fill(grid, null);

        final Vector3f pos = new Vector3f();
        for (W2SWidget object : objects.values()) {
            if (object.shouldSkip())
                continue;

            object.setComputed();
            object.getWorldPos(pos);

            Vector4f cameraSpacePos = new Vector4f(pos, 1.0f)
                    .mul(mat);

            if (cameraSpacePos.w < 0.0f) {
                cameraSpacePos.y = screenHeight;
                cameraSpacePos.x = -cameraSpacePos.x;
            }

            float x = (int) ((cameraSpacePos.x() / cameraSpacePos.z() * 0.5F + 0.5F) * screenWidth);
            float y = (int) ((1.0F - (cameraSpacePos.y() / cameraSpacePos.z() * 0.5F + 0.5F)) * screenHeight);

            if (object.limitInScreen()) {
                x = Mth.clamp(x, 16, screenWidth - 16);
                y = Mth.clamp(y, 16, screenHeight - 16);
            }

            object.setInScreen(
                    x > 0 && y > 0 && x < screenWidth && y < screenHeight
            );

            object.setScreenPos(x, y);

            //tryMove(getGridPosition(object.x, object.y), object);
            //apply(getGridPosition(object.x, object.y), object);

            float distance = Vector3f.distanceSquared(pos.x, pos.y, pos.z, (float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z);
            object.calculateRenderScale(distance);
            if (object.getScale() < 1.0f)
                continue;

            if (locked == null && object.getX() > left && object.getX() < right && object.isSelectable()) {
                inRange.add(object);
            }
        }

        if (!this.animation.isRunning()) {
            if (!inRange.isEmpty()) {
                this.highlight = inRange.get((int) scroll % inRange.size());
            } else {
                this.highlight = null;
            }
        }
    }

    public void addWorldPositionObject(UUID uuid, World2ScreenWidget widget) {
        if (widget == null) {
            this.toRemove.add(uuid);
            return;
        }
//        if (ThatSkyInteractions.getInstance().getClient().blocked(uuid))
//            return;
        this.objects.put(uuid, widget);
    }

    public void addWorldPositionObjectForced(UUID uuid, World2ScreenWidget widget) {
        this.objects.put(uuid, widget);
    }

    public void remove(UUID other) {
        this.toRemove.add(other);
    }

    public void scroll(double mouseY) {
        if (this.inRange.isEmpty()) {
            this.scroll = 0;
        } else {
            this.scroll = (this.scroll + mouseY) % this.inRange.size();
        }
    }

    public boolean click(int button) {
        if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return false;

        boolean consumed = false;
        for (Iterator<W2SWidget> iterator = objects.values().iterator(); iterator.hasNext(); ) {
            W2SWidget object = iterator.next();

            if(object.click(button)){
                consumed = true;
            }

        }

        if(!consumed){
            this.activeScreen = null;
            this.screenUUID = null;
        }

        return consumed;
    }

    public void lock(World2ScreenWidget locked) {
        this.locked = locked;
    }
}
