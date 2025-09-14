package fun.wraq.events.modules;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.SceptreAttribute;
import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BowSkillStruct.BowSkill3;
import fun.wraq.common.util.struct.BowSkillStruct.BowSkill6;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill13;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill3;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill6;
import fun.wraq.core.AttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.ChargedClearS2CPacket;
import fun.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.series.overworld.chapter1.mine.MineSword;
import fun.wraq.series.overworld.chapter1.snow.SnowSword;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.E;
import static java.lang.Math.log;

public class AttackEventModule {
    public static double SwordSKillEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        return Math.max(0, (data.getInt(StringUtils.SkillArray[0]) - 45) * 0.0033);
    }

    public static double BowSKillEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        return Math.max(0, (data.getInt(StringUtils.SkillArray[1]) - 45) * 0.0033);
    }

    public static double ManaSKillEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        return Math.max(0, (data.getInt(StringUtils.SkillArray[2]) - 45) * 0.005);
    }

    public static void MineSwordAndSnowSwordSlowDownForce(Item item, Mob monster) {
        if (item instanceof MineSword || item instanceof SnowSword) {
            if ((item instanceof MineSword mineSword && mineSword.getWraqTier() == 3) || item instanceof SnowSword) {
                Compute.addSlowDownEffect(monster, 40, 1);
            }
            else {
                Compute.addSlowDownEffect(monster, 40, 2);
            }
            monster.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(), 40), serverPlayer));
        }
    }

    public static double ToPlayerBlackForest(CompoundTag data, Player player) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0"))
            return player.getHealth() * 0.09f;
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3"))
            return player.getHealth() * 0.12f;
        return 0;
    }

    public static void ToPlayerBlackForestGet2(CompoundTag data, Player hurter, double CHitDamage, double BreakDefence0, double BreakDefense, double Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0"))
            data.putDouble("BlackForestExDamage", hurter.getHealth() * 0.09f * (1.0d + CHitDamage) * (1.0d - (0.25F * log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3"))
            data.putDouble("BlackForestExDamage", hurter.getHealth() * 0.12f * (1.0d + CHitDamage) * (1.0d - (0.25F * log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
    }

    public static double ToPlayerSeaSword(CompoundTag data, Player hurter) {
        if (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) {
            data.putDouble("SeaSwordExDamage", (hurter.getMaxHealth() - hurter.getHealth()) * 0.09f);
            return (hurter.getMaxHealth() - hurter.getHealth()) * 0.09f;
        }
        if (data.contains("SeaSword3") && data.getBoolean("SeaSword3")) {
            data.putDouble("SeaSwordExDamage", (hurter.getMaxHealth() - hurter.getHealth()) * 0.16f);
            return (hurter.getMaxHealth() - hurter.getHealth()) * 0.16f;
        }
        return 0;
    }

    public static double ToPlayerVolcanoRune2(CompoundTag data, Player hurter) {
        if (data.contains("volcanoRune2") && data.getInt("volcanoRune2") == 2) {
            return hurter.getMaxHealth() * 0.04f;
        }
        return 0;
    }

    public static double ToPlayerForestRune3(CompoundTag data, Player hurter) {
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0)
            return hurter.getMaxHealth() * 0.1f;
        return 0;
    }

    public static void ToPlayerBlackForestGet4(CompoundTag data, Player hurter, double CHitDamage, double BreakDefence0, double BreakDefense, double Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0"))
            data.putDouble("BlackForestExDamage", hurter.getHealth() * 0.09f * (1.0d - (0.25F * log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3"))
            data.putDouble("BlackForestExDamage", hurter.getHealth() * 0.12f * (1.0d - (0.25F * log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
    }

    public static double SwordSkill0(CompoundTag data, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
        ExDamageIgnoreDefence += BaseDamage * Compute.getSwordSkillLevel(data, 0) * 0.01;
        return ExDamageIgnoreDefence;
    }

    public static void SwordSkill3Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 3) > 0) {
            if (Utils.SwordSkill3Map.containsKey(name)) {
                SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(name);
                if (swordSkill3.getTime() > TickCount && swordSkill3.getEntity().equals(entity)) {
                    if (swordSkill3.getCount() < 10) swordSkill3.setCount(swordSkill3.getCount() + 1);
                    swordSkill3.setTime(TickCount + 200);
                } else {
                    swordSkill3.setEntity(entity);
                    swordSkill3.setCount(1);
                    swordSkill3.setTime(TickCount + 200);
                }
            } else {
                SwordSkill3 swordSkill3 = new SwordSkill3(entity, 1, TickCount + 200);
                Utils.SwordSkill3Map.put(name, swordSkill3);
            }
            SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(name);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10, swordSkill3.getCount(), 0), (ServerPlayer) player);
        }
    }

    public static double SwordSkill3(CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 3) > 0 && Utils.SwordSkill3Map.containsKey(name)) {
            SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(name);
            if (swordSkill3.getEntity().equals(monster) && swordSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.getSwordSkillLevel(data, 3) * 0.002 * swordSkill3.getCount();
            }
        }
        return DamageEnhance;
    }

    public static void SwordSkill5Attack(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        if (Compute.getSwordSkillLevel(data, 5) > 0) {
            data.putInt(StringUtils.SwordSkillNum.Skill5, TickCount + 60);
            ModNetworking.sendToClient(new SkillImageS2CPacket(6, 3, 3, 0, 0), (ServerPlayer) player);
        }
    }

    public static void SwordSkill6Attack(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 6) > 0) {
            if (data.getBoolean("Crit")) {
                if (Utils.SwordSkill6Map.containsKey(name)) {
                    SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(name);
                    if (swordSkill6.getTime() > TickCount) {
                        if (swordSkill6.getCount() < 10) swordSkill6.setCount(swordSkill6.getCount() + 1);
                        swordSkill6.setTime(TickCount + 200);
                    } else {
                        swordSkill6.setTime(TickCount + 200);
                        swordSkill6.setCount(1);
                    }
                } else {
                    SwordSkill6 swordSkill6 = new SwordSkill6(1, TickCount + 200);
                    Utils.SwordSkill6Map.put(name, swordSkill6);
                }
                SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(name);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 10, 10, swordSkill6.getCount(), 0), (ServerPlayer) player);
            } else {
                if (Utils.SwordSkill6Map.containsKey(name)) {
                    SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(name);
                    swordSkill6.setCount(0);
                } else {
                    SwordSkill6 swordSkill6 = new SwordSkill6(0, 0);
                    Utils.SwordSkill6Map.put(name, swordSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 0, 0, 0, 0), (ServerPlayer) player);

            }
        }
    }

    public static void SwordSkill13Attack(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 13) > 0) {
            if (!Utils.SwordSkill13Map.containsKey(name)) {
                if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
                    SwordSkill13 swordSkill13 = new SwordSkill13(2, TickCount + 120);
                    Utils.SwordSkill13Map.put(name, swordSkill13);
                } else {
                    SwordSkill13 swordSkill13 = new SwordSkill13(1, TickCount + 120);
                    Utils.SwordSkill13Map.put(name, swordSkill13);
                }
            } else {
                SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(name);
                if (swordSkill13.getTime() > TickCount) {
                    if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
                        swordSkill13.setCount(swordSkill13.getCount() + 2);
                        swordSkill13.setTime(TickCount + 120);
                    } else {
                        swordSkill13.setCount(swordSkill13.getCount() + 1);
                        swordSkill13.setTime(TickCount + 120);
                    }
                } else {
                    if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
                        swordSkill13 = new SwordSkill13(2, TickCount + 120);
                        Utils.SwordSkill13Map.put(name, swordSkill13);
                    } else {
                        swordSkill13 = new SwordSkill13(1, TickCount + 120);
                        Utils.SwordSkill13Map.put(name, swordSkill13);
                    }
                }
                if (swordSkill13.getCount() > 12) {
                    swordSkill13.setCount(12);
                }
            }
            SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(name);
            ModNetworking.sendToClient(new SkillImageS2CPacket(15, 6, 6, swordSkill13.getCount(), 0), (ServerPlayer) player);
        }
    }

    public static double SwordSkill13(CompoundTag data, Player player, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 13) > 0) {
            if (Utils.SwordSkill13Map.containsKey(name)) {
                SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(name);
                if (swordSkill13.getTime() > TickCount) {
                    ExDamageIgnoreDefence += BaseDamage * swordSkill13.getCount() * 0.0025 * Compute.getSwordSkillLevel(data, 13);
                    Compute.playerHeal(player, (BaseDamage * swordSkill13.getCount() * 0.0025 * 0.01 * Compute.getSwordSkillLevel(data, 13)));
                }
            }
        }
        return ExDamageIgnoreDefence;
    }

    public static double SwordSkill14(CompoundTag data, Player player, double BaseDamage, LivingEntity monster) {
        double ExDamageIgnoreDefence = 0;
        if (Compute.getSwordSkillLevel(data, 14) > 0) {
            double PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            double MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate > MonsterHealthRate) {
                ExDamageIgnoreDefence += BaseDamage * 0.2 * Math.min(1.0, (PlayerHealthRate - MonsterHealthRate) / 0.66);
            } else return 0;
        }
        return ExDamageIgnoreDefence;
    }

    public static double BowSkill0(CompoundTag data, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
        if (Compute.getBowSkillLevel(data, 0) > 0) {
            ExDamageIgnoreDefence += BaseDamage * 0.01 * Compute.getBowSkillLevel(data, 0);
        }
        return ExDamageIgnoreDefence;
    }

    public static void BowSkill3Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getBowSkillLevel(data, 3) > 0) {
            if (Utils.BowSkill3Map.containsKey(name)) {
                BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(name);
                if (bowSkill3.getTime() > TickCount && bowSkill3.getEntity().equals(entity)) {
                    if (bowSkill3.getCount() < 3) bowSkill3.setCount(bowSkill3.getCount() + 1);
                    bowSkill3.setTime(TickCount + 200);
                } else {
                    bowSkill3.setEntity(entity);
                    bowSkill3.setCount(1);
                    bowSkill3.setTime(TickCount + 200);
                }
            } else {
                BowSkill3 bowSkill3 = new BowSkill3(entity, 1, TickCount + 200);
                Utils.BowSkill3Map.put(name, bowSkill3);
            }
            BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(name);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10, bowSkill3.getCount(), 1), (ServerPlayer) player);
        }
    }

    public static double BowSkill3(CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getBowSkillLevel(data, 3) > 0 && Utils.BowSkill3Map.containsKey(name)) {
            BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(name);
            if (bowSkill3.getEntity().equals(monster) && bowSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.getBowSkillLevel(data, 3) * 0.033 / 5 * bowSkill3.getCount();
            }
        }
        return DamageEnhance;
    }

    public static double NetherBowDamageEnhance(Entity Arrow, CompoundTag dataArrow, LivingEntity hurter) {
        double EnhanceDamage = 1;
        if (Arrow.getPersistentData().contains("IsNetherBow") && Arrow.getPersistentData().getBoolean("IsNetherBow")) {
            Vec3 ShootPos = new Vec3(dataArrow.getDouble("PosX"), dataArrow.getDouble("PosY"), dataArrow.getDouble("PosZ"));
            Vec3 TargetPos = new Vec3(hurter.getX(), hurter.getY(), hurter.getZ());
            double Distance = ShootPos.distanceTo(TargetPos);
            if (Distance > 100) Distance = 100;
            EnhanceDamage = (1.5 + Distance / 100.0);
        }
        return EnhanceDamage;
    }

    public static void BowSkill5(CompoundTag data, Player player) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.getBowSkillLevel(data, 5) > 0) {
            data.putInt(StringUtils.BowSkillNum.Skill5, TickCount + 100);
            ModNetworking.sendToClient(new SkillImageS2CPacket(6, 5, 5, 0, 1), (ServerPlayer) player);
        }
    }

    public static void BowSkill6Attack(CompoundTag data, Player player, boolean flag) {
        int TickCount = Tick.get();
        String name = player.getName().getString();
        if (Compute.getBowSkillLevel(data, 6) > 0) {
            if (flag) {
                if (Utils.BowSkill6Map.containsKey(name)) {
                    BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(name);
                    if (bowSkill6.getTime() > TickCount) {
                        if (bowSkill6.getCount() < 3) bowSkill6.setCount(bowSkill6.getCount() + 1);
                        bowSkill6.setTime(TickCount + 200);
                    } else {
                        bowSkill6.setTime(TickCount + 200);
                        bowSkill6.setCount(1);
                    }
                } else {
                    BowSkill6 bowSkill6 = new BowSkill6(1, TickCount + 200);
                    Utils.BowSkill6Map.put(name, bowSkill6);
                }
                BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(name);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 10, 10, bowSkill6.getCount(), 1), (ServerPlayer) player);
            } else {
                if (Utils.BowSkill6Map.containsKey(name)) {
                    BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(name);
                    bowSkill6.setCount(0);
                } else {
                    BowSkill6 bowSkill6 = new BowSkill6(0, 0);
                    Utils.BowSkill6Map.put(name, bowSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7, 0, 0, 0, 1), (ServerPlayer) player);
            }
        }
    }

    public static double SwordSkill12(CompoundTag data, Player player, double BaseDamage) {
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 12) > 0 && Utils.SwordSkill12.containsKey(name)
                && Utils.SwordSkill12.get(name)) {
            return BaseDamage * Compute.getSwordSkillLevel(data, 12) * 0.4;
        }
        return 0;
    }

    public static void SwordSkill12Attack(CompoundTag data, Player player) {
        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 12) > 0 && Utils.SwordSkill12.containsKey(name)
                && Utils.SwordSkill12.get(name)) {
            Level level = player.level();
            Random random = new Random();
            Compute.getNearMob(player, 6).forEach(mob -> {
                AttackEvent.attackToMonster(mob, player, 0.2f * Compute.getSwordSkillLevel(data, 12),
                        true, AttackEvent.crit(player, mob, false));
                if (random.nextInt(0, 1) == 0) {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket
                            = new ClientboundLevelParticlesPacket(ModParticles.BLADE0.get(), true,
                            mob.getX(), mob.getY() + 1, mob.getZ(), 0, 0, 0, 0, 0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.connection.send(clientboundLevelParticlesPacket);
                    });
                } else {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket
                            = new ClientboundLevelParticlesPacket(ModParticles.BLADE1.get(), true,
                            mob.getX(), mob.getY() + 1, mob.getZ(), 0, 0, 0, 0, 0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.connection.send(clientboundLevelParticlesPacket);
                    });
                }
            });
            Utils.SwordSkill12.put(name, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(0), (ServerPlayer) player);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_SWEEP), SoundSource.PLAYERS, player.getX(), player.getY() + 1, player.getZ(), 1, 1, 0);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));
        }
    }

    public static double BowSkill12(CompoundTag data, Player player, double BaseDamage) {
        String name = player.getName().getString();
        if (Compute.getBowSkillLevel(data, 12) > 0 && Utils.BowSkill12.containsKey(name)
                && Utils.BowSkill12.get(name)) {
            return BaseDamage * Compute.getBowSkillLevel(data, 12) * 0.4;
        }
        return 0;
    }

    public static void BowSkill12Attack(CompoundTag data, Player player) {
        String name = player.getName().getString();
        if (Compute.getBowSkillLevel(data, 12) > 0 && Utils.BowSkill12.containsKey(name)
                && Utils.BowSkill12.get(name)) {
            Random random = new Random();
            Compute.getNearMob(player, 6).forEach(mob -> {
                if (random.nextDouble() < PlayerAttributes.critRate(player)) {
                    Damage.causeAttackDamageToMonster_RateAdDamage(player, mob,
                            0.8 * Compute.getBowSkillLevel(data, 12)
                                    * (1 + PlayerAttributes.critDamage(player)));
                } else {
                    Damage.causeAttackDamageToMonster_RateAdDamage(player, mob,
                            0.8 * Compute.getBowSkillLevel(data, 12));
                }
            });
            Utils.BowSkill12.put(name, false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(1), (ServerPlayer) player);
        }
    }

    public static void SnowArmorEffect(Player player, Mob monster) {
        if (SuitCount.getSnowSuitCount(player) >= 4) {
            monster.setDeltaMovement(0, 0, 0);
            Compute.addSlowDownEffect(monster, 5, 1);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(), 5), serverPlayer));
        }
    }

    public static double NetherBow(Player player, Mob monster) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.NETHER_BOW.get()))
            return Math.min(1, monster.distanceTo(player) / 100);
        return 0;
    }

    public static double MineShield(Player player) {
        if (Utils.shieldTag.containsKey(player.getItemInHand(InteractionHand.OFF_HAND).getItem()))
            return 0.75 * (1 - (100 / (100 + PlayerAttributes.defence(player))));
        else return 0;
    }

    public static double IceArmorDamageEnhance(Player player, Mob mob) {
        if (SuitCount.getIceSuitCount(player) > 0 && mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            return SuitCount.getIceSuitCount(player) * 0.15;
        }
        return 0;
    }

    public static double SoulSwordActive(Player player) {
        if (Utils.SoulSwordMap.containsKey(player) && Utils.SoulSwordMap.get(player)) {
            Utils.SoulSwordMap.put(player, false);
            Compute.sendEffectLastTime(player, ModItems.SOUL_SWORD.get().getDefaultInstance(), 0);
            return 0.5;
        }
        return 0;
    }

    public static void snowShieldEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.SNOW_SHIELD.get())) {
            int TickCount = Tick.get();
            Utils.SnowShieldPlayerEffectTickMap.put(player, TickCount + 40);
            Utils.SnowShieldPlayerEffectMap.put(player, (MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.defence) / 4));
            Utils.SnowShieldMobEffectMap.put(mob, TickCount + 40);
            Compute.addDefenceDecreaseEffectParticle(mob, 40);
            Compute.sendEffectLastTime(player, ModItems.SNOW_SOUL.get().getDefaultInstance(), 40);
             Compute.sendMobEffectHudToNearPlayer(mob, ModItems.SNOW_SHIELD.get(), "SnowShieldDefenceDecrease", 40, 0, false);
        }
    }

    public static void ManaKnifeHealthRecover(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MANA_KNIFE.get())) {
            Compute.playerHeal(player, PlayerAttributes.attackDamage(player) * 0.05);
        }
    }

    public static double SwordSkill5DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.getSwordSkillLevel(data, 4) > 0
                && player.getMainHandItem().getItem() instanceof SwordAttribute) {
            return Compute.getSwordSkillLevel(data, 4) * 0.03;
        }
        return 0;
    }

    public static double ManaSkill5DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.getManaSkillLevel(data, 4) > 0 && SceptreAttribute.isHandling(player)) {
            return Compute.getManaSkillLevel(data, 4) * 0.03;
        }
        return 0;
    }
}
