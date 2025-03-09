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
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class DivineGolemSpawnController extends MobSpawnController {

    public static String mobName = "圣光傀儡";
    public static Style style = CustomStyle.DIVINE_STYLE;
    private static DivineGolemSpawnController instance;

    public static DivineGolemSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2293, 86, 792),
                    new Vec3(2312, 88, 786),
                    new Vec3(2300, 88, 777),
                    new Vec3(2319, 88, 775),
                    new Vec3(2317, 89, 765),
                    new Vec3(2340, 88, 750),
                    new Vec3(2318, 88, 738),
                    new Vec3(2344, 86, 737)
            );
            instance = new DivineGolemSpawnController(spawnPos, 2368, 804, 2270, 708, world, 280);
        }
        return instance;
    }

    public DivineGolemSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        IronGolem mob = new IronGolem(EntityType.IRON_GOLEM, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                10000, 650, 650,
                0.4, 3, 0.6, 450, 25,
                6000 * Math.pow(10, 4), 0.4);
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
                new ItemAndRate(DivineIslandItems.DIVINE_SOUL.get(), 0.1),
                new ItemAndRate(DivineIslandItems.DIVINE_GOLEM_SOUL.get(), 0.2),
                new ItemAndRate(ModItems.silverCoin.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
                new ItemAndRate(Element.getPiece0ItemMap().get(DivineUtils.currentDayElement), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "DivineGolem";
    }
}
