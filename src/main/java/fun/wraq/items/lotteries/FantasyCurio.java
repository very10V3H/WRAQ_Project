package fun.wraq.items.lotteries;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Set;

public class FantasyCurio extends Item implements ICurioItem {

    private final int tier;
    public FantasyCurio(Properties properties, int tier) {
        super(properties);
        Utils.curiosList.add(this);
        this.tier = tier;
    }

    public static double playerFantasyAttributeEnhance(Player player) {
        double enhance = 0;
        Set<Item> curioSet = WraqCurios.CuriosAttribute.getDistinctCuriosSet(player);
        if (curioSet.contains(ModItems.FANTASY_MEDAL_2.get())) {
            enhance += 0.05;
        } else if (curioSet.contains(ModItems.FANTASY_MEDAL_1.get())) {
            enhance += 0.04;
        } else if (curioSet.contains(ModItems.FANTASY_MEDAL.get())) {
            enhance += 0.03;
        }
        if (curioSet.contains(ModItems.FANTASY_BRACELET_2.get())) {
            enhance += 0.05;
        } else if (curioSet.contains(ModItems.FANTASY_BRACELET_1.get())) {
            enhance += 0.04;
        } else if (curioSet.contains(ModItems.FANTASY_BRACELET.get())) {
            enhance += 0.03;
        }
        if (curioSet.contains(SpecialEventItems.SCALE_2025_0.get())) {
            enhance += 0.01;
        } else if (WraqCurios.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_1.get())) {
            enhance += 0.02;
        } else if (WraqCurios.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_2.get())) {
            enhance += 0.03;
        } else if (WraqCurios.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_3.get())) {
            enhance += 0.04;
        }
        return enhance;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 使基础属性的最终值提升" + new int[]{3, 4, 5}[tier] + "%")
                .withStyle(CustomStyle.styleOfFantasy));
        components.add(Te.s(" 暴击几率/百分比穿透不受加成", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(Te.s(" 同一槽位的幻想饰品仅生效最高加成", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
