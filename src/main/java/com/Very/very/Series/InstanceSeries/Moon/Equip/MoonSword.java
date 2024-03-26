package com.Very.very.Series.InstanceSeries.Moon.Equip;

import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonSword extends SwordItem{
    public static Map<Player,Boolean> playerFlagMap = new HashMap<>();

    private final double BaseDamage = 480;
    private final double DefencePenetration0 = 3200;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 2.40d;
    private final double HealSteal = 0.08F;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public MoonSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.Moon1Italic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
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
        Style style = Utils.styleOfMoon1;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("星剑").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("行星之引").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("永升之星").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("将").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("目标周围半径6内所有单位的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("，提供在10s内持续衰减的等额").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("")));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("400%")).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.CoolDownTimeDescription(components,27);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
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

    public static Map<Player,Double> attackDamageNumMap = new HashMap<>();
    public static Map<Player,Integer> attackDamageTickMap = new HashMap<>();

    public static void Active(Player player) {
        playerFlagMap.put(player,true);
        Compute.EffectLastTimeSend(player, ModItems.MoonSword.get().getDefaultInstance(),8888,0,true);
    }

    public static void MoonSwordActive(Player player, Mob mob) {
        if (playerFlagMap.containsKey(player) && playerFlagMap.get(player)) {
            playerFlagMap.put(player,false);
            Compute.PlayerShieldProvider(player,400,Compute.PlayerAttributes.PlayerAttackDamage(player) * 4);
            Compute.EffectLastTimeSend(player,ModItems.MoonSword.get().getDefaultInstance(),200);
            List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class,AABB.ofSize(mob.position(),15,15,15));
            mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
            double attackDamage = 0;
            for (Mob mob1 : mobList) {
                ItemStack helmet = mob1.getItemBySlot(EquipmentSlot.HEAD);
                if (helmet.getItem() instanceof MobArmor mobArmor) {
                    attackDamage += mobArmor.AttackDamage;
                }
            }
            List<Player> playerList = mob.level().getEntitiesOfClass(Player.class,AABB.ofSize(mob.position(),15,15,15));
            playerList.removeIf(player1 -> player1.distanceTo(mob) > 6);
            for (Player player1 : playerList) {
                attackDamage += Compute.PlayerAttributes.PlayerAttackDamage(player1);
            }
            attackDamageNumMap.put(player,attackDamage);
            attackDamageTickMap.put(player,player.getServer().getTickCount() + 200);
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSword.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1,2,mob.position());
        });
    }

    public static double ExAttackDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        if (attackDamageTickMap.containsKey(player) && attackDamageTickMap.get(player) > tickCount) {
            return attackDamageNumMap.get(player) * (attackDamageTickMap.get(player) - tickCount) / 200;
        }
        return 0;
    }
}
