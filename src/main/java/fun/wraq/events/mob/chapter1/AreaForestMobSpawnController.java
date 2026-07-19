/**
 * AI-Generated, 2026-07-19
 * 区域森林怪物刷新控制器：
 * 在 X3775,Z2958 - X4170,Z3185 区域内，当有玩家进入时，
 * 在其附近动态生成森林僵尸或森林骷髅。
 * <p>
 * 两种怪物共享相同的属性与掉落表，骷髅主手持击退弓。
 * 等级范围 27-37（基于 averageLevel=32 的 ±5 浮动），
 * 均落在要求的 25-40 区间内。
 * </p>
 */
package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.AreaMobSpawnController;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AreaForestMobSpawnController extends AreaMobSpawnController {

    public static String mobName = "森林僵尸";
    private static AreaForestMobSpawnController instance;

    /**
     * 获取单例，在首次调用时创建并自动注册到 {@link AreaMobSpawnController} 的静态列表中。
     * 每次调用都会确保控制器已注册（应对 {@code onServerStop} 清空列表后的重入场景）。
     */
    public static AreaForestMobSpawnController getInstance(Level level) {
        if (instance == null) {
            instance = new AreaForestMobSpawnController(
                    Te.s(mobName, CustomStyle.styleOfForest),
                    level, 32,
                    List.of(new MobSpawnController.Boundary(
                            new Vec3(4170, 200, 3185),
                            new Vec3(3775, 40, 2958))));
        }
        AreaMobSpawnController.registerController(instance, level);
        return instance;
    }

    private AreaForestMobSpawnController(Component mobName, Level level, int averageLevel,
                                         List<MobSpawnController.Boundary> boundaries) {
        super(mobName, level, averageLevel, boundaries);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Random random = new Random();
        if (random.nextBoolean()) {
            // 森林僵尸
            Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
            MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfForest);
            zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_AXE.getDefaultInstance());
            return zombie;
        } else {
            // 森林骷髅 — 主手持击退弓
            Skeleton skeleton = new Skeleton(EntityType.SKELETON, this.level);
            MobSpawn.setStainArmorOnMob(skeleton, CustomStyle.styleOfForest);
            ItemStack bow = Items.BOW.getDefaultInstance();
            bow.enchant(Enchantments.KNOCKBACK, 2);
            skeleton.setItemInHand(InteractionHand.MAIN_HAND, bow);
            return skeleton;
        }
    }

    @Override
    protected Component getMobName(Mob mob) {
        if (mob instanceof Skeleton) {
            return Te.s("森林骷髅", CustomStyle.styleOfForest);
        }
        return null; // 僵尸继续使用默认 mobName "森林僵尸"
    }

    @Override
    public MobAttributes getMobAttributes() {
        // 与 ForestZombieSpawnController 保持一致
        return new MobAttributes(120, 10, 10, 0.2, 1, 0, 0, 0, 600, 0.24);
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
            drops.add(new ItemAndRate(ModItems.FOREST_SOUL.get(), 0.1));
            drops.add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1.5));
            drops.add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));

            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_0.get(), 0.02));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_1.get(), 0.005));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_2.get(), 0.001));
            drops.add(new ItemAndRate(ModItems.PLAIN_CREST_3.get(), 0.0002));
            drops.add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.1));
            drops.add(new ItemAndRate(NewRuneItems.FOREST_NEW_RUNE.get(), 0.001));
        }
        return drops;
    }

    @Override
    public String getKillCountDataKey() {
        return "AreaForestZombie";
    }

    @Override
    public List<MobSpawnController.Boundary> getExcludedBoundaries() {
        return List.of(
                new MobSpawnController.Boundary(new Vec3(3986, 107, 3172), new Vec3(3946, 67, 3132)),
                new MobSpawnController.Boundary(new Vec3(4041, 139, 3178), new Vec3(4001, 99, 3138))
        );
    }
}
