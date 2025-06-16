package fun.wraq.series.overworld.sakura.bunker.mob;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class BunkerBlazeSpawnController extends MobSpawnController {

    public static String mobName = "熔心之火";
    public static Style style = CustomStyle.BUNKER_STYLE;
    private static BunkerBlazeSpawnController instance;

    public static BunkerBlazeSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(3841, -9, 1955),
                    new Vec3(3840, -3, 1984),
                    new Vec3(3809, -3, 2001),
                    new Vec3(3841, -3, 2001),
                    new Vec3(3873, -1, 2001),
                    new Vec3(3766, -3, 2032),
                    new Vec3(3841, -3, 2032),
                    new Vec3(3872, -3, 2031),
                    new Vec3(3766, -4, 2063),
                    new Vec3(3810, -3, 2063),
                    new Vec3(3840, -2, 2063)
            );
            instance = new BunkerBlazeSpawnController(spawnPos, 3938, 2095, 3718, 1889, world, 265);
        }
        return instance;
    }

    public BunkerBlazeSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Blaze mob = new Blaze(EntityType.BLAZE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                6500, 450, 450,
                0.4, 3, 0.5, 350, 25,
                2500 * Math.pow(10, 4), 0.4);
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.fire, 5);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(BunkerItems.BUNKER_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
                new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.5),
                new ItemAndRate(BunkerItems.BUNKER_HELMET_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_CHEST_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_LEGGINGS_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_BOOTS_0.get(), 0.00025)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "BunkerBlaze";
    }
}
