package fun.wraq.events.mob.chapter3_nether;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C3LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WitherSkeletonSpawnController extends MobSpawnController {

    public static String mobName = "凋零骷髅";
    private static WitherSkeletonSpawnController instance;

    public static WitherSkeletonSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(507, 67, -698),
                    new Vec3(507, 67, -650),
                    new Vec3(491, 67, -633),
                    new Vec3(525, 67, -633),
                    new Vec3(558, 67, -597),
                    new Vec3(558, 67, -646),
                    new Vec3(533, 67, -665),
                    new Vec3(565, 70, -624),
                    new Vec3(578, 67, -633),
                    new Vec3(577, 67, -665),
                    new Vec3(507, 67, -620)
            );
            instance = new WitherSkeletonSpawnController(spawnPos, 598, -583, 445, -753, world, 80);
        }
        return instance;
    }

    public WitherSkeletonSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                         int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("森林僵尸", CustomStyle.styleOfWither), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        WitherSkeleton witherSkeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfWither;
        MobSpawn.setMobCustomName(witherSkeleton, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(witherSkeleton), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(witherSkeleton, 200, 50, 50, 0.35, 3, 0.2, 5, 15, 9000, 0.25);

        // 设置物品
        witherSkeleton.setItemInHand(InteractionHand.MAIN_HAND, Items.STONE_SWORD.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(witherSkeleton), list);
        return witherSkeleton;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 4);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.Ruby.get(), 1));
            add(new ItemAndRate(ModItems.NetherQuartz.get(), 0.33));
            add(new ItemAndRate(ModItems.witherSkeletonSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.5));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.04));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.4));
            add(new ItemAndRate(ModItems.toNether.get(), 0.01));
            add(new ItemAndRate(C3LootItems.witherSkeletonLootSword.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.netherNewRune.get(), 0.001));
        }};
    }
}
