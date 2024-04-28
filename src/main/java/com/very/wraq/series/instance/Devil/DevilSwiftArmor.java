package com.very.wraq.series.instance.Devil;

import com.very.wraq.Items.MobItem.MobArmor;
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

public class DevilSwiftArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfMana;
    private String type = "";
    public DevilSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,1728d);
        Utils.AttackDamage.put(this,240d);
        Utils.Defence.put(this,120d);
        Utils.ManaDefence.put(this,120d);
        Utils.SwiftnessUp.put(this,2d);
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
        Compute.DescriptionPassive(components,Component.literal("唤魔者之息").withStyle(style));
        components.add(Component.literal(" 箭矢命中目标时，为你提供等同于目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("加成，持续2s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Player,Double> DevilSwiftArmorPassiveNumMap = new HashMap<>();
    public static Map<Player,Integer> DevilSwiftArmorPassiveTickMap = new HashMap<>();

    public static void DevilSwiftArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.DevilSwiftBoots.get())) {
            if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof MobArmor mobArmor) {
                int TickCount = player.getServer().getTickCount();
                DevilSwiftArmorPassiveNumMap.put(player,mobArmor.Defence * 0.5);
                DevilSwiftArmorPassiveTickMap.put(player,TickCount + 40);
                Compute.EffectLastTimeSend(player,ModItems.DevilBlood.get().getDefaultInstance(),40);
            }
        }
    }

    public static double DevilSwiftArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilSwiftArmorPassiveTickMap.containsKey(player) && DevilSwiftArmorPassiveTickMap.get(player) > TickCount)
            return DevilSwiftArmorPassiveNumMap.get(player);
        return 0;
    }
}
