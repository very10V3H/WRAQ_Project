package fun.wraq.events.mob.chapter2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.mcreator.borninchaosv.entity.FirelightEntity;
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

public class FireLightSpawnController extends MobSpawnController {

    public static String mobName = "焰芒虫";
    private static FireLightSpawnController instance;

    public static FireLightSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1274, 77, 112),
                    new Vec3(1265, 75, 101),
                    new Vec3(1274, 77, 90),
                    new Vec3(1267, 73, 79),
                    new Vec3(1283, 76, 72),
                    new Vec3(1273, 74, 64),
                    new Vec3(1284, 76, 53),
                    new Vec3(1276, 75, 42),
                    new Vec3(1284, 76, 25)
            );
            instance = new FireLightSpawnController(spawnPos, 1313, 126, 1243, -8, world, 40);
        }
        return instance;
    }

    public FireLightSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("焰芒虫", CustomStyle.styleOfVolcano), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
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
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(firelight, 150, 30, 30, 0.3, 2, 0.1, 3, 10, 2250, 0.3);

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

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(firelight), list);
        return firelight;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.fire, 2);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VolcanoSoul.get(), 1.2));
            add(new ItemAndRate(ModItems.copperCoin.get(), 3));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.VolcanoCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.VolcanoCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.VolcanoCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.VolcanoCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.FireElementPiece0.get(), 0.2));
            add(new ItemAndRate(NewRuneItems.volcanoNewRune.get(), 0.001));
            add(new ItemAndRate(C2LootItems.fireLightKnife.get(), 0.005));
        }};
    }
}
