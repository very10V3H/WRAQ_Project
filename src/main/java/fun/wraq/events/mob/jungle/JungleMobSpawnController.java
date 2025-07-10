package fun.wraq.events.mob.jungle;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

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
    public Map<String, Double> damageCount = new HashMap<>();

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
        if (getNearbyPlayers(level).isEmpty()) {
            clear();
            return;
        }
        if (mobs.isEmpty()) {
            if (Tick.get() % 20 == 0) {
                if (Tick.get() > lastSpawnTick + refreshInterval) {
                    spawnMob(level);
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
            players.removeIf(player -> {
                return player == null || !player.isAlive()
                        || mobs.stream().allMatch(mob -> mob.distanceTo(player) > detectionRadius);
            });
            if (mobs.stream().allMatch(LivingEntity::isDeadOrDying)) {
                players.forEach(this::tryToReward);
                onEndBroad();
                reset();
            } else {
                mobs.stream().filter(LivingEntity::isAlive)
                        .forEach(mob -> {
                            this.handleMobTick(mob);
                            players.addAll(Compute.getNearPlayer(level, mob.position(), detectionRadius));
                        });
                mobs.forEach(mob -> {
                    if (mob != null && mob.isAlive()) {
                        Player player = Compute.getNearestPlayer(mob, 32);
                        if (player != null) {
                            mob.setTarget(player);
                        }
                        if (getElementUnit() != null) {
                            Element.provideElement(mob, getElementUnit().type(), getElementUnit().value());
                        }
                    }
                });
            }
        }
    }

    public void onMobWithStandDamage(Mob mob, Player player, double damage) {
        if (mobs.contains(mob)) {
            players.add(player);
        }
        damageCount.compute(Name.get(player), (k, v) -> v == null ? damage : v + damage);
    }

    public void removeNearArmorStand(Level level) {
        Compute.removeNearArmorStand(level, descriptionPos, 4);
    }

    public void onEndBroad() {

    }

    public void reset() {
        lastSpawnTick = Tick.get();
        clear();
    }

    public void clear() {
        players.clear();
        mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobs.clear();
        damageCount.clear();
    }

    public Set<Player> getNearbyPlayers(Level level) {
        Set<Player> playerSet = new HashSet<>(Compute.getNearPlayer(level, descriptionPos, detectionRadius));
        mobs.forEach(mob -> {
            playerSet.addAll(Compute.getNearPlayer(level, mob.position(), detectionRadius));
        });
        return playerSet;
    }

    public double modifyMobWithstandDamage(Mob mob, Player player) {
        return 1;
    }

    public void handleMobTick(Mob mob) {

    }

    public abstract void tryToReward(Player player);

    public abstract void spawnMob(Level level);

    public abstract List<ItemAndRate> getRewardItemList();

    public void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("野怪", CustomStyle.styleOfRed), content);
    }

    public MobAttributes getMobAttributes() {
        return null;
    }

    public Element.Unit getElementUnit() {
        return null;
    }

    public List<Component> getSpecialDescription() {
        return null;
    }
}
