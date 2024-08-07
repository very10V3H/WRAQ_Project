package com.very.wraq.events.mob.chapter4_end;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C4LootItems;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EndermiteSpawnController extends MobSpawnController {

    public static String mobName = "寂域灵螨";
    private static EndermiteSpawnController instance;

    public static EndermiteSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(-28, 89, -220),
                    new Vec3(-11.5, 87, -240.5),
                    new Vec3(5, 87, -248),
                    new Vec3(0.5, 86, -223.5),
                    new Vec3(39.5, 89, -229.5),
                    new Vec3(46, 89, -252),
                    new Vec3(60.5, 87, -240.5),
                    new Vec3(71, 89, -224)
            );
            instance = new EndermiteSpawnController(spawnPos, spawnPos.size() * 4, 104, -128, -61, -289, world, 4, 140);
        }
        return instance;
    }

    public EndermiteSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Endermite endermite = new Endermite(EntityType.ENDERMITE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfEnd;
        Compute.SetMobCustomName(endermite, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(endermite), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(endermite, 1200, 1700, 1700, 0.45, 5, 0.3, 1200, 25, 100 * Math.pow(10, 4), 0.4);

        // 设置物品


        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(endermite), list);
        return endermite;
    }

    @Override
    public void tick() {

    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EnderMiteSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.75));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.05));
            add(new ItemAndRate(C4LootItems.endermiteSceptre.get(), 0.005));
        }};
    }
}
