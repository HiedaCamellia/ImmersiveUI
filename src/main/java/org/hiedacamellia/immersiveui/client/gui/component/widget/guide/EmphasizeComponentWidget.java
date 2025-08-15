package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

/**
 * 用于高亮显示单行文本的强调控件。
 */
public class EmphasizeComponentWidget extends EmphasizeWidget {

    /**
     * 构造一个EmphasizeComponentWidget。
     * @param x X坐标
     * @param y Y坐标
     * @param message 显示的文本内容
     */
    public EmphasizeComponentWidget(int x, int y,Component message) {
        super(x, y, IUIMinecraftUtils.getFont().width(message)+2, IUIMinecraftUtils.getFont().lineHeight+2, message);
    }

    /**
     * 渲染强调效果和文本内容。
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        IUIGuiUtils.drawString(guiGraphics,getMessage(),getX()+1,getY()+1,0xFFFFFFFF,false);
    }
}
