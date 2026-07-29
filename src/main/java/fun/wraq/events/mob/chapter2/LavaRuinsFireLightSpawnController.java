/** AI-Generated, 2026-07-29 */
package fun.wraq.events.mob.chapter2;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.mcreator.borninchaosv.entity.FirelightEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 熔岩废墟 - 焰芒虫固定坐标刷怪控制器。
 * <p>
 * 分布在熔岩废墟区域（坐标组），等级 50-57，属性倍率 1.0-1.35。
 * 使用 GraySlime 的 Map{@literal <Vec3, RateAttr>} 模式，每点独立控制等级与倍率。
 * </p>
 */
public class LavaRuinsFireLightSpawnController extends MobSpawnController {

    public static String mobName = "焰芒虫";
    private static LavaRuinsFireLightSpawnController instance;

    public static LavaRuinsFireLightSpawnController getInstance(Level world) {
        if (instance == null) {
            Map<Vec3, RateAttr> posToRateAttrMap = new HashMap<>();

            // 坐标格式: new Vec3(x, y, z) → new RateAttr(等级, 属性倍率, 掉落倍率)
            posToRateAttrMap.put(new Vec3(3720, 72, 3730), new RateAttr(50, 1, 1));
            posToRateAttrMap.put(new Vec3(3735, 74, 3748), new RateAttr(51, 1, 1));
            posToRateAttrMap.put(new Vec3(3748, 77, 3766), new RateAttr(52, 1.1, 1.1));
            posToRateAttrMap.put(new Vec3(3725, 75, 3768), new RateAttr(53, 1.15, 1.15));
            posToRateAttrMap.put(new Vec3(3766, 80, 3782), new RateAttr(53, 1.15, 1.15));
            posToRateAttrMap.put(new Vec3(3738, 77, 3785), new RateAttr(54, 1.2, 1.2));
            posToRateAttrMap.put(new Vec3(3779, 79, 3797), new RateAttr(55, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(3752, 77, 3806), new RateAttr(55, 1.25, 1.25));
            posToRateAttrMap.put(new Vec3(3780, 82, 3815), new RateAttr(56, 1.3, 1.3));
            posToRateAttrMap.put(new Vec3(3757, 76, 3831), new RateAttr(56, 1.3, 1.3));
            posToRateAttrMap.put(new Vec3(3754, 79, 3855), new RateAttr(57, 1.35, 1.35));

            instance = new LavaRuinsFireLightSpawnController(posToRateAttrMap, world,
                    List.of(
                            new Boundary(new Vec3(3810, 90, 3870), new Vec3(3700, 65, 3720))
                    ));
        }
        return instance;
    }

    public LavaRuinsFireLightSpawnController(Map<Vec3, RateAttr> posToRateAttr,
                                              Level level, List<Boundary> multiBoundaryList) {
        super(Te.s(mobName, CustomStyle.styleOfVolcano), posToRateAttr, level, multiBoundaryList);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 40, 40, 0.35, 2.5, 0.15, 5, 10, 3500, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        return new FirelightEntity(BornInChaosV1ModEntities.FIRELIGHT.get(), this.level);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        List<ItemAndRate> drops = new ArrayList<>();
        drops.add(new ItemAndRate(ModItems.VOLCANO_SOUL.get(), 0.1));
        drops.add(new ItemAndRate(ModItems.COPPER_COIN.get(), 2.5));
        drops.add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
        drops.add(ItemAndRate.ofExp(6));
        return drops;
    }

    @Override
    public String getKillCountDataKey() {
        return "LavaRuinsFireLight";
    }

    @Override
    public int getSpawnInterval() {
        return Tick.s(60);
    }
}
