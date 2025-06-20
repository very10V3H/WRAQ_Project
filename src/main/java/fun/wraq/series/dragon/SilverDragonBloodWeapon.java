package fun.wraq.series.dragon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.damage.OnCauseFinalDamageEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;

public interface SilverDragonBloodWeapon extends OnCauseFinalDamageEquip, ActiveItem {
    Map<String, Set<Mob>> mobsMap = new HashMap<>();

    static void onCauseDamage(Player player, Mob mob) {
        if (!mobsMap.containsKey(Name.get(player))) {
            mobsMap.put(Name.get(player), new HashSet<>());
        }
        Set<Mob> mobs = mobsMap.get(Name.get(player));
        mobs.add(mob);
    }

    int getMaxCount();

    Map<String, Integer> playerCountMap = new HashMap<>();
    Map<Mob, Integer> mobCountMap = new HashMap<>();
    Map<Mob, Integer> mobMaxCountMap = new HashMap<>();

    String MOB_EFFECT_HUD_TAG = "SilverDragonBloodWeaponMobCount";
    static void addCountToMob(Mob mob, int maxCount, int count) {
        mobCountMap.compute(mob, (k, v) -> v == null ? count : Math.min(maxCount, v + count));
        mobMaxCountMap.compute(mob,
                (k, v) -> v == null ? maxCount : Math.max(v, maxCount));
        Compute.sendMobEffectHudToNearPlayer(mob, SpecialEventItems.DRAGON_DIAMOND.get(),
                MOB_EFFECT_HUD_TAG, 8888, mobCountMap.get(mob), true);
    }

    static void addCountToPlayer(Player player, int maxCount, int count) {
        playerCountMap.compute(Name.get(player),
                (k, v) -> v == null ? count : Math.min(maxCount, v + count));
        Compute.sendEffectLastTime(player, SpecialEventItems.DRAGON_DIAMOND.get(),
                playerCountMap.get(Name.get(player)), true);
    }

    static void handleTick(Player player) {
        if (player.getMainHandItem().getItem() instanceof SilverDragonBloodWeapon weapon) {
            if (player.tickCount % 20 == 1 && mobsMap.containsKey(Name.get(player))) {
                Set<Mob> mobs = mobsMap.get(Name.get(player));
                if (!mobs.isEmpty()) {
                    addCountToPlayer(player, weapon.getMaxCount(), 1);
                    mobs.forEach(mob -> {
                        addCountToMob(mob, weapon.getMaxCount(), 1);
                    });
                    mobs.clear();
                }
            }
        }
    }

    static void commonActive(Player player) {
        Item item = player.getMainHandItem().getItem();
        if (item instanceof SilverDragonBloodWeapon weapon) {
            if (player.isShiftKeyDown()) {
                int sum = Compute.getNearMob(player, 6).stream().mapToInt(mob -> {
                    int count = mobCountMap.getOrDefault(mob, 0);
                    mobCountMap.put(mob, 0);
                    Compute.removeMobEffectHudToNearPlayer(mob,
                            SpecialEventItems.DRAGON_DIAMOND.get(), MOB_EFFECT_HUD_TAG);
                    ParticleProvider.createLineEffectParticle(player.level(),
                            mob, player, CustomStyle.SILVER_DRAGON_STYLE);
                    return count;
                }).sum();
                addCountToPlayer(player, weapon.getMaxCount(), sum);
                MySound.soundToPlayer(player, SoundEvents.ENDER_DRAGON_SHOOT);
            } else {
                int count = playerCountMap.getOrDefault(Name.get(player), 0);
                if (count > 0) {
                    if (item instanceof WraqSword) {
                        Compute.getNearMob(player, 6).forEach(mob -> {
                            addCountToMob(mob, weapon.getMaxCount(), count);
                            ParticleProvider.createLineEffectParticle(player.level(),
                                    player, mob, CustomStyle.SILVER_DRAGON_STYLE);
                        });
                    } else {
                        Compute.getNearMob(player.level(), WraqPower.getDefaultTargetPos(player, 32), 6)
                                .forEach(mob -> {
                                    addCountToMob(mob, weapon.getMaxCount(), count);
                                    ParticleProvider.createLineEffectParticle(player.level(),
                                            player, mob, CustomStyle.SILVER_DRAGON_STYLE);
                                });
                    }
                    playerCountMap.put(Name.get(player), 0);
                    Compute.removeEffectLastTime(player, SpecialEventItems.DRAGON_DIAMOND.get());
                }
                MySound.soundToPlayer(player, SoundEvents.ENDER_DRAGON_SHOOT);
            }
            Compute.playerItemCoolDown(player, item, 5);
        }
    }

    static void onMobDead(Mob mob) {
        int count = mobCountMap.getOrDefault(mob, 0);
        if (count > 0) {
            Compute.getNearMob(mob.level(), mob.position(), 6).forEach(eachMob -> {
                ParticleProvider.createLineEffectParticle(mob.level(),
                        mob, eachMob, CustomStyle.SILVER_DRAGON_STYLE);
                addCountToMob(eachMob, mobMaxCountMap.getOrDefault(mob, 0), count);
            });
        }
        mobCountMap.remove(mob);
        mobCountMap.entrySet().removeIf(entry -> entry.getKey().isDeadOrDying() || entry.getKey() == null);
        mobMaxCountMap.remove(mob);
        mobMaxCountMap.entrySet().removeIf(entry -> entry.getKey().isDeadOrDying() || entry.getKey() == null);
    }

    static double getMobWithstandDamageRate(Mob mob) {
        return mobCountMap.getOrDefault(mob, 0) * 0.01;
    }

    static List<Component> getCommonDescription(int maxTier, Item item) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.SILVER_DRAGON_STYLE;
        ComponentUtils.descriptionPassive(components, Te.s("银龙之血", CustomStyle.SILVER_DRAGON_STYLE));
        components.add(Te.s(" 每与敌方战斗1s，自身与敌方获得1层", "银龙之血.", style));
        components.add(Te.s(" 带有", "银龙之血", style, "的敌方，受到的伤害将提升"));
        components.add(Te.s(" 其拥有", "银龙之血", style, "层数 * 1%"));
        components.add(Te.s(" 在敌方后，其", "银龙之血", style, "将扩散至周围其他敌方."));
        components.add(Te.s(" 银龙之血", style, "最大层数: ", maxTier, style));
        components.add(Te.s(" 银龙之血扩散时，敌方的最大层数取决于", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 曾造成过伤害的玩家武器最大层数", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        ComponentUtils.descriptionActive(components, Te.s("血之主", CustomStyle.SILVER_DRAGON_STYLE));
        components.add(Te.s(" · 将自身的", "银龙之血", style,
                "施加给" + (item instanceof WraqSword ? "周围" : "准星附近") + "所有敌人."));
        components.add(Te.s(" · 按shift释放，将汲取周围所有敌方的", "银龙之血", style));
        components.add(Te.s(" · ", "冷却时间 5s", ChatFormatting.AQUA));
        return components;
    }
}
