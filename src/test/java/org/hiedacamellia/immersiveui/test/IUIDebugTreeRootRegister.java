package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.api.event.CollectDebugTreeRootEvent;
import org.hiedacamellia.immersiveui.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.gui.component.fakescreen.CreateScreenLayerScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class IUIDebugTreeRootRegister {

    @SubscribeEvent
    public static void registerTestScreen(CollectDebugTreeRootEvent event) {

        DebugTreeEntryWidget testScreen = DebugTreeEntryWidget.create(new DebugEntry(ImmersiveUI.MODID, new TestScreen()), Component.literal("Test Screen"), Minecraft.getInstance().font);
        DebugTreeEntryWidget testIgScreen = DebugTreeEntryWidget.create(new DebugEntry(ImmersiveUI.MODID, new CreateScreenLayerScreenScreen(new TestScreen())), Component.literal("Ig Fake Screen"), Minecraft.getInstance().font);
        DebugTreeEntryWidget parent = DebugTreeEntryWidget.create(null, Component.literal(ImmersiveUI.MODID), Minecraft.getInstance().font);
        parent.addChild(testScreen);
        parent.addChild(testIgScreen);
        event.registerRoot(parent);

    }
}
