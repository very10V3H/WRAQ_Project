package fun.wraq.process.system.teamInstance.instances.spring;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SpringSnakeInstance extends NewTeamInstance {

    public static final Style style = CustomStyle.styleOfSpring;
    List<Vec3> offset = List.of(
            new Vec3(0, 0, 0),
            new Vec3(2, 0, 0),
            new Vec3(0, 0, 2),
            new Vec3(-2, 0, 0),
            new Vec3(0, 0, -2)
    );
    public static final String mobName = "金蛇次生体";
    public static ServerBossEvent bossEvent = new ServerBossEvent(Te.s(mobName, style),
            BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
    public static final Vec3 summonPos = new Vec3(2635.5, 192, 1724.5);

    public static SpringSnakeInstance instance;

    public static SpringSnakeInstance getInstance() {
        if (instance == null) {
            instance = new SpringSnakeInstance(false, new Vec3(2635.5, 192, 1724.5), Te.s(mobName, style),
                    Te.s("旧世魔王居所", CustomStyle.styleOfDemon), 6, 200, 1, 4, Tick.min(5),
                    Level.OVERWORLD, new Vec2(90, 0), 10);
        }
        return instance;
    }

    public SpringSnakeInstance(boolean inChallenging, Vec3 prepareCenterPos, MutableComponent description,
                               MutableComponent regionDescription, double prepareDetectRange, int levelRequire,
                               int minPlayerNum, int maxPlayerNum, int maxChallengeTime,
                               ResourceKey<Level> dimension, Vec2 rot, int reasonCost) {
        super(inChallenging, prepareCenterPos, description, regionDescription, prepareDetectRange, levelRequire,
                minPlayerNum, maxPlayerNum, maxChallengeTime, dimension, rot, reasonCost);
    }

    @Override
    public void initMobList(Level level) {
        for (int i = 0; i < 5; i++) {
            Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
            double maxHealth = 750 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, Te.s(mobName, style),
                    200, 2800, 240, 240, 0.4, 5,
                    0.4, 70, 25,
                    maxHealth, 0.45);
            MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfDemon);
            zombie.setItemSlot(EquipmentSlot.HEAD, Items.GOLDEN_HELMET.getDefaultInstance());
            zombie.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_HOE));
            zombie.setBaby(true);
            zombie.moveTo(summonPos.add(offset.get(i)));
            mobList.add(new ConditionSummonMob(0, zombie, summonPos.add(offset.get(i)), 32));
        }
    }

    @Override
    public void handleTick(Level level) {
        if (!mobList.isEmpty()) {
            double maxHealth = mobList
                    .stream().mapToDouble(conditionSummonMob -> conditionSummonMob.mob().getMaxHealth())
                    .sum();
            double currentHealth = mobList
                    .stream().mapToDouble(conditionSummonMob -> conditionSummonMob.mob().getHealth())
                    .sum();
            bossEvent.setProgress((float) (currentHealth / maxHealth));
            players.forEach(player -> {
                bossEvent.addPlayer((ServerPlayer) player);
            });
        }
    }

    @Override
    public void reward(Player player) {
        getRewardList().forEach(itemAndRate -> {
            if (!itemAndRate.sendWithMSG(player, 1).is(Items.AIR) && itemAndRate.getRate() <= 0.01) {
                Compute.formatBroad(Te.s("新春活动", style), Te.s(player, "击杀",
                        mobName, style, "获得了", SpecialEventItems.SCALE_2025_0.get()));
            }
        });
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.devil);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.devil;
    }

    @Override
    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(SpecialEventItems.BIG_RED_ENVELOPE.get(), 1),
                new ItemAndRate(SpecialEventItems.SCALE_PIECE.get(), 0.05),
                new ItemAndRate(SpecialEventItems.SCALE_2025_0.get(), 0.01)
        );
    }

    @Override
    public double judgeDamage(Player player, Mob mob, double originDamage) {
        return originDamage;
    }

    @Override
    public void clear() {
        super.clear();
        bossEvent.removeAllPlayers();
    }

    public static Map<Player, Integer> passiveCountMap = new WeakHashMap<>();

    public static void onPlayerWithstandDamage(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName) && !SpecialEffectOnPlayer.inVertigo(player)) {
            passiveCountMap.compute(player, (k, v) -> v == null ? 1 : v + 1);
            int count = passiveCountMap.get(player);
            if (count == 5) {
                SpecialEffectOnPlayer.addVertigoEffect(player, Tick.s(2));
                passiveCountMap.put(player, 0);
            } else {
                Compute.sendDebuffTime(player, SpecialEventItems.SCALE_PIECE.get(), Tick.s(2), count);
            }
        }
    }

    @Override
    public String getKillCountDataKey() {
        return "SpringSnake";
    }
}
