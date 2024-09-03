package com.very.wraq.events.mob.chapter3_nether;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C3LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagmaSpawnController extends MobSpawnController {

    public static String mobName = "熔岩聚合物";
    private static MagmaSpawnController instance;

    public static MagmaSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(482, 63, -624),
                    new Vec3(480, 64, -614),
                    new Vec3(482, 64, -602),
                    new Vec3(489, 62, -598),
                    new Vec3(498, 60, -591),
                    new Vec3(510, 63, -593),
                    new Vec3(504, 67, -672),
                    new Vec3(577, 60, -614),
                    new Vec3(517, 60, -607)
            );
            instance = new MagmaSpawnController(spawnPos, spawnPos.size() * 4, 528, -582, 464, -645, world, 4, 80);
        }
        return instance;
    }

    public MagmaSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        MagmaCube magmaCube = new MagmaCube(EntityType.MAGMA_CUBE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfNether;
        Compute.SetMobCustomName(magmaCube, Component.literal(mobName).withStyle(style), xpLevel);
        magmaCube.setSize(2, true);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(magmaCube), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(magmaCube, 200, 600, 600, 0.35, 3, 0.2, 500, 15, 54000, 0.25);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(magmaCube), list);
        return magmaCube;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 4);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.Ruby.get(), 0.5));
            add(new ItemAndRate(ModItems.NetherQuartz.get(), 0.15));
            add(new ItemAndRate(ModItems.magmaSoul.get(), 0.4));
            add(new ItemAndRate(ModItems.copperCoin.get(), 3));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.2));
            add(new ItemAndRate(ModItems.toNether.get(), 0.005));
            add(new ItemAndRate(C3LootItems.magmaLootSceptre.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.netherNewRune.get(), 0.001));
        }};
    }
}
