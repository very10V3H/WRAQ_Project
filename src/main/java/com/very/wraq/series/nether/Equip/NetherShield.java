package com.very.wraq.series.nether.Equip;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherShield extends Item{
    private final double Defence = 250;
    private final double MaxHealth = 250;
    private final double AttackDamage = 20;
    private final double CritDamage = 0.15;
    private final double ExpUp = 0.75;

    public NetherShield() {
        super(new Properties().rarity(Utils.NetherItalic).stacksTo(1));
        Utils.Defence.put(this,Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.CritDamage.put(this,CritDamage);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.ShieldTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = CustomStyle.styleOfNether;
        stack.setHoverName(Component.literal("遗骸铸盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("盾击").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 基于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("为你至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components,Component.literal("碎骨化灰").withStyle(MainStyle));
        components.add(Component.literal(" -若目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("高于你的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("则为你提供等同于差值的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")));
        components.add(Component.literal(" -若目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("低于你的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("则为你提供基于差值的至多").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" -效果均持续5s").withStyle(MainStyle));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("NetherShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
