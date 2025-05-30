package fun.wraq.series.events.dragonboat;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonDiamond extends WraqItem {

    public DragonDiamond(Properties properties) {
        super(properties, false, true);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("悠远之境产出的珍贵宝物", CustomStyle.styleOfWorld, ChatFormatting.ITALIC));
        components.add(Te.s(" 悠远的银龙传说之地，失传于尘世.", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 放置在背包中，每颗提供:"));
        components.add(Te.s(" - ", ComponentUtils.AttributeDescription.attackDamage("0.5%")));
        components.add(Te.s(" - ", ComponentUtils.AttributeDescription.manaDamage("0.5%")));
        components.add(Te.s(" 至多生效10颗", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static Map<String, Integer> countMap = new HashMap<>();
    public static void handleEachStack(ItemStack itemStack, Player player) {
        if (itemStack.getItem() instanceof DragonDiamond) {
            countMap.compute(Name.get(player), (k, v) -> v == null ? itemStack.getCount() : v + itemStack.getCount());
        }
    }

    public static double getAttackDamageEnhance(Player player) {
        return Math.min(10, countMap.getOrDefault(Name.get(player), 0)) * 0.005;
    }

    public static double getManaDamageEnhance(Player player) {
        return Math.min(10, countMap.getOrDefault(Name.get(player), 0)) * 0.005;
    }
}
