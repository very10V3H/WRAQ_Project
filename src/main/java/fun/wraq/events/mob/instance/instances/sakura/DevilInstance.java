package fun.wraq.events.mob.instance.instances.sakura;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DevilInstance extends NoTeamInstance {

    private static DevilInstance instance;
    public static boolean devilSkill3Flag = false;
    public static boolean devilSkill4Flag = false;
    public static double exDamage = 0;

    public static String mobName = "魔王";

    public static DevilInstance getInstance() {
        if (instance == null) {
            instance = new DevilInstance(new Vec3(2624, 192, 1724), 50, 60, new Vec3(2624, 192, 1724),
                    Component.literal(mobName).withStyle(CustomStyle.styleOfBloodMana));
        }
        return instance;
    }

    public DevilInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 150);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        List<Player> players = getNearPlayers(mob.level());
        int frequency = 160;
        if (mob.tickCount % frequency == 0) {
            Random random = new Random();
            if (random.nextDouble() < 0.5) {
                skill1(mob, players);
            } else skill2(mob, players);
        }
        skill3(mob, players);
        skill4(mob, players);
    }

    @Override
    public void summonModule(Level level) {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, level);

        zombie.setBaby(true);
        MobSpawn.setMobCustomName(zombie, Component.literal(mobName).withStyle(CustomStyle.styleOfBloodMana), 150);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), 150);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 1500, 110, 110,
                0.4, 4, 0.25, 50, 0, 100 * Math.pow(10, 4), 0.35);

        zombie.setHealth(zombie.getMaxHealth());
        zombie.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorDevilHelmet.get().getDefaultInstance());
        zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
        zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
        zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());
        zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.manaKnife.get().getDefaultInstance());

        devilSkill3Flag = true;
        devilSkill4Flag = true;
        exDamage = 0;

        zombie.moveTo(pos);
        level.addFreshEntity(zombie);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(zombie.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(zombie);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> itemAndRate.sendWithMSG(player, 1));
        DailyMission.addCount(player, DailyMission.devilInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 150);
        Guide.trig(player, 20);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.devil);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("冰霜骑士装备").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.DevilLoot.get(), 1),
                new ItemAndRate(ModItems.TabooPiece.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

    public static void skill1(Mob mob, List<Player> playerList) {
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                int manaDecrease = 300;
                if (Mana.getPlayerCurrentManaNum(player) < manaDecrease) {
                    manaDecrease -= (int) Mana.getPlayerCurrentManaNum(player);
                    Mana.addOrCostPlayerMana(player, -Mana.getPlayerCurrentManaNum(player));
                    Damage.causeManaDamageToPlayer(mob, player, 20 * manaDecrease);
                } else Mana.addOrCostPlayerMana(player, -manaDecrease);
/*                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("魔源奔流", CustomStyle.styleOfBloodMana),
                        Te.m("魔王吸取大量法力值!", ChatFormatting.RED), 0, 20, 10);*/
            }
        });
        exDamage += 600;
        MobSpawn.MobBaseAttributes.attackDamage.put(mobName, 3000 + exDamage);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
    }

    public static void skill2(Mob mob, List<Player> playerList) {
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                MonsterAttackEvent.monsterAttack(mob, player, 2000);
                mob.heal(75000);
/*                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("腥月之子", CustomStyle.styleOfBloodMana),
                        Te.m("魔王吸取大量生命值!", ChatFormatting.RED), 0, 20, 10);*/
            }
        });
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
    }

    public static void skill3(Mob mob, List<Player> playerList) {
        if (devilSkill3Flag && mob.getHealth() < mob.getMaxHealth() * 0.25) {
            playerList.forEach(player -> {
                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("浴魔复生", CustomStyle.styleOfBloodMana),
                        Te.m("魔王回复大量生命值!", ChatFormatting.RED), 0, 20, 10);
            });
            exDamage = 0;
            MobSpawn.MobBaseAttributes.attackDamage.put(mobName, 1500d);
            devilSkill3Flag = false;
            mob.heal(mob.getMaxHealth() * 0.75f);
            ParticleProvider.createBallDisperseParticle(ParticleTypes.WITCH, (ServerLevel) mob.level(), mob.position(), 0.75, 30);
        }
    }

    public static void skill4(Mob mob, List<Player> playerList) {
        if (devilSkill4Flag && mob.getHealth() < mob.getMaxHealth() * 0.2) {
            devilSkill4Flag = false;
            playerList.forEach(player -> {
                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("魔能释溢", CustomStyle.styleOfBloodMana),
                        Te.m("魔王对所有玩家造成高额魔法伤害!", ChatFormatting.RED), 0, 20, 10);
                if (player.distanceTo(mob) < 50) {
                    Damage.causeManaDamageToPlayer(mob, player, player.getMaxHealth());
                }
            });
            ParticleProvider.createBallDisperseParticle(ParticleTypes.WITCH, (ServerLevel) mob.level(), mob.position(), 0.75, 30);
        }
    }
}
