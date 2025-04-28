package fun.wraq.events.mob.instance.instances.tower;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.client.ParticleEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaTowerInstance extends NoTeamInstance {

    public static final Style style = CustomStyle.MANA_TOWER_STYLE;
    public static ManaTowerInstance instance;

    public static ManaTowerInstance getInstance() {
        if (instance == null) {
            instance = new ManaTowerInstance(new Vec3(1511, 110, -535), 40, 60, new Vec3(1511, 110, -535),
                    Te.s("炼魔塔", style), 250);
        }
        return instance;
    }

    public ManaTowerInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos,
                             MutableComponent name, int level) {
        super(pos, range, delayTick, armorStandPos, name, level);
    }

    @Override
    public int getMaxPlayerNum() {
        return 1;
    }

    public static final Vec3 FLOOR_1_PLATFORM_CENTER_POS = new Vec3(1511.5, 110, -534.5);
    public static final Vec3 FLOOR_2_PLATFORM_CENTER_POS = new Vec3(1511.5, 135, -534.5);
    public static final Vec3 FLOOR_3_PLATFORM_CENTER_POS = new Vec3(1511.5, 158, -534.5);
    public static final Vec3 FLOOR_4_PLATFORM_CENTER_POS = new Vec3(1511.5, 188, -534.5);
    public static final Vec3 FLOOR_5_PLATFORM_CENTER_POS = new Vec3(1511.5, 218, -534.5);

    public int currentFloor = 0;

    @Override
    public void tickModule() {
        Level overworld = Tick.server.overworld();
        if (!anyMobAlive()) {
            if (!containMob(ManaTowerEachFloorMob.FLOOR_2_MOB_NAME)) {
                ManaTowerEachFloorMob.FLOOR_2_MOB_POS.forEach(pos -> {
                    mobList.add(ManaTowerEachFloorMob.spawnFloor2Mob(overworld, pos, getManaTowerPieceGetCount()));
                });
                setTitle(Te.s("前往魔塔中心", style), Te.s("传送至2层，清理剩余怪物"));
                currentFloor = 2;
            } else if (!containMob(ManaTowerEachFloorMob.FLOOR_3_MOB_NAME)) {
                ManaTowerEachFloorMob.FLOOR_3_MOB_POS.forEach(pos -> {
                    mobList.add(ManaTowerEachFloorMob.spawnFloor3Mob(overworld, pos, getManaTowerPieceGetCount()));
                });
                setTitle(Te.s("前往魔塔中心", style), Te.s("传送至3层，清理剩余怪物"));
                currentFloor = 3;
            } else if (!containMob(ManaTowerEachFloorMob.FLOOR_4_MOB_NAME)) {
                mobList.add(ManaTowerEachFloorMob.spawnFloor4Mob(overworld,
                        ManaTowerEachFloorMob.FLOOR_4_MOB_POS, getManaTowerPieceGetCount()));
                setTitle(Te.s("前往魔塔中心", style), Te.s("传送至4层，清理剩余怪物"));
                currentFloor = 4;
            } else if (!containMob(ManaTowerEachFloorMob.FLOOR_5_MOB_NAME)) {
                mobList.add(ManaTowerEachFloorMob.spawnFloor5Mob(overworld,
                        ManaTowerEachFloorMob.FLOOR_5_MOB_POS, getManaTowerPieceGetCount()));
                setTitle(Te.s("前往魔塔中心", style), Te.s("传送至5层，清理剩余怪物"));
                currentFloor = 5;
            }
        }
        teleportPlayer(ManaTowerEachFloorMob.FLOOR_1_MOB_NAME,
                FLOOR_1_PLATFORM_CENTER_POS, FLOOR_2_PLATFORM_CENTER_POS);
        teleportPlayer(ManaTowerEachFloorMob.FLOOR_2_MOB_NAME,
                FLOOR_2_PLATFORM_CENTER_POS, FLOOR_3_PLATFORM_CENTER_POS);
        teleportPlayer(ManaTowerEachFloorMob.FLOOR_3_MOB_NAME,
                FLOOR_3_PLATFORM_CENTER_POS, FLOOR_4_PLATFORM_CENTER_POS);
        teleportPlayer(ManaTowerEachFloorMob.FLOOR_4_MOB_NAME,
                FLOOR_4_PLATFORM_CENTER_POS, FLOOR_5_PLATFORM_CENTER_POS);

        mobList.stream().filter(mob -> MobSpawn.getMobOriginName(mob).equals(ManaTowerEachFloorMob.FLOOR_3_MOB_NAME)
                        && mob.isAlive())
                .forEach(ManaTowerEachFloorMob::provideElementOnFloor3Mob);

        players.forEach(player -> {
            ManaTowerData.sendTickToClient(player, spawnTick, Tick.get() + Tick.s(5), currentFloor);
        });
    }

    private void teleportPlayer(String mobName, Vec3 floorPos1, Vec3 floorPos2) {
        if (allDead(mobName)) {
            players.stream().filter(player -> player.position().distanceTo(floorPos1) < 2)
                    .forEach(player -> Compute.teleportPlayerToPos(player, floorPos2));
        }
    }

    private boolean allDead(String mobName) {
        return mobList.stream().filter(mob -> MobSpawn.getMobOriginName(mob).equals(mobName))
                .noneMatch(LivingEntity::isAlive);
    }

    private boolean containMob(String mobName) {
        return mobList.stream().anyMatch(mob -> mobName.equals(MobSpawn.getMobOriginName(mob)));
    }

    public int getManaTowerPieceGetCount() {
        return players.stream()
                .mapToInt(ManaTowerData::getPlayerManaTowerPieceGetCount)
                .sum();
    }

    @Override
    public void summonModule(Level level) {
        ManaTowerEachFloorMob.FLOOR_1_MOB_POS.forEach(pos -> {
            Utils.fourPosOffset.forEach(offset -> {
                mobList.add(ManaTowerEachFloorMob.spawnFloor1Mob(level, pos.add(offset), getManaTowerPieceGetCount()));
            });
            setTitle(Te.s("炼魔试炼", style),
                    Te.s("尽全力清理怪物!",
                            "(原界亲和度:" + getManaTowerPieceGetCount() + ")", CustomStyle.MANA_TOWER_STYLE));
        });
        currentFloor = 1;
        players.forEach(player -> {
            ManaTowerData.sendTickToClient(player, Tick.get(), Tick.get() + Tick.s(5), currentFloor);
        });
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        players.forEach(player -> {
            ManaTowerData.sendTickToClient(player, Tick.get(), Tick.get(), 0);
        });
        currentFloor = 0;
        super.reset(tick, removeMob);
    }

    @Override
    public int getRewardNeedItemCount() {
        return 0;
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss)
                && player.experienceLevel >= 220;
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON
                .append(Te.s("且需要达到", Utils.getLevelDescription(220)));
    }

    @Override
    public void exReward(Player player) {
        super.exReward(player);
        ManaTowerData.onPlayerFinishedChallenge(player, Tick.get() - spawnTick - Tick.s(3));
        Compute.teleportPlayerToPos(player, FLOOR_1_PLATFORM_CENTER_POS);
    }

    @Override
    public List<ItemAndRate> getRewardList() {
        return List.of();
    }

    @Override
    public String getKillCountDataKey() {
        return "ManaTowerChallengeTimes";
    }

    private void setTitle(Component title, Component subTitle) {
        players.forEach(player -> {
            Compute.setPlayerShortTitleAndSubTitle(player, title, subTitle);
        });
    }

    public static Vec3 getFloorPlatformCenterPos(int floor) {
        switch (floor) {
            case 1 -> {
                return FLOOR_1_PLATFORM_CENTER_POS;
            }
            case 2 -> {
                return FLOOR_2_PLATFORM_CENTER_POS;
            }
            case 3 -> {
                return FLOOR_3_PLATFORM_CENTER_POS;
            }
            case 4 -> {
                return FLOOR_4_PLATFORM_CENTER_POS;
            }
            case 5 -> {
                return FLOOR_5_PLATFORM_CENTER_POS;
            }
        }
        return FLOOR_1_PLATFORM_CENTER_POS;
    }

    @OnlyIn(Dist.CLIENT)
    public static void provideClientParticle(Player player) {
        if (ManaTowerData.clientExpiredTick > ClientUtils.serverTick
                && ManaTowerData.clientFloor > 0 && ManaTowerData.clientFloor < 5) {
            ParticleEvent.createSpinParticle(player, getFloorPlatformCenterPos(ManaTowerData.clientFloor - 1),
                    1.5, ModParticles.VOLCANO_TP.get(), 20);
        }
    }

    @Override
    public @Nullable Component getExtraInfo() {
        return Te.s("元素魔物的元素为:",
                Element.nameMap.getOrDefault(ManaTowerEachFloorMob.getCurrentFloor3MobElementType(), "无"),
                Element.styleMap.getOrDefault(ManaTowerEachFloorMob.getCurrentFloor3MobElementType(),
                        CustomStyle.styleOfStone));
    }
}
