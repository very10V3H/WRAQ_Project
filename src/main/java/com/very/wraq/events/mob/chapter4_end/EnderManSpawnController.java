package com.very.wraq.events.mob.chapter4_end;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C4LootItems;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnderManSpawnController extends MobSpawnController {

    public static String mobName = "终界使者";
    private static EnderManSpawnController instance;

    public static EnderManSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(18, 59, -67),
                    new Vec3(46, 58, -54),
                    new Vec3(69, 59, -41),
                    new Vec3(28, 62, -38),
                    new Vec3(11, 65, -31),
                    new Vec3(71, 60, -21),
                    new Vec3(47, 64, -15),
                    new Vec3(11, 65, -10),
                    new Vec3(71, 61, 1),
                    new Vec3(12, 64, 11),
                    new Vec3(44, 63, 16),
                    new Vec3(67, 61, 26),
                    new Vec3(23, 63, 37),
                    new Vec3(54, 61, 49)
            );
            instance = new EnderManSpawnController(spawnPos, spawnPos.size() * 4, 99, 62, -8, -114, world, 4, 80);
        }
        return instance;
    }

    public EnderManSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        EnderMan enderMan = new EnderMan(EntityType.ENDERMAN, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfEnd;
        Compute.setMobCustomName(enderMan, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(enderMan), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(enderMan, 220, 660, 660, 0.35, 3, 0.2, 500, 15, 9900, 0.25);

        // 设置物品


        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(enderMan), list);
        return enderMan;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            if (mob.isAlive()) {
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 5, 5, 5));
                for (Player player : playerList) {
                    if (player.position().distanceTo(mob.position()) <= 2.8) {
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 3));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3));
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 10));
                    }
                }
            }
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.RecallPiece.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.625));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.05));
            add(new ItemAndRate(ModItems.toEnd.get(), 0.01));
            add(new ItemAndRate(NewRuneItems.endNewRune.get(), 0.001));
            add(new ItemAndRate(C4LootItems.enderManSword.get(), 0.005));
        }};
    }
}
