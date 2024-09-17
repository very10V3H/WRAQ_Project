package com.very.wraq.series.overworld.chapter1.volcano.armor;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.suit.SuitCount;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.volcano.VolcanoSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoArmorHelmet extends ArmorItem {
    public VolcanoArmorHelmet(ItemMaterial Materrial, Type Slots) {
        super(Materrial, Slots, new Properties().rarity(CustomStyle.VolcanoItalic));
        Utils.attackDamage.put(this, 10d);
        Utils.manaDamage.put(this, 20d);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.YELLOW)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        VolcanoSuitDescription.VolcanoArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static double exAttackDamage(Player player) {
        return SuitCount.getVolcanoCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) : 0;
    }

    public static double exManaDamage(Player player) {
        return SuitCount.getVolcanoCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 2 : 0;
    }
}
