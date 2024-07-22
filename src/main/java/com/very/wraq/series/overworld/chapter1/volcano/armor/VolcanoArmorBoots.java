package com.very.wraq.series.overworld.chapter1.volcano.armor;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.volcano.VolcanoSuitDescription;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoArmorBoots extends ArmorItem {
    public VolcanoArmorBoots(ItemMaterial Materrial, Type Slots) {
        super(Materrial, Slots, new Properties().rarity(CustomStyle.VolcanoItalic));
        Utils.movementSpeedCommon.put(this, 0.25);
        Utils.attackDamage.put(this, 8d);
        Utils.manaDamage.put(this, 16d);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.YELLOW)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        VolcanoSuitDescription.VolcanoArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }
}
