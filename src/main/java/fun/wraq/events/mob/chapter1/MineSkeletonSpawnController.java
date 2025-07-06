package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class MineSkeletonSpawnController extends MobSpawnController {

    public static String mobName = "被遗忘的矿工";
    private static MineSkeletonSpawnController instance;

    public static MineSkeletonSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1169, -36, -35),
                    new Vec3(1173, -36, -43),
                    new Vec3(1168, -36, -53),
                    new Vec3(1173, -36, -63),
                    new Vec3(1173, -36, -79),
                    new Vec3(1169, -36, -91),
                    new Vec3(1173, -35, -102),
                    new Vec3(1169, -35, -115),
                    new Vec3(1173, -36, -126),
                    new Vec3(1169, -35, -137),
                    new Vec3(1173, -36, -148)
            );
            instance = new MineSkeletonSpawnController(spawnPos, 1200, -30, 1149, -164, world, 24);
        }
        return instance;
    }

    public MineSkeletonSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("被遗忘的矿工", CustomStyle.styleOfMine), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(100, 10, 10, 0.2, 1, 0, 0, 0, 1250, 0.2);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfMine;
        MobSpawn.setMobCustomName(skeleton, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, getMobAttributes());
        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.IRON_HELMET), new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_LEGGINGS), new ItemStack(Items.IRON_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            skeleton.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        skeleton.setItemInHand(InteractionHand.MAIN_HAND, Items.STONE_PICKAXE.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(skeleton), list);
        return skeleton;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.stone, 1);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(ModItems.MINE_SOUL.get(), 0.8),
                new ItemAndRate(ModItems.MINE_SOUL_1.get(), 0.1),
                new ItemAndRate(ModItems.COPPER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01),
                new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_0.get(), 0.1),
                new ItemAndRate(C1LootItems.MINE_SKELETON_PICKAXE.get(), 0.005),
                new ItemAndRate(NewRuneItems.MINE_NEW_RUNE.get(), 0.001),

                // 以下为矿石掉落
                new ItemAndRate(Items.RAW_COPPER, 0.2),
                new ItemAndRate(Items.RAW_IRON, 0.2),
                new ItemAndRate(Items.RAW_GOLD, 0.05),
                new ItemAndRate(Items.DIAMOND, 0.05)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "MineSkeleton";
    }
}
