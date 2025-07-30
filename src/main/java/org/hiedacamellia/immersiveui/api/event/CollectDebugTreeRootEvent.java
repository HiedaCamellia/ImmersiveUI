package org.hiedacamellia.immersiveui.api.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import org.hiedacamellia.immersiveui.client.debug.DebugRegistries;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

import java.util.List;

/**
 * CollectDebugTreeRootEvent 是一个客户端事件类，用于收集debug页面树的根节点。
 * 它允许其他组件通过事件总线注册debug页面树的根节点。
 */
@OnlyIn(Dist.CLIENT)
public class CollectDebugTreeRootEvent extends Event {

    /** 存储debug页面树根节点的列表。 */
    private final List<DebugTreeEntryWidget> roots;

    /**
     * 构造函数，初始化事件并设置根节点列表。
     *
     * @param roots debug页面树根节点的列表
     */
    public CollectDebugTreeRootEvent(List<DebugTreeEntryWidget> roots) {
        this.roots = roots;
    }

    /**
     * 注册一个debug页面树根节点。
     * 同时将其添加到 DebugRegistries 中进行全局管理。
     *
     * @param root 要注册的debug页面树根节点
     */
    public void registerRoot(DebugTreeEntryWidget root) {
        roots.add(root);
        DebugRegistries.INSTANCE.registerRoot(root);
    }

    /**
     * 获取所有已注册的debug页面树根节点。
     *
     * @return debug页面树根节点的列表
     */
    public List<DebugTreeEntryWidget> getRoots() {
        return roots;
    }
}