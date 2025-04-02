package fun.wraq.series.events.labourDay;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.WeakHashMap;

public class LabourDayIronHoe extends Item {

    public LabourDayIronHoe(Properties p_41383_) {
        super(p_41383_);
        Display.souvenirsList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        OldCoin.LabourDaySuffix(components);
        components.add(Component.literal(" 劳动的工具").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 将其置于背包(非精妙背包)，为你提供:"));
        components.add(Component.literal(" 1. ").withStyle(ChatFormatting.AQUA).
                append(Component.literal("25%魔法伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 2. ").withStyle(ChatFormatting.AQUA).
                append(Component.literal("10%额外产出").withStyle(ChatFormatting.GOLD)));
        components.add(Component.literal(" - 额外产出包含：怪物掉落、采矿、砍伐、采收、钓鱼").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(ComponentUtils.getSuffixOfSouvenirs());
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }


    public static WeakHashMap<Player, Boolean> playerIsIn = new WeakHashMap<>();

    public static void tick(Player player) {
        if (player.tickCount % 20 != 0) return;
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (inventory.getItem(i).is(ModItems.LabourDayIronHoe.get())) {
                playerIsIn.put(player, true);
                return;
            }
        }
        playerIsIn.put(player, false);
    }

    public static double playerManaDamageEnhance(Player player) {
        return (playerIsIn.containsKey(player) && playerIsIn.get(player)) ? 0.25 : 0;
    }

    public static double playerExHarvest(Player player) {
        return (playerIsIn.containsKey(player) && playerIsIn.get(player)) ? 0.1 : 0;
    }
}
