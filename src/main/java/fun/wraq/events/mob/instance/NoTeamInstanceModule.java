package fun.wraq.events.mob.instance;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.events.mob.instance.instances.dimension.NetherInstance;
import fun.wraq.events.mob.instance.instances.element.*;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss1Instance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss2Instance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss3Instance;
import fun.wraq.events.mob.instance.instances.sakura.DevilInstance;
import fun.wraq.events.mob.instance.instances.sakura.SakuraBossInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoTeamInstanceModule {

    public static class AllowRewardKey {
        public static final String allowRewardInstance = "allowRewardInstance";
        public static final String nether = "allowRewardNether";
        public static final String purpleIron = "allowRewardPurpleIron";
        public static final String iceKnight = "allowRewardIceKnight";
        public static final String sakuraBoss = "allowRewardSakuraBoss";
        public static final String devil = "allowRewardDevil";
        public static final String moon = "allowRewardMoon";
        public static final String blackCastle = "allowRewardBlackCastle";
        public static final String moontainBoss = "allowRewardMoontainBoss";
        public static final String enderGuardian = "allowRewardEnderGuardian";
        public static final String warden = "allowRewardWarden";
        public static final String harbinger = "allowRewardHarbinger";
    }

    public static class AllowRewardCondition {
        public static final MutableComponent devil = Te.s("需要至少", "锻造", CustomStyle.styleOfMine, "过",
                "1件", "冰霜骑士武器", CustomStyle.styleOfIce, "，方能获取奖励。");
    }

    public static boolean getPlayerAllowReward(Player player, String tag) {
        return getPlayerAllowRewardTag(player).getBoolean(tag);
    }

    public static void putPlayerAllowReward(Player player, String tag, boolean value) {
        getPlayerAllowRewardTag(player).putBoolean(tag, value);
    }

    public static CompoundTag getPlayerAllowRewardTag(Player player) {
        CompoundTag compoundTag = player.getPersistentData();
        if (!compoundTag.contains(AllowRewardKey.allowRewardInstance)) compoundTag.put(AllowRewardKey.allowRewardInstance, new CompoundTag());
        return compoundTag.getCompound(AllowRewardKey.allowRewardInstance);
    }

    public static List<fun.wraq.events.mob.instance.NoTeamInstance> noTeamInstancesOverworld = new ArrayList<>() {{
        add(PlainInstance.getInstance());
        add(DevilInstance.getInstance());
        add(MoonInstance.getInstance());
        add(PurpleIronInstance.getInstance());
        add(SakuraBossInstance.getInstance());
        add(IceInstance.getInstance());
        add(MoontainBoss1Instance.getInstance());
        add(MoontainBoss2Instance.getInstance());
        add(MoontainBoss3Instance.getInstance());
        add(WardenInstance.getInstance());
        add(MushroomInstance.getInstance());
    }};

    public static List<fun.wraq.events.mob.instance.NoTeamInstance> noTeamInstancesNether = new ArrayList<>() {{
        add(NetherInstance.getInstance());
    }};

    public static List<fun.wraq.events.mob.instance.NoTeamInstance> noTeamInstancesEnd = new ArrayList<>() {{
        add(CitadelGuardianInstance.getInstance());
    }};

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            ServerLevel level = (ServerLevel) event.level;
            Level overworld = event.level.getServer().getLevel(Level.OVERWORLD);
            int tick = Tick.get();
            if (level.equals(overworld)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesOverworld) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummonThenHandleTick(level);
                        if (tick % 20 == 0) {
                            noTeamInstance.summonLeftSecondsArmorStand(level);
                        }
                    } else {
                        noTeamInstance.reset(tick, true);
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
            if (level.dimension().equals(Level.NETHER)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesNether) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummonThenHandleTick(level);
                        if (tick % 20 == 0) {
                            noTeamInstance.summonLeftSecondsArmorStand(level);
                        }
                    } else {
                        noTeamInstance.reset(tick, true);
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
            if (level.dimension().equals(Level.END)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesEnd) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummonThenHandleTick(level);
                        if (tick % 20 == 0) {
                            noTeamInstance.summonLeftSecondsArmorStand(level);
                        }
                    } else {
                        noTeamInstance.reset(tick, true);
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
        }
    }

    public static void reset() {
        noTeamInstancesOverworld.forEach(instance -> {
            instance.reset(0, true);
        });
        noTeamInstancesNether.forEach(instance -> {
            instance.reset(0, true);
        });
        noTeamInstancesEnd.forEach(instance -> {
            instance.reset(0, true);
        });
    }

    public static boolean hasPlayerNearInstance(ServerLevel serverLevel, NoTeamInstance noTeamInstance) {
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        boolean hasPlayerNearby = false;
        for (ServerPlayer serverPlayer : playerList) {
            if (serverPlayer.isAlive()
                    && serverPlayer.position().distanceTo(noTeamInstance.pos) < noTeamInstance.range) {
                hasPlayerNearby = true;
            }
        }
        return hasPlayerNearby;
    }

    public static void handlePlayerRightClick(Player player) {
        if ((player.getMainHandItem().is(ModItems.notePaper.get()))
                || player.getMainHandItem().is(MoontainItems.HEART.get())) {
            Item item = player.getMainHandItem().getItem();
            if (player.level().dimension().equals(Level.OVERWORLD)) {
                noTeamInstancesOverworld.forEach(instance -> {
                    if (instance.getSummonAndRewardNeedItem().equals(item)
                            && player.position().distanceTo(instance.pos) < 12 && Tick.get() > instance.summonTick) {
                        instance.ready = true;
                    }
                });
            }
            if (player.level().dimension().equals(Level.NETHER)) {
                noTeamInstancesNether.forEach(instance -> {
                    if (instance.getSummonAndRewardNeedItem().equals(item)
                            && player.position().distanceTo(instance.pos) < 12 && Tick.get() > instance.summonTick) {
                        instance.ready = true;
                    }
                });
            }
            if (player.level().dimension().equals(Level.END)) {
                noTeamInstancesEnd.forEach(instance -> {
                    if (instance.getSummonAndRewardNeedItem().equals(item)
                            && player.position().distanceTo(instance.pos) < 12 && Tick.get() > instance.summonTick) {
                        instance.ready = true;
                    }
                });
            }
        }
    }

    public static List<NoTeamInstance> getAllInstance() {
        List<NoTeamInstance> list = new ArrayList<>();
        list.addAll(noTeamInstancesOverworld);
        list.addAll(noTeamInstancesNether);
        list.addAll(noTeamInstancesEnd);
        return list;
    }

    public static Map<String, Component> nameMap = new HashMap<>();
    public static Map<String, Component> getNameMap() {
        if (nameMap.isEmpty()) {
            getAllInstance().forEach(noTeamInstance -> {
                nameMap.put(noTeamInstance.name.getString(),noTeamInstance.name);
            });
        }
        return nameMap;
    }

    public static double onMobWithstandDamageRate(Player player, Mob mob) {
        NoTeamInstance instance = getAllInstance()
                .stream().filter(noTeamInstance -> noTeamInstance.mobList.contains(mob))
                .findFirst()
                .orElse(null);
        return instance == null ? 1 : instance.onMobWithStandDamageRate(player, mob);
    }

    public static void onDead(Player player) {
        getAllInstance().forEach(instance -> instance.players.removeIf(eachPlayer -> {
            return eachPlayer.getName().getString().equals(player.getName().getString());
        }));
    }
}

