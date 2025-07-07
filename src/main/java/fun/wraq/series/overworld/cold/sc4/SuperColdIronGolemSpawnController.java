package fun.wraq.series.overworld.cold.sc4;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.golem.IceGolem;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class SuperColdIronGolemSpawnController extends MobSpawnController {

    public static String mobName = "寒铁傀儡";
    public static Style style = CustomStyle.styleOfIce;
    private static SuperColdIronGolemSpawnController instance;

    public static SuperColdIronGolemSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2590, 137, -4046),
                    new Vec3(2572, 148, -4021),
                    new Vec3(2624, 148, -4015),
                    new Vec3(2519, 160, -3985),
                    new Vec3(2558, 153, -3998),
                    new Vec3(2600, 151, -3996),
                    new Vec3(2512, 161, -3961),
                    new Vec3(2551, 159, -3966),
                    new Vec3(2597, 157, -3971),
                    new Vec3(2636, 149, -3963)
            );
            instance = new SuperColdIronGolemSpawnController(spawnPos, 2700, -3800, 2400, -4100, world, 315);
        }
        return instance;
    }

    public SuperColdIronGolemSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                             int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(30000, 1100, 1100, 0.4, 3, 0.6, 800, 25, 20000 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        IceGolem mob = new IceGolem(ModEntityType.ICE_GOLEM.get(), this.level);
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
        if (mob.getMaxHealth() < 1000) {
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getMobAttributes().maxHealth);
            mob.heal(mob.getMaxHealth());
        }
        if (mob.tickCount % 40 == (mobList.indexOf(mob) % 40)) {
            commonAttack(mob);
        }
        Compute.mobHealthRecover(mob, 0.02);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    public static void onDead(Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)
                && mob.getTarget() != null && mob.getTarget() instanceof Player player) {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player);
        }
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(SuperColdItems.COLD_IRON_GOLEM_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "SuperColdSnowGolem";
    }

    public void commonAttack(Mob mob) {
        Player player = Compute.getNearestPlayer(mob, 16);
        if (player != null) {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player);
            ParticleProvider.createIafLineParticle(mob.level(), mob, player, EnumParticles.DragonIce);
        }
    }
}
