package org.hiedacamellia.immersiveui.client.debug;


import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

import java.util.ArrayList;
import java.util.List;

public class DebugRegistries {

    public static final DebugRegistries INSTANCE = new DebugRegistries();

    private List<DebugTreeEntryWidget> roots = new ArrayList<>();

    public void registerRoot(DebugTreeEntryWidget root){
        roots.add(root);
    }

    public List<DebugTreeEntryWidget> getRoots(){
        return roots;
    }

}
