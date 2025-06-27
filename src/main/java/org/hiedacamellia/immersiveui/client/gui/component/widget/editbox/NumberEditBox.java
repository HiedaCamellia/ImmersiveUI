package org.hiedacamellia.immersiveui.client.gui.component.widget.editbox;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;

public class NumberEditBox extends EditBox {

    public NumberEditBox(int x, int y, int width, int height, Component message) {
        super(IUIMinecraftUtil.getFont(), x, y, width, height, message);
    }

    public int getInt() {
        try {
            return Integer.parseInt(getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public double getDouble() {
        try {
            return Double.parseDouble(getValue());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    public void setInt(int value) {
        setValue(String.valueOf(value));
    }
    public void setDouble(double value) {
        setValue(String.valueOf(value));
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (Character.isDigit(codePoint)) {
            return super.charTyped(codePoint, modifiers);
        }
        return false;
    }
}
