package com.very.wraq.events.mob.chapter6_castle;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C6LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
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
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlazeSpawnController extends MobSpawnController {

    public static String mobName = "熔岩湖溢出物";
    private static BlazeSpawnController instance;

    public static BlazeSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2437, 152, -1432),
                    new Vec3(2450, 152, -1434),
                    new Vec3(2461, 152, -1426),
                    new Vec3(2449, 149, -1420),
                    new Vec3(2429, 152, -1419),
                    new Vec3(2461, 152, -1409),
                    new Vec3(2433, 152, -1404),
                    new Vec3(2461, 152, -1409),
                    new Vec3(2447, 152, -1402)
            );
            instance = new BlazeSpawnController(spawnPos, spawnPos.size() * 4, 2476, -1395, 2420, -1448, world, 4, 180);
        }
        return instance;
    }

    public BlazeSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Blaze blaze = new Blaze(EntityType.BLAZE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfPower;
        Compute.SetMobCustomName(blaze, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(blaze), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(blaze, 2000, 4000, 2000, 0.4, 4, 0.25, 1400, 20, 300 * Math.pow(10, 4), 0.35);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            blaze.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        blaze.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(blaze), list);
        return blaze;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 4);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BlazeSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.58));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.06));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.4));
            add(new ItemAndRate(C6LootItems.blazeSword.get(), 0.005));
        }};
    }
}
