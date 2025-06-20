package fun.wraq.events.mob.chapter5.origin;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C5LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BloodManaSpawnController extends MobSpawnController {

    public static String mobName = "腥月血灵";
    private static BloodManaSpawnController instance;

    public static BloodManaSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2204, 65, 1444),
                    new Vec3(2209, 67, 1427),
                    new Vec3(2221, 65, 1457),
                    new Vec3(2228, 70, 1417),
                    new Vec3(2238, 71, 1434),
                    new Vec3(2250, 67, 1453),
                    new Vec3(2254, 71, 1408),
                    new Vec3(2267, 71, 1423),
                    new Vec3(2272, 66, 1450),
                    new Vec3(2272, 72, 1394),
                    new Vec3(2289, 69, 1383),
                    new Vec3(2286, 69, 1405),
                    new Vec3(2307, 65, 1396),
                    new Vec3(2295, 65, 1422)
            );
            instance = new BloodManaSpawnController(spawnPos, 2326, 1480, 2162, 1322, world, 116);
        }
        return instance;
    }

    public BloodManaSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("腥月血灵", CustomStyle.styleOfBloodMana), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(700, 65, 65, 0.4, 3, 0.25, 10, 20, 100000, 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfBloodMana;
        MobSpawn.setMobCustomName(zombie, Component.literal("腥月血灵").withStyle(style), xpLevel);
        zombie.setBaby(true);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(zombie, style);
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 3);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BLOOD_MANA_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.615));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.03));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.3));
            add(new ItemAndRate(C5LootItems.BLOOD_MANA_LOOT_BOOTS.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "BloodMana";
    }
}
