package fun.wraq.process.func.damage;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.TickEvent;

import java.util.*;

public record Dot(int type, double value, int frequency, int stopTick, String playerName,
                  boolean computeCrit, String tag) {
    // type : 0 - ignoreDefence 1 - attack 2 - mana
    // parameter "computeCrit" can only be used in type 1 or 2

    public static void addDotOnMob(Mob mob, Dot dot) {
        if (!mobDotList.containsKey(mob)) {
            mobDotList.put(mob, new ArrayList<>());
        }
        if (dot.tag != null) {
            mobDotList.get(mob).removeIf(dot1 -> dot.tag.equals(dot1.tag));
        }
        mobDotList.get(mob).add(dot);
    }

    public static Map<Mob, List<Dot>> mobDotList = new HashMap<>();

    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) return;
        int tick = Tick.get();
        Random random = new Random();
        List<Mob> removeMobList = new ArrayList<>();
        List<Dot> removeDotList = new ArrayList<>();
        mobDotList.forEach((mob, list) -> {
            if (mob.isDeadOrDying()) removeMobList.add(mob);
            list.forEach(dot -> {
                ServerPlayer serverPlayer = event.getServer().getPlayerList().getPlayerByName(dot.playerName);
                if (tick >= dot.stopTick() || serverPlayer == null) {
                    removeDotList.add(dot);
                    return;
                }

                if (tick % (20 / dot.frequency) == 0) {
                    switch (dot.type) {
                        case 0 -> Damage.causeTrueDamageToMonster(serverPlayer, mob, dot.value);
                        case 1 -> {
                            if (dot.computeCrit) {
                                if (random.nextDouble() < PlayerAttributes.critRate(serverPlayer)) {
                                    Damage.causeAttackDamageToMonster(serverPlayer, mob,
                                            dot.value * (1 + PlayerAttributes.critDamage(serverPlayer)));
                                } else Damage.causeAttackDamageToMonster(serverPlayer, mob, dot.value);
                            }
                        }
                        case 2 -> {
                            if (dot.computeCrit) {
                                if (random.nextDouble() < PlayerAttributes.critRate(serverPlayer)) {
                                    Damage.causeManaDamageToMonster(serverPlayer, mob,
                                            dot.value * (1 + PlayerAttributes.critDamage(serverPlayer)));
                                } else Damage.causeManaDamageToMonster(serverPlayer, mob, dot.value);
                            }
                        }
                    }
                }
            });
            list.removeAll(removeDotList);
            if (list.isEmpty()) removeMobList.add(mob);
        });
        removeMobList.forEach(mob -> mobDotList.remove(mob));
    }
}
