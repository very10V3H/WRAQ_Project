package fun.wraq.process.func.effect;

import com.mojang.datafixers.util.Pair;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class SpecialEffectOnPlayer {

    public static int clientSilentTick = 0;
    public static int clientBlindTick = 0;

    // 普通减速效果
    public static void addSlowdownEffect(Player player, double rate, int tick, String tag) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerSlowdownEffectModifier,
                tag, rate, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/speed_reduction", tick, (int) (rate * 100), false);
    }

    // 持续衰减减速效果
    public static Map<String, Double> slowdownEffectMap = new HashMap<>();
    public static Map<String, Integer> slowdownStartTimeMap = new HashMap<>();
    public static Map<String, Integer> slowdownEndTimeMap = new HashMap<>();
    public static void slowdownEffectProvide(Player player, int lastTick, double fullEffect) {
        lastTick -= (int) (lastTick * PlayerAttributes.playerToughness(player));
        int tick = Tick.get();
        String name = player.getName().getString();
        slowdownEffectMap.put(name, fullEffect);
        slowdownStartTimeMap.put(name, tick);
        slowdownEndTimeMap.put(name, tick + lastTick);
        Compute.sendDebuffTime(player, ModItems.SlimeBall.get(), lastTick, 0);
    }

    public static double slowdownEffectValue(Player player) {
        int tick = Tick.get();
        String name = player.getName().getString();
        if (slowdownEffectMap.containsKey(name) && slowdownEndTimeMap.containsKey(name)
                && slowdownStartTimeMap.containsKey(name) && slowdownEndTimeMap.get(name) >= tick) {
            int fullRange = slowdownEndTimeMap.get(name) - slowdownStartTimeMap.get(name);
            int currentRange = slowdownEndTimeMap.get(name) - tick;
            return 1 - (slowdownEffectMap.get(name) * ((double) currentRange / fullRange)
                    + StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerSlowdownEffectModifier));
        }
        return 1;
    }

    // 眩晕
    public static Map<String, Integer> vertigoTickMap = new HashMap<>();
    public static void addVertigoEffect(Player player, int tick) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        String name = player.getName().getString();
        vertigoTickMap.put(name, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/vertigo", tick);
        imprisonPosMap.put(name, player.position());
        imprisonRotMap.put(name, Pair.of(player.getXRot(), player.getYRot()));

        Compute.sendDebuffTime(player, "hud/blind", tick, 0, false);
        ModNetworking.sendToClient(new BlindTickS2CPacket(tick), (ServerPlayer) player);
        Compute.sendDebuffTime(player, "hud/silent", tick, 0, false);
        ModNetworking.sendToClient(new SilentTickS2CPacket(tick), (ServerPlayer) player);
    }

    public static boolean inVertigo(Player player) {
        String name = player.getName().getString();
        int tick = Tick.get();
        return vertigoTickMap.getOrDefault(name, 0) > tick;
    }

    // 禁锢
    public static Map<String, Integer> imprisonTickMap = new HashMap<>();
    public static Map<String, Vec3> imprisonPosMap = new HashMap<>();
    public static Map<String, Pair<Float, Float>> imprisonRotMap = new HashMap<>();
    public static void addImprisonEffect(Player player, int tick) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        String name = player.getName().getString();
        imprisonTickMap.put(name, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/imprison", tick);
        imprisonPosMap.put(name, player.position());
        imprisonRotMap.put(name, Pair.of(player.getXRot(), player.getYRot()));
    }

    public static boolean inImprison(Player player) {
        String name = player.getName().getString();
        int tick = Tick.get();
        return inVertigo(player) || imprisonTickMap.getOrDefault(name, 0) > tick;
    }

    public static void addHealingReduction(Player player, String tag, int tick) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        addHealingReduction(player, tag, -0.4, tick);
    }

    public static void addHealingReduction(Player player, String tag, double value, int tick) {
        StableAttributesModifier.addM(player,
                StableAttributesModifier.playerHealAmplifierReductionModifier,
                tag, value, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/healing_reduction",
                tick, (int) (value * 100), false);
    }

    // 沉默
    public static Map<Player, Integer> silentExpiredTickMap = new WeakHashMap<>();
    public static void addSilentEffect(Player player, int tick) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        silentExpiredTickMap.put(player, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/silent", tick, 0, false);
        ModNetworking.sendToClient(new SilentTickS2CPacket(tick), (ServerPlayer) player);
    }

    public static boolean inSilent(Player player) {
        return inVertigo(player) || silentExpiredTickMap.getOrDefault(player, 0) > Tick.get();
    }

    // 致盲
    public static Map<Player, Integer> blindExpiredTickMap = new WeakHashMap<>();
    public static void addBlindEffect(Player player, int tick) {
        tick -= (int) (tick * PlayerAttributes.playerToughness(player));
        blindExpiredTickMap.put(player, Tick.get() + tick);
        Compute.sendDebuffTime(player, "hud/blind", tick, 0, false);
        ModNetworking.sendToClient(new BlindTickS2CPacket(tick), (ServerPlayer) player);
    }

    public static boolean inBlind(Player player) {
        return blindExpiredTickMap.getOrDefault(player, 0) > Tick.get();
    }

    public static void cleanse(Player player) {
        String name = player.getName().getString();
        boolean effective = false;
        if (vertigoTickMap.containsKey(name)) {
            effective = true;
            vertigoTickMap.remove(name);
            Compute.removeDebuffTime(player, "hud/vertigo");
        }
        if (imprisonTickMap.containsKey(name)) {
            effective = true;
            imprisonTickMap.remove(name);
            Compute.removeDebuffTime(player, "hud/imprison");
        }
        if (silentExpiredTickMap.containsKey(player)) {
            effective = true;
            silentExpiredTickMap.remove(player);
            Compute.removeDebuffTime(player, "hud/silent");
        }
        if (blindExpiredTickMap.containsKey(player)) {
            effective = true;
            blindExpiredTickMap.remove(player);
            Compute.removeDebuffTime(player, "hud/blind");
        }
        if (StableAttributesModifier.playerSlowdownEffectModifier.containsKey(player)) {
            effective = true;
            StableAttributesModifier.playerSlowdownEffectModifier.get(player).clear();
            StableAttributesModifier.playerSlowdownEffectModifier.remove(player);
            Compute.removeDebuffTime(player, "hud/slowdown");
        }
        if (StableAttributesModifier
                .getModifierValue(player, StableAttributesModifier.playerHealAmplifierReductionModifier) - 0 > 1e-6) {
            effective = true;
            StableAttributesModifier.playerHealAmplifierReductionModifier.remove(player);
            Compute.removeDebuffTime(player, "hud/healing_reduction");
        }
        if (effective) {
            MySound.soundToPlayer(player, SoundEvents.DOLPHIN_JUMP);
            Compute.sendFormatMSG(player, Te.s("净化", CustomStyle.styleOfWater),
                    Te.s("你已被净化!", CustomStyle.styleOfWater));
        }
    }
}
