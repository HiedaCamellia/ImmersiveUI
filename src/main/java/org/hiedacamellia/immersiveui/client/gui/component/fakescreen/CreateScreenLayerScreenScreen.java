package org.hiedacamellia.immersiveui.client.gui.component.fakescreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

import java.util.UUID;

public class CreateScreenLayerScreenScreen extends Screen {

    private Screen screen;

    public CreateScreenLayerScreenScreen(Screen screen) {
        super(Component.empty());
        this.screen = screen;
    }

    public void init(){
        if(Minecraft.getInstance().level==null){
            onClose();
        }else {
            onClose();
            ScreenWidgetLayer.INSTANCE.setScreen(screen);
        }
    }
}
