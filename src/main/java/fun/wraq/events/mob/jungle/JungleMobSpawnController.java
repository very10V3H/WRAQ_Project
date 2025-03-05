package fun.wraq.events.mob.jungle;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class JungleMobSpawnController {
    public final Component name;
    public List<Mob> mobs = new ArrayList<>();
    public final Vec3 descriptionPos;
    public int lastSpawnTick = 0;
    public final int refreshInterval;
    public final Vec3 mobUpperBoundary;
    public final Vec3 mobLowerBoundary;
    public final int mobXpLevel;
    public final double detectionRadius;
    public Set<Player> players = new HashSet<>();

    public JungleMobSpawnController(Component name, Vec3 descriptionPos, Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                    int mobXpLevel, double detectionRadius, int refreshInterval) {
        this.name = name;
        this.descriptionPos = descriptionPos.add(0.5, 0, 0.5);
        this.mobUpperBoundary = mobUpperBoundary;
        this.mobLowerBoundary = mobLowerBoundary;
        this.mobXpLevel = mobXpLevel;
        this.detectionRadius = detectionRadius;
        this.refreshInterval = refreshInterval;
    }

    public void handleLevelTick(Level level) {
        if (getNearbyPlayers(level).isEmpty() && players.isEmpty()) {
            reset();
            return;
        }
        if (mobs.isEmpty()) {
            if (Tick.get() % 20 == 0) {
                if (Tick.get() > lastSpawnTick + refreshInterval) {
                    spawnMob(level);
                    lastSpawnTick = Tick.get();
                    removeNearArmorStand(level);
                } else {
                    removeNearArmorStand(level);
                    Compute.summonArmorStand(level, descriptionPos, name);
                    Compute.summonArmorStand(level, descriptionPos.add(0, -0.25, 0),
                            Te.s("下次刷新：",
                                    (lastSpawnTick + refreshInterval - Tick.get()) / 20 + "s", ChatFormatting.AQUA));
                }
            }
        } else {
            if (mobs.stream().allMatch(LivingEntity::isDeadOrDying)) {
                players.forEach(this::tryToReward);
                reset();
            }
        }
    }

    public void onMobWithStandDamage(Mob mob, Player player) {
        if (mobs.contains(mob)) {
            players.add(player);
        }
    }

    public void removeNearArmorStand(Level level) {
        Compute.removeNearArmorStand(level, descriptionPos, 4);
    }

    public void reset() {
        lastSpawnTick = Tick.get();
        players.clear();
        mobs.clear();
    }

    public Set<Player> getNearbyPlayers(Level level) {
        return Compute.getNearPlayer(level, descriptionPos, detectionRadius);
    }

    public double modifyMobWithstandDamage(Mob mob, Player player) {
        return 1;
    }

    public abstract void tryToReward(Player player);
    public abstract void spawnMob(Level level);
    public abstract List<ItemAndRate> getRewardItemList();

    public void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("野怪", CustomStyle.styleOfRed), content);
    }
}
