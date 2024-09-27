package com.very.wraq.events.mob.chapter1;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C1LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
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

public class PlainZombieSpawnController extends MobSpawnController {

    public static String mobName = "平原僵尸";
    private static PlainZombieSpawnController instance;

    public static PlainZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(688, 80, 262),
                    new Vec3(703, 81, 275),
                    new Vec3(714, 79, 257),
                    new Vec3(729, 80, 272),
                    new Vec3(740, 78, 254),
                    new Vec3(750, 79, 270),
                    new Vec3(761, 78, 256),
                    new Vec3(772, 78, 2),
                    new Vec3(787, 77, 279),
                    new Vec3(823, 79, 265)
            );
            instance = new PlainZombieSpawnController(spawnPos, 40, 850, 290, 666, 235, world, 4, 4);
        }
        return instance;
    }

    public PlainZombieSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfPlain;

        MobSpawn.setMobCustomName(zombie, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 20, 0, 0, 0.2, 1, 0, 0, 0, 100, 0.2);

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
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_HOE.getDefaultInstance());

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
            add(new ItemAndRate(ModItems.PlainSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.copperCoin.get(), 1.5));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.01));
            add(new ItemAndRate(ModItems.PlainCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.PlainCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.PlainCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.PlainCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.plainNewRune.get(), 0.001));
            add(new ItemAndRate(C1LootItems.plainZombieHoe.get(), 0.005));
        }};
    }
}
