package fun.wraq.series.overworld.cold.sc3.fir;

import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class FirElfSpawnController extends MobSpawnController {

    public static String mobName = "冷杉精灵";
    public static Style style = CustomStyle.styleOfIce;
    private static FirElfSpawnController instance;

    public static FirElfSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2663, 143, -3814),
                    new Vec3(2690, 138, -3818),
                    new Vec3(2711, 135, -3818),
                    new Vec3(2674, 141, -3792),
                    new Vec3(2699, 137, -3789),
                    new Vec3(2721, 134, -3799),
                    new Vec3(2657, 141, -3774),
                    new Vec3(2668, 141, -3760),
                    new Vec3(2698, 139, -3757),
                    new Vec3(2723, 139, -3776)
            );
            instance = new FirElfSpawnController(spawnPos, 2757, -3742, 2636, -3842, world, 310);
        }
        return instance;
    }

    public FirElfSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(18000, 950, 950, 0.4, 3, 0.6, 700, 25, 12500 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        EntityPixie mob = new EntityPixie(IafEntityRegistry.PIXIE.get(), this.level);
        mob.setColor(2);
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
        if (mob.tickCount % 50 == 0) {
            Player player = Compute.getNearestPlayer(mob, 16);
            if (player != null) {
                ShulkerBullet shulkerBullet = new ShulkerBullet(mob.level(), mob, player, mob.getDirection().getAxis());
                shulkerBullet.shootFromRotation(mob, mob.getXRot(), mob.getYRot(), 1, 1.5f, 1);
                mob.level().addFreshEntity(shulkerBullet);
            }
        }
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(SuperColdItems.FIR_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "FirElf";
    }
}
