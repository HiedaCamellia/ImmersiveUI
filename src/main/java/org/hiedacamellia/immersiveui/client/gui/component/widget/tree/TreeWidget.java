package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.ImmersiveUI;

import java.util.List;

/**
 * TreeWidget 是一个自定义的树形组件，用于显示和管理树形结构的节点。
 * 它支持拖拽、选择、更新布局以及渲染标题和子节点。
 *
 * @param <T> 节点数据的类型
 * @param <V> 节点组件的类型，必须继承自 TreeEntryWidget
 */
public class TreeWidget<T, V extends TreeEntryWidget<T>> extends AbstractContainerWidget {

    protected Font font; // 用于渲染文本的字体

    protected List<? extends V> root; // 根节点列表

    protected TreeEntryWidget<T> onDrag; // 当前正在拖拽的节点
    protected TreeEntryWidget<T> select; // 当前选中的节点

    private double dragStartX, dragStartY; // 拖拽起始位置的 X 和 Y 坐标
    private double dragOriginX, dragOriginY; // 拖拽节点的原始位置

    private boolean showTitle = true; // 是否显示标题

    protected boolean dragable = true; // 是否允许拖拽
    protected int titleWidth, titleHeight; // 标题的宽度和高度

    /**
     * 获取当前选中的节点。
     *
     * @return 当前选中的节点
     */
    public TreeEntryWidget<T> getSelect() {
        return select;
    }

    /**
     * 判断指定的节点是否正在被拖拽。
     *
     * @param widget 要检查的节点
     * @return 如果正在拖拽该节点返回 true，否则返回 false
     */
    public boolean isDrag(V widget) {
        return onDrag == widget;
    }

    /**
     * 隐藏标题。
     */
    public void hideTitle() {
        showTitle = false;
    }

    /**
     * 更新组件的布局，包括根节点及其子节点的位置和大小。
     */
    public void updateWidget() {
        int x = this.getX();
        int y = this.getY();
        int offset = showTitle ? titleHeight : 0;
        int width = 0;
        for (V treeEntryWidget : root) {
            treeEntryWidget.setX(x);
            treeEntryWidget.setY(y + offset);
            treeEntryWidget.updateWidget();
            offset += treeEntryWidget.getHeight();
            width = Math.max(width, treeEntryWidget.getWidth());
        }
        this.height = offset;
        this.width = showTitle ? Math.max(width, titleWidth) : width;
    }

    /**
     * 构造一个 TreeWidget 实例，使用单个根节点。
     *
     * @param root      根节点
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     */
    public TreeWidget(V root, int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        this.root = List.of(root);
        root.tree((TreeWidget<T, TreeEntryWidget<T>>) this);
        this.titleHeight = font.lineHeight;
        this.titleWidth = font.width(component);
        updateWidget();
    }

    /**
     * 构造一个 TreeWidget 实例，使用多个根节点。
     *
     * @param root      根节点列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     */
    public TreeWidget(List<? extends V> root, int x, int y, Component component, Font font) {
        super(x, y, 0, 0, component);
        this.font = font;
        this.root = root;
        for (V treeEntryWidget : root) {
            treeEntryWidget.tree((TreeWidget<T, TreeEntryWidget<T>>) this);
        }
        this.titleHeight = font.lineHeight;
        this.titleWidth = font.width(component);
        updateWidget();
    }

    /**
     * 创建一个 TreeWidget 实例，使用单个根节点。
     *
     * @param root      根节点
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     * @param <T>       节点数据的类型
     * @param <V>       节点组件的类型
     * @return 创建的 TreeWidget 实例
     */
    public static <T, V extends TreeEntryWidget<T>> TreeWidget<T, V> of(V root, int x, int y, Component component, Font font) {
        return new TreeWidget<>(root, x, y, component, font);
    }

    /**
     * 创建一个 TreeWidget 实例，使用多个根节点。
     *
     * @param root      根节点列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     * @param <T>       节点数据的类型
     * @param <V>       节点组件的类型
     * @return 创建的 TreeWidget 实例
     */
    public static <T, V extends TreeEntryWidget<T>> TreeWidget<T, V> of(List<V> root, int x, int y, Component component, Font font) {
        return new TreeWidget<>(root, x, y, component, font);
    }

    /**
     * 根据鼠标位置获取对应的节点。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 鼠标所在的节点
     */
    public TreeEntryWidget<T> getAt(double mouseX, double mouseY) {
        TreeEntryWidget<T> widget;
        for (TreeEntryWidget<T> child : root) {
            widget = child.getAt(mouseX, mouseY);
            if (widget != null) {
                return widget;
            }
        }
        return null;
    }


    /**
     * 获取树的根节点。
     *
     * @return 根节点
     */
    @Override
    public List<? extends GuiEventListener> children() {
        return root;
    }


    /**
     * 处理鼠标点击事件。
     * 如果点击了一个子节点，则选择该节点并可能开始拖拽。
     * 如果点击了根节点，则更新组件布局。
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按钮
     * @return 如果点击了一个子节点返回 true，否则返回 false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TreeEntryWidget<T> child = getAt(mouseX, mouseY);
        select = child;
        if (child != null && !child.isRoot()) {
            boolean v = child.mouseClicked(mouseX, mouseY, button);
            updateWidget();
            if (!v && dragable) {
                this.setDragging(true);
                child.fold();
                this.onDrag = child;
                this.dragStartX = mouseX;
                this.dragStartY = mouseY;
                this.dragOriginX = child.getX();
                this.dragOriginY = child.getY();
            }
            return true;
        } else if (child != null && child.isRoot()) {
            boolean v = child.mouseClicked(mouseX, mouseY, button);
            updateWidget();
            return true;
        } else {
            updateWidget();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * 处理鼠标释放事件。
     * 如果正在拖拽，则结束拖拽并处理拖拽的节点。
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按钮
     * @return 始终返回 false
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        if (onDrag != null) {
            TreeEntryWidget<T> child = getAt(mouseX, mouseY);
            ImmersiveUI.LOGGER.info(child.getMessage().getString());
            if (onDrag != child) {
                if (child.shouldAccept(mouseX, mouseY)) {
                    child.accept(onDrag);
                } else {
                    child.insert(onDrag);
                }
            }
            onDrag = null;
            updateWidget();
        }
        return false;
    }

    /**
     * 检查是否正在拖拽。
     *
     * @return 如果正在拖拽返回 true，否则返回 false
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging()) {
            if (onDrag != null) {
                int xOff = (int) (mouseX - dragStartX);
                int yOff = (int) (mouseY - dragStartY);
                onDrag.setX((int) (dragOriginX + xOff));
                onDrag.setY((int) (dragOriginY + yOff));
            }
            return true;
        }
        return false;
    }

    @Override
    protected int contentHeight() {
        return getHeight() - (showTitle ? titleHeight : 0);
    }

    @Override
    protected double scrollRate() {
        return 0;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        renderBg(guiGraphics, mouseX, mouseY, v);
        if (showTitle) {
            renderTitle(guiGraphics, mouseX, mouseY, v);
        }
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFDDDDDD);
    }

    /**
     * 渲染组件的标题。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + titleHeight, 0xFFAAAAAA);
        guiGraphics.drawString(font, this.getMessage(), this.getX(), this.getY(), 0x000000, false);
    }

    /**
     * 渲染子节点。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    protected void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        for (V treeEntryWidget : root) {
            treeEntryWidget.render(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        // 未实现旁白更新逻辑
    }
}