package com.very.wraq.coreAttackModule;

import com.very.wraq.customized.Customize;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdCurios;
import com.very.wraq.customized.players.sceptre.liulixian_.LiuLiXianCurios1F;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.SkillPackets.Charging.ChargedClearS2CPacket;
import com.very.wraq.netWorking.misc.SkillPackets.SkillImageS2CPacket;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.mana.NewArrowMagma;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSceptre;
import com.very.wraq.series.instance.Ice.IceBook;
import com.very.wraq.series.instance.Moon.Equip.MoonBook;
import com.very.wraq.series.instance.Moon.Equip.MoonSceptre;
import com.very.wraq.series.instance.Moon.MoonCurios;
import com.very.wraq.series.overWorld.Castle.TreeBracelet;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.very.wraq.valueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.DamageEnhances;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
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
        BasicAttack(player,entity, PlayerAttributes.PlayerManaDamage(player) * rate,
                PlayerAttributes.PlayerManaPenetration(player),
                PlayerAttributes.PlayerManaPenetration0(player),player.level(),manaArrow);
    }


    public static void BasicAttack(Player player, Entity entity, double BaseDamage, double DefencePenetration, double DefencePenetration0, Level level, Entity Arrow) {
        if (player == null) return;
        CompoundTag data = player.getPersistentData();

        if (entity instanceof Mob monster && !(entity instanceof Villager)) {

            Utils.PlayerFireWorkFightCoolDown.put(player,player.getServer().getTickCount() + 200);
            double Damage = BaseDamage;
            double Defence = Compute.MonsterManaDefence(monster);
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double HealthSteal = PlayerAttributes.PlayerManaHealthSteal(player);

            Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand)) Damage = 0; //术法全析
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) Damage *= LiuLiXianCurios1F.Skill5BaseDamage(player);

            Damage *= CgswdCurios.NormalManaAttackDamageRate(player,monster);

            ExDamage += ManaSkill12(data, player, BaseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ExDamage += BlackForestCore(player, monster); // 收割魔核
            ExDamage += EndRune1(player,BaseDamage); // 末地符石
            ExDamage += EarthManaArmor(player,monster); // 地蕴魔法被动
            ExDamage += Customize.ManaExDamage(player,monster,BaseDamage); // Customized

            DamageIgnoreDefence += AttackEventModule.VolcanoRune2(player); //火山符石-熔岩强击
            DamageIgnoreDefence += Compute.ManaSkillLevelGet(data, 0) * BaseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            DamageIgnoreDefence += ManaSKill6(data, player, BaseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            DamageIgnoreDefence += SeaCore(player, monster); // 救赎魔核
            DamageIgnoreDefence += SakuraCoreExIgnoreDefenceDamage(player); // 樱妖魔核
            DamageIgnoreDefence += MoonCurios.Passive(player,monster); // 朔望馈赠
            DamageIgnoreDefence += MoonBook.MoonBook(player,monster); // 尘月副手
            DamageIgnoreDefence += Customize.DamageIgnoreDefence(player); // Customized
            DamageIgnoreDefence += CastleManaArmor.ExIgnoreDefenceDamage(player);

            if (Compute.ManaSkillLevelGet(data, 5) > 0 && player.getHealth() / player.getMaxHealth() < 0.8) {
                DamageIgnoreDefence += BaseDamage * 0.02 * Compute.ManaSkillLevelGet(data, 5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）

            double DamageEnhance = 0; // 乘区0
            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += SakuraCoreDecreaseDamage(player); // 樱妖魔核
            DamageEnhance += DamageEnhances.PlayerCommonDamageUpOrDown(player,monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player,monster); // 下界混沌套装
            DamageEnhance += Customize.ManaDamageEnhance(player,monster); // Customized
            DamageEnhance += DamageEnhances.PlayerManaDamageEnhance(player); // 魔法伤害提升

            double NormalAttackDamageEnhance = 0;
            NormalAttackDamageEnhance += DamageEnhances.PlayerNormalManaAttackDamageEnhance(player); // 普通法球攻击伤害提升
            Random random = new Random();
            boolean IsCrit = random.nextDouble(1) < PlayerAttributes.PlayerCritRate(player);
            if (IsCrit) NormalAttackDamageEnhance += ManaSkill10(player); // 力凝魔核
            data.putBoolean(StringUtils.DamageTypes.Crit, IsCrit);

            if (ManaRune2(data, player, monster, Damage)) Damage *= 3;

            if (data.getBoolean(StringUtils.Debug)) {
                player.sendSystemMessage(Component.literal("---NormalManaAttack---"));
                player.sendSystemMessage(Component.literal("Damage : " + Damage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
                player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + DamageIgnoreDefence));
            }

            Damage *= (1 + DamageEnhance) * (1 + NormalAttackDamageEnhance);
            ExDamage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            Damage += ExDamage;
            // final damage enhance
            Damage *= (1 + DamageEnhances.PlayerFinalDamageEnhance(player,monster));
            DamageIgnoreDefence *= (1 + DamageEnhances.PlayerFinalDamageEnhance(player,monster));
            // defence compute
            Damage *= Compute.ManaDefenceDamageDecreaseRate(Defence,DefencePenetration,DefencePenetration0);
            // total damage
            Damage *= DamageEnhances.PlayerTotalDamageRate(player);
            DamageIgnoreDefence *= DamageEnhances.PlayerTotalDamageRate(player);
            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            Element.Unit playerUnit = Element.EntityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.Life, 0));
            if (playerUnit.value() > 0) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player,monster,playerUnit.type(),playerUnit.value(),false,Damage + DamageIgnoreDefence);
                Element.EntityElementUnit.put(player.getId(),new Element.Unit(Element.Life,0));
            }
            Damage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            DamageIgnoreDefence *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            // final damage
            Compute.Damage.DirectDamageToMob(player, monster, Damage + DamageIgnoreDefence);
            // health steal
            Compute.PlayerHealSteal(player, (Damage) * HealthSteal * 0.5);
            // display
            if (IsCrit) Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + DamageIgnoreDefence)).withStyle(CustomStyle.styleOfEntropy));
            else Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + DamageIgnoreDefence)).withStyle(CustomStyle.styleOfMana));
            Compute.DamageActionBarPacketSend(player,Damage,DamageIgnoreDefence,true,IsCrit);
            // effect
            PlainSceptrePassive(player);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.LiuLiXianIgnoreDefenceDamage(player,monster,Damage + DamageIgnoreDefence);
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
                            Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 0.5f,true);
                        }
                    }
                }
            }
            SnowRune0(player);
            AttackEventModule.EndRune2(player,monster); // 末地符石
            AttackEvent.SpringManaArmor(player,monster);
            Compute.ChargingModule(data,player);
            ManaAttackModule.IceSceptre(player,monster);
            IceBook.IceBookPassive(player,monster);
            CastleSceptre.ExDamage(player,monster,Damage);
            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player,monster,Damage);
            Customize.ManaNormalAttackEffect(player,monster); // Customized
            TreeBracelet.Passive(player,monster); // 古树手镯
            MoonSceptre.Passive(player,monster); //
            MoonSceptre.MoonSceptreActive(player,monster); // 星穹玉杖
            Compute.AddtionEffects(player,monster);

            if (data.getBoolean(StringUtils.Debug)) {
                player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageEnhances.PlayerFinalDamageEnhance(player,monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : " + Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (Damage + DamageIgnoreDefence)));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }

        }
        if (entity instanceof Player hurter) {
            double damage;
            double Defence = PlayerAttributes.PlayerManaDefence(hurter);
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double DamageEnhance = 0;
            double HealSteal = PlayerAttributes.PlayerManaHealthSteal(player);

            ExDamage += ManaSkill12(data, player, BaseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）

            DamageIgnoreDefence += Compute.ManaSkillLevelGet(data, 0) * BaseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            if (Compute.ManaSkillLevelGet(data, 5) > 0 && player.getHealth() / player.getMaxHealth() < 0.5) {
                DamageIgnoreDefence += BaseDamage * 0.02 * Compute.ManaSkillLevelGet(data, 5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）
            DamageIgnoreDefence += ManaSKill6(data, player, BaseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）

            DamageEnhance += ManaSkill3(data, player, hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.ManaSkillLevelGet(data, 4) * 0.03; // 法术专注（额外造成3%的伤害，额外受到1.5%的伤害）

            if (Defence < DefencePenetration0) Defence = 0;
            else Defence -= DefencePenetration0;

            if (Defence < 0) Defence = Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            damage = BaseDamage * (1.0d - (0.25F * log(((Defence) * (1.0d - DefencePenetration)) * (E * E / 250) + 1)));
            if (ManaRune2(data, player, hurter, damage)) damage *= 3;

            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);

            Compute.Damage.DirectDamageToPlayer(player, hurter, (damage + DamageIgnoreDefence) * 0.1f);

            Compute.PlayerHeal(player, (damage + DamageIgnoreDefence) * HealSteal);

            ManaSkill3Attack(data, player, hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            ManaSkill6Attack(data, player, true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ManaSkill13Attack(data, player); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）

            MagmaPower(data, player, level, hurter, Arrow);

            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.getPosition(1), 5, 5, 5));
            for (Player player1 : playerList) {
                if (player1 != player && player1 != hurter) {
                    if (player1.getPosition(1).add(0, 1, 0).distanceTo(hurter.getPosition(1).add(0, 1, 0)) <= 2) {
                        Compute.Damage.ManaDamageToPlayer(hurter, player1, 0.5f);
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

    public static void SnowRune0(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
            if (!data.contains("snowRune0") || data.getInt("snowRune0") == 0) {
                data.putInt("snowRune0", 1);
                data.putInt("snowRune0Time", 100);
            } else {
                if (data.getInt("snowRune0") < 5) {
                    data.putInt("snowRune0", data.getInt("snowRune0") + 1);
                    Compute.EffectLastTimeSend(player,ModItems.SnowRune0.get().getDefaultInstance(), 100);
                    data.putInt("snowRune0Time", 100);
                } else {
                    data.putInt("snowRune0Time", 100);
                    Compute.EffectLastTimeSend(player,ModItems.SnowRune0.get().getDefaultInstance(), 100);
                }
            }
        }
    }

    public static void ManaSkill3Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data, 3) > 0) {
            if (Utils.ManaSkill3Map.containsKey(player)) {
                ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
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
                Utils.ManaSkill3Map.put(player, manaSkill3);
            }
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10, manaSkill3.getCount(), 2), (ServerPlayer) player);
        }
    }

    public static double ManaSkill3(CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data, 3) > 0 && Utils.ManaSkill3Map.containsKey(player)) {
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
            if (manaSkill3.getEntity().equals(monster) && manaSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.ManaSkillLevelGet(data, 3) * 0.04 * manaSkill3.getCount();
            }
        }
        return DamageEnhance;
    }

    public static void ManaSkill6Attack(CompoundTag data, Player player, boolean flag) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data, 6) > 0) {
            if (flag) {
                if (Utils.ManaSkill6Map.containsKey(player)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                    if (manaSkill6.getTime() > TickCount) {
                        if (manaSkill6.getCount() < 3) manaSkill6.setCount(manaSkill6.getCount() + 1);
                        manaSkill6.setTime(TickCount + 200);
                    } else {
                        manaSkill6.setTime(TickCount + 200);
                        manaSkill6.setCount(1);
                    }
                } else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(1, TickCount + 200);
                    Utils.ManaSkill6Map.put(player, manaSkill6);
                }
                ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 10, 10, manaSkill6.getCount(), 2), (ServerPlayer) player);

            } else {
                if (Utils.ManaSkill6Map.containsKey(player)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                    manaSkill6.setCount(0);
                } else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(0, 0);
                    Utils.ManaSkill6Map.put(player, manaSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 0, 0, 0, 2), (ServerPlayer) player);

            }
        }
    }

    public static double ManaSkill12(CompoundTag data, Player player, double BaseDamage) {
        if (Compute.ManaSkillLevelGet(data, 12) > 0 && Utils.ManaSkill12.containsKey(player)
                && Utils.ManaSkill12.get(player)) {
            return BaseDamage * Compute.ManaSkillLevelGet(data, 12) * 3;
        }
        return 0;
    }

    public static void ManaSkill12Attack(CompoundTag data, Player player) {
        if (Compute.ManaSkillLevelGet(data, 12) > 0 && Utils.ManaSkill12.containsKey(player)
                && Utils.ManaSkill12.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 20, 20, 20));
            for (Mob mob : mobList) {
                if (mob.position().distanceTo(player.position()) < 6) {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 2 * Compute.ManaSkillLevelGet(data, 12),false);
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            for (Player player1 : playerList) {
                if (player1 != player && player1.position().distanceTo(player.position()) < 6) {
                    Compute.Damage.AttackDamageToPlayer_RateAdDamage(player, player1, 2 * Compute.ManaSkillLevelGet(data, 12));
                }
            }

            Utils.ManaSkill12.put(player, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(2), (ServerPlayer) player);

            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            Compute.SoundToAll(player, ModSounds.Nether_Power.get());

        }
    }

    public static void ManaSkill13Attack(CompoundTag data, Player player) {
        if (Compute.ManaSkillLevelGet(data, 13) > 0 && Utils.ManaSkill13.containsKey(player)
                && Utils.ManaSkill13.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 20, 20, 20));
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();

            mobList.forEach(mob -> {
                if (mob.position().distanceTo(player.position()) < 6) Count.getAndIncrement();
            });
            playerList.forEach(player1 -> {
                if (player1 != player && player1.position().distanceTo(player.position()) < 6) Count.getAndIncrement();
            });

            if (Count.get() > 10) Count.set(10);
            for (Mob mob : mobList) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 0.2f * Compute.ManaSkillLevelGet(data, 13) * Count.get(),false);
            }
            for (Player player1 : playerList) {
                if (player1 != player)
                    Compute.Damage.AttackDamageToPlayer_RateAdDamage(player, player1, 0.2f * Compute.ManaSkillLevelGet(data, 13) * Count.get());
            }
            Compute.PlayerManaAddOrCost(player, (data.getDouble("MAXMANA") * 0.2 * Compute.ManaSkillLevelGet(data, 13)));
            Utils.ManaSkill13.put(player, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(3), (ServerPlayer) player);

            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            Compute.SoundToAll(player, ModSounds.Nether_Power.get());
        }
    }

    public static double ManaSKill6(CompoundTag data, Player player, double BaseDamage) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data, 6) > 0 && Utils.ManaSkill6Map.containsKey(player) && Utils.ManaSkill6Map.get(player).getTime() > TickCount) {
            return Compute.ManaSkillLevelGet(data, 6) * 0.0066f * BaseDamage * Utils.ManaSkill6Map.get(player).getCount();
        }
        return 0;
    }

    public static void PlainSceptrePassive(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre0.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre1.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre2.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre3.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PlainSceptre4.get())) {
            Compute.PlayerHeal(player, player.getMaxHealth() * 0.01f);
            player.getPersistentData().putInt(StringUtils.PlainSwordActive.PlainSceptre, player.getServer().getTickCount() + 100);
        }
    }

    public static double ManaSkill4(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Utils.SceptreTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()))
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
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)
                && !Utils.playerSakuraCoreMap.getOrDefault(player, false)) {

            return PlayerAttributes.PlayerManaDamage(player);
        }
        return 0;
    }

    public static double SakuraCoreDecreaseDamage(Player player) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)
                && Utils.playerSakuraCoreMap.getOrDefault(player, false)) {
            Compute.PlayerHeal(player, PlayerAttributes.PlayerManaDamage(player) * 0.0125);
        }
        return 0;
    }

    public static void SakuraCore(Player player) {
        CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)
                && data.getString(StringUtils.ManaCore.ManaCore).equals(StringUtils.ManaCore.SakuraCore)) {
            if (!Utils.playerSakuraCoreMap.containsKey(player)) Utils.playerSakuraCoreMap.put(player, true);
            else Utils.playerSakuraCoreMap.put(player, !Utils.playerSakuraCoreMap.get(player));
        }
    }
    public static double ManaSkill10(Player player) {
        CompoundTag data = player.getPersistentData();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            return PlayerAttributes.PlayerCritDamage(player)  * Compute.ManaSkillLevelGet(data, 10) * 0.3;
        } // 法术专精-力凝魔核
        return 0;
    }

    public static double ManaSkill10DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) > 10 && Utils.SceptreTag.containsKey(player.getMainHandItem().getItem())) {
            return PlayerAttributes.PlayerCoolDownDecrease(player) * 0.15;
        }
        return 0;
    }

    public static double ManaSkill11(Player player) {
        CompoundTag data = player.getPersistentData();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            return Compute.ManaSkillLevelGet(data, 11) * 0.25;
        } // 法术专精-术法全析
        return 0;
    }

    public static double NetherManaArmor(Player player, Mob mob) {
        return Compute.ArmorCount.NetherMana(player) * 0.12 * Math.min(1, Compute.MonsterManaDefence(mob) / 500.0);
    }
    public static void IceSceptre(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceSceptre.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.DevilSceptre.get()) ) {
            Utils.IceSceptreEffectMap.put(player,player.getServer().getTickCount() + 40);
            Utils.IceSceptreEffectNumMap.put(player,Math.min(3000,Compute.MonsterManaDefence(mob)));
            Compute.EffectLastTimeSend(player,ModItems.IceSceptre.get().getDefaultInstance(),40);
            Level level = player.level();
            if (level.getBlockState(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ())).is(Blocks.AIR)) {
                level.setBlockAndUpdate(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),Blocks.ICE.defaultBlockState());
                level.destroyBlock(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),false);
            }
        }
    }
    public static double EndRune1(Player player, double BaseDamage) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        if (data.getInt(StringUtils.EndRune) == 1 && (!Utils.EndRune1CoolDown.containsKey(player)
                || Utils.EndRune1CoolDown.get(player) < TickCount)) {
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            int ManaCost = 0;
            if (Utils.ManaCost.containsKey(item)) ManaCost = Utils.ManaCost.get(item).intValue();
            if (ManaCost > 0) {
                Utils.EndRune1CoolDown.put(player,TickCount + 240);
                Compute.PlayerManaAddOrCost(player,Compute.PlayerCurrentManaNum(player));
                Compute.CoolDownTimeSend(player,ModItems.EndRune0.get().getDefaultInstance(),240);
                return BaseDamage * (Compute.PlayerCurrentManaNum(player) / ManaCost);
            }
        }
        return 0;
    }
    public static double EarthManaArmor(Player player, Mob mob) {
        if (Compute.ArmorCount.EarthMana(player) > 0) {
            return Compute.XpStrengthAPDamage(player,mob.getHealth() * 0.25 * Compute.ArmorCount.EarthMana(player) / mob.getMaxHealth());
        }
        return 0;
    }
    public static void ShangMengLiActive(Player player, boolean IsNormalAttack) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLi_Sceptre.get())) {
            if (Utils.ShangMengLiActiveCounts < 100) {
                if (IsNormalAttack) Utils.ShangMengLiActiveCounts += 4;
                else Utils.ShangMengLiActiveCounts += 7;
            }
            if (Utils.ShangMengLiActiveCounts > 100) Utils.ShangMengLiActiveCounts = 100;
            Compute.EffectLastTimeSend(player,ModItems.ShangMengLi_Sceptre1.get().getDefaultInstance(),8888,Utils.ShangMengLiActiveCounts,true);
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLiSword.get())) ShangMengLiSword.SwordAir(player,false);
    }
    public static double FengxiaojuExDamagePassive(Player player, double BaseDamage) {
        if (Utils.Fengxiaoju != null && Utils.Fengxiaoju.equals(player) && Utils.FengxiaojuCurios) {
            if (Utils.FengxiaojuAttackCount < 2) {
                Utils.FengxiaojuAttackCount ++;
                Compute.EffectLastTimeSend(player,ModItems.FengxiaojuCurios.get().getDefaultInstance(),8888,Utils.FengxiaojuAttackCount,true);
            }
            else {
                Utils.FengxiaojuAttackCount = 0;
                Compute.EffectLastTimeSend(player,ModItems.FengxiaojuCurios.get().getDefaultInstance(),8888,Utils.FengxiaojuAttackCount,true);
                return Compute.XpStrengthAPDamage(player,5);
            }
        }
        return 0;
    }
}
