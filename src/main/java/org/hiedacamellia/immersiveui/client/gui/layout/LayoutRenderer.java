package org.hiedacamellia.immersiveui.client.gui.layout;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.gui.animate.AnimateUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LayoutRenderer {

    private ILayoutElement layout;
    private int x,y;
    private int width,height;
    private Map<Integer,ILayoutElement> objects;
    private Map<Integer,ILayoutElement> smooth2;
    private boolean onSmooth2 = false;
    private float smoothTime = 1;
    private float smoothProcess = 0;

    public LayoutRenderer(int x,int y,ILayoutElement layout){
        this.layout = layout;
        this.objects = layout.getObjects();
        this.x = x;
        this.y = y;
    }

    public void position(int x,int y){
        this.x = x;
        this.y = y;
        this.layout.position(x,y);
    }

    public void size(int width,int height){
        this.width = width;
        this.height = height;
        this.layout.size(width,height);
    }

    public void smooth2Layout(ILayoutElement smooth2){
        this.layout = smooth2;
        smooth2Layout(smooth2.getObjects());
    }

    private void smooth2Layout(Map<Integer,ILayoutElement> smooth2){
        this.smooth2 = smooth2;
        this.onSmooth2 = true;
        this.smoothProcess = 0;
    }

    public void smoothTime(float time){
        smoothTime = time;
    }

    public void render(GuiGraphics guiGraphics, float deltaTicks){
        if(onSmooth2){
            Set<Integer> set = objects.keySet();
            set.addAll(smooth2.keySet());
            smoothProcess += deltaTicks;
            set.forEach(index -> {
                ILayoutElement element = objects.get(index);
                ILayoutElement element2 = smooth2.get(index);
                if(element!=null && element2!=null){
                    double process = AnimateUtils.Lerp.smooth(0,smoothTime,smoothProcess);
                    double x = element.getX()*process + element2.getX()*(1-process);
                    double y = element.getY()*process + element2.getY()*(1-process);
                    element.position((float)x,(float)y);
                    element2.position((float)x,(float)y);
                    RenderSystem.setShaderColor(1.0f,1.0f,1.0f, (float) process);
                    element.render(guiGraphics,width,height);
                    RenderSystem.setShaderColor(1.0f,1.0f,1.0f, (float) (1-process));
                    element2.render(guiGraphics,width,height);
                    RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
                }else if(element!=null || element2!=null){
                    Objects.requireNonNullElse(element, element2).render(guiGraphics, width, height);
                }
            });
            if(smoothProcess>=smoothTime){
                onSmooth2 = false;
                smoothProcess = 0;
            }
        }
        else
        {
            layout.render(guiGraphics, width, height);
        }
    }
}
