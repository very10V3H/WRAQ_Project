package fun.wraq.events.mob.moontain;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
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
                    new Vec3(1996, 159, -867)
            );
            instance = new MoontainCommon1SpawnController(spawnPos,
                    (int) MoontainEntities.commonUpPos.x, (int) MoontainEntities.commonUpPos.y, (int) MoontainEntities.commonUpPos.z,
                    (int) MoontainEntities.commonDownPos.x, (int) MoontainEntities.commonDownPos.y, (int) MoontainEntities.commonDownPos.z,
                    world, 160);
        }
        return instance;
    }

    public MoontainCommon1SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                          int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("望山孤魂", CustomStyle.styleOfMoontain), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 8, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(1600, 130, 130, 0.4, 3, 0.3, 45, 20, 250 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        MrPumpkinEntity mrPumpkin = new MrPumpkinEntity(BornInChaosV1ModEntities.MR_PUMPKIN.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(mrPumpkin, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mrPumpkin, xpLevel, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mrPumpkin), list);
        return mrPumpkin;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.tickCount % 100 == 0) {
            mob.addEffect(new MobEffectInstance(MobEffects.LUCK, 200));
        }
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(MoontainItems.SOUL_FRAGMENT.get(), 0.1));
            add(new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 0.25));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.8));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainCommon1";
    }
}
