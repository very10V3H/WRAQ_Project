package fun.wraq.events.mob.moontain;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.mcreator.borninchaosv.entity.MrPumpkinEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoontainCommon1SpawnController extends MobSpawnController {

    public static String mobName = "望山孤魂";
    private static MoontainCommon1SpawnController instance;

    public static MoontainCommon1SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1971, 151, -891),
                    new Vec3(1969, 151, -881),
                    new Vec3(1969, 155, -869),
                    new Vec3(1983, 151, -892),
                    new Vec3(1980, 151, -881),
                    new Vec3(1983, 151, -868),
                    new Vec3(1985, 160, -869),
                    new Vec3(1996, 159, -893),
                    new Vec3(1996, 159, -882),
                    new Vec3(1995, 151, -880),
                    new Vec3(1993, 151, -869),
                    new Vec3(1996, 159, -867),
                    new Vec3(1975, 207, -892),
                    new Vec3(1971, 207, -881),
                    new Vec3(1988, 207, -894),
                    new Vec3(1981, 207, -881),
                    new Vec3(1984, 207, -872),
                    new Vec3(1995, 207, -881),
                    new Vec3(1996, 207, -870)
            );
            instance = new MoontainCommon1SpawnController(spawnPos, spawnPos.size() * 4,
                    (int) MoontainEntities.commonUpPos.x, (int) MoontainEntities.commonUpPos.y, (int) MoontainEntities.commonUpPos.z,
                    (int) MoontainEntities.commonDownPos.x, (int) MoontainEntities.commonDownPos.y, (int) MoontainEntities.commonDownPos.z,
                    world, 3, 160);
        }
        return instance;
    }

    public MoontainCommon1SpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                          int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ,
                0, 16, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        MrPumpkinEntity mrPumpkin = new MrPumpkinEntity(BornInChaosV1ModEntities.MR_PUMPKIN.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(mrPumpkin, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mrPumpkin, xpLevel, 3200, 200,
                200, 0.4, 5, 0.3, 70, 25,
                2000 * Math.pow(10, 4), 0.4);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mrPumpkin), list);
        return mrPumpkin;
    }

    @Override
    public void tick() {

    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(C7Items.vdSoul.get(), 0.1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 1));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.06));
        }};
    }
}
