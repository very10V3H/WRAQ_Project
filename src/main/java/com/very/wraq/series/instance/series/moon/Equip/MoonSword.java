package com.very.wraq.series.instance.series.moon.Equip;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonSword extends WraqSword implements ActiveItem {

    public static Map<Player, Boolean> playerFlagMap = new HashMap<>();

    public MoonSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1200d);
        Utils.defencePenetration0.put(this, 2900d);
        Utils.healthSteal.put(this, 0.3);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components, Component.literal("永升之星").withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(style)).
                append(Component.literal("目标周围半径6内所有单位的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("，提供在10s内持续衰减的等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("25%")));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("400%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        Compute.CoolDownTimeDescription(components, 27);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Player, Double> attackDamageNumMap = new HashMap<>();
    public static Map<Player, Integer> attackDamageTickMap = new HashMap<>();

    public static void Active(Player player) {
        playerFlagMap.put(player, true);
        Compute.sendEffectLastTime(player, ModItems.MoonSword.get().getDefaultInstance(), 8888, 0, true);
    }

    public static void MoonSwordActive(Player player, Mob mob) {
        if (playerFlagMap.containsKey(player) && playerFlagMap.get(player)) {
            playerFlagMap.put(player, false);
            Compute.playerShieldProvider(player, 400, PlayerAttributes.attackDamage(player) * 4);
            Compute.sendEffectLastTime(player, ModItems.MoonSword.get().getDefaultInstance(), 200);
            List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
            mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
            double attackDamage = 0;
            for (Mob mob1 : mobList)
                attackDamage += MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob1, MobSpawn.MobBaseAttributes.attackDamage);
            List<Player> playerList = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 15, 15, 15));
            playerList.removeIf(player1 -> player1.distanceTo(mob) > 6);
            for (Player player1 : playerList) {
                attackDamage += PlayerAttributes.attackDamage(player1);
            }
            attackDamageNumMap.put(player, attackDamage);
            attackDamageTickMap.put(player, player.getServer().getTickCount() + 200);
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSword.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1, 2, mob.position());
        });
    }

    public static double ExAttackDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        if (attackDamageTickMap.containsKey(player) && attackDamageTickMap.get(player) > tickCount) {
            return attackDamageNumMap.get(player) * (attackDamageTickMap.get(player) - tickCount) * 0.25 / 200;
        }
        return 0;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            Compute.playerItemCoolDown(player, ModItems.MoonSword.get(), 27);
            MoonSword.Active(player);
        }
    }
}
