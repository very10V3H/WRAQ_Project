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

    @SafeVarargs
    public static<T> MutableComponent s(T... textAndStyle) {
        MutableComponent mutableComponent = Component.literal("");
        for (int i = 0; i < textAndStyle.length; i++) {
            T componentOrStyle = textAndStyle[i];
            if (componentOrStyle instanceof Component component) {
                mutableComponent.append(component);
                continue;
            }
            if (componentOrStyle instanceof String string) {
                MutableComponent soleComponent = Component.literal(string);
                int index = i + 1;
                if (index < textAndStyle.length && !(textAndStyle[index] instanceof Style || textAndStyle[index] instanceof ChatFormatting)) {
                    soleComponent.withStyle(ChatFormatting.WHITE);
                }
                while (index < textAndStyle.length && (textAndStyle[index] instanceof Style || textAndStyle[index] instanceof ChatFormatting)) {
                    if (textAndStyle[index] instanceof Style style) soleComponent.withStyle(style);
                    if (textAndStyle[index] instanceof ChatFormatting chatFormatting) soleComponent.withStyle(chatFormatting);
                    index ++;
                }
                mutableComponent.append(soleComponent);
            }
        }
        return mutableComponent;
    }
}