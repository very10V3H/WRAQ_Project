package fun.wraq.events.mob.chapter7;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MushroomLinSpawnController extends MobSpawnController {

    public static String mobName = "菌菇灵";
    private static MushroomLinSpawnController instance;

    public static MushroomLinSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2010, 126, -1814),
                    new Vec3(2033, 126, -1800),
                    new Vec3(2034, 126, -1786),
                    new Vec3(2033, 126, -1773),
                    new Vec3(2021, 127, -1758),
                    new Vec3(2006, 126, -1759),
                    new Vec3(1993, 126, -1759),
                    new Vec3(1978, 126, -1780),
                    new Vec3(1979, 126, -1799),
                    new Vec3(1996, 126, -1798)
            );
            instance = new MushroomLinSpawnController(spawnPos, 2059, -1738, 1968, -1836, world, 225);
        }
        return instance;
    }

    public MushroomLinSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, CustomStyle.MUSHROOM_STYLE), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Piglin mob = new Piglin(EntityType.PIGLIN, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.MUSHROOM_STYLE;
        MobSpawn.setMobCustomName(mob, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 3000, 200, 200,
                0.4, 5, 0.3, 75, 25,
                500 * Math.pow(10, 4), 0.4);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", CustomStyle.styleOfRed.getColor().getValue());
            tag.put("display", tag1);
            mob.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_MushroomCow"));
        if (random.nextBoolean()) {
            mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_SWORD));
        } else {
            mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.CROSSBOW));
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 5);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(MushroomItems.BROWN_MUSHROOM.get(), 0.8));
            add(new ItemAndRate(MushroomItems.NETHER_MUSHROOM.get(), 0.01));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.95));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.5));
        }};
    }
}
