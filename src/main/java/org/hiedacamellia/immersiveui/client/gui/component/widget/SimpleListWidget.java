package org.hiedacamellia.immersiveui.client.gui.component.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleListWidget extends ContainerObjectSelectionList<SimpleListWidget.WidgetEntry> {

    public SimpleListWidget(Minecraft client, int width, int height, int l, int m) {
        super(client, width, height, l, m);
        this.centerListVertically = false;
    }

    public int getRowWidth() {
        return 400;
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 32;
    }

    protected static class WidgetEntry extends Entry<WidgetEntry> {

        final List<AbstractWidget> widgets;

        private WidgetEntry(@NotNull List<AbstractWidget> widgets) {

            this.widgets = widgets;
        }

        @Override
        public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.widgets.forEach((widget) -> {
                widget.setY(y);
                widget.render(context, mouseX, mouseY, tickDelta);
            });
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.widgets;
        }
        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.widgets;
        }
    }
}
