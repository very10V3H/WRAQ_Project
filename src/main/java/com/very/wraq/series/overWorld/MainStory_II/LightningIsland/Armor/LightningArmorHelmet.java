package com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LightningArmorHelmet extends ArmorItem {
    double Defence = 80;
    double MaxHealth = 600;
    public LightningArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Utils.LightningItalic));
        Utils.Defence.put(this, Defence);
        Utils.MaxHealth.put(this, MaxHealth);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("唤雷之顶").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(CustomStyle.styleOfLightingIsland)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfLightingIsland,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfLightingIsland,ChatFormatting.WHITE);
        LightningArmorHelmet.LightningArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void LightningArmorCommonDescription (List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("唤雷庇护").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal("1.攻击/受到攻击时，对目标/攻击者造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("伤害的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("雷击。").withStyle(CustomStyle.styleOfLightingIsland)).
                append(Component.literal("(多件唤雷装备的效果能够叠加)").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("2.免疫").withStyle(ChatFormatting.WHITE).
                append(Component.literal("唤雷岛雷击与神秘力量。").withStyle(CustomStyle.styleOfLightingIsland)));
        components.add(Component.literal("以雷霆，击碎黑暗！").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfLightingIsland,ChatFormatting.WHITE);
        components.add(Component.literal("IslandArmor-I").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }
}
