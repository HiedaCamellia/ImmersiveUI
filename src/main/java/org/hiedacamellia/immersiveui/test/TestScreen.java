package org.hiedacamellia.immersiveui.test;

import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutColor;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.layout.LinearLayout;
import org.hiedacamellia.immersiveui.client.graphic.layout.widget.ComponentWidget;
import org.hiedacamellia.immersiveui.client.graphic.screen.LayoutScreen;

public class TestScreen extends LayoutScreen {

    protected static LinearLayout layout = new LinearLayout();
    protected static ComponentWidget widget = new ComponentWidget(Component.literal("04w5g65th45wh654e64w5h65ey56nj45wh65nj56ret6e555t4rehry65t4huj65yw45muyje4h53hh5y7h45jr5he54wehw5"));

    protected static ComponentWidget widget2 = new ComponentWidget(Component.literal("123123"));
    static {
        layout.vertical();
        widget.setBackgroundColor(new LayoutColor(255,255,255,255));
        widget.setMargin(new LayoutElements.Margin(100,50,100,50));
        widget2.setBackgroundColor(new LayoutColor(255,255,255,255));
        widget2.setMargin(new LayoutElements.Margin(50,0,50,0));
        layout.add(widget);
        layout.add(widget2);

    }




    public TestScreen() {
        super(Component.empty(), layout);
    }

}
