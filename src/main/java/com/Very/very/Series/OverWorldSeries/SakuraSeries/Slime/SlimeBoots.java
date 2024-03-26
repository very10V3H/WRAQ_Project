package com.Very.very.Series.OverWorldSeries.SakuraSeries.Slime;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SlimeBoots extends ArmorItem {
    public SlimeBoots(ItemMaterial Materrial, Type Slots) {
        super(Materrial,Slots,new Properties().rarity(Utils.LifeItalic));
        Utils.AttackDamage.put(this,45d);
        Utils.ManaDamage.put(this,250d);
        Utils.ManaDefence.put(this,200d);
        Utils.Defence.put(this,100d);
        Utils.MovementSpeed.put(this,0.4);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfHealth;
        Compute.ForgingHoverName(stack,Component.literal("莘岛史莱姆鞋子").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("Q弹史莱姆！").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 使穿戴者获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("跳跃提升！").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("史莱姆缓冲！").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 使你受到的伤害直接减少等同于你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10%")).
                append(Component.literal("的数额！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SlimeBoots.get());
    }
}
