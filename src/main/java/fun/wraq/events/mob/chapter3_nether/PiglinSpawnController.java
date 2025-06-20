package fun.wraq.events.mob.chapter3_nether;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C3LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PiglinSpawnController extends MobSpawnController {

    public static String mobName = "猪灵";
    private static PiglinSpawnController instance;

    public static PiglinSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(621, 79, -642),
                    new Vec3(607, 78, -630),
                    new Vec3(621, 79, -626),
                    new Vec3(594, 78, -624),
                    new Vec3(609, 79, -613),
                    new Vec3(591, 78, -611),
                    new Vec3(597, 79, -599),
                    new Vec3(609, 80, -595)
            );
            instance = new PiglinSpawnController(spawnPos, 633, -588, 574, -659, world, 80);
        }
        return instance;
    }

    public PiglinSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("猪灵", CustomStyle.styleOfGold), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 50, 50, 0.35, 3, 0.2, 5, 15, 9000, 0.25);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Piglin piglin = new Piglin(EntityType.PIGLIN, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfGold;
        MobSpawn.setMobCustomName(piglin, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(piglin), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(piglin, getMobAttributes());
        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.GOLDEN_CHESTPLATE),
                new ItemStack(Items.GOLDEN_LEGGINGS), new ItemStack(Items.GOLDEN_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            piglin.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        piglin.setItemInHand(InteractionHand.MAIN_HAND, Items.GOLDEN_SWORD.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(piglin), list);
        return piglin;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 4);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.RUBY.get(), 1));
            add(new ItemAndRate(ModItems.NETHER_QUARTZ.get(), 0.33));
            add(new ItemAndRate(ModItems.PIGLIN_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.5));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.04));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.4));
            add(new ItemAndRate(ModItems.NETHER_PEARL.get(), 0.01));
            add(new ItemAndRate(C3LootItems.PIGLIN_SWORD.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.NETHER_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Piglin";
    }
}
