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
    //缓存每个子控件的宽度
    private List<Integer> childWidths;
    private boolean built;

    public LinearLayout(int index) {
        super(index);
        this.children = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.flex = false;
        this.built = false;
    }

    public void horizontal() {
        this.orientation = Orientation.HORIZONTAL;
    }

    public void vertical() {
        this.orientation = Orientation.VERTICAL;
    }


    public void flex() {
        this.flex = true;
    }

    @Override
    public void add(ILayoutElement layout) {
        add(layout, 1);
    }

    @Override
    public int size() {
        return children.size();
    }

    @Override
    public ILayoutElement get(int index) {
        return children.get(index);
    }

    public void add(ILayoutElement layout, int weight) {
        this.children.add(layout);
        this.weights.add(weight);
    }


    public void remove(ILayoutElement layout) {
        remove(children.indexOf(layout));
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

    public void add(AbstractWidget widget,int index) {
        children.add(new WidgetLayout(widget,index));
        weights.add(1);
    }

    public void add(AbstractWidget widget, int weight, int index) {
        children.add(new WidgetLayout(widget,index));
        weights.add(weight);
    }

    //计算每个子控件的宽度
    @Override
    public void build() {
        this.children.forEach(ILayoutElement::build);
        switch (orientation) {
            case HORIZONTAL:
                buildHorizontal();
                break;
            case VERTICAL:
                buildVertical();
                break;
        }
        built = true;
    }

    private void buildHorizontal() {
        if (flex) {
            int totalWidth = 0;
            int maxHeight = 0;
            this.childWidths = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                int j = children.get(i).getWidth();
                if (children.get(i).getHeight() > maxHeight) {
                    maxHeight = children.get(i).getHeight();
                }
                totalWidth += j;
                this.childWidths.add(j);
            }
            this.width = totalWidth;
            this.height = maxHeight;
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
            int maxWidth = 0;
            this.childWidths = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                int j = children.get(i).getHeight();
                if (children.get(i).getWidth() > maxWidth) {
                    maxWidth = children.get(i).getWidth();
                }
                totalHeight += j;
                this.childWidths.add(j);
            }
            this.height = totalHeight;
            this.width = maxWidth;
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
    public void render(GuiGraphics guiGraphics, float width, float height) {
        if (!built) {
            build();
        }
        int nx = 0;
        for (int i = 0; i < children.size(); i++) {
            switch (orientation) {
                case HORIZONTAL:
                    children.get(i).position(x+nx, y);
                    children.get(i).render(guiGraphics, childWidths.get(i), height);
                    break;
                case VERTICAL:
                    children.get(i).position(x, y + nx);
                    children.get(i).render(guiGraphics, width, childWidths.get(i));
                    break;
            }
            nx += childWidths.get(i);
        }
    }

    @Override
    public int collides(float x, float y){
        for(int i = 0; i < children.size(); i++){
            if(children.get(i).collides(x,y) != -1){
                return children.get(i).getIndex();
            }
        }
        return -1;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
