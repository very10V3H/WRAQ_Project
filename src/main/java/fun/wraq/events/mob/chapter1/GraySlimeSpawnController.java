/** AI-Generated, 2026-07-21 */
package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.c1.NewC1Items;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

/**
 * 苍峡软玉 GraySlime 的固定坐标刷怪控制器。
 * <p>
 * 分布在两个区域（坐标组），等级 35-45，属性倍率 1.0-2.0。
 * </p>
 */
public class GraySlimeSpawnController extends MobSpawnController {

    public static String mobName = "苍峡软玉";
    private static GraySlimeSpawnController instance;

    public static GraySlimeSpawnController getInstance(Level world) {
        if (instance == null) {
            Map<Vec3, RateAttr> posToRateAttrMap = new HashMap<>();

            // 区域一：X:3654-3717, Y:65-68, Z:2942-2966
            posToRateAttrMap.put(new Vec3(3717, 68, 2946), new RateAttr(35, 1, 1));
            posToRateAttrMap.put(new Vec3(3708, 68, 2954), new RateAttr(35, 1, 1));
            posToRateAttrMap.put(new Vec3(3702, 67, 2965), new RateAttr(36, 1.1, 1.05));
            posToRateAttrMap.put(new Vec3(3698, 68, 2945), new RateAttr(36, 1.1, 1.05));
            posToRateAttrMap.put(new Vec3(3692, 67, 2966), new RateAttr(37, 1.2, 1.1));
            posToRateAttrMap.put(new Vec3(3687, 66, 2942), new RateAttr(37, 1.2, 1.1));
            posToRateAttrMap.put(new Vec3(3682, 67, 2965), new RateAttr(38, 1.3, 1.15));
            posToRateAttrMap.put(new Vec3(3679, 66, 2950), new RateAttr(38, 1.3, 1.15));
            posToRateAttrMap.put(new Vec3(3665, 66, 2942), new RateAttr(39, 1.4, 1.2));
            posToRateAttrMap.put(new Vec3(3654, 65, 2951), new RateAttr(40, 1.4, 1.2));

            // 区域二：X:3468-3546, Y:81-94, Z:2906-2929
            posToRateAttrMap.put(new Vec3(3546, 81, 2906), new RateAttr(41, 1.5, 1.25));
            posToRateAttrMap.put(new Vec3(3542, 81, 2928), new RateAttr(42, 1.5, 1.25));
            posToRateAttrMap.put(new Vec3(3530, 82, 2907), new RateAttr(43, 1.6, 1.3));
            posToRateAttrMap.put(new Vec3(3526, 85, 2928), new RateAttr(44, 1.7, 1.35));
            posToRateAttrMap.put(new Vec3(3513, 86, 2908), new RateAttr(45, 1.8, 1.4));
            posToRateAttrMap.put(new Vec3(3509, 87, 2929), new RateAttr(45, 1.9, 1.45));
            posToRateAttrMap.put(new Vec3(3496, 88, 2910), new RateAttr(45, 2, 1.5));
            posToRateAttrMap.put(new Vec3(3484, 93, 2925), new RateAttr(45, 2, 1.5));
            posToRateAttrMap.put(new Vec3(3468, 94, 2919), new RateAttr(45, 2, 1.5));

            instance = new GraySlimeSpawnController(posToRateAttrMap, world,
                    List.of(
                            new Boundary(new Vec3(3720, 75, 2970), new Vec3(3650, 60, 2940)),
                            new Boundary(new Vec3(3550, 100, 2935), new Vec3(3460, 75, 2900))
                    ));
        }
        return instance;
    }

    public GraySlimeSpawnController(Map<Vec3, RateAttr> posToRateAttr,
                                    Level level, List<Boundary> multiBoundaryList) {
        super(Te.s("苍峡软玉", CustomStyle.styleOfStone), posToRateAttr, level, multiBoundaryList);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(600, 150, 150, 0.2, 1.5, 0, 0, 0, 5000, 0.15);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        return new fun.wraq.entities.entities.GraySlime.GraySlime(
                fun.wraq.common.registry.ModEntityType.GRAY_SLIME.get(), this.level);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.water, 1);
    }

    @Override
    public List<ItemAndRate> getDropList(int xpLevel) {
        List<ItemAndRate> drops = new ArrayList<>();
        drops.add(new ItemAndRate(NewC1Items.GRAY_SLIME_BALL.get(), 0.3));
        drops.add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1));
        drops.add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
        drops.add(ItemAndRate.ofExp(2));
        return drops;
    }

    @Override
    public String getKillCountDataKey() {
        return "GraySlime";
    }
}
