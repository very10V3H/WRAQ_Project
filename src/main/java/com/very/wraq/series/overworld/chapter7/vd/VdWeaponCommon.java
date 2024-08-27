package com.very.wraq.series.overworld.chapter7.vd;

import com.mojang.logging.LogUtils;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.*;

public interface VdWeaponCommon {

    Map<Integer, Integer> clientVdWeaponPassiveCountMap = new HashMap<>();

    // 数值需要确定
    static List<Component> additionalDescriptions(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfWorld;
        ComponentUtils.descriptionPassive(components, Component.literal("研习").withStyle(style));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(stack.getHoverName()).append(Component.literal("每秒对周围敌人施加一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("研习值").withStyle(style)).
                append(Component.literal("，至多5层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("研习值").withStyle(style)).
                append(Component.literal("会使你对其造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("8%").withStyle(CustomStyle.styleOfPower)));
        ComponentUtils.descriptionPassive(components, Component.literal("分析").withStyle(style));
        components.add(Component.literal(" 使用任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("主动效果").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("后，你的下一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通攻击").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("将拥有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("150%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的基础数值").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionActive(components, Component.literal("解构").withStyle(style));
        components.add(Component.literal(" 清空周围敌人的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("研习值").withStyle(style)).
                append(Component.literal("，每层对敌人造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.MaxHealth("4%")).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        ComponentUtils.manaCostDescription(components, 100);
        ComponentUtils.coolDownTimeDescription(components, 15);
        ComponentUtils.descriptionPassive(components, Component.literal("解决").withStyle(style));
        components.add(Component.literal(" 若").withStyle(ChatFormatting.WHITE).
                append(Component.literal("解构").withStyle(style)).
                append(Component.literal("成功击杀了敌人，将返还").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法力消耗").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("与重置其").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冷却时间").withStyle(CustomStyle.styleOfIce)));
        return components;
    }

    record CountOnMob(Mob mob, int count, int tick) {}

    static List<Mob> getNearMobs(Player player) {
        return player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 12, 12, 12)).stream().filter(mob -> mob.distanceTo(player) < 6).toList();
    }

    static void tick(Player player) {
        if (player.tickCount % 20 == 0) VdWeaponCommon.countAdd(player);
    }

    Map<String, List<CountOnMob>> countMap = new HashMap<>();

    static void countAdd(Player player) {
        if (!(player.getMainHandItem().getItem() instanceof VdWeaponCommon)) return;
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        if (!countMap.containsKey(name)) countMap.put(name, new ArrayList<>());
        List<CountOnMob> list = countMap.get(name);
        if (list.size() > 100) {
            list.clear();
            LogUtils.getLogger().error("VdWeapon passive list size bigger than 100");
        }
        getNearMobs(player).forEach(mob -> {
            CountOnMob countOnMob = null;
            List<CountOnMob> removeList = new ArrayList<>();
            for (CountOnMob countOnMob1 : list) {
                if (countOnMob1.mob == mob) countOnMob = countOnMob1;
                if (!countOnMob1.mob.isAlive()) removeList.add(countOnMob1);
            }
            if (countOnMob == null) {
                list.add(new CountOnMob(mob, 1, tick));
                ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.5, 6, CustomStyle.styleOfWorld.getColor().getValue());
            }
            else {
                list.add(new CountOnMob(mob, Math.min(5, countOnMob.count + 1), tick));
                if (countOnMob.count < 5) ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.5 + (countOnMob.count * 0.05), countOnMob.count * 6, CustomStyle.styleOfWorld.getColor().getValue());
                else {
                    ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.5 + (countOnMob.count * 0.05), countOnMob.count * 3, CustomStyle.styleOfWorld.getColor().getValue());
                    ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.5 + (countOnMob.count * 0.05), countOnMob.count * 3, CustomStyle.styleOfRed.getColor().getValue());
                }
                removeList.add(countOnMob);
            }
            list.removeAll(removeList);
        });
    }

    static double damageEnhance(Player player, Mob mob) {
        if (countMap.containsKey(player.getName().getString())) {
            List<CountOnMob> list = countMap.get(player.getName().getString());
            CountOnMob countOnMob = list.stream().filter(countOnMob1 -> countOnMob1.mob == mob).findAny().orElse(null);
            return countOnMob != null ? countOnMob.count * 0.08 : 0;
        }
        return 0;
    }

    Map<String, Boolean> intensifiedAttackMap = new HashMap<>();

    static void onReleaseActive(Player player, Item item) {
        if (item instanceof VdWeaponCommon) {
            intensifiedAttackMap.put(player.getName().getString(), true);
            Compute.sendEffectLastTime(player, item, 0, true);
        }
    }

    static double normalAttackRateEnhance(Player player) {
        boolean intensified = intensifiedAttackMap.getOrDefault(player.getName().getString(), false);
        intensifiedAttackMap.put(player.getName().getString(), false);
        List<Item> list = List.of(C7Items.vdSword.get(), C7Items.vdBow.get(), C7Items.vdSceptre.get());
        list.forEach(item -> Compute.removeEffectLastTime(player, item));
        return intensified ? 0.5 : 0;
    }

    static void active(Player player, Item item) {
        if (Compute.playerManaCost(player, 100)) {
            List<Item> list = List.of(C7Items.vdSword.get(), C7Items.vdBow.get(), C7Items.vdSceptre.get());
            list.forEach(item1 -> Compute.playerItemCoolDown(player, item1, 15));
            List<CountOnMob> countOnMobs = countMap.get(player.getName().getString());
            List<Mob> nearMobs = getNearMobs(player);
            Set<Mob> mobHashSet = new HashSet<>();
            Set<Mob> removeSet = new HashSet<>();
            Map<Mob, Integer> countMap = new HashMap<>();
            for (CountOnMob countOnMob : countOnMobs) {
                mobHashSet.add(countOnMob.mob);
                countMap.put(countOnMob.mob, countOnMob.count);
            }
            nearMobs.forEach(mob -> {
                if (mobHashSet.contains(mob)) {
                    removeSet.add(mob);
                    if (mob.isAlive()) {
                        Compute.Damage.IgnoreDefenceDamageToMonster_Direct(player, mob, mob.getMaxHealth() * 0.04 * countMap.get(mob));
                        if (mob.getHealth() < mob.getMaxHealth() * 0.04 * countMap.get(mob)) {
                            player.getCooldowns().removeCooldown(item);
                            Compute.playerManaAddOrCost(player, 100);
                        }
                    }
                }
            });
            countOnMobs.removeIf(countOnMob -> removeSet.contains(countOnMob.mob));
            ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfWorld.getColor().getValue());
        }
    }
}
