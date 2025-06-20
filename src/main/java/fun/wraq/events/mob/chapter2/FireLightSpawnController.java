package fun.wraq.events.mob.chapter2;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.mcreator.borninchaosv.entity.FirelightEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireLightSpawnController extends MobSpawnController {

    public static String mobName = "焰芒虫";
    private static FireLightSpawnController instance;

    public static FireLightSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1274, 77, 112),
                    new Vec3(1265, 75, 101),
                    new Vec3(1274, 77, 90),
                    new Vec3(1267, 73, 79),
                    new Vec3(1283, 76, 72),
                    new Vec3(1273, 74, 64),
                    new Vec3(1284, 76, 53),
                    new Vec3(1276, 75, 42),
                    new Vec3(1284, 76, 25)
            );
            instance = new FireLightSpawnController(spawnPos, 1313, 126, 1243, -8, world, 40);
        }
        return instance;
    }

    public FireLightSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("焰芒虫", CustomStyle.styleOfVolcano), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(150, 30, 30, 0.3, 2, 0.1, 3, 10, 2250, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        FirelightEntity firelight = new FirelightEntity(BornInChaosV1ModEntities.FIRELIGHT.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
        MobSpawn.setMobCustomName(firelight, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(firelight), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(firelight, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(firelight), list);
        return firelight;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VOLCANO_SOUL.get(), 1.2));
            add(new ItemAndRate(ModItems.COPPER_COIN.get(), 3));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(NewRuneItems.VOLCANO_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C2LootItems.FIRE_LIGHT_KNIFE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "FireLight";
    }
}
