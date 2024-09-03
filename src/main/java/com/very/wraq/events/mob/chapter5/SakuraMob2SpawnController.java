package com.very.wraq.events.mob.chapter5;

import com.very.wraq.entities.entities.SakuraMob.SakuraMob;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C5LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
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

public class SakuraMob2SpawnController extends MobSpawnController {

    public static String mobName = "樱灵";
    private static SakuraMob2SpawnController instance;

    public static SakuraMob2SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2249, 142, 1761),
                    new Vec3(2273, 144, 1763),
                    new Vec3(2297, 148, 1755),
                    new Vec3(2312, 152, 1739),
                    new Vec3(2326, 153, 1724),
                    new Vec3(2342, 155, 1708),
                    new Vec3(2358, 158, 1695),
                    new Vec3(2269, 144, 1737),
                    new Vec3(2296, 147, 1717),
                    new Vec3(2318, 148, 1699),
                    new Vec3(2339, 150, 1677)
            );
            instance = new SakuraMob2SpawnController(spawnPos, spawnPos.size() * 4, 2358, 1775, 2221, 1630, world, 4, 132);
        }
        return instance;
    }

    public SakuraMob2SpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SakuraMob sakuraMob = new SakuraMob(ModEntityType.SakuraMob.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfSakura;
        Compute.SetMobCustomName(sakuraMob, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(sakuraMob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(sakuraMob, 0, 1400, 1400, 0.4, 4, 0.25, 700, 20, 240000, 0.35);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            sakuraMob.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(sakuraMob), list);
        return sakuraMob;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 3);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SakuraPetal.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.615));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.03));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.3));
            add(new ItemAndRate(C5LootItems.sakuraChest.get(), 0.005));
        }};
    }
}
