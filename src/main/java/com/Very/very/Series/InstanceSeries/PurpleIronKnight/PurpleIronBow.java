package com.Very.very.Series.InstanceSeries.PurpleIronKnight;

import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PurpleIronBow extends PickaxeItem {

    private static final double[] ExAttackDamage = {
            200,300,400,500
    };

    private static final double[] CritDamage = {
            0.4,0.6,0.8,1
    };

    private static final double[] Swiftness = {
            1,2,3,4
    };

    public PurpleIronBow(Properties p_40524_, int tier) {
        super(ItemTier.VMaterial,2,0,p_40524_);
        Utils.AttackDamage.put(this,ExAttackDamage[tier]);
        Utils.CritDamage.put(this,CritDamage[tier]);
        Utils.SwiftnessUp.put(this,Swiftness[tier]);
        Utils.PassiveEquipTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.LevelRequire.put(this,160);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfPurpleIron;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("器灵                   ").withStyle(Utils.styleOfSakura).append(Component.literal("长弓").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("晶体析构").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，每1000差值提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.LevelRequire(components,Utils.LevelRequire.get(this));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfPurpleIronKnight(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.experienceLevel < 160) return 0;
        boolean isOn = false;
        Inventory inventory = player.getInventory();
        for (int i = 3 ; i < 9 ; i ++) {
            if (inventory.getItem(i).getItem() instanceof PurpleIronBow) isOn = true;
        }
        if (!isOn) return 0;
        ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
        double ManaDefence = 0;
        if (helmet.getItem() instanceof MobArmor mobArmor) ManaDefence = mobArmor.ManaDefence;
        return Math.abs(Compute.PlayerAttributes.PlayerManaDefence(player) - ManaDefence) * 0.25 / 1000;
    }
}
