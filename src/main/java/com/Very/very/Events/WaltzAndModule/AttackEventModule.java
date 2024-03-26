package com.Very.very.Events.WaltzAndModule;

import com.Very.very.Customize.Players.Crush.CrushiSword;
import com.Very.very.CoreAttackModule.AttackEvent;
import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.DefencePenetrationParticleS2CPacket;
import com.Very.very.NetWorking.Packets.ParticlePackets.DragonBreathParticleS2CPacket;
import com.Very.very.NetWorking.Packets.ParticlePackets.SlowDownParticleS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedClearS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ZuesSwordS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillImageS2CPacket;
import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowSword4;
import com.Very.very.Series.NetherSeries.Equip.WitherBow.WitherBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Bow.ForestBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Bow.MineBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Bow.PlainBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Bow.VolcanoBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword3;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.PosAndLastTime;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill6;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
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
    public static double SwordSKillEnhance(Player player){
        CompoundTag data = player.getPersistentData();
        return Math.max(0,(data.getInt(StringUtils.SkillArray[0]) - 45) * 0.0033);
    }
    public static double BowSKillEnhance(Player player){
        CompoundTag data = player.getPersistentData();
        return Math.max(0,(data.getInt(StringUtils.SkillArray[1]) - 45) * 0.0033);
    }
    public static double ManaSKillEnhance(Player player){
        CompoundTag data = player.getPersistentData();
        return Math.max(0,(data.getInt(StringUtils.SkillArray[2]) - 45) * 0.005);
    }

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
    public static void MineSwordAndSnowSwordSlowDownForce(Item item, Mob monster) {
        if(item instanceof MineSword0 || item instanceof MineSword1 || item instanceof MineSword2 || item instanceof MineSword3
                || item instanceof SnowSword0 || item instanceof SnowSword1 || item instanceof SnowSword2
                || item instanceof SnowSword3 || item instanceof SnowSword4) {
            if (item instanceof MineSword3 || item instanceof SnowSword0 || item instanceof SnowSword1 || item instanceof SnowSword2
                    || item instanceof SnowSword3 || item instanceof SnowSword4) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1,false,false));
            else monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,false,false));
            monster.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(),40),serverPlayer));
        }
    }
    public static void MineSwordAndSnowSwordSlowDownForcePlayer(Item item, Player monster)
    {
        if(item instanceof MineSword0 || item instanceof MineSword1 || item instanceof MineSword2 || item instanceof MineSword3
                || item instanceof SnowSword0 || item instanceof SnowSword1 || item instanceof SnowSword2
                || item instanceof SnowSword3 || item instanceof SnowSword4) {
            if (item instanceof MineSword3 || item instanceof SnowSword0 || item instanceof SnowSword1 || item instanceof SnowSword2
                    || item instanceof SnowSword3 || item instanceof SnowSword4) monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1,false,false));
            else monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,false,false));
        }
    }

/*    public static void KillPositiveEffect(Player attacker)
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

    }*/
    public static void SnowRune2(CompoundTag data, Mob monster, Player player, double Defence) {
        if (data.contains("snowRune") && data.getInt("snowRune") == 2 && data.contains("snowRune2") && data.getInt("snowRune2") == 0) {
            data.putInt("snowRune2",100);
            Compute.CoolDownTimeSend(player,ModItems.SnowRune0.get().getDefaultInstance(),100);
            monster.getPersistentData().putInt("snowRune2Defence",30);
            Compute.AddSlowDownEffect(monster,30 * Compute.EndRune3Judge(player,3),100);
            Utils.SnowRune2MobController.add(monster);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(monster.getId(),30),serverPlayer));

            BlockState blockState = Blocks.ICE.defaultBlockState();
            BlockPos blockPos = new BlockPos((int) monster.getX(),(int) (monster.getY() + 0.9),(int) monster.getZ());

            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                player.level().setBlockAndUpdate(blockPos,blockState);
                player.level().destroyBlock(blockPos,false);
            }

        }
    }
    public static double ForestRune3(Player player, CompoundTag data) {
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0) {
            return Compute.XpStrengthADDamage(player,2) * Compute.EndRune3Judge(player,0);
        }
        else return 0;
    }
    public static double BlackForest(Player player, Mob monster) {
        if (Utils.BlackForestSwordActiveMap.containsKey(player)) {
            double ExRate = monster.getHealth() * Utils.BlackForestSwordActiveMap.get(player) / monster.getMaxHealth();
            Utils.BlackForestSwordActiveMap.remove(player);
            Compute.EffectLastTimeSend(player,ModItems.BlackForestSword0.get().getDefaultInstance(),0);
            return Compute.XpStrengthADDamage(player,1 + ExRate);
        }
        return 0;
    }
    public static double WaltzCompute(Player player,Mob monster, CompoundTag data) {
        double ExDamage = 0;
        Vec3 vec3 = player.getPosition(1).subtract(monster.getPosition(1));
        if (monster.getPersistentData().contains("QuartzSabre") && monster.getPersistentData().getInt("QuartzSabre") > 0)
            ExDamage = Compute.WaltzMonsterBefore(vec3, player, monster);
        if (ExDamage > 0) data.putDouble("QuartzSabreDamage", ExDamage);
        return ExDamage;
    }
    public static double LightningArmor(Player player, int LightningArmorCount, CompoundTag data) {
        if (LightningArmorCount > 0) {
            data.putBoolean("LightningArmor", true);
            return Compute.PlayerAttributes.PlayerDefence(player) * 0.5f * LightningArmorCount;
        } else data.putBoolean("LightningArmor", false);
        return 0;
    }
    public static double SeaSword(Player player, Mob monster) {
        if (Utils.SeaSwordActiveMap.containsKey(player)) {
            double ExRate = (1 - (monster.getHealth() / monster.getMaxHealth())) * Utils.SeaSwordActiveMap.get(player);
            Utils.SeaSwordActiveMap.remove(player);
            Compute.EffectLastTimeSend(player,ModItems.SeaSword0.get().getDefaultInstance(),0);
            return Compute.XpStrengthADDamage(player,1 + ExRate);
        }
        return 0;
    }
    public static double VolcanoRune2(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 2) {
            if (Utils.VolcanoRune2Map.containsKey(player) && Utils.VolcanoRune2Map.get(player) == 2) {
                Utils.VolcanoRune2Map.put(player,0);
                return Math.max(Compute.XpStrengthADDamage(player,1), Compute.XpStrengthAPDamage(player,0.25)) * Compute.EndRune3Judge(player,1);
            }
            else Utils.VolcanoRune2Map.put(player,Utils.VolcanoRune2Map.getOrDefault(player,0) + 1);
        }
        return 0;
    }
    public static double ToPlayerBlackForest(CompoundTag data, Player player) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) return player.getHealth() * 0.09f;
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) return player.getHealth() * 0.12f;
        return 0;
    }
    public static void ToPlayerBlackForestGet2(CompoundTag data, Player hurter, double CHitDamage, double BreakDefence0, double BreakDefense, double Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putDouble("BlackForestExDamage",hurter.getHealth() * 0.09f * (1.0d + CHitDamage) * (1.0d - (0.25F *  log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putDouble("BlackForestExDamage",hurter.getHealth() * 0.12f * (1.0d + CHitDamage) * (1.0d - (0.25F *  log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
    }
    public static double ToPlayerWaltz(Player player, Player hurter, CompoundTag data) {
        double ExDamage = 0;
        Vec3 vec3 = player.position().subtract(hurter.position());
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0)
            ExDamage = Compute.WaltzPlayerBefore(vec3, player, hurter);
        if (ExDamage > 0) data.putDouble("QuartzSabreDamage", ExDamage);
        return ExDamage;
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
        if (data.contains("volcanoRune2") && data.getInt("volcanoRune2") == 2)
        {
            return hurter.getMaxHealth()*0.04f;
        }
        return 0;
    }
    public static double ToPlayerForestRune3(CompoundTag data, Player hurter) {
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.getInt("DGreen3") == 0) return hurter.getMaxHealth() * 0.1f;
        return 0;
    }
    public static void ToPlayerBlackForestGet4(CompoundTag data, Player hurter, double CHitDamage, double BreakDefence0, double BreakDefense, double Defence) {
        if (data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0")) data.putDouble("BlackForestExDamage",hurter.getHealth() * 0.09f * (1.0d - (0.25F *  log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
        if (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3")) data.putDouble("BlackForestExDamage",hurter.getHealth() * 0.12f * (1.0d - (0.25F *  log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1)))));
    }
    public static void ArrowSnowRune3(Player player, CompoundTag data, LivingEntity hurter, Level level) {
        if (data.contains("snowRune") && data.getInt("snowRune") == 3 && data.contains("snowRune3") && data.getInt("snowRune3") == 0) {
            data.putInt("snowRune3", 100);
            double DamageToMob = 0;
            double DamageToPlayer = 0;
            Compute.CoolDownTimeSend(player,ModItems.SnowRune3.get().getDefaultInstance(),100);
            List<Mob> MobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(hurter.position(), 10, 10, 10));
            for (Mob mob : MobList) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2,false,false));
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(),60),serverPlayer));

                DamageToMob += Compute.Damage.AttackDamageToMonster_RateAdDamage(player, mob, 2.5 * Compute.EndRune3Judge(player,3));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.position(), 10, 10, 10));
            for (Player player1 : playerList) {
                if (player1 != player && !player1.level().equals(player1.level().getServer().getLevel(Level.OVERWORLD))) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                    DamageToPlayer += Compute.Damage.AttackDamageToPlayer_RateAdDamage(player, player1, 2.5f);
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
    public static double SwordSkill0 (CompoundTag data, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
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
    public static double SwordSkill3 (CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
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
                if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
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
                    if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
                        swordSkill13.setCount(swordSkill13.getCount() + 2);
                        swordSkill13.setTime(TickCount + 120);
                    }
                    else {
                        swordSkill13.setCount(swordSkill13.getCount() + 1);
                        swordSkill13.setTime(TickCount + 120);
                    }
                }
                else {
                    if (data.getBoolean(StringUtils.DamageTypes.Crit)) {
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
    public static double SwordSkill13 (CompoundTag data, Player player, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
        int TickCount = player.getServer().getTickCount();
        if (Compute.SwordSkillLevelGet(data,13) > 0) {
            if (Utils.SwordSkill13Map.containsKey(player)) {
                SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(player);
                if (swordSkill13.getTime() > TickCount) {
                    ExDamageIgnoreDefence += BaseDamage * swordSkill13.getCount() * 0.0025 * Compute.SwordSkillLevelGet(data,13);
                    Compute.PlayerHeal(player,  (BaseDamage * swordSkill13.getCount() * 0.0025 * 0.01 * Compute.SwordSkillLevelGet(data,13)));
                }
            }
        }
        return ExDamageIgnoreDefence;
    }
    public static double SwordSkill14 (CompoundTag data, Player player, double BaseDamage, LivingEntity monster) {
        double ExDamageIgnoreDefence = 0;
        if (Compute.SwordSkillLevelGet(data,14) > 0) {
            double PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            double MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate > MonsterHealthRate) {
                ExDamageIgnoreDefence += BaseDamage * 0.2 * Math.min(1.0,(PlayerHealthRate - MonsterHealthRate) / 0.66);
            }
            else return 0;
        }
        return ExDamageIgnoreDefence;
    }
    public static double BowSkill0 (CompoundTag data, double BaseDamage) {
        double ExDamageIgnoreDefence = 0;
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
    public static double BowSkill3 (CompoundTag data, Player player, LivingEntity monster) {
        double DamageEnhance = 0;
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,3) > 0 && Utils.BowSkill3Map.containsKey(player)) {
            BowSkill3 bowSkill3 = Utils.BowSkill3Map.get(player);
            if (bowSkill3.getEntity().equals(monster) && bowSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.BowSkillLevelGet(data,3) * 0.033/5 * bowSkill3.getCount();
            }
        }
        return DamageEnhance;
    }
    public static double NetherBowDamageEnhance (Entity Arrow, CompoundTag dataArrow, LivingEntity hurter) {
        double EnhanceDamage = 1;
        if (Arrow.getPersistentData().contains("IsNetherBow") && Arrow.getPersistentData().getBoolean("IsNetherBow")) {
            Vec3 ShootPos = new Vec3(dataArrow.getDouble("PosX"), dataArrow.getDouble("PosY"), dataArrow.getDouble("PosZ"));
            Vec3 TargetPos = new Vec3(hurter.getX(), hurter.getY(), hurter.getZ());
            double Distance = ShootPos.distanceTo(TargetPos);
            if (Distance > 100) Distance = 100;
            EnhanceDamage =  (1.5 + Distance / 100.0);
        }
        return EnhanceDamage;
    }
    public static void BowPositiveEffect (ItemStack Bow, Player player, CompoundTag data, int TickCount) {
        if(Bow.getItem() instanceof PlainBow) {
            player.addEffect(new MobEffectInstance(ModEffects.PLAINBOW.get(),40));
            data.putInt(StringUtils.PlainBowSkill,TickCount + 40);
        }
        if(Bow.getItem() instanceof ForestBow) player.heal(player.getMaxHealth() * 0.05f);
        if(Bow.getItem() instanceof VolcanoBow){
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
    public static double SwordSkill12 (CompoundTag data, Player player, double BaseDamage) {
        if (Compute.SwordSkillLevelGet(data,12) > 0 && Utils.SwordSkill12.containsKey(player)
                && Utils.SwordSkill12.get(player)) {
            return BaseDamage * Compute.SwordSkillLevelGet(data,12) * 2;
        }
        return 0;
    }
    public static void SwordSkill12Attack (CompoundTag data, Player player) {
        if (Compute.SwordSkillLevelGet(data,12) > 0 && Utils.SwordSkill12.containsKey(player)
                && Utils.SwordSkill12.get(player)) {
            Level level = player.level();
            Random random = new Random();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),10,10,10));
            for (Mob mob : mobList) {
                AttackEvent.AttackToMonster(mob,player,player.getItemInHand(InteractionHand.MAIN_HAND).getItem(),data,0.2f * Compute.SwordSkillLevelGet(data,12));
                if (random.nextInt(0,1) == 0) {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ModParticles.BLADE0.get(),true,
                            mob.getX(),mob.getY() + 1,mob.getZ(),0,0,0,0,0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                }
                else {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ModParticles.BLADE1.get(),true,
                            mob.getX(),mob.getY() + 1,mob.getZ(),0,0,0,0,0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.position(),10,10,10));
            for (Player player1 : playerList) {
                if (player1 != player) AttackEvent.AttackToPlayer(player,player1,data,player.getItemInHand(InteractionHand.MAIN_HAND).getItem(),0.2f * Compute.SwordSkillLevelGet(data,12));
                if (random.nextInt(0,1) == 0) {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ModParticles.BLADE0.get(),true,
                            player1.getX(),player1.getY() + 1,player1.getZ(),0,0,0,0,0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                }
                else {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ModParticles.BLADE1.get(),true,
                            player1.getX(),player1.getY() + 1,player1.getZ(),0,0,0,0,0);
                    player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                }
            }
            Utils.SwordSkill12.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(0),(ServerPlayer) player);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_SWEEP), SoundSource.PLAYERS,player.getX(),player.getY() + 1,player.getZ(),1,1,0);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));
        }
    }
    public static double BowSkill12 (CompoundTag data, Player player, double BaseDamage) {
        if (Compute.BowSkillLevelGet(data,12) > 0 && Utils.BowSkill12.containsKey(player)
                && Utils.BowSkill12.get(player)) {
            return BaseDamage * Compute.BowSkillLevelGet(data,12) * 3;
        }
        return 0;
    }
    public static void BowSkill12Attack (CompoundTag data, Player player) {
        if (Compute.BowSkillLevelGet(data,12) > 0 && Utils.BowSkill12.containsKey(player)
                && Utils.BowSkill12.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),10,10,10));
            for (Mob mob : mobList) {
                Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,6 * Compute.BowSkillLevelGet(data,12));
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.position(),10,10,10));
            for (Player player1 : playerList) {
                Compute.Damage.AttackDamageToPlayer_RateAdDamage(player,player1,0.2f * Compute.BowSkillLevelGet(data,12));
            }
            Utils.BowSkill12.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(1),(ServerPlayer) player);
        }
    }
    public static double BowSkill14 (CompoundTag data, Player player, double BaseDamage) {
        if (Compute.BowSkillLevelGet(data,14) > 0) {
            int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
            double rate = ((TickCount - data.getInt(StringUtils.BowSkillNum.Skill14)) / 400d);
            if (rate > 1) rate = 1;
            data.putInt(StringUtils.BowSkillNum.Skill14,TickCount);
            return BaseDamage * Compute.BowSkillLevelGet(data,14) * rate * 2;
        }
        return 0;
    }
    public static void BowSkill13Attack(CompoundTag data, Player player, Entity entity) {
        int TickCount = player.getServer().getTickCount();
        if (Compute.BowSkillLevelGet(data,13) > 0 && data.getInt(StringUtils.BowSkillNum.Skill13) < TickCount) {
            Level level = player.level();
            double damage = Compute.PlayerAttributes.PlayerAttackDamage(player);
            Random random = new Random();
            for (int i = 0; i < 20; i ++) {
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage,false);
                arrow.setDeltaMovement(0,-1,0);
                arrow.moveTo(entity.getX() + random.nextInt(-2,2),entity.getY() + random.nextInt(-5,5) + 10,entity.getZ() + random.nextInt(-2,2));
                ProjectileUtil.rotateTowardsMovement(arrow,0);
                level.addFreshEntity(arrow);
            }
            data.putInt(StringUtils.BowSkillNum.Skill13,TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(14,10,10,0,1),(ServerPlayer) player);
        }
    } // 箭雨
    public static void SnowArmorEffect (Player player, Mob monster) {
        if (Compute.ArmorCount.Snow(player) >= 4) {
            monster.setDeltaMovement(0,0,0);
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,100,false,false));
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(monster.getId(),5),serverPlayer));
        }
    }
    public static void WitherBow (Player player, ItemStack Bow, CompoundTag data, int TickCount) {
        if (Bow.getItem() instanceof WitherBow witherBow) {
            if (player.getHealth() <= player.getMaxHealth() * 0.07f) {
                player.kill();
                Compute.FormatBroad(player.level(),Component.literal("死亡").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal(player.getName().getString()+"被自己的魔法干掉了。"));
            }
            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.07f);
            data.putInt(StringUtils.WitherBow.Effect + witherBow.Num, TickCount + 100);
            Compute.EffectLastTimeSend(player,ModItems.WitherBow0.get().getDefaultInstance(),100);
        }
    }

    public static double NetherArmorEffect (Player player, Mob mob) {
        if (Compute.ArmorCount.Nether(player) > 0) {
            double Defence = Compute.MonsterDefence(mob);
            return Math.min(0.5f,Defence * Compute.NetherSuitEffectRate(player) * 0.5f / 500.0d);
        }
        return 0;
    }

    public static double NetherArmorEffect (Player player, Player hurter) {
        if (Compute.ArmorCount.Nether(player) > 0) {
            double Defence = Compute.PlayerAttributes.PlayerDefence(hurter);
            return Math.min(0.5f,Defence * Compute.NetherSuitEffectRate(player) * 0.5f / 500.0d);
        }
        return 0;
    }
    public static double BowSkill4(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Utils.BowTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()))
            return Compute.BowSkillLevelGet(data,4) * 0.03;
        return 0;
    }
    public static double SwordSkill4(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Utils.SwordTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()))
            return Compute.SwordSkillLevelGet(data,4) * 0.03;
        return 0;
    }
    public static void SakuraBowDefenceUp(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraBow.get())
                && !Utils.playerSakuraBowMap.getOrDefault(player,false)) {
            Utils.SakuraBowEffectMap.put(player,player.getServer().getTickCount() + 20);
            Compute.EffectLastTimeSend(player,ModItems.SakuraBow.get().getDefaultInstance(),20);
        }
    }
    public static void SakuraBowHealth(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraBow.get())
                && Utils.playerSakuraBowMap.getOrDefault(player,false)) {
            Compute.PlayerHeal(player,Compute.PlayerAttributes.PlayerAttackDamage(player) * 0.05);
        }
    }
    public static void SakuraBow(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraBow.get())) {
            if (!Utils.playerSakuraBowMap.containsKey(player)) Utils.playerSakuraBowMap.put(player,true);
            else Utils.playerSakuraBowMap.put(player,!Utils.playerSakuraBowMap.get(player));
        }
    }
    public static void SeaBow(Player player, Mob monster) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SeaBow.get())) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(monster.position(),10,10,10));
            double Damage = Compute.PlayerAttributes.PlayerAttackDamage(player) * 0.5;
            mobList.forEach(mob -> {
                if (mob.distanceTo(monster) <= 3) {
                    if (mob.getHealth() < Damage) {
                        Compute.PlayerShieldProvider(player,200,Damage);
                    }
                    Compute.Damage.DirectDamageToMob(player,mob,Damage);
                }
            });
        }
    }
    public static void MineBow(Player player, Mob monster) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof MineBow mineBow) {
            double rate = (mineBow.getNum() + 1) * 0.2;
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(monster.position(),13,13,13));
            mobList.forEach(mob -> {
                if (mob != monster) {
                    if (mob.distanceTo(monster) <= 4) {
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,rate);
                    }
                }
            });
        }
    }
    public static double NetherBow(Player player, Mob monster) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.NetherBow.get())) return Math.min(1,monster.distanceTo(player) / 100);
        return 0;
    }
    public static double MineShield(Player player) {
        if (Utils.ShieldTag.containsKey(player.getItemInHand(InteractionHand.OFF_HAND).getItem())) return Compute.PlayerAttributes.PlayerDefence(player) / 100 * 0.1;
        else return 0;
    }
    public static void IceSword(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceSword.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.DevilSword.get()) ) {
            Utils.IceSwordEffectMap.put(player,player.getServer().getTickCount() + 40);
            Utils.IceSwordEffectNumMap.put(player,Math.min(3000, Compute.MonsterDefence(mob)));
            Compute.EffectLastTimeSend(player,ModItems.IceSword.get().getDefaultInstance(),40);
            Level level = player.level();
            if (level.getBlockState(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ())).is(Blocks.AIR)) {
                level.setBlockAndUpdate(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),Blocks.ICE.defaultBlockState());
                level.destroyBlock(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),false);
            }
        }
    }
    public static void IceBow(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceBow.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.DevilBow.get())) {
            Utils.IceBowEffectMap.put(player,player.getServer().getTickCount() + 40);
            Utils.IceBowEffectNumMap.put(player,Compute.MonsterDefence(mob));
            Compute.EffectLastTimeSend(player,ModItems.IceBow.get().getDefaultInstance(),40);
            Level level = player.level();
            if (level.getBlockState(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ())).is(Blocks.AIR)) {
                level.setBlockAndUpdate(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),Blocks.ICE.defaultBlockState());
                level.destroyBlock(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),false);
            }
        }
    }
    public static double IceArmorDamageEnhance(Player player, Mob mob) {
        if (Compute.ArmorCount.Ice(player) > 0 && mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            return Compute.ArmorCount.Ice(player) * 0.5;
        }
        return 0;
    }
    public static void EndRune0Judge(Player player, Mob mob) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        if (data.contains(StringUtils.EndRune) && data.getInt(StringUtils.EndRune) == 0
                && (!Utils.EndRune0CoolDown.containsKey(player)
                || Utils.EndRune0CoolDown.get(player) < TickCount)) {
            if (mob.getHealth() / mob.getMaxHealth() < 0.1) {
                Vec3 vec3 = player.position().subtract(mob.position());
                Vec3 vec31 = mob.position().add(vec3.normalize().scale(0.5));
                player.teleportTo(vec31.x,vec31.y,vec31.z);
                Utils.EndRune0Effect.put(player,true);
                Utils.EndRune0CoolDown.put(player,TickCount + 240);
                Compute.CoolDownTimeSend(player,ModItems.EndRune0.get().getDefaultInstance(),240);
            }
        }
    }
    public static double EndRune0DamageEnhance(Player player, double BaseDamage) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.EndRune) && data.getInt(StringUtils.EndRune) == 0
                && Utils.EndRune0Effect.containsKey(player)
                && Utils.EndRune0Effect.get(player).equals(true)) {
            Utils.EndRune0Effect.put(player,false);
            return BaseDamage * 10;
        }
        return 0;
    }
    public static void EndRune2(Player player, Mob mob) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        if (data.getInt(StringUtils.EndRune) == 2
                && (!Utils.EndRune2CoolDown.containsKey(player)
                || Utils.EndRune2CoolDown.get(player) < TickCount)) {
            Utils.EndRune2Pos.put(player,new PosAndLastTime(
                    new Vec3(mob.position().x,mob.position().y,mob.position().z),
                    100,Compute.LevelTypeJudge(player)));
            Utils.EndRune2CoolDown.put(player,TickCount + 240);
            Compute.CoolDownTimeSend(player,ModItems.EndRune0.get().getDefaultInstance(),240);
            ModNetworking.sendToClient(new DragonBreathParticleS2CPacket(
                    mob.getX(),mob.getY(),mob.getZ(),Compute.LevelTypeJudge(player)),(ServerPlayer) player) ;
        }
    }
    public static double ZeusSwordDamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            int FlexibilityAbilityPoint = data.getInt(StringUtils.Ability.Power);
            if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
                int Point = FlexibilityAbilityPoint + (FlexibilityAbilityPoint / 10) * 5;
                return Point * 0.03;
            }
        }
        return 0;
    }
    public static void ShowDickerBow(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShowDickerBow.get())) {
            Utils.ShowDicker = player;
            if (Utils.ShowDickerCount < 10 && Utils.ShowDickerArrowCount == 0) Utils.ShowDickerCount ++;
            Compute.EffectLastTimeSend(player,ModItems.ShowDickerBow.get().getDefaultInstance(),8888,Utils.ShowDickerCount,true);
        }
    }
    public static double ShowDickerBow(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShowDickerBow.get())) {
            Utils.ShowDicker = player;
            return Compute.XpStrengthADDamage(player,mob.getHealth() * 4/mob.getMaxHealth());
        }
        return 0;
    }
    public static double SoulSwordActive(Player player) {
        if (Utils.SoulSwordMap.containsKey(player) && Utils.SoulSwordMap.get(player)) {
            Utils.SoulSwordMap.put(player,false);
            Compute.EffectLastTimeSend(player,ModItems.SoulSword.get().getDefaultInstance(),0);
            return 0.5;
        }
        return 0;
    }
    public static void CrushArrow(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            if (Utils.CrushMob != null && !Utils.CrushMob.equals(mob)) Utils.CrushMob.removeEffect(MobEffects.GLOWING);
            Utils.CrushMob = mob;
            Utils.CrushMob.addEffect(new MobEffectInstance(MobEffects.GLOWING,88888));
        }
    }
    public static double CrushEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())
                && Utils.CrushMob != null && Utils.CrushMob.equals(mob)) {
            ModNetworking.sendToClient(new ZuesSwordS2CPacket(8),(ServerPlayer) player);
            return 2.5;
        }
        return 0;
    }
    public static double LengXueSword(Player player, Mob mob) {
        if (Utils.LengXuePlayer != null && Utils.LengXuePlayer.equals(player)
                && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LXYZOSword.get())) {
            if (Utils.LengXueAttackFlag) {
                Utils.LengXueMob = mob;
                Utils.LengXueMobCount = 60;
                Compute.EffectLastTimeSend(player,ModItems.LXYZOSword.get().getDefaultInstance(),0);
                Utils.LengXueAttackFlag = false;
                return 2.44;
            }
            if (Utils.LengXueMob != null && Utils.LengXueMob.equals(mob)) {
                return 2.44;
            }
        }
        return 0;
    }
    public static void CrushSwordCrit(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            Utils.Crush1CritDamage1Tick = TickCount + 30;
            if (Utils.CrushMob != null && Utils.CrushMob != mob) {
                Utils.CrushMob.removeEffect(MobEffects.GLOWING);
                Utils.CrushMob = mob;
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING,8888));
            }
            if (Utils.CrushMob == null) {
                Utils.CrushMob = mob;
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING,8888));
            }
        }
    }
    public static double CrushSwordCritDamageEnhance(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            if (Utils.CrushMob != null && Utils.CrushMob == mob) {
                return 2;
            }
        }
        return 0;
    }
    public static void CrushSwordAttackLightning(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            if (Utils.Crush1 == null) Utils.Crush1 = player;
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 6) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.moveTo(mob.position());
                    player.level().addFreshEntity(lightningBolt);
                    Utils.CrushDefenceDecreaseMap.put(mob,TickCount + 30);
                    Compute.AddDefenceDescreaseEffectParticle(mob,30);
                }
            });
            if (Utils.Crush1CritDamage2Count < 4) Utils.Crush1CritDamage2Count ++;
            Utils.Crush1CritDamage2Tick = TickCount + 30;
            Compute.EffectLastTimeSend(player,ModItems.ZeusSword.get().getDefaultInstance(),30,Utils.Crush1CritDamage2Count);
        }
    }
    public static void GuangYiArrowMobDefenceDecreaseEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && Utils.GuangYiIsInMode) {
            int TickCount = player.getServer().getTickCount();
            int Count = Utils.GuangYiMobDefenceDecreaseMap.getOrDefault(mob,0);
            if (Utils.GuangYiMobDefenceDecreaseTime.containsKey(mob) && Utils.GuangYiMobDefenceDecreaseTime.get(mob) > TickCount)
                Utils.GuangYiMobDefenceDecreaseMap.put(mob,Math.min(5,Count + 1));
            else Utils.GuangYiMobDefenceDecreaseMap.put(mob,1);
            Utils.GuangYiMobDefenceDecreaseTime.put(mob,TickCount + 100);
            Compute.AddDefenceDescreaseEffectParticle(mob,100);
        }
    }
    public static double GuangYiArrowDistanceDamageEnhance(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && Utils.GuangYiIsInMode) {
            return player.distanceTo(mob) * 0.01;
        }
        return 0;
    }
    public static double GuangYiArrowExDamage(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && !Utils.GuangYiArrowFlag) {
            return Compute.XpStrengthADDamage(player,mob.getHealth() * 4/mob.getMaxHealth());
        }
        return 0;
    }
    public static void GuangYiArrowRain(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && Utils.GuangYiArrowFlag) {
            Level level = player.level();
            double damage = Compute.PlayerAttributes.PlayerAttackDamage(player);
            Random random = new Random();
            for (int i = 0; i < 20; i ++) {
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage,false);
                arrow.setDeltaMovement(0,-1,0);
                arrow.moveTo(mob.getX() + random.nextInt(-2,2),mob.getY() + random.nextInt(-5,5) + 10,mob.getZ() + random.nextInt(-2,2));
                ProjectileUtil.rotateTowardsMovement(arrow,0);
                level.addFreshEntity(arrow);
            }
            Compute.PlayerHeal(player,damage * 0.05);
        }
    }
    public static double CrushExDamage(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && CrushiSword.ActiveTick > TickCount) {
            return Compute.XpStrengthADDamage(player, mob.getHealth() * 4 / mob.getMaxHealth());
        }
        return 0;
    }
    public static void CrushCuriosCountsAdd(Player player, Boolean Crit) {
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1ZeusIsOn) {
            int TickCount = player.getServer().getTickCount();
            if (Utils.CrushCuriosLastTick < TickCount) Utils.CrushCuriosCounts = 0;
            if (Crit) Utils.CrushCuriosCounts += 2;
            else Utils.CrushCuriosCounts += 1;
            if (Utils.CrushCuriosCounts > 12) Utils.CrushCuriosCounts = 12;
            Utils.CrushCuriosLastTick = TickCount + 100;
            Compute.EffectLastTimeSend(player,ModItems.ZeusCurios.get().getDefaultInstance(),100,Utils.CrushCuriosCounts);
        }
    }
    public static double CrushCuriosCountsDamageEnhance(Player player) {
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1ZeusIsOn) {
            int TickCount = player.getServer().getTickCount();
            if (Utils.CrushCuriosCounts == 12 && Utils.CrushCuriosLastTick > TickCount) {
                return 0.075;
            }
        }
        return 0;
    }
    public static double CrushCuriosCountsIgnoreDefenceDamage(Player player, double BaseDamage) {
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1ZeusIsOn) {
            int TickCount = player.getServer().getTickCount();
            if (Utils.CrushCuriosCounts == 12 && Utils.CrushCuriosLastTick > TickCount) {
                return 0.075 * BaseDamage;
            }
        }
        return 0;
    }
    public static void SnowShieldEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.SnowShield.get())) {
            int TickCount = player.getServer().getTickCount();
            Utils.SnowShieldPlayerEffectTickMap.put(player,TickCount + 40);
            ItemStack itemStack = mob.getItemBySlot(EquipmentSlot.HEAD);
            if (itemStack.getItem() instanceof MobArmor mobArmor) {
                Utils.SnowShieldPlayerEffectMap.put(player, (mobArmor.Defence / 4));
                Utils.SnowShieldMobEffectMap.put(mob, TickCount + 40);
                Compute.AddDefenceDescreaseEffectParticle(mob,40);
                Compute.EffectLastTimeSend(player,ModItems.SnowSoul.get().getDefaultInstance(),40);
            }
        }
    }

    public static double NetherShieldEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.NetherShield.get())) {
            int TickCount = player.getServer().getTickCount();
            ItemStack itemStack = mob.getItemBySlot(EquipmentSlot.HEAD);
            if (itemStack.getItem() instanceof MobArmor mobArmor) {
                if (Compute.PlayerAttributes.PlayerDefenceWithoutNetherShield(player) < mobArmor.Defence) {
                    Utils.NetherShieldPlayerDefenceEnhanceMap.put(player,mobArmor.Defence - Compute.PlayerAttributes.PlayerDefenceWithoutNetherShield(player));
                    Utils.NetherShieldPlayerDefenceEnhanceTickMap.put(player,TickCount + 100);
                    Compute.EffectLastTimeSend(player,ModItems.Ruby.get().getDefaultInstance(),100);
                }
                else {
                    return (Compute.PlayerAttributes.PlayerDefence(player) - Compute.PlayerAttributes.PlayerDefenceWithoutNetherShield(player)) / 200;
                }
            }
        }
        return 0;
    }

    public static void ManaKnifeHealthRecover(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaKnife.get())) {
            Compute.PlayerHeal(player,Compute.PlayerAttributes.PlayerAttackDamage(player) * 0.05);
        }
    }

    public static void WcBowEffect(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get()) && Utils.WcBowStatus) {
            int TickCount = player.getServer().getTickCount();
            Utils.WcBowTick = TickCount + 100;
            if (Utils.WcBowTick < TickCount) Utils.WcBowCount = 0;
            if (Utils.WcBowCount < 5) Utils.WcBowCount ++;
            Compute.EffectLastTimeSend(player,ModItems.WcBow.get().getDefaultInstance(),100,Utils.WcBowCount);
        }
    }

    public static double SwordSkill5DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.SwordSkillLevelGet(data, 4) > 0 && Utils.SwordTag.containsKey(player.getMainHandItem().getItem())) {
            return Compute.SwordSkillLevelGet(data, 4) * 0.03;
        }
        return 0;
    }

    public static double ManaSkill5DamageEnhance(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data, 4) > 0 && Utils.SceptreTag.containsKey(player.getMainHandItem().getItem())) {
            return Compute.ManaSkillLevelGet(data, 4) * 0.03;
        }
        return 0;
    }
}
