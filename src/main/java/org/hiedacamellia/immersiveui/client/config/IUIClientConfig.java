package org.hiedacamellia.immersiveui.client.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * IUIClientConfig 是一个客户端配置类，用于定义和管理模组的客户端配置选项。
 * 它包含了调试模式的配置选项，并提供了配置规范的构建器。
 */
public class IUIClientConfig {

    /** 配置规范的构建器，用于定义配置选项。 */
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /**
     * 调试模式配置选项。
     * 设置为 true 以启用调试信息。
     */
    public static final ModConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Set to true to enable debug info") // 英文注释
            .comment("\u8bbe\u7f6e\u4e3atrue\u4ee5\u542f\u7528\u8c03\u8bd5\u4fe1\u606f") // 中文注释
            .define("debug", false);

    /** 配置规范实例，用于存储所有定义的配置选项。 */
    public static final ModConfigSpec SPEC = BUILDER.build();

}