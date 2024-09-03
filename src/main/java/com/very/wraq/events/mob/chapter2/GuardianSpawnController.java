package com.very.wraq.events.mob.chapter2;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C2LootItems;
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
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuardianSpawnController extends MobSpawnController {

    public static String mobName = "神殿守卫";
    private static GuardianSpawnController instance;

    public static GuardianSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1103, 30, 923),
                    new Vec3(1073, 30, 912),
                    new Vec3(1103, 30, 903),
                    new Vec3(1073, 30, 893),
                    new Vec3(1102, 30, 882),
                    new Vec3(1122, 22, 887),
                    new Vec3(1122, 22, 911),
                    new Vec3(1054, 22, 909),
                    new Vec3(1054, 22, 890),
                    new Vec3(1089, 20, 932),
                    new Vec3(1089, 20, 888)
            );
            instance = new GuardianSpawnController(spawnPos, 40, 1168, 949, 1007, 801, world, 4, 100);
        }
        return instance;
    }

    public GuardianSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Guardian guardian = new Guardian(EntityType.GUARDIAN, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSea;

        Compute.SetMobCustomName(guardian, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(guardian), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(guardian, 500, 1000, 1000, 0.35, 3, 0.2, 500, 15, 30000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            guardian.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        guardian.setItemInHand(InteractionHand.MAIN_HAND, Items.TRIDENT.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        MobSpawn.dropList.put(MobSpawn.getMobOriginName(guardian), list);
        return guardian;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.water, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SeaSoul.get(), 1.5));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.4375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.guardianSceptre.get(), 0.005));
        }};
    }
}
