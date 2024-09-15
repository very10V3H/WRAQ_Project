package com.very.wraq.events.mob.chapter7;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C7LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.mcreator.borninchaosv.entity.BoneImpEntity;
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

public class BoneImpSpawnController extends MobSpawnController {

    public static String mobName = "炽鬼";
    private static BoneImpSpawnController instance;

    public static BoneImpSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2536, 131, -658),
                    new Vec3(2552, 134, -661),
                    new Vec3(2568, 135, -656),
                    new Vec3(2582, 132, -652),
                    new Vec3(2592, 134, -659),
                    new Vec3(2606, 134, -659),
                    new Vec3(2615, 133, -651),
                    new Vec3(2622, 134, -638),
                    new Vec3(2625, 134, -623),
                    new Vec3(2643, 133, -616)
            );
            instance = new BoneImpSpawnController(spawnPos, spawnPos.size() * 4, 2656, -611, 2524, -678, world, 4, 210);
        }
        return instance;
    }

    public BoneImpSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                  int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        BoneImpEntity boneImp = new BoneImpEntity(BornInChaosV1ModEntities.BONE_IMP.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
        Compute.setMobCustomName(boneImp, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(boneImp), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(boneImp, 2800, 4400, 4400, 0.4, 4, 0.3, 2200, 20, 800 * Math.pow(10, 4), 0.4);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            boneImp.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(boneImp), list);
        return boneImp;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 5);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(C7Items.boneImpSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.87));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.06));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.5));
            add(new ItemAndRate(C7LootItems.boneImpHelmet.get(), 0.005));
        }};
    }
}
