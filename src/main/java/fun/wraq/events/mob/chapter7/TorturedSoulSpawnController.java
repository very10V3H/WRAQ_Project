package fun.wraq.events.mob.chapter7;

import com.obscuria.aquamirae.common.entities.TorturedSoul;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TorturedSoulSpawnController extends MobSpawnController {

    public static String mobName = "被折磨的灵魂";
    private static TorturedSoulSpawnController instance;

    public static TorturedSoulSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(968, 199, -15),
                    new Vec3(944, 198, -2),
                    new Vec3(954, 193, 0),
                    new Vec3(970, 199, 0),
                    new Vec3(981, 199, -5),
                    new Vec3(938, 197, 5),
                    new Vec3(954, 194, 7),
                    new Vec3(931, 195, 15),
                    new Vec3(940, 195, 11),
                    new Vec3(951, 195, 12),
                    new Vec3(966, 193, 14),
                    new Vec3(943, 196, 24),
                    new Vec3(943, 196, 35)
            );
            instance = new TorturedSoulSpawnController(spawnPos, 1000, 51, 900, -50, world, 220);
        }
        return instance;
    }

    public TorturedSoulSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("被折磨的灵魂", CustomStyle.styleOfWorld), canSpawnPos, boundaryUpX, 210,
                boundaryUpZ, boundaryDownX, 188, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(3200, 200, 200, 0.4, 3, 0.3, 70, 25, 500 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        TorturedSoul boneImp = new TorturedSoul(AquamiraeEntities.TORTURED_SOUL.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfWorld;
        MobSpawn.setMobCustomName(boneImp, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(boneImp), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(boneImp, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(boneImp), list);
        return boneImp;
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(C7Items.VD_SOUL.get(), 0.1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 1.3));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "TorturedSoul";
    }
}
