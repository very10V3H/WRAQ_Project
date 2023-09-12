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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class ManaSword extends SwordItem {
    private float BaseDamage = 65.0F;
    private float BreakDefence = 0.0F;
    private float CriticalHitRate = 0.20F;
    private float CHitDamage = 0.75F;
    private float HealSteal = 0.00F;
    private float SpeedUp = 0.2F;
    public ManaSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Item.Properties());
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
            player.getCooldowns().addCooldown(this,(int) (100-100*Compute.PlayerCoolDownDecrease(player)));
            data.putInt("ManaSwordActive",data.getInt("MANA"));
            data.putInt("MANA",0);
            Compute.ManaStatusUpdate(player);
            Compute.PlayerPowerParticle(player);
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("玛莫提乌斯之噬").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ba00ff"))).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("魔力渴望").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana));
        components.add(Component.literal("玛莫提乌斯之噬对魔力十分渴望。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("攻击唤魔者/唤魔大师时，").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("无视其100%的护甲。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("攻击玩家时，").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("偷取玩家10%最大法力值。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionActive(components,Component.literal("魔力灌注").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana));
        components.add(Component.literal("将自身魔力全部灌注入玛莫提乌斯之噬，获得短暂的巨幅攻击力加成。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("消耗全部").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("并获得等同于消耗量的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        Compute.CoolDownTimeDescription(components,5);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Mana-Sword-0").withStyle(Utils.styleOfMana).withStyle(ChatFormatting.ITALIC));
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
