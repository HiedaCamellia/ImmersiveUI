package org.hiedacamellia.immersiveui.client.gui.component.widget;

public abstract class ClickableWidget extends AbstractWidget{

    public ClickableWidget(int length, int width) {
        super(length, width);
    }

    public abstract void click(boolean click);
}
