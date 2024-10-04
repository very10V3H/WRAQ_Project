package fun.wraq.events.mob.chapter5;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C5LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PillagerSpawnController extends MobSpawnController {

    public static String mobName = "海盗";
    private static PillagerSpawnController instance;

    public static PillagerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1038, 67, 1083),
                    new Vec3(1042, 67, 1089),
                    new Vec3(1038, 67, 1097),
                    new Vec3(1039, 64, 1083),
                    new Vec3(1039, 64, 1094),
                    new Vec3(1176, 70, 1140),
                    new Vec3(1173, 67, 1149),
                    new Vec3(1178, 67, 1153),
                    new Vec3(1175, 68, 1160),
                    new Vec3(1176, 64, 1155),
                    new Vec3(1176, 63, 1147)
            );
            instance = new PillagerSpawnController(spawnPos, spawnPos.size() * 4, 1205, 1179, 1014, 1048, world, 4, 108);
        }
        return instance;
    }

    public PillagerSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Pillager pillager = new Pillager(EntityType.PILLAGER, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfShip;
        MobSpawn.setMobCustomName(pillager, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(pillager), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(pillager, 600, 65, 65, 0.4, 4, 0.25, 10, 20, 60000, 0.35);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            pillager.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        pillager.setItemInHand(InteractionHand.MAIN_HAND, Items.CROSSBOW.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(pillager), list);
        return pillager;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.water, 3);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ShipPiece.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.615));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.03));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get(), 0.3));
            add(new ItemAndRate(C5LootItems.pillagerBow.get(), 0.005));
        }};
    }
}
