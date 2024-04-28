package com.very.wraq.series.instance.Taboo;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import com.very.wraq.valueAndTools.registry.ModItems;
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

public class TabooAttackArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfBloodMana;
    private String type = "";
    public TabooAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,4096d);
        Utils.AttackDamage.put(this,300d);
        Utils.Defence.put(this,500d);
        Utils.ManaDefence.put(this,240d);
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
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("禁忌秘法-加护").withStyle(style));
        components.add(Component.literal(" 当受到来自怪物的伤害时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("，则消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("来使即将到来的伤害减少80%").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.TabooAttackLeggings.get());
    }

    public static double Passive(Player player) {
        if (!IsOn(player)) return 1;
        if (Compute.PlayerCurrentManaNum(player) / Compute.PlayerMaxManaNum(player) > 0.1) {
            Compute.PlayerManaAddOrCost(player, (- Compute.PlayerMaxManaNum(player) * 0.1));
            return 0.2;
        }
        return 1;
    }



}
