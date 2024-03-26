package com.Very.very.Customize.Players.ShowDicker;

import com.Very.very.Customize.Customize;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemTier;
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

public class ShowdickerBow extends PickaxeItem {
    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double BreakDefence = 0.4F;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 4;
    private final double SpeedUp = 0.6F;
    public ShowdickerBow(Properties p_40524_) {
        super(ItemTier.VMaterial,2,0,p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfIce;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("专注射击").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 进行4次普通攻击后，你将可以连续释放8发箭矢").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        Compute.DescriptionPassive(components,Component.literal("切割").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的箭矢将根据目标当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("，提供至多").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4倍").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 世间万物，皆系于一箭之上").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚阿瓦罗萨的寒冰之弓，授予对维瑞阿契做出了杰出贡献的 - showdicker4").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.ShowdickerBow));
        return super.use(level, player, interactionHand);
    }
}
