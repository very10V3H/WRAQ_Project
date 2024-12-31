package fun.wraq.events.mob.chapter3_nether;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetherSkeletonSpawnController extends MobSpawnController {

    public static String mobName = "下界骷髅";
    private static NetherSkeletonSpawnController instance;

    public static NetherSkeletonSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(507, 67, -711),
                    new Vec3(507, 67, -678),
                    new Vec3(474, 67, -633),
                    new Vec3(509, 67, -633),
                    new Vec3(544, 67, -633),
                    new Vec3(558, 67, -624),
                    new Vec3(558, 67, -672),
                    new Vec3(577, 67, -665),
                    new Vec3(565, 67, -649),
                    new Vec3(577, 67, -665),
                    new Vec3(565, 70, -624)
            );
            instance = new NetherSkeletonSpawnController(spawnPos, 598, -583, 445, -753, world, 80);
        }
        return instance;
    }

    public NetherSkeletonSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                         int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("下界骷髅", CustomStyle.styleOfNether), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfNether;
        MobSpawn.setMobCustomName(skeleton, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, 200, 50, 50, 0.35, 3, 0.2, 5, 15, 9000, 0.25);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.NETHERITE_HELMET), new ItemStack(Items.NETHERITE_CHESTPLATE),
                new ItemStack(Items.NETHERITE_LEGGINGS), new ItemStack(Items.NETHERITE_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            skeleton.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        skeleton.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(skeleton), list);
        return skeleton;
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
            add(new ItemAndRate(ModItems.netherSkeletonSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.5));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.04));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.4));
            add(new ItemAndRate(ModItems.toNether.get(), 0.01));
            add(new ItemAndRate(C3LootItems.netherSkeletonLootBow.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.netherNewRune.get(), 0.001));
        }};
    }
}
