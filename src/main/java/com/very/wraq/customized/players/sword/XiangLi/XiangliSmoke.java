package com.very.wraq.customized.players.sword.XiangLi;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class XiangliSmoke extends SwordItem{
    private final double BaseDamage = 800;

    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = Customize.CritRate;
    private final double CHitDamage = Customize.CritDamage;
    private final double HealSteal = Customize.HealthSteal;
    private final double SpeedUp = Customize.MovementSpeed;
    private final double AttackSpeedUp = -2f;
    public XiangliSmoke(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.FieldItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfField;
        Compute.ForgingHoverName(stack,Component.literal("妖刀-樱").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("悦刻五代").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("理塘王的信仰").withStyle(style));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Component.literal("理塘王").withStyle(style)).
                append(Component.literal("持有悦刻五代时，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("150%")).
                append(Component.literal("，并获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1.5格跳跃提升").withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionActive(components,Component.literal("烟雾缭绕").withStyle(style));
        components.add(Component.literal(" 理塘王").withStyle(style).
                append(Component.literal("抽一口悦刻五代").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("并用烟雾对周围敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("1000%")).
                append(Component.literal("同时对周围敌人造成致盲效果，并说一句藏话。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 怒马鲜衣少年郎，谁人不识理塘王！").withStyle(style));
        Compute.CoolDownTimeDescription(components,3);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一支悦刻五代，授予对维瑞阿契做出了杰出贡献的 - 一只香梨").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
