package fun.wraq.events.mob.instance;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.events.mob.instance.instances.dimension.NetherInstance;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.events.mob.instance.instances.element.MoonInstance;
import fun.wraq.events.mob.instance.instances.element.PlainInstance;
import fun.wraq.events.mob.instance.instances.element.PurpleIronInstance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss1Instance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss2Instance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss3Instance;
import fun.wraq.events.mob.instance.instances.sakura.DevilInstance;
import fun.wraq.events.mob.instance.instances.sakura.SakuraBossInstance;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class NoTeamInstanceModule {

    public static class AllowRewardKey {
        public static String allowRewardInstance = "allowRewardInstance";
        public static String nether = "allowRewardNether";
        public static String purpleIron = "allowRewardPurpleIron";
        public static String iceKnight = "allowRewardIceKnight";
        public static String sakuraBoss = "allowRewardSakuraBoss";
        public static String devil = "allowRewardDevil";
        public static String moon = "allowRewardMoon";
        public static String blackCastle = "allowRewardBlackCastle";
        public static String moontainBoss = "allowRewardMoontainBoss";
        public static String enderGuardian = "allowRewardEnderGuardian";
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
            int tick = level.getServer().getTickCount();
            if (level.equals(overworld)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesOverworld) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummon(level);
                        noTeamInstance.tickModule();
                        if (tick % 20 == 0) noTeamInstance.summonLeftSecondsArmorStand(level);
                    } else {
                        noTeamInstance.reset(tick, true);
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
            if (level.dimension().equals(Level.NETHER)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesNether) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummon(level);
                        noTeamInstance.tickModule();
                        if (tick % 20 == 0) noTeamInstance.summonLeftSecondsArmorStand(level);
                    } else {
                        noTeamInstance.reset(tick, true);
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
            if (level.dimension().equals(Level.END)) {
                for (NoTeamInstance noTeamInstance : noTeamInstancesEnd) {
                    if (hasPlayerNearInstance(level, noTeamInstance)) {
                        noTeamInstance.detectAndSummon(level);
                        noTeamInstance.tickModule();
                        if (tick % 20 == 0) noTeamInstance.summonLeftSecondsArmorStand(level);
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
                    && serverPlayer.position().distanceTo(noTeamInstance.pos) < noTeamInstance.range) hasPlayerNearby = true;
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
}

