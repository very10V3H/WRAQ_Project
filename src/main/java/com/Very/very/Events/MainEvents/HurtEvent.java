package com.Very.very.Events.MainEvents;

import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.Events.WaltzAndModule.HurtEventModule;
import com.Very.very.Files.FileHandler;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.CritHitParticleS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.SwordSkill12S2CPacket;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

import static java.lang.Math.max;

@Mod.EventBusSubscriber
public class HurtEvent {
    @SubscribeEvent
    public static void Hurt(LivingHurtEvent event) throws IOException {
        event.setPhase(EventPriority.LOWEST);
        if (!event.getEntity().level().isClientSide) {
            Entity Attacker = event.getSource().getEntity();
            Entity Hurter = event.getEntity();
            if (Hurter.getClass() == Villager.class ||
                    Hurter.getClass() == WanderingTrader.class ||
                    Hurter instanceof Animal || event.getEntity().level().isClientSide
            ) event.setCanceled(true);
            else {
                if (Attacker == null && Hurter instanceof Player) {
                    Player player = (Player) Hurter;
                    double damage = event.getAmount();
                    double damageAfterComputeShield = Compute.PlayerShieldDecrease(player, damage);
                    if (damageAfterComputeShield > 0) {
                        player.setHealth((float) (player.getHealth() - damageAfterComputeShield));
                    }
                    event.setCanceled(true);
                }
                if (Attacker instanceof Player) {
                    Player player = (Player) event.getSource().getEntity();
                    if (player.getName().getString().equals("very_H") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))) {
                        Hurter.remove(Entity.RemovalReason.KILLED);
                    }
                    if (Hurter instanceof Mob monster) {
                        CompoundTag data = player.getPersistentData();
                        HurtEventModule.ActionBarNumPacketSender(player, monster);
                        Compute.ChargingModule(data,player); // 盈能攻击
                        if (monster.getHealth() <= event.getAmount()) {
                            monster.kill();
                            FileHandler.WARQLogsWrite(player,StringUtils.LogsType.MobKill,"击杀了:" + monster.getName().getString());
                            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSoundPacket);
                            Compute.Drops(player, monster, monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), player.getItemInHand(InteractionHand.MAIN_HAND));
                            if ((data.contains(StringUtils.DamageTypes.IsAttack) && data.getBoolean(StringUtils.DamageTypes.IsAttack))) // 近战攻击专属增益
                                AttackEventModule.KillPositiveEffect(player);
                            HurtEventModule.VolcanoRune0Judge(player);
                            HurtEventModule.SwordSkill2(data,player); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                            HurtEventModule.BowSkill2(data,player); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                            HurtEventModule.ManaSkill2(data,player); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）
                        }
                        float BaseDamage = Compute.PlayerAttackDamage(player);
                        float BreakDefense = Compute.PlayerBreakDefence(player);
                        float CHitDamage = Compute.PlayerCriticalHitDamage(player);
                        float Defence = Compute.MonsterDefence(monster);
                        float HealSteal = Compute.PlayerHealSteal(player);
                        float BreakDefence0 = Compute.PlayerBreakDefence0(player);
                        float HealEffectUp = Compute.PlayerHealEffectUp(player);

                        if ((data.contains(StringUtils.DamageTypes.IsAttack) && data.getBoolean(StringUtils.DamageTypes.IsAttack))) {

                            AttackEventModule.SwordSkill3Attack(data,player,monster); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
                            AttackEventModule.SwordSkill6Attack(data,player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
                            AttackEventModule.SwordSkill13Attack(data,player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）


                            if (Defence == 0) Defence = (float) monster.getAttribute(Attributes.ARMOR).getValue();
                            float damage = 0;
                            if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
                                AttackEventModule.SwordSkill5Attack(data,player); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
                                data.putBoolean(StringUtils.DamageTypes.Crit, false);
                                damage = data.getFloat(StringUtils.DamageTypes.DamageAmount) + data.getFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount);
                                if (damage > 0) {
                                    if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                                        damage *= 0.8f;
                                    player.setHealth(Math.min((player.getHealth() + (damage) * HealSteal) * (1 + HealEffectUp), player.getMaxHealth()));
                                    ModNetworking.sendToClient(new CritHitParticleS2CPacket(monster.getX(), monster.getY(), monster.getZ()), (ServerPlayer) player);
                                }
                                HurtEventModule.SabreDamage(player, monster, HealEffectUp);
                                ModNetworking.sendToClient(new SoundsS2CPacket(0), (ServerPlayer) Attacker);
                            } else {
                                damage = data.getFloat(StringUtils.DamageTypes.DamageAmount) + data.getFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount);
                                if (damage > 0) {
                                    if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                                        damage *= 0.8f;
                                    player.setHealth(Math.min((player.getHealth() + damage * HealSteal) * (1 + HealEffectUp), player.getMaxHealth()));
                                }
                                HurtEventModule.SabreDamage(player, monster, HealEffectUp);
                                ModNetworking.sendToClient(new SoundsS2CPacket(1), (ServerPlayer) Attacker);
                            }

                            AttackEventModule.SwordSkill12Attack(data,player); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）

                            HurtEventModule.ForestRune3Judge(player, monster, HealEffectUp, BaseDamage);
                            HurtEventModule.VolcanoRune2Judge(player, monster, BaseDamage);

                            AttackEventModule.ExDamageMSG(player);
                            data.putBoolean(StringUtils.DamageTypes.IsAttack, false);
                        }
                        data.putBoolean(StringUtils.DamageTypes.IsMana,false);
                        data.putBoolean(StringUtils.DamageTypes.IsBow,false);
                        data.putBoolean(StringUtils.DamageTypes.Crit,false);
                    }
                    if (Hurter instanceof Player hurter) {
                        if (hurter.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)
                                || Attacker.level().equals(Attacker.getServer().getLevel(Level.OVERWORLD))
                                || HurtEventModule.PlainRune3Judge(hurter)) event.setCanceled(true);
                        else {
                            Player attacker = (Player) Attacker;
                            event.setCanceled(true);
                            CompoundTag dataA = attacker.getPersistentData();
                            CompoundTag dataH = hurter.getPersistentData();
                            if (dataA.contains(StringUtils.DamageTypes.IsAttack) && dataA.getBoolean(StringUtils.DamageTypes.IsAttack)) {
                                hurter.setLastHurtByPlayer(attacker);
                                double damage = event.getAmount();
                                double damageAfterComputeShield = Compute.PlayerShieldDecrease(hurter, damage);

                                AttackEventModule.SwordSkill3Attack(dataA,attacker,hurter); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
                                AttackEventModule.SwordSkill6Attack(dataA,attacker); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
                                AttackEventModule.SwordSkill13Attack(dataA,attacker); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）

                                Compute.ChargingModule(dataA,attacker); // 盈能攻击
                                Compute.ChargingModule(dataH,hurter); // 盈能攻击

                                if (dataA.getBoolean(StringUtils.DamageTypes.Crit)) {
                                    AttackEventModule.SwordSkill5Attack(dataA,attacker); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
                                }

                                if (damageAfterComputeShield > 0) {
                                    hurter.setHealth((float) (hurter.getHealth() - damageAfterComputeShield));
                                    attacker.heal((float) damageAfterComputeShield * Compute.PlayerHealSteal(attacker) * (1 + Compute.PlayerHealEffectUp(attacker)));
                                    if (damageAfterComputeShield >= hurter.getHealth()) {
                                        AttackEventModule.KillPositiveEffect(attacker);
                                        if (hurter.getHealth() < damageAfterComputeShield && hurter.isDeadOrDying()) {
                                            Compute.FormatBroad(hurter.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                                    Component.literal(hurter.getName().getString() + "被").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                            append(attacker.getName()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                            append(Component.literal("使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                            append(attacker.getItemInHand(InteractionHand.MAIN_HAND).getDisplayName()).
                                                            append(Component.literal("重伤。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                        }
                                        hurter.setLastHurtByPlayer(attacker);
                                        HurtEventModule.VolcanoRune0Judge(attacker);
                                        HurtEventModule.SwordSkill2(dataA,attacker); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                                        HurtEventModule.BowSkill2(dataA,attacker); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                                        HurtEventModule.ManaSkill2(dataA,attacker); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）
                                    }
                                }

                                AttackEventModule.SwordSkill12Attack(dataA,attacker); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）

                                HurtEventModule.LightingArmorJudge(attacker, hurter);
                                HurtEventModule.VolcanoRune2Judge(attacker, hurter);
                                HurtEventModule.FightMSGSender(attacker, hurter);
                                HurtEventModule.ManaSwordJudge(attacker, hurter);
                                HurtEventModule.ForestRune1And2Judge(attacker, hurter);
                                HurtEventModule.ForestRune3Judge(attacker, hurter);
                                AttackEventModule.ExDamageMSG(attacker);
                                dataA.putBoolean(StringUtils.DamageTypes.IsAttack, false);
                            }
                            dataA.putBoolean(StringUtils.DamageTypes.IsMana,false);
                            dataA.putBoolean(StringUtils.DamageTypes.IsBow,false);
                            dataA.putBoolean(StringUtils.DamageTypes.Crit,false);
                        }
                    }
                }
            }
        }
    }
}