package fun.wraq.series.holy;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.series.holy.ice.IceHolyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HolyChest extends NewLotteries {
    public HolyChest(Properties properties, List<Item> tier1Items, List<Item> tier2Items,
                     List<Item> tier3Items, List<Item> tier4Items) {
        super(properties, new ArrayList<>() {{
            tier1Items.forEach(item -> add(new Loot(new ItemStack(item), 0.1598 / tier1Items.size())));
            tier2Items.forEach(item -> add(new Loot(new ItemStack(item), 0.032 / tier2Items.size())));
            tier3Items.forEach(item -> add(new Loot(new ItemStack(item), 0.0064 / tier3Items.size())));
            tier4Items.forEach(item -> add(new Loot(new ItemStack(item), 0.0026 / tier4Items.size())));
            add(new Loot(new ItemStack(IceHolyItems.PIECE_0.get()), 0.7992));
        }}, HolyItems.HOLY_CHEST_KEY.get());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(""));
        if (KEY != null) {
            components.add(Te.s(" 开启需要消耗一个", KEY));
        }
        components.add(Component.literal(" 可能获得的奖励有:").withStyle(ChatFormatting.GOLD));
        ChatFormatting style = ChatFormatting.AQUA;
        components.add(Te.s(" 1.", style, "神圣圣器", ChatFormatting.LIGHT_PURPLE,
                " 「" + String.format("%.2f%%", 0.0026 * 100) + "」", ChatFormatting.RED));
        components.add(Te.s(" 2.", style, "傲世圣器", ChatFormatting.AQUA,
                " 「" + String.format("%.2f%%", 0.0064 * 100) + "」", ChatFormatting.GOLD));
        components.add(Te.s(" 3.", style, "超凡圣器", ChatFormatting.GOLD,
                " 「" + String.format("%.2f%%", 0.032 * 100) + "」"));
        components.add(Te.s(" 4.", style, "璀璨圣器",
                " 「" + String.format("%.2f%%", 0.1598 * 100) + "」"));
        components.add(Te.s(" 5.", style, "璀璨碎片",
                " 「" + String.format("%.2f%%", 0.7992 * 100) + "」"));
    }
}
