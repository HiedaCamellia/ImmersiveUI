package org.hiedacamellia.immersiveui.client.graphic.layout;

import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ICacheable;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class LinearLayout extends Layout implements ICacheable {

    private final List<Layout> children;
    private Orientation orientation;

    private List<Vector2f> cachedPositions;

    public LinearLayout() {
        this.children = new ArrayList<>();
    }

    public void horizontal() {
        this.orientation = Orientation.HORIZONTAL;
    }

    public void vertical() {
        this.orientation = Orientation.VERTICAL;
    }

    public void add(Layout layout) {
        children.add(layout);
    }

    public int size() {
        return children.size();
    }

    public List<Layout> get() {
        return children;
    }

    public Layout get(int index) {
        return children.get(index);
    }

    public void remove(Layout layout) {
        remove(children.indexOf(layout));
    }

    public void remove(int index) {
        children.remove(index);
    }

    public void clear() {
        children.clear();
    }

    @Override
    public void cache(float x,float y) {
        cachedPositions=new ArrayList<>();
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i) instanceof ICacheable cacheable) {
                if(cacheable.shouldCache()) {
                    cacheable.cache(x,y);
                }
            }
        }
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    public boolean isCached() {
        return cachedPositions!=null;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
