package fun.wraq.events.mob.chapter2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpiderSpawnController extends MobSpawnController {

    public static String mobName = "雨林蜘蛛";
    private static SpiderSpawnController instance;

    public static SpiderSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1122, 70, 131),
                    new Vec3(1118, 69, 160),
                    new Vec3(1116, 67, 186),
                    new Vec3(1117, 68, 211),
                    new Vec3(1115, 70, 231),
                    new Vec3(1114, 72, 259),
                    new Vec3(1112, 69, 285),
                    new Vec3(1112, 69, 304),
                    new Vec3(1223, 71, 149),
                    new Vec3(1201, 66, 174),
                    new Vec3(1197, 69, 197),
                    new Vec3(1191, 72, 220),
                    new Vec3(1183, 68, 242),
                    new Vec3(1179, 72, 266),
                    new Vec3(1174, 71, 290),
                    new Vec3(1150, 63, 323)
            );
            instance = new SpiderSpawnController(spawnPos, 1247, 355, 1092, 87, world, 32);
        }
        return instance;
    }

    public SpiderSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("雨林蜘蛛", CustomStyle.styleOfForest), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Spider spider = new Spider(EntityType.SPIDER, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfSpider;
        MobSpawn.setMobCustomName(spider, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(spider), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(spider, 120, 20, 20, 0.3, 2, 0.1, 3, 10, 1750, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            spider.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(spider), list);
        return spider;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.life, 2);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ForestSoul.get(), 0.8));
            add(new ItemAndRate(ModItems.SpiderSoul.get(), 0.1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.3125));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.ForestCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.ForestCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.ForestCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.ForestCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get(), 0.2));
            add(new ItemAndRate(C1LootItems.spiderBoots.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Spider";
    }
}
