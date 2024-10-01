package fun.wraq.events.mob.chapter2;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
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

public class LightningZombieController extends MobSpawnController {

    public static String mobName = "雷光灯塔驻卫";
    private static LightningZombieController instance;

    public static LightningZombieController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1719, 63, 1162),
                    new Vec3(1734, 64, 1175),
                    new Vec3(1718, 66, 1192),
                    new Vec3(1748, 66, 1188),
                    new Vec3(1727, 67, 1211),
                    new Vec3(1765, 68, 1214),
                    new Vec3(1715, 67, 1226),
                    new Vec3(1729, 68, 1239),
                    new Vec3(1757, 69, 1248),
                    new Vec3(1723, 69, 1255),
                    new Vec3(1760, 69, 1263),
                    new Vec3(1735, 67, 1275),
                    new Vec3(1717, 66, 1288)
            );
            instance = new LightningZombieController(spawnPos, spawnPos.size() * 4, 1790, 1312, 1694, 1131, world, 4, 92);
        }
        return instance;
    }

    public LightningZombieController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfLightning;
        MobSpawn.setMobCustomName(zombie, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 450, 55, 55, 0.35, 3, 0.2, 5, 15, 20000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.IRON_HELMET), new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_LEGGINGS), new ItemStack(Items.IRON_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            zombie.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_SWORD.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.lightning, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LightningSoul.get(), 1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.4375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.LightningElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.lightningZombieHelmet.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.lightningNewRune.get(), 0.001));
        }};
    }
}
