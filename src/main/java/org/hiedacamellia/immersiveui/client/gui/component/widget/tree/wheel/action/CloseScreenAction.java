package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

public class CloseScreenAction {

    public static void execute(CompoundTag tag) {
        if(World2ScreenWidgetLayer.INSTANCE.activeScreen!=null){
            World2ScreenWidgetLayer.INSTANCE.remove(World2ScreenWidgetLayer.INSTANCE.activeScreen.getId());
        }else if(Minecraft.getInstance().screen!=null){
            Minecraft.getInstance().screen.onClose();
        }
    }
}
