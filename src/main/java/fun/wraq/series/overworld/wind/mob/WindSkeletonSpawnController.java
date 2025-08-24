package fun.wraq.series.overworld.wind.mob;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.wind.WindItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WindSkeletonSpawnController extends MobSpawnController {

    public static String mobName = "怀德风骨";
    private static WindSkeletonSpawnController instance;

    public static WindSkeletonSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1795, 150, -1605),
                    new Vec3(1807, 150, -1614),
                    new Vec3(1819, 151, -1624),
                    new Vec3(1841, 150, -1630),
                    new Vec3(1864, 150, -1624),
                    new Vec3(1879, 158, -1611),
                    new Vec3(1892, 161, -1593),
                    new Vec3(1818, 153, -1595),
                    new Vec3(1834, 150, -1611),
                    new Vec3(1844, 151, -1594),
                    new Vec3(1859, 150, -1601)
            );
            instance = new WindSkeletonSpawnController(spawnPos, 1904, -1577, 1785, -1651, world, 190);
        }
        return instance;
    }

    public WindSkeletonSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("怀德风骨", CustomStyle.styleOfWind), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2200, 160, 160, 0.4, 3, 0.25, 60, 20, 400 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfWind;
        MobSpawn.setMobCustomName(skeleton, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(skeleton, style);
        skeleton.setItemInHand(InteractionHand.MAIN_HAND, WindItems.WIND_SWORD_0.get().getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(skeleton), list);
        return skeleton;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.wind, 4);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(WindItems.WIND_SOUL.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.58));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.WIND_ELEMENT_PIECE_0.get(), 0.4));
            add(new ItemAndRate(NewRuneItems.KAZE_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "WindSkeleton";
    }
}
