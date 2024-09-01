package com.very.wraq.core;

import com.very.wraq.commands.stable.players.DebugCommand;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.MySound;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.very.wraq.common.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.DamageInfluence;
import com.very.wraq.common.attributeValues.MobAttributes;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.attributeValues.SameTypeModule;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.ChargedClearS2CPacket;
import com.very.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.mana.NewArrowMagma;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.mixture.WraqMixture;
import com.very.wraq.series.instance.series.castle.CastleManaArmor;
import com.very.wraq.series.instance.series.ice.IceBook;
import com.very.wraq.series.instance.series.moon.Equip.MoonBook;
import com.very.wraq.series.instance.series.moon.Equip.MoonSceptre;
import com.very.wraq.series.instance.series.moon.MoonCurios;
import com.very.wraq.series.newrunes.chapter1.VolcanoNewRune;
import com.very.wraq.series.overworld.castle.TreeBracelet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.E;
import static java.lang.Math.log;

public class ManaAttackModule {
    public static void BasicAttack(Player player, Entity entity, double rate) {
        ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, player.level());
        BasicAttack(player, entity, PlayerAttributes.manaDamage(player) * rate,
                PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), player.level(), manaArrow);
    }


    public static void BasicAttack(Player player, Entity entity, double baseDamage, double DefencePenetration, double DefencePenetration0, Level level, Entity Arrow) {
        if (player == null) return;
        CompoundTag data = player.getPersistentData();

        if (entity instanceof Mob monster && !(entity instanceof Villager)) {

            Utils.PlayerFireWorkFightCoolDown.put(player, player.getServer().getTickCount() + 200);
            double damage = baseDamage * DamageInfluence.getPlayerNormalAttackBaseRate(player);
            damage *= VolcanoNewRune.attackEnhance(player);
            double defence = MobAttributes.manaDefence(monster);
            double exDamage = 0;
            double damageIgnoreDefence = 0;
            double healthSteal = PlayerAttributes.manaHealthSteal(player);

            Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.sceptreTag.containsKey(mainhand)) damage = 0; //术法全析

            exDamage += ManaSkill12(data, player, baseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            exDamage += BlackForestCore(player, monster); // 收割魔核
            exDamage += EarthManaArmor(player, monster); // 地蕴魔法被动

            damageIgnoreDefence += Compute.ManaSkillLevelGet(data, 0) * baseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            damageIgnoreDefence += ManaSKill6(data, player, baseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            damageIgnoreDefence += SeaCore(player, monster); // 救赎魔核
            damageIgnoreDefence += SakuraCoreExIgnoreDefenceDamage(player); // 樱妖魔核
            damageIgnoreDefence += MoonCurios.Passive(player, monster); // 朔望馈赠
            damageIgnoreDefence += MoonBook.MoonBook(player, monster); // 尘月副手
            damageIgnoreDefence += CastleManaArmor.ExIgnoreDefenceDamage(player);

            if (Compute.ManaSkillLevelGet(data, 5) > 0 && player.getHealth() / player.getMaxHealth() < 0.8) {
                damageIgnoreDefence += baseDamage * 0.02 * Compute.ManaSkillLevelGet(data, 5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）

            double DamageEnhance = 0; // 乘区0
            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += SakuraCoreDecreaseDamage(player); // 樱妖魔核
            DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

            double NormalAttackDamageEnhance = 0;
            NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalManaAttackDamageEnhance(player); // 普通法球攻击伤害提升
            Random random = new Random();
            boolean isCrit = random.nextDouble(1) < PlayerAttributes.critRate(player);
            if (isCrit) NormalAttackDamageEnhance += ManaSkill10(player); // 力凝魔核
            data.putBoolean(StringUtils.DamageTypes.Crit, isCrit);

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("---NormalManaAttack---"));
                player.sendSystemMessage(Component.literal("Damage : " + damage));
                player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
                player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + damageIgnoreDefence));
            }

            damage *= (1 + DamageEnhance) * (1 + NormalAttackDamageEnhance);
            exDamage *= (1 + DamageEnhance);
            damageIgnoreDefence *= (1 + DamageEnhance);
            damage += exDamage;
            // final damage enhance
            damage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            damageIgnoreDefence *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            // defence compute
            damage *= Compute.manaDefenceDamageDecreaseRate(defence, DefencePenetration, DefencePenetration0);
            // total damage
            damage *= DamageInfluence.getPlayerTotalDamageRate(player);
            damageIgnoreDefence *= DamageInfluence.getPlayerTotalDamageRate(player);
            // mob control
            damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            damageIgnoreDefence *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            String elementType = "";
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.life, 0));
            elementType = playerUnit.type();
            if (playerUnit.value() > 0) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, damage + damageIgnoreDefence);
                Element.entityElementUnit.put(player.getId(), new Element.Unit(Element.life, 0));
            }

            double elementDamage = (damage + damageIgnoreDefence) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

            damage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            damageIgnoreDefence *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            // final damage
            Compute.Damage.DirectDamageToMob(player, monster, damage + damageIgnoreDefence);
            // health steal
            Compute.healByHealthSteal(player, damage * healthSteal);

            // display
            if (isCrit)
                Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(CustomStyle.styleOfEntropy), 1);
            else
                Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(CustomStyle.styleOfMana), 1);

            if (elementDamage != 0 && !elementType.isEmpty())
                Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, true, isCrit, elementType, elementDamage);
            else Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, true, isCrit);

            // effect
            PlainSceptrePassive(player);
            ManaSkill3Attack(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            ManaSkill6Attack(data, player, true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ManaSkill13Attack(data, player); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）
            SakuraCore(player); // 樱妖魔核
            MagmaPower(data, player, level, monster, Arrow);

            if (!(Arrow instanceof NewArrowMagma)) {
                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(monster.getPosition(1), 5, 5, 5));
                for (Mob mob : mobList) {
                    if (mob != monster) {
                        if (mob.getPosition(1).add(0, 1, 0).distanceTo(monster.getPosition(1).add(0, 1, 0)) <= 2) {
                            Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 0.5f, true);
                        }
                    }
                }
            }

            AttackEvent.SpringManaArmor(player, monster);
            Compute.ChargingModule(data, player);
            ManaAttackModule.IceSceptre(player, monster);
            IceBook.IceBookPassive(player, monster);
            Compute.manaDamageExEffect(player, monster, damage);
            SameTypeModule.onNormalAttackHitMob(player, monster, 1, damage + damageIgnoreDefence);
            WraqMixture.onLastTimeManaArrowHit(player);

            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, damage);
            TreeBracelet.Passive(player, monster); // 古树手镯
            MoonSceptre.Passive(player, monster); //
            MoonSceptre.MoonSceptreActive(player, monster); // 星穹玉杖
            Compute.AdditionEffects(player, monster, damage + damageIgnoreDefence, 1);
            if (mainhand instanceof OnHitEffectMainHandWeapon onHitEffectMainHandWeapon) {
                onHitEffectMainHandWeapon.onHit(player, monster);
            }

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : " + Compute.defenceDamageDecreaseRate(defence, DefencePenetration, DefencePenetration0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + damageIgnoreDefence)));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }
        }
        if (entity instanceof Player hurter) {
            double damage;
            double Defence = PlayerAttributes.manaDefence(hurter);
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double DamageEnhance = 0;
            double HealSteal = PlayerAttributes.manaHealthSteal(player);

            ExDamage += ManaSkill12(data, player, baseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）

            DamageIgnoreDefence += Compute.ManaSkillLevelGet(data, 0) * baseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            if (Compute.ManaSkillLevelGet(data, 5) > 0 && player.getHealth() / player.getMaxHealth() < 0.5) {
                DamageIgnoreDefence += baseDamage * 0.02 * Compute.ManaSkillLevelGet(data, 5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）
            DamageIgnoreDefence += ManaSKill6(data, player, baseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）

            DamageEnhance += ManaSkill3(data, player, hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.ManaSkillLevelGet(data, 4) * 0.03; // 法术专注（额外造成3%的伤害，额外受到1.5%的伤害）

            if (Defence < DefencePenetration0) Defence = 0;
            else Defence -= DefencePenetration0;

            if (Defence < 0) Defence = Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            damage = baseDamage * (1.0d - (0.25F * log(((Defence) * (1.0d - DefencePenetration)) * (E * E / 250) + 1)));
            if (ManaRune2(data, player, hurter, damage)) damage *= 3;

            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);

            Compute.Damage.DirectDamageToPlayer(player, hurter, (damage + DamageIgnoreDefence) * 0.1f);

            Compute.playerHeal(player, (damage + DamageIgnoreDefence) * HealSteal);

            ManaSkill3Attack(data, player, hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            ManaSkill6Attack(data, player, true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ManaSkill13Attack(data, player); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）

            MagmaPower(data, player, level, hurter, Arrow);

            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.getPosition(1), 5, 5, 5));
            for (Player player1 : playerList) {
                if (player1 != player && player1 != hurter) {
                    if (player1.getPosition(1).add(0, 1, 0).distanceTo(hurter.getPosition(1).add(0, 1, 0)) <= 2) {
                        Compute.Damage.manaDamageToPlayer(hurter, player1, 0.5f);
                    }
                }
            }
        }
    }

    public static boolean ManaRune2(CompoundTag data, Player player, LivingEntity monster, double damage) {
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") == 0) {
            Level level = player.level();
            data.putInt("ManaRune2", 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 200, 200, 0, 4), (ServerPlayer) player);
            monster.getPersistentData().putInt("ManaRune2", 60);
            Utils.MonsterAttributeDataProvider.add(monster);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(monster.getId(), 60), serverPlayer));

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setCause((ServerPlayer) player);
            lightningBolt.setDamage(0);
            lightningBolt.setVisualOnly(true);
            lightningBolt.moveTo(monster.position());
            lightningBolt.setSilent(true);
            level.addFreshEntity(lightningBolt);


            return true;
        }
        return false;
    }

    public static void MagmaPower(CompoundTag data, Player player, Level level, LivingEntity hurter, Entity Arrow) {
        if (Arrow instanceof NewArrowMagma) {
            Compute.MagmaPower(hurter, level, player);
        } else {
            if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
                Compute.MagmaPower(hurter, level, player);
                data.putBoolean("MagmaPower", false);
            }
        }
    }


    public static void ManaSkill3Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 3) > 0) {
            if (Utils.ManaSkill3Map.containsKey(name)) {
                ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(name);
                if (manaSkill3.getTime() > TickCount && manaSkill3.getEntity().equals(entity)) {
                    if (manaSkill3.getCount() < 5) manaSkill3.setCount(manaSkill3.getCount() + 1);
                    manaSkill3.setTime(TickCount + 200);
                } else {
                    manaSkill3.setEntity(entity);
                    manaSkill3.setCount(1);
                    manaSkill3.setTime(TickCount + 200);
                }
            } else {
                ManaSkill3 manaSkill3 = new ManaSkill3(entity, 1, TickCount + 200);
                Utils.ManaSkill3Map.put(name, manaSkill3);
            }
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(name);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10, manaSkill3.getCount(), 2), (ServerPlayer) player);
        }
    }

    public static double ManaSkill3(CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 3) > 0 && Utils.ManaSkill3Map.containsKey(name)) {
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(name);
            if (manaSkill3.getEntity().equals(monster) && manaSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.ManaSkillLevelGet(data, 3) * 0.04 * manaSkill3.getCount();
            }
        }
        return DamageEnhance;
    }

    public static void ManaSkill6Attack(CompoundTag data, Player player, boolean flag) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 6) > 0) {
            if (flag) {
                if (Utils.ManaSkill6Map.containsKey(name)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(name);
                    if (manaSkill6.getTime() > TickCount) {
                        if (manaSkill6.getCount() < 3) manaSkill6.setCount(manaSkill6.getCount() + 1);
                        manaSkill6.setTime(TickCount + 200);
                    } else {
                        manaSkill6.setTime(TickCount + 200);
                        manaSkill6.setCount(1);
                    }
                } else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(1, TickCount + 200);
                    Utils.ManaSkill6Map.put(name, manaSkill6);
                }
                ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(name);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 10, 10, manaSkill6.getCount(), 2), (ServerPlayer) player);

            } else {
                if (Utils.ManaSkill6Map.containsKey(name)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(name);
                    manaSkill6.setCount(0);
                } else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(0, 0);
                    Utils.ManaSkill6Map.put(name, manaSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 0, 0, 0, 2), (ServerPlayer) player);

            }
        }
    }

    public static double ManaSkill12(CompoundTag data, Player player, double BaseDamage) {
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 12) > 0 && Utils.ManaSkill12.containsKey(name)
                && Utils.ManaSkill12.get(name)) {
            return BaseDamage * Compute.ManaSkillLevelGet(data, 12) * 0.4;
        }
        return 0;
    }

    public static void ManaSkill12Attack(CompoundTag data, Player player) {
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 12) > 0 && Utils.ManaSkill12.containsKey(name)
                && Utils.ManaSkill12.get(name)) {
            Level level = player.level();
            Random random = new Random();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 20, 20, 20));
            for (Mob mob : mobList) {
                if (mob.position().distanceTo(player.position()) < 6) {
                    if (random.nextDouble() < PlayerAttributes.critRate(player)) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, Compute.ManaSkillLevelGet(data, 12) * PlayerAttributes.critDamage(player), false);
                    } else Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, Compute.ManaSkillLevelGet(data, 12), false);
                }
            }
            Utils.ManaSkill12.put(name, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(2), (ServerPlayer) player);

            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            MySound.SoundToAll(player, ModSounds.Nether_Power.get());

        }
    }

    public static void ManaSkill13Attack(CompoundTag data, Player player) {
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 13) > 0 && Utils.ManaSkill13.containsKey(name)
                && Utils.ManaSkill13.get(name)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 20, 20, 20));
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();

            mobList.forEach(mob -> {
                if (mob.position().distanceTo(player.position()) < 6) Count.getAndIncrement();
            });

            if (Count.get() > 10) Count.set(10);
            for (Mob mob : mobList) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 0.2 * Compute.ManaSkillLevelGet(data, 13) * Count.get(), false);
            }

            double recoverManaValue = (Compute.PlayerMaxManaNum(player) - Compute.PlayerCurrentManaNum(player)) * 0.05 * Compute.ManaSkillLevelGet(data, 13);
            Compute.playerManaAddOrCost(player, recoverManaValue);
            Utils.ManaSkill13.put(name, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(3), (ServerPlayer) player);

            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            MySound.SoundToAll(player, ModSounds.Nether_Power.get());
        }
    }

    public static double ManaSKill6(CompoundTag data, Player player, double BaseDamage) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.ManaSkillLevelGet(data, 6) > 0 && Utils.ManaSkill6Map.containsKey(name) && Utils.ManaSkill6Map.get(name).getTime() > TickCount) {
            return Compute.ManaSkillLevelGet(data, 6) * 0.0066f * BaseDamage * Utils.ManaSkill6Map.get(name).getCount();
        }
        return 0;
    }

    public static void PlainSceptrePassive(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre0.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre1.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre2.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre3.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre4.get())) {
            Compute.playerHeal(player, player.getMaxHealth() * 0.01f);
            player.getPersistentData().putInt(StringUtils.PlainSwordActive.PlainSceptre, player.getServer().getTickCount() + 100);
        }
    }

    public static double ManaSkill4(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Utils.sceptreTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()))
            return Compute.ManaSkillLevelGet(data, 4) * 0.03;
        return 0;
    }

    public static double SeaCore(Player player, Mob monster) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SeaCore)) {
            double Rate = (1 - monster.getHealth() / monster.getMaxHealth());
            return Compute.XpStrengthAPDamage(player, Rate) * 0.5;
        }
        return 0;
    }

    public static double BlackForestCore(Player player, Mob monster) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.BlackForestCore)) {
            double Rate = monster.getHealth() / monster.getMaxHealth();
            return Compute.XpStrengthAPDamage(player, Rate) * 0.5;
        }
        return 0;
    }

    public static double SakuraCoreExIgnoreDefenceDamage(Player player) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        String name = player.getName().getString();
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)
                && !Utils.playerSakuraCoreMap.getOrDefault(name, false)) {
            return PlayerAttributes.manaDamage(player);
        }
        return 0;
    }

    public static double SakuraCoreDecreaseDamage(Player player) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        String name = player.getName().getString();
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)
                && Utils.playerSakuraCoreMap.getOrDefault(name, false)) {
            Compute.playerHeal(player, PlayerAttributes.manaDamage(player) * 0.0125);
        }
        return 0;
    }

    public static void SakuraCore(Player player) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        String name = player.getName().getString();
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)) {
            if (!Utils.playerSakuraCoreMap.containsKey(name)) Utils.playerSakuraCoreMap.put(name, true);
            else Utils.playerSakuraCoreMap.put(name, !Utils.playerSakuraCoreMap.get(name));
        }
    }

    public static double ManaSkill10(Player player) {
        CompoundTag data = player.getPersistentData();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
            return PlayerAttributes.critDamage(player) * Compute.ManaSkillLevelGet(data, 10) * 0.125;
        } // 法术专精-力凝魔核
        return 0;
    }

    public static double ManaSkill10DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data, 10) > 10 && Utils.sceptreTag.containsKey(player.getMainHandItem().getItem())) {
            return PlayerAttributes.coolDownDecrease(player) * 0.15;
        }
        return 0;
    }

    public static double ManaSkill11(Player player) {
        CompoundTag data = player.getPersistentData();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
            return Compute.ManaSkillLevelGet(data, 11) * 0.1;
        } // 法术专精-术法全析
        return 0;
    }

    public static double NetherManaArmor(Player player, Mob mob) {
        return Compute.ArmorCount.NetherMana(player) * 0.12 * Math.min(1, MobAttributes.manaDefence(mob) / 500.0);
    }

    public static void IceSceptre(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceSceptre.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.DevilSceptre.get())) {
            Utils.IceSceptreEffectMap.put(player, player.getServer().getTickCount() + 40);
            Utils.IceSceptreEffectNumMap.put(player, Math.min(3000, MobAttributes.manaDefence(mob)));
            Compute.sendEffectLastTime(player, ModItems.IceSceptre.get().getDefaultInstance(), 40);
            Level level = player.level();
            if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
            }
        }
    }

    public static double EarthManaArmor(Player player, Mob mob) {
        if (Compute.ArmorCount.EarthMana(player) > 0) {
            return Compute.XpStrengthAPDamage(player, mob.getHealth() * 0.25 * Compute.ArmorCount.EarthMana(player) / mob.getMaxHealth());
        }
        return 0;
    }
}
