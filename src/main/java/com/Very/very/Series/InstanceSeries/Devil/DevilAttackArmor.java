package com.Very.very.Series.InstanceSeries.Devil;

import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevilAttackArmor extends ArmorItem {
    private static final Style style = Utils.styleOfMana;
    private String type = "";
    public DevilAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,3072d);
        Utils.AttackDamage.put(this,240d);
        Utils.Defence.put(this,400d);
        Utils.ManaDefence.put(this,200d);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("紫晶铁").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("封魔者之怒").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 受到来自怪物的伤害时，为你提供等同于怪物").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("加成，持续2s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Player,Double> DevilAttackArmorPassiveNumMap = new HashMap<>();
    public static Map<Player,Integer> DevilAttackArmorPassiveTickMap = new HashMap<>();

    public static void DevilAttackArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.DevilAttackChest.get())) {
            if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof MobArmor mobArmor) {
                int TickCount = player.getServer().getTickCount();
                DevilAttackArmorPassiveNumMap.put(player,mobArmor.AttackDamage * 0.5);
                DevilAttackArmorPassiveTickMap.put(player,TickCount + 40);
                Compute.EffectLastTimeSend(player,ModItems.DevilBlood.get().getDefaultInstance(),40);
            }
        }
    }

    public static double DevilAttackArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilAttackArmorPassiveTickMap.containsKey(player) && DevilAttackArmorPassiveTickMap.get(player) > TickCount)
            return DevilAttackArmorPassiveNumMap.get(player);
        return 0;
    }
}
