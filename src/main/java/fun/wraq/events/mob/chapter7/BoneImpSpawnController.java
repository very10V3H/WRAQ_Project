package fun.wraq.events.mob.chapter7;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C7LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.mcreator.borninchaosv.entity.BoneImpEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoneImpSpawnController extends MobSpawnController {

    public static String mobName = "炽鬼";
    private static BoneImpSpawnController instance;

    public static BoneImpSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2536, 131, -658),
                    new Vec3(2552, 134, -661),
                    new Vec3(2568, 135, -656),
                    new Vec3(2582, 132, -652),
                    new Vec3(2592, 134, -659),
                    new Vec3(2606, 134, -659),
                    new Vec3(2615, 133, -651),
                    new Vec3(2622, 134, -638),
                    new Vec3(2625, 134, -623),
                    new Vec3(2643, 133, -616)
            );
            instance = new BoneImpSpawnController(spawnPos, 2656, -611, 2524, -678, world, 210);
        }
        return instance;
    }

    public BoneImpSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("炽鬼", CustomStyle.styleOfVolcano), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);;
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2800, 190, 190, 0.4, 3, 0.3, 70, 20, 400 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        BoneImpEntity boneImp = new BoneImpEntity(BornInChaosV1ModEntities.BONE_IMP.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
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
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 5);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(C7Items.BONE_IMP_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.87));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.5));
            add(new ItemAndRate(C7LootItems.BONE_IMP_HELMET.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "BoneImp";
    }
}
