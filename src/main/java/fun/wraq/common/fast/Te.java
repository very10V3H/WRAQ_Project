package fun.wraq.common.fast;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class Te {
    public static MutableComponent m(String v) {
        return Component.literal(v).withStyle(ChatFormatting.WHITE);
    }

    public static MutableComponent m(String v, Style style) {
        return Component.literal(v).withStyle(style);
    }

    public static MutableComponent m(String v, Style... style) {
        MutableComponent component = Component.literal(v);
        for (Style style1 : style) {
            component.withStyle(style1);
        }
        return component;
    }

    public static MutableComponent m(String v, ChatFormatting style) {
        return Component.literal(v).withStyle(style);
    }

    public static MutableComponent m(String v, ChatFormatting... style) {
        MutableComponent component = Component.literal(v);
        for (ChatFormatting style1 : style) {
            component.withStyle(style1);
        }
        return component;
    }

    public static MutableComponent m(Component... components) {
        MutableComponent mutableComponent = Component.literal("");
        for (Component component : components) {
            mutableComponent.append(component);
        }
        return mutableComponent;
    }

    public static MutableComponent s(Component... components) {
        MutableComponent mutableComponent = Component.literal(" ");
        for (Component component : components) {
            mutableComponent.append(component);
        }
        return mutableComponent;
    }
}
