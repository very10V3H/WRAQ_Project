package fun.wraq.events.mob.moontain;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.mcreator.borninchaosv.entity.FelsteedEntity;
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

public class MoontainCommon2ExSpawnController extends MobSpawnController {

    public static String mobName = "望山魂驹E";
    private static MoontainCommon2ExSpawnController instance;

    public static MoontainCommon2ExSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1985, 223, -872),
                    new Vec3(1995, 223, -893),
                    new Vec3(1996, 223, -883),
                    new Vec3(1997, 223, -872)
            );
            instance = new MoontainCommon2ExSpawnController(spawnPos,
                    (int) MoontainEntities.commonUpPos.x, (int) MoontainEntities.commonUpPos.y, (int) MoontainEntities.commonUpPos.z,
                    (int) MoontainEntities.commonDownPos.x, (int) MoontainEntities.commonDownPos.y, (int) MoontainEntities.commonDownPos.z,
                    world, 180);
        }
        return instance;
    }

    public MoontainCommon2ExSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                            int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("望山魂驹", CustomStyle.styleOfMoontain), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 8, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        FelsteedEntity felsteedEntity = new FelsteedEntity(BornInChaosV1ModEntities.FELSTEED.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(felsteedEntity, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(felsteedEntity, xpLevel, 2200, 155,
                155, 0.4, 3, 0.3, 60, 20,
                350 * Math.pow(10, 4), 0.4);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(felsteedEntity), list);
        return felsteedEntity;
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
            add(new ItemAndRate(MoontainItems.LEATHER.get(), 0.12));
            add(new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 0.36));
            add(new ItemAndRate(ModItems.silverCoin.get(), 1.1));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainCommon2";
    }
}
