package org.hiedacamellia.immersiveui.client.gui.component.widget;

public abstract class ClickableWidget extends AbstractWidget{

    public ClickableWidget(int height, int width) {
        super(height, width);
    }

    public abstract void click(boolean click);

    public abstract boolean click();
}
