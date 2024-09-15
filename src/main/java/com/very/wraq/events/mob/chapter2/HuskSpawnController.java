package com.very.wraq.events.mob.chapter2;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C2LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HuskSpawnController extends MobSpawnController {

    public static String mobName = "脆弱的岩灵";
    private static HuskSpawnController instance;

    public static HuskSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(783, 67, 407),
                    new Vec3(786, 67, 390),
                    new Vec3(795, 67, 381),
                    new Vec3(782, 67, 380),
                    new Vec3(789, 68, 371),
                    new Vec3(805, 67, 374),
                    new Vec3(814, 67, 367),
                    new Vec3(799, 69, 360),
                    new Vec3(822, 66, 361),
                    new Vec3(811, 68, 352),
                    new Vec3(829, 67, 351),
                    new Vec3(855, 69, 330),
                    new Vec3(894, 69, 325),
                    new Vec3(905, 69, 323),
                    new Vec3(902, 68, 333),
                    new Vec3(1067, 69, 334),
                    new Vec3(1106, 67, 324),
                    new Vec3(1162, 67, 356),
                    new Vec3(1185, 67, 350),
                    new Vec3(1181, 65, 365),
                    new Vec3(1198, 65, 371),
                    new Vec3(1189, 65, 381),
                    new Vec3(1198, 65, 371),
                    new Vec3(1211, 66, 379),
                    new Vec3(1203, 67, 390),
                    new Vec3(1223, 68, 391),
                    new Vec3(1215, 68, 403),
                    new Vec3(1233, 67, 402),
                    new Vec3(1240, 65, 418),
                    new Vec3(1257, 63, 427)
            );
            instance = new HuskSpawnController(spawnPos, spawnPos.size() * 2, world, 2, 84);
        }
        return instance;
    }

    public HuskSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, 60, level, mobPlayerRate, averageLevel,
                List.of(new Boundary(new Vec3(933, 1000, 415), new Vec3(760, -100, 289)),
                        new Boundary(new Vec3(1230, 1000, 453), new Vec3(1034, -100, 270))));
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Husk husk = new Husk(EntityType.HUSK, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfHusk;
        MobSpawn.setMobCustomName(husk, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(husk), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(husk, 400, 800, 800, 0.35, 3, 0.2, 500, 15, 12000, 0.3);

        // 设置物品
/*        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            husk.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }*/
        husk.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(husk), list);
        return husk;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.stone, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.huskSoul.get(), 1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.4375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.huskSword.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.huskNewRune.get(), 0.001));
        }};
    }
}
