package com.very.wraq.events.modules;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.nether.Equip.ManaSword;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HurtEventModule {
    public static void LightingArmorJudge(Player player, Mob monster) {
        if (player.getPersistentData().contains("LightningArmor") && player.getPersistentData().getBoolean("LightningArmor")) {
            player.getPersistentData().putBoolean("LightningArmor", false);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
            lightningBolt.setDamage(0);
            lightningBolt.setSilent(true);
            lightningBolt.setVisualOnly(true);
            lightningBolt.moveTo(monster.position());
            player.level().addFreshEntity(lightningBolt);
        }
    }

    public static void SabreDamage(Player player, Mob monster) {
        CompoundTag data = player.getPersistentData();
        if (data.contains("QuartzSabreDamage") && data.getDouble("QuartzSabreDamage") > 0) {
            double QuartzSabreDamage = data.getDouble("QuartzSabreDamage");
            data.putDouble("QuartzSabreDamage", 0);
            monster.getPersistentData().putInt("QuartzSabrePos", -1);
            Compute.playerHeal(player, QuartzSabreDamage);
            if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                Compute.formatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                        Component.literal("华尔兹提供了额外伤害").append(Component.literal(String.format("%.2f", QuartzSabreDamage)).withStyle(ChatFormatting.GOLD)));
            data.putInt("QuartzSabreSpeedUp", 40);
        }
    }

    public static void ForestRune3Judge(Player player, Mob monster, double BaseDamage) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0) {
            Compute.playerHeal(player, 100 + BaseDamage * 0.1);
            data.putInt("DGreen3", 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(1, 200, 200, 0, 4), (ServerPlayer) player);
            BlockState blockState = Blocks.OAK_LEAVES.defaultBlockState();
            BlockPos blockPos = new BlockPos((int) monster.getX(), (int) (monster.getY() + 0.9), (int) monster.getZ());
            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                player.level().setBlockAndUpdate(blockPos, blockState);
                player.level().destroyBlock(blockPos, false);
            }
        }
    }

    public static void VolcanoRune2Judge(Player attacker, Player hurter) {
        CompoundTag data = attacker.getPersistentData();
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 2 && data.getBoolean("IsAttack")) {
            if (!data.contains("volcanoRune2")) data.putInt("volcanoRune2", 1);
            else data.putInt("volcanoRune2", data.getInt("volcanoRune2") + 1);
            if (data.getInt("volcanoRune2") == 3) {
                data.putInt("volcanoRune2", 0);
            }
        }
    }

    public static void ManaSwordJudge(Player attacker, Player player) {
        CompoundTag dataA = attacker.getPersistentData();
        CompoundTag dataH = player.getPersistentData();
        if (attacker.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword) {
            dataA.putInt("MANA", min(dataA.getInt("MAXMANA"),
                    dataA.getInt("MANA") + (int) (dataH.getInt("MAXMANA") * 0.1)));
            dataH.putInt("MANA", max(0,
                    dataH.getInt("MANA") - (int) (dataH.getInt("MAXMANA") * 0.1)));
            Compute.ManaStatusUpdate(attacker);
            Compute.ManaStatusUpdate(player);
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("战斗信息").withStyle(CustomStyle.styleOfNether)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(attacker.getName().getString() + "在对你的攻击中偷取了").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(String.valueOf((int) (dataH.getInt("MAXMANA") * 0.1))).withStyle(CustomStyle.styleOfMana)).
                    append(Component.literal("法力值。")));
            attacker.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("战斗信息").withStyle(CustomStyle.styleOfNether)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal("你在在本次攻击中攻击中偷取了").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(String.valueOf((int) (dataH.getInt("MAXMANA") * 0.1))).withStyle(CustomStyle.styleOfMana)).
                    append(Component.literal("法力值。")));
        }
    }

    public static void ForestRune3Judge(Player attacker, Player player) {
        CompoundTag dataA = attacker.getPersistentData();
        if (dataA.contains(StringUtils.ForestRune.ForestRune) && dataA.getInt(StringUtils.ForestRune.ForestRune) == 3 && dataA.getInt("DGreen3") == 0) {
            if (!dataA.contains("IgnoreRune") || (!dataA.getBoolean("IgnoreRune")))
                Compute.formatMSGSend(attacker, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                        Component.literal("森林符石-生长汲取").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("在本次攻击中提供了 ").withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f", player.getMaxHealth() * 0.1f)).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal(" 额外伤害并恢复了 ").withStyle(ChatFormatting.WHITE)).append(Component.literal(String.format("%.2f", player.getMaxHealth() * 0.01F)).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal(" 生命值")).withStyle(ChatFormatting.WHITE)));
            Compute.playerHeal(attacker, player.getMaxHealth() * 0.01F);
            dataA.putInt("DGreen3", 200);
            BlockState blockState = Blocks.OAK_LEAVES.defaultBlockState();
            BlockPos blockPos = new BlockPos((int) player.getX(), (int) (player.getY() + 0.9), (int) player.getZ());
            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                player.level().setBlockAndUpdate(blockPos, blockState);
                player.level().destroyBlock(blockPos, false);
            }
        }
    }

    public static void LightingArmorJudge(Player attacker, Player player) {
        int LightningArmorCount = Compute.LightningArmorCount(player);
        if (LightningArmorCount > 0) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
            lightningBolt.setDamage(0);
            lightningBolt.setSilent(true);
            lightningBolt.setVisualOnly(true);
            lightningBolt.moveTo(attacker.position());
            player.level().addFreshEntity(lightningBolt);
            attacker.setHealth((float) (attacker.getHealth() - PlayerAttributes.defence(player) * 0.5f * LightningArmorCount));
        }
    }

    public static void ForestRune1And2Judge(Player attacker, Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag dataH = player.getPersistentData();
        if (dataH.contains(StringUtils.ForestRune.ForestRune) && dataH.getInt(StringUtils.ForestRune.ForestRune) == 1) {
            attacker.setHealth(attacker.getHealth() - player.getMaxHealth() * 0.01f);
            attacker.setLastHurtByPlayer(player);
            dataH.putInt(StringUtils.ForestRune.ForestRune1, TickCount + 20);
        } else {
            if (dataH.getInt(StringUtils.ForestRune.ForestRune) == 2 && player.getHealth() < player.getMaxHealth() * 0.2 && dataH.contains(StringUtils.ForestRune.ForestRune)) {
                if (dataH.contains(StringUtils.ForestRune.ForestRune2) && dataH.getInt(StringUtils.ForestRune.ForestRune2) == 0) {
                    player.setHealth(player.getMaxHealth());
                    dataH.putInt(StringUtils.ForestRune.ForestRune2, 2400);
                    player.sendSystemMessage(Component.literal("森林符石-狂野生长 ").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已为你恢复满生命值")));
                }
            }
        }
    }

    public static void FightMSGSender(Player attacker, Player player) {
        CompoundTag dataA = player.getPersistentData();
        CompoundTag dataH = player.getPersistentData();
        if (attacker.getPersistentData().contains("Crit") && attacker.getPersistentData().getBoolean("Crit")) {
            attacker.getPersistentData().putBoolean("Crit", false);
            if (!dataH.contains("IgnoreFight") || (!dataH.getBoolean("IgnoreFight")))
                Compute.formatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                        Component.literal("对方造成了暴击！").append(Component.literal(String.format("%.2f", attacker.getPersistentData().getDouble("ToPlayerDamage"))).withStyle(ChatFormatting.GOLD)));
            if (!dataA.contains("IgnoreFight") || (!dataA.getBoolean("IgnoreFight")))
                Compute.formatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                        Component.literal("造成了暴击！").append(Component.literal(String.format("%.2f", attacker.getPersistentData().getDouble("ToPlayerDamage"))).withStyle(ChatFormatting.GOLD)));
            ModNetworking.sendToClient(new SoundsS2CPacket(0), (ServerPlayer) attacker);
            ModNetworking.sendToClient(new SoundsS2CPacket(0), (ServerPlayer) player);
        }
        if (attacker.getPersistentData().contains("Crit") && !attacker.getPersistentData().getBoolean("Crit")) {
            ModNetworking.sendToClient(new SoundsS2CPacket(1), (ServerPlayer) attacker);
            ModNetworking.sendToClient(new SoundsS2CPacket(1), (ServerPlayer) player);
        }
    }

    public static boolean PlainRune3Judge(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains("Green") && data.getInt("Green") == 3 && data.contains("Green3")) {
            if (data.getInt("Green3") == 200) {
                player.sendSystemMessage(Component.literal("平原的加护为你抵御了一次伤害！").withStyle(ChatFormatting.GREEN));
                data.putInt("Green3", 0);
                return true;
            }
        }
        return false;
    }

    public static void SwordSkill2(CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data, 2) > 0) {
            data.putInt(StringUtils.SwordSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 0), (ServerPlayer) player);
        }
    }

    public static void BowSkill2(CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data, 2) > 0) {
            data.putInt(StringUtils.BowSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 1), (ServerPlayer) player);

        }
    }

    public static void ManaSkill2(CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.ManaSkillLevelGet(data, 2) > 0) {
            data.putInt(StringUtils.ManaSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 2), (ServerPlayer) player);

        }
    }

    public static void ManaSkill14(CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.ManaSkillLevelGet(data, 14) > 0 && data.getInt(StringUtils.ManaSkillNum.Skill14) < TickCount) {
            data.putInt(StringUtils.ManaSkillNum.Skill14, TickCount + 200);
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

            if (Count.get() > 5) Count.set(5);
            for (Mob mob : mobList) {
                if (mob.position().distanceTo(player.position()) < 6) {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, Compute.ManaSkillLevelGet(data, 14) * Count.get() * 2, false);
                }
            }
            for (Player player1 : playerList)
                if (player1 != player)
                    Compute.Damage.manaDamageToPlayer(player, player1, Compute.ManaSkillLevelGet(data, 14) * Count.get());
            ServerPlayer serverPlayer = (ServerPlayer) player;
            Compute.playerShieldProvider(player, 100, player.getMaxHealth() * 0.01 * Count.get() * Compute.ManaSkillLevelGet(data, 14));
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 100, ParticleTypes.WITCH);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5, 6, 100, ParticleTypes.WITCH);

            Compute.SoundToAll(player, ModSounds.Nether_Power.get());
            ModNetworking.sendToClient(new SkillImageS2CPacket(15, 10, 10, 0, 2), (ServerPlayer) player);
        }
    }
}
