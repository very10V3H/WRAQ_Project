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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkyVexSpawnController extends MobSpawnController {

    public static String mobName = "天空城的不速之客";
    private static SkyVexSpawnController instance;

    public static SkyVexSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(941, 292, -34)
            );
            instance = new SkyVexSpawnController(spawnPos, spawnPos.size() * 10, 967, 320, -2, 909, 265, -53, 20, 60, world, 10, 72);
        }
        return instance;
    }

    public SkyVexSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownY, int boundaryDownZ, double summonOffset, int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, summonOffset, detectionRange, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Vex vex = new Vex(EntityType.VEX, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfSky;
        MobSpawn.setMobCustomName(vex, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(vex), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(vex, 200, 5, 5, 0.35, 3, 0.2, 5, 15, 6000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            vex.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        vex.setItemInHand(InteractionHand.MAIN_HAND, Items.DIAMOND_SWORD.getDefaultInstance());
        vex.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(vex), list);
        // 直接送至背包
        MobSpawn.dropsDirectToInventory.put(MobSpawn.getMobOriginName(vex), true);
        return vex;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.wind, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SkySoul.get(), 0.8));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.SkyCrest0.get(), 0.02));
            add(new ItemAndRate(ModItems.SkyCrest1.get(), 0.005));
            add(new ItemAndRate(ModItems.SkyCrest2.get(), 0.001));
            add(new ItemAndRate(ModItems.SkyCrest3.get(), 0.0002));
            add(new ItemAndRate(ModItems.WindElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.skyVexBow.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.skyNewRune.get(), 0.001));
        }};
    }
}
