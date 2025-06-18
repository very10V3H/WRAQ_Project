package fun.wraq.series.overworld.cold.sc2.stone;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class StoneSpiderKingSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "石岸蜘蛛皇";
    public static final Style STYLE = CustomStyle.styleOfStone;
    public static final int XP_LEVEL = 300;

    public static StoneSpiderKingSpawnController instance;

    public static StoneSpiderKingSpawnController getInstance() {
        if (instance == null) {
            instance = new StoneSpiderKingSpawnController(Te.s(MOB_NAME, STYLE),
                    new Vec3(2182, 82, -3147),
                    new Vec3(916, 110, 2388),
                    new Vec3(2163, 69, -3167), XP_LEVEL, 48, Tick.min(3));
        }
        return instance;
    }

    public StoneSpiderKingSpawnController(Component name, Vec3 descriptionPos,
                                          Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                          int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(24000, 1100, 1100, 0.4, 3, 0.6, 750, 25, 20 * Math.pow(10, 8), 0.45);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.stone, 8);
    }

    @Override
    public void spawnMob(Level level) {
        Spider mob = new Spider(EntityType.SPIDER, level);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        mob.moveTo(new Vec3(2182, 82, -3147));
        level.addFreshEntity(mob);
        mobs.add(mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 100 == 99 && RandomUtils.nextDouble(0, 1) < 0.2) {
            if (RandomUtils.nextBoolean()) {
                StoneSpiderSpawnController.skill1(mob);
            } else {
                StoneSpiderSpawnController.skill2(mob);
            }
        }
        if (mob.tickCount % 20 == 0) {
            Compute.getNearPlayer(mob.level(), mob.position(), 16).forEach(player -> {
                Damage.causeManaDamageToPlayer(mob, player, player.getMaxHealth() * 0.15);
                ParticleProvider.createLineEffectParticle(mob.level(), mob, player, CustomStyle.styleOfStone);
            });
        }
        Compute.mobHealthRecover(mob, 0.02);
        super.handleMobTick(mob);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(NewAreaItems.STONE_SPIDER_PIECE.get(), 1),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_KNIFE_0.get(), 0.01),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_GEM_ATTACK_0.get(), 0.01),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_GEM_MANA_0.get(), 0.01),
                new ItemAndRate(ModItems.GOLD_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1),
                new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_1.get(), 0.1)
        );
    }
}
