package fun.wraq.events.mob.chapter2;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.mcreator.borninchaosv.entity.SearedSpiritEntity;
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

public class SearedSpirit2SpawnController extends MobSpawnController {

    public static String mobName = "炽魂";
    private static SearedSpirit2SpawnController instance;

    public static SearedSpirit2SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2535, 124, -613),
                    new Vec3(2547, 124, -599),
                    new Vec3(2551, 125, -614),
                    new Vec3(2558, 123, -593),
                    new Vec3(2559, 126, -622),
                    new Vec3(2565, 126, -610),
                    new Vec3(2575, 124, -617),
                    new Vec3(2576, 124, -601),
                    new Vec3(2584, 122, -582),
                    new Vec3(2588, 124, -610),
                    new Vec3(2594, 123, -594)
            );
            instance = new SearedSpirit2SpawnController(spawnPos, spawnPos.size() * 4, 2605, -575, 2525, -624, world, 4, 56);
        }
        return instance;
    }

    public SearedSpirit2SpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                        int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SearedSpiritEntity searedSpiritEntity = new SearedSpiritEntity(BornInChaosV1ModEntities.SEARED_SPIRIT.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfVolcano;
        MobSpawn.setMobCustomName(searedSpiritEntity, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(searedSpiritEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(searedSpiritEntity, 200, 35, 35, 0.3, 2, 0.1, 3, 10, 3000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            searedSpiritEntity.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(searedSpiritEntity), list);
        return searedSpiritEntity;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VolcanoSoul.get(), 1.5));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.VolcanoCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.VolcanoCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.VolcanoCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.VolcanoCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.2));
            add(new ItemAndRate(NewRuneItems.volcanoNewRune.get(), 0.001));
            add(new ItemAndRate(C2LootItems.searedSpiritStick.get(), 0.005));
        }};
    }
}
