package fun.wraq.series.overworld.cold.sc4;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class SuperColdSnowGolemSpawnController extends MobSpawnController {

    public static String mobName = "吹雪傀儡";
    public static Style style = CustomStyle.styleOfIce;
    private static SuperColdSnowGolemSpawnController instance;

    public static SuperColdSnowGolemSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2630, 128, -4087),
                    new Vec3(2593, 132, -4064),
                    new Vec3(2678, 127, -4064),
                    new Vec3(2647, 130, -4047),
                    new Vec3(2670, 135, -4017),
                    new Vec3(2707, 123, -4016),
                    new Vec3(2640, 145, -3988),
                    new Vec3(2676, 143, -3971),
                    new Vec3(2718, 133, -3979),
                    new Vec3(2704, 142, -3948)
            );
            instance = new SuperColdSnowGolemSpawnController(spawnPos, 2800, -3900, 2600, -4150, world, 315);
        }
        return instance;
    }

    public SuperColdSnowGolemSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                             int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(20000, 1000, 1000, 0.4, 3, 0.6, 800, 25, 17500 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SnowGolem mob = new SnowGolem(EntityType.SNOW_GOLEM, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        Compute.mobHealthRecover(mob, 0.02);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(SuperColdItems.BLIZZARD_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "SuperColdSnowGolem";
    }
}
