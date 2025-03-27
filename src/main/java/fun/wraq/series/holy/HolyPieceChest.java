package fun.wraq.series.holy;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.lottery.NewLotteries;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HolyPieceChest extends NewLotteries {
    public HolyPieceChest(Properties properties, List<Item> items) {
        super(properties, new ArrayList<>() {{
            items.forEach(item -> add(new Loot(new ItemStack(item), 1d / items.size())));
        }});
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(""));
        components.add(Component.literal(" 可能获得的奖励有:").withStyle(ChatFormatting.GOLD));
        for (int i = 0; i < loots.size(); i++) {
            Loot loot = loots.get(i);
            ChatFormatting style = ChatFormatting.AQUA;
            components.add(Te.s(" " + (i + 1) + ".", style,
                    loot.itemStack().getDisplayName(), " *" + loot.itemStack().getCount(), style,
                    " 「" + String.format("%.2f%%", loot.rate() * 100) + "」", style));
        }
    }
}
