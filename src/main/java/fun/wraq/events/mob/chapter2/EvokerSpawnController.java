package fun.wraq.events.mob.chapter2;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvokerSpawnController extends MobSpawnController {

    public static String mobName = "唤魔者";
    private static EvokerSpawnController instance;

    public static EvokerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1414, 70, -218),
                    new Vec3(1443, 67, -188),
                    new Vec3(1496, 68, -163),
                    new Vec3(1529, 69, -177),
                    new Vec3(1560, 67, -195),
                    new Vec3(1584, 66, -218),
                    new Vec3(1597, 70, -248),
                    new Vec3(1587, 69, -285),
                    new Vec3(1552, 69, -306),
                    new Vec3(1514, 72, -313),
                    new Vec3(1473, 69, -318),
                    new Vec3(1433, 69, -309)
            );
            instance = new EvokerSpawnController(spawnPos, spawnPos.size() * 2, 1649, -137, 1374, -381, world, 1, 56);
        }
        return instance;
    }

    public EvokerSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ,
                16, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Evoker evoker = new Evoker(EntityType.EVOKER, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMana;
        MobSpawn.setMobCustomName(evoker, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(evoker), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(evoker, 200, 40, 40, 0.3, 2, 0.1, 3, 10, 4000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            evoker.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(evoker), list);
        return evoker;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EvokerSoul.get(), 1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.ManaCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.ManaCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.ManaCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.ManaCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.evokerSceptre.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.evokerNewRune.get(), 0.001));
        }};
    }
}
