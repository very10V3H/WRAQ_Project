package fun.wraq.series.overworld.newarea.stone;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class StoneSpiderSpawnController extends MobSpawnController {

    public static String mobName = "石岸蜘蛛";
    public static Style style = CustomStyle.styleOfStone;
    private static StoneSpiderSpawnController instance;

    public static StoneSpiderSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(872, 67, 2357),
                    new Vec3(866, 66, 2380),
                    new Vec3(875, 71, 2370),
                    new Vec3(892, 96, 2368),
                    new Vec3(912, 102, 2387),
                    new Vec3(920, 102, 2393),
                    new Vec3(931, 101, 2392),
                    new Vec3(935, 102, 2408)
            );
            instance = new StoneSpiderSpawnController(spawnPos, 948, 2418, 846, 2332, world, 300);
        }
        return instance;
    }

    public StoneSpiderSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Spider mob = new Spider(EntityType.SPIDER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                9000, 600, 600,
                0.4, 3, 0.6, 400, 25,
                5000 * Math.pow(10, 4), 0.4);
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.stone, 8);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(NewAreaItems.STONE_SPIDER_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.silverCoin.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
                new ItemAndRate(ModItems.StoneElementPiece0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "StoneSpider";
    }
}
