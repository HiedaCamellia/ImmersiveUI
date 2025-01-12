package org.hiedacamellia.immersiveui.client.graphic.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.layout.Layout;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ICacheable;

public class LayoutScreen extends Screen {

    protected Layout layout;

    protected LayoutScreen(Component title,Layout layout) {
        super(title);
        this.layout = layout;
    }

    @Override
    protected void init() {
        super.init();
        layout.setMaxHeight(height);
        layout.setMaxWidth(width);
        if(layout instanceof ICacheable){
            ((ICacheable) layout).cache(0,0);
        }
        this.addRenderableWidget(layout);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        layout.render(guiGraphics,mouseX,mouseY,partialTick);
    }
}
