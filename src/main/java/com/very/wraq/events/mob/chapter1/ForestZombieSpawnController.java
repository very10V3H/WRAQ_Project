package com.very.wraq.events.mob.chapter1;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C1LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForestZombieSpawnController extends MobSpawnController {

    public static String mobName = "森林僵尸";
    private static ForestZombieSpawnController instance;

    public static ForestZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(858, 80, 243),
                    new Vec3(882, 78, 240),
                    new Vec3(888, 80, 226),
                    new Vec3(908, 81, 202),
                    new Vec3(913, 80, 183),
                    new Vec3(946, 79, 165),
                    new Vec3(948, 80, 140),
                    new Vec3(932, 79, 171)
            );
            instance = new ForestZombieSpawnController(spawnPos, 40, 975, 260, 824, 81, world, 4, 12);
        }
        return instance;
    }

    public ForestZombieSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfForest;

        Compute.SetMobCustomName(zombie, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 60, 50, 50, 0.2, 1, 0, 0, 0, 300, 0.2);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            zombie.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_AXE.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 1);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ForestSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.copperCoin.get(), 1.5));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.01));
            add(new ItemAndRate(ModItems.ForestCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.ForestCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.ForestCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.ForestCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.forestNewRune.get(), 0.001));
            add(new ItemAndRate(C1LootItems.forestZombieAxe.get(), 0.005));
        }};
    }
}
