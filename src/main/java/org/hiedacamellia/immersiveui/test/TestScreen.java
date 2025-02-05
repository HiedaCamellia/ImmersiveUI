package org.hiedacamellia.immersiveui.test;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;

public class TestScreen extends Screen {
    protected TestScreen() {
        super(Component.empty());
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        RenderSystem.enableBlend();
        guiGraphics.drawString(Minecraft.getInstance().font, "Hello World", 10, 10, 0xFFFFFF);

        RenderSystem.disableBlend();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){

    }
}
