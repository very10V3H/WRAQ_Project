package com.very.wraq.series.instance.Moon.Equip;

import com.very.wraq.Items.MobItem.MobArmor;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonSceptre extends SwordItem {

    public static Map<Player,Integer> coolDownMap = new HashMap<>();
    public MoonSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.ManaDamage.put(this, 2560d);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, 3200d);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.CoolDownDecrease.put(this,0.2);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon1;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("噬星之月 ").withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(style)).
                append(Component.literal("目标周围半径6内所有单位的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("，提供在10s内持续衰减的4倍").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExManaDamage("")));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        Compute.CoolDownTimeDescription(components,27);
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.ManaAttack(player,11);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static Map<Player,Double> manaDamageNumMap = new HashMap<>();
    public static Map<Player,Integer> manaDamageTickMap = new HashMap<>();

    public static void MoonSceptreActive(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSceptre.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < TickCount) {
                coolDownMap.put(player, (int) (TickCount + 540 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
                Compute.CoolDownTimeSend(player,ModItems.MoonSceptre.get().getDefaultInstance(), (int) (540 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
                Compute.PlayerShieldProvider(player,400,PlayerAttributes.PlayerManaDamage(player));
                Compute.EffectLastTimeSend(player,ModItems.MoonSceptre.get().getDefaultInstance(),200);
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
                    attackDamage += PlayerAttributes.PlayerAttackDamage(player1);
                }
                manaDamageNumMap.put(player,attackDamage);
                manaDamageTickMap.put(player,player.getServer().getTickCount() + 200);

            }
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSceptre.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1,2,mob.position());
        });
    }

    public static double ExManaDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        if (manaDamageTickMap.containsKey(player) && manaDamageTickMap.get(player) > tickCount) {
            return manaDamageNumMap.get(player) * 4 * (manaDamageTickMap.get(player) - tickCount) / 200;
        }
        return 0;
    }
}
