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
import net.minecraft.network.chat.Component;
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
                    new Vec3(567, 67, 365),
                    new Vec3(615, 69, 362),
                    new Vec3(665, 73, 363),
                    new Vec3(717, 73, 375),
                    new Vec3(566, 65, 403),
                    new Vec3(617, 68, 395),
                    new Vec3(641, 70, 420),
                    new Vec3(700, 70, 416),
                    new Vec3(540, 67, 436),
                    new Vec3(589, 68, 445),
                    new Vec3(655, 69, 455),
                    new Vec3(733, 67, 457),
                    new Vec3(569, 69, 489),
                    new Vec3(629, 69, 483),
                    new Vec3(699, 68, 489)
            );
            instance = new WindSkeletonSpawnController(spawnPos, 796, 537, 467, 272, world, 80);
        }
        return instance;
    }

    public WindSkeletonSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("怀德风骨", CustomStyle.styleOfWind), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(400, 50, 50, 0.35, 3, 0.2, 5, 15, 9000, 0.3);
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
        skeleton.setItemInHand(InteractionHand.MAIN_HAND, ModItems.KAZE_SWORD_0.get().getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(skeleton), list);
        return skeleton;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.wind, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.KAZE_SOUL.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.4375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.WIND_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.WIND_SKELETON_SWORD.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.KAZE_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "WindSkeleton";
    }
}
