package com.very.wraq.process.system.element;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RainbowPowder extends Item {

    public RainbowPowder(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        stack.setHoverName(RainBowName());
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static List<Color> colorList = new ArrayList<>() {{
        add(new Color(new Color.RGB(0, 255, 0), new Color.RGB(0, 255, 255), 100));
        add(new Color(new Color.RGB(0, 255, 255), new Color.RGB(255, 0, 0), 100));
        add(new Color(new Color.RGB(255, 0, 0), new Color.RGB(128, 128, 128), 100));
        add(new Color(new Color.RGB(128, 128, 128), new Color.RGB(1, 255, 255), 100));
        add(new Color(new Color.RGB(1, 255, 255), new Color.RGB(100, 149, 237), 100));
        add(new Color(new Color.RGB(100, 149, 237), new Color.RGB(0, 255, 127), 100));
        add(new Color(new Color.RGB(0, 255, 127), new Color.RGB(0, 255, 0), 100));
    }};

    public static Map<Color.RGB, Color.RGB> colorMap = new HashMap<>() {{
        put(new Color.RGB(0, 255, 0), new Color.RGB(0, 255, 255));
        put(new Color.RGB(0, 255, 255), new Color.RGB(255, 0, 0));
        put(new Color.RGB(255, 0, 0), new Color.RGB(128, 128, 128));
        put(new Color.RGB(128, 128, 128), new Color.RGB(1, 255, 255));
        put(new Color.RGB(1, 255, 255), new Color.RGB(100, 149, 237));
        put(new Color.RGB(100, 149, 237), new Color.RGB(0, 255, 127));
        put(new Color.RGB(0, 255, 127), new Color.RGB(0, 255, 0));
    }};

    public static Component RainBowName() {
        for (int i = 0; i < colorList.size(); i++) {
            Color color = colorList.get(i);
            if (color.Add()) {
                colorList.set(i, new Color(color.targetRGB, colorMap.get(color.targetRGB), 100));
            }
        }
        return Component.literal("七").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(0).getRGB()))).withStyle(ChatFormatting.BOLD).
                append(Component.literal("色").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(1).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal("棱").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(2).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal("镜").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(3).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal("碎").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(4).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal("片").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(5).getRGB()))).withStyle(ChatFormatting.BOLD));
    }
}
