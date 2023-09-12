package com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.BossItems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoBossSword extends SwordItem{
    private final float BaseDamage = 90.0F;
    private final float BreakDefence = 0.350F;
    private final float CriticalHitRate = 0.30F;
    private final float CHitDamage = 1.25F;
    private final float HealSteal = 0.05F;
    private final float SpeedUp = 0.4F;
    public VolcanoBossSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Rarity.EPIC));
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SwordTag.put(this,1.0F);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if(!level.isClientSide && useHand.equals(InteractionHand.MAIN_HAND))
        {
            CompoundTag data = player.getPersistentData();
            if(data.getInt("MANA") < 60) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("装备使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else {
                Compute.USE(player,player.getMainHandItem().getItem());
            }
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = Utils.styleOfVolcano;
        Compute.ForgingHoverName(stack,Component.literal("<次元级>").withStyle(ChatFormatting.GOLD).append(Component.literal("熔岩制造者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.ShortHandleSword));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);

        components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("熔岩维度展开: ").withStyle(ChatFormatting.RESET).withStyle(MainStyle)).
                append(Component.literal("森林制造者释放制造熔岩次元的能量，并将周围生物拖入属于熔岩次元。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("∰1.削减范围内的所有生物").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("⚔ 攻击力 * 熔岩次元熵").withStyle(ChatFormatting.RESET).withStyle(MainStyle)).
                append(Component.literal("的伤害并施加持续5s的减速效果。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

        Compute.CoolDownTimeDescription(components,10);
        Compute.EmojiDescriptionManaCost(components,60);
        components.add(Component.literal(" "));
        components.add(Component.literal("Dimension-Volcano").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
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

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
