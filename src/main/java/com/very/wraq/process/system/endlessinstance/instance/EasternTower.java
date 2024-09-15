package com.very.wraq.process.system.endlessinstance.instance;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import com.very.wraq.render.toolTip.CustomStyle;
import net.mcreator.borninchaosv.entity.BarrelZombieEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EasternTower extends DailyEndlessInstance {

    private static EasternTower instance;
    public static EasternTower getInstance() {
        if (instance == null) instance = new EasternTower(new Vec3(2336, 148, 17), 1200, -1, 0, 4);
        return instance;
    }

    public static Component name = Component.literal("东洋塔").withStyle(CustomStyle.styleOfHusk);
    private EasternTower(Vec3 pos, int lastTick, int leftTick, int killCount, int maxMobNum) {
        super(name, pos, lastTick, leftTick, killCount, maxMobNum);
    }

    public static String mobName = "无尽熵增怪物";

    @Override
    protected Mob summonMob(Level level) {
        BarrelZombieEntity mob = new BarrelZombieEntity(BornInChaosV1ModEntities.BARREL_ZOMBIE.get(), level);
        int levelDifference = getPlayerLevel() - 150;
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getPlayerLevel(),
                1000 + levelDifference * 5, 2000 + levelDifference * 100, 2000 + levelDifference * 100, 0.5, 10,
                0.01 * levelDifference, 2000 * 100 * levelDifference, 0, getMobMaxHealth(), 0.2);
        Style style = CustomStyle.styleOfHusk;
        MobSpawn.setMobCustomName(mob, Component.literal(mobName).withStyle(style), getPlayerLevel());
        return mob;
    }

    @Override
    protected void reward(Player player) {
        sendFormatMSG(player, Component.literal("测试阶段，暂无奖励"));
    }

    private double getMobMaxHealth() {
        double num_10k = 10000;
        double killCount = Math.min(200, getKillCount());
        double rate = (killCount / 200.0);
        if (getPlayerLevel() < 170) {
            return (10 + 20 * rate) * num_10k;
        } else if (getPlayerLevel() < 190) {
            return (30 + 270 * rate) * num_10k;
        } else if (getPlayerLevel() < 210) {
            return (100 + 900 * rate) * num_10k;
        } else {
            return (1000 + 9000 * rate) * num_10k;
        }
    }
}
