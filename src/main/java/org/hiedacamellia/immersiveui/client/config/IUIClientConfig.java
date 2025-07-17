package org.hiedacamellia.immersiveui.client.config;

import net.neoforged.neoforge.common.ModConfigSpec;


public class IUIClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    public static final ModConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Set to true to enable debug info")
            .comment("\u8bbe\u7f6e\u4e3atrue\u4ee5\u542f\u7528\u8c03\u8bd5\u4fe1\u606f")
            .define("debug", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

}
