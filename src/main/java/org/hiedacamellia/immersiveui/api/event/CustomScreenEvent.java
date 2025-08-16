package org.hiedacamellia.immersiveui.api.event;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.Event;

public class CustomScreenEvent extends Event {

    private Screen screen;

    public CustomScreenEvent(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
