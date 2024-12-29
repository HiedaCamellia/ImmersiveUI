package org.hiedacamellia.immersiveui.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.gui.component.widget.AbstractWidget;

import java.util.ArrayList;
import java.util.List;

public class LinearLayout extends Layout {

    private final List<ILayoutElement> children;
    private final List<Integer> weights;

    private Orientation orientation;
    private boolean flex;

    private int width;
    private int height;
    //缓存每个子控件的宽度
    private List<Integer> childWidths;
    private boolean built;

    public LinearLayout() {
        children = new ArrayList<>();
        weights = new ArrayList<>();
        this.flex = false;
        this.built = false;
    }

    public void horizontal() {
        this.orientation = Orientation.HORIZONTAL;
    }

    public void vertical() {
        this.orientation = Orientation.VERTICAL;
    }

    public void size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void flex() {
        this.flex = true;
    }

    @Override
    public void add(ILayoutElement layout) {
        children.add(layout);
        weights.add(1);
    }

    public void add(ILayoutElement layout, int weight) {
        children.add(layout);
        weights.add(weight);
    }

    public void remove(ILayoutElement layout) {
        weights.remove(children.indexOf(layout));
        children.remove(layout);
    }

    public void remove(int index) {
        weights.remove(index);
        children.remove(index);
    }

    public void clear() {
        children.clear();
        weights.clear();
    }

    public List<ILayoutElement> getChildren() {
        return children;
    }

    public List<Integer> getWeights() {
        return weights;
    }

    public void add(AbstractWidget widget) {
        children.add(new WidgetLayout(widget));
        weights.add(1);
    }

    public void add(AbstractWidget widget, int weight) {
        children.add(new WidgetLayout(widget));
        weights.add(weight);
    }

    //计算每个子控件的宽度
    @Override
    public void build() {
        switch (orientation) {
            case HORIZONTAL:
                buildHorizontal();
                break;
            case VERTICAL:
                buildVertical();
                break;
        }
        this.children.forEach(ILayoutElement::build);
        built = true;
    }

    private void buildHorizontal() {
        if (flex) {
            int totalWidth = 0;
            this.childWidths = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                int j = children.get(i).getWidth();
                totalWidth += j;
                this.childWidths.add(j);
            }
            this.width = totalWidth;
        } else {
            childWidths = new ArrayList<>();
            int totalWeight = 0;
            for (int i = 0; i < children.size(); i++) {
                totalWeight += weights.get(i);
            }
            for (int i = 0; i < children.size(); i++) {
                childWidths.add(width * weights.get(i) / totalWeight);
            }
        }
    }

    private void buildVertical() {
        if (flex) {
            int totalHeight = 0;
            this.childWidths = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                int j = children.get(i).getHeight();
                totalHeight += j;
                this.childWidths.add(j);
            }
            this.height = totalHeight;
        } else {
            childWidths = new ArrayList<>();
            int totalWeight = 0;
            for (int i = 0; i < children.size(); i++) {
                totalWeight += weights.get(i);
            }
            for (int i = 0; i < children.size(); i++) {
                childWidths.add(height * weights.get(i) / totalWeight);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, float x, float y, float width, float height) {
        if (!built) {
            build();
        }
        int nx = 0;
        for (int i = 0; i < children.size(); i++) {
            switch (orientation) {
                case HORIZONTAL:
                    children.get(i).render(guiGraphics, x + nx, y, childWidths.get(i), height);
                    break;
                case VERTICAL:
                    children.get(i).render(guiGraphics, x, y + nx, width, childWidths.get(i));
                    break;
            }
            nx += childWidths.get(i);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
