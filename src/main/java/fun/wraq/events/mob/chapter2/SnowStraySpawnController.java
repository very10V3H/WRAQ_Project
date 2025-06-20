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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowStraySpawnController extends MobSpawnController {

    public static String mobName = "冰川流浪者";
    private static SnowStraySpawnController instance;

    public static SnowStraySpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1558, 63, -2202),
                    new Vec3(1567, 63, -2215),
                    new Vec3(1559, 63, -2231),
                    new Vec3(1643, 63, -2241),
                    new Vec3(1625, 63, -2247),
                    new Vec3(1580, 63, -2259),
                    new Vec3(1582, 63, -2308),
                    new Vec3(1568, 63, -2289),
                    new Vec3(1552, 63, -2302),
                    new Vec3(1521, 63, -2312),
                    new Vec3(1502, 63, -2278),
                    new Vec3(1490, 63, -2303)
            );
            instance = new SnowStraySpawnController(spawnPos, 1682, -2171, 1461, -2377, world, 100);
        }
        return instance;
    }

    public SnowStraySpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("冰川流浪者", CustomStyle.styleOfSnow), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(500, 60, 60, 0.35, 3, 0.2, 5, 15, 30000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Stray stray = new Stray(EntityType.STRAY, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSnow;
        MobSpawn.setMobCustomName(stray, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(stray, style);
        stray.setItemInHand(InteractionHand.MAIN_HAND, ModItems.SNOW_SWORD_3.get().getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(stray), list);
        return stray;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SNOW_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.5));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.SNOW_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.SNOW_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.SNOW_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.SNOW_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.SNOW_STRAY_PICKAXE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "SnowStray";
    }
}
