package org.hiedacamellia.immersiveui.client.graphic.layout.interfaces;

public interface IWidget extends IRenderable {

    int width();

    int height();

    int x();

    int y();

    void x(int x);

    void y(int y);

    void position(int x,int y);

}
