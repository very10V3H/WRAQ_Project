package com.Very.very.Series.NetherSeries.Equip;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherArmorHelmet extends ArmorItem {
    private final float defence = 50.0F;
    private final float healup = 100.0F;
    public NetherArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Rarity.UNCOMMON));
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("要塞之核").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        NetherArmorHelmet.NetherArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void NetherArmorCommonDescription (List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("亡灵之力").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether));
        components.add(Component.literal("将所有的").
                append(Compute.AttributeDescription.ExHealth("")).
                append(Component.literal("转换为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("")));
        components.add(Component.literal("将每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthRecover("1")).
                append(Component.literal("转换为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("10%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal("NetherArmor-I").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
    }
}
