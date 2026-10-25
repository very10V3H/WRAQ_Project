package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ForestZombieSpawnController extends MobSpawnController {

    public static String mobName = "森林僵尸";
    private static ForestZombieSpawnController instance;

    public static ForestZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            // 保留 RateAttr 机制，仅把刷新点换回 d3bfb71d 之前的坐标。
            Map<Vec3, RateAttr> posToRateAttrMap = new HashMap<>();
            posToRateAttrMap.put(new Vec3(858, 80, 243), new RateAttr(10));
            posToRateAttrMap.put(new Vec3(882, 78, 240), new RateAttr(11, 1.05, 1.05));
            posToRateAttrMap.put(new Vec3(888, 80, 226), new RateAttr(12, 1.1, 1.1));
            posToRateAttrMap.put(new Vec3(908, 81, 202), new RateAttr(13, 1.15, 1.15));
            posToRateAttrMap.put(new Vec3(913, 80, 183), new RateAttr(14, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(946, 79, 165), new RateAttr(15, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(948, 80, 140), new RateAttr(16, 1.3, 1.3));
            posToRateAttrMap.put(new Vec3(932, 79, 171), new RateAttr(17, 1.35, 1.35));

            instance = new ForestZombieSpawnController(posToRateAttrMap, world,
                    List.of(new Boundary(new Vec3(975, 120, 260), new Vec3(824, 50, 81))));
        }
        return instance;
    }

    public ForestZombieSpawnController(Map<Vec3, RateAttr> posToRateAttr,
                                       Level level, List<Boundary> multiBoundaryList) {
        super(Te.s("森林僵尸", CustomStyle.styleOfForest), posToRateAttr, level, multiBoundaryList);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(60, 5, 5, 0.2, 1, 0, 0, 0, 300, 0.2);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        // 设置装备
        MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfForest);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_AXE.getDefaultInstance());
        return zombie;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.life, 1);
    }

    @Override
    public List<ItemAndRate> getDropList(int xpLevel) {
        List<ItemAndRate> drops = new ArrayList<>();
        drops.add(new ItemAndRate(ModItems.FOREST_SOUL.get(), 0.1));
        drops.add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1.5));
        drops.add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
        drops.add(ItemAndRate.ofExp(2));
        if (xpLevel >= 20) {
            drops.add(ItemAndRate.ofExp(2));
            drops.add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.1));
            drops.add(new ItemAndRate(NewRuneItems.FOREST_NEW_RUNE.get(), 0.001));
        }
        return drops;
    }

    @Override
    public String getKillCountDataKey() {
        return "ForestZombie";
    }
}
