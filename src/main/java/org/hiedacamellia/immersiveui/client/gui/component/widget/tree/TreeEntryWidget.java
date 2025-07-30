package org.hiedacamellia.immersiveui.client.gui.component.widget.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeEntryWidget 是一个自定义的树形结构组件，用于表示树的节点。
 * 它支持折叠/展开、拖拽、子节点管理等功能。
 *
 * @param <T> 节点数据的类型
 */
public class TreeEntryWidget<T> extends AbstractWidget {

    protected Font font; // 用于渲染文本的字体

    protected boolean fold = true; // 是否折叠子节点

    protected TreeEntryWidget<T> parent; // 父节点

    protected List<TreeEntryWidget<T>> children = new ArrayList<>(); // 子节点列表

    protected T data; // 节点关联的数据

    protected boolean isRoot = false; // 是否为根节点

    protected TreeWidget<T, TreeEntryWidget<T>> tree; // 所属的树组件

    protected int selfWidth, selfHeight, foldWidth; // 节点的宽度、高度和折叠图标宽度

    protected final Component foldComponent = Component.literal("▶"); // 折叠图标
    protected final Component unfoldComponent = Component.literal("▼"); // 展开图标

    /**
     * 设置所属的树组件，并将当前节点标记为根节点。
     *
     * @param tree 所属的树组件
     */
    public void tree(TreeWidget<T, TreeEntryWidget<T>> tree) {
        isRoot = true;
        this.tree = tree;
    }

    /**
     * 获取所属的树组件。
     *
     * @return 所属的树组件
     */
    public TreeWidget<T, TreeEntryWidget<T>> getTree() {
        if (isRoot) return tree;
        return parent.getTree();
    }

    /**
     * 判断当前节点是否为根节点。
     *
     * @return 如果是根节点返回 true，否则返回 false
     */
    public boolean isRoot() {
        return isRoot;
    }

    /**
     * 在当前节点之前插入一个节点。
     *
     * @param widget 要插入的节点
     */
    public void insert(TreeEntryWidget<T> widget) {
        if (widget.isRoot()) return;
        if (parent == null) return;
        if (parent == widget.parent) {
            parent.moveChild(widget, parent.children.indexOf(this));
            return;
        }
        int i = parent.children.indexOf(this);
        if (i == -1) return;
        widget.parent.removeChild(widget);
        parent.addChild(i, widget);
    }

    /**
     * 根据鼠标位置获取当前节点或其子节点。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 鼠标所在的节点
     */
    public TreeEntryWidget<T> getAt(double mouseX, double mouseY) {
        TreeEntryWidget<T> widget = getWidgetAt(mouseX, mouseY);
        if (widget == null) {
            return isHovered(mouseX, mouseY) ? this : null;
        }
        return widget.getAt(mouseX, mouseY);
    }

    /**
     * 根据鼠标位置获取直接子节点。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 鼠标所在的直接子节点
     */
    public TreeEntryWidget<T> getWidgetAt(double mouseX, double mouseY) {
        if (fold) return null;
        for (TreeEntryWidget<T> child : children) {
            if (child.isHovered(mouseX, mouseY)) {
                return child;
            }
        }
        return null;
    }

    /**
     * 判断鼠标位置是否可以接受拖拽的节点。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 如果可以接受返回 true，否则返回 false
     */
    public boolean shouldAccept(double mouseX, double mouseY) {
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + foldWidth;
        int y1 = y0 + selfHeight;
        int x2 = x0 + foldWidth + selfWidth;
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y0 && mouseY <= y1;
    }

    /**
     * 接受一个拖拽的节点作为子节点。
     *
     * @param widget 要接受的节点
     */
    public void accept(TreeEntryWidget<T> widget) {
        widget.parent.removeChild(widget);
        addChild(widget);
    }

    /**
     * 构造一个 TreeEntryWidget 实例。
     *
     * @param message 节点显示的消息
     * @param font    用于渲染文本的字体
     */
    public TreeEntryWidget(Component message, Font font) {
        super(0, 0, 0, 0, message);
        this.font = font;
        this.selfHeight = font.lineHeight;
        this.selfWidth = font.width(message);
        this.foldWidth = font.width(foldComponent);
    }

    /**
     * 创建一个 TreeEntryWidget 实例。
     *
     * @param data      节点关联的数据
     * @param component 节点显示的消息
     * @param font      用于渲染文本的字体
     * @param <T>       节点数据的类型
     * @return 创建的 TreeEntryWidget 实例
     */
    public static <T> TreeEntryWidget<T> of(T data, Component component, Font font) {
        TreeEntryWidget<T> widget = new TreeEntryWidget<>(component, font);
        widget.setData(data);
        widget.width = widget.selfWidth;
        widget.height = widget.selfHeight;
        return widget;
    }

    /**
     * 在当前节点的子节点列表中添加一个子节点。
     *
     * @param index  子节点的插入位置
     * @param child  要添加的子节点
     */
    public void addChild(int index,TreeEntryWidget<T> child) {
        children.add(index,child);
        child.parent = this;
        updateWidget();
    }

    /**
     * 在当前节点的子节点列表中添加一个子节点。
     *
     * @param child 要添加的子节点
     */
    public void addChild(TreeEntryWidget<T> child) {
        children.add(child);
        child.parent = this;
        updateWidget();
    }

    /**
     * 从当前节点的子节点列表中移除一个子节点。
     *
     * @param child 要移除的子节点
     */
    public void removeChild(TreeEntryWidget<T> child) {
        children.remove(child);
        child.parent = null;
        updateWidget();
    }

    /**
     * 移动一个子节点到指定位置。
     *
     * @param child 要移动的子节点
     * @param index 新的位置索引
     */
    public void moveChild(TreeEntryWidget<T> child,int index){
        children.remove(child);
        children.add(index,child);
        updateWidget();
    }

    /**
     *  检查当前节点是否有子节点。
     *
     * @return  如果有子节点返回 true，否则返回 false
     */
    public boolean hasChild(){
        return !children.isEmpty();
    }

    /**
     * 设置当前节点关联的数据。
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取当前节点关联的数据。
     *
     * @return 当前节点的数据
     */
    public T getData() {
        return data;
    }

    /**
     * 折叠当前节点的子节点，使其不可见。
     * 在折叠状态下，子节点不会被渲染。
     */
    public void fold() {
        fold = true;
        getTree().updateWidget();
    }

    /**
     * 展开当前节点的子节点，使其可见。
     * 在展开状态下，子节点会被渲染。
     */
    public void unfold() {
        fold = false;
        getTree().updateWidget();
    }

    /**
     * 更新当前节点及其子节点的布局。
     */
    public void updateWidget() {
        int X = this.getX() + foldWidth;
        int Y = this.getY() + selfHeight;
        for (TreeEntryWidget<T> child : children) {
            child.setX(X);
            child.setY(Y);
            child.updateWidget();
            Y += child.getHeight();
        }
        updateHeight();
        updateWidth();
    }

    private void updateHeight() {
        int height = selfHeight;
        if (fold) {
            this.height = height;
            return;
        }
        for (AbstractWidget child : children) {
            height += child.getHeight();
        }
        this.height = height;
    }

    private void updateWidth() {
        int width = 0;
        if(hasChild()) {
            if (fold) {
                this.width = Math.max(width + foldWidth, foldWidth+selfWidth);
                return;
            }
            for (AbstractWidget child : children) {
                width = Math.max(width, child.getWidth());
            }
            this.width = Math.max(width + foldWidth, foldWidth+selfWidth);
        }else {
            this.width = selfWidth;
        }
    }

    /**
     * 检查鼠标位置是否可以切换折叠状态。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按钮
     * @return 如果可以切换折叠状态返回 true，否则返回 false
     */
    public boolean shouldChangeFold(double mouseX, double mouseY, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            int x0 = this.getX();
            int y0 = this.getY();
            int x1 = x0 + foldWidth;
            int y1 = y0 + selfHeight;
            return mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1;
        }
        return false;
    }


    /**
     * 处理鼠标点击事件。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按钮
     * @return 如果处理了点击事件返回 true，否则返回 false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(hasChild()) {
            if (shouldChangeFold(mouseX, mouseY, button)) {
                //ImmersiveUI.LOGGER.info("fold："+fold);
                if (fold) {
                    unfold();
                } else {
                    fold();
                }
                return true;
            }
            if (isHovered(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                return false;
            }
            if(!fold) {
                TreeEntryWidget<T> child = getWidgetAt(mouseX, mouseY);
                if (child != null) {
                    boolean v = child.mouseClicked(mouseX, mouseY, button);
                    updateWidget();
                    return v;
                }
            }
        }else {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查鼠标是否悬停在当前节点上。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 如果鼠标悬停在当前节点上返回 true，否则返回 false
     */
    public boolean isHovered(double mouseX,double mouseY) {
        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = x0 + this.width;
        int y1 = y0 + this.height;
        return mouseX >= x0 && mouseX <= x1 && mouseY >= y0 && mouseY <= y1;
    }


    /**
     * 渲染当前节点及其子节点。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX     鼠标的 X 坐标
     * @param mouseY     鼠标的 Y 坐标
     * @param v          渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(hasChild())
            guiGraphics.drawString(font, fold ? foldComponent.copy().append(getMessage()) : unfoldComponent.copy().append(getMessage()), this.getX(), this.getY(), 0xFFFFFF);
        else
            guiGraphics.drawString(font, getMessage(), this.getX(), this.getY(), 0xFFFFFF);

        if(!getTree().isDrag(this)) {
            if (isHovered(mouseX, mouseY) && getTree().isDragging() && getWidgetAt(mouseX, mouseY) == null && !shouldAccept(mouseX, mouseY)) {
                guiGraphics.fill(this.getX(), this.getY() - 1, getTree().getX() + getTree().getWidth(), this.getY() + 2, 0x80FF0000);
            }
            if (shouldAccept(mouseX, mouseY) && getTree().isDragging() && getWidgetAt(mouseX, mouseY) == null) {
                guiGraphics.fill(this.getX()+4, this.getY() + 2, getTree().getX() + getTree().getWidth(), this.getY() + 7, 0x800000FF);
            }
        }
        if(fold) return;
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    protected void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        for (TreeEntryWidget<T> child : children) {
            child.renderWidget(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
