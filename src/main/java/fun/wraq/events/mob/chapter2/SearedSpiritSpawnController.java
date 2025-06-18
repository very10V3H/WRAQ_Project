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
import net.mcreator.borninchaosv.entity.SearedSpiritEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearedSpiritSpawnController extends MobSpawnController {

    public static String mobName = "炽魂";
    private static SearedSpiritSpawnController instance;

    public static SearedSpiritSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1494, 80, 22),
                    new Vec3(1476, 79, 26),
                    new Vec3(1454, 78, 20),
                    new Vec3(1439, 80, 27),
                    new Vec3(1437, 80, 8),
                    new Vec3(1450, 79, -12),
                    new Vec3(1465, 78, -27),
                    new Vec3(1476, 74, -49)
            );
            instance = new SearedSpiritSpawnController(spawnPos, 1526, 43, 1405, -107, world, 48);
        }
        return instance;
    }

    public SearedSpiritSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("炽魂", CustomStyle.styleOfVolcano), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 35, 35, 0.3, 2, 0.1, 3, 10, 4500, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SearedSpiritEntity searedSpiritEntity = new SearedSpiritEntity(BornInChaosV1ModEntities.SEARED_SPIRIT.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
        MobSpawn.setMobCustomName(searedSpiritEntity, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(searedSpiritEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(searedSpiritEntity, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(searedSpiritEntity), list);
        return searedSpiritEntity;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VOLCANO_SOUL.get(), 1.5));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.VOLCANO_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(NewRuneItems.VOLCANO_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C2LootItems.SEARED_SPIRIT_STICK.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "SearedSpirit";
    }
}
