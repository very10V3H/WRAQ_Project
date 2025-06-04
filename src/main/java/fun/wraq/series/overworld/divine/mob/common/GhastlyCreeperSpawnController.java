package fun.wraq.series.overworld.divine.mob.common;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class GhastlyCreeperSpawnController extends MobSpawnController {

    public static String mobName = "瑕光魔物";
    public static Style style = CustomStyle.GHASTLY_STYLE;
    private static GhastlyCreeperSpawnController instance;

    public static GhastlyCreeperSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2377, 83, 732),
                    new Vec3(2393, 69, 712),
                    new Vec3(2406, 69, 697),
                    new Vec3(2415, 68, 690),
                    new Vec3(2425, 68, 683),
                    new Vec3(2390, 74, 743),
                    new Vec3(2399, 69, 725),
                    new Vec3(2411, 68, 714),
                    new Vec3(2421, 67, 704),
                    new Vec3(2437, 67, 694)
            );
            instance = new GhastlyCreeperSpawnController(spawnPos, 2480, 800, 2300, 600, world, 285);
        }
        return instance;
    }

    public GhastlyCreeperSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                         int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Creeper mob = new Creeper(EntityType.CREEPER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                11000, 700, 700,
                0.4, 3, 0.6, 500, 25,
                7000 * Math.pow(10, 4), 0.6);
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            DivineUtils.handleMobTick(mob);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.GHASTLY_NUGGET.get(), 0.08),
                new ItemAndRate(DivineIslandItems.GHASTLY_GUN_POWDER.get(), 0.2),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.07),
                new ItemAndRate(Element.getPiece0ItemMap().get(DivineUtils.currentDayElement), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "GhastlyCreeper";
    }
}
