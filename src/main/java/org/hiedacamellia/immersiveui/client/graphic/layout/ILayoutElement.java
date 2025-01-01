package org.hiedacamellia.immersiveui.client.graphic.layout;

import org.hiedacamellia.immersiveui.client.graphic.element.LayoutElements;
import org.hiedacamellia.immersiveui.client.graphic.element.LayoutColor;
import org.joml.Vector3f;

public interface ILayoutElement {

    LayoutElements.Margin getMargin();

    LayoutElements.Padding getPadding();

    LayoutElements.Border getBorder();

    LayoutColor getColor();

    LayoutColor getBackgroundColor();

    LayoutColor getBorderColor();

    Vector3f getTranslate();

    int getMinWidth();

    int getMinHeight();

    int getMaxWidth();

    int getMaxHeight();

    LayoutElements.Display getDisplay();

    LayoutElements.Align getAlign();

    LayoutElements.Cursor getCursor();
}
