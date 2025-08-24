package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PlainInstance extends NoTeamInstance {

    private static PlainInstance instance;

    public static String mobName = "普莱尼";

    public static PlainInstance getInstance() {
        if (instance == null) {
            instance = new PlainInstance(new Vec3(1167, 112, 28), 30, 60, new Vec3(1167, 112, 28),
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
                    Damage.causeManaDamageToPlayer(mob, player, 250);
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
                    Damage.causeManaDamageToPlayer(mob, player, 400);
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
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 10000 * (1 + 0.75 * (Math.max(1, players.size())) - 1);
        return new MobAttributes(400, 40, 40, 0.2, 1, 0, 0, 0, maxHealth, 0.2);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.life, 2);
    }

    @Override
    public void summonModule(Level level) {
        ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
        MobSpawn.setMobCustomName(zombie, Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain), 50);
        zombie.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LIBRARIAN, 0));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), 50);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, getMainMobAttributes());
        zombie.setHealth(zombie.getMaxHealth());
        MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfLife);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_HOE));
        zombie.moveTo(pos);
        level.addFreshEntity(zombie);
        mobList.add(zombie);
        MobSpawn.setCanNotAddSlowDownOrImprison(zombie);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(zombie.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);

        List<Vec3> offset = List.of(
                new Vec3(1, 0, 0),
                new Vec3(-1, 0, 0),
                new Vec3(0, 0, 1),
                new Vec3(0, 0, -1)
        );
        for (int i = 0 ; i < 4 ; i ++) {
            spawnBabyZombie(level, pos.add(offset.get(i)));
        }
    }

    private void spawnBabyZombie(Level level, Vec3 pos) {
        ZombieVillager babyZombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
        MobSpawn.setMobCustomName(babyZombie, Te.s("普莱尼信徒"), 50);
        babyZombie.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LIBRARIAN, 0));
        babyZombie.setBaby(true);
        double maxHealth = 2000 * (1 + 0.75 * (players.size()) - 1);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(babyZombie, 100, 40, 40, 0.2,
                1, 0, 0, 0, maxHealth, 0.2);
        babyZombie.moveTo(pos);
        level.addFreshEntity(babyZombie);
        mobList.add(babyZombie);
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.PLAIN_BOSS);
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
        return List.of(new ItemAndRate(ModItems.PLAIN_BOSS_SOUL.get(), 2),
                new ItemAndRate(ModItems.PLAIN_BOSS_SCEPTRE.get(), 0.02),
                new ItemAndRate(ModItems.PLAIN_NECKLACE.get(), 0.16),
                new ItemAndRate(ModItems.PLAIN_ATTACK_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_MANA_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_HEALTH_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_DEFENCE_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "PlainBoss";
    }
}
