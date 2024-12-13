package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainInstance extends NoTeamInstance {

    private static PlainInstance instance;

    public static String mobName = "普莱尼";

    public static PlainInstance getInstance() {
        if (instance == null) {
            instance = new PlainInstance(new Vec3(1167, 111, 24), 30, 60, new Vec3(1167, 111, 24),
                    Component.literal("普莱尼").withStyle(ChatFormatting.GREEN));
        }
        return instance;
    }

    public PlainInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 50);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        Level level = mob.level();
        List<Player> players = getNearPlayers(level);
        if (mob.tickCount % 100 == 0) {
            players.forEach(player -> {
                if (player.position().distanceTo(mob.position()) <= 6) {
                    player.heal(75);
                } else {
                    Damage.manaDamageToPlayer(mob, player, 125);
                    mob.heal(125);
                }
            });
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                    1, 1, 120, ModParticles.LONG_PLAIN.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                    1.5, 1, 120, ModParticles.LONG_PLAIN.get(), 1);
        }

        if (mob.tickCount % 100 == 50) {
            players.forEach(player -> {
                if (player.position().distanceTo(mob.position()) <= 6) {
                    Damage.manaDamageToPlayer(mob, player, 200);
                    mob.heal(200);
                } else {
                    player.heal(100);
                }
            });

            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                    1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                    1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
        Element.ElementProvider(mob, Element.life, 2);
    }

    @Override
    public void summonModule(Level level) {
        Stray stray = new Stray(EntityType.STRAY, level);
        MobSpawn.setMobCustomName(stray, Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain), 50);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), 50);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, 400, 40, 40, 0.2,
                1, 0, 0, 0, 20000, 0.2);

        stray.setHealth(stray.getMaxHealth());
        stray.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorPlainBossHelmet.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorPlainBossChest.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorPlainBossLeggings.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorPlainBossBoots.get().getDefaultInstance());

        stray.moveTo(pos);
        level.addFreshEntity(stray);
        mobList.add(stray);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(stray.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
        DailyMission.addCount(player, DailyMission.plainInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 50);
        Guide.trig(player, 11);
    }

    @Override
    public boolean allowReward(Player player) {
        return true;
    }

    @Override
    public Component allowRewardCondition() {
        return null;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.PlainBossSoul.get(), 1),
                new ItemAndRate(ModItems.PlainAttackRing0.get(), 0.0125),
                new ItemAndRate(ModItems.PlainManaAttackRing0.get(), 0.0125),
                new ItemAndRate(ModItems.PlainHealthRing0.get(), 0.0125),
                new ItemAndRate(ModItems.PlainDefenceRing0.get(), 0.0125),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1),
                new ItemAndRate(ModItems.plainNecklace.get(), 0.08));
    }
}
