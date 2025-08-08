package fun.wraq.core;

import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.attribute.SameTypeModule;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.ManaSkillStruct.ManaSkill3;
import fun.wraq.common.util.struct.ManaSkillStruct.ManaSkill6;
import fun.wraq.customized.uniform.mana.ManaCurios1;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.Charging.ChargedClearS2CPacket;
import fun.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.mana.ManaNewSkillPassive0;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.passive.mixture.Mixture;
import fun.wraq.series.end.citadel.CitadelCurio;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
import fun.wraq.series.overworld.extraordinary.equip.KanupusSword;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

public class ManaAttackModule {

    public static void causeBaseAttack(Player player, Mob mob, double rate, boolean mainShoot) {
        ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, rate);
        causeBaseAttack(player, mob, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), player.level(), manaArrow, mainShoot);
    }

    public static void causeBaseAttack(Player player, Entity entity, double defencePenetration,
                                       double defencePenetration0, Level level, ManaArrow manaArrow, boolean mainShoot) {
        if (!(entity instanceof Mob mob)) {
            return;
        }
        if (entity instanceof Villager) {
            return;
        }
        if (player == null) {
            return;
        }
        if (SpecialEffectOnPlayer.inBlind(player)) {
            Compute.summonValueItemEntity(mob.level(), player, mob,
                    Te.s("未命中", CustomStyle.styleOfEnd), 1);
            return;
        }
        CompoundTag data = player.getPersistentData();
        Utils.PlayerFireWorkFightCoolDown.put(player, Tick.get() + 200);

        double rate = manaArrow.rate;
        rate += DamageInfluence.getPlayerNormalAttackBaseDamageEnhance(player, 2);
        double damage = PlayerAttributes.manaDamage(player) * rate;
        double defence = MobAttributes.manaDefence(mob);
        double exDamage = 0;
        double trueDamage = 0;
        double healthSteal = PlayerAttributes.manaHealthSteal(player);

        exDamage += ManaSkill12(data, player, damage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
        exDamage += BlackForestCore(player, mob); // 收割魔核
        exDamage += EarthManaArmor(player, mob); // 地蕴魔法被动

        trueDamage += Compute.getManaSkillLevel(data, 0) * damage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
        trueDamage += ManaSKill6(data, player, damage); // 完美（持续命中目标，将至多造成50%额外真实伤害）
        trueDamage += SeaCore(player, mob); // 救赎魔核
        trueDamage += SakuraCoreExIgnoreDefenceDamage(player); // 樱妖魔核
        trueDamage += CastleManaArmor.ExIgnoreDefenceDamage(player);

        if (Compute.getManaSkillLevel(data, 5) > 0 && player.getHealth() / player.getMaxHealth() < 0.8) {
            trueDamage += damage * 0.02 * Compute.getManaSkillLevel(data, 5);
        } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）

        double damageEnhance = 0; // 乘区0
        damageEnhance += SakuraCoreDecreaseDamage(player); // 樱妖魔核
        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        damageEnhance += IceInstance.IceKnightHealthManaDamageFix(mob); // 冰霜骑士伤害修正
        damageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        double NormalAttackDamageEnhance = 0;
        NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalManaAttackDamageEnhance(player); // 普通法球攻击伤害提升
        Random random = new Random();
        boolean isCrit = random.nextDouble(1) < PlayerAttributes.critRate(player);
        data.putBoolean(StringUtils.DamageTypes.Crit, isCrit);

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("---NormalManaAttack---"));
            player.sendSystemMessage(Component.literal("baseDamage : " + damage));
            player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
            player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + trueDamage));
        }

        if (isCrit) {
            damage *= PlayerAttributes.critDamage(player);
        }

        damage *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance);
        exDamage *= (1 + damageEnhance);
        trueDamage *= (1 + damageEnhance);
        damage += exDamage;
        // final damage enhance
        damage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        trueDamage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        // defence compute
        damage *= Damage.defenceDamageDecreaseRate(player, mob, defence, defencePenetration, defencePenetration0);
        // total damage
        damage *= DamageInfluence.getPlayerTotalDamageRate(player);
        trueDamage *= DamageInfluence.getPlayerTotalDamageRate(player);
        // livingEntity control
        damage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        trueDamage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        String elementType = "";
        Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
        elementType = playerUnit.type();
        if (playerUnit.value() > 0) {
            ElementDamageEffect = Element.ElementEffectAddToEntity(player, mob, playerUnit.type(), playerUnit.value(), false, damage + trueDamage);
        }

        double elementDamage = (damage + trueDamage) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

        damage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        trueDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        // final damage
        Damage.beforeCauseDamage(player, mob, damage + trueDamage);
        Damage.causeDirectDamageToMob(player, mob, damage + trueDamage);
        // health steal
        Compute.healByHealthSteal(player, mob, damage);

        // display
        if (isCrit)
            Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(CustomStyle.styleOfEntropy), 1);
        else
            Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(CustomStyle.styleOfMana), 1);

        if (elementDamage != 0 && !elementType.isEmpty())
            Compute.damageActionBarPacketSend(player, damage, trueDamage, true, isCrit, elementType, elementDamage);
        else Compute.damageActionBarPacketSend(player, damage, trueDamage, true, isCrit);

        // effect
        ManaSkill3Attack(data, player, mob); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
        ManaSkill6Attack(data, player, true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
        ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
        SakuraCore(player); // 樱妖魔核

        Compute.getNearMob(mob, 2).forEach(eachMob -> {
            if (eachMob != mob) {
                Damage.causeRateApDamageToMonster(player, eachMob, 0.5f, true);
            }
        });

        Compute.ChargingModule(data, player);
        Compute.manaDamageExEffect(player, mob, damage);
        SameTypeModule.onNormalAttackHitMob(player, mob, 1, damage + trueDamage);

        ManaCurios1.ManaDamageExTrueDamage(player, mob, damage);

        if (mainShoot) {
            OnHitEffectEquip.hit(player, mob);
            OnHitEffectCurios.hit(player, mob);
            OnHitEffectPassiveEquip.hit(player, mob);
            EnhanceNormalAttackModifier.onHitEffect(player, mob, 2);
            if (manaArrow.manaArrowHitEntity != null) {
                manaArrow.manaArrowHitEntity.onHit(manaArrow, entity);
            }
            CitadelCurio.onNormalAttackOrSkillHit(player, mob, damage + trueDamage, false);
            Mixture.onReleaseNormalAttackOrHit(player);
            Compute.additionEffects(player, mob, damage + trueDamage, 1);
        }

        ManaNewSkillPassive0.onManaArrowHit(player, mob);

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,mob) : "
                    + DamageInfluence.getPlayerFinalDamageEnhance(player, mob)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : "
                    + Damage.defenceDamageDecreaseRate(player, mob, defence, defencePenetration, defencePenetration0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + trueDamage)));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }

        if (player.getMainHandItem().getItem() instanceof KanupusSword) {
            AttackEvent.attackToMonster(mob, player, 0.5, true, false);
        }
    }

    public static void ManaSkill3Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.getManaSkillLevel(data, 3) > 0) {
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

    public static double getManaSkill3DamageEnhance(Player player, LivingEntity mob) {
        double DamageEnhance = 0;
        CompoundTag data = player.getPersistentData();
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.getManaSkillLevel(data, 3) > 0 && Utils.ManaSkill3Map.containsKey(name)) {
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(name);
            if (manaSkill3.getEntity().equals(mob) && manaSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.getManaSkillLevel(data, 3) * 0.04 * manaSkill3.getCount();
            }
        }
        return DamageEnhance;
    }

    public static void ManaSkill6Attack(CompoundTag data, Player player, boolean flag) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.getManaSkillLevel(data, 6) > 0) {
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
        if (Compute.getManaSkillLevel(data, 12) > 0 && Utils.ManaSkill12.containsKey(name)
                && Utils.ManaSkill12.get(name)) {
            return BaseDamage * Compute.getManaSkillLevel(data, 12) * 0.4;
        }
        return 0;
    }

    public static void ManaSkill12Attack(CompoundTag data, Player player) {
        String name = player.getName().getString();
        if (Compute.getManaSkillLevel(data, 12) > 0 && Utils.ManaSkill12.containsKey(name)
                && Utils.ManaSkill12.get(name)) {
            Random random = new Random();
            Compute.getNearMob(player, 6).forEach(mob -> {
                if (random.nextDouble() < PlayerAttributes.critRate(player)) {
                    Damage.causeRateApDamageToMonster(player, mob, Compute.getManaSkillLevel(data, 12) * PlayerAttributes.critDamage(player), false);
                } else {
                    Damage.causeRateApDamageToMonster(player, mob, Compute.getManaSkillLevel(data, 12), false);
                }
            });
            Utils.ManaSkill12.put(name, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(2), (ServerPlayer) player);

            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());

        }
    }

    public static double ManaSKill6(CompoundTag data, Player player, double BaseDamage) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        String name = player.getName().getString();
        if (Compute.getManaSkillLevel(data, 6) > 0 && Utils.ManaSkill6Map.containsKey(name) && Utils.ManaSkill6Map.get(name).getTime() > TickCount) {
            return Compute.getManaSkillLevel(data, 6) * 0.0066f * BaseDamage * Utils.ManaSkill6Map.get(name).getCount();
        }
        return 0;
    }

    public static double ManaSkill4(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Utils.sceptreTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()))
            return Compute.getManaSkillLevel(data, 4) * 0.03;
        return 0;
    }

    public static double SeaCore(Player player, Mob mob) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SeaCore)) {
            double Rate = (1 - mob.getHealth() / mob.getMaxHealth());
            return PlayerAttributes.manaDamage(player) * Rate;
        }
        return 0;
    }

    public static double BlackForestCore(Player player, Mob mob) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.BlackForestCore)) {
            double Rate = mob.getHealth() / mob.getMaxHealth();
            return PlayerAttributes.manaDamage(player) * Rate;
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

    public static double EarthManaArmor(Player player, Mob mob) {
        if (SuitCount.getEarthManaSuitCount(player) > 0) {
            return PlayerAttributes.manaDamage(player) * mob.getHealth() * 0.25 * SuitCount.getEarthManaSuitCount(player) / mob.getMaxHealth();
        }
        return 0;
    }
}
