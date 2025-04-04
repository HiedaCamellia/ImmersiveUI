package org.hiedacamellia.immersiveui.api.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import org.hiedacamellia.immersiveui.client.debug.DebugRegistries;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

import java.util.List;


@OnlyIn(Dist.CLIENT)
public class CollectDebugTreeRootEvent extends Event {

    private final List<DebugTreeEntryWidget> roots;

    public CollectDebugTreeRootEvent(List<DebugTreeEntryWidget> roots) {
        this.roots = roots;
    }

    public void registerRoot(DebugTreeEntryWidget root) {
        roots.add(root);
        DebugRegistries.INSTANCE.registerRoot(root);
    }

    public List<DebugTreeEntryWidget> getRoots() {
        return roots;
    }
}
