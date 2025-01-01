package org.hiedacamellia.immersiveui.client.graphic.widget;

import org.hiedacamellia.immersiveui.client.graphic.layout.ILayoutElement;

import javax.annotation.Nullable;

public interface IWidget {

    ILayoutElement layout();

    int width();

    int height();

    int x();

    int y();

    void x(int x);

    void y(int y);

    void position(int x,int y);

    @Nullable
    IWidget parent();
}
