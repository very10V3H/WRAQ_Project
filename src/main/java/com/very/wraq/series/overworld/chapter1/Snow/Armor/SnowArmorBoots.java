package com.very.wraq.series.overworld.chapter1.Snow.Armor;

import com.very.wraq.series.overworld.chapter1.Snow.SnowSuitDescription;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SnowArmorBoots extends ArmorItem {

    private final double MaxHealth = 480;
    private final double Defence = 160;
    private static final Style style = CustomStyle.styleOfSnow;

    public SnowArmorBoots(ItemMaterial Material, Type Slots) {
        super(Material, Slots, new Properties().rarity(CustomStyle.SnowItalic));
        Utils.maxHealth.put(this, MaxHealth);
        Utils.defence.put(this, Defence);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.3);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        SnowSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }

}
