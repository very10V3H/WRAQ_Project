package com.very.wraq.events.mob.chapter2;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C2LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlimeSpawnController extends MobSpawnController {

    public static String mobName = "史莱姆";
    private static SlimeSpawnController instance;

    public static SlimeSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1030, 63, -470),
                    new Vec3(1038, 63, -482),
                    new Vec3(1040, 67, -496),
                    new Vec3(1053, 64, -509),
                    new Vec3(1048, 67, -524),
                    new Vec3(1057, 64, -538),
                    new Vec3(1049, 71, -549),
                    new Vec3(1059, 64, -565),
                    new Vec3(1054, 66, -579)
            );
            instance = new SlimeSpawnController(spawnPos, spawnPos.size() * 4, 1072, -454, 1004, -631, world, 4, 76);
        }
        return instance;
    }

    public SlimeSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Slime slime = new Slime(EntityType.SLIME, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfLife;
        MobSpawn.setMobCustomName(slime, Component.literal(mobName).withStyle(style), xpLevel);
        slime.setSize(2, true);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(slime), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(slime, 200, 5, 5, 0.35, 3, 0.2, 5, 15, 42000, 0.3);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(slime), list);
        return slime;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 3);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SlimeBall.get(), 0.4));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.1875));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.15));
            add(new ItemAndRate(C2LootItems.slimeChest.get(), 0.005));
        }};
    }
}
