package com.very.wraq.common.fast;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class Te {
    public static MutableComponent m(String v) {
        return Component.literal(v);
    }

    public static MutableComponent m(String v, Style style) {
        return Component.literal(v).withStyle(style);
    }

    public static MutableComponent m(String v, ChatFormatting style) {
        return Component.literal(v).withStyle(style);
    }
}
