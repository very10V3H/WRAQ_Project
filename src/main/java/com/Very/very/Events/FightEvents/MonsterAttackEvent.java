package com.Very.very.Events.FightEvents;

import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaCurios1;
import com.Very.very.Customize.Players.Eliaoi.EliaoiCurios1;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJuCurios1;
import com.Very.very.Customize.Players.Wcndymlgb.WcndymlgbCurios;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios2;
import com.Very.very.Customize.Players.liulixian_.LiuLiXianCurios1F;
import com.Very.very.Customize.Players.liulixian_.LiulixianCurios;
import com.Very.very.Entity.Entities.Civil.Civil;
import com.Very.very.Events.Instance.Devil;
import com.Very.very.Events.WaltzAndModule.HurtEventModule;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.DamageDecreaseParticleS2CPacket;
import com.Very.very.NetWorking.Packets.ParticlePackets.SlowDownParticleS2CPacket;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.iLightningArmorBoots;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.iLightningArmorChest;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.iLightningArmorHelmet;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.iLightningArmorLeggings;
import com.Very.very.Series.InstanceSeries.Castle.CastleCurios;
import com.Very.very.Series.InstanceSeries.Devil.DevilAttackArmor;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonBelt;
import com.Very.very.Series.InstanceSeries.Taboo.TabooAttackArmor;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.LakePower;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthPower;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Slime.SlimeBoots;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber
public class MonsterAttackEvent {

    @SubscribeEvent
    public static void monsterAttackEvent(LivingAttackEvent event)
    {
        if (!event.getEntity().level().isClientSide)
        {
            if (event.getEntity() instanceof Civil civil && event.getSource().getEntity() instanceof Mob monster) {
                event.setCanceled(true);
                double Damage = 0;
                if (monster.getAttribute(Attributes.ATTACK_DAMAGE) != null) Damage = monster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                double Defence = 0;
                double DefencePenetration = Utils.DefencePenetration.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(),0d);
                double DefencePenetration0 = Utils.DefencePenetration0.getOrDefault(monster.getItemBySlot(EquipmentSlot.HEAD).getItem(),0d);
                if (civil.owner != null) Defence = Compute.PlayerAttributes.PlayerDefence(civil.owner);
                Damage *= Compute.DefenceDamageDecreaseRate(Defence,DefencePenetration,DefencePenetration0);

                civil.setLastHurtByMob(monster);
                civil.setHealth((float) (civil.getHealth() - Damage));
                List<Player> playerList = monster.level().getEntitiesOfClass(Player.class, AABB.ofSize(civil.position(),20,20,20));
                playerList.removeIf(player -> player.distanceTo(civil) > 10);
                playerList.forEach(player -> {
                    ModNetworking.sendToClient(new SoundsS2CPacket(2),(ServerPlayer) player);
                });
            }

            if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Mob monster
                    && event.getEntity().hurtTime == 0) {
                event.setCanceled(true);
                player.setLastHurtByMob(monster);

                Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
                double BreakDefence = Utils.DefencePenetration.getOrDefault(monsterHelmet,0d);
                double BreakDefence0 = Utils.DefencePenetration0.getOrDefault(monsterHelmet,0d);
                double CritRate = Utils.CritRate.getOrDefault(monsterHelmet,0d);
                double CritDamage = Utils.CritDamage.getOrDefault(monsterHelmet,0d);

                double BaseDamage = 0;
                double ExDamage = 0;
                if (monster.getAttribute(Attributes.ATTACK_DAMAGE) != null) BaseDamage = monster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();

                if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DevilManaHelmet.get()))
                    BaseDamage += Devil.DevilAttackUp;

                double Defence = Compute.PlayerAttributes.PlayerDefence(player);
                double CritDamageDecrease = Compute.PlayerCritDamageDecrease(player);

                ExDamage += MonsterExDamage(monster,player); // 各种怪物伤害增益

                double Damage =  ((BaseDamage + ExDamage) * CritDamage(CritRate,CritDamage,CritDamageDecrease) *
                        Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0));

                MonsterAttack(monster,player,Damage);

            }
        }
    }
    public static double MonsterExDamage (Mob monster, Player player) {
        double ExDamage = 0;
        Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (monsterHelmet.equals(ModItems.ArmorPlain.get())) {
            ExDamage += player.getMaxHealth() * 0.05;
        }
        return ExDamage;
    }
    public static double CritDamage (double CritRate, double CritDamage, double CritDamageDecrease) {
        Random random = new Random();
        if (random.nextDouble(1) < CritRate) return 1 + Math.max(0, CritDamage * (1 - CritDamageDecrease));
        return 1;
    }
    public static void ForestRuneAndLightningArmor (Player player, Mob monster) {
        CompoundTag data = player.getPersistentData();
        Item PlayerHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item PlayerChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item PlayerLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item PlayerBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        int LightningArmorCount = 0;
        if (PlayerHelmet instanceof LightningArmorHelmet) LightningArmorCount ++;
        if (PlayerChest instanceof LightningArmorChest) LightningArmorCount ++;
        if (PlayerLeggings instanceof LightningArmorLeggings) LightningArmorCount ++;
        if (PlayerBoots instanceof LightningArmorBoots) LightningArmorCount ++;

        if (PlayerHelmet instanceof iLightningArmorHelmet) LightningArmorCount += 2;
        if (PlayerChest instanceof iLightningArmorChest) LightningArmorCount += 2;
        if (PlayerLeggings instanceof iLightningArmorLeggings) LightningArmorCount += 2;
        if (PlayerBoots instanceof iLightningArmorBoots) LightningArmorCount += 2;

        if(LightningArmorCount == 0 && player.getX() < 1523 && player.getX() > 1182 && player.getZ() < 633 && player.getZ() > 371) {
            player.getPersistentData().putBoolean("Lighting",true);
        }
        if(data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 1)
        {
            if(LightningArmorCount > 0)
            {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                lightningBolt.setDamage(0);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(monster.getPosition(1));
                player.level().addFreshEntity(lightningBolt);
            }
            monster.hurt(monster.damageSources().playerAttack(player),(float) (player.getMaxHealth()*0.1+Compute.PlayerAttributes.PlayerDefence(player) * 0.5 * LightningArmorCount * Compute.EndRune3Judge(player,0)));
            data.putInt(StringUtils.ForestRune.ForestRune1,player.getServer().getTickCount() + 20);
        }
        else
        {
            if(LightningArmorCount > 0)
            {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                lightningBolt.setDamage(0);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(monster.getPosition(1));
                player.level().addFreshEntity(lightningBolt);
                monster.hurt(monster.damageSources().playerAttack(player),(float) (Compute.PlayerAttributes.PlayerDefence(player)*0.5*LightningArmorCount));
            }
            if(data.getInt(StringUtils.ForestRune.ForestRune) == 2 && player.getHealth() < player.getMaxHealth()*0.2 && data.contains(StringUtils.ForestRune.ForestRune))
            {
                if(data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) == 0)
                {
                    player.setHealth(player.getMaxHealth());
                    data.putInt(StringUtils.ForestRune.ForestRune2,2400);
                    player.sendSystemMessage(Component.literal("森林符石-狂野生长 ").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已为你恢复满生命值")));
                }
            }
        }
    }
    public static double ScarecrowChestPlate(Player player) {
        double DamageDecrease = 0;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WheatArmorChest.get())) {
            DamageDecrease += 0.3;
        }
        return DamageDecrease;
    }
    public static void SnowArmorEffect (Player player, Mob monster) {
        if (Compute.ArmorCount.Snow(player) >= 4) {
            int TickCount = player.getServer().getTickCount();
            monster.setDeltaMovement(0,0,0);
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,100,false,false));
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(),100),serverPlayer));

            CompoundTag data = monster.getPersistentData();
            data.putInt(StringUtils.SnowArmorEffect, TickCount + 60);

            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(monster.getId(),60),serverPlayer));
        }
    }
    public static double SnowArmorEffectDamageDecrease (Mob monster) {
        double DamageDecrease = 0;
        CompoundTag data = monster.getPersistentData();
        int TickCount = monster.getServer().getTickCount();
        if (data.getInt(StringUtils.SnowArmorEffect) > TickCount) DamageDecrease += 0.25;
        return DamageDecrease;
    }
    public static void SnowStrayAttackEffect (Player player, Mob monster) {
        if (monster instanceof Stray) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,100,false,false));
        }
    }
    public static void ForestZombieHealEffect (Mob monster) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorForestZombie.get())) {
            monster.heal(monster.getMaxHealth() * 0.1f);
        }
    }
    public static void MineMonsterAttack (Mob monster, Player player) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorMine.get())) {
            int TickCount = player.getServer().getTickCount();
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.MineMonsterEffect,TickCount + 60);
        }
    }
    public static void MineShield (Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MineShield.get())) {
            Utils.MineShieldEffect.put(player,player.getServer().getTickCount() + 100);
            Compute.EffectLastTimeSend(player,ModItems.MineSoul.get().getDefaultInstance(),100);
        }
    }
    public static void MonsterAttack (Mob monster, Player player, double Damage) {
        CompoundTag data = player.getPersistentData();

        double DamageDecrease = Compute.SwordSkill1And4(data,player);

        DamageDecrease += Compute.SwordSkill14(data,player,monster);
        DamageDecrease += Compute.BowSkill4(data,player);
        DamageDecrease += Compute.ManaSkill4(data,player);
        DamageDecrease += Compute.LevelSuppress(player,monster); // 等级压制
        DamageDecrease += ScarecrowChestPlate(player); // 稻草甲
        DamageDecrease += SnowArmorEffectDamageDecrease(monster); // 冰川盔甲
        DamageDecrease += EarthPower.MobDamageDecrease(monster); // 地蕴法术
        DamageDecrease += CastleCurios.DamageDecrease(player);
        DamageDecrease += LakePower.PlayerDefend(player); // 湖泊法术
        DamageDecrease += YxwgCurios2.Passive5DamageDecrease(player);

        Damage *= (1 - DamageDecrease);
        Damage *= TabooAttackArmor.Passive(player);
        Damage *= EliaoiCurios1.MonsterAttackDamageEnhance(player);

        if (Utils.witherBonePowerCCMonster.contains(monster)) Damage *= 0.8;
        if (data.contains(StringUtils.SakuraDemonSword)
                && data.getInt(StringUtils.SakuraDemonSword) > player.getServer().getTickCount()) Damage = 0;

        if (SlimeBoots.IsOn(player)) {
            Damage -= player.getMaxHealth() * 0.1;
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_HURT), SoundSource.PLAYERS,player.getX(),player.getY(),player.getZ(),0.4f,0.4f,0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        } // 史莱姆鞋子 伤害削减

        Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        double HealSteal = Utils.HealthSteal.getOrDefault(monsterHelmet,0d);

        Random random = new Random();
        if (random.nextDouble() < Compute.PlayerDodgeRate(player)) {
            Damage = 0;
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.SLIME_JUMP_SMALL),SoundSource.PLAYERS,player.getX(),player.getY(),player.getZ(),0.4f,0.4f,0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
            ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                    new ClientboundSetActionBarTextPacket(Component.literal("闪避！").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible));
            ((ServerPlayer) player).connection.send(clientboundSetActionBarTextPacket);
        } // 闪避几率

        if (Utils.RollingTickMap.containsKey(player) && player.getServer().getTickCount() < Utils.RollingTickMap.get(player)) Damage = 0;
        if (mobIsInLimit(monster)) Damage = 0;
        if (playerIsProtectingByPlainRune3(player)) Damage = 0;

        Damage *= 0.5; // 怪物伤害降低

        if (Damage > 0) {
            double DamageAfterShieldDecrease = Compute.PlayerShieldDecrease(player, Damage);
            DamageAfterShieldDecrease *= LiulixianCurios.protectPlayer(player, Damage);

            if (LiuLiXianCurios1F.IsLiuLiXian(player))
                DamageAfterShieldDecrease = LiuLiXianCurios1F.LiuLiXianDamageCompute(player, DamageAfterShieldDecrease);
            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + DamageAfterShieldDecrease));
            } else {
                if (DamageAfterShieldDecrease > 0 && player.isAlive()) {
                    if (player.getHealth() / player.getMaxHealth() < 0.5) HurtEventModule.ManaSkill14(data, player);
                    if (player.getHealth() < DamageAfterShieldDecrease) {
                        Compute.FormatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                Component.literal("").
                                        append(player.getDisplayName()).
                                        append(Compute.DescriptionWhiteColor("在与")).
                                        append(monster.getDisplayName()).
                                        append(Component.literal("的战斗中不幸重伤。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.PlayerDeadTimeMap.put(player.getName().getString(), player.getServer().getTickCount() + 6000);
                    }
                    player.setHealth((float) (player.getHealth() - DamageAfterShieldDecrease));
                    FengXiaoJuCurios1.Store(player,DamageAfterShieldDecrease);
                    player.hurtTime = 10;
                    monster.heal((float) (DamageAfterShieldDecrease * HealSteal));
                    MoonBelt.PassiveGetDamage(player, DamageAfterShieldDecrease); // 尘月玉缠
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
            BlackFeisaCurios1.Passive2(player);
            YxwgCurios2.Passive2(player);
            YxwgCurios2.Passive6(player,DamageAfterShieldDecrease);
            WcndymlgbCurios.Summon(player);
        }
    }

    public static Map<Mob,Integer> mobDamageLimitTickMap = new HashMap<>();

    public static boolean mobIsInLimit(Mob mob) {
        int TickCount = mob.getServer().getTickCount();
        return mobDamageLimitTickMap.containsKey(mob) && mobDamageLimitTickMap.get(mob) > TickCount;
    }

    public static void addMobToLimitMap(Mob mob, int tick) {
        int TickCount = mob.getServer().getTickCount();
        mobDamageLimitTickMap.put(mob,TickCount + tick);
    }

    public static Map<Player,Integer> playerPlainRune3CoolDownMap = new HashMap<>();

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
    }

}
