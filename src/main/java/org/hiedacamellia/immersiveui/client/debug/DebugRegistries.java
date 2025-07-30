package org.hiedacamellia.immersiveui.client.debug;

import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * DebugRegistries 是一个用于管理调试树根节点的注册表类。
 * 它提供了注册和获取调试树根节点的功能。
 */
public class DebugRegistries {

    /** DebugRegistries 的单例实例。 */
    public static final DebugRegistries INSTANCE = new DebugRegistries();

    /** 存储调试树根节点的列表。 */
    private List<DebugTreeEntryWidget> roots = new ArrayList<>();

    /**
     * 注册一个调试树根节点。
     *
     * @param root 要注册的调试树根节点
     */
    public void registerRoot(DebugTreeEntryWidget root) {
        roots.add(root);
    }

    /**
     * 获取所有已注册的调试树根节点。
     *
     * @return 调试树根节点的列表
     */
    public List<DebugTreeEntryWidget> getRoots() {
        return roots;
    }
}