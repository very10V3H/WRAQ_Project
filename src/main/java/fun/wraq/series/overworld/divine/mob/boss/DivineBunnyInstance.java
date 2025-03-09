package fun.wraq.series.overworld.divine.mob.boss;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class DivineBunnyInstance extends NoTeamInstance {

    private static DivineBunnyInstance instance;

    public static String mobName = "圣光班尼";
    public Mob boss;
    public static Style style = CustomStyle.DIVINE_STYLE;
    public static final double MAX_HEALTH = 10 * Math.pow(10, 8);
    public static final int XP_LEVEL = 285;

    public static DivineBunnyInstance getInstance() {
        if (instance == null) {
            instance = new DivineBunnyInstance(new Vec3(2400, 72, 670), 60, 60,
                    new Vec3(2400, 72, 670), Te.s(mobName, style));
        }
        return instance;
    }

    public DivineBunnyInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null || boss.tickCount == 0) return;

        int tick = boss.tickCount;
        DivineUtils.handleMobTick(boss);
        if (tick % 60 == 0) {
            commonAttack();
        }
        if (tick % 100 == 0) {
            dotAttack();
        }
    }

    @Override
    public void summonModule(Level level) {
        Rabbit mob = new Rabbit(EntityType.RABBIT, level);
        mob.setVariant(Rabbit.Variant.EVIL);
        MobSpawn.setMobCustomName(mob, Component.literal(mobName).withStyle(style), XP_LEVEL);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 15000, 800, 800, 0.4,
                3, 0.6, 600, 25, MAX_HEALTH, 0.3);
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        mobList.add(mob);
        boss = mob;
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(mob.getDisplayName(),
                BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    private void commonAttack() {
        players.forEach(player -> {
            DivineUtils.createDivineParticle(player,
                    boss.getEyePosition(), player.getEyePosition().subtract(0, 1, 0));
            Damage.causeManaDamageToPlayer(boss, player, player.getMaxHealth() * 0.2 * getDamageRate());
        });
    }

    private void dotAttack() {
        Player randomPlayer = players.stream().toList().get(RandomUtils.nextInt(0, players.size()));
        Compute.createRangeEffectDot(boss, randomPlayer.position(), 5, ((mob, player) -> {
            Damage.causeManaDamageToPlayer(boss, player, player.getMaxHealth() * 0.1 * getDamageRate());
        }), style, 5, 40);
    }

    private double getDamageRate() {
        int holyLightCount = (int) players.stream().mapToInt(DivineUtils::getHolyLightCount).count();
        if (holyLightCount < 100) {
            return 1 + (100 - holyLightCount) / 100.0;
        }
        return 1;
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON;
    }

    @Override
    public Item getSummonAndRewardNeedItem() {
        return ModItems.REASON.get();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 5;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.DIVINE_BOSS_SOUL.get(), 0.5),
                new ItemAndRate(DivineIslandItems.DIVINE_KNIFE.get(), 0.01),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        DivineUtils.sendMSG(player, content);
    }

    @Override
    public String getKillCountDataKey() {
        return "DivineBunny";
    }

    @Override
    public List<Component> getIntroduction() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", style, "每3s对周围所有玩家造成基于最大生命值的魔法伤害。"));
        components.add(Te.s("2.", style, "每5s随机选定一名玩家，在其位置生成一片持续伤害区域。"));
        components.add(Te.s("3.", style, "当玩家的圣光辐照层数小于100层，对玩家的伤害提升(100 - 层数)%。"));
        return components;
    }
}
