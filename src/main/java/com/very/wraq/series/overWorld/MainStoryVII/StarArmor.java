package com.very.wraq.series.overWorld.MainStoryVII;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class StarArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfMoon1;
    private String type = "";
    public StarArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,8192d);
        Utils.AttackDamage.put(this,500d);
        Utils.ManaDamage.put(this,2000d);
        Utils.Defence.put(this,1100d);
        Utils.ManaDefence.put(this,800d);
        Utils.CritDamage.put(this,0.7);
        Utils.CoolDownDecrease.put(this,0.3);
        Utils.SwiftnessUp.put(this,3d);
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
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("披星戴月").withStyle(style));
        components.add(Component.literal(" - 其一 手持近战武器时:").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 对距离5格以内的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" - 其二 手持远程武器时:").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 对距离5格以外的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" -多件梦月防具能够线性提升被动的伤害提升效果").withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryVII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.StarLeggings.get())
                || player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.StarHelmet.get())) {
            Item weapon = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Utils.SwordTag.containsKey(weapon) && mob.distanceTo(player) <= 5) return Compute.ArmorCount.Moon(player) * 0.25;
            if ((Utils.BowTag.containsKey(weapon) || Utils.SceptreTag.containsKey(weapon)) && mob.distanceTo(player) >= 5) return Compute.ArmorCount.Moon(player) * 0.25;
        }
        return 0;
    }
}
