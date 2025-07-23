package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

import java.util.UUID;

public class CreateIngameScreenScreen extends Screen {

    private Screen screen;

    public CreateIngameScreenScreen(Screen screen) {
        super(Component.empty());
        this.screen = screen;
    }

    public void init(){
        if(Minecraft.getInstance().level==null){
            onClose();
        }else {
            onClose();
            UUID uuid = UUID.randomUUID();
            World2ScreenScreen world2ScreenScreen = new World2ScreenScreen(uuid, screen, Minecraft.getInstance().player);
            World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, world2ScreenScreen);
            World2ScreenWidgetLayer.INSTANCE.setActiveScreen(world2ScreenScreen);
        }
    }
}
