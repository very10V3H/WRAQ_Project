package com.very.wraq.events.fight;

import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.process.system.potion.NewPotionEffects;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.end.eventController.LightningIslandRecall.*;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.series.instance.series.devil.DevilAttackArmor;
import com.very.wraq.series.instance.series.moon.Equip.MoonBelt;
import com.very.wraq.series.instance.series.taboo.TabooAttackArmor;
import com.very.wraq.series.overworld.chapter2.lightningIsland.Armor.*;
import com.very.wraq.series.overworld.chapter7.star.StarBottle;
import com.very.wraq.series.newrunes.chapter1.ForestNewRune;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakePower;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import com.very.wraq.series.overworld.sakuraSeries.Slime.SlimeBoots;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.DamageInfluence;
import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
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

        double DamageDecrease = Compute.SwordSkill1And4(data, player);

        DamageDecrease += Compute.SwordSkill14(data, player, monster);
        DamageDecrease += Compute.BowSkill4(data, player);
        DamageDecrease += Compute.ManaSkill4(data, player);
        DamageDecrease += Compute.LevelSuppress(player, monster); // 等级压制
        DamageDecrease += ScarecrowChestPlate(player); // 稻草甲
        DamageDecrease += SnowArmorEffectDamageDecrease(monster); // 冰川盔甲
        DamageDecrease += EarthPower.MobDamageDecrease(monster); // 地蕴法术
        DamageDecrease += CastleCurios.DamageDecrease(player);
        DamageDecrease += LakePower.PlayerDefend(player); // 湖泊法术

        damage *= (1 - DamageDecrease);
        damage *= TabooAttackArmor.Passive(player);
        damage *= (1 - NewPotionEffects.resistanceEnhance(player));

        damage *= DamageInfluence.getPlayerWithstandDamageInfluence(player, monster);

        if (Utils.witherBonePowerCCMonster.contains(monster)) damage *= 0.8;
        if (data.contains(StringUtils.SakuraDemonSword)
                && data.getInt(StringUtils.SakuraDemonSword) > player.getServer().getTickCount()) damage = 0;

        if (SlimeBoots.IsOn(player)) {
            damage -= player.getMaxHealth() * 0.1;
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_HURT), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        } // 史莱姆鞋子 伤害削减

        double healthSteal = MobAttributes.healthSteal(monster);

        Random random = new Random();
        if (random.nextDouble() < Compute.PlayerDodgeRate(player)) {
            damage = 0;
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_JUMP_SMALL), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
            ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                    new ClientboundSetActionBarTextPacket(Component.literal("闪避！").withStyle(CustomStyle.styleOfFlexible));
            ((ServerPlayer) player).connection.send(clientboundSetActionBarTextPacket);
        } // 闪避几率
        String name = player.getName().getString();
        if (Utils.rollingTickMap.containsKey(name) && player.getServer().getTickCount() < Utils.rollingTickMap.get(name))
            damage = 0;

        if (ForestNewRune.protectPlayerFromDamage(player, damage)) damage = 0;
        if (damage > 0) {
            double damageAfterShieldDecrease = Compute.PlayerShieldDecrease(player, damage);
            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + damageAfterShieldDecrease));
            } else {
                if (damageAfterShieldDecrease > 0 && player.isAlive()) {
                    if (player.getHealth() / player.getMaxHealth() < 0.5) HurtEventModule.ManaSkill14(data, player);
                    Compute.Damage.DirectDamageToPlayer(monster, player, damageAfterShieldDecrease);
                    player.hurtTime = 10;
                    monster.heal((float) (damageAfterShieldDecrease * healthSteal));
                    MoonBelt.PassiveGetDamage(player, damageAfterShieldDecrease); // 尘月玉缠
                }
                ModNetworking.sendToClient(new SoundsS2CPacket(2), (ServerPlayer) player);
            }

            ForestRuneAndLightningArmor(player, monster);
            SnowArmorEffect(player, monster);
            SnowStrayAttackEffect(player, monster);
            ForestZombieHealEffect(monster);
            MineMonsterAttack(monster, player);
            MineShield(player);
            DevilAttackArmor.DevilAttackArmorPassive(player, monster); // 封魔者圣铠
            StarBottle.playerBattleTickMapRefresh(player);
        }
    }

    @SubscribeEvent
    public static void monsterAttackEvent(LivingAttackEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity() instanceof Civil civil && event.getSource().getEntity() instanceof Mob monster) {
                event.setCanceled(true);
                double Damage = 0;
                if (monster.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                    Damage = monster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                double Defence = 0;
                double DefencePenetration = Utils.defencePenetration.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), 0d);
                double DefencePenetration0 = Utils.defencePenetration0.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), 0d);
                if (civil.owner != null) Defence = PlayerAttributes.defence(civil.owner);
                Damage *= Compute.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0);

                civil.setLastHurtByMob(monster);
                civil.setHealth((float) (civil.getHealth() - Damage));
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
                double CritDamageDecrease = Compute.PlayerCritDamageDecrease(player);

                exDamage += MonsterExDamage(monster, player); // 各种怪物伤害增益

                double finalDamage = ((baseDamage + exDamage) * CritDamage(critRate, critDamage, CritDamageDecrease) *
                        Compute.defenceDamageDecreaseRate(playerDefence, defencePenetration, defencePenetration0));

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

    public static void ForestRuneAndLightningArmor(Player player, Mob monster) {
        CompoundTag data = player.getPersistentData();
        Item PlayerHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item PlayerChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item PlayerLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item PlayerBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        int LightningArmorCount = 0;
        if (PlayerHelmet instanceof LightningArmor) LightningArmorCount++;
        if (PlayerChest instanceof LightningArmor) LightningArmorCount++;
        if (PlayerLeggings instanceof LightningArmor) LightningArmorCount++;
        if (PlayerBoots instanceof LightningArmor) LightningArmorCount++;

        if (PlayerHelmet instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerChest instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerLeggings instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerBoots instanceof IntensifiedLightningArmor) LightningArmorCount += 2;

        if (LightningArmorCount == 0 && player.getX() < 1523 && player.getX() > 1182 && player.getZ() < 633 && player.getZ() > 371) {
            player.getPersistentData().putBoolean("Lighting", true);
        }
        if (LightningArmorCount > 0) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
            lightningBolt.setDamage(0);
            lightningBolt.setSilent(true);
            lightningBolt.setVisualOnly(true);
            lightningBolt.moveTo(monster.getPosition(1));
            player.level().addFreshEntity(lightningBolt);
            monster.hurt(monster.damageSources().playerAttack(player), (float) (PlayerAttributes.defence(player) * 0.5 * LightningArmorCount));
        }
    }

    public static double ScarecrowChestPlate(Player player) {
        double DamageDecrease = 0;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WheatArmorChest.get())) {
            DamageDecrease += 0.3;
        }
        return DamageDecrease;
    }

    public static void SnowArmorEffect(Player player, Mob monster) {
        if (Compute.ArmorCount.Snow(player) >= 4) {
            int TickCount = player.getServer().getTickCount();
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
        int TickCount = monster.getServer().getTickCount();
        if (data.getInt(StringUtils.SnowArmorEffect) > TickCount) DamageDecrease += 0.25;
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

    public static void MineMonsterAttack(Mob monster, Player player) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorMine.get())) {
            int TickCount = player.getServer().getTickCount();
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.MineMonsterEffect, TickCount + 60);
        }
    }

    public static void MineShield(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MineShield.get())) {
            Utils.MineShieldEffect.put(player, player.getServer().getTickCount() + 100);
            Compute.sendEffectLastTime(player, ModItems.MineSoul.get().getDefaultInstance(), 100);
        }
    }

/*    public static Map<Player,Integer> playerPlainRune3CoolDownMap = new HashMap<>();

    public static boolean playerIsProtectingByPlainRune3(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        if (data.contains(StringUtils.PlainRunes.Rune) && data.getInt(StringUtils.PlainRunes.Rune) == 3) {
            if (!playerPlainRune3CoolDownMap.containsKey(player) || playerPlainRune3CoolDownMap.get(player) < TickCount) {
                playerPlainRune3CoolDownMap.put(player,TickCount + 200);
                Compute.CoolDownTimeSend(player,ModItems.PlainRune3.get().getDefaultInstance(),200);
                return true;
            }
        }
        return false;
    }*/

}
