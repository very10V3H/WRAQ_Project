package fun.wraq.events.mob.chapter2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.mcreator.borninchaosv.entity.DreadHoundEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DreadHoundSpawnController extends MobSpawnController {

    public static String mobName = "森林狼";
    private static DreadHoundSpawnController instance;

    public static DreadHoundSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1754, 78, 55),
                    new Vec3(1763, 77, 31),
                    new Vec3(1776, 79, 54),
                    new Vec3(1787, 78, 29),
                    new Vec3(1798, 79, 52),
                    new Vec3(1808, 79, 27),
                    new Vec3(1821, 79, 51),
                    new Vec3(1832, 80, 25),
                    new Vec3(1845, 79, 48),
                    new Vec3(1858, 82, 24),
                    new Vec3(1871, 79, 46),
                    new Vec3(1888, 86, 23)
            );
            instance = new DreadHoundSpawnController(spawnPos, 1916, 56, 1731, -1, world, 66);
        }
        return instance;
    }

    public DreadHoundSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("森林狼", CustomStyle.styleOfForest), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        DreadHoundEntity dreadHoundEntity = new DreadHoundEntity(BornInChaosV1ModEntities.DREAD_HOUND.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfForest;
        MobSpawn.setMobCustomName(dreadHoundEntity, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(dreadHoundEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(dreadHoundEntity, 200, 45, 45,
                0.3, 2, 0.1, 3, 10, 8000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            dreadHoundEntity.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(dreadHoundEntity), list);
        return dreadHoundEntity;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 2);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ForestSoul.get(), 1));
            add(new ItemAndRate(ModItems.wolfLeather.get(), 1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.ForestCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.ForestCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.ForestCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.ForestCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.forestWolfChest.get(), 0.005));
        }};
    }
}
