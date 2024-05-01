package com.very.wraq.series.instance.Moon.Equip;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class MoonArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfMoon1;
    private String type = "";
    public MoonArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,4096d);
        Utils.AttackDamage.put(this,250d);
        Utils.ManaDamage.put(this,1000d);
        Utils.Defence.put(this,550d);
        Utils.ManaDefence.put(this,400d);
        Utils.CritDamage.put(this,0.35);
        Utils.CoolDownDecrease.put(this,0.15);
        Utils.SwiftnessUp.put(this,1.5d);
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
        Compute.DescriptionPassive(components,Component.literal("阴晴圆缺").withStyle(style));
        components.add(Component.literal(" - 其一 手持近战武器时:").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 对距离5格以内的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("15%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" - 其二 手持远程武器时:").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 对距离5格以外的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("15%伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" -多件尘月防具能够线性提升被动的伤害提升效果").withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MoonLeggings.get())
                || player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MoonHelmet.get())) {
            Item weapon = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Utils.SwordTag.containsKey(weapon) && mob.distanceTo(player) <= 5) return Compute.ArmorCount.Moon(player) * 0.15;
            if ((Utils.BowTag.containsKey(weapon) || Utils.SceptreTag.containsKey(weapon)) && mob.distanceTo(player) >= 5) return Compute.ArmorCount.Moon(player) * 0.15;
        }
        return 0;
    }
}
