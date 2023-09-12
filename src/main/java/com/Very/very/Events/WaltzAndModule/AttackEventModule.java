package com.Very.very.Events.WaltzAndModule;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedClearS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillImageS2CPacket;
import com.Very.very.Projectile.BowTest.MyArrow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.MineSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.MineSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.MineSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.MineSword3;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill6;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.*;

public class AttackEventModule {
    public static void AnimalThingsGetModule(Entity entity, Player player)
    {
        if(entity.getClass() == Cow.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(892).getDefaultInstance();
            ItemStack itemStack1 = MinecartItem.byId(818).getDefaultInstance();
            player.addItem(itemStack0);
            player.addItem(itemStack1);
        }
        if(entity.getClass() == Sheep.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(1023).getDefaultInstance();
            ItemStack itemStack1 = MinecartItem.byId(167).getDefaultInstance();
            player.addItem(itemStack0);
            player.addItem(itemStack1);
        }
        if(entity.getClass() == Chicken.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(769).getDefaultInstance();
            ItemStack itemStack1 = MinecartItem.byId(894).getDefaultInstance();
            player.addItem(itemStack0);
            player.addItem(itemStack1);
        }
        if(entity.getClass() == Pig.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(799).getDefaultInstance();
            player.addItem(itemStack0);
        }
        if(entity.getClass() == Horse.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(818).getDefaultInstance();
            player.addItem(itemStack0);
        }
        if(entity.getClass() == Rabbit.class)
        {
            ItemStack itemStack0 = MinecartItem.byId(1013).getDefaultInstance();
            ItemStack itemStack1 = MinecartItem.byId(1014).getDefaultInstance();
            player.addItem(itemStack0);
            player.addItem(itemStack1);
        }
    }
    public static void LakeSwordSpeedImproveModule(Item item, Player player, CompoundTag data)
    {
        int TickCount = player.getServer().getTickCount();
        if(item instanceof LakeSword0){
            data.putInt(StringUtils.LakeSwordSkill,TickCount + 20);
            player.addEffect(new MobEffectInstance(ModEffects.LAKESWORD.get(),20));
        }
        else
        {
            if(item instanceof LakeSword1) {
                data.putInt(StringUtils.LakeSwordSkill,TickCount + 30);
                player.addEffect(new MobEffectInstance(ModEffects.LAKESWORD.get(),30));
            }
            else
            {
                if(item instanceof LakeSword2) {
                    data.putInt(StringUtils.LakeSwordSkill,TickCount + 40);
                    player.addEffect(new MobEffectInstance(ModEffects.LAKESWORD.get(),40));
                }
                else
                {
                    if(item instanceof LakeSword3) {
                        data.putInt(StringUtils.LakeSwordSkill,TickCount + 60);
                        player.addEffect(new MobEffectInstance(ModEffects.LAKESWORD.get(),60));
                    }
                }
            }
        }
    }
    public static void MineSwordSlowDownForce(Item item, Mob monster)
    {
        if(item instanceof MineSword0) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
        else
        {
            if(item instanceof MineSword1) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
            else
            {
                if(item instanceof MineSword2) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
                else
                {
                    if(item instanceof MineSword3) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2));
                }
            }
        }
    }
    public static void MineSwordSlowDownForcePlayer(Item item, Player monster)
    {
        if(item instanceof MineSword0) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
        else
        {
            if(item instanceof MineSword1) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
            else
            {
                if(item instanceof MineSword2) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
                else
                {
                    if(item instanceof MineSword3) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2));
                }
            }
        }
    }
    public static void KillPositiveEffect(Player attacker)
    {
        CompoundTag data = attacker.getPersistentData();
        if (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) {
            attacker.heal(attacker.getMaxHealth() * 0.25f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者为你治疗了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.25f)).withStyle(Utils.styleOfSea)).
                            append("生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
        }
        if (data.contains("SeaSword3") && data.getBoolean("SeaSword3")) {
            attacker.heal(attacker.getMaxHealth() * 0.4f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者为你治疗了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.4f)).withStyle(Utils.styleOfSea)).
                            append("生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
        }
        if (data.contains("SeaSword4") && data.getBoolean("SeaSword4")) {
            attacker.heal(attacker.getMaxHealth() * 0.4f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者为你治疗了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.4f)).withStyle(Utils.styleOfSea)).
                            append("生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
        }
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            Compute.PlayerShieldProvider(attacker, 200, attacker.getMaxHealth() * 0.25f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者为你提供了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.25f)).withStyle(Utils.styleOfBlackForest)).
                            append("护盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            Compute.PlayerShieldProvider(attacker, 200, attacker.getMaxHealth() * 0.4f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者为你提供了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.4f)).withStyle(Utils.styleOfBlackForest)).
                            append("护盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        }
        if (data.contains("BlackForestSword4") && data.getBoolean("BlackForestSword4")) {
            Compute.PlayerShieldProvider(attacker, 200, attacker.getMaxHealth() * 0.4f);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))Compute.FormatMSGSend(attacker, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者为你提供了").append(Component.literal(String.format("%.2f", attacker.getMaxHealth() * 0.4f)).withStyle(Utils.styleOfBlackForest)).
                            append("护盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        }

    }
    public static void ExDamageMSG(Player player)
    {
        CompoundTag data = player.getPersistentData();
        if (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) {
            player.removeEffect(ModEffects.SEASWORD.get());
            data.putBoolean("SeaSword0", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者造成了额外伤害").append(Component.literal(String.format("%.2f", data.getFloat("SeaSwordExDamage"))).withStyle(Utils.styleOfSea)));
        }
        if (data.contains("SeaSword3") && data.getBoolean("SeaSword3")) {
            player.removeEffect(ModEffects.SEASWORD.get());
            data.putBoolean("SeaSword3", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者造成了额外伤害").append(Component.literal(String.format("%.2f", data.getFloat("SeaSwordExDamage"))).withStyle(Utils.styleOfSea)));
        }
        if (data.contains("SeaSword4") && data.getBoolean("SeaSword4")) {
            player.removeEffect(ModEffects.SEASWORD.get());
            data.putBoolean("SeaSword4", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂救赎者造成了额外伤害").append(Component.literal(String.format("%.2f", data.getFloat("SeaSwordExDamage"))).withStyle(Utils.styleOfSea)));
        }
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            player.removeEffect(ModEffects.BLACKFORESTSWORD.get());
            data.putBoolean("BlackForestSword0", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者造成了额外攻击").append(Component.literal(String.format("%.2f", data.getFloat("BlackForestExDamage"))).withStyle(Utils.styleOfBlackForest)));
            data.putFloat("BlackForestExDamage",0);
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            player.removeEffect(ModEffects.BLACKFORESTSWORD.get());
            data.putBoolean("BlackForestSword3", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者造成了额外攻击").append(Component.literal(String.format("%.2f", data.getFloat("BlackForestExDamage"))).withStyle(Utils.styleOfBlackForest)));
            data.putFloat("BlackForestExDamage",0);
        }
        if (data.contains("BlackForestSword4") && data.getBoolean("BlackForestSword4")) {
            player.removeEffect(ModEffects.BLACKFORESTSWORD.get());
            data.putBoolean("BlackForestSword4", false);
            if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                    Component.literal("灵魂收割者造成了额外攻击").append(Component.literal(String.format("%.2f", data.getFloat("BlackForestExDamage"))).withStyle(Utils.styleOfBlackForest)));
            data.putFloat("BlackForestExDamage",0);
        }
    }
    public static void SnowRune2(CompoundTag data, Mob monster, Player player, float Defence) {
        if (data.contains("snowRune") && data.getInt("snowRune") == 2 && data.contains("snowRune2") && data.getInt("snowRune2") == 0) {
            data.putInt("snowRune2",100);
            monster.getPersistentData().putInt("snowRune2Defence",30);
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,30,100,false,false));
            Utils.SnowRune2MobController.add(monster);
            BlockState blockState = Blocks.ICE.defaultBlockState();
            BlockPos blockPos = new BlockPos((int) monster.getX(),(int) (monster.getY() + 0.9),(int) monster.getZ());

            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                player.level().setBlockAndUpdate(blockPos,blockState);
                player.level().destroyBlock(blockPos,false);
            }
            if(!data.contains("IgnoreRune")||(!data.getBoolean("IgnoreRune"))) Compute.FormatMSGSend(player,Component.literal("符石").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfRune),
                    Component.literal("冰川符石-凛冬之意").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                            append(Component.literal("削减了目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.2f",Defence*0.5)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                            append(Component.literal("护甲值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        }
    }
    public static float ForestRune3(CompoundTag data, Mob monster, float BaseDamage) {
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0) {
            if (monster.getMaxHealth() * 0.1f >= BaseDamage * 10) return BaseDamage * 10;
            else return monster.getMaxHealth() * 0.1F;
        }
        else return 0;
    }
    public static float BlackForest(CompoundTag data, Mob monster, float BaseDamage) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            if (monster.getHealth() * 0.09f >= BaseDamage * 10) return BaseDamage * 10;
            return monster.getHealth() * 0.09f;
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            if (monster.getHealth() * 0.12f >= BaseDamage * 10) return BaseDamage * 10;
            return monster.getHealth() * 0.12f;
        }
        if (data.contains("BlackForestSword4") && data.getBoolean("BlackForestSword4")) {
            if (monster.getHealth() * 0.16f >= BaseDamage * 10) return BaseDamage * 10;
            return monster.getHealth() * 0.16f;
        }
        return 0;
    }
    public static void BlackForestExDamageNumGet1(CompoundTag data, Mob monster, float CHitDamage) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            data.putFloat("BlackForestExDamage",monster.getHealth() * 0.09f * (1.0F + CHitDamage));
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            data.putFloat("BlackForestExDamage",monster.getHealth() * 0.12f * (1.0F + CHitDamage));
        }
    }
    public static void BlackForestExDamageNumGet2(CompoundTag data, Mob monster, float CHitDamage, float Defence, float BreakDefence0, float BreakDefense) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            if(monster.getHealth() * 0.09f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))) < 0) data.putFloat("BlackForestExDamage",0);
            else data.putFloat("BlackForestExDamage",monster.getHealth() * 0.09f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            if(monster.getHealth() * 0.12f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))) < 0) data.putFloat("BlackForestExDamage",0);
            else data.putFloat("BlackForestExDamage",monster.getHealth() * 0.12f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        }
    }
    public static float WaltzCompute(Player player,Mob monster, CompoundTag data) {
        float ExDamage = 0;
        Vec3 vec3 = player.getPosition(1.0f).subtract(monster.getPosition(1.0f));
        if (monster.getPersistentData().contains("QuartzSabre") && monster.getPersistentData().getInt("QuartzSabre") > 0)
            ExDamage = Compute.WaltzMonsterBefore(vec3, player, monster);
        if (ExDamage > 0) data.putFloat("QuartzSabreDamage", ExDamage);
        return ExDamage;
    }
    public static float LightningArmor(Player player, int LightningArmorCount, CompoundTag data) {
        if (LightningArmorCount > 0) {
            data.putBoolean("LightningArmor", true);
            return Compute.PlayerDefence(player) * 0.5f * LightningArmorCount;
        } else data.putBoolean("LightningArmor", false);
        return 0;
    }
    public static float SeaSword(CompoundTag data,Mob monster, float BaseDamage) {
        if (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) {
            if ((monster.getMaxHealth() - monster.getHealth()) * 0.09f >= BaseDamage * 10) {
                data.putFloat("SeaSwordExDamage", BaseDamage * 10);
                return BaseDamage * 10;
            }
            else {
                data.putFloat("SeaSwordExDamage", (monster.getMaxHealth() - monster.getHealth()) * 0.09f);
                return (monster.getMaxHealth() - monster.getHealth()) * 0.09f;
            }
        }
        if (data.contains("SeaSword3") && data.getBoolean("SeaSword3")) {
            if ((monster.getMaxHealth() - monster.getHealth()) * 0.16f >= BaseDamage * 10) {
                data.putFloat("SeaSwordExDamage", BaseDamage * 10);
                return BaseDamage * 10;
            }
            else {
                data.putFloat("SeaSwordExDamage", (monster.getMaxHealth() - monster.getHealth()) * 0.16f);
                return (monster.getMaxHealth() - monster.getHealth()) * 0.16f;
            }
        }
        if (data.contains("SeaSword4") && data.getBoolean("SeaSword4")) {
            if ((monster.getMaxHealth() - monster.getHealth()) * 0.2f >= BaseDamage * 10) {
                data.putFloat("SeaSwordExDamage", BaseDamage * 10);
                return BaseDamage * 10;
            }
            else {
                data.putFloat("SeaSwordExDamage", (monster.getMaxHealth() - monster.getHealth()) * 0.2f);
                return (monster.getMaxHealth() - monster.getHealth()) * 0.2f;
            }
        }
        return 0;
    }
    public static float VolcanoRune2(CompoundTag data, Mob monster, float BaseDamage) {
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 2 && data.contains("volcanoRune2") && data.getInt("volcanoRune2") == 2) {
            if (monster.getMaxHealth() * 0.04f >= BaseDamage * 10) return BaseDamage * 10;
            return monster.getMaxHealth()*0.04f;
        }
        return 0;
    }
    public static void BlackForestSwordGet3(CompoundTag data,Mob monster) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putFloat("BlackForestExDamage",monster.getHealth() * 0.09f);
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putFloat("BlackForestExDamage",monster.getHealth() * 0.12f);

    }
    public static void BlackForestSwordGet4(CompoundTag data,Mob monster,float Defence, float BreakDefence0, float BreakDefense) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) {
            if(monster.getHealth() * 0.09f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))) < 0) data.putFloat("BlackForestExDamage",0);
            else data.putFloat("BlackForestExDamage",monster.getHealth() * 0.09f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        }
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) {
            if(monster.getHealth() * 0.12f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))) < 0) data.putFloat("BlackForestExDamage",0);
            else data.putFloat("BlackForestExDamage",monster.getHealth() * 0.12f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        }
    }
    public static float ToPlayerBlackForest(CompoundTag data, Player player) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) return player.getHealth() * 0.09f;
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) return player.getHealth() * 0.12f;
        return 0;
    }
    public static void ToPlayerBlackForestGet1(CompoundTag data, Player hurter, float CHitDamage) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.09f * (1.0F + CHitDamage));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.12f * (1.0F + CHitDamage));
    }
    public static void ToPlayerBlackForestGet2(CompoundTag data, Player hurter, float CHitDamage, float BreakDefence0, float BreakDefense, float Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.09f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.12f * (1.0F + CHitDamage) * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
    }
    public static float ToPlayerWaltz(Player player, Player hurter, CompoundTag data) {
        float ExDamage = 0;
        Vec3 vec3 = player.getPosition(1.0f).subtract(hurter.getPosition(1.0f));
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0)
            ExDamage = Compute.WaltzPlayerBefore(vec3, player, hurter);
        if (ExDamage > 0) data.putFloat("QuartzSabreDamage", ExDamage);
        return ExDamage;
    }
    public static float ToPlayerSeaSword(CompoundTag data, Player hurter) {
        if (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) {
            data.putFloat("SeaSwordExDamage", (hurter.getMaxHealth() - hurter.getHealth()) * 0.09f);
            return (hurter.getMaxHealth() - hurter.getHealth()) * 0.09f;
        }
        if (data.contains("SeaSword3") && data.getBoolean("SeaSword3")) {
            data.putFloat("SeaSwordExDamage", (hurter.getMaxHealth() - hurter.getHealth()) * 0.16f);
            return (hurter.getMaxHealth() - hurter.getHealth()) * 0.16f;
        }
        return 0;
    }
    public static float ToPlayerVolcanoRune2(CompoundTag data, Player hurter) {
        if (data.contains("volcanoRune2") && data.getInt("volcanoRune2") == 2)
        {
            return hurter.getMaxHealth()*0.04f;
        }
        return 0;
    }
    public static float ToPlayerForestRune3(CompoundTag data, Player hurter) {
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0) return hurter.getMaxHealth() * 0.1f;
        return 0;
    }
    public static void ToPlayerBlackForestGet3(CompoundTag data, Player hurter, float CHitDamage) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.09f);
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.12f);
    }
    public static void ToPlayerBlackForestGet4(CompoundTag data, Player hurter, float CHitDamage, float BreakDefence0, float BreakDefense, float Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.09f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putFloat("BlackForestExDamage",hurter.getHealth() * 0.12f * (1.0F - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0F - BreakDefense) * (E * E / 100) + 1)))));
    }
    public static void ArrowSnowRune3(Player player, CompoundTag data, LivingEntity hurter, Level level) {
        if (data.contains("snowRune") && data.getInt("snowRune") == 3 && data.contains("snowRune3") && data.getInt("snowRune3") == 0) {
            data.putInt("snowRune3", 100);
            float DamageToMob = 0;
            float DamageToPlayer = 0;
            List<Mob> MobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(hurter.getPosition(1.0f), 10, 10, 10));
            for (Mob mob : MobList) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                DamageToMob += Compute.DamageToMonster(player, mob, 2.5f);
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.getPosition(1.0f), 10, 10, 10));
            for (Player player1 : playerList) {
                if (player1 != player) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                    DamageToPlayer += Compute.DamageToPlayer(player, player1, 2.5f);
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
            }
            if (DamageToMob > 0 && (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune"))))
                Compute.FormatMSGSend(player, Component.literal("符石").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfRune),
                        Component.literal("冰川符石-冰霜箭矢").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                                append(Component.literal("对怪物造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(String.format("%.2f", DamageToMob)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("伤害值。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            if (DamageToPlayer > 0 && (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune"))))
                Compute.FormatMSGSend(player, Component.literal("符石").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfRune),
                        Component.literal("冰川符石-冰霜箭矢").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                                append(Component.literal("对玩家造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(String.format("%.2f", DamageToPlayer)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("伤害值。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

        }
    }
    public static float SwordSkill0 (CompoundTag data, float BaseDamage) {
        float ExDamageIgnoreDefence = 0;
        ExDamageIgnoreDefence += BaseDamage * Compute.SwordSkillLevelGet(data,0) * 0.01;
        return ExDamageIgnoreDefence;
    }
    public static void SwordSkill3Attack (CompoundTag data, Player player, Entity entity) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,3) > 0) {
            if (Utils.SwordSkill3Map.containsKey(player)) {
                SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(player);
                if (swordSkill3.getTime() > TickCount && swordSkill3.getEntity().equals(entity)) {
                    if (swordSkill3.getCount() < 10) swordSkill3.setCount(swordSkill3.getCount() + 1);
                    swordSkill3.setTime(TickCount + 200);
                }
                else {
                    swordSkill3.setEntity(entity);
                    swordSkill3.setCount(1);
                    swordSkill3.setTime(TickCount + 200);
                }
            }
            else {
                SwordSkill3 swordSkill3 = new SwordSkill3(entity,1,TickCount + 200);
                Utils.SwordSkill3Map.put(player,swordSkill3);
            }
            SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(player);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4,10,10,swordSkill3.getCount(),0),(ServerPlayer) player);
        }
    }
    public static float SwordSkill3 (CompoundTag data, Player player, LivingEntity monster) {
        float DamageEnhance = 0;
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,3) > 0 && Utils.SwordSkill3Map.containsKey(player)) {
            SwordSkill3 swordSkill3 = Utils.SwordSkill3Map.get(player);
            if (swordSkill3.getEntity().equals(monster) && swordSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.SwordSkillLevelGet(data,3) * 0.002 * swordSkill3.getCount();
            }
        }
        return DamageEnhance;
    }
    public static void SwordSkill5Attack (CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,5) > 0) {
            data.putInt(StringUtils.SwordSkillNum.Skill5,TickCount + 60);
            ModNetworking.sendToClient(new SkillImageS2CPacket(6,3,3,0,0), (ServerPlayer) player);
        }
    }
    public static void SwordSkill6Attack (CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,6) > 0) {
            if (data.getBoolean("Crit")) {
                if (Utils.SwordSkill6Map.containsKey(player)) {
                    SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(player);
                    if (swordSkill6.getTime() > TickCount) {
                        if (swordSkill6.getCount() < 10) swordSkill6.setCount(swordSkill6.getCount() + 1);
                        swordSkill6.setTime(TickCount + 200);
                    }
                    else {
                        swordSkill6.setTime(TickCount + 200);
                        swordSkill6.setCount(1);
                    }
                }
                else {
                    SwordSkill6 swordSkill6 = new SwordSkill6(1,TickCount + 200);
                    Utils.SwordSkill6Map.put(player,swordSkill6);
                }
                SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(player);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,10,10,swordSkill6.getCount(),0), (ServerPlayer) player);
            }
            else {
                if (Utils.SwordSkill6Map.containsKey(player)) {
                    SwordSkill6 swordSkill6 = Utils.SwordSkill6Map.get(player);
                    swordSkill6.setCount(0);
                }
                else {
                    SwordSkill6 swordSkill6 = new SwordSkill6(0,0);
                    Utils.SwordSkill6Map.put(player,swordSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,0,0,0,0), (ServerPlayer) player);

            }
        }
    }
    public static void SwordSkill13Attack (CompoundTag data, Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,13) > 0) {
            if (!Utils.SwordSkill13Map.containsKey(player)) {
                if (data.contains("Crit")) {
                    SwordSkill13 swordSkill13 = new SwordSkill13(2,TickCount + 120);
                    Utils.SwordSkill13Map.put(player,swordSkill13);
                }
                else {
                    SwordSkill13 swordSkill13 = new SwordSkill13(1,TickCount + 120);
                    Utils.SwordSkill13Map.put(player,swordSkill13);
                }
            }
            else {
                SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(player);
                if (swordSkill13.getTime() > TickCount) {
                    if (data.contains("Crit")) {
                        swordSkill13.setCount(swordSkill13.getCount() + 2);
                        swordSkill13.setTime(TickCount + 120);
                    }
                    else {
                        swordSkill13.setCount(swordSkill13.getCount() + 1);
                        swordSkill13.setTime(TickCount + 120);
                    }
                }
                else {
                    if (data.contains("Crit")) {
                        swordSkill13 = new SwordSkill13(2,TickCount + 120);
                        Utils.SwordSkill13Map.put(player,swordSkill13);
                    }
                    else {
                        swordSkill13 = new SwordSkill13(1,TickCount + 120);
                        Utils.SwordSkill13Map.put(player,swordSkill13);
                    }
                }
                if (swordSkill13.getCount() > 12) {
                    swordSkill13.setCount(12);
                }
            }
            SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(player);
            ModNetworking.sendToClient(new SkillImageS2CPacket(15,6,6,swordSkill13.getCount(),0), (ServerPlayer) player);
        }
    }
    public static float SwordSkill13 (CompoundTag data, Player player, float BaseDamage) {
        float ExDamageIgnoreDefence = 0;
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,13) > 0) {
            if (Utils.SwordSkill13Map.containsKey(player)) {
                SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(player);
                if (swordSkill13.getTime() > TickCount) {
                    ExDamageIgnoreDefence += BaseDamage * swordSkill13.getCount() * 0.005 * Compute.SwordSkillLevelGet(data,13);
                }
            }
        }
        return ExDamageIgnoreDefence;
    }
    public static float SwordSkill14 (CompoundTag data, Player player, float BaseDamage, LivingEntity monster) {
        float ExDamageIgnoreDefence = 0;
        if (Compute.SwordSkillLevelGet(data,14) > 0) {
            float PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            float MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate > MonsterHealthRate) {
                ExDamageIgnoreDefence += BaseDamage * 0.2 * Math.min(1.0,(PlayerHealthRate - MonsterHealthRate) / 0.66);
            }
            else return 0;
        }
        return ExDamageIgnoreDefence;
    }
    public static float BowSkill0 (CompoundTag data, float BaseDamage) {
        float ExDamageIgnoreDefence = 0;
        if (Compute.BowSkillLevelGet(data,0) > 0) {
            ExDamageIgnoreDefence += BaseDamage * 0.01 * Compute.BowSkillLevelGet(data,0);
        }
        return ExDamageIgnoreDefence;
    }

    public static void BowSkill3Attack (CompoundTag data, Player player, Entity entity) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,3) > 0) {
            if (Utils.BowSkill3Map.containsKey(player)) {
                BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(player);
                if (bowSkill3.getTime() > TickCount && bowSkill3.getEntity().equals(entity)) {
                    if (bowSkill3.getCount() < 3) bowSkill3.setCount(bowSkill3.getCount() + 1);
                    bowSkill3.setTime(TickCount + 200);
                }
                else {
                    bowSkill3.setEntity(entity);
                    bowSkill3.setCount(1);
                    bowSkill3.setTime(TickCount + 200);
                }
            }
            else {
                BowSkill3 bowSkill3 = new BowSkill3(entity,1,TickCount + 200);
                Utils.BowSkill3Map.put(player,bowSkill3);
            }
            BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(player);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4,10,10,bowSkill3.getCount(),1),(ServerPlayer) player);
        }
    }
    public static float BowSkill3 (CompoundTag data, Player player, LivingEntity monster) {
        float DamageEnhance = 0;
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,3) > 0 && Utils.BowSkill3Map.containsKey(player)) {
            BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(player);
            if (bowSkill3.getEntity().equals(monster) && bowSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.BowSkillLevelGet(data,3) * 0.033/5 * bowSkill3.getCount();
            }
        }
        return DamageEnhance;
    }
    public static float NetherBowDamageEnhance (Entity Arrow, CompoundTag dataArrow, LivingEntity hurter) {
        float EnhanceDamage = 1;
        if (Arrow.getPersistentData().contains("IsNetherBow") && Arrow.getPersistentData().getBoolean("IsNetherBow")) {
            Vec3 ShootPos = new Vec3(dataArrow.getDouble("PosX"), dataArrow.getDouble("PosY"), dataArrow.getDouble("PosZ"));
            Vec3 TargetPos = new Vec3(hurter.getX(), hurter.getY(), hurter.getZ());
            double Distance = ShootPos.distanceTo(TargetPos);
            if (Distance > 100) Distance = 100;
            EnhanceDamage = (float) (1.5 + Distance / 100.0);
        }
        return EnhanceDamage;
    }
    public static void BowPositiveEffect (ItemStack Bow, Player player, CompoundTag data, int TickCount) {
        if(Bow.is(Moditems.plainbow.get())) {
            player.addEffect(new MobEffectInstance(ModEffects.PLAINBOW.get(),40));
            data.putInt(StringUtils.PlainBowSkill,TickCount + 40);
        }
        if(Bow.is(Moditems.forestbow.get())) player.heal(player.getMaxHealth() * 0.05f);
        if(Bow.is(Moditems.volcanobow.get())){
            player.addEffect(new MobEffectInstance(ModEffects.VOLCANOBOW.get(),50));
            data.putInt(StringUtils.VolcanoBowSkill,TickCount + 50);
        }
    }
    public static void BowSkill5 (CompoundTag data, Player player) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.BowSkillLevelGet(data,5) > 0) {
            data.putInt(StringUtils.BowSkillNum.Skill5,TickCount + 100);
            ModNetworking.sendToClient(new SkillImageS2CPacket(6,5,5,0,1), (ServerPlayer) player);
        }
    }
    public static void BowSkill6Attack (CompoundTag data, Player player, boolean flag) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,6) > 0) {
            if (flag) {
                if (Utils.BowSkill6Map.containsKey(player)) {
                    BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(player);
                    if (bowSkill6.getTime() > TickCount) {
                        if (bowSkill6.getCount() < 3) bowSkill6.setCount(bowSkill6.getCount() + 1);
                        bowSkill6.setTime(TickCount + 200);
                    }
                    else {
                        bowSkill6.setTime(TickCount + 200);
                        bowSkill6.setCount(1);
                    }
                }
                else {
                    BowSkill6 bowSkill6 = new BowSkill6(1,TickCount + 200);
                    Utils.BowSkill6Map.put(player,bowSkill6);
                }
                BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(player);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,10,10,bowSkill6.getCount(),1), (ServerPlayer) player);
            }
            else {
                if (Utils.BowSkill6Map.containsKey(player)) {
                    BowSkill6 bowSkill6 = Utils.BowSkill6Map.get(player);
                    bowSkill6.setCount(0);
                }
                else {
                    BowSkill6 bowSkill6 = new BowSkill6(0,0);
                    Utils.BowSkill6Map.put(player,bowSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,0,0,0,1), (ServerPlayer) player);
            }
        }
    }
    public static float SwordSkill12 (CompoundTag data, Player player, float BaseDamage) {
        if (Compute.SwordSkillLevelGet(data,12) > 0 && Utils.SwordSkill12.containsKey(player)
                && Utils.SwordSkill12.get(player)) {
            return BaseDamage * Compute.SwordSkillLevelGet(data,12) * 0.4f;
        }
        return 0;
    }
    public static void SwordSkill12Attack (CompoundTag data, Player player) {
        if (Compute.SwordSkillLevelGet(data,12) > 0 && Utils.SwordSkill12.containsKey(player)
                && Utils.SwordSkill12.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Mob mob : mobList) {
                Compute.DamageToMonster(player,mob,0.2f * Compute.SwordSkillLevelGet(data,12));
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Player player1 : playerList) {
                Compute.DamageToPlayer(player,player1,0.2f * Compute.SwordSkillLevelGet(data,12));
            }
            Utils.SwordSkill12.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(0),(ServerPlayer) player);
        }
    }
    public static float BowSkill12 (CompoundTag data, Player player, float BaseDamage) {
        if (Compute.BowSkillLevelGet(data,12) > 0 && Utils.BowSkill12.containsKey(player)
                && Utils.BowSkill12.get(player)) {
            return BaseDamage * Compute.BowSkillLevelGet(data,12) * 0.4f;
        }
        return 0;
    }
    public static void BowSkill12Attack (CompoundTag data, Player player) {
        if (Compute.BowSkillLevelGet(data,12) > 0 && Utils.BowSkill12.containsKey(player)
                && Utils.BowSkill12.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Mob mob : mobList) {
                Compute.DamageToMonster(player,mob,0.2f * Compute.BowSkillLevelGet(data,12));
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Player player1 : playerList) {
                Compute.DamageToPlayer(player,player1,0.2f * Compute.BowSkillLevelGet(data,12));
            }
            Utils.BowSkill12.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(1),(ServerPlayer) player);
        }
    }
    public static float BowSkill14 (CompoundTag data, Player player, float BaseDamage) {
        if (Compute.BowSkillLevelGet(data,14) > 0) {
            int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
            float rate = (TickCount - data.getInt(StringUtils.BowSkillNum.Skill14) / 100.0f);
            if (rate > 1) rate = 1;
            data.putInt(StringUtils.BowSkillNum.Skill14,TickCount);
            return BaseDamage * Compute.BowSkillLevelGet(data,14) * rate;
        }
        return 0;
    }
    public static void BowSkill13Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,13) > 0 && data.getInt(StringUtils.BowSkillNum.Skill13) < TickCount) {
            Level level = player.level();
            float damage = Compute.PlayerAttackDamage(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            float ExpUp = Compute.ExpGetImprove(player);
            Random random = new Random();
            for (int i = 0; i < 20; i ++) {
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage,CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),false);
                arrow.setDeltaMovement(0,-1,0);
                arrow.moveTo(entity.getX() + random.nextInt(-2,2),entity.getY() + random.nextInt(-5,5) + 10,entity.getZ() + random.nextInt(-2,2));
                level.addFreshEntity(arrow);
            }
            data.putInt(StringUtils.BowSkillNum.Skill13,TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(14,10,10,0,1),(ServerPlayer) player);
        }
    } // 箭雨
}
