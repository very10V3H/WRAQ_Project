package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MushroomInstance extends NoTeamInstance {

    private static MushroomInstance instance;

    public static String mobName = "菌菇??";
    public Mob boss;
    public static Style style = CustomStyle.styleOfWarden;
    public static final double MAX_HEALTH = 1.5 * Math.pow(10, 8);
    public static final int XP_LEVEL = 260;

    public static MushroomInstance getInstance() {
        if (instance == null) {
            instance = new MushroomInstance(new Vec3(2347, -42, -704), 60, 60, new Vec3(2347, -42, -704),
                    Component.literal("菌菇??").withStyle(style));
        }
        return instance;
    }

    public MushroomInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null) return;
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        super.reset(tick, removeMob);
    }

    @Override
    public void summonModule(Level level) {
        Warden warden = new Warden(EntityType.WARDEN, level);
        MobSpawn.setMobCustomName(warden, Component.literal("坚守者").withStyle(style), XP_LEVEL);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(warden), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(warden, 6500, 600, 600, 0.4,
                5, 0.6, 300, 25, MAX_HEALTH, 0.35);

        warden.setHealth(warden.getMaxHealth());
        warden.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 12000L);
        warden.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
        warden.moveTo(pos);
        level.addFreshEntity(warden);
        mobList.add(warden);
        boss = warden;

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(warden.getDisplayName(),
                BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            boolean reward = itemAndRate.sendWithMSG(player, 1);
            if (itemAndRate.getRate() < 0.01 && reward) {
                sendFormatMSG(player, Te.s(player.getDisplayName(), "击败", "坚守者", CustomStyle.styleOfWarden,
                        "获得了", itemAndRate.getItemStack().getDisplayName()));
            }
        });

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), XP_LEVEL);
    }

    @Override
    public boolean allowReward(Player player) {
        if (MobSpawn.totalKillCount.getOrDefault(player.getName().getString(), new HashMap<>())
                .getOrDefault(CitadelGuardianInstance.mobName, 0) >= 50) {
            NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden, true);
        }
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden);
    }

    @Override
    public int getRewardNeedItemCount() {
        return 4;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("必须击杀", CitadelGuardianInstance.mobName, CustomStyle.styleOfEnd, "至少",
                "50次", ChatFormatting.RED, "才能获取奖励。");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(WardenItems.WARDEN_SOUL_INGOT.get(), 1),
                new ItemAndRate(GemItems.ANCIENT_DARKNESS_GEM_0.get(), 0.0033),
                new ItemAndRate(GemItems.ANCIENT_SILENT_GEM_0.get(), 0.0033),
                new ItemAndRate(GemItems.ANCIENT_ECHO_GEM_0.get(), 0.0033),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("菌菇", style), content);
    }

    public void commonAttack() {
        double commonAttackDamage = 5000;
        Level level = boss.level();
        getAllPlayers(level).forEach(player -> {
            ParticleProvider.createLineEffectParticle(level, (int) player.distanceTo(boss) * 5,
                    boss.getEyePosition(), player.getEyePosition(), style);
            Damage.causeAttackDamageToPlayer(boss, player, commonAttackDamage);
            Damage.causeManaDamageToPlayer(boss, player, commonAttackDamage, 0, 200);
        });
    }

    public void skill1() {
        Level level = boss.level();
        List<Player> playerList = getAllPlayers(level).stream().toList();
        if (playerList.isEmpty()) return;
        Player randomPlayer = playerList.get(RandomUtils.nextInt(0, playerList.size()));

        // 造成伤害
        PersistentRangeEffect.addEffect(boss, randomPlayer.position(), 5, (effect -> {
            Compute.getNearEntity(boss, Player.class, 5)
                    .stream().filter(e -> e instanceof Player)
                    .map(e -> (Player) randomPlayer).forEach(eachPlayer -> {
                        Damage.causeManaDamageToPlayer(boss, eachPlayer, 5000, 0, 200);
                    });
        }), 5, 40);

        // 制造粒子
        ParticleProvider.createSpaceEffectParticle(level, randomPlayer.position(), 5, 100,
                CustomStyle.MUSHROOM_STYLE, 40);
    }

    public void skill2() {
        Level level = boss.level();
        getAllPlayers(level).forEach(player -> {
            ParticleProvider.createBreakBlockParticle(player, Blocks.BROWN_MUSHROOM);
            SpecialEffectOnPlayer.addImprisonEffect(player, 40);
        });
    }
}
