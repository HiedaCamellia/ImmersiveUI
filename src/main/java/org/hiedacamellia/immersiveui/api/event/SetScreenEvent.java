package org.hiedacamellia.immersiveui.api.event;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.Event;

public class SetScreenEvent extends Event {
    private final Screen screen;

    public SetScreenEvent(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
