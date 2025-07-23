package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.WheelTreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.WheelTreeWidget;

import java.util.List;

public abstract class WheelMenuScreen extends Screen {

    private WheelTreeWidget wheelTreeWidget;

    public WheelTreeWidget getTree(){
        return wheelTreeWidget;
    }

    public boolean renderBackground = true;

    public WheelMenuScreen(Component title) {
        super(title);
    }

    @Override
    public void init(){
        super.init();
        wheelTreeWidget = WheelTreeWidget.create(getWheelEntries(),0,0, getTitle(), font);
        addRenderableWidget(wheelTreeWidget);
    }

    public abstract List<? extends WheelTreeEntryWidget> getWheelEntries();

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(renderBackground) super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        else {
            ScreenTempTarget.BLUR_INSTANCE.unbindWrite();
            ScreenTempTarget.SCREEN_INSTANCE.bindWrite(false);
        }
    }
}
