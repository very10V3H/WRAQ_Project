package com.Very.very.Series.NetherSeries.Equip;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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

public class QuartzSword extends SwordItem {
    private float BaseDamage = 80.0F;
    private float ManaDamage = 65.0F;
    private float BreakDefence = 0.4F;
    private float CriticalHitRate = 0.45F;
    private float CHitDamage = 1.0F;
    private float HealSteal = 0.08F;
    private float SpeedUp = 0.4F;
    public QuartzSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties());
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.ManaDamage.put(this,this.ManaDamage);
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
            if(data.contains("MANA") && data.getInt("MANA") < 30) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Compute.USE(player,this);
            }
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("夸塔兹之刃").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552"))).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfQuartz,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).
                append(Component.literal(" 65").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionOfAddtion(components);
        components.add(Component.literal("主动：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                append(Component.literal("夸塔兹能量涌动").withStyle(ChatFormatting.RESET).withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552")))));
        components.add(Component.literal("对周围所有单位雷击，造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("与").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("250%")));
        Compute.CoolDownTimeDescription(components,5);
        Compute.EmojiDescriptionManaCost(components,30);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfQuartz,ChatFormatting.WHITE);
        components.add(Component.literal("Quartz-Sword-0").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
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
}
