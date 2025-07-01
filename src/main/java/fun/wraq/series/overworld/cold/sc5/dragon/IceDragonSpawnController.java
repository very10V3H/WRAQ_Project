package fun.wraq.series.overworld.cold.sc5.dragon;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IceDragonSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "极寒冰龙";
    public static final Style STYLE = CustomStyle.styleOfIce;
    public static final int XP_LEVEL = 330;

    public static IceDragonSpawnController instance;

    public static IceDragonSpawnController getInstance() {
        if (instance == null) {
            instance = new IceDragonSpawnController(Te.s(MOB_NAME, STYLE),
                    new Vec3(2086, 63, -4219),
                    new Vec3(2236, 111, -4100),
                    new Vec3(2000, 40, -4271),
                    XP_LEVEL, 64, Tick.s(5));
        }
        return instance;
    }

    public IceDragonSpawnController(Component name, Vec3 descriptionPos,
                                    Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                    int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {

    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(20000, 1500, 1500, 1, 3, 0.99, 1000, 0, 1000 * Math.pow(10, 8), 0.45);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.ice, 10);
    }

    @Override
    public void spawnMob(Level level) {
        EntityIceDragon mob  = new EntityIceDragon(IafEntityRegistry.ICE_DRAGON.get(), level);
        mob.setAgeInDays(50);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        mob.moveTo(new Vec3(2086, 63, -4219));
        level.addFreshEntity(mob);
        mobs.add(mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 40 == 15) {
            rangeEffectAttack(mob);
        }
        if (mob.tickCount % 100 == 88 || mob.tickCount % 100 == 90 || mob.tickCount % 100 == 92) {
            commonAttack(mob);
        }
        super.handleMobTick(mob);
    }

    // 每2s一次，随机选定玩家
    public void rangeEffectAttack(Mob mob) {
        if (players.isEmpty()) {
            return;
        }
        List<Player> playerList = new ArrayList<>(players);
        Player player = playerList.get(RandomUtils.nextInt(0, playerList.size()));
        MySound.soundToNearPlayer(mob.level(), mob.position(), SoundEvents.SNOW_BREAK);
        Vec3 pos = player.position();
        ParticleProvider.createBallDisperseParticle(ParticleTypes.SNOWFLAKE,
                (ServerLevel) player.level(), pos, 1, 40);
        Vec3 startPos = mob.getEyePosition();
        ParticleProvider.createIafLineParticle(player.level(),
                (int) pos.distanceTo(startPos) * 5, startPos, pos, EnumParticles.DragonIce);
        Compute.getNearPlayer(mob.level(), pos, 8).forEach(eachPlayer -> {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player, 1);
        });
        PersistentRangeEffect.addEffect(player, pos, 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangePlayer().forEach(eachPlayer -> {
                    MonsterAttackEvent.causeCommonAttackToPlayer(mob, player, 1);
                });
                ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), pos,
                        8, 100, ParticleTypes.SNOWFLAKE);
            }
        }, 20, Tick.s(4));
    }

    public void commonAttack(Mob mob) {
        if (players.isEmpty()) {
            return;
        }
        players.stream().min(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.distanceTo(mob) - o2.distanceTo(mob));
            }
        }).ifPresent(player -> {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player, 1);
            ParticleProvider.createIafLineParticle(player.level(), mob, player, EnumParticles.DragonIce);
            Compute.createIceParticle(player);
            SpecialEffectOnPlayer.addImprisonEffect(player, Tick.s(3));
        });
    }

    @Override
    public double modifyMobWithstandDamage(Mob mob, Player player) {
        return mob.distanceTo(player) > 32 ? 0 : 1;
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of();
    }
}
