package com.very.wraq.events.mob.instance.instances;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.instance.NoTeamInstance;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurpleIronInstance extends NoTeamInstance {

    private static PurpleIronInstance instance;

    public static String mobName = "紫水晶巨蟹";

    public static PurpleIronInstance getInstance() {
        if (instance == null) {
            instance = new PurpleIronInstance(new Vec3(1171.5, -35.5, -171.5), 50, 200, new Vec3(1171.5, -35.5, -171.5),
                    Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron));
        }
        return instance;
    }

    public PurpleIronInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        Element.ElementProvider(mob, Element.stone, 3);
    }

    @Override
    public void summonModule(Level level) {
        Amethyst_Crab_Entity amethystCrabEntity = new Amethyst_Crab_Entity(ModEntities.AMETHYST_CRAB.get(), level);

        Compute.SetMobCustomName(amethystCrabEntity, Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron), 120);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(amethystCrabEntity), 120);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(amethystCrabEntity, 1000, 1200, 1200, 0.35, 3, 0.2, 500, 15, 400000, 0.3);

        amethystCrabEntity.setHealth(amethystCrabEntity.getMaxHealth());

        amethystCrabEntity.moveTo(pos);
        level.addFreshEntity(amethystCrabEntity);
        mobList.add(amethystCrabEntity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(amethystCrabEntity.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> itemAndRate.dropWithBounding(lastMob, 1, player));
        DailyMission.addCount(player, DailyMission.purpleIronInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.PurpleIronBud1.get(), 1),
                new ItemAndRate(new ItemStack(ModItems.gemPiece.get(), 8), 0.1),
                new ItemAndRate(ModItems.PurpleIronBud2.get(), 0.1),
                new ItemAndRate(ModItems.PurpleIronSword.get(), 0.01),
                new ItemAndRate(ModItems.PurpleIronBow.get(), 0.01),
                new ItemAndRate(ModItems.PurpleIronSceptre.get(), 0.01),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }
}
