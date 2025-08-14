package fun.wraq.events.mob.chapter5.origin;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PillagerSpawnController extends MobSpawnController {

    public static String mobName = "海盗";
    private static PillagerSpawnController instance;

    public static PillagerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1038, 67, 1083),
                    new Vec3(1042, 67, 1089),
                    new Vec3(1038, 67, 1097),
                    new Vec3(1039, 64, 1083),
                    new Vec3(1039, 64, 1094)
            );
            instance = new PillagerSpawnController(spawnPos, 1205, 1179, 1014, 1048, world, 108);
        }
        return instance;
    }

    public PillagerSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("海盗", CustomStyle.styleOfShip), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(600, 65, 65, 0.4, 3, 0.25, 10, 20, 60000, 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Pillager pillager = new Pillager(EntityType.PILLAGER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfShip;
        MobSpawn.setMobCustomName(pillager, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(pillager), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(pillager, getMobAttributes());
        // 设置物品
        pillager.setItemInHand(InteractionHand.MAIN_HAND, Items.CROSSBOW.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(pillager), list);
        return pillager;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.water, 3);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SHIP_PIECE.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.615));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.03));
            add(new ItemAndRate(ModItems.WATER_ELEMENT_PIECE_0.get(), 0.3));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Pillager";
    }
}
