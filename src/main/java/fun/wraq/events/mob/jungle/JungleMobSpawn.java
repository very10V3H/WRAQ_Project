package fun.wraq.events.mob.jungle;

import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonSpawnController;
import fun.wraq.series.overworld.cold.sc5.dragon.SimulateIceDragonSpawnController;
import fun.wraq.series.overworld.divine.mob.jungle.DivineJungleMob0SpawnController;
import fun.wraq.series.overworld.divine.mob.jungle.DivineJungleMob1SpawnController;
import fun.wraq.series.overworld.cold.sc2.stone.StoneSpiderKingSpawnController;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class JungleMobSpawn {
    public static List<JungleMobSpawnController> overworldController = new ArrayList<>();

    public static List<JungleMobSpawnController> getOverworldController() {
        if (overworldController.isEmpty()) {
            overworldController.add(BunkerGhastSpawnController.getInstance());
            overworldController.add(BlazePowerSpawnController.getInstance());
            overworldController.add(EvokerMasterSpawnController.getInstance());
            overworldController.add(DivineJungleMob0SpawnController.getInstance());
            overworldController.add(DivineJungleMob1SpawnController.getInstance());
            overworldController.add(StoneSpiderKingSpawnController.getInstance());
            overworldController.add(IceDragonSpawnController.getInstance());
            overworldController.add(SimulateIceDragonSpawnController.getInstance());
        }
        return overworldController;
    }

    public static void handleLevelTick(TickEvent.LevelTickEvent event) {
        if (event.side.isClient() || event.phase.equals(TickEvent.Phase.END)) {
            return;
        }
        Level level = event.level;
        if (level.dimension().equals(Level.OVERWORLD)) {
            getOverworldController().forEach(controller -> {
                controller.handleLevelTick(level);
            });
        }
    }

    public static void onMobWithstandDamage(Mob mob, Player player) {
        getOverworldController().forEach(controller -> {
            controller.onMobWithStandDamage(mob, player);
        });
    }

    public static double modifyMobWithstandDamage(Mob mob, Player player) {
        JungleMobSpawnController controller = getOverworldController()
                .stream().filter(eachController -> {
                    return eachController.mobs.contains(mob);
                }).findAny().orElse(null);
        if (controller != null) {
            return controller.modifyMobWithstandDamage(mob, player);
        }
        return 1;
    }

    public static void removeAllMobs() {
        getOverworldController().forEach(controller -> {
            controller.mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        });
    }
}
