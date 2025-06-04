package fun.wraq.events.mob.moontain;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.mcreator.borninchaosv.entity.MrPumpkinEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoontainCommon1ExSpawnController extends MobSpawnController {

    public static String mobName = "望山孤魂E";
    private static MoontainCommon1ExSpawnController instance;

    public static MoontainCommon1ExSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1975, 207, -892),
                    new Vec3(1971, 207, -881),
                    new Vec3(1988, 207, -894),
                    new Vec3(1981, 207, -881),
                    new Vec3(1984, 207, -872),
                    new Vec3(1995, 207, -881),
                    new Vec3(1996, 207, -870)
            );
            instance = new MoontainCommon1ExSpawnController(spawnPos,
                    (int) MoontainEntities.commonUpPos.x, (int) MoontainEntities.commonUpPos.y, (int) MoontainEntities.commonUpPos.z,
                    (int) MoontainEntities.commonDownPos.x, (int) MoontainEntities.commonDownPos.y, (int) MoontainEntities.commonDownPos.z,
                    world, 170);
        }
        return instance;
    }

    public MoontainCommon1ExSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                            int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("望山孤魂", CustomStyle.styleOfMoontain), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 8, level, averageLevel);
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
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mrPumpkin, xpLevel, 1800, 140,
                140, 0.4, 3, 0.3, 55, 20,
                300 * Math.pow(10, 4), 0.35);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mrPumpkin), list);
        return mrPumpkin;
    }

    @Override
    public void tick() {
        if (Tick.get() % 100 == 0) {
            mobList.forEach(mob -> {
                mob.addEffect(new MobEffectInstance(MobEffects.LUCK, 200));
            });
        }
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(MoontainItems.SOUL_FRAGMENT.get(), 0.12));
            add(new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 0.3));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.96));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainCommon1";
    }
}
