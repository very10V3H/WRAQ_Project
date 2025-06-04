package fun.wraq.events.mob.chapter2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.mcreator.borninchaosv.entity.ZombieLumberjackEntity;
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

public class LumberJackSpawnController extends MobSpawnController {

    public static String mobName = "伐木工";
    private static LumberJackSpawnController instance;

    public static LumberJackSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1596, 75, 55),
                    new Vec3(1604, 76, 34),
                    new Vec3(1619, 75, 54),
                    new Vec3(1631, 76, 32),
                    new Vec3(1641, 76, 53),
                    new Vec3(1649, 78, 32),
                    new Vec3(1664, 77, 52),
                    new Vec3(1673, 79, 32),
                    new Vec3(1682, 77, 48),
                    new Vec3(1700, 80, 33),
                    new Vec3(1701, 78, 47)
            );
            instance = new LumberJackSpawnController(spawnPos, 1737, 64, 1571, -7, world, 60);
        }
        return instance;
    }

    public LumberJackSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("伐木工", CustomStyle.styleOfForest), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        ZombieLumberjackEntity zombieLumberjackEntity = new ZombieLumberjackEntity(BornInChaosV1ModEntities.ZOMBIE_LUMBERJACK.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfHusk;
        MobSpawn.setMobCustomName(zombieLumberjackEntity, Component.literal("伐木工").withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombieLumberjackEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombieLumberjackEntity, 200, 45,
                45, 0.3, 2, 0.1, 3, 10,
                7000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            zombieLumberjackEntity.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombieLumberjackEntity), list);
        return zombieLumberjackEntity;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.life, 2);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.FOREST_SOUL.get(), 2));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.FOREST_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.FOREST_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.FOREST_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.FOREST_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.LUMBER_JACK_AXE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "LumberJack";
    }
}
