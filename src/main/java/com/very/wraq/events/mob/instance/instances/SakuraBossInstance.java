package com.very.wraq.events.mob.instance.instances;

import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.instance.NoTeamInstance;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
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

public class SakuraBossInstance extends NoTeamInstance {

    private static SakuraBossInstance instance;

    public static String mobName = "突见忍";

    public static SakuraBossInstance getInstance() {
        if (instance == null) {
            instance = new SakuraBossInstance(new Vec3(2257, 140, 1694), 30, 200, new Vec3(2257, 140, 1694),
                    Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura));
        }
        return instance;
    }

    public SakuraBossInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        Boss2 sakuraBoss = new Boss2(ModEntityType.Boss2.get(), level);

        sakuraBoss.setBaby(true);
        Compute.SetMobCustomName(sakuraBoss, Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura), 150);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(sakuraBoss), 150);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(sakuraBoss, 1500, 2000, 2000, 0.4, 4, 0.25, 800, 20, 800 * Math.pow(10, 4), 0.35);

        sakuraBoss.setHealth(sakuraBoss.getMaxHealth());

        sakuraBoss.moveTo(pos);
        level.addFreshEntity(sakuraBoss);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(sakuraBoss.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(sakuraBoss);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> itemAndRate.dropWithBounding(lastMob, 1, player));

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 150);
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.Boss2Piece.get(), 1),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }
}
