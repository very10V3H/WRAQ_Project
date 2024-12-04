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
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.impl.GemOnWithstandDamage;
import fun.wraq.series.gems.passive.impl.GemWithstandDamageRateModifier;
import fun.wraq.series.instance.series.devil.DevilAttackArmor;
import fun.wraq.series.instance.series.taboo.TabooAttackArmor;
import fun.wraq.series.newrunes.chapter1.ForestNewRune;
import fun.wraq.series.overworld.chapter1.waterSystem.LakePower;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import fun.wraq.series.overworld.sakuraSeries.Slime.SlimeBoots;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class MonsterAttackEvent {

    public static void monsterAttack(Mob monster, Player player, double damage) {
        CompoundTag data = player.getPersistentData();

        double damageDecreaseRate = Compute.getSwordSkill1And4(data, player);

        damageDecreaseRate += Compute.getSwordSkill14(data, player, monster);
        damageDecreaseRate += Compute.getBowSkill4(data, player);
        damageDecreaseRate += Compute.getManaSkill4(data, player);
        damageDecreaseRate += DamageInfluence.levelSuppress(player, monster); // 等级压制
        damageDecreaseRate += ScarecrowChestPlate(player); // 稻草甲
        damageDecreaseRate += SnowArmorEffectDamageDecrease(monster); // 冰川盔甲
        damageDecreaseRate += EarthPower.MobDamageDecrease(monster); // 地蕴法术
        damageDecreaseRate += LakePower.PlayerDefend(player); // 湖泊法术

        damage *= (1 - damageDecreaseRate);
        damage *= TabooAttackArmor.Passive(player);

        damage *= DamageInfluence.getPlayerWithstandDamageInfluence(player, monster);

        if (Utils.witherBonePowerCCMonster.contains(monster)) damage *= 0.8;
        if (data.contains(StringUtils.SakuraDemonSword)
                && data.getInt(StringUtils.SakuraDemonSword) > Tick.get()) damage = 0;

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
            damage -= player.getMaxHealth() * 0.1;
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_HURT), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        }

        // 副手盾受到伤害削减
        if (Utils.shieldTag.containsKey(player.getOffhandItem().getItem())) {
            double damageDecreaseValue = PlayerAttributes.defence(player) + PlayerAttributes.manaDefence(player);
            if (damageDecreaseValue > damage * 0.75) {
                MySound.soundToPlayer(player, SoundEvents.SHIELD_BLOCK);
            }
            damage -= damageDecreaseValue;
        }

        damage *= WardenInstance.onPlayerWithstandDamageRate(player, monster);
        damage *= GemWithstandDamageRateModifier.onWithstandDamageRate(player, monster, damage);

        double healthSteal = MobAttributes.healthSteal(monster);

        if (damage > 0) {
            double finalDamage = Shield.decreasePlayerShield(player, damage);

            // 伤害削减属性
            finalDamage -= PlayerAttributes.damageDirectDecrease(player);

            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + finalDamage));
            } else {
                if (finalDamage > 0 && player.isAlive()) {
                    Damage.causeDirectDamageToPlayer(monster, player, finalDamage);
                    player.hurtTime = 10;
                    monster.heal((float) (finalDamage * healthSteal));
                    OnWithStandDamageCurios.withStandDamage(player, monster, finalDamage);
                }
                ModNetworking.sendToClient(new SoundsS2CPacket(2), (ServerPlayer) player);
            }

            SnowArmorEffect(player, monster);
            SnowStrayAttackEffect(player, monster);
            ForestZombieHealEffect(monster);
            mineMonsterAttack(monster, player);
            mineShield(player);
            DevilAttackArmor.DevilAttackArmorPassive(player, monster); // 封魔者圣铠
            StarBottle.playerBattleTickMapRefresh(player);
            GemOnWithstandDamage.withStandDamage(player, monster, finalDamage);
        }
        CitadelGuardianInstance.playerWithstandDamage(player, monster);
    }

    @SubscribeEvent
    public static void monsterAttackEvent(LivingAttackEvent event) {
        if (!event.getEntity().level().isClientSide) {
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
                List<Player> playerList = monster.level().getEntitiesOfClass(Player.class, AABB.ofSize(civil.position(), 20, 20, 20));
                playerList.removeIf(player -> player.distanceTo(civil) > 10);
                playerList.forEach(player -> {
                    ModNetworking.sendToClient(new SoundsS2CPacket(2), (ServerPlayer) player);
                });
            }

            if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Mob monster
                    && player.invulnerableTime == 0) {
                event.setCanceled(true);
                player.setLastHurtByMob(monster);

                double defencePenetration = MobAttributes.defencePenetration(monster);
                double defencePenetration0 = MobAttributes.defencePenetration0(monster);
                double critRate = MobAttributes.critRate(monster);
                double critDamage = MobAttributes.critDamage(monster);

                double baseDamage = MobAttributes.attackDamage(monster);
                double exDamage = 0;

                double playerDefence = PlayerAttributes.defence(player);
                double CritDamageDecrease = PlayerAttributes.decreasePlayerCritDamage(player);

                exDamage += MonsterExDamage(monster, player); // 各种怪物伤害增益

                double finalDamage = ((baseDamage + exDamage) * CritDamage(critRate, critDamage, CritDamageDecrease) *
                        Damage.defenceDamageDecreaseRate(playerDefence, defencePenetration, defencePenetration0));

                monsterAttack(monster, player, finalDamage);
            }
        }
    }

    public static double MonsterExDamage(Mob monster, Player player) {
        double ExDamage = 0;
        Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (monsterHelmet.equals(ModItems.ArmorPlain.get())) {
            ExDamage += player.getMaxHealth() * 0.05;
        }
        return ExDamage;
    }

    public static double CritDamage(double CritRate, double CritDamage, double CritDamageDecrease) {
        Random random = new Random();
        if (random.nextDouble(1) < CritRate) return 1 + Math.max(0, CritDamage * (1 - CritDamageDecrease));
        return 1;
    }

    public static double ScarecrowChestPlate(Player player) {
        double DamageDecrease = 0;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WheatArmorChest.get())) {
            DamageDecrease += 0.3;
        }
        return DamageDecrease;
    }

    public static void SnowArmorEffect(Player player, Mob monster) {
        if (SuitCount.getSnowSuitCount(player) >= 4) {
            int TickCount = Tick.get();
            monster.setDeltaMovement(0, 0, 0);
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 100, false, false));
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(), 100), serverPlayer));

            CompoundTag data = monster.getPersistentData();
            data.putInt(StringUtils.SnowArmorEffect, TickCount + 60);

            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(monster.getId(), 60), serverPlayer));
        }
    }

    public static double SnowArmorEffectDamageDecrease(Mob monster) {
        double DamageDecrease = 0;
        CompoundTag data = monster.getPersistentData();
        if (data.getInt(StringUtils.SnowArmorEffect) > Tick.get()) DamageDecrease += 0.25;
        return DamageDecrease;
    }

    public static void SnowStrayAttackEffect(Player player, Mob monster) {
        if (monster instanceof Stray) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 100, false, false));
        }
    }

    public static void ForestZombieHealEffect(Mob monster) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorForestZombie.get())) {
            monster.heal(monster.getMaxHealth() * 0.1f);
        }
    }

    public static void mineMonsterAttack(Mob monster, Player player) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorMine.get())) {
            int TickCount = Tick.get();
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.MineMonsterEffect, TickCount + 60);
        }
    }

    public static void mineShield(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MineShield.get())) {
            Utils.MineShieldEffect.put(player, Tick.get() + 100);
            Compute.sendEffectLastTime(player, ModItems.OreRune.get().getDefaultInstance(), 100);
        }
    }
}
