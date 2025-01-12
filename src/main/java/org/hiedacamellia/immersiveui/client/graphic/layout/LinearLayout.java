package org.hiedacamellia.immersiveui.client.graphic.layout;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.layout.interfaces.ICacheable;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class LinearLayout extends Layout implements ICacheable {

    private final List<Layout> children;
    private Orientation orientation;

    private List<Vector2f> cachedPositions;
    private Vector2f cachedPosition;

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
    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
        for(Layout layout : children){
            layout.setMaxWidth(maxWidth);
        }
    }
    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
        for (Layout layout : children) {
            layout.setMaxHeight(maxHeight);
        }
    }

    @Override
    public void cache(float x, float y) {
        if(!isCached()){
            cachedPositions.forEach(vector2f -> {
                vector2f.add(cachedPosition.negate(new Vector2f(x,y)));
            });
            for(int i = 0; i < children.size(); i++){
                Layout layout = children.get(i);
                if(layout instanceof ICacheable cacheable){
                    if(cacheable.shouldCache()){
                        Vector2f f = cachedPositions.get(i);
                        cacheable.cache(f.x,f.y);
                    }
                }
            }
            cachedPosition = new Vector2f(x, y);
            return;
        }
        cachedPosition = new Vector2f(x, y);
        cachedPositions = new ArrayList<>();
        float current = switch (this.orientation) {
            case HORIZONTAL -> x;
            case VERTICAL -> y;
        };
        float step = switch (this.orientation) {
            case HORIZONTAL -> (float) getMaxWidth() / children.size();
            case VERTICAL -> (float) getMaxHeight() / children.size();
        };
        for (int i = 0; i < children.size(); i++) {
            Layout layout = children.get(i);
            switch (layout.getAlign()) {
                case LEFT -> {
                    switch (this.orientation) {
                        case HORIZONTAL -> cachedPositions.add(new Vector2f(current, y));
                        case VERTICAL -> cachedPositions.add(new Vector2f(x, current));
                    }
                }
                case CENTER -> {
                    switch (this.orientation) {
                        case HORIZONTAL -> cachedPositions.add(new Vector2f(current - (float) layout.getMaxWidth() / 2, y));
                        case VERTICAL -> cachedPositions.add(new Vector2f(x, current - (float) layout.getMaxHeight() / 2));
                    }
                }
                case RIGHT -> {
                    switch (this.orientation) {
                        case HORIZONTAL -> cachedPositions.add(new Vector2f(current - (float) layout.getMaxWidth(), y));
                        case VERTICAL -> cachedPositions.add(new Vector2f(x, current - (float) layout.getMaxHeight()));
                    }
                }
                case null, default -> cachedPositions.add(new Vector2f());
            }
            current += step;
            if (children.get(i) instanceof ICacheable cacheable) {
                if (cacheable.shouldCache()) {
                    Vector2f f = cachedPositions.get(i);
                    cacheable.cache(f.x, f.y);
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
        return cachedPositions != null;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Layout layout : children) {
            layout.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        //ImmersiveUI.LOGGER.info("rendered");
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
