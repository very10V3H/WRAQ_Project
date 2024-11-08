package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VolcanoPower extends Item implements ActiveItem {

    private final int tier;

    public VolcanoPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    public int getTier() {
        return tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.DescriptionActive(components, Component.literal("爆裂之焰").withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal(" 使").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人爆裂，在每个敌人位置处产生小范围").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        ComponentUtils.coolDownTimeDescription(components, CoolDownTime[tier]);
        ComponentUtils.manaCostDescription(components, ManaCost[tier]);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            10, 9, 8, 7
    };

    public static double[] effect = {
            1, 1.15, 1.3, 1.5
    };

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, VolcanoPower.ManaCost[tier] - 10 * SuitCount.getObsiManaESuitCount(player), true)) {
            PowerLogic.VolcanoPower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 7);
        }
    }
}
