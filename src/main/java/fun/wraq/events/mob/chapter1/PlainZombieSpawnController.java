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

public class PlainZombieSpawnController extends MobSpawnController {

    public static String mobName = "平原僵尸";
    private static PlainZombieSpawnController instance;

    public static PlainZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            Map<Vec3, RateAttr> posToRateAttrMap = new HashMap<>();
            posToRateAttrMap.put(new Vec3(4009, 67, 3381), new RateAttr(0));
            posToRateAttrMap.put(new Vec3(4017, 66, 3373), new RateAttr(1));
            posToRateAttrMap.put(new Vec3(4024, 68, 3383), new RateAttr(2, 1.1, 1.1));
            posToRateAttrMap.put(new Vec3(4032, 67, 3374), new RateAttr(3, 1.15, 1.15));
            posToRateAttrMap.put(new Vec3(4031, 69, 3390), new RateAttr(4, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(4050, 66, 3374), new RateAttr(5, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(4050, 68, 3384), new RateAttr(5, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(4051, 72, 3396), new RateAttr(6, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(4059, 72, 3399), new RateAttr(6, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(4059, 68, 3385), new RateAttr(7, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(4059, 66, 3376), new RateAttr(8, 1.3, 1.3));
            posToRateAttrMap.put(new Vec3(4072, 66, 3382), new RateAttr(9, 1.35, 1.35));
            posToRateAttrMap.put(new Vec3(4072, 69, 3393), new RateAttr(9, 1.35, 1.35));

            instance = new PlainZombieSpawnController(posToRateAttrMap, world,
                    List.of(new Boundary(new Vec3(4100, 90, 3427), new Vec3(3980, 50, 3345))));
        }
        return instance;
    }

    public PlainZombieSpawnController(Map<Vec3, RateAttr> posToRateAttr,
                                      Level level, List<Boundary> multiBoundaryList) {
        super(Te.s("平原僵尸", CustomStyle.styleOfPlain), posToRateAttr, level, multiBoundaryList);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(20, 0, 0, 0.2, 1, 0, 0, 0, 20, 0.2);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        // 设置装备
        MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfPlain);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_HOE.getDefaultInstance());
        return zombie;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.life, 1);
    }

    @Override
    public List<ItemAndRate> getDropList(int xpLevel) {
        List<ItemAndRate> drops = new ArrayList<>();
        drops.add(new ItemAndRate(ModItems.PLAIN_SOUL.get(), 0.1));
        drops.add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1));
        drops.add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
        drops.add(ItemAndRate.ofExp(1));
        if (xpLevel >= 10) {
            drops.add(ItemAndRate.ofExp(1));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_0.get(), 0.02));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_1.get(), 0.005));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_2.get(), 0.001));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_3.get(), 0.0002));
            drops.add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.1));
            drops.add(new ItemAndRate(NewRuneItems.PLAIN_NEW_RUNE.get(), 0.001));
        }
        return drops;
    }

    @Override
    public String getKillCountDataKey() {
        return "PlainZombie";
    }
}
