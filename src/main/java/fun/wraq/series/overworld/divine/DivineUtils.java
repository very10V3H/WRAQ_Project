package fun.wraq.series.overworld.divine;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.mob.boss.DivineBalanceInstance;
import fun.wraq.series.overworld.divine.mob.boss.DivineBunnyInstance;
import fun.wraq.series.overworld.divine.mob.common.DivineGolemSpawnController;
import fun.wraq.series.overworld.divine.mob.common.DivineSentrySpawnController;
import fun.wraq.series.overworld.divine.mob.common.GhastlyCreeperSpawnController;
import fun.wraq.series.overworld.divine.mob.common.GhastlyHuskSpawnController;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DivineUtils {
    public static Style style = CustomStyle.DIVINE_STYLE;
    public static String currentDayElement = Element.life;
    public static final Vec3 ToDivineIslandBoatPos = new Vec3(1899.5, 70, 295.5);
    public static final Vec3 InDivineIslandBoatPos = new Vec3(2294.5, 82, 980.5);
    public static final Vec3 ToSunIslandBoatPos = new Vec3(2270.5, 70, 874.5);
    public static final Vec3 InSunIslandBoatPos = new Vec3(1899.5, 67, 304.5);

    public static void handleServerLevelEvent(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (event.phase == TickEvent.Phase.END && level.dimension().equals(Level.OVERWORLD) && !level.isClientSide) {
            if (level.getDayTime() % 24000 == 1) {
                currentDayElement = Element.elementList.get((int) (level.getDayTime() / 24000 % 7));
                Tick.server.getPlayerList().getPlayers()
                        .stream().filter(serverPlayer -> serverPlayer.experienceLevel >= 230)
                        .forEach(serverPlayer -> {
                            sendMSG(serverPlayer, Te.s("圣光岛", style, "的",
                                    "共鸣元素", CustomStyle.styleOfWorld,
                                    "已变更为", Element.getElementDescription(currentDayElement)));
                        });
            }
        }
    }

    public static boolean isInDivineIsland(Player player) {
        return player.level().dimension().equals(Level.OVERWORLD)
                && player.getX() > 2000 && player.getX() < 2730
                && player.getZ() > 170 && player.getZ() < 920;
    }

    public static void decreasePlayerHealth(Player player, double value) {
        Compute.decreasePlayerHealth(player, value, Te.s("被", "圣光", style, "辐照飞升"));
    }

    public static void handlePlayerTick(Player player) {
        if (player.level().dimension().equals(Level.OVERWORLD)) {
            if (player.position().distanceTo(ToDivineIslandBoatPos) < 2) {
                Compute.teleportPlayerToPos(player, InDivineIslandBoatPos);
            } else if (player.position().distanceTo(ToSunIslandBoatPos) < 2) {
                Compute.teleportPlayerToPos(player, InSunIslandBoatPos);
            }
            if (isInDivineIsland(player)) {
                if (player.experienceLevel < 230) {
                    decreasePlayerHealth(player, player.getMaxHealth() * 0.005);
                    if (player.tickCount % 20 == 0) {
                        sendMSG(player, Te.s("未达到", Utils.getLevelDescription(230),
                                "无法承受", "圣光的辐照.", style));
                    }
                }
                if (player.isFallFlying()) {
                    decreasePlayerHealth(player, player.getMaxHealth() * 0.005);
                    if (player.tickCount % 20 == 0) {
                        addHolyLightCount(player, 5);
                        sendMSG(player, Te.s("在圣光岛飞行似乎会受到强烈的辐照!"));
                    }
                }
            }
        }
        handleHolyLightTick(player);
    }

    public static void handleMobTick(Mob mob) {
        Element.provideElement(mob, currentDayElement, 6);
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600));
        if (manifestExpiredTickMap.getOrDefault(mob, 0) > Tick.get()) {
            mob.removeEffect(MobEffects.INVISIBILITY);
        } else {
            mob.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600));
        }
    }

    public static void provideElementToMob(Mob mob) {
        Element.provideElement(mob, currentDayElement, 6);
    }

    public final static Set<String> MOB_NAME_SET = new HashSet<>() {{
        add(DivineSentrySpawnController.mobName);
        add(DivineGolemSpawnController.mobName);
        add(DivineBunnyInstance.mobName);
    }};

    public final static Set<String> GHASTLY_MOB_NAME_SET = new HashSet<>() {{
        add(GhastlyCreeperSpawnController.mobName);
        add(GhastlyHuskSpawnController.mobName);
    }};

    public static void onPlayerWithstandDamage(Mob mob, Player player) {
        if (MOB_NAME_SET.contains(MobSpawn.getMobOriginName(mob))) {
            SpecialEffectOnPlayer.addHealingReduction(player, "DivineMobHealingReduction", 40);
            SpecialEffectOnPlayer.slowdownEffectProvide(player, 40, 0.8);
        }
        if (GHASTLY_MOB_NAME_SET.contains(MobSpawn.getMobOriginName(mob))) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40));
            SpecialEffectOnPlayer.slowdownEffectProvide(player, 40, 0.8);
        }
        onWithstandMobDamage(player);
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("圣光岛", style), content);
    }

    public static void createDivineParticle(Player player, Vec3 startPos, Vec3 finalPos) {
        ParticleProvider.createLineParticle(player.level(), (int) finalPos.distanceTo(startPos) * 5, startPos,
                finalPos, ParticleTypes.END_ROD);
    }

    public static void createDivineParticle(Level level, Vec3 startPos, Vec3 finalPos) {
        ParticleProvider.createLineParticle(level, (int) finalPos.distanceTo(startPos) * 5, startPos,
                finalPos, ParticleTypes.END_ROD);
    }

    public static Map<String, Integer> holyLightCountMap = new HashMap<>();

    public static void setHolyLightCount(Player player, int count) {
        holyLightCountMap.put(Name.get(player), count);
        if (holyLightCountMap.get(Name.get(player)) == 0) {
            Compute.removeEffectLastTime(player, DivineIslandItems.DIVINE_RUNE_ARMOR.get());
        }
    }

    public static int getHolyLightCount(Player player) {
        return holyLightCountMap.getOrDefault(Name.get(player), 0);
    }

    public static void addHolyLightCount(Player player, int count) {
        holyLightCountMap.compute(Name.get(player), (k, v) -> v == null ? count : Math.min(1000, v + count));
        Compute.sendEffectLastTime(player, DivineIslandItems.DIVINE_RUNE_ARMOR.get(),
                holyLightCountMap.get(Name.get(player)), true);
    }

    public static void decreaseHolyLightCount(Player player, int count) {
        if (holyLightCountMap.getOrDefault(Name.get(player), 0) <= 0) {
            return;
        }
        holyLightCountMap.compute(Name.get(player), (k, v) -> v == null ? 0 : Math.max(0, v - count));
        if (holyLightCountMap.get(Name.get(player)) == 0) {
            Compute.removeEffectLastTime(player, DivineIslandItems.DIVINE_RUNE_ARMOR.get());
        } else {
            Compute.sendEffectLastTime(player, DivineIslandItems.DIVINE_RUNE_ARMOR.get(),
                    holyLightCountMap.get(Name.get(player)), true);
        }
    }

    public static void onPlayerKillMob(Player player, Mob mob) {
        if (isInDivineIsland(player)) {
            String mobName = MobSpawn.getMobOriginName(mob);
            if (MOB_NAME_SET.contains(mobName)) {
                addHolyLightCount(player, 1);
            }
            if (GHASTLY_MOB_NAME_SET.contains(mobName)) {
                decreaseHolyLightCount(player, 1);
            }
            if (RandomUtils.nextDouble(0, 1) < 0.1) {
                MySound.soundToPlayer(player, SoundEvents.BLAZE_SHOOT);
                Compute.createRangeEffectDot(player.level(), player.position(), 4, (eachPlayer -> {
                    Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.1,
                            Te.s("被", "圣光辐照", style, "飞升"));
                }), style, Tick.get() + 20, 6, 80);
            }
        }
    }

    public static void handleHolyLightTick(Player player) {
        if (player.tickCount % 100 == 14 && !Compute.playerIsInBattle(player, Tick.s(60))) {
            if (getHolyLightCount(player) > 0) {
                decreaseHolyLightCount(player, 10);
                decreasePlayerHealth(player, player.getMaxHealth() * 0.1);
            }
        }
    }

    public static void onWithstandMobDamage(Player player) {
        if (isInDivineIsland(player) && getHolyLightCount(player) > 0) {
            decreaseHolyLightCount(player, 5);
            decreasePlayerHealth(player, player.getMaxHealth() * 0.05);
        }
    }

    // 属性改动
    public static double getPlayerWithstandDamageExRate(Player player) {
        return isInDivineIsland(player) ? getHolyLightCount(player) * 0.01 : 0;
    }

    public static double getPlayerElementStrengthDecreaseRate(Player player) {
        return isInDivineIsland(player) ? getHolyLightCount(player) * 0.001 : 0;
    }

    public static double getPlayerExCommonDamageEnhanceRate(Player player) {
        return isInDivineIsland(player) ? getHolyLightCount(player) * 0.002 : 0;
    }

    public static Map<Mob, Integer> manifestExpiredTickMap = new HashMap<>();

    public static void addManifestOnMob(Mob mob, int lastTick, Item icon) {
        manifestExpiredTickMap.entrySet().removeIf(entry -> entry.getKey().isDeadOrDying());
        manifestExpiredTickMap.put(mob, Tick.get() + lastTick);
        Compute.sendMobEffectHudToNearPlayer(mob, icon, "DivineManifest", lastTick, 0, false);
    }

    public static Set<String> manifestControlMobs = new HashSet<>() {{
        add(DivineBalanceInstance.mobName);
    }};

    public static Map<String, Integer> nextAllowSendMSGTickMap = new HashMap<>();

    public static double getManifestMobDamageModifyRate(Player player, Mob mob) {
        if (manifestControlMobs.contains(MobSpawn.getMobOriginName(mob))
                && manifestExpiredTickMap.getOrDefault(mob, 0) < Tick.get()) {
            if (nextAllowSendMSGTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                nextAllowSendMSGTickMap.put(Name.get(player), Tick.get() + Tick.s(1));
                sendMSG(player, Te.s("这个怪物隐蔽于", "未知光域", style,
                        "，需要使用", "圣光武器", style, "将其", "剥离", style, "才能造成伤害。"));
            }
            return 0;
        }
        return 1;
    }
}
