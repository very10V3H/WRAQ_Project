package com.very.wraq.series.instance.PurpleIronKnight;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
            0.15,0.2,0.25,0.35
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
        Style style = CustomStyle.styleOfPurpleIron;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("晶体析构").withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(style)).
                append(Component.literal("至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%伤害提升").withStyle(style)));
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
        double ManaDefence = 0;
        ManaDefence = Compute.MonsterManaDefence(mob);
        double value = Math.abs(Compute.PlayerAttributes.PlayerManaDefence(player) - ManaDefence);
        return (0.5 - (250 / (500 + value)));
    }
}
