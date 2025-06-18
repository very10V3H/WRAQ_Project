package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
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

public class PlainZombieSpawnController extends MobSpawnController {

    public static String mobName = "平原僵尸";
    private static PlainZombieSpawnController instance;

    public static PlainZombieSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(688, 80, 262),
                    new Vec3(703, 81, 275),
                    new Vec3(714, 79, 257),
                    new Vec3(729, 80, 272),
                    new Vec3(740, 78, 254),
                    new Vec3(750, 79, 270),
                    new Vec3(761, 78, 256),
                    new Vec3(772, 78, 2),
                    new Vec3(787, 77, 279),
                    new Vec3(823, 79, 265)
            );
            instance = new PlainZombieSpawnController(spawnPos, 850, 290, 666, 235, world, 4);
        }
        return instance;
    }

    public PlainZombieSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("平原僵尸", CustomStyle.styleOfPlain), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(20, 0, 0, 0.2, 1, 0, 0, 0, 100, 0.2);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfPlain;
        MobSpawn.setMobCustomName(zombie, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(zombie, style);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.WOODEN_HOE.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.life, 1);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PLAIN_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.COPPER_COIN.get(), 1.5));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
            add(new ItemAndRate(ModItems.PLAIN_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.PLAIN_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.PLAIN_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.PLAIN_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.PLAIN_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C1LootItems.PLAIN_ZOMBIE_HOE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "PlainZombie";
    }
}
