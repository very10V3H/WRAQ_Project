package com.very.wraq.events.fight;

import com.very.wraq.customized.players.bow.MyMission.MyMissionBow;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios2;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.files.FileHandler;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.MinecartItem;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

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
                if (Attacker == null && Hurter instanceof Player player) {
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
                    if (Hurter instanceof Civil) {
                        event.setCanceled(true);
                        return;
                    }

                    if (Hurter instanceof Mob monster) {
                        CompoundTag data = player.getPersistentData();
                        HurtEventModule.ActionBarNumPacketSender(player, monster);
                        if (monster.getHealth() <= event.getAmount()) {
                            monster.kill();
                            FileHandler.WRAQLogWrite(player,StringUtils.LogsType.MobKill,"击杀了:" + monster.getName().getString());
                            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSoundPacket);
                            Compute.Drops(player, monster, monster.getItemBySlot(EquipmentSlot.HEAD).getItem(), player.getItemInHand(InteractionHand.MAIN_HAND));
                            /*if ((data.contains(StringUtils.DamageTypes.IsAttack) && data.getBoolean(StringUtils.DamageTypes.IsAttack))) // 近战攻击专属增益*/
                            HurtEventModule.VolcanoRune0Judge(player);
                            HurtEventModule.SwordSkill2(data,player); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                            HurtEventModule.BowSkill2(data,player); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                            HurtEventModule.ManaSkill2(data,player); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）
                            MyMissionBow.CountAdd(player,monster);
                            YxwgCurios2.Passive1(player);
                            QiFuCurios1.Passive2(player);
                            ZuoSiCurios.KillMob(player,monster);
                        }
                    }

                    if (Hurter instanceof Player hurter) {
                        event.setCanceled(true);
/*                        if (Attacker.level().equals(Attacker.getServer().getLevel(Level.OVERWORLD))
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

                                Compute.ChargingModule(dataA,attacker); // 盈能攻击
                                Compute.ChargingModule(dataH,hurter); // 盈能攻击

                                if (dataA.getBoolean(StringUtils.DamageTypes.Crit)) {
                                    AttackEventModule.SwordSkill5Attack(dataA,attacker); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
                                }

                                if (damageAfterComputeShield > 0) {
                                    hurter.setHealth((float) (hurter.getHealth() - damageAfterComputeShield));
                                    attacker.heal((float) (damageAfterComputeShield * PlayerAttributes.PlayerHealSteal(attacker) * (1 + PlayerAttributes.PlayerHealEffectUp(attacker))));
                                    if (damageAfterComputeShield >= hurter.getHealth()) {
                                        AttackEventModule.KillPositiveEffect(attacker);
                                        if (hurter.getHealth() < damageAfterComputeShield && hurter.isDeadOrDying()) {
                                            Compute.FormatBroad(hurter.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                                    Component.literal(hurter.getName().getString() + "被").withStyle(ChatFormatting.WHITE).
                                                            append(attacker.getName()).withStyle(ChatFormatting.WHITE).
                                                            append(Component.literal("使用").withStyle(ChatFormatting.WHITE)).
                                                            append(attacker.getItemInHand(InteractionHand.MAIN_HAND).getDisplayName()).
                                                            append(Component.literal("重伤。").withStyle(ChatFormatting.WHITE)));
                                        }
                                        hurter.setLastHurtByPlayer(attacker);
                                        HurtEventModule.VolcanoRune0Judge(attacker);
                                        HurtEventModule.SwordSkill2(dataA,attacker); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                                        HurtEventModule.BowSkill2(dataA,attacker); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                                        HurtEventModule.ManaSkill2(dataA,attacker); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）
                                    }
                                }

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
                        }*/
                    }
                }
            }
        }
    }
    public static void BlazeReflectDamage (Mob monster, Player player) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorBlaze.get())
                || monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorBlaze.get())) {
            Compute.Damage.ManaDamageToPlayer(monster,player,player.getMaxHealth() * 0.02f);
        }
    }
}