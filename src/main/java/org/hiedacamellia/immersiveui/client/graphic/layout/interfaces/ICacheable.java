package org.hiedacamellia.immersiveui.client.graphic.layout.interfaces;

@SuppressWarnings("unused")
public interface ICacheable {

    void cache(float x,float y);

    boolean shouldCache();

    boolean isCached();

}
