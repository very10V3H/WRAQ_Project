package fun.wraq.events.mob.chapter2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkySkeletonSpawnController extends MobSpawnController {

    public static String mobName = "外来飞艇卫兵";
    private static SkySkeletonSpawnController instance;

    public static SkySkeletonSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(941, 288, -20),
                    new Vec3(930, 285, -14),
                    new Vec3(941, 282, -13),
                    new Vec3(952, 285, -14),
                    new Vec3(941, 286, -4),
                    new Vec3(941, 289, 9)
            );
            instance = new SkySkeletonSpawnController(spawnPos, spawnPos.size() * 3, 954, 293, 18, 925, 279, -29, 0, 40,
                    world, 2, 60);
        }
        return instance;
    }

    public SkySkeletonSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownY, int boundaryDownZ, double summonOffset, int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        super(Te.s("外来飞艇卫兵", CustomStyle.styleOfSky), canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, summonOffset, detectionRange, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfSky;
        MobSpawn.setMobCustomName(skeleton, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, 200, 45, 45, 0.3,
                3, 0.1, 3, 10, 8000, 0.3);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.CHAINMAIL_HELMET), new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_LEGGINGS), new ItemStack(Items.CHAINMAIL_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            itemStacks[i].enchant(Enchantments.UNBREAKING, 1);
            skeleton.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        skeleton.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
        skeleton.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(skeleton), list);
        // 直接送至背包
        MobSpawn.dropsDirectToInventory.put(MobSpawn.getMobOriginName(skeleton), true);
        return skeleton;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.wind, 2);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SKY_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.SKY_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.SKY_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.SKY_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.SKY_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.WIND_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.SKY_VEX_BOW.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.SKY_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "SkySkeleton";
    }
}
