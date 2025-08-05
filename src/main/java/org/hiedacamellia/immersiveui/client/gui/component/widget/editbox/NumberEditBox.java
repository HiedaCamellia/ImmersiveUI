package org.hiedacamellia.immersiveui.client.gui.component.widget.editbox;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

/**
 * NumberEditBox 是一个扩展自 EditBox 的文本框组件，
 * 用于输入和处理数值类型的数据（整数和浮点数）。
 */
public class NumberEditBox extends EditBox {

    /**
     * 构造一个 NumberEditBox 实例。
     *
     * @param x       文本框的 X 坐标
     * @param y       文本框的 Y 坐标
     * @param width   文本框的宽度
     * @param height  文本框的高度
     * @param message 文本框的提示信息
     */
    public NumberEditBox(int x, int y, int width, int height, Component message) {
        super(IUIMinecraftUtils.getFont(), x, y, width, height, message);
    }

    /**
     * 获取文本框中的整数值。
     * 如果文本框内容无法解析为整数，则返回 0。
     *
     * @return 文本框中的整数值
     */
    public int getInt() {
        try {
            return Integer.parseInt(getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 获取文本框中的浮点数值。
     * 如果文本框内容无法解析为浮点数，则返回 0.0。
     *
     * @return 文本框中的浮点数值
     */
    public double getDouble() {
        try {
            return Double.parseDouble(getValue());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * 设置文本框的整数值。
     *
     * @param value 要设置的整数值
     */
    public void setInt(int value) {
        setValue(String.valueOf(value));
    }

    /**
     * 设置文本框的浮点数值。
     *
     * @param value 要设置的浮点数值
     */
    public void setDouble(double value) {
        setValue(String.valueOf(value));
    }

    /**
     * 处理字符输入事件。
     * 仅允许输入数字、控制字符、小数点和负号。
     *
     * @param codePoint 输入的字符
     * @param modifiers 修饰符键状态
     * @return 如果字符被接受，则返回 true；否则返回 false
     */
    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (Character.isDigit(codePoint) || Character.isISOControl(codePoint) || codePoint == '.' || codePoint == '-') {
            return super.charTyped(codePoint, modifiers);
        }
        return false;
    }
}