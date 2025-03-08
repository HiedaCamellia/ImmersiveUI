package org.hiedacamellia.immersiveui.client.debug;


import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

import java.util.ArrayList;
import java.util.List;

public class DebugRegistries {

    private static List<DebugTreeEntryWidget> roots = new ArrayList<>();

    public static void registerRoot(DebugTreeEntryWidget root){
        roots.add(root);
    }

    public static List<DebugTreeEntryWidget> getRoots(){
        return roots;
    }

}
