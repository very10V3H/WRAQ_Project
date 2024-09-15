package com.very.wraq.events.mob.chapter1;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C2LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import net.mcreator.borninchaosv.entity.FirelightEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireLightSpawnController extends MobSpawnController {

    public static String mobName = "焰芒虫";
    private static FireLightSpawnController instance;

    public static FireLightSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(447, 71, -11),
                    new Vec3(477, 77, -4),
                    new Vec3(431, 70, 3),
                    new Vec3(461, 75, 17),
                    new Vec3(423, 72, 21),
                    new Vec3(482, 78, 22),
                    new Vec3(443, 75, 34),
                    new Vec3(462, 77, 34),
                    new Vec3(428, 73, 47),
                    new Vec3(481, 77, 47),
                    new Vec3(465, 76, 56),
                    new Vec3(445, 75, 60),
                    new Vec3(458, 78, 72),
                    new Vec3(488, 81, 74)
            );
            instance = new FireLightSpawnController(spawnPos, spawnPos.size() * 4, 553, 129, 374, -109, world, 4, 28);
        }
        return instance;
    }

    public FireLightSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        FirelightEntity firelight = new FirelightEntity(BornInChaosV1ModEntities.FIRELIGHT.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
        MobSpawn.setMobCustomName(firelight, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(firelight), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(firelight, 80, 50, 50, 0.2, 1, 0, 0, 0, 1500, 0.2);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            firelight.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        firelight.setItemInHand(InteractionHand.MAIN_HAND, Items.TRIDENT.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(firelight), list);
        return firelight;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 1);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VolcanoSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.copperCoin.get(), 1.5));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.01));
            add(new ItemAndRate(ModItems.VolcanoCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.VolcanoCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.VolcanoCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.VolcanoCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.volcanoNewRune.get(), 0.001));
            add(new ItemAndRate(C2LootItems.fireLightKnife.get(), 0.001));
        }};
    }
}
