package fun.wraq.events.mob.ore;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class Ore3SpawnController extends MobSpawnController {

    public static String mobName = "被遗忘的粉钻矿工";
    private static Ore3SpawnController instance;

    public static Ore3SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2442, 118, 1696),
                    new Vec3(2420, 103, 1721),
                    new Vec3(2439, 142, 1723),
                    new Vec3(2396, 94, 1727),
                    new Vec3(2427, 185, 1733),
                    new Vec3(2447, 185, 1733),
                    new Vec3(2460, 185, 1733),
                    new Vec3(2459, 158, 1737),
                    new Vec3(2466, 170, 1750)
            );
            instance = new Ore3SpawnController(spawnPos, 2500, 190, 1780, 2350, 80, 1650, world, 168);
        }
        return instance;
    }

    public Ore3SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                               int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                               Level level, int averageLevel) {
        super(Te.s("被遗忘的粉钻矿工", CustomStyle.styleOfSakura), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ,
                15, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(800, 100, 100, 0.4, 3, 0.15, 30, 20, 30 * Math.pow(10, 4), 0.25);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSakura;
        MobSpawn.setMobCustomName(zombie, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, getMobAttributes());
        zombie.setBaby(true);
        // 设置物品
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Items.NETHERITE_PICKAXE.getDefaultInstance());
        MobSpawn.setStainArmorOnMob(zombie, style);
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombie), list);
        return zombie;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.stone, 3);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_0.get(), 0.03),
                new ItemAndRate(Items.RAW_GOLD, 0.12),
                new ItemAndRate(Items.DIAMOND, 0.1),
                new ItemAndRate(Items.EMERALD, 0.1),
                new ItemAndRate(Items.REDSTONE, 0.25),
                new ItemAndRate(Items.LAPIS_LAZULI, 0.25),
                new ItemAndRate(OreItems.WRAQ_ORE_3_ITEM.get(), 0.01)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "Ore3";
    }
}
