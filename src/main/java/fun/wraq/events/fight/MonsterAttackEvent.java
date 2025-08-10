package fun.wraq.events.fight;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnWithStandDamageCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.mob.chapter2.SkySkeletonSpawnController;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.teamInstance.instances.spring.SpringSnakeInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.impl.GemOnWithstandDamage;
import fun.wraq.series.instance.series.devil.DevilAttackArmor;
import fun.wraq.series.newrunes.chapter1.ForestNewRune;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.sakura.Slime.SlimeBoots;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class MonsterAttackEvent {

    public static void mobAttack(Mob mob, Player player, double damage) {
        CompoundTag data = player.getPersistentData();
        double withStandDamageRate = 0;
        withStandDamageRate += DamageInfluence.modifyPlayerWithstandDamageRate(player, mob, damage);
        damage *= (1 + withStandDamageRate);
        damage *= SkySkeletonSpawnController.getDamageRate(mob);
        if (data.contains(StringUtils.SakuraDemonSword)
                && data.getInt(StringUtils.SakuraDemonSword) > Tick.get()) {
            damage = 0;
        }

        // 闪避几率
        Random random = new Random();
        if (random.nextDouble() < Compute.PlayerDodgeRate(player)) {
            damage = 0;
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_JUMP_SMALL), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
            ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                    new ClientboundSetActionBarTextPacket(Component.literal("闪避！").withStyle(CustomStyle.styleOfFlexible));
            ((ServerPlayer) player).connection.send(clientboundSetActionBarTextPacket);
        }

        String name = player.getName().getString();
        if (Utils.rollingTickMap.containsKey(name) && Tick.get() < Utils.rollingTickMap.get(name)) {
            damage = 0;
        }
        if (ForestNewRune.protectPlayerFromDamage(player, damage)) {
            damage = 0;
        }

        // 史莱姆鞋子 伤害削减
        if (SlimeBoots.IsOn(player) && damage > 0) {
            damage -= player.getMaxHealth() * 0.025;
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_HURT), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        }

        // 副手盾受到伤害削减
        if (Utils.shieldTag.containsKey(player.getOffhandItem().getItem())
                && Utils.swordTag.containsKey(player.getMainHandItem().getItem())) {
            double damageDecreaseValue = PlayerAttributes.defence(player)
                    + PlayerAttributes.manaDefence(player) + PlayerAttributes.maxHealth(player) * 0.01;
            if (damageDecreaseValue > damage * 0.75) {
                MySound.soundToPlayer(player, SoundEvents.SHIELD_BLOCK);
            }
            damage -= damageDecreaseValue;
        }

        if (damage > 0) {
            double finalDamage = Shield.decreasePlayerShield(player, damage);

            // 伤害削减属性
            finalDamage -= PlayerAttributes.damageDirectDecrease(player);

            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + finalDamage));
            } else {
                if (finalDamage > 0 && player.isAlive()) {
                    Damage.causeDirectDamageToPlayer(mob, player, finalDamage);
                    player.hurtTime = 10;
                    mob.heal((float) (finalDamage * MobAttributes.healthSteal(mob)));
                    OnWithStandDamageCurios.withStandDamage(player, mob, finalDamage);
                }
                ModNetworking.sendToClient(new SoundsS2CPacket(2), (ServerPlayer) player);
            }

            SnowArmorEffect(player, mob);
            mineShield(player);
            DevilAttackArmor.DevilAttackArmorPassive(player, mob); // 封魔者圣铠
            StarBottle.playerBattleTickMapRefresh(player);
            GemOnWithstandDamage.withStandDamage(player, mob, finalDamage);
            SpringSnakeInstance.onPlayerWithstandDamage(player, mob);
            DivineUtils.onPlayerWithstandDamage(mob, player);
        }
        CitadelGuardianInstance.playerWithstandDamage(player, mob);

    }

    public static void causeCommonAttackToPlayer(Mob mob, Player player) {
        causeCommonAttackToPlayer(mob, player, 1);
    }

    public static void causeCommonAttackToPlayer(Mob mob, Player player, double rate) {
        player.setLastHurtByMob(mob);
        double defencePenetration = MobAttributes.defencePenetration(mob);
        double defencePenetration0 = MobAttributes.defencePenetration0(mob);
        double critRate = MobAttributes.critRate(mob);
        double critDamage = MobAttributes.critDamage(mob);
        double baseDamage = MobAttributes.attackDamage(mob) * rate;
        double exDamage = 0;
        double playerDefence = PlayerAttributes.defence(player);
        double CritDamageDecrease = PlayerAttributes.decreasePlayerCritDamage(player);
        double finalDamage = ((baseDamage + exDamage) * CritDamage(critRate, critDamage, CritDamageDecrease) *
                Damage.defenceDamageDecreaseRate(playerDefence, defencePenetration, defencePenetration0));
        mobAttack(mob, player, finalDamage);
    }

    @SubscribeEvent
    public static void monsterAttackEvent(LivingAttackEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Mob monster
                    && player.invulnerableTime == 0) {
                event.setCanceled(true);
                causeCommonAttackToPlayer(monster, player);
            }
            if (event.getEntity() instanceof Civil civil && event.getSource().getEntity() instanceof Mob monster) {
                event.setCanceled(true);
                double damage = 0;
                if (monster.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                    damage = monster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                double Defence = 0;
                double DefencePenetration = Utils.defencePenetration.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), 0d);
                double DefencePenetration0 = Utils.defencePenetration0.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), 0d);
                if (civil.owner != null) Defence = PlayerAttributes.defence(civil.owner);
                damage *= Damage.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0);
                civil.setLastHurtByMob(monster);
                civil.setHealth((float) (civil.getHealth() - damage));
                Compute.getNearPlayer(civil, 10).forEach(eachPlayer -> {
                    ModNetworking.sendToClient(new SoundsS2CPacket(2), eachPlayer);
                });
            }
        }
    }

    public static double CritDamage(double CritRate, double CritDamage, double CritDamageDecrease) {
        Random random = new Random();
        if (random.nextDouble(1) < CritRate) return 1 + Math.max(0, CritDamage * (1 - CritDamageDecrease));
        return 1;
    }

    public static void SnowArmorEffect(Player player, Mob monster) {
        if (SuitCount.getSnowSuitCount(player) >= 4) {
            int TickCount = Tick.get();
            monster.setDeltaMovement(0, 0, 0);
            Compute.addSlowDownEffect(monster, 5, 1);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(), 100), serverPlayer));

            CompoundTag data = monster.getPersistentData();
            data.putInt(StringUtils.SnowArmorEffect, TickCount + 60);

            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(monster.getId(), 60), serverPlayer));
        }
    }

    public static double SnowArmorEffectDamageDecrease(Mob monster) {
        double damageRate = 0;
        CompoundTag data = monster.getPersistentData();
        if (data.getInt(StringUtils.SnowArmorEffect) > Tick.get()) {
            damageRate -= 0.25;
        }
        return damageRate;
    }

    public static void mineShield(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MINE_SHIELD.get())) {
            Utils.MineShieldEffect.put(player, Tick.get() + 100);
            Compute.sendEffectLastTime(player, ModItems.ORE_RUNE.get().getDefaultInstance(), 100);
        }
    }
}
