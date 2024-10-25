package fun.wraq.events.mob.moontain;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.mcreator.borninchaosv.entity.SenorPumpkinEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoontainCommon3SpawnController extends MobSpawnController {

    public static String mobName = "望山阁灵";
    private static MoontainCommon3SpawnController instance;

    public static MoontainCommon3SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1971, 192, -893),
                    new Vec3(1971, 183, -892),
                    new Vec3(1970, 183, -882),
                    new Vec3(1971, 191, -881),
                    new Vec3(1982, 191, -892),
                    new Vec3(1983, 183, -890),
                    new Vec3(1980, 191, -881),
                    new Vec3(1981, 183, -881),
                    new Vec3(1982, 191, -872),
                    new Vec3(1979, 183, -869),
                    new Vec3(1994, 183, -881),
                    new Vec3(1995, 191, -881),
                    new Vec3(1995, 191, -870),
                    new Vec3(1998, 183, -869),
                    new Vec3(1973, 232, -891),
                    new Vec3(1971, 231, -882),
                    new Vec3(1983, 231, -890),
                    new Vec3(1986, 231, -881),
                    new Vec3(1979, 231, -869),
                    new Vec3(1994, 231, -881),
                    new Vec3(1994, 231, -870)
            );
            instance = new MoontainCommon3SpawnController(spawnPos,
                    (int) MoontainEntities.commonUpPos.x, (int) MoontainEntities.commonUpPos.y, (int) MoontainEntities.commonUpPos.z,
                    (int) MoontainEntities.commonDownPos.x, (int) MoontainEntities.commonDownPos.y, (int) MoontainEntities.commonDownPos.z,
                    world, 160);
        }
        return instance;
    }

    public MoontainCommon3SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                          int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SenorPumpkinEntity senorPumpkinEntity = new SenorPumpkinEntity(BornInChaosV1ModEntities.SENOR_PUMPKIN.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(senorPumpkinEntity, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(senorPumpkinEntity, xpLevel, 3200, 200,
                200, 0.4, 5, 0.3, 70, 25,
                2000 * Math.pow(10, 4), 0.4);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(senorPumpkinEntity), list);
        return senorPumpkinEntity;
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
