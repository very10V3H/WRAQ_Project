package com.very.wraq.series.overWorld.Castle;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BeaconBow extends PickaxeItem {

    private static final double[] ExAttackDamage = {
            100,150,200,250
    };

    private static final double[] CritDamage = {
            0.1,0.2,0.25,0.3
    };

    private static final double[] Swiftness = {
            0.5,1,1.5,2
    };


    public BeaconBow(Properties p_40524_, int tier) {
        super(ItemTier.VMaterial,2,0,p_40524_);
        Utils.AttackDamage.put(this,ExAttackDamage[tier]);
        Utils.CritDamage.put(this,CritDamage[tier]);
        Utils.SwiftnessUp.put(this,Swiftness[tier]);
        Utils.PassiveEquipTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.LevelRequire.put(this,140);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfPower;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.LevelRequire(components,Utils.LevelRequire.get(this));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
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
}
