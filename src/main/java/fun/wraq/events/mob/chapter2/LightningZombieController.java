package fun.wraq.events.mob.chapter2;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
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
            instance = new LightningZombieController(spawnPos, 1790, 1312, 1694, 1131, world, 92);
        }
        return instance;
    }

    public LightningZombieController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("雷光灯塔驻卫", CustomStyle.styleOfLightning), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(450, 55, 55, 0.35, 3, 0.2, 5, 15, 20000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfLightning;
        MobSpawn.setMobCustomName(zombie, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, getMobAttributes());
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
    public Element.Unit getElement() {
        return new Element.Unit(Element.lightning, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LIGHTNING_SOUL.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.4375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.LIGHTNING_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(NewRuneItems.LIGHTNING_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "LightningZombie";
    }
}
