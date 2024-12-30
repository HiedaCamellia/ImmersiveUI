package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;
import org.hiedacamellia.immersiveui.client.gui.component.screen.ScreenComponent;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_BEGIN_DISTANCE;
import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_DISTANCE;

@OnlyIn(Dist.CLIENT)
public abstract class World2ScreenComponent extends World2ScreenWidget {

    protected ScreenComponent screenComponent;

    protected World2ScreenComponent(ScreenComponent screenComponent) {
        this.screenComponent = screenComponent;
    }

    public void render(GuiGraphics guiGraphics, boolean highlight, float value, float deltaTicks){
        if(screenComponent != null) {
            screenComponent.setScreenPos(x, y);
            screenComponent.render(guiGraphics, deltaTicks);
        }
    }

    public abstract void getWorldPos(Vector3f out);

    public abstract boolean click();
}
