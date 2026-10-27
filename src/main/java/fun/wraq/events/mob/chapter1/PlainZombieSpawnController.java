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
            // 保留 RateAttr 机制，仅把刷新点换回 d3bfb71d 之前的坐标。
            Map<Vec3, RateAttr> posToRateAttrMap = new HashMap<>();
            posToRateAttrMap.put(new Vec3(812, 81, 172), new RateAttr(0));
            posToRateAttrMap.put(new Vec3(822, 82, 185), new RateAttr(1));
            posToRateAttrMap.put(new Vec3(934, 82, 177), new RateAttr(2, 1.1, 1.1));
            posToRateAttrMap.put(new Vec3(831, 82, 198), new RateAttr(3, 1.15, 1.15));
            posToRateAttrMap.put(new Vec3(854, 83, 171), new RateAttr(4, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(851, 82, 185), new RateAttr(5, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(866, 81, 151), new RateAttr(6, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(879, 80, 161), new RateAttr(7, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(888, 80, 176), new RateAttr(8, 1.3, 1.3));
            posToRateAttrMap.put(new Vec3(870, 81, 195), new RateAttr(9, 1.35, 1.35));

            // 旧刷新区域：X 上界从 902 扩到 950，覆盖 x=934 的刷新点。
            instance = new PlainZombieSpawnController(posToRateAttrMap, world,
                    List.of(new Boundary(new Vec3(950, 120, 210), new Vec3(795, 50, 129))));
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
    public Mob setMobItem() {
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
