package fun.wraq.events.mob.chapter1;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForestZombieSpawnController extends MobSpawnController {

    public static String mobName = "森林僵尸";
    private static ForestZombieSpawnController instance;

    public static ForestZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(858, 80, 243),
                    new Vec3(882, 78, 240),
                    new Vec3(888, 80, 226),
                    new Vec3(908, 81, 202),
                    new Vec3(913, 80, 183),
                    new Vec3(946, 79, 165),
                    new Vec3(948, 80, 140),
                    new Vec3(932, 79, 171)
            );
            instance = new ForestZombieSpawnController(spawnPos, 975, 260, 824, 81, world, 12);
        }
        return instance;
    }

    public ForestZombieSpawnController(List<Vec3> canSpawnPos,
                                       int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ,
                                       Level level, int averageLevel) {
        super(Te.s("森林僵尸", CustomStyle.styleOfForest), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfForest;

        MobSpawn.setMobCustomName(zombie, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 60, 5, 5, 0.2, 1, 0, 0, 0, 300, 0.2);

        // 设置物品
        MobSpawn.setStainArmorOnMob(zombie, style);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_AXE.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.life, 1);
        });
    }

    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.FOREST_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1.5));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
            add(new ItemAndRate(ModItems.FOREST_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.FOREST_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.FOREST_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.FOREST_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.FOREST_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C1LootItems.FOREST_ZOMBIE_AXE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "ForestZombie";
    }
}
