package com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

public class BlackForestSword0 extends SwordItem{
    private final float BaseDamage = 40.0F;
    private final float BreakDefence = 0.20F;
    private final float CriticalHitRate = 0.30F;
    private final float CHitDamage = 0.50F;
    private final float HealSteal = 0.05F;
    private final float SpeedUp = 0.1F;
    private final float AttackRangeUp = 1.0F;
    public BlackForestSword0(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties());
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.AttackRangeUp.put(this,this.AttackRangeUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SwordTag.put(this,1.0F);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if(!level.isClientSide && useHand.equals(InteractionHand.MAIN_HAND))
        {
            CompoundTag data = player.getPersistentData();
            if(data.getInt("MANA") < 10) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("装备使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else Compute.USE(player,player.getMainHandItem().getItem());
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("灵魂收割者").withStyle(Utils.styleOfBlackForest).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("斧头").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_RED)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfBlackForest,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp,AttackRangeUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfBlackForest,ChatFormatting.WHITE);
        BlackForestSword0.BlackForestSwordCommonDescription(components,"0");
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
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }
    public static void BlackForestSwordCommonDescription (List<Component> components, String Num) {
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("土葬").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest));
        components.add(Component.literal("使下一次的普通攻击附带:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal("目标当前生命值9%的物理伤害。").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea));
        components.add(Component.literal("对怪物造成的基于生命值的伤害最大不超过攻击力的10倍").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("若目标死亡，则获得自身").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("25%")).
                append(Component.literal("的护盾,持续10s。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,3);
        Compute.EmojiDescriptionManaCost(components,10);
        components.add(Component.literal("BlackForest-Sword-" + Num).withStyle(Utils.styleOfBlackForest).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }
}
