package fun.wraq.series.overworld.cold.sc5.dragon;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Name;
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
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.ChatFormatting;
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
import java.util.Set;

public class IceDragonSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "极寒冰龙";
    public static final Style STYLE = CustomStyle.styleOfIce;
    public static final int XP_LEVEL = 330;
    public static final double MAX_HEALTH = 500 * Math.pow(10, 8);

    public static IceDragonSpawnController instance;

    public static IceDragonSpawnController getInstance() {
        if (instance == null) {
            instance = new IceDragonSpawnController(Te.s(MOB_NAME, STYLE),
                    new Vec3(2086, 63, -4219),
                    new Vec3(2236, 111, -4100),
                    new Vec3(2000, 40, -4271),
                    XP_LEVEL, 64, Tick.s(30));
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
        if (damageCount.getOrDefault(Name.get(player), 0d) < MAX_HEALTH * 0.1) {
            sendMSG(player, Te.s("至少需要造成极寒冰龙最大生命值的",
                    "15%伤害", ChatFormatting.AQUA, "才能获取奖励."));
            return;
        }
        if (Reason.getPlayerReasonValue(player) < 20) {
            sendMSG(player, Te.s("需要至少", "20理智", "才能获取奖励."));
            return;
        }
        if (Compute.getPlayerReputation(player) < 40) {
            sendMSG(player, Te.s("需要至少", "40声望", "才能获得奖励."));
            return;
        }
        if (!InventoryOperation.checkPlayerHasItem(player, SuperColdItems.SUPER_COLD_CRYSTAL.get(), 1)) {
            sendMSG(player, Te.s("需要一个", SuperColdItems.SUPER_COLD_CRYSTAL.get(), "才能获得奖励."));
            return;
        }
        Reason.addOrCostPlayerReasonValue(player, -20);
        Compute.addOrCostReputation(player, -40);
        InventoryOperation.removeItem(player, SuperColdItems.SUPER_COLD_CRYSTAL.get(), 1);
        InventoryOperation.giveItemStackWithMSG(player, SuperColdItems.SUPER_COLD_DRAGON_LOTTERY.get());
    }

    public static MobAttributes getAttributes() {
        return new MobAttributes(2000, 1500, 1500, 1, 10, 0.99, 1000, 0, MAX_HEALTH, 0.45);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return getAttributes();
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.ice, 10);
    }

    @Override
    public void spawnMob(Level level) {
        EntityIceDragon mob = new EntityIceDragon(IafEntityRegistry.ICE_DRAGON.get(), level);
        mob.setAgeInDays(49);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        mob.moveTo(new Vec3(2086, 63, -4219));
        level.addFreshEntity(mob);
        mobs.add(mob);
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 80 == 15) {
            rangeEffectAttack(mob);
        }
        if (mob.tickCount % 100 == 88 || mob.tickCount % 100 == 90 || mob.tickCount % 100 == 92) {
            commonAttack(mob);
        }
        super.handleMobTick(mob);
        players.forEach(player -> {
            Compute.sendEffectLastTime(player, SuperColdItems.SUPER_COLD_STONE.get(),
                    (int) (damageCount.getOrDefault(Name.get(player), 0d) * 100 / MAX_HEALTH), true);
        });
    }

    @Override
    public void clear() {
        players.forEach(player -> {
            Compute.removeEffectLastTime(player, SuperColdItems.SUPER_COLD_STONE.get());
        });
        super.clear();
    }

    @Override
    public void onEndBroad() {
        Compute.formatBroad(Te.s("极寒冰龙", CustomStyle.styleOfIce),
                Te.s(players.size() + "名玩家成功通过了极寒冰龙挑战!"));
        players.stream().sorted(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (damageCount.getOrDefault(Name.get(o1), 0d)
                        - damageCount.getOrDefault(Name.get(o2), 0d));
            }
        }).forEach(player -> {
            Compute.broad(Te.s(player, " ".repeat(8), String.format("%.1f%%",
                    damageCount.getOrDefault(Name.get(player), 0d)
                            * 100 / MAX_HEALTH), ChatFormatting.RED), 8);
        });
    }

    public static void skill1(Mob mob, Set<Player> players) {
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
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player, 10);
        });
        PersistentRangeEffect.addEffect(player, pos, 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangePlayer().forEach(eachPlayer -> {
                    MonsterAttackEvent.causeCommonAttackToPlayer(mob, eachPlayer, 20);
                    SpecialEffectOnPlayer.addImprisonEffect(eachPlayer, 10);
                    Compute.createIceParticle(player);
                });
                ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), pos,
                        8, 100, ParticleTypes.SNOWFLAKE);
            }
        }, 10, Tick.s(4));
    }

    // 每2s一次，随机选定玩家
    public void rangeEffectAttack(Mob mob) {
        skill1(mob, players);
    }

    public static void skill2(Mob mob, Set<Player> players) {
        if (players.isEmpty()) {
            return;
        }
        players.stream().min(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.distanceTo(mob) - o2.distanceTo(mob));
            }
        }).ifPresent(player -> {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player, 10);
            ParticleProvider.createIafLineParticle(player.level(), mob, player, EnumParticles.DragonIce);
            Compute.createIceParticle(player);
            SpecialEffectOnPlayer.addImprisonEffect(player, Tick.s(3));
        });
    }

    public void commonAttack(Mob mob) {
        skill2(mob, players);
    }

    @Override
    public double modifyMobWithstandDamage(Mob mob, Player player) {
        return mob.distanceTo(player) > 32 ? 0 : 1;
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of();
    }

    public static List<Component> getDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" · ", STYLE, "若距离其超过32格，则无法对其造成伤害."));
        components.add(Te.s(" · ", STYLE, "技能1 每隔4s，随机选定一名玩家，在其位置制造极寒领域."));
        components.add(Te.s(" ".repeat(8), "领域持续4s，每0.5s对范围内所有玩家造成高额伤害."));
        components.add(Te.s(" · ", STYLE, "技能2 每隔5s，选择最近的一名玩家，对其造成三段高额伤害."));
        components.add(Te.s(" · ", STYLE, "技能3 随机触发，对目标玩家进行吐息，造成多段持续伤害."));
        components.add(Te.s(" · ", STYLE, "机制1 其将无视来自非普通攻击的真实伤害."));
        components.add(Te.s(" · ", STYLE, "机制2 其将无视来自与其距离超过32格玩家的伤害."));
        components.add(Te.s(" * ", CustomStyle.styleOfGold, "获得奖励需要："));
        components.add(Te.s(" · ", CustomStyle.styleOfGold, "至少对其造成10%最大生命值伤害."));
        components.add(Te.s(" · ", CustomStyle.styleOfGold, "在战斗中，会在hud列表以极寒晶石图标显示造成百分比."));
        components.add(Te.s(" · ", CustomStyle.styleOfGold, "消耗20理智.", CustomStyle.styleOfFlexible));
        components.add(Te.s(" · ", CustomStyle.styleOfGold, "消耗40声望.", CustomStyle.styleOfGold));
        components.add(Te.s(" · ", CustomStyle.styleOfGold,
                "需要一个", SuperColdItems.SUPER_COLD_CRYSTAL.get()));
        return components;
    }

    @Override
    public List<Component> getSpecialDescription() {
        return getDescription();
    }
}
