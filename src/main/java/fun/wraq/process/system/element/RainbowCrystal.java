package fun.wraq.process.system.element;

import fun.wraq.process.system.element.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RainbowCrystal extends Item {

    public RainbowCrystal(Properties p_41383_) {
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

    public static List<fun.wraq.process.system.element.Color> colorList = new ArrayList<>() {{
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(0, 255, 0), new fun.wraq.process.system.element.Color.RGB(0, 255, 255), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(0, 255, 255), new fun.wraq.process.system.element.Color.RGB(255, 0, 0), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(255, 0, 0), new fun.wraq.process.system.element.Color.RGB(128, 128, 128), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(128, 128, 128), new fun.wraq.process.system.element.Color.RGB(1, 255, 255), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(1, 255, 255), new fun.wraq.process.system.element.Color.RGB(100, 149, 237), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(100, 149, 237), new fun.wraq.process.system.element.Color.RGB(0, 255, 127), 100));
        add(new fun.wraq.process.system.element.Color(new fun.wraq.process.system.element.Color.RGB(0, 255, 127), new fun.wraq.process.system.element.Color.RGB(0, 255, 0), 100));
    }};

    public static Map<fun.wraq.process.system.element.Color.RGB, fun.wraq.process.system.element.Color.RGB> colorMap = new HashMap<>() {{
        put(new fun.wraq.process.system.element.Color.RGB(0, 255, 0), new fun.wraq.process.system.element.Color.RGB(0, 255, 255));
        put(new fun.wraq.process.system.element.Color.RGB(0, 255, 255), new fun.wraq.process.system.element.Color.RGB(255, 0, 0));
        put(new fun.wraq.process.system.element.Color.RGB(255, 0, 0), new fun.wraq.process.system.element.Color.RGB(128, 128, 128));
        put(new fun.wraq.process.system.element.Color.RGB(128, 128, 128), new fun.wraq.process.system.element.Color.RGB(1, 255, 255));
        put(new fun.wraq.process.system.element.Color.RGB(1, 255, 255), new fun.wraq.process.system.element.Color.RGB(100, 149, 237));
        put(new fun.wraq.process.system.element.Color.RGB(100, 149, 237), new fun.wraq.process.system.element.Color.RGB(0, 255, 127));
        put(new fun.wraq.process.system.element.Color.RGB(0, 255, 127), new fun.wraq.process.system.element.Color.RGB(0, 255, 0));
    }};

    public static Component RainBowName() {
        return rainBowNameFourChar("七色棱镜");
    }

    public static Component rainBowNameFourChar(String s) {
        for (int i = 0; i < colorList.size(); i++) {
            fun.wraq.process.system.element.Color color = colorList.get(i);
            if (color.Add()) {
                colorList.set(i, new Color(color.targetRGB, colorMap.get(color.targetRGB), 100));
            }
        }
        return Component.literal(s.substring(0, 1)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(0).getRGB()))).withStyle(ChatFormatting.BOLD).
                append(Component.literal(s.substring(1, 2)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(1).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal(s.substring(2, 3)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(2).getRGB()))).withStyle(ChatFormatting.BOLD)).
                append(Component.literal(s.substring(3, 4)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(3).getRGB()))).withStyle(ChatFormatting.BOLD));
    }
}
