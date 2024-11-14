package fun.wraq.events.mob.ore;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
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

import java.util.List;
import java.util.Random;

public class Ore3SpawnController extends MobSpawnController {

    public static String mobName = "被遗忘的粉钻矿工";
    private static Ore3SpawnController instance;

    public static Ore3SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2442, 118, 1696),
                    new Vec3(2420, 103, 1721),
                    new Vec3(2439, 142, 1723),
                    new Vec3(2396, 94, 1727),
                    new Vec3(2427, 185, 1733),
                    new Vec3(2447, 185, 1733),
                    new Vec3(2460, 185, 1733),
                    new Vec3(2459, 158, 1737),
                    new Vec3(2466, 170, 1750)
            );
            instance = new Ore3SpawnController(spawnPos, 2500, 190, 1780, 2350, 80, 1650, world, 168);
        }
        return instance;
    }

    public Ore3SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                               int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                               Level level, int averageLevel) {
        super(canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ,
                15, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSakura;

        MobSpawn.setMobCustomName(zombie, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 1500, 110, 110, 0.4,
                4, 0.25, 50, 20, 100 * Math.pow(10, 4), 0.35);

        zombie.setBaby(true);
        // 设置物品
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.NETHERITE_PICKAXE.getDefaultInstance());
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
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.stone, 3);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(ModItems.StoneElementPiece0.get(), 0.03),
                new ItemAndRate(Items.RAW_GOLD, 0.12),
                new ItemAndRate(Items.DIAMOND, 0.1),
                new ItemAndRate(Items.EMERALD, 0.1),
                new ItemAndRate(Items.REDSTONE, 0.25),
                new ItemAndRate(Items.LAPIS_LAZULI, 0.25),
                new ItemAndRate(OreItems.WRAQ_ORE_3_ITEM.get(), 0.01)
        );
    }
}
