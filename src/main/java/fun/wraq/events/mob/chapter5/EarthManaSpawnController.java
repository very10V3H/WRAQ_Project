package fun.wraq.events.mob.chapter5;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C5LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class EarthManaSpawnController extends MobSpawnController {

    public static String mobName = "地蕴蓝灵";
    private static EarthManaSpawnController instance;

    public static EarthManaSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2397, 168, 1633),
                    new Vec3(2394, 164, 1610),
                    new Vec3(2413, 176, 1599),
                    new Vec3(2436, 173, 1583),
                    new Vec3(2408, 157, 1575),
                    new Vec3(2433, 151, 1552),
                    new Vec3(2448, 158, 1543),
                    new Vec3(2400, 129, 1509),
                    new Vec3(2423, 141, 1527),
                    new Vec3(2449, 149, 1515),
                    new Vec3(2468, 158, 1501)
            );
            instance = new EarthManaSpawnController(spawnPos, 2488, 1638, 2363, 1456, world, 124);
        }
        return instance;
    }

    public EarthManaSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("地蕴蓝灵", CustomStyle.styleOfJacaranda), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfJacaranda;
        MobSpawn.setMobCustomName(zombie, Component.literal("地蕴蓝灵").withStyle(style), xpLevel);
        zombie.setBaby(true);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 800, 70, 70, 0.4, 4, 0.25, 10, 20, 180000, 0.35);

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

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 3);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EarthManaSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.615));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.03));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.3));
            add(new ItemAndRate(C5LootItems.earthManaSceptre.get(), 0.005));
        }};
    }
}
