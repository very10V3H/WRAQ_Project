package fun.wraq.series.overworld.chapter7.star;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.DamageInfluenceCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class StarBottle extends WraqCurios implements DamageInfluenceCurios {

    public StarBottle(Properties p_41383_) {
        super(p_41383_);
        Utils.defencePenetration.put(this, 0.09);
        Utils.manaPenetration.put(this, 0.09);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfMoon1;
        Compute.DescriptionPassive(components, Component.literal("聚星集屑").withStyle(style));
        components.add(Component.literal(" 处于战斗状态时，每秒会收集一枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)));
        components.add(Component.literal(" 每拥有10枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getCommonDamageEnhance("3%")));
        components.add(Component.literal(" 当你拥有70枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("时，会开始释放收集到的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("星屑").withStyle(style)));
        components.add(Component.literal(" 每秒会将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("掷向半径16格内的目标;")));
        components.add(Te.s("每枚", "星屑", style, "造成", "(星屑数) * 10%", CustomStyle.styleOfSea,
                ComponentUtils.getAutoAdaptDamageDescription("")));
        components.add(Component.literal(" 只有当星星瓶中的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("被完全释放后，你才可以再次收集").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("星屑").withStyle(style)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterStar();
    }

    public static WeakHashMap<Player, Boolean> playerStatusMap = new WeakHashMap<>(); // false - 收集状态 true 掷出状态
    public static WeakHashMap<Player, Integer> playerCountsMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerLastBattleTick = new WeakHashMap<>();

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0 && PlayerIsInCollectMode(player)) countAdd(player);
        if (playerCountsMap.containsKey(player) && playerCountsMap.get(player) == 70) {
            playerStatusMap.put(player, true);
        }
        if (!playerStatusMap.containsKey(player) || (playerStatusMap.get(player) && playerCountsMap.get(player) <= 0)) {
            playerStatusMap.put(player, false);
        }
        if (playerStatusMap.get(player) && playerCountsMap.get(player) > 0 && player.tickCount % 20 == 0) {
            attackRange(player);
        }
    }

    public static void playerBattleTickMapRefresh(Player player) {
        playerLastBattleTick.put(player, Tick.get());
    }

    public static boolean PlayerIsInCollectMode(Player player) {
        return !playerStatusMap.containsKey(player) || !playerStatusMap.get(player);
    }

    private static void countAdd(Player player) {
        if (PlayerIsInCollectMode(player) && playerLastBattleTick.containsKey(player) && playerLastBattleTick.get(player) + 100 > Tick.get()) {
            playerCountsMap.put(player, Math.min(70, playerCountsMap.getOrDefault(player, 0) + 1));
            Compute.sendEffectLastTime(player, ModItems.StarBottle.get().getDefaultInstance(), 8888, playerCountsMap.get(player), true);
        }
    }

    private static void attackRange(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 40, 40, 40));
        mobList.removeIf(mob -> mob.distanceTo(player) > 16);
        mobList.forEach(mob -> {
            if (playerCountsMap.get(player) > 0) {
                playerCountsMap.put(player, playerCountsMap.get(player) - 1);
                Damage.causeAutoAdaptionRateDamageToMob(player, mob, (double) playerCountsMap.get(player) / 10, true);
                ParticleProvider.createLineParticle(player.level(), (int) mob.distanceTo(player) * 2, player.position(), mob.position(), ParticleTypes.FIREWORK);
            }
        });
        Compute.sendEffectLastTime(player, ModItems.StarBottle.get().getDefaultInstance(), 8888, playerCountsMap.get(player), true);
    }

    @Override
    public double rate(Player player) {
        if (playerCountsMap.containsKey(player)) {
            return playerCountsMap.get(player) * 0.03 / 10.0;
        }
        return 0;
    }
}
