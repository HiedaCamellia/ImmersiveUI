package org.hiedacamellia.immersiveui.client.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin implements ILayoutExtension {

    @Unique
    private LayoutLocation immersiveUI$layoutLocation = LayoutLocation.NONE;

    @Unique
    public LayoutLocation getLayoutLocation(){
        return immersiveUI$layoutLocation;
    }

    @Unique
    public void setLayoutLocation(LayoutLocation location){
        this.immersiveUI$layoutLocation = location;
    }
}
