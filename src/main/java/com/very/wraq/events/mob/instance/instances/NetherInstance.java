package com.very.wraq.events.mob.instance.instances;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.instance.NoTeamInstance;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleCurios;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetherInstance extends NoTeamInstance {

    private static NetherInstance instance;

    public static String mobName = "燃魂";

    public static NetherInstance getInstance() {
        if (instance == null) {
            instance = new NetherInstance(new Vec3(529, 64, -540), 50, 200, new Vec3(529, 64, -540),
                    Component.literal("燃魂").withStyle(CustomStyle.styleOfPower));
        }
        return instance;
    }

    public NetherInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        Ignited_Revenant_Entity entity = new Ignited_Revenant_Entity(ModEntities.IGNITED_REVENANT.get(), level);

        entity.setBaby(true);
        Compute.SetMobCustomName(entity, Component.literal("燃魂").withStyle(CustomStyle.styleOfPower), 90);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 90);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 1000, 900, 900, 0.35, 3, 0.2, 500, 15, 300000, 0.3);

        entity.moveTo(pos);
        level.addFreshEntity(entity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            if (itemAndRate.getItemStack().is(ModItems.netherHand.get())) {
                CastleCurios.randomAttackAttributeProvide(itemAndRate.getItemStack(), 3, 0.6);
            }
            itemAndRate.dropWithBounding(lastMob, 1, player);
        });

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.NetherQuartz.get(), 8),
                new ItemAndRate(ModItems.Ruby.get(), 8),
                new ItemAndRate(ModItems.netherHand.get(), 0.08),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

}
