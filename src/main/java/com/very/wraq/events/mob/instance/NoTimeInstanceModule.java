package com.very.wraq.events.mob.instance;

import com.very.wraq.events.mob.instance.instances.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class NoTimeInstanceModule {

    public static List<NoTeamInstance> noTeamInstancesOverworld = new ArrayList<>() {{
        add(PlainInstance.getInstance());
        add(DevilInstance.getInstance());
        add(MoonInstance.getInstance());
        add(PurpleIronInstance.getInstance());
        add(SakuraBossInstance.getInstance());
        add(IceInstance.getInstance());
    }};

    public static List<NoTeamInstance> noTeamInstancesNether = new ArrayList<>() {{
        add(NetherInstance.getInstance());
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
                    }
                    noTeamInstance.bossInfoSet(level);
                }
            }
        }
    }

    public static void clearMob() {
        for (NoTeamInstance noTeamInstance : noTeamInstancesOverworld) {
            for (Mob mob : noTeamInstance.mobList) {
                mob.remove(Entity.RemovalReason.KILLED);
            }
        }
    }

    public static boolean hasPlayerNearInstance(ServerLevel serverLevel, NoTeamInstance noTeamInstance) {
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        boolean hasPlayerNearby = false;
        for (ServerPlayer serverPlayer : playerList) {
            if (serverPlayer.position().distanceTo(noTeamInstance.pos) < noTeamInstance.range) hasPlayerNearby = true;
        }
        return hasPlayerNearby;
    }
}

