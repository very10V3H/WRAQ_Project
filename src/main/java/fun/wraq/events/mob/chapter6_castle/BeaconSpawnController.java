package fun.wraq.events.mob.chapter6_castle;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C6LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BeaconSpawnController extends MobSpawnController {

    public static String mobName = "烽火台哨卫";
    private static BeaconSpawnController instance;

    public static BeaconSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2413, 153, -1535),
                    new Vec3(2417, 153, -1545),
                    new Vec3(2424, 158, -1528),
                    new Vec3(2426, 169, -1538),
                    new Vec3(2434, 167, -1529),
                    new Vec3(2436, 168, -1518),
                    new Vec3(2439, 166, -1542),
                    new Vec3(2441, 182, -1527),
                    new Vec3(2452, 170, -1534),
                    new Vec3(2451, 168, -1517)
            );
            instance = new BeaconSpawnController(spawnPos, 2467, -1506, 2400, -1559, world, 180);
        }
        return instance;
    }

    public BeaconSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("烽火台哨卫", CustomStyle.styleOfPower), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2000, 160, 130, 0.4, 3, 0.25, 55, 20, 300 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Skeleton beacon = new Skeleton(EntityType.SKELETON, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfPower;
        MobSpawn.setMobCustomName(beacon, Te.s(mobName, style), xpLevel);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(beacon), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(beacon, getMobAttributes());
        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            beacon.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        beacon.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(beacon), list);
        return beacon;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 4);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BEACON_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.58));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.4));
            add(new ItemAndRate(C6LootItems.BEACON_LOOT_BOW.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Beacon";
    }
}
