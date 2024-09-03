package com.very.wraq.events.mob.chapter1;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C1LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeDrownSpawnController extends MobSpawnController {

    public static String mobName = "河流故灵";
    private static LakeDrownSpawnController instance;

    public static LakeDrownSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1225, 63, 18),
                    new Vec3(1240, 63, 23),
                    new Vec3(1211, 63, 28),
                    new Vec3(1226, 63, 34),
                    new Vec3(1241, 63, 41),
                    new Vec3(1209, 63, 43),
                    new Vec3(1226, 63, 49),
                    new Vec3(1241, 63, 171),
                    new Vec3(1203, 63, 55),
                    new Vec3(1233, 63, 61),
                    new Vec3(1215, 63, 63)
            );
            instance = new LakeDrownSpawnController(spawnPos, 40, 1262, 71, 1182, -4, world, 4, 18);
        }
        return instance;
    }

    public LakeDrownSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Drowned drowned = new Drowned(EntityType.DROWNED, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfWater;

        Compute.SetMobCustomName(drowned, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(drowned), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(drowned, 80, 50, 50, 0.2, 1, 0, 0, 0, 750, 0.2);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            drowned.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        drowned.setItemInHand(InteractionHand.MAIN_HAND, Items.TRIDENT.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        MobSpawn.dropList.put(MobSpawn.getMobOriginName(drowned), list);
        return drowned;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.water, 1);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LakeSoul.get(), 1.5));
            add(new ItemAndRate(ModItems.copperCoin.get(), 3));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.01));
            add(new ItemAndRate(ModItems.LakeCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.LakeCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.LakeCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.LakeCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.lakeNewRune.get(), 0.001));
            add(new ItemAndRate(C1LootItems.lakeDrownHelmet.get(), 0.005));
        }};
    }
}
