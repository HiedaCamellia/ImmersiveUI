package org.hiedacamellia.immersiveui.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;
import java.util.Map;

public abstract class Layout implements ILayoutElement{

    protected int width, height;
    protected float x, y;
    protected int index;

    public Layout(int index){
        this.index = index;
    }

    @Override
    public void add(ILayoutElement layout) {

    }

    @Override
    public void build() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ILayoutElement get(int index) {
        return null;
    }

    @Override
    public int getIndex(){
        return index;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getX(){
        return x;
    }

    @Override
    public float getY(){
        return y;
    }

    @Override
    public void size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void position(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Map<Integer,ILayoutElement> getObjects(){
        Map<Integer,ILayoutElement> objects = new HashMap<>();
        objects.put(getIndex(),this);
        for(int i=0;i<size();i++){
            objects.putAll(get(i).getObjects());
        }
        return objects;
    }
}
