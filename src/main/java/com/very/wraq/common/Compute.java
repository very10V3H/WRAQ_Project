package com.very.wraq.common;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.very.wraq.commands.changeable.CompensateCommand;
import com.very.wraq.commands.stable.players.DebugCommand;
import com.very.wraq.commands.stable.players.DpsCommand;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.*;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.DamageInfluence;
import com.very.wraq.common.attributeValues.MobAttributes;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.attributeValues.SpecialEffectOnPlayer;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.core.ManaAttackModule;
import com.very.wraq.core.MyArrow;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.events.fight.MonsterAttackEvent;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.events.instance.Moon;
import com.very.wraq.events.mob.MobDeadModule;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AnimationPackets.*;
import com.very.wraq.networking.misc.*;
import com.very.wraq.networking.misc.EntropyPackets.EntropyS2CPacket;
import com.very.wraq.networking.misc.Limit.CheckBlockLimitS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.BowSkill12S2CPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.ManaSkill12S2CPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.ManaSkill13S2CPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.SwordSkill12S2CPacket;
import com.very.wraq.networking.misc.ToolTipPackets.CoolDownTimeS2CPacket;
import com.very.wraq.networking.misc.USE.MobEffectHudS2CPacket;
import com.very.wraq.networking.reputation.ReputationValueS2CPacket;
import com.very.wraq.networking.unSorted.ColdSyncS2CPacket;
import com.very.wraq.networking.unSorted.DebuffTimeS2CPacket;
import com.very.wraq.networking.unSorted.PlayerCallBack;
import com.very.wraq.networking.unSorted.SwiftSyncS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.func.plan.PlanPlayer;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.series.labourDay.LabourDayIronHoe;
import com.very.wraq.process.series.labourDay.LabourDayIronPickaxe;
import com.very.wraq.process.series.potion.NewPotionEffects;
import com.very.wraq.process.system.element.Color;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.ElementValue;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import com.very.wraq.process.system.teamInstance.NewTeamInstanceEvent;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.OnKillEffectOffHandItem;
import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.end.eventController.LightningIslandRecall.IntensifiedLightningArmor;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.series.instance.series.castle.CastleSceptre;
import com.very.wraq.series.instance.series.moon.Equip.MoonBelt;
import com.very.wraq.series.instance.series.taboo.TabooManaArmor;
import com.very.wraq.series.nether.Equip.WitherBook;
import com.very.wraq.series.newrunes.chapter2.HuskNewRune;
import com.very.wraq.series.newrunes.chapter3.NetherNewRune;
import com.very.wraq.series.overworld.chapter1.ManaBook.ManaNote;
import com.very.wraq.series.overworld.chapter1.forest.bossItems.ForestBossSword;
import com.very.wraq.series.overworld.chapter1.volcano.bossItems.VolcanoBossSword;
import com.very.wraq.series.overworld.chapter1.waterSystem.bossItems.LakeBoss;
import com.very.wraq.series.overworld.chapter2.lightningIsland.Armor.LightningArmor;
import com.very.wraq.series.overworld.chapter7.star.StarBottle;
import com.very.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import com.very.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaCurios;
import com.very.wraq.series.specialevents.summer.SummerEvent;
import com.very.wraq.series.worldsoul.SoulEquipAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.very.wraq.core.ManaAttackModule.ManaSkill3;
import static com.very.wraq.core.ManaAttackModule.NetherManaArmor;
import static java.lang.Math.*;


public class Compute {
    private static final Logger log = LoggerFactory.getLogger(Compute.class);

    public static void ManaStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        double MaxMana = data.getDouble("MAXMANA");
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand))
            MaxMana *= (1 - Compute.ManaSkillLevelGet(data, 10) * 0.1);
        ModNetworking.sendToClient(new ManaSyncS2CPacket(MaxMana, data.getDouble("MANA")), (ServerPlayer) player);
    }

    public static void SwiftStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new SwiftSyncS2CPacket(100, data.getDouble(StringUtils.Swift)), (ServerPlayer) player);
    }

    public static boolean PlayerSwiftChange(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        double CurrentSwift = data.getDouble(StringUtils.Swift);
        double MaxSwift = data.getDouble(StringUtils.MaxSwift);
        if (CurrentSwift + Num < 0) return false;
        else {
            data.putDouble(StringUtils.Swift, min(MaxSwift, CurrentSwift + Num));
            SwiftStatusUpdate(player);
            return true;
        }
    }

    public static double PlayerCritDamageDecrease(Player player) {
        double CritDamageDecrease = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("CritDamageDecrease"))
            CritDamageDecrease += stackmainhandtag.getDouble("CritDamageDecrease");
        if (Utils.critDamageDecrease.containsKey(boots)) CritDamageDecrease += Utils.critDamageDecrease.get(boots);
        if (Utils.critDamageDecrease.containsKey(leggings))
            CritDamageDecrease += Utils.critDamageDecrease.get(leggings);
        if (Utils.critDamageDecrease.containsKey(chest)) CritDamageDecrease += Utils.critDamageDecrease.get(chest);
        if (Utils.critDamageDecrease.containsKey(helmet)) CritDamageDecrease += Utils.critDamageDecrease.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critDamageDecrease.containsKey(mainhand))
            CritDamageDecrease += Utils.critDamageDecrease.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critDamageDecrease.containsKey(offhand))
            CritDamageDecrease += Utils.critDamageDecrease.get(offhand);
        if (ArmorCount.Mine(player) >= 2) CritDamageDecrease += 0.5;
        return CritDamageDecrease;
    }

    public static void playerManaAddOrCost(Player player, double Mana) {
        CompoundTag data = player.getPersistentData();
        if (Mana > 0) data.putDouble("MANA", Math.min(data.getDouble("MANA") + Mana, data.getDouble("MAXMANA")));
        else data.putDouble("MANA", Math.max(data.getDouble("MANA") + Mana, 0));
        ManaStatusUpdate(player);
    }

    public static double PlayerCurrentManaNum(Player player) {
        return player.getPersistentData().getDouble("MANA");
    }

    public static double PlayerMaxManaNum(Player player) {
        return player.getPersistentData().getDouble("MAXMANA");
    }


    public static double SwordSkill1And4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.swordTag.containsKey(mainhand)) {
            Decrease += Compute.SwordSkillLevelGet(data, 1) * 0.01;
            Decrease -= Compute.SwordSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double SwordSkill14(CompoundTag data, Player player, LivingEntity monster) {
        double Enhance = 0;
        if (SwordSkillLevelGet(data, 14) > 0) {
            double PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            double MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate < MonsterHealthRate) {
                Enhance += 0.2 * Math.min(1.0, (MonsterHealthRate - PlayerHealthRate) / 0.66);
            }
        }
        return Enhance;
    }

    public static double BowSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.bowTag.containsKey(mainhand)) {
            Decrease -= Compute.BowSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double ManaSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.sceptreTag.containsKey(mainhand)) {
            Decrease -= Compute.ManaSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double PlayerShieldDecrease(Player player, double value) {
        double TmpNum = value;
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            Iterator<Shield> iterator1 = shieldQueue.iterator();
            while (iterator1.hasNext()) {
                Shield shield = iterator1.next();
                if (shield.getValue() > TmpNum) {
                    shield.setValue(shield.getValue() - TmpNum);
                    PlayerShieldCompute(player);
                    return 0;
                } else {
                    TmpNum -= shield.getValue();
                    shield.setTick(0);
                    shield.setValue(0);
                }
            }
            PlayerShieldCompute(player);
            return TmpNum;
        }
        return value;
    }

    public static void playerShieldProvider(Player player, int tick, double value) {
        Shield shield = new Shield(tick, value);
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue == null) {
            shieldQueue = new LinkedList<>();
            shieldQueue.add(shield);
            Utils.playerShieldQueue.put(player.getName().getString(), shieldQueue);
        } else {
            shieldQueue.add(shield);
        }
        PlayerShieldCompute(player);
    }

    public static double PlayerShieldCompute(Player player) {
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            Iterator<Shield> iterator0 = shieldQueue.iterator();
            double shieldValue = 0;
            while (iterator0.hasNext()) {
                Shield shield = iterator0.next();
                if (shield.getTick() > 0) shieldValue += shield.getValue();
                if (shield.getTick() == 0 || shield.getValue() == 0) iterator0.remove();
            }
            double tmpShieldNum = 9.0 * (shieldValue * 1.0 / player.getMaxHealth());
            ModNetworking.sendToClient(new ShieldSyncS2CPacket((int) ceil(tmpShieldNum), (int) ceil(shieldValue)), (ServerPlayer) player);
            return shieldValue;
        }
        ModNetworking.sendToClient(new ShieldSyncS2CPacket(0, 0), (ServerPlayer) player);
        return 0;
    }

    public static double PlayerShieldClear(Player player) {
        double shieldValue = Compute.PlayerShieldCompute(player);
        Compute.PlayerShieldDecrease(player, shieldValue);
        return shieldValue;
    }

    public static void forgingHoverName(ItemStack stack) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);

        MutableComponent suffix = Component.literal("");
        MutableComponent prefix = Component.literal("");

        if (Utils.sceptreTag.containsKey(stack.getItem())) {
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                String ManaCore = data.getString(StringUtils.ManaCore.ManaCore);
                if (ManaCore.equals(StringUtils.ManaCore.SeaCore))
                    suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.BlackForestCore))
                    suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfHusk)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.KazeCore))
                    suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfKaze)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.SakuraCore))
                    suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfDemon)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
            }
        }

        Component defaultName = stack.getItem().getDefaultInstance().getHoverName();

        stack.setHoverName(Component.literal("")
                .append(prefix)
                .append(suffix)
                .append(defaultName));
    }

    public static int levelUpperLimit = 250;
    public static int expGetUpperLimit = 125;

    public static double getCurrentXpLevelUpNeedXpPoint(int xpLevel) {
        return Math.pow(Math.E, 3 + (xpLevel / 100d) * 7);
    }

    public static void givePercentExpToPlayer(Player player, double num, double ExpUp, int ExpLevel) {
        if (player.experienceLevel >= levelUpperLimit) return;
        if (ExpLevel >= expGetUpperLimit) ExpLevel = expGetUpperLimit;
        if (ExpLevel - player.experienceLevel > 8) ExpLevel = player.experienceLevel;

        CompoundTag data = player.getPersistentData();
        double LevelUpNeedXp = getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
        double ExpLevelXp = getCurrentXpLevelUpNeedXpPoint(ExpLevel);
        double XpBeforeUp = (ExpLevelXp * num);
        double XpUp = (ExpLevelXp * num) * ExpUp;
        double Xp = XpBeforeUp + XpUp;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + Xp);
        else data.putDouble("Xp", Xp);
        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.sendFormatMSG(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", XpBeforeUp)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));
    }

    public static void giveExpToPlayer(Player player, double num) {
        if (player.experienceLevel >= levelUpperLimit) return;
        CompoundTag data = player.getPersistentData();
        double LevelUpNeedXp = getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
        double XpUp = 0;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + num);
        else data.putDouble("Xp", num);
        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.sendFormatMSG(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));
    }

    public static void use(Player player, Item tool) {
        if (SpecialEffectOnPlayer.inVertigo(player)) return;

        CompoundTag data = player.getPersistentData();
        if (player.getCooldowns().isOnCooldown(tool)) return;
        if (tool instanceof ActiveItem activeItem) {
            activeItem.active(player);
            VdWeaponCommon.onReleaseActive(player, tool);
        }

        if (tool instanceof ForestBossSword && playerManaCost(player, 180)) {
            if (data.getInt(StringUtils.Entropy.Forest) > 0) {
                player.getCooldowns().addCooldown(ModItems.ForestBossSword.get(), (int) (200 - 200 * PlayerAttributes.coolDownDecrease(player)));
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1, 6, 100, ModParticles.LONG_ENTROPY.get());
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1.5, 6, 100, ModParticles.LONG_ENTROPY.get());
                MySound.SoundToAll(player, ModSounds.Nether_Power.get());
            } else {
                Compute.sendFormatMSG(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfHealth),
                        Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.WHITE));
            }
        } else if (tool instanceof VolcanoBossSword && playerManaCost(player, 180)) {
            if (data.getInt(StringUtils.Entropy.Volcano) > 0) {
                Level level = player.level();
                player.getCooldowns().addCooldown(ModItems.VolcanoBossSword.get(), (int) (200 - 200 * PlayerAttributes.coolDownDecrease(player)));

                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Mob mob : mobList) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Damage.AttackDamageToMonster_RateAdDamage(player, mob, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                        player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                                ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));
                    }
                }
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Player player1 : playerList) {
                    if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Damage.AttackDamageToPlayer_RateAdDamage(player, player1, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                    }
                }
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1, 6, 100, ModParticles.LONG_ENTROPY.get());
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1.5, 6, 100, ModParticles.LONG_ENTROPY.get());

                MySound.SoundToAll(player, ModSounds.Lava.get());
            } else {
                Compute.sendFormatMSG(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfPower),
                        Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.WHITE));
            }
        } else if (tool instanceof LakeBoss.LakeBossSword && playerManaCost(player, 180)) {
            player.getCooldowns().addCooldown(ModItems.LakeBossSword.get(), (int) (600 - 20 * EntropyRate(data.getInt(StringUtils.Entropy.Lake))));
            for (Item item : Utils.CoolDownItem) {
                player.getCooldowns().removeCooldown(item);
            }
        }
    }

    public static void AddManaDefenceDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void AddDefenceDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void AddDamageDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void addSlowDownEffect(Mob mob, int Tick, int Tier) {
        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Tick, Tier, false, false, false));
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void playerItemCoolDown(Player player, Item item, double Seconds) {
        double coolDownDecrease = PlayerAttributes.coolDownDecrease(player);
        player.getCooldowns().addCooldown(item, (int) (Seconds * 20 * (1 - coolDownDecrease)));
        if (Utils.powerTag.containsKey(item)) {
            if (!PowerLogic.playerPowerCoolDownRecord.containsKey(player))
                PowerLogic.playerPowerCoolDownRecord.put(player, new HashMap<>());
            Map<Item, Integer> map = PowerLogic.playerPowerCoolDownRecord.get(player);
            map.put(item, (int) (Seconds * 20 * (1 - coolDownDecrease)));

            PowerLogic.playerLastTimeReleasePowerCoolDownTime.put(player, (int) (Seconds * 20 * (1 - coolDownDecrease)));
        }
    }

    public static boolean playerManaCost(Player player, double manaCost) {
        if (PlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            if (ArmorCount.EarthMana(player) > 0) {
                playerHeal(player, manaCost * ArmorCount.EarthMana(player));
            }
            TabooManaArmor.storeCostToList(player, manaCost); //
            playerManaAddOrCost(player, -manaCost);
        }
        return true;
    }

    public static boolean playerManaCost(Player player, double manaCost, boolean IsMana) {
        if (PlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            PowerLogic.playerLastTimeReleasePowerManaCost.put(player, manaCost);
            if (ArmorCount.EarthMana(player) > 0) {
                playerHeal(player, manaCost * ArmorCount.EarthMana(player));
            }
            TabooManaArmor.storeCostToList(player, manaCost); //
            playerManaAddOrCost(player, -manaCost);
        }
        return true;
    }

    public static void formatBroad(Level level, Component type, Component content) {
        List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
        for (ServerPlayer player : playerList) {
            CompoundTag data = player.getPersistentData();
            if (type.getString().equals("副本") || type.getString().equals("黄金屋")) {
                if (!data.getBoolean(StringUtils.IgnoreType.Instance) && Utils.playerTeamMap.containsKey(player)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                            append(content));
                }
            } else {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                        append(content));

            }
        }
    }

    public static void sendFormatMSG(Player player, Component type, Component content) {
        if (player != null)
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                    append(content));
    }

    public static void msgSendToPlayer(Player player, Component content, int blank) {
        String blankString = " ".repeat(blank);
        player.sendSystemMessage(Component.literal(blankString).
                append(content));
    }

    public static Component MaterialRequirement(int index, Component materialType, int num) {
        return Component.literal(" " + index + ".").withStyle(ChatFormatting.GRAY).
                append(materialType).
                append(Component.literal("*" + num).withStyle(ChatFormatting.WHITE));

    }

    public static int itemStackCount(Player player, Item item) {
        Inventory inventory = player.getInventory();
        int ExistNum = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(item) && InventoryCheck.itemOwnerCorrect(inventory.player, itemStack))
                ExistNum += itemStack.getCount();
        }
        return ExistNum;
    }

    public static int itemStackCount(Inventory inventory, Item item) {
        int ExistNum = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(item) && InventoryCheck.itemOwnerCorrect(inventory.player, itemStack))
                ExistNum += itemStack.getCount();
        }
        return ExistNum;
    }

    public static boolean ItemStackCheck(Inventory inventory, Item item, int RequirementNum) {
        return itemStackCount(inventory, item) >= RequirementNum;
    }

    public static boolean itemStackRemoveIgnoreVB(Inventory inventory, Item item, int removeNum) {
        int num = removeNum;
        if (!ItemStackCheck(inventory, item, removeNum)) return false;
        else {
            LogUtils.getLogger().info("{} {} {} {}", inventory.player.getName().getString(), Utils.LogTypes.cost, item.toString(), removeNum);
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (inventory.getItem(i).is(item)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getCount() < num) {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i, itemStack);
                    } else {
                        itemStack.setCount(itemStack.getCount() - num);
                        inventory.setItem(i, itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean itemStackRemove(Inventory inventory, Item item, int removeNum) {
        int num = removeNum;
        if (!ItemStackCheck(inventory, item, removeNum)) return false;
        else {
            LogUtils.getLogger().info("{} {} {} {}", inventory.player.getName().getString(), Utils.LogTypes.cost, item.toString(), removeNum);
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (inventory.getItem(i).is(item)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getCount() < num) {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i, itemStack);
                    } else {
                        itemStack.setCount(itemStack.getCount() - num);
                        inventory.setItem(i, itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void ComponentAddLevelReward(List<Component> components, int rate) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        if (rate != 12) {
            components.add(Component.literal(" 当你获得下一等阶附身符时").
                    append(Component.literal("(" + (rate + 1) * 5 + ")").withStyle(ChatFormatting.LIGHT_PURPLE)));
            components.add(Component.literal("右键可以将附身符化为一些补给物资。"));
        } else {
            components.add(Component.literal("你在维瑞阿契已经有些时日了，感谢你的陪伴！").withStyle(ChatFormatting.WHITE));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal("不断地探索，使你的阅历逐渐提高。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("LevelReward").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
    }

    public static int ItemCheck(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.items.size(); i++) {
            if (!inventory.items.get(i).isEmpty() && inventory.getItem(i).is(itemStack.getItem())) return i;
        }
        return -1;
    }

    public static void broad(Level level, Component component) {
        PlayerList list = level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        for (Player player : list1) {
            player.sendSystemMessage(component);
        }
    }

    public static void broad(Level level, Component component, int blank) {
        PlayerList list = level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        String blankString = " ".repeat(blank);
        for (Player player : list1) {
            player.sendSystemMessage(Component.literal(blankString).append(component));
        }
    }

    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, ChatFormatting chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                        append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, Style chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                        append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void DescriptionOfBasic(List<Component> components) {
        components.add(Component.literal("β-基础属性:").withStyle(CustomStyle.styleOfPlain));
    }


    public static void DescriptionOfOnly(List<Component> components) {
        components.add(Component.literal("-唯一:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.GREEN));
    }

    public static void DescriptionOfAddition(List<Component> components) {
        components.add(Component.literal("α-额外属性:").withStyle(CustomStyle.styleOfPower));
    }

    public static void DescriptionNum(List<Component> components, String string, Component component, String string1) {
        components.add(Component.literal(string).withStyle(ChatFormatting.WHITE).
                append(component).append(string1).withStyle(ChatFormatting.WHITE));
    }

    public static void RandomPotionBagProvider(Player player, int MaxNum, double Rate) {
        int Count = 0;
        Random random = new Random();
        ItemStack itemStack[] = new ItemStack[13];
        itemStack[0] = ModItems.AttackUp_PotionBag.get().getDefaultInstance();
        itemStack[1] = ModItems.Breakdefenceup_potionBag.get().getDefaultInstance();
        itemStack[2] = ModItems.CoolDownDecreaseUp_PotionBag.get().getDefaultInstance();
        itemStack[3] = ModItems.CritDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[4] = ModItems.CritRateUp_PotionBag.get().getDefaultInstance();
        itemStack[5] = ModItems.defenceup_potionBag.get().getDefaultInstance();
        itemStack[6] = ModItems.HealStealUp_PotionBag.get().getDefaultInstance();
        itemStack[7] = ModItems.ManaBreakdefenceup_potionBag.get().getDefaultInstance();
        itemStack[8] = ModItems.ManaDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[9] = ModItems.Manadefenceup_potionBag.get().getDefaultInstance();
        itemStack[10] = ModItems.ManaReplyUp_PotionBag.get().getDefaultInstance();
        itemStack[11] = ModItems.SpeedUp_PotionBag.get().getDefaultInstance();
        itemStack[12] = ModItems.HealthRecover_PotionBag.get().getDefaultInstance();
        for (int i = 0; i < MaxNum; i++) {
            ItemStack TmpStack = itemStack[random.nextInt(13)];
            TmpStack.getOrCreateTagElement(Utils.MOD_ID);
            if (random.nextDouble(1) < Rate) TmpStack.setCount(random.nextInt(2, 4));
            player.addItem(TmpStack);
        }
    }

    public static boolean BlockLimitContainBlockPos(BlockPos blockPos) {
        boolean contains = false;
        PlayerCallBack removing = null;
        for (PlayerCallBack playerCallBack : Utils.playerCallBackList) {
            if (playerCallBack.getBlockPos().equals(blockPos)) {
                if (playerCallBack.getPlayer() == null || playerCallBack.getPlayer().position().distanceTo(playerCallBack.getBlockPos().getCenter()) > 8) {
                    contains = false;
                    removing = playerCallBack;
                } else {
                    ModNetworking.sendToClient(new CheckBlockLimitS2CPacket(), (ServerPlayer) playerCallBack.getPlayer());
                    contains = true;
                }
            }
        }
        Utils.playerCallBackList.remove(removing);
        return contains;
    }

    public static int BrewingLevel(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        String[] DataName = {
                "PlainBrewingExp",
                "ForestBrewingExp",
                "LakeBrewingExp",
                "VolcanoBrewingExp",
                "SnowBrewingExp",
                "SkyBrewingExp",
                "EvokerBrewingExp",
                "NetherBrewingExp"
        };
        boolean flag = true;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) <= 1500) flag = false;
        }
        if (flag) return 6;

        flag = true;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) <= 800) flag = false;
        }
        if (flag) return 5;

        int Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 400) Count++;
        }
        if (Count >= 6) return 4;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 200) Count++;
        }
        if (Count >= 5) return 3;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 100) Count++;
        }
        if (Count >= 5) return 2;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 50) Count++;
        }
        if (Count >= 4) return 1;
        return 0;
    }

    public static boolean BrewingLevelReward(Player player, int Level, CompoundTag data) {
        Random random = new Random();
        double[] LevelRate = {0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3};
        double ExpUp = PlayerAttributes.expUp(player);
        if (random.nextDouble() < LevelRate[Level]) {
            if (data.contains(InventoryCheck.owner)) {
                Compute.sendFormatMSG(player, Component.literal("酿造").withStyle(CustomStyle.styleOfBrew), Component.literal("你的酿造经验为你节省了这次酿造的材料消耗并为你提供了经验值。"));
                Compute.formatBroad(player.level(), Component.literal("酿造").withStyle(CustomStyle.styleOfBrew),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 完成了一次 ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("完美酿造").withStyle(CustomStyle.styleOfBrew)));
                Compute.givePercentExpToPlayer(player, 0.02, ExpUp, player.experienceLevel);
            }
            return true;
        }
        return false;
    }

    public static void TimeGive(String string, Player player) {
        Calendar calendar = Calendar.getInstance();
        int StringInt = Integer.parseInt(string);
        int timeCode = calendar.get(Calendar.YEAR) *
                calendar.get(Calendar.MONTH) *
                calendar.get(Calendar.DATE) +
                calendar.get(Calendar.MONTH) *
                        calendar.get(Calendar.DATE) +
                calendar.get(Calendar.DATE);
        int timeCode0 = calendar.get(Calendar.HOUR) *
                calendar.get(Calendar.YEAR) *
                calendar.get(Calendar.MONTH) *
                calendar.get(Calendar.DATE) + timeCode;
        if (StringInt == timeCode) {
            Utils.GemsForPlain = false;
            player.sendSystemMessage(Component.literal("~1"));
        }
        if (StringInt == timeCode0) {
            Utils.GemsForForest = false;
            player.sendSystemMessage(Component.literal("~2"));
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void itemStackGive(Player player, ItemStack itemStack) {
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (itemStack.getCount() > 0) {
            if (InventoryCheck.getBoundingList().contains(itemStack.getItem()))
                InventoryCheck.addOwnerTagToItemStack(player, itemStack);
            if (!PlayerIgnore.IgnoreItemGet(player)) {
                if (itemStack.getCount() > 1) {
                    sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("*" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)));
                } else {
                    sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
            }
            Inventory inventory = player.getInventory();
            if (inventory.getFreeSlot() != -1) {
                player.addItem(itemStack);
            } else {
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, player.level());
                itemEntity.setItem(itemStack);
                itemEntity.moveTo(player.position());
                player.level().addFreshEntity(itemEntity);
                sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                        Component.literal("背包已无空位，请注意。"));
            }
        }
    }

    public static class PlayerIgnore {
        public static boolean IgnoreItemGet(Player player) {
            return player.getPersistentData().contains(StringUtils.IgnoreType.ItemGet)
                    && player.getPersistentData().getBoolean(StringUtils.IgnoreType.ItemGet);
        }
    }

    public static void AttributeProvider(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for (int i = 0; i < Utils.AttributeName.length; i++) {
            if (dataI.contains(Utils.AttributeName[i])) {
                if (data.contains(Utils.AttributeName[i])) {
                    data.putDouble(Utils.AttributeName[i], data.getDouble(Utils.AttributeName[i]) + dataI.getDouble(Utils.AttributeName[i]));
                } else data.putDouble(Utils.AttributeName[i], dataI.getDouble(Utils.AttributeName[i]));
            }
        }
    }

    public static void AttributeDecrease(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for (int i = 0; i < Utils.AttributeName.length; i++) {
            if (dataI.contains(Utils.AttributeName[i])) {
                if (data.contains(Utils.AttributeName[i])) {
                    data.putDouble(Utils.AttributeName[i], data.getDouble(Utils.AttributeName[i]) - dataI.getDouble(Utils.AttributeName[i]));
                }
            }
        }
    }

    public static void RandomAttributeProvider(ItemStack stack, int num, double level, Player player) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            String Attribute = Utils.AttributeName[r.nextInt(Utils.AttributeName.length)];
            double BaseValue = Utils.AttributeMap.get(Attribute);
            data.putDouble(Attribute, data.getDouble(Attribute) + BaseValue * r.nextDouble(0.1, level));
        }
    }

    public static void VBIncomeAndMSGSend(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains("VB")) data.putDouble("VB", num);
        else data.putDouble("VB", data.getDouble("VB") + num);
        if (!data.getBoolean(StringUtils.IgnoreType.VB)) {
            sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                    Component.literal("你的账户收入:").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.RED)).
                            append(Component.literal("VB,").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("当前余额:").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f", data.getDouble("VB"))).withStyle(ChatFormatting.GOLD)));

        }
    }

    public static void VBExpenseAndMSGSend(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        data.putDouble("VB", data.getDouble("VB") - num);
        if (!data.getBoolean(StringUtils.IgnoreType.VB)) {
            sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                    Component.literal("你的账户支出:").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.GREEN)).
                            append(Component.literal("VB,").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("当前余额:").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f", data.getDouble("VB"))).withStyle(ChatFormatting.GOLD)));
        }
    }

    public static double CurrentVB(Player player) {
        return player.getPersistentData().getDouble("VB");
    }

    public static Vec3 getFaceCircleVec(Player player, double angle) {

        double r = 1;
        Vector3f pickVec3 = player.pick(10, 0, true).getLocation().toVector3f();
        Vector3f faceVec3 = player.pick(1, 0, true).getLocation().toVector3f();
        Vector3f vector3f = pickVec3.sub(faceVec3);
        Vec3 nVec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
        Vec3 iVec = new Vec3(1, 0, 0);
        Vec3 jVec = new Vec3(0, 1, 0);
        Vec3 kVec = new Vec3(0, 0, 1);
        Vec3 aVec;
        if (nVec.cross(iVec).length() == 0) {
            aVec = nVec.cross(jVec);
        } else aVec = nVec.cross(iVec);
        aVec = aVec.normalize();
        Vec3 bVec = nVec.cross(aVec).normalize();

        return new Vec3(r * Math.cos(angle) * aVec.x + r * Math.sin(angle) * bVec.x,
                r * Math.cos(angle) * aVec.y + r * Math.sin(angle) * bVec.y,
                r * Math.cos(angle) * aVec.z + r * Math.sin(angle) * bVec.z);

    }

    public static void PlayerPowerParticle(Player player) {
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ParticleTypes.WITCH, true,
                player.getX(),
                player.getY(),
                player.getZ(),
                0.5f,
                0.5f,
                0.5f,
                1,
                0);
        List<ServerPlayer> list = player.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer1 : list) {
            serverPlayer1.connection.send(clientboundLevelParticlesPacket);
        }
    }

    public static void MagmaPower(Entity entity, Level level, Player player) {
        List<ServerPlayer> playerList0 = entity.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer : playerList0) {
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.EXPLOSION_EMITTER, true, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0, 0, 1);
            serverPlayer.connection.send(clientboundLevelParticlesPacket);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.GENERIC_EXPLODE), SoundSource.PLAYERS, entity.getX(), entity.getY(), entity.getZ(), 1f, 1f, 0);
            serverPlayer.connection.send(clientboundSoundPacket);
        }
        ParticleProvider.RandomMoveParticle(entity, 1, 1, 24, ParticleTypes.ASH);
        ParticleProvider.RandomMoveParticle(entity, 1, 1, 24, ParticleTypes.LAVA);
        List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(entity.position(), 10, 10, 10));
        for (Mob mob : list) {
            Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, 3, true,
                    Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 60), serverPlayer));

        }
    }

    public static boolean DotIn(Vec2 dot, Vec2 dot1, Vec2 dot2, Vec2 dot3, Vec2 dot4) {
/*        Vec2 vec1 = Vec2Sub(dot1,dot2);
        Vec2 vec2 = Vec2Sub(dot2,dot3);
        Vec2 vec3 = Vec2Sub(dot3,dot4);
        Vec2 vec4 = Vec2Sub(dot4,dot1);
        if ((Vec2Cross(vec1,Vec2Sub(dot1,dot)) > 0 && Vec2Cross(vec2,Vec2Sub(dot2,dot)) > 0
                && Vec2Cross(vec3,Vec2Sub(dot3,dot)) > 0 && Vec2Cross(vec4,Vec2Sub(dot4,dot)) > 0 ) ||
                (Vec2Cross(vec1,Vec2Sub(dot1,dot)) < 0 && Vec2Cross(vec2,Vec2Sub(dot2,dot)) < 0
                        && Vec2Cross(vec3,Vec2Sub(dot3,dot)) < 0 && Vec2Cross(vec4,Vec2Sub(dot4,dot)) < 0 ) ) {
            return true;
        }
        return false;*/
        double a = (dot2.x - dot1.x) * (dot.y - dot1.y) - (dot2.y - dot1.y) * (dot.x - dot1.x);
        double b = (dot3.x - dot2.x) * (dot.y - dot2.y) - (dot3.y - dot2.y) * (dot.x - dot2.x);
        double c = (dot4.x - dot3.x) * (dot.y - dot3.y) - (dot4.y - dot3.y) * (dot.x - dot3.x);
        double d = (dot1.x - dot4.x) * (dot.y - dot4.y) - (dot1.y - dot4.y) * (dot.x - dot4.x);
        if ((a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0)) return true;
        return false;
    }

    public static double Vec2Cross(Vec2 vec1, Vec2 vec2) {
        return vec1.x * vec2.y - vec1.y * vec2.x;
    }

    public static Vec2 Vec2Sub(Vec2 vec1, Vec2 vec2) {
        Vec2 vec = new Vec2(vec2.x - vec1.x, vec2.y - vec1.x);
        return vec;
    }

    public static double SArmorMaxHealth(Player player) {
        double MaxHealthCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        return MaxHealthCount * 2;
    }

    public static double SArmorCoolDown(Player player) {
        double CoolDown = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        return CoolDown * 0.01f * 1.5f;
    }

    public static double SArmorAttackDamage(Player player) {
        double AttackDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        return AttackDamageCount * 1.5f;
    }

    public static double SArmorCritRate(Player player) {
        double CritRate = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        return CritRate * 0.01f * 1.5f;
    }

    public static double SArmorCritDamage(Player player) {
        double CritDamage = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        return CritDamage * 0.01f * 1.5f;
    }

    public static double SArmorManaDamage(Player player) {
        double ManaDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        return ManaDamageCount * 1.5f;
    }

    public static double SArmorHealSteal(Player player) {
        double HealSteal = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        return HealSteal * 0.01f * 1.5f;
    }

    public static int LightningArmorCount(Player player) {
        Item PlayerHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item PlayerChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item PlayerLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item PlayerBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        int LightningArmorCount = 0;
        if (PlayerHelmet instanceof LightningArmor) LightningArmorCount++;
        if (PlayerChest instanceof LightningArmor) LightningArmorCount++;
        if (PlayerLeggings instanceof LightningArmor) LightningArmorCount++;
        if (PlayerBoots instanceof LightningArmor) LightningArmorCount++;

        if (PlayerHelmet instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerChest instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerLeggings instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        if (PlayerBoots instanceof IntensifiedLightningArmor) LightningArmorCount += 2;
        return LightningArmorCount;
    }

    public static void CodeHitMonster(Level level, Mob monster, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !mobList.contains(monster)) mobList.add(monster);
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        for (Mob mob : mobList) {
            if (mob != monster) {
                mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());

                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                com.very.wraq.common.Utils.Struct.Gather gather = new Gather(20 * Effect, monster.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, monster.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }

    public static void CodeHitPlayer(Level level, Player hurter, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !playerList.contains(hurter)) playerList.add(hurter);
        for (Mob mob : mobList) {
            mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);

            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                Gather gather = new Gather(20 * Effect, hurter.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                if (player1 != hurter) player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, hurter.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }

    public static boolean RecallPlayerCheck(ServerPlayer serverPlayer) {
        if (Utils.kazeRecall.recallPlayer != null && Utils.kazeRecall.recallPlayer.equals(serverPlayer)) return true;
        if (Utils.spiderRecall.recallPlayer != null && Utils.spiderRecall.recallPlayer.equals(serverPlayer))
            return true;
        return false;
    }

    public static double Vec3Angle(Vec3 VecA, Vec3 VecB) {
        return acos(abs(VecA.dot(VecB)) / (VecA.length() * VecB.length()));
    }

    public static void AttackJudge(Player player) {
        ItemStack MainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (MainHandItem.is(ModItems.VolcanoSword0.get()) || MainHandItem.is(ModItems.VolcanoSword1.get())
                || MainHandItem.is(ModItems.VolcanoSword2.get()) || MainHandItem.is(ModItems.VolcanoSword4.get())
                || MainHandItem.is(ModItems.ManaSword.get()) || MainHandItem.is(ModItems.QuartzSword.get())
                || MainHandItem.is(ModItems.QuartzSabre.get()) || MainHandItem.is(ModItems.SeaSword0.get())
                || MainHandItem.is(ModItems.SeaSword1.get()) || MainHandItem.is(ModItems.SeaSword2.get())
                || MainHandItem.is(ModItems.SeaSword3.get()) || MainHandItem.is(ModItems.SeaSword4.get())
                || MainHandItem.is(ModItems.KazeSword0.get()) || MainHandItem.is(ModItems.KazeSword1.get())
                || MainHandItem.is(ModItems.KazeSword2.get()) || MainHandItem.is(ModItems.KazeSword3.get())
                || MainHandItem.is(ModItems.KazeSword4.get())) {
            ModNetworking.sendToServer(new SwordAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.RangeAttackCount = 10;
            ClientUtils.RangeAttackAnimationCount = 14;
        }
        if (MainHandItem.is(ModItems.MineSword0.get()) || MainHandItem.is(ModItems.MineSword1.get())
                || MainHandItem.is(ModItems.MineSword2.get()) || MainHandItem.is(ModItems.MineSword3.get())
                || MainHandItem.is(ModItems.SnowSword0.get()) || MainHandItem.is(ModItems.SnowSword1.get())
                || MainHandItem.is(ModItems.SnowSword2.get()) || MainHandItem.is(ModItems.SnowSword3.get())
                || MainHandItem.is(ModItems.SnowSword4.get())) {
            ModNetworking.sendToServer(new PickAxeAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.PickAxeAttackCount = 10;
            ClientUtils.PickAxeAttackAnimationCount = 14;
        }
    }

    public static void RateItemStackGive(ItemStack itemStack, double rate, Player player) throws IOException {
        Random r = new Random();
        double totalRate = rate;
        if (totalRate > 1) {
            int Count = (int) totalRate;
            if (r.nextDouble() < totalRate % 1) Count++;
            itemStackGive(player, new ItemStack(itemStack.getItem(), Count));
        } else {
            if (r.nextDouble(1.0d) < rate) {
                if (rate <= 0.01) {
                    ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_LEVELUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 1, 0);
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.connection.send(clientboundSoundPacket);
                    Compute.formatBroad(player.level(), Component.literal("稀有掉落").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
                itemStackGive(player, itemStack);
            }
        }
    }

    public static int SwordSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Sword);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int BowSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Bow);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int ManaSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Mana);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static void ChargingModule(CompoundTag data, Player player) {
        if (Compute.SwordSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new SwordSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new ManaSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data, 13) > 0)
            ModNetworking.sendToClient(new ManaSkill13S2CPacket(8), (ServerPlayer) player);
        if (Compute.BowSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new BowSkill12S2CPacket(8), (ServerPlayer) player);
    }

    public static void SetMobCustomName(Mob mob, Item ArmorItem, Component component) {
        int Level = Utils.mobLevel.get(ArmorItem).intValue();
        if (Level < 25) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.GREEN)
                    .append(component));
        } else if (Level < 50) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.BLUE)
                    .append(component));
        } else if (Level < 75) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RED)
                    .append(component));
        } else if (Level < 100) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.LIGHT_PURPLE)
                    .append(component));
        } else if (Level < 125) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfEntropy)
                    .append(component));
        } else if (Level < 175) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfCastle)
                    .append(component));
        } else if (Level < 200) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfPurpleIron)
                    .append(component));
        } else if (Level < 225) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfMoon1)
                    .append(component));
        }
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0);
        mob.getAttribute(Attributes.ARMOR).setBaseValue(0);
    }

    public static void SetMobCustomName(Mob mob, Component component, int level) {
        Style[] styles = {
                Style.EMPTY.applyFormat(ChatFormatting.GREEN),
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), // 25 - 50
                Style.EMPTY.applyFormat(ChatFormatting.RED), // 50 - 75
                Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), // 75 - 100
                CustomStyle.styleOfEntropy, // 125
                CustomStyle.styleOfEntropy, // 150
                CustomStyle.styleOfCastle, // 175
                CustomStyle.styleOfPurpleIron, // 200
                CustomStyle.styleOfMoon1, // 225
                CustomStyle.styleOfWorld, // 250
                CustomStyle.styleOfSakura // 275
        };
        mob.setCustomName(Component.literal("Lv." + level + " ").withStyle(styles[level / 25])
                .append(component));
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0);
        mob.getAttribute(Attributes.ARMOR).setBaseValue(0);
    }

    public static double ExtractRate(int ExtractLevel) {
        return Math.min(Math.min(0.1, ExtractLevel * 0.001)
                        + Math.max(0, (ExtractLevel - 100) * 0.0002)
                        + Math.max(0, (ExtractLevel - 1000) * 0.002)
                , 0.33);
    }

    public static double LevelSuppress(Player player, Mob monster) {
        int mobLevel = MobSpawn.MobBaseAttributes.xpLevel.getOrDefault(MobSpawn.getMobOriginName(monster), 0);
        return (player.experienceLevel - mobLevel) / 500d;
    }

    public static double defenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {
        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;
        if (DefenceAfterCompute < 0) return 2 - (2000 / (2000 - DefenceAfterCompute));
        return (2000 / (2000 + DefenceAfterCompute));
    }

    public static double manaDefenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {
        // DefencePenetration = 百分比穿透 // DefencePenetration0 = 固定穿透
        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;
        // DefenceAfterCompute = 计算完穿透的护甲/魔抗值

        if (DefenceAfterCompute < 0) return 2 - (2000 / (2000 - DefenceAfterCompute));

        return (2000 / (2000 + DefenceAfterCompute));
    }

    public static void CoolDownTimeDescription(List<Component> components, int seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void CoolDownTimeDescription(List<Component> components, double seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.1f", seconds) + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void ManaCostDescription(List<Component> components, int num) {
        components.add(Component.literal(" 法力消耗:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(num + "").withStyle(ChatFormatting.LIGHT_PURPLE)));
    }

    public static ItemStack FoilAddItemStack(ItemStack itemStack) {
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
        map.put(Enchantments.UNBREAKING, 1);
        EnchantmentHelper.setEnchantments(map, itemStack);
        return itemStack;
    }

    public static Boolean onSky(LivingEntity entity) {
        int X = entity.getBlockX();
        int Y = entity.getBlockY();
        int Z = entity.getBlockZ();
        return entity.level().getBlockState(new BlockPos(X, Y - 2, Z)) == Blocks.AIR.defaultBlockState()
                || entity.level().getBlockState(new BlockPos(X, Y - 1, Z)) == Blocks.AIR.defaultBlockState();
    }

    public static void EmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionAttackSpeed(List<Component> components, double AttackSpeed) {
        components.add(Component.literal(Utils.Emoji.AttackSpeed + " 攻击速度").withStyle(CustomStyle.styleOfSky).
                append(Component.literal(" " + String.format("%.1f", (4 + AttackSpeed))).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionCritRate(List<Component> components, double CriticalHitRate) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f%%", CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionCritRate(List<Component> components, double CriticalHitRate, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f%%", CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 法术吸血").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionHealSteal(List<Component> components, double HealSteal, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f‱", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionCommonMovementSpeed(List<Component> components, double MovementSpeed) {
        if (MovementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.1f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format("%.1f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

        }
    }

    public static void SoulEmojiDescriptionMovementSpeed(List<Component> components, double MovementSpeed, int ForgeTimes) {
        if (MovementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));

        }
    }

    public static void EmojiDescriptionAttackRange(List<Component> components, double AttackRangeUp) {
        components.add(Component.literal(Utils.Emoji.AttackRange + " 攻击距离").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("+" + String.format("%.2f", AttackRangeUp)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void ManaCostDescription(List<Component> components, double ManaCost) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulManaCostDescription(List<Component> components, double ManaCost, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("- " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaCost)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaPenetration(List<Component> components, double ManaPenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaPenetration(List<Component> components, double ManaPenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.ManaPenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaPenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaRecover(List<Component> components, double ManaRecover) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f", ManaRecover)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaRecover(List<Component> components, double ManaRecover, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", ManaRecover)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaRecover)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionMaxHealth(List<Component> components, double MaxHealth) {
        if (MaxHealth < 0) {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("-" + String.format("%.0f", -MaxHealth)).withStyle(ChatFormatting.RED)));

        } else {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f", MaxHealth)).withStyle(ChatFormatting.WHITE)));
        }
    }


    public static void EmojiDescriptionHealAmplification(List<Component> components, double HealEffect) {
        components.add(Component.literal(Utils.Emoji.HealthAmplification + " 治疗强度").withStyle(CustomStyle.styleOfHealth).
                append(Component.literal("+" + String.format("%.0f%%", HealEffect * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExpUp(List<Component> components, double ExpUp) {
        components.add(Component.literal(Utils.Emoji.ExpUp + " 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionLuckyUp(List<Component> components, double Lucky) {
        components.add(Component.literal(Utils.Emoji.Lucky + " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", Lucky * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefenceRate(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲加成").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", Defence * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExAttackDamageRate(List<Component> components, double ExDamageRate) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f%%", ExDamageRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionCoolDown(List<Component> components, double CoolDown) {
        components.add(Component.literal(Utils.Emoji.CoolDown + " 技能急速").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", CoolDown * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 基础护甲").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionHealthRecover(List<Component> components, double HealthReplay) {
        components.add(Component.literal(Utils.Emoji.HealthRecover + " 生命回复").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format("%.1f", HealthReplay)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExAttackDamage(List<Component> components, double ExDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f", ExDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法抗性").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionMaxMana(List<Component> components, double MaxMana) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionMaxMana(List<Component> components, double MaxMana, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionSwiftness(List<Component> components, double Swiftness) {
        components.add(Component.literal(Utils.Emoji.Swiftness + " 迅捷").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format("%.2f", Swiftness)).withStyle(ChatFormatting.WHITE)));
    }

    public static void DescriptionModuleSword(ItemStack itemStack, List<Component> components, double BaseDamage) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double ExDamageForging = 0;
        if (data.contains("Forging")) ExDamageForging = forgingValue(data, BaseDamage);
        double ExDamageProficiency = 0;
        if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * (data.getInt("KillCount") / 100000.0);
        ChatFormatting[] chatFormattings = {
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.YELLOW,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.RED
        };
        if (data.contains("Forging")) {
            components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageForging)).withStyle(ChatFormatting.YELLOW)));
        } else components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal(" ")).
                append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])));
    }

    public static void DescriptionModuleSceptre(ItemStack itemStack, List<Component> components, double BaseDamage) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double ExDamageForging = 0;
        if (data.contains("Forging")) ExDamageForging = forgingValue(data, BaseDamage);
        double ExDamageProficiency = 0;
        if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * (data.getInt("KillCount") / 100000.0);
        ChatFormatting[] chatFormattings = {
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.YELLOW,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.RED
        };
        if (data.contains("Forging")) {
            components.add(Component.literal(Utils.Emoji.Sword + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageForging)).withStyle(ChatFormatting.BLUE)));
        } else components.add(Component.literal(Utils.Emoji.Sword + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal(" ")).
                append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])));

    }

    public static void BasicSwordDescription(ItemStack itemStack, List<Component> components, double BaseDamage, double BreakDefence,
                                             double CriticalHitRate, double CriticalHitDamage, double HealSteal, double SpeedUp) {
        DescriptionModuleSword(itemStack, components, BaseDamage);
        EmojiDescriptionDefencePenetration(components, BreakDefence);
        EmojiDescriptionCritRate(components, CriticalHitRate);
        EmojiDescriptionCritDamage(components, CriticalHitDamage);
        EmojiDescriptionHealSteal(components, HealSteal);
        EmojiDescriptionCommonMovementSpeed(components, SpeedUp);
    }

    public static class AttributeDescription {

        public static Component ExpUp(String content) {
            return Component.literal(Utils.Emoji.ExpUp + " " + content + "经验加成").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component MaxHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "最大生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component LossHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "已损失生命值").withStyle(ChatFormatting.DARK_GREEN);
        }

        public static Component Swiftness(String content) {
            return Component.literal(Utils.Emoji.Swiftness + " " + content + "迅捷").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeedWithoutBattle(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "脱战移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component ExMovementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "额外移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component MovementSpeedDecrease(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.RED);
        }

        public static Component ManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaCost(String content) {
            return Component.literal(Utils.Emoji.ManaCost + " " + content + "法力消耗").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ExManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "额外魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaRecover(String content) {
            return Component.literal(Utils.Emoji.ManaRecover + " " + content + "法力回复").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component Health(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component healValue(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "治疗量").withStyle(ChatFormatting.GREEN);
        }

        public static Component Defence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲").withStyle(ChatFormatting.GRAY);
        }

        public static Component HealAmplification(String content) {
            return Component.literal(Utils.Emoji.HealthAmplification + " " + content + "治疗强度").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component HealthRecover(String content) {
            return Component.literal(Utils.Emoji.HealthRecover + " " + content + "生命回复").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component ManaDefence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "魔法抗性").withStyle(ChatFormatting.BLUE);
        }

        public static Component ExAttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "额外攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component DefencePenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲穿透").withStyle(ChatFormatting.GRAY);
        }

        public static Component CritDamage(String content) {
            return Component.literal(Utils.Emoji.CritDamage + " " + content + "暴击伤害").withStyle(ChatFormatting.BLUE);
        }

        public static Component CritRate(String content) {
            return Component.literal(Utils.Emoji.CritRate + " " + content + "暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component AttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaPenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "法术穿透").withStyle(ChatFormatting.BLUE);
        }

        public static Component CoolDown(String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "技能急速").withStyle(ChatFormatting.AQUA);
        }

        public static Component HealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "生命偷取").withStyle(ChatFormatting.RED);
        }

        public static Component SkillHealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "全能吸血").withStyle(CustomStyle.styleOfField);
        }

        public static Component ManaHealSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(CustomStyle.styleOfMana);
        }

        public static Component MaxMana(String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "法力值").withStyle(CustomStyle.styleOfMana);
        }

        public static Component ExHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "额外生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component AttackRange(String content) {
            return Component.literal(Utils.Emoji.AttackRange + " " + content + "攻击距离").withStyle(CustomStyle.styleOfSea);
        }

        public static Component AttackDamageValue(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "物理伤害").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaDamageValue(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法伤害").withStyle(ChatFormatting.LIGHT_PURPLE);
        }
    }

    public static void SuitDescription(List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.AQUA));
    }

    public static void SuitDoubleDescription(List<Component> components) {
        components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
    }

    public static void SuitDoubleDescription(List<Component> components, int Count) {
        if (Count >= 2)
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
        else
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuitQuadraDescription(List<Component> components) {
        components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void SuitQuadraDescription(List<Component> components, int Count) {
        if (Count >= 4)
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
        else
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuffixOfMainStoryI(List<Component> components) {
        components.add(Component.literal("ChapterI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfMainStoryI() {
        return Component.literal("ChapterI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC);
    }

    public static void SuffixOfMainStoryII(List<Component> components) {
        components.add(Component.literal("ChapterII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryIII(List<Component> components) {
        components.add(Component.literal("ChapterIII").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryV(List<Component> components) {
        components.add(Component.literal("ChapterV").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMoon(List<Component> components) {
        components.add(Component.literal("MoonPalace").withStyle(CustomStyle.styleOfMoon).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfCastle(List<Component> components) {
        components.add(Component.literal("BlackCastle").withStyle(CustomStyle.styleOfCastle).withStyle(ChatFormatting.ITALIC));
    }

    public static String GetRGB(int r, int g, int b) {
        char[] chars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        return "#" + chars[r / 16] + chars[r % 16] + chars[g / 16] + chars[g % 16] + chars[b / 16] + chars[b % 16];
    }

    public static List<Color> colorList = new ArrayList<>() {{
        add(new Color(new Color.RGB(0, 255, 0), new Color.RGB(0, 255, 255), 100));
        add(new Color(new Color.RGB(0, 255, 255), new Color.RGB(255, 0, 0), 100));
        add(new Color(new Color.RGB(255, 0, 0), new Color.RGB(128, 128, 128), 100));
        add(new Color(new Color.RGB(128, 128, 128), new Color.RGB(1, 255, 255), 100));
        add(new Color(new Color.RGB(1, 255, 255), new Color.RGB(100, 149, 237), 100));
        add(new Color(new Color.RGB(100, 149, 237), new Color.RGB(0, 255, 127), 100));
        add(new Color(new Color.RGB(0, 255, 127), new Color.RGB(0, 255, 0), 100));
    }};

    public static Map<Color.RGB, Color.RGB> colorMap = new HashMap<>() {{
        put(new Color.RGB(0, 255, 0), new Color.RGB(0, 255, 255));
        put(new Color.RGB(0, 255, 255), new Color.RGB(255, 0, 0));
        put(new Color.RGB(255, 0, 0), new Color.RGB(128, 128, 128));
        put(new Color.RGB(128, 128, 128), new Color.RGB(1, 255, 255));
        put(new Color.RGB(1, 255, 255), new Color.RGB(100, 149, 237));
        put(new Color.RGB(100, 149, 237), new Color.RGB(0, 255, 127));
        put(new Color.RGB(0, 255, 127), new Color.RGB(0, 255, 0));
    }};

    public static void suffixOfElement(List<Component> components) {
        components.add(getSuffixOfElement());
    }

    public static Component getSuffixOfElement() {
        for (int i = 0; i < colorList.size(); i++) {
            Color color = colorList.get(i);
            if (color.Add()) {
                colorList.set(i, new Color(color.targetRGB, colorMap.get(color.targetRGB), 100));
            }
        }
        return Component.literal("E").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(0).getRGB()))).withStyle(ChatFormatting.ITALIC).
                append(Component.literal("l").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(1).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(2).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("m").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(3).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(4).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("n").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(5).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("t").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(6).getRGB()))).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfPurpleIronKnight(List<Component> components) {
        components.add(Component.literal("PurpleIronKnight").withStyle(CustomStyle.styleOfPurpleIron).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfIce(List<Component> components) {
        components.add(Component.literal("Ice").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfWorldSoul(List<Component> components) {
        components.add(Component.literal("WorldSoul").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryIV(List<Component> components) {
        components.add(Component.literal("MainStoryIV").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryVII(List<Component> components) {
        components.add(Component.literal("MainStoryVII").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC));
    }

    public static class MaterialLevelDescription {
        public static void Low(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.GREEN));
        }

        public static void Normal(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.YELLOW));
        }

        public static void Rare(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.AQUA));
        }

        public static void Epic(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

    public static void RuneAttributeDescription(List<Component> components) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append("符石属性:").withStyle(ChatFormatting.WHITE));
    }

    public static void DescriptionPassive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("被动 ").withStyle(ChatFormatting.GREEN)).
                append(name));
    }

    public static void solePassiveDescription(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("唯一被动 ").withStyle(ChatFormatting.GREEN)).
                append(name));
    }

    public static void DescriptionActive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("主动 ").withStyle(ChatFormatting.AQUA)).
                append(name));
    }

    public static double forgingValue(ItemStack itemStack, double baseValue) {
        if (itemStack.getTagElement(Utils.MOD_ID) == null) return 0;
        return forgingValue(itemStack.getOrCreateTagElement(Utils.MOD_ID), baseValue);
    }

    public static double forgingValue(CompoundTag data, double BaseValue) {
        int forgingLevel = data.getInt("Forging");
        if (data.contains(StringUtils.QingMingForgePaper)) ++forgingLevel;
        if (data.contains(StringUtils.LabourDayForgePaper)) ++forgingLevel;

        if (forgingLevel <= 10) {
            return 2 * forgingLevel + BaseValue * 0.02f * forgingLevel;
        } else if (forgingLevel <= 20) {
            return 20 + 4 * (forgingLevel - 10) + BaseValue * (0.04f * (forgingLevel - 10) + 0.2f);
        } else if (forgingLevel <= 24) {
            return 60 + BaseValue * (0.2f * (forgingLevel - 20) + 0.6f);
        } else {
            return 60 + BaseValue * (1.4 + (forgingLevel - 24) * 0.4);
        }
    }

    public static double proficiencyValue(ItemStack itemStack, double baseValue) {
        if (itemStack.getTagElement(Utils.MOD_ID) == null) return 0;
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.KillCount.KillCount)) {
            return baseValue * 0.5 *
                    Math.min(1, (data.getInt(StringUtils.KillCount.KillCount) / 100000.0));
        }
        return 0;
    }

    public static double SakuraDemonSword(Player player, double DamageBeforeDefence) {
        double DamageInfluence = 0;
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount) {
            DamageInfluence += DamageBeforeDefence * 0.5f;
        }
        return DamageInfluence;
    }

    public static class ArmorCount {
        public static int Star(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.StarHelmet.get())) Count++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.StarLeggings.get())) Count++;

            return Count;
        }

        public static int Moon(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MoonHelmet.get())) Count++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MoonLeggings.get())) Count++;

            return Count;
        }

        public static int Plain(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PlainArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PlainArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PlainArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PlainArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.PlainBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest4) > 0) Count++;
            return Count;
        }

        public static int plainCountWithoutCrest(Player player) {
            int count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PlainArmorHelmet.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PlainArmorChest.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PlainArmorLeggings.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PlainArmorBoots.get())) count++;
            return count;
        }

        public static int Forest(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ForestArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ForestArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ForestArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ForestArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.ForestBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest4) > 0) Count++;
            return Count;
        }

        public static int forestCountWithoutCrest(Player player) {
            int count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ForestArmorHelmet.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ForestArmorChest.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ForestArmorLeggings.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ForestArmorBoots.get())) count++;
            return count;
        }

        public static int Lake(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LakeArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LakeArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LakeArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LakeArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.LakeBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest4) > 0) Count++;
            return Count;
        }

        public static int lakeCountWithoutCrest(Player player) {
            int count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LakeArmorHelmet.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LakeArmorChest.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LakeArmorLeggings.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LakeArmorBoots.get())) count++;
            return count;
        }

        public static int Volcano(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.VolcanoArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.VolcanoArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.VolcanoArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.VolcanoArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.VolcanoBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest4) > 0) Count++;
            return Count;
        }

        public static int volcanoCountWithoutCrest(Player player) {
            int count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.VolcanoArmorHelmet.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.VolcanoArmorChest.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.VolcanoArmorLeggings.get())) count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.VolcanoArmorBoots.get())) count++;
            return count;
        }

        public static int LifeMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBoots.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest4) > 0) Count++;
            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) Count++;
            return Count;
        }

        public static int LifeManaE(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmetE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChestE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggingsE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBootsE.get())) Count++;
            return Count;
        }

        public static int ObsiMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBoots.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest4) > 0) Count++;
            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) Count++;
            return Count;
        }

        public static int ObsiManaE(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmetE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChestE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggingsE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBootsE.get())) Count++;
            return Count;
        }

        public static int Mine(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MineArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.MineArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MineArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.MineArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.MineBracelet.get())) Count++;

            if (player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest4) > 0) Count++;

            return Count;
        }

        public static int Snow(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SnowArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SnowArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SnowArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SnowArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SnowBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest4) > 0) Count++;
            return Count;
        }

        public static int Sky(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SkyArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SkyArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SkyArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SkyArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SkyBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest3) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest4) > 0) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Nether(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.NetherPower.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Nether) > 0) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Leather(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LeatherArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LeatherArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LeatherArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LeatherArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int PurpleIron(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PurpleIronArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PurpleIronArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PurpleIronArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PurpleIronArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Ice(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.IceArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.IceArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.IceArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.IceArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int NetherMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherManaArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.NetherSceptre.get())) Count += 2;
            if (Count > 6) return 6;
            return Count;
        }

        public static int SpringAttack(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringAttackArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringAttackArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringAttackArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringAttackArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int SpringSwift(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringSwiftArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringSwiftArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringSwiftArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringSwiftArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int SpringMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int EarthMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.EarthManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.EarthManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.EarthManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.EarthManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int BloodMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BloodManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BloodManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.BloodManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.BloodManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleAttack(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleAttackHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleAttackChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleAttackLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleAttackBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleSwift(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleSwiftHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleSwiftChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleSwiftLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleSwiftBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleManaHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleManaChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleManaLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleManaBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }
    }

    public static double SkySuitEffectRate(Player player) {
        int Count = ArmorCount.Sky(player);
        switch (Count) {
            case 1 -> {
                return 0.2f;
            }
            case 2 -> {
                return 0.5f;
            }
            case 3 -> {
                return 1.4f;
            }
            case 4 -> {
                return 2;
            }
        }
        return 0;

    }

    public static double NetherSuitEffectRate(Player player) {
        int Count = ArmorCount.Nether(player);
        switch (Count) {
            case 1 -> {
                return 0.2f;
            }
            case 2 -> {
                return 0.5f;
            }
            case 3 -> {
                return 0.7f;
            }
            case 4 -> {
                return 1;
            }
        }
        return 0;
    }

    public static void BallParticle(Vec3 vec3, Level level, double r, ParticleOptions particleOptions, int N) {
        for (int i = 0; i <= N / 4; i++) {
            double v = (r / (N / 4.0) * i) * (r / (N / 4.0) * i);
            ParticleProvider.VerticleCircleParticle(vec3, level, r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
            ParticleProvider.VerticleCircleParticle(vec3, level, -r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
        }
    }

    public static void BallParticle(ServerPlayer serverPlayer, Vec3 vec3, double r, ParticleOptions particleOptions, int N) {
        for (int i = 0; i <= N / 4; i++) {
            double v = (r / (N / 4.0) * i) * (r / (N / 4.0) * i);
            ParticleProvider.VerticleCircleParticle(vec3, serverPlayer, r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
            ParticleProvider.VerticleCircleParticle(vec3, serverPlayer, -r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
        }
    }

    public static double EntropyRate(int EntropyLevel) {
        int Level = 0;
        if (EntropyLevel <= 10) {
            return EntropyLevel;
        } else {
            Level += 10;
            for (int i = 1; i <= 8; i++) {
                double TmpNum1 = Math.pow(10, i);
                double TmpNum2 = Math.pow(10, i) * 9;
                if (EntropyLevel > TmpNum1) {
                    Level += (EntropyLevel - TmpNum1) * (TmpNum1 / TmpNum2);
                }
            }
        }
        return Level;
    }

    public static void EntropyPacketSend(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        CompoundTag data = serverPlayer.getPersistentData();
        if (data.contains(StringUtils.Entropy.Forest) && data.getInt(StringUtils.Entropy.Forest) > 0
                || data.contains(StringUtils.Entropy.Volcano) && data.getInt(StringUtils.Entropy.Volcano) > 0
                || data.contains(StringUtils.Entropy.Lake) && data.getInt(StringUtils.Entropy.Lake) > 0
                || data.contains(StringUtils.Entropy.Sky) && data.getInt(StringUtils.Entropy.Sky) > 0) {
            ModNetworking.sendToClient(new EntropyS2CPacket(data.getInt(StringUtils.Entropy.Forest),
                    data.getInt(StringUtils.Entropy.Volcano),
                    data.getInt(StringUtils.Entropy.Lake),
                    data.getInt(StringUtils.Entropy.Sky),
                    data.getInt(StringUtils.Entropy.Snow)
            ), serverPlayer);
        }
    }

    public static Component DescriptionWhiteColor(String content) {
        return Component.literal(content).withStyle(ChatFormatting.WHITE);
    }

    public static void playerHeal(Player player, double Num) {
        if (Num < 0) return;
        double healNum = Num * (PlayerAttributes.healEffectUp(player));
        healNum = Math.min(healNum, player.getMaxHealth() - player.getHealth());
        LifeElementSword.StoreToList(player, healNum);
        player.heal((float) healNum);
    }

    public static void healByHealthSteal(Player player, double num) {
        double rate = 1;
        playerHeal(player, num * rate * 0.1);
    }

    public static void Proficiency(ItemStack equip, Item monsterhelmet) {
        if (Utils.mainHandTag.containsKey(equip.getItem()) && !Compute.IsSoulEquip(equip) && Utils.mobLevel.containsKey(monsterhelmet)) {
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (!dataI.contains(StringUtils.KillCount.KillCount))
                dataI.putInt(StringUtils.KillCount.KillCount, Utils.mobLevel.get(monsterhelmet).intValue() * 5);
            else
                dataI.putInt(StringUtils.KillCount.KillCount, Math.min(Integer.MAX_VALUE, dataI.getInt(StringUtils.KillCount.KillCount) + Utils.mobLevel.get(monsterhelmet).intValue() * 5));
        }
    }

    public static void Proficiency(Player player, int xpLevel) {
        ItemStack equip = player.getMainHandItem();
        if (Utils.mainHandTag.containsKey(equip.getItem()) && !Compute.IsSoulEquip(equip)) {
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            InventoryCheck.addOwnerTagToItemStack(player, equip);
            if (!dataI.contains(StringUtils.KillCount.KillCount))
                dataI.putInt(StringUtils.KillCount.KillCount, xpLevel * 5);
            else
                dataI.putInt(StringUtils.KillCount.KillCount, Math.min(Integer.MAX_VALUE, dataI.getInt(StringUtils.KillCount.KillCount) + xpLevel * 5));
        }
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, Style MainStyle) {
        if (player.getItemBySlot(equipmentSlot).is(item)) {
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
            return 1;
        } else
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, ChatFormatting MainStyle) {
        if (player.getItemBySlot(equipmentSlot).is(item)) {
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
            return 1;
        } else
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static void playerItemUseWithRecord(Player player) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        LogUtils.getLogger().info("{} {} {}", player.getName().getString(), Utils.LogTypes.itemUsed, itemStack);
        itemStack.shrink(1);
    }

    public static void PlayerItemUseOffHandWithRecord(Player player) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        LogUtils.getLogger().info("{} {} {}", player.getName().getString(), Utils.LogTypes.itemUsed, itemStack);
        itemStack.shrink(1);
    }

    public static void PlayerItemDeleteWithRecord(Player player, ItemStack itemStack) {

    }

    public static void playerItemUseWithRecord(Player player, int Num) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        itemStack.setCount(itemStack.getCount() - Num);
    }

    public static class Damage {

        public static void causeAutoAdaptionRateDamageToMob(Player player, Mob mob, double rate) {
            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();
            if (Utils.swordTag.containsKey(item) || Utils.bowTag.containsKey(item))
                AttackDamageToMonster_RateAdDamage(player, mob, rate * 2);
            if (Utils.sceptreTag.containsKey(item))
                ManaDamageToMonster_RateApDamage(player, mob, rate, false);
        }

        public static double DamageIgNoreDefenceToMonster(Player player, Mob monster, double Damage) {
            CompoundTag data = player.getPersistentData();

            double DamageEnhance = 0;
            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装

            Damage *= (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));

            Compute.SummonValueItemEntity(monster.level(), player, monster,
                    Component.literal(String.format("%.0f", Damage)).withStyle(CustomStyle.styleOfSea), 2);
            DirectDamageToMob(player, monster, Damage);
            return Damage;
        }

        public static double IgnoreDefenceDamageToMonster_Direct(Player player, Mob monster, double damage) {
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(CustomStyle.styleOfSea), 2);
            DirectDamageToMob(player, monster, damage);
            return damage;
        }

        public static double AttackDamageToMonster_RateAdDamage(Player player, Mob monster, double num) {
            double Defence = MobAttributes.defence(monster);
            double BaseDamage = PlayerAttributes.attackDamage(player);
            double BreakDefence = PlayerAttributes.defencePenetration(player);
            double BreakDefence0 = PlayerAttributes.defencePenetration0(player);
            double DamageEnhance = 0;

            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += DamageInfluence.PlayerAttackDamageEnhance(player);
            DamageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正

            BaseDamage *= (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));
            BaseDamage *= defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", BaseDamage * num)).withStyle(ChatFormatting.YELLOW), 0);
            DirectDamageToMob(player, monster, BaseDamage * num);
            return BaseDamage * num;
        }

        public static double AttackDamageToMonster_AdDamage(Player player, Mob monster, double damage) {
            double defence = MobAttributes.defence(monster);
            double defencePenetration = PlayerAttributes.defencePenetration(player);
            double defencePenetration0 = PlayerAttributes.defencePenetration0(player);
            double damageEnhance = 0;

            damageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            damageEnhance += DamageInfluence.PlayerAttackDamageEnhance(player);
            damageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正

            damage *= DamageInfluence.MonsterControlDamageEffect(player, monster);

            damage *= (1 + damageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));
            damage *= defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
            DirectDamageToMob(player, monster, damage);
            return damage;
        }

        public static double AttackDamageToMonster_AdDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
            if (computeDefenceOrEnhance) {
                damage *= defenceDamageDecreaseRate(MobAttributes.defence(monster), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
                damage *= (1 + DamageInfluence.PlayerAttackDamageEnhance(player));
            }
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
            DirectDamageToMob(player, monster, damage);
            return damage;
        }

        public static double attackDamageToMonsterOnlyComputeDefence(Player player, Mob mob, double damage) {
            damage *= defenceDamageDecreaseRate(MobAttributes.defence(mob), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
            Compute.SummonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
            DirectDamageToMob(player, mob, damage);
            return damage;
        }

        public static double ManaDamageToMonster_ApDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
            if (computeDefenceOrEnhance) {
                damage *= defenceDamageDecreaseRate(MobAttributes.manaDefence(monster), PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player));
                damage *= (1 + DamageInfluence.PlayerManaDamageEnhance(player));
            }
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
            DirectDamageToMob(player, monster, damage);
            return damage;
        }

        public static double AttackDamageToPlayer_RateAdDamage(Player player, Player hurter, double num) {

            double Defence = PlayerAttributes.defence(hurter);
            double BaseDamage = PlayerAttributes.attackDamage(player);
            double BreakDefence = PlayerAttributes.defencePenetration(player);
            double BreakDefence0 = PlayerAttributes.defencePenetration0(player);
            double DamageEnhance = 0;
            CompoundTag data = player.getPersistentData();

            DamageEnhance += Compute.SwordSkillLevelGet(data, 4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
            DamageEnhance -= (1 - defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

            DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
            return BaseDamage * num * (1 + DamageEnhance);
        }

        public static void ManaDamageToMonster_RateApDamage(Player player, Mob monster, double num, boolean isPower) {
            CompoundTag data = player.getPersistentData();

            double Defence = MobAttributes.manaDefence(monster);
            double BaseDamage = PlayerAttributes.manaDamage(player) * num;
            double BreakDefence = PlayerAttributes.manaPenetration(player);
            double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += DamageInfluence.PlayerManaDamageEnhance(player); // 魔法伤害提升
            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + BaseDamage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }
            BaseDamage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            ExDamage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            double totalDamage = (BaseDamage + ExDamage) * (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.life, 0));
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, totalDamage);
                    Element.entityElementUnit.put(player.getId(), new Element.Unit(Element.life, 0));
                }
            }

            totalDamage *= DamageInfluence.MonsterControlDamageEffect(player, monster);
            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
            Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);

            DirectDamageToMob(player, monster, totalDamage);
            Compute.manaDamageExEffect(player, monster, totalDamage);
            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
            if (isPower) {
                Compute.AdditionEffects(player, monster, totalDamage, 1);
                WitherBook.witherBookEffect(player, monster);
            }

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.PlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }

        }

        public static void ManaDamageToMonster_RateApDamage_ElementAddition(Player player, Mob monster, double num, boolean isPower, String elementType, double elementValue) {
            CompoundTag data = player.getPersistentData();

            double defence = MobAttributes.manaDefence(monster);
            double baseDamage = PlayerAttributes.manaDamage(player) * num;
            double defencePenetration = PlayerAttributes.manaPenetration(player);
            double defencePenetration0 = PlayerAttributes.manaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += DamageInfluence.PlayerManaDamageEnhance(player); // 魔法伤害提升
            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + baseDamage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }
            baseDamage *= Compute.manaDefenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
            ExDamage *= Compute.manaDefenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
            double totalDamage = (baseDamage + ExDamage) * (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, elementType, elementValue, false, totalDamage);
                Element.entityElementUnit.put(player.getId(), new Element.Unit(Element.life, 0));
            }

            double elementDamage = totalDamage * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

            totalDamage *= DamageInfluence.MonsterControlDamageEffect(player, monster);
            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;

            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);

            if (elementDamage != 0 && !elementType.isEmpty() && elementValue != 0)
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false, elementType, elementDamage);
            else
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);

            DirectDamageToMob(player, monster, totalDamage);
            Compute.manaDamageExEffect(player, monster, totalDamage);
            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
            if (isPower) {
                Compute.AdditionEffects(player, monster, totalDamage, 1);
                WitherBook.witherBookEffect(player, monster);
            }

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.PlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }

        }

        public static void ManaDamageToMonster_ApDamage(Player player, Mob monster, double damage) {
            CompoundTag data = player.getPersistentData();

            double Defence = MobAttributes.manaDefence(monster);
            double BreakDefence = PlayerAttributes.manaPenetration(player);
            double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += DamageInfluence.PlayerManaDamageEnhance(player); // 魔法伤害提升

            damage += ExDamage;

            damage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

            double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));
            totalDamage *= DamageInfluence.MonsterControlDamageEffect(player, monster);
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
            DirectDamageToMob(player, monster, totalDamage);
            Compute.manaDamageExEffect(player, monster, totalDamage);
            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
        }

        public static void ManaDamageToMonster_ApDamage(Player player, Mob monster, double damage, boolean isPower) {
            CompoundTag data = player.getPersistentData();

            double Defence = MobAttributes.manaDefence(monster);
            double BreakDefence = PlayerAttributes.manaPenetration(player);
            double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += DamageInfluence.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += DamageInfluence.PlayerManaDamageEnhance(player); // 魔法伤害提升

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + damage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }

            damage += ExDamage;
            damage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

            double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.PlayerFinalDamageEnhance(player, monster));
            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.life, 0));
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, totalDamage);
                    Element.entityElementUnit.put(player.getId(), new Element.Unit(Element.life, 0));
                }
            }

            totalDamage *= DamageInfluence.MonsterControlDamageEffect(player, monster);
            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
            DirectDamageToMob(player, monster, totalDamage);
            Compute.manaDamageExEffect(player, monster, totalDamage);
            ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
            if (isPower) {
                WitherBook.witherBookEffect(player, monster);
                Compute.AdditionEffects(player, monster, totalDamage, 1);
            }

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.PlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }
        }

        public static void manaDamageToPlayer(Player player, Player hurter, double num) {

            double BaseDamage = PlayerAttributes.manaDamage(player);
            double BreakDefence = PlayerAttributes.manaPenetration(player);
            double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
            double Defence = PlayerAttributes.manaDefence(hurter);
            double DamageEnhance = 0;

            DamageEnhance -= (1 - manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

            DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
        }

        public static void manaDamageToPlayer(Mob monster, Player player, double damage) {
            double manaDefence = PlayerAttributes.manaDefence(player);
            damage *= manaDefenceDamageDecreaseRate(manaDefence, MobAttributes.defencePenetration(monster), MobAttributes.defencePenetration0(monster));
            MonsterAttackEvent.monsterAttack(monster, player, damage);
            BloodManaCurios.passive(player);
        }

        public static void manaDamageToPlayer(Mob monster, Player player, double damage, double penetration, double penetration0) {
            double manaDefence = PlayerAttributes.manaDefence(player);
            damage *= manaDefenceDamageDecreaseRate(manaDefence, penetration, penetration0);
            MonsterAttackEvent.monsterAttack(monster, player, damage);
            BloodManaCurios.passive(player);
        }

        public static void manaDamageToPlayer_RateApDamage(Mob mob, Player player, double rate) {
            double damage = MobAttributes.attackDamage(mob) * rate;
            double manaDefence = PlayerAttributes.manaDefence(player);
            double manaPenetration = PlayerAttributes.manaPenetration(player);
            double manaPenetration0 = PlayerAttributes.manaPenetration0(player);
            damage *= manaDefenceDamageDecreaseRate(manaDefence, manaPenetration, manaPenetration0);
            MonsterAttackEvent.monsterAttack(mob, player, damage);
            BloodManaCurios.passive(player);
        }

        public static void DamageIgnoreDefenceToPlayer(Mob mob, Player player, double Damage) {
            MonsterAttackEvent.monsterAttack(mob, player, Damage);
        }

        public static void DirectDamageToPlayer(Mob mob, Player player, double damage) {
            if (damage >= player.getHealth()) {
                Compute.playerDeadModule(player);
                Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                        Component.literal("").
                                append(player.getDisplayName()).
                                append(Compute.DescriptionWhiteColor("在与")).
                                append(mob.getDisplayName()).
                                append(Component.literal("的战斗中不幸重伤。").withStyle(ChatFormatting.WHITE)));
                NewTeamInstanceEvent.getOverworldInstances().forEach(newTeamInstance -> {
                    if (!newTeamInstance.players.isEmpty()) {
                        if (newTeamInstance.players.contains(player)) newTeamInstance.deadTimes++;
                    }
                });
            }
            player.setHealth((float) (player.getHealth() - damage));
        }

        public static void AttackDamageToPlayer(Mob monster, Player player, double Damage) {
            double Defence = PlayerAttributes.defence(player);
            Damage *= defenceDamageDecreaseRate(Defence, 0, 0);
            MonsterAttackEvent.monsterAttack(monster, player, Damage);
        }

        public static void AttackDamageToPlayer(Mob monster, Player player, double Damage, double defencePenetration, double defencePenetration0) {
            double Defence = PlayerAttributes.defence(player);
            Damage *= defenceDamageDecreaseRate(Defence, defencePenetration, defencePenetration0);
            MonsterAttackEvent.monsterAttack(monster, player, Damage);
        }

        public static void AttackDamageToPlayer_NumDamage(Mob monster, Player player, double Damage, double BreakDefence, double BreakDefence0) {
            double Defence = PlayerAttributes.defence(player);
            Damage *= defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            MonsterAttackEvent.monsterAttack(monster, player, Damage);
        }

        public static void DirectDamageToMob(Player player, Entity entity, double damage) {
            if (entity instanceof Mob mob && !(entity instanceof Allay) && !(entity instanceof Animal)) {
                if (entity instanceof Villager) return;
                if (mob.isDeadOrDying()) return;
                if (DailyEndlessInstance.prohibitPlayerCauseDamage(player, mob)) return;
                /*Castle.CauseDamageRecord(player, mob); */
                if (Moon.isMoonAttackImmune(player, (Mob) entity)) damage *= 0.5;
                if (Moon.isMoonManaImmune(player, (Mob) entity)) damage *= 0.5;
                CastleSecondFloor.PlayerPickItemExDamage(player, mob);
                damage *= CastleSecondFloor.MobDamageImmune(player, mob);
                /*AttackEvent.DamageCount(player, (Mob) entity, 0, damage);*/
                if (player.isCreative())
                    player.sendSystemMessage(Component.literal("" + mob.getHealth() / mob.getMaxHealth()));
                // ---- // 测试新模式
                entity.hurt(entity.damageSources().playerAttack(player), 0);
                MySound.soundOnMob(mob, SoundEvents.PLAYER_HURT);
                double finalDamage = mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.WoodenStake5.get()) ? 0 : (float) damage;
                if (mob.getHealth() <= finalDamage && mob.isAlive()) {
                    // 怪物死亡技艺
                    MobDeadModule.deadModule(mob);
                    LogUtils.getLogger().info("{} {} {}", player.getName().getString(), Utils.LogTypes.killed, mob.getName().getString());

                    mob.kill();
                    CompoundTag data = player.getPersistentData();

                    MobSpawn.drop(mob, player);

                    HurtEventModule.SwordSkill2(data, player); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                    HurtEventModule.BowSkill2(data, player); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                    HurtEventModule.ManaSkill2(data, player); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）

                    // 副手击杀效果
                    Item offhandItem = player.getOffhandItem().getItem();
                    if (offhandItem instanceof OnKillEffectOffHandItem item) item.onKill(player, mob);
                    NetherNewRune.onKill(player, mob);
                    HuskNewRune.onKill(player, mob);
                    DailyEndlessInstance.onKillMob(player, mob);
                } else mob.setHealth((float) (mob.getHealth() - finalDamage));

                // ---- //
                FireEquip.IgniteEffect(player, mob);
                DpsCommand.CalculateDamage(player, damage);
                MoonBelt.PassiveCauseDamage(player, damage); // 尘月玉缠
                entity.invulnerableTime = 0;
                StarBottle.playerBattleTickMapRefresh(player);
                Element.ElementParticleProvider(mob);
            }
        }

        public static void DirectDamageToPlayer(Player player, Player hurter, double Damage) {
            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + Damage));
                // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
            } else {
                hurter.hurt(hurter.damageSources().playerAttack(player), (float) Damage);
                hurter.invulnerableTime = 0;
                // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
            }
        }

        // 对于任意怪物对玩家造成的伤害，都需经过MonsterAttackEvent.MonsterAttack进行伤害以及其他增益的计算。
        // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
        // 对于玩家对怪物造成的伤害增益，需分为不同类型的伤害值进行计算。即需前往每个类型进行修改。其中，有RateAd/ApDamage可用。
    }

    public static void RandomToDesParticle(int num, Vec3 DesPos, Level level, double r) {
        if (Utils.particleOptionsList.isEmpty()) Utils.setParticleOptionsList();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            double angle = (2 * PI * random.nextDouble(1));
            Vec3 ParticleStartPos = new Vec3(DesPos.x - 4 + random.nextDouble(8) + r * Math.cos(angle),
                    DesPos.y - 2 + random.nextDouble(4),
                    DesPos.z - 4 + random.nextDouble(8) + r * Math.sin(angle));

            Vec3 Delta = DesPos.subtract(ParticleStartPos);
            double randomDouble = random.nextDouble(2);
            level.addParticle(Utils.particleOptionsList.get(random.nextInt(Utils.particleOptionsList.size())),
                    ParticleStartPos.x, ParticleStartPos.y, ParticleStartPos.z,
                    Delta.normalize().scale(randomDouble).x,
                    Delta.normalize().scale(randomDouble).y,
                    Delta.normalize().scale(randomDouble).z);
        }
    }

    public static boolean IsSoulEquip(ItemStack itemStack) {
        return itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())
                || itemStack.is(ModItems.SoulSceptre.get());
    }

    public static void resetSkillAndAbility(Player player) {
        CompoundTag data = player.getPersistentData();
        data.remove(StringUtils.SkillPoint_Total);
        data.remove(StringUtils.SkillPoint_Used);
        data.remove(StringUtils.SkillData.Sword);
        data.remove(StringUtils.SkillData.Bow);
        data.remove(StringUtils.SkillData.Mana);
        data.remove(StringUtils.Skill.SwordBase);
        data.remove(StringUtils.Skill.BowBase);
        data.remove(StringUtils.Skill.ManaBase);
        data.remove(StringUtils.AbilityPoint_Total);
        data.remove(StringUtils.AbilityPoint_Used);
        for (int i = 0; i < 5; i++) {
            data.remove(StringUtils.AbilityArray[i]);
        }
        for (int i = 0; i < 3; i++) {
            data.remove(StringUtils.SkillArray[i]);
        }
        Compute.sendFormatMSG(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                Component.literal("你的大脑对于技艺的理解回归了数个时段。。。").withStyle(ChatFormatting.WHITE));
    }

    public static int getPlayerPsValue(Player player) {
        return player.getPersistentData().getInt(StringUtils.PsValue);
    }

    public static boolean addOrCostPlayerPsValue(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int CurrentPsValue = data.getInt(StringUtils.PsValue);
        if (CurrentPsValue > 100 && value > 0) return false;
        if (CurrentPsValue + value < 0) return false;
        CurrentPsValue += value;
        if (value < 0) {
            data.putInt(StringUtils.PsValue, CurrentPsValue);
            ModNetworking.sendToClient(new PsValueS2CPacket(CurrentPsValue), (ServerPlayer) player);
        } else {
            data.putInt(StringUtils.PsValue, Math.min(CurrentPsValue, 100));
            ModNetworking.sendToClient(new PsValueS2CPacket(Math.min(CurrentPsValue, 100)), (ServerPlayer) player);
        }
        return true;
    }

    public static boolean addOrCostPlayerPsValueIgnoreLimit(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int CurrentPsValue = data.getInt(StringUtils.PsValue);
        if (CurrentPsValue + value < 0) return false;
        CurrentPsValue += value;
        data.putInt(StringUtils.PsValue, CurrentPsValue);
        ModNetworking.sendToClient(new PsValueS2CPacket(CurrentPsValue), (ServerPlayer) player);
        return true;
    }

    public static void respawnPlayer(Player player) {
        player.heal(player.getMaxHealth());
        ServerLevel overWorld = player.level().getServer().getLevel(Level.OVERWORLD);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        BlockPos spawnPos = serverPlayer.getRespawnPosition();
        if (spawnPos != null) {
            serverPlayer.teleportTo(overWorld,
                    spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), serverPlayer.getRespawnAngle(), 0);
        } else {
            serverPlayer.teleportTo(overWorld,
                    956, 232, 17, 0, 0);
        }
    }

    public static void ManaCoreDescription(List<Component> components) {
        components.add(Component.literal("δ-魔核属性:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void ClientCircleParticle(Level level, Vec3 bottomPos, Vec3 delta, double r, ParticleOptions particleOptions, int num) {
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y, bottomPos.z + r * sin(angle));
            level.addParticle(particleOptions, Point.x, Point.y, Point.z,
                    delta.x, delta.y, delta.z);
        }
    }

    public static void ClientCircleRollParticle(Level level, Vec3 bottomPos, Vec3 delta, double r, ParticleOptions particleOptions, int particleNum, double Height, int RollNum) {
        for (int j = 0; j < RollNum; j++) {
            for (int i = 0; i < particleNum; i++) {
                double angle = (2 * Math.PI / particleNum) * (i);
                Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y + Height * (j + i * 1.0 / particleNum), bottomPos.z + r * sin(angle));
                level.addParticle(particleOptions, Point.x, Point.y, Point.z,
                        delta.x, delta.y, delta.z);
            }
        }
    }

    public static void PurpleIronArmorAttributeGive(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(StringUtils.RandomAttribute.attackDamage, random.nextInt(50, 100));
        data.putInt(StringUtils.RandomAttribute.manaDamage, random.nextInt(300, 400));
        data.putInt(StringUtils.RandomAttribute.defence, random.nextInt(50, 100));
        data.putInt(StringUtils.RandomAttribute.maxHealth, random.nextInt(400, 800));
    }

    public static void IceArmorAttributeGive(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(StringUtils.RandomAttribute.attackDamage, random.nextInt(100, 140));
        data.putInt(StringUtils.RandomAttribute.manaDamage, random.nextInt(600, 800));
        data.putInt(StringUtils.RandomAttribute.defence, random.nextInt(100, 200));
        data.putInt(StringUtils.RandomAttribute.maxHealth, random.nextInt(800, 1600));
    }


    public static void purpleMineBlockPosInit(Level level, boolean byCommand) {
        if (Utils.playerDigCount > 200 || byCommand) {
            Compute.formatBroad(level, Component.literal("紫晶矿").withStyle(CustomStyle.styleOfPurpleIron),
                    Component.literal("樱岛紫晶矿已刷新！").withStyle(ChatFormatting.WHITE));
            if (!Utils.playerDigPos.isEmpty()) {
                Utils.playerDigPos.forEach(blockSP -> {
                    if (blockSP.getBlockState().is(Blocks.AMETHYST_BLOCK))
                        level.setBlockAndUpdate(blockSP.getBlockPos(), Blocks.STONE.defaultBlockState());
                    else
                        level.setBlockAndUpdate(blockSP.getBlockPos(), blockSP.getBlockState());
                });
            }
            Random random = new Random();
            BlockPos blockPos1 = new BlockPos(2025, 45, 1280);
            BlockPos blockPos2 = new BlockPos(1982, 24, 1243);
            BlockPos blockPos3 = new BlockPos(1955, 45, 1231);
            BlockPos blockPos4 = new BlockPos(1901, 24, 1194);
            for (int i = 0; i < 16000; i++) {
                BlockPos blockPos = new BlockPos(random.nextInt(blockPos2.getX(), blockPos1.getX()),
                        random.nextInt(blockPos2.getY(), blockPos1.getY()),
                        random.nextInt(blockPos2.getZ(), blockPos1.getZ()));
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.STONE)) {
                    level.setBlockAndUpdate(blockPos, Blocks.AMETHYST_BLOCK.defaultBlockState());
                }

                blockPos = new BlockPos(random.nextInt(blockPos4.getX(), blockPos3.getX()),
                        random.nextInt(blockPos4.getY(), blockPos3.getY()),
                        random.nextInt(blockPos4.getZ(), blockPos3.getZ()));
                blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.STONE)) {
                    level.setBlockAndUpdate(blockPos, Blocks.AMETHYST_BLOCK.defaultBlockState());
                }
            }
        }
    }

    /*    public static boolean PlayerIsOnline(Player player) {
        CompoundTag data = player.getPersistentData();
        return (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online));
    }*/
    public static void PlayerColdNumAddOrCost(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MaxCold) || !data.contains(StringUtils.CurrentCold)) {
            data.putDouble(StringUtils.MaxCold, 100);
            data.putDouble(StringUtils.CurrentCold, 0);
        } else {
            double MaxCold = data.getDouble(StringUtils.MaxCold);
            double CurrentCold = data.getDouble(StringUtils.CurrentCold);
            if (Num > 0) data.putDouble(StringUtils.CurrentCold, Math.min(100, CurrentCold + Num));
            else data.putDouble(StringUtils.CurrentCold, Math.max(0, CurrentCold + Num));
        }
        PlayerColdNumStatusUpdate(player);
    }

    public static void PlayerColdNumStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new ColdSyncS2CPacket(data.getDouble(StringUtils.MaxCold), data.getDouble(StringUtils.CurrentCold)), (ServerPlayer) player);
    }

    public static double PlayerCurrentColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.CurrentCold);
    }

    public static double PlayerMaxColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.MaxCold);
    }

    public static double PlayerColdEffect(Player player) {
        if (PlayerCurrentColdNum(player) > 90) return -0.5;
        return 0;
    }

    public static Calendar StringToCalendar(String DateString) throws ParseException {
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        if (!Objects.equals(DateString, "")) {
            Date date1 = tmpDate.parse(DateString);
            cal.setTime(date1);
        }
        return cal;
    }

    public static String CalendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        return tmpDate.format(date);
    }

    public static int playerReputation(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Reputation);
    }

    public static long calenderDateDifference(Calendar cal1, Calendar cal2) {
        return ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (24 * 60 * 60 * 1000));
    }

    public static boolean playerReputationAddOrCost(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        ChatFormatting chatFormatting = ChatFormatting.GREEN;
        if (Num < 0) {
            if (playerReputation(player) + Num < 0) {
                Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                        Component.literal("当前声望不足。").withStyle(ChatFormatting.WHITE));
                return false;
            }
            chatFormatting = ChatFormatting.RED;
        }
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + Num);
        data.putInt(StringUtils.ReputationCalculate, data.getInt(StringUtils.ReputationCalculate) + Num);
        Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                Component.literal("你的声望值:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + playerReputation(player)).withStyle(ChatFormatting.YELLOW)).
                        append(Component.literal(" (" + Num + ")").withStyle(chatFormatting)));
        ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)), (ServerPlayer) player);
        return true;
    }

    public static double playerFantasyAttributeEnhance(Player player) {
        double enhance = 1;
        CompoundTag data = player.getPersistentData();
        if (data.getBoolean(StringUtils.FantasyMedal)) enhance += 0.03;
        if (data.getBoolean(StringUtils.FantasyBracelet)) enhance += 0.03;
        return enhance;
    }

    public static boolean hasBonfireNearBy(Player player) {
        int x = player.getBlockX() - 5, y = player.getBlockY() - 5, z = player.getBlockZ() - 5;
        Level level = player.level();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    BlockState blockState = level.getBlockState(new BlockPos(x + i, y + j, z + k));
                    if (blockState.is(Blocks.CAMPFIRE) || blockState.is(Blocks.SOUL_CAMPFIRE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int playerMineLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Mine.Exp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerMineExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Mine.Exp, data.getInt(StringUtils.Mine.Exp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("采矿经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                append(Component.literal(" (" + data.getInt(StringUtils.Mine.Exp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static int playerGardeningLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Gardening.Xp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerGardeningExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Gardening.Xp, data.getInt(StringUtils.Gardening.Xp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("园艺经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                append(Component.literal(" (" + data.getInt(StringUtils.Gardening.Xp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static int playerLopLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Lop.Xp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerLopExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Lop.Xp, data.getInt(StringUtils.Lop.Xp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("伐木经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(CustomStyle.styleOfHusk)).
                append(Component.literal("" + Num).withStyle(CustomStyle.styleOfHusk)).
                append(Component.literal(" (" + data.getInt(StringUtils.Lop.Xp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static void SummonValueItemEntity(Level level, Player player, Mob mob, Component component, int type) {
        Vec3 delta = player.position().subtract(mob.position());
        Vec3 delta0 = new Vec3(delta.x, 0, delta.z);
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemEntity.setItem(ModItems.Value.get().getDefaultInstance());
        itemEntity.setCustomName(component);
        itemEntity.setCustomNameVisible(true);
        itemEntity.setNoGravity(true);
        Vec3 pos = mob.getEyePosition();
        Random r = new Random();
        if (type == 0)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(r.nextDouble()));
        if (type == 1)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(-1 * r.nextDouble()));
        itemEntity.moveTo(pos.add(r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25));
        itemEntity.setPickUpDelay(200);
        itemEntity.setDeltaMovement(new Vec3(delta0.normalize().scale(0.1).x, 0.1, delta0.normalize().scale(0.1).z));
        Utils.valueItemEntity.add(new ItemEntityAndResetTime(itemEntity, level.getServer().getTickCount() + 12));
        level.addFreshEntity(itemEntity);
    }

    public static double XpStrengthADDamage(Player player, double rate) {
        return PlayerAttributes.attackDamage(player) * (1 + player.experienceLevel * 5 / 100) * rate;
        // rate 为倍率
    }

    public static double XpStrengthAPDamage(Player player, double rate) {
        return PlayerAttributes.manaDamage(player) * (1 + player.experienceLevel * 5 / 100) * rate;
        // rate 为倍率
    }

    public static double XpStrengthDamage(Player player, double rate) {
        double attackDamage = PlayerAttributes.attackDamage(player), manaDamage = PlayerAttributes.manaDamage(player), base = (1 + player.experienceLevel * 5 / 100);
        return attackDamage * 4 > manaDamage ? attackDamage * 4 * base * rate : manaDamage * base * rate;
    }

    public static void TransformMapToSortedList() {
        if (Utils.dayKillCountList.size() > 0) Utils.dayKillCountList = new ArrayList<>();
        if (Utils.dayFishCountList.size() > 0) Utils.dayFishCountList = new ArrayList<>();
        if (Utils.dayMineCountList.size() > 0) Utils.dayMineCountList = new ArrayList<>();
        if (Utils.dayCropCountList.size() > 0) Utils.dayCropCountList = new ArrayList<>();
        if (Utils.dayLopCountList.size() > 0) Utils.dayLopCountList = new ArrayList<>();
        if (Utils.dayOnlineCountList.size() > 0) Utils.dayOnlineCountList = new ArrayList<>();

        Utils.dayKillCount.keySet().forEach(s -> {
            Utils.dayKillCountList.add(new PlayerNameAndCount(s, Utils.dayKillCount.get(s)));
        });
        Utils.dayFishCount.keySet().forEach(s -> {
            Utils.dayFishCountList.add(new PlayerNameAndCount(s, Utils.dayFishCount.get(s)));
        });
        Utils.dayMineCount.keySet().forEach(s -> {
            Utils.dayMineCountList.add(new PlayerNameAndCount(s, Utils.dayMineCount.get(s)));
        });
        Utils.dayCropCount.keySet().forEach(s -> {
            Utils.dayCropCountList.add(new PlayerNameAndCount(s, Utils.dayCropCount.get(s)));
        });
        Utils.dayLopCount.keySet().forEach(s -> {
            Utils.dayLopCountList.add(new PlayerNameAndCount(s, Utils.dayLopCount.get(s)));
        });
        Utils.dayOnlineCount.keySet().forEach(s -> {
            Utils.dayOnlineCountList.add(new PlayerNameAndCount(s, Utils.dayOnlineCount.get(s)));
        });

        Utils.dayKillCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayFishCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayMineCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayCropCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayLopCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayOnlineCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());

    }

    public static void SummonArmorStandForDisplay(Level level, List<ArmorStand> armorStands, List<PlayerNameAndCount> playerNameAndCounts, String type, Vec3 location) {
        armorStands.forEach(armorStand -> {
            if (armorStand.isAlive()) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        List<Component> components = ListToComponents(playerNameAndCounts, level, type);
        for (int i = 0; i < 6; i++) {
            ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
            armorStands.add(armorStand);
            armorStand.setNoGravity(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(components.get(i));
            armorStand.setInvulnerable(true);
            armorStand.setInvisible(true);
            armorStand.noPhysics = true;
            armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
            armorStand.moveTo(location.add(0, -0.25 * i, 0));
            level.addFreshEntity(armorStand);
        }
    }

    public static List<Component> ListToComponents(List<PlayerNameAndCount> playerNameAndCounts, Level level, String type) {
        List<Component> components = new ArrayList<>();
        switch (type) {
            case "Kill" -> components.add(Component.literal("击杀数排名").withStyle(CustomStyle.styleOfNether));
            case "Fish" -> components.add(Component.literal("钓鱼数排名").withStyle(CustomStyle.styleOfSea));
            case "Mine" -> components.add(Component.literal("挖矿数排名").withStyle(CustomStyle.styleOfSakuraMine));
            case "Crop" -> components.add(Component.literal("采集数排名").withStyle(CustomStyle.styleOfHealth));
            case "Lop" -> components.add(Component.literal("伐木数排名").withStyle(CustomStyle.styleOfHusk));
            case "Online" -> components.add(Component.literal("在线时长排名").withStyle(CustomStyle.styleOfSakura));
        }
        for (int i = 0; i < 5; i++) {
            if (i >= playerNameAndCounts.size()) {
                components.add(Component.literal((i + 1) + ".暂无").withStyle(ChatFormatting.GRAY));
            } else {
                ChatFormatting chatFormatting;
                switch (i) {
                    case 0 -> chatFormatting = ChatFormatting.RED;
                    case 1 -> chatFormatting = ChatFormatting.LIGHT_PURPLE;
                    case 2 -> chatFormatting = ChatFormatting.GOLD;
                    default -> chatFormatting = ChatFormatting.AQUA;
                }
                PlayerNameAndCount playerNameAndCount = playerNameAndCounts.get(i);
                Component playerName = null;
                if (level.getServer().getPlayerList().getPlayerByName(playerNameAndCount.name()) == null) {
                    playerName = Utils.playerNameMap.get(playerNameAndCount.name());
                } else
                    playerName = level.getServer().getPlayerList().getPlayerByName(playerNameAndCount.name()).getDisplayName();

                components.add(Component.literal((i + 1) + ". ").withStyle(chatFormatting).
                        append(playerName).
                        append(Component.literal(": " + playerNameAndCount.count()).withStyle(ChatFormatting.WHITE)));

            }
        }
        return components;
    }

    public static void RemoveAllArmorStandForDisplay() {
        LogUtils.getLogger().info("Removing armorstands.");
        Utils.dayKillCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayFishCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayMineCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayCropCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayLopCountDisplayList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        Utils.dayOnlineCountDisplayList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        if (Utils.recordTimeArmorStand != null) Utils.recordTimeArmorStand.remove(Entity.RemovalReason.KILLED);
        if (Utils.recordTimeArmorStand1 != null) Utils.recordTimeArmorStand1.remove(Entity.RemovalReason.KILLED);
    }

    public static void SummonRecordTimeDisplay(Level level, Vec3 location, boolean isFirst) {
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        if (isFirst) {
            if (Utils.recordTimeArmorStand != null) Utils.recordTimeArmorStand.remove(Entity.RemovalReason.KILLED);
            Utils.recordTimeArmorStand = armorStand;
        } else {
            if (Utils.recordTimeArmorStand1 != null) Utils.recordTimeArmorStand1.remove(Entity.RemovalReason.KILLED);
            Utils.recordTimeArmorStand1 = armorStand;
        }
        armorStand.setNoGravity(true);
        armorStand.setCustomNameVisible(true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String time = simpleDateFormat.format(Utils.countBeginToRecordTime.getTime());
        armorStand.setCustomName(Component.literal("记录开始时间:" + time).withStyle(ChatFormatting.AQUA));
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.noPhysics = true;
        armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
        armorStand.moveTo(location);
        level.addFreshEntity(armorStand);
    }

    public static void AdditionEffects(Player player, Mob mob, double damage, int type) {
        if (!Element.ElementPieceOnWeapon(player)) Element.ResonanceEffectGive(player);
    }

    public static void SummonWoodenStake(Level level) {
        Item[] Armors = {
                ModItems.WoodenStake0.get(),
                ModItems.WoodenStake1.get(),
                ModItems.WoodenStake2.get(),
                ModItems.WoodenStake3.get(),
                ModItems.WoodenStake4.get(),
                ModItems.WoodenStake5.get()
        };
        Vec3[] vec3s = {
                new Vec3(309.5, 64, 961.5),
                new Vec3(311.5, 64, 961.5),
                new Vec3(313.5, 64, 961.5),
                new Vec3(315.5, 64, 961.5),
                new Vec3(317.5, 64, 961.5),
                new Vec3(370.5, 83, 989.5)
        };
        for (int i = 0; i < 6; i++) {
            if (Utils.WoodenStake[i] == null || !Utils.WoodenStake[i].isAlive()) {
                Utils.WoodenStake[i] = new Zombie(EntityType.ZOMBIE, level);
                Compute.SetMobCustomName(Utils.WoodenStake[i], Armors[i], Component.literal("木桩").withStyle(ChatFormatting.GREEN));
                Utils.WoodenStake[i].setItemSlot(EquipmentSlot.HEAD, Armors[i].getDefaultInstance());
                Utils.WoodenStake[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.pow(10, (i == 5) ? 12 : 9));
                Utils.WoodenStake[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
                Utils.WoodenStake[i].heal(Utils.WoodenStake[i].getMaxHealth());
                Utils.WoodenStake[i].moveTo(vec3s[i]);
                level.addFreshEntity(Utils.WoodenStake[i]);
            }
        }
    }


    public static void HealWoodenStake() {
        for (Zombie zombie : Utils.WoodenStake) {
            if (zombie != null) zombie.heal(zombie.getMaxHealth());
        }
    }

    public static boolean thisTeamIsChallenging(PlayerTeam playerTeam) {
        return Utils.ChallengingPlayerTeam.contains(playerTeam);
    }

    public static void ManaCoreAddition(ItemStack stack, List<Component> components) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)) {
            Compute.ManaCoreDescription(components);
            String ManaCore = data.getString(StringUtils.ManaCore.ManaCore);
            String SeaCore = StringUtils.ManaCore.SeaCore;
            String BlackForestCore = StringUtils.ManaCore.BlackForestCore;
            String KazeCore = StringUtils.ManaCore.KazeCore;
            String SakuraCore = StringUtils.ManaCore.SakuraCore;
            if (ManaCore.equals(SeaCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂救赎").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标已损失生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfSea).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            } else if (ManaCore.equals(BlackForestCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂收割").withStyle(CustomStyle.styleOfHusk));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标当前生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfHusk).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("额外魔法伤害").withStyle(CustomStyle.styleOfHusk)));
                components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));

            } else if (ManaCore.equals(KazeCore)) {
                Compute.DescriptionPassive(components, Component.literal("狂风晶核").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("-获得").withStyle(ChatFormatting.WHITE).
                        append(AttributeDescription.movementSpeedWithoutBattle("30%")));
                components.add(Component.literal("-基于你的").withStyle(ChatFormatting.WHITE).
                        append(AttributeDescription.movementSpeedWithoutBattle("")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(AttributeDescription.ManaPenetration("")));
                components.add(Component.literal("-每").withStyle(ChatFormatting.WHITE).
                        append(AttributeDescription.ExMovementSpeed("1%")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(AttributeDescription.ManaPenetration("1")));
            } else if (ManaCore.equals(SakuraCore)) {
                Compute.DescriptionPassive(components, Component.literal("樱妖晶核").withStyle(CustomStyle.styleOfDemon));
                components.add(Component.literal("第一枚法球造成").withStyle(ChatFormatting.WHITE).
                        append(AttributeDescription.ManaDamage("100%")).
                        append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("第二枚法球回复").withStyle(ChatFormatting.WHITE).
                        append(AttributeDescription.ManaDamage("1.25%")).
                        append(AttributeDescription.Health("")));
            }
        } else {
            components.add(Component.literal(" 「尚未加载魔核」").withStyle(CustomStyle.styleOfMana));
        }
    }

    public static void FireWorkSummon(Player player, Mob monster) {
        CompoundTag compoundTag = new CompoundTag();
        byte a = 1;
        byte[] bytes = {0, 1, 2, 3, 4};
        String[] strings = {
                "SMALL_BALL",
                "LARGE_BALL",
                "CREEPER",
                "STAR",
                "BURST"
        };
        Random random = new Random();
        compoundTag.putByte("Type", a);
        compoundTag.putByte("Trail", a);
        compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
        compoundTag.putByte("Flicker", a);
        compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
        compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
        ListTag listTag = new ListTag();
        listTag.add(compoundTag);
        itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(
                player.level(), itemStack, monster);
        fireworkRocketEntity.setSilent(true);
        fireworkRocketEntity.setDeltaMovement(0, 0, 0);
        fireworkRocketEntity.moveTo(monster.position().add(0, 1, 0));
        player.level().addFreshEntity(fireworkRocketEntity);
    }

    public static void removeEffectLastTime(Player player, Item item) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeS2CPacket(item.getDefaultInstance()), (ServerPlayer) player);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(itemStack, tickCount), (ServerPlayer) player);
    }

    public static void sendEffectLastTimeToClientPlayer(Item item, int level, int tick, boolean noTime) {
        if (Element.elementList.isEmpty()) Element.setElementList();
        if (Element.elementList.contains(item)) {
            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> Element.elementList.contains(effectTimeLast.itemStack.getItem()));
        }
        ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.itemStack.is(item));
        if (noTime)
            ClientUtils.effectTimeLasts.add(new EffectTimeLast(item.getDefaultInstance(), tick, tick, level, true));
        else ClientUtils.effectTimeLasts.add(new EffectTimeLast(item.getDefaultInstance(), tick, tick, level));
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount) {
        sendEffectLastTime(player, item, tickCount, 0, false);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount, int level) {
        sendEffectLastTime(player, itemStack.getItem(), tickCount, level, false);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount, int level) {
        sendEffectLastTime(player, item, tickCount, level, false);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount, int level, boolean forever) {
        sendEffectLastTime(player, itemStack.getItem(), tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int level, boolean forever) {
        sendEffectLastTime(player, item, 25565, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount, int level, boolean forever) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(item.getDefaultInstance(), tickCount, level, forever), (ServerPlayer) player);
    }

    public static void coolDownTimeSend(Player player, Item item, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket(item.getDefaultInstance(), tickCount), (ServerPlayer) player);
    }

    public static void coolDownTimeSend(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket(itemStack, tickCount), (ServerPlayer) player);
    }

    public static void debuffTimeSend(Player player, ItemStack itemStack, int tickCount, int level) {
        ModNetworking.sendToClient(new DebuffTimeS2CPacket(itemStack, tickCount, level), (ServerPlayer) player);
    }

    public static void debuffTimeSend(Player player, Item item, int tickCount, int level) {
        ModNetworking.sendToClient(new DebuffTimeS2CPacket(item.getDefaultInstance(), tickCount, level), (ServerPlayer) player);
    }

    public static int LevelTypeJudge(Player player) {
        Level level = player.level();
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        Level nether = player.getServer().getLevel(Level.NETHER);
        Level end = player.getServer().getLevel(Level.END);
        if (level.equals(overWorld)) return 0;
        if (level.equals(nether)) return 1;
        return 2;
    }

    public static void iceParticleCreate(Entity entity) {
        BlockPos blockPos = entity.blockPosition().above();
        if (entity.level().getBlockState(blockPos).is(Blocks.AIR)) {
            entity.level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
            entity.level().destroyBlock(blockPos, false);
        }
    }

    public static void leafParticleCreate(Entity entity, Level level) {
        BlockPos blockPos = entity.blockPosition().above();
        if (level.getBlockState(blockPos).is(Blocks.AIR)) {
            level.setBlockAndUpdate(blockPos, Blocks.OAK_LEAVES.defaultBlockState());
            level.destroyBlock(blockPos, false);
        }
    }

    public static void FaceIceParticleCreate(Entity entity, Level level) {
        Vec3 vec3 = entity.pick(0.5, 0, false).getLocation();
        BlockPos blockPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
        if (level.getBlockState(blockPos).is(Blocks.AIR)) {
            level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
            level.destroyBlock(blockPos, false);
        }
    }

    public static boolean MonsterCantBeMove(Mob mob) {
        for (int i = 0; i < Utils.instanceKillCount.length; i++) {
            if (mob instanceof Civil) return true;
            if (Utils.instanceList.get(i).getMobList() != null && Utils.instanceList.get(i).getMobList().contains(mob))
                return true;
        }
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorGiant.get())) return true;
        return false;
    }

    public static void RepelMob(Player player, Vec3 StartPos, double range, double rate, double scaleLimit) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(StartPos, 20, 20, 20));
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(StartPos);
            if (PosVec.length() <= range) {
                if (!MonsterCantBeMove(mob))
                    mob.setDeltaMovement(PosVec.normalize().scale(Math.min(scaleLimit, rate / PosVec.length())));
            }
        });
    }

    public static void RepelPlayer(Mob mob, Vec3 StartPos, double range, double rate, double scaleLimit) {
        List<Player> mobList = mob.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(StartPos, 20, 20, 20));
        mobList.forEach(player -> {
            Vec3 PosVec = player.position().subtract(StartPos);
            if (PosVec.length() <= range) {
                Vec3 deltaMovement = PosVec.normalize().scale(Math.min(scaleLimit, rate / PosVec.length()));
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket
                        = new ClientboundSetEntityMotionPacket(player.getId(), deltaMovement);
                ((ServerPlayer) player).connection.send(clientboundSetEntityMotionPacket);
            }
        });
    }

    public static List<Mob> getPlayerRayMobList(Player player, double detectStep, double detectRange, double maxDistance) {
        Level level = player.level();
        Vec3 targetPos = player.pick(25, 0, false).getLocation();
        Vec3 startPos = player.pick(0.5, 0, false).getLocation();
        Vec3 posVec = targetPos.subtract(startPos).normalize();
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < maxDistance; i += detectStep) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(startPos.add(posVec.scale(i)), detectRange, detectRange, detectRange));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }
        return mobList;
    }

    public static void Laser(Player player, ParticleOptions particleOptions, double BaseDamage, int TickCoolDown, boolean isPower) {
        Level level = player.level();
        int TickCount = player.getServer().getTickCount();
        Vec3 TargetPos = player.pick(25, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }

        if (!Utils.playerLaserCoolDown.containsKey(player)) Utils.playerLaserCoolDown.put(player, new HashMap<>());
        Map<Mob, Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(player);

        mobList.forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob, TickCount + TickCoolDown);
                if (isPower) {
                    Damage.ManaDamageToMonster_ApDamage(player, mob, BaseDamage, true);
                } else {
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                            PlayerAttributes.manaDamage(player),
                            PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Lava);
                    ManaAttackModule.BasicAttack(player, mob, BaseDamage,
                            PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaPenetration0(player),
                            level, newArrow);
                }
            }
        });
    }

    public static void TargetLocationLaser(Player player, Vec3 location, ParticleOptions particleOptions, double BaseDamage, int TickCoolDown) {
        Level level = player.level();
        int TickCount = player.getServer().getTickCount();
        Vec3 TargetPos = location;
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }

        if (!Utils.playerLaserCoolDown.containsKey(player)) Utils.playerLaserCoolDown.put(player, new HashMap<>());
        Map<Mob, Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(player);

        mobList.forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob, TickCount + TickCoolDown);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                        PlayerAttributes.manaDamage(player),
                        PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Lava);
                ManaAttackModule.BasicAttack(player, mob, BaseDamage,
                        PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player),
                        level, newArrow);
            }
        });
    }

    public static double PlayerDodgeRate(Player player) {
        double swift = Math.min(PlayerAttributes.extraSwiftness(player), 90);
        double rate = 0;
        if (swift <= 10) rate = swift * 0.02;
        else if (swift <= 20) rate = 0.2 + (swift - 10) * 0.015;
        else if (swift <= 30) rate = 0.35 + (swift - 20) * 0.01;
        else rate = 0.45 + (swift - 30) * 0.005;
        return rate * 0.5;
    }

    public static class CuriosAttribute {

        public static void resetCuriosList(Player player) {
            if (Utils.playerCuriosListMap.containsKey(player)) {
                Utils.playerCuriosListMap.get(player).clear();
            }
        }

        public static double attributeValue(Player player, Map<Item, Double> attributeMap, String attributeName) {
            if (Utils.playerCuriosListMap.containsKey(player)) {
                AtomicReference<Double> value = new AtomicReference<>((double) 0);
                List<ItemStack> list = Utils.playerCuriosListMap.get(player);
                List<Item> itemList = new ArrayList<>();
                list.forEach(itemStack -> {
                    if (!itemList.contains(itemStack.getItem())
                            && (!Utils.levelRequire.containsKey(itemStack.getItem())
                            || player.experienceLevel >= Utils.levelRequire.get(itemStack.getItem()))) {
                        if (attributeMap.containsKey(itemStack.getItem()))
                            value.set(value.get() + attributeMap.get(itemStack.getItem()));
                        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(attributeName)) {
                            if (itemStack.getItem() instanceof RandomCurios)
                                value.set(value.get() + data.getDouble(attributeName)
                                        * CastleCurios.AttributeValueMap.get(attributeName));
                            else value.set(value.get() + data.getInt(attributeName));
                        }
                        itemList.add(itemStack.getItem());
                    }
                });
                return value.get();
            }
            return 0;
        }
    }

    public static boolean isOnCurios(Player player, Item curios) {
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            for (ItemStack itemStack : curiosList) {
                if (itemStack.is(curios)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Mob detectPlayerPickMob(Player player) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                return mob;
            }
        }
        return null;
    }

    public static Entity detectPlayerPickEntity(Player player, double distance, double range, Class<? extends Entity> clazz) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(distance, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0; i < Distance; i += 0.5) {
            List<? extends Entity> entities = level.getEntitiesOfClass(clazz, AABB.ofSize(StartPos.add(PosVec.scale(i)),
                    i > range ? range : 0.5, i > range ? range : 0.5, i > range ? range : 0.5));
            for (Entity entity : entities) {
                if (!entity.equals(player)) return entity;
            }
        }
        return null;
    }


    public static void ParticleEffectAddOnEntity(Entity entity, ParticleOptions particleOptions) {
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1.25, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.75, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.5, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.25, 0.4, 8, particleOptions, 0);
    }

    public static void AddCuriosToList(Player player, ItemStack stack) {
        if (!Utils.playerCuriosListMap.containsKey(player)) Utils.playerCuriosListMap.put(player, new ArrayList<>());
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        boolean hasSamaItem = false;
        for (ItemStack itemStack : curiosList) {
            if (itemStack.is(stack.getItem())) hasSamaItem = true;
        }
        if (!hasSamaItem) curiosList.add(stack);
    }

    public static void RemoveCuriosInList(Player player, ItemStack stack) {
        if (!Utils.playerCuriosListMap.containsKey(player)) Utils.playerCuriosListMap.put(player, new ArrayList<>());
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        curiosList.removeIf(s -> s.is(Items.AIR) || s.is(stack.getItem()));
    }

    public static boolean PlayerHasCurios(Player player, Item item) {
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            for (ItemStack itemStack : curiosList) {
                if (itemStack.is(item)) return true;
            }
        }
        return false;
    }

    public static void EndTp(List<Player> playerList, Vec3 pos) {
        playerList.forEach(player -> {
            ((ServerPlayer) player).teleportTo((ServerLevel) player.level(), pos.x, pos.y, pos.z, 0, 0);
        });
    }

    public static void damageActionBarPacketSend(Player player, double baseDamage, double ignoreDefenceDamage,
                                                 boolean isMana, boolean isCrit) {

        String string = "";
        String Crit = " ";
        if (isCrit) Crit = Utils.Emoji.CritRate;
        if (ignoreDefenceDamage > 0) {
            string = "+ [" + String.format("%.0f", ignoreDefenceDamage) + "]";
        }

        Style critStyle = isMana ? CustomStyle.styleOfEntropy : CustomStyle.styleOfPower;
        ChatFormatting damageStyle = isMana ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.YELLOW;

        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal(Crit).withStyle(critStyle).
                        append(Component.literal(String.format("%.0f", baseDamage) + " ").withStyle(damageStyle)).
                        append(Component.literal(string).withStyle(CustomStyle.styleOfSea)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);

    }

    public static void damageActionBarPacketSend(Player player, double baseDamage, double ignoreDefenceDamage,
                                                 boolean isMana, boolean isCrit, String elementType, double elementDamageValue) {
        String string = "";
        String crit = " ";
        if (isCrit) crit = Utils.Emoji.CritRate;
        if (ignoreDefenceDamage > 0) {
            string = "+ [" + String.format("%.0f", ignoreDefenceDamage) + "] ";
        }
        String elementDamageValueString = " ";
        if (elementDamageValue != 0) {
            elementDamageValueString = "「" + String.format("%.0f", elementDamageValue) + "」";
        }

        Style critStyle = isMana ? CustomStyle.styleOfEntropy : CustomStyle.styleOfPower;
        ChatFormatting damageStyle = isMana ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.YELLOW;

        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal(crit).withStyle(critStyle).
                        append(Component.literal(String.format("%.0f", baseDamage) + " ").withStyle(damageStyle)).
                        append(Component.literal(string).withStyle(CustomStyle.styleOfSea)).
                        append(Component.literal(elementDamageValueString).withStyle(Element.styleMap.get(elementType))));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);

    }

    public static boolean PlayerCanChallengeThisInstance(Player player, int instanceNum) {
        CompoundTag data = player.getPersistentData();
        if (instanceNum > 9) return true;
        if (data.getInt(StringUtils.PlayerInstanceProgress) >= instanceNum) return true;
        return false;
    }

    public record GatherMob(Mob mob, int tick, Vec3 pos) {
    }

    public static List<GatherMob> gatherMobList = new ArrayList<>();

    public static void Gather(int TickCount) {
        gatherMobList.removeIf(gatherMob -> gatherMob.tick < TickCount);
        gatherMobList.forEach(gatherMob -> {
            if (!MonsterCantBeMove(gatherMob.mob))
                gatherMob.mob.setDeltaMovement(gatherMob.pos.subtract(gatherMob.mob.position()).scale(0.2));
        });
    }

    public static void MonsterGatherProvider(Mob mob, int lastTick, Vec3 gatherPos) {
        int TickCount = mob.getServer().getTickCount();
        gatherMobList.add(new GatherMob(mob, TickCount + lastTick, gatherPos));
    }

    public static void EntitySmoothlyMoveToPos(Entity entity, Vec3 pos) {
        if (entity.position().subtract(pos).length() > 0.1)
            entity.setDeltaMovement(pos.subtract(entity.position()).scale(0.2));
        else {
            entity.setDeltaMovement(0, 0, 0);
        }
    }

    public static void EntitySmoothlyMoveToPosWithLimitSpeed(Entity entity, Vec3 pos, double speed) {
        if (entity.position().subtract(pos).length() > 0.1)
            entity.setDeltaMovement(pos.subtract(entity.position()).normalize().scale(speed));
        else {
            entity.setDeltaMovement(0, 0, 0);
        }
    }

    public static Vec3 GetPlayerBackPos(Player player, int type) {
        Vec3 vec3 = player.pick(-1, 0, false).getLocation();
        switch (type) {
            case 0 -> {
                return vec3.add(0, 2, 0);
            }
            case 1 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(Math.sqrt(4)));
            }
            case 2 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(-Math.sqrt(4)));
            }
            case 3 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(4)).add(0, -1, 0);
            }
            case 4 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()).scale(-4)).add(0, -1, 0);
            }
        }
        vec3 = player.pick(-1, 0, false).getLocation().
                add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get()));
        return vec3;
    }

    public static class PassiveEquip {

        public static double getAttribute(Player player, Map<Item, Double> map) {
            HashSet<Class<? extends Item>> set = new HashSet<>();
            double total = 0;
            for (int i = 3; i < 9; i++) {
                Item item = player.getInventory().getItem(i).getItem();
                if (!set.contains(item.getClass()) && Utils.passiveEquipTag.containsKey(item)
                        && map.containsKey(item)
                        && (!Utils.levelRequire.containsKey(item) || Utils.levelRequire.get(item) <= player.experienceLevel)) {
                    total += map.get(item);
                    set.add(item.getClass());
                }
            }
            return total;
        }
    }

    public static void LevelRequire(List<Component> components, int Level) {
        components.add(Component.literal(" 等级需求:").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("" + Level).withStyle(Utils.levelStyleList.get(Level / 25))));
    }

    public static void PlayerHealthDecrease(Player player, double value, Component component) {
        if (player.getHealth() <= value) {
            formatBroad(player.level(), Component.literal("").withStyle(ChatFormatting.RED),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(component));
            player.setHealth(0);
            Compute.playerDeadModule(player);
        } else {
            player.setHealth((float) (player.getHealth() - value));
        }
    }

    public static void PlayerHealthDecrease(Player player, double value, double threshold) {
        if ((player.getHealth() - value) / player.getMaxHealth() < threshold) {
            player.setHealth((float) (player.getMaxHealth() * threshold));
        } else player.setHealth((float) (player.getHealth() - value));
    }

    public static boolean PlayerUseWithHud(Player player, Map<Player, Integer> coolDownMap, Item item, Map<Player, Integer> lastTickMap, int lastTick, int manaCost, int coolDownSeconds) {
        int tickCount = player.getServer().getTickCount();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            Compute.coolDownTimeSend(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            lastTickMap.put(player, tickCount + lastTick);
            Compute.sendEffectLastTime(player, item.getDefaultInstance(), lastTick);
            return true;
        }
        return false;
    }

    public static boolean PlayerUseWithHud(Player player, Map<Player, Integer> coolDownMap, Item item, int manaCost, int coolDownSeconds) {
        int tickCount = player.getServer().getTickCount();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            Compute.coolDownTimeSend(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            return true;
        }
        return false;
    }

    public static List<Item> mobArmorList = new ArrayList<>();

    public static void setMobArmorList() {
        mobArmorList.add(ModItems.MobArmorCastleKnightHelmet.get());
        mobArmorList.add(ModItems.MobArmorPurpleIronKnightHelmet.get());
        mobArmorList.add(ModItems.MobArmorGiant.get());
    }

    public static double InstanceMobDamageLimit(Player player, Mob mob, double damage) {
        Item mobArmor = mob.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (mobArmorList.isEmpty()) setMobArmorList();
        if (!mobArmorList.contains(mobArmor)) return damage;
        double damageBoundary = 0.5;
        return Math.min(mob.getMaxHealth() * 0.5, damage);
    }

    public static Vec3 MyPlayerPickLocation(Player player, double distance) {
        Vec3 TargetPos = player.pick(distance, 0, false).getLocation();
        if (detectPlayerPickEntity(player, distance, 0.5, Mob.class) != null)
            TargetPos = detectPlayerPickEntity(player, distance, 0.5, Mob.class).position();
        return TargetPos;
    }

    public static void TraceArrowShoot(Player player, ParticleOptions particleOptions) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 80, 80, 80));
        mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);

        Mob NearestMob = null;
        double Distance = 80;
        for (Mob mob : mobList) {
            if (mob.distanceTo(player) < Distance) {
                NearestMob = mob;
                Distance = mob.distanceTo(player);
            }
        }

        MyArrow myArrow = new MyArrow(EntityType.ARROW, player.level(), player, true);
        myArrow.setDeltaMovement(NearestMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
        myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
        myArrow.setCritArrow(true);
        myArrow.setNoGravity(true);
        ProjectileUtil.rotateTowardsMovement(myArrow, 1);
        player.level().addFreshEntity(myArrow);
        ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player),
                player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), particleOptions);
    }

    public static void IgniteMob(Player player, Mob mob, int lastTick) {
        FireEquip.PlayerIgniteMobEffect(player, mob);
        mob.setRemainingFireTicks(lastTick);
    }

    public static List<Mob> OneShotLaser(Player player, boolean isAd, double damage, ParticleOptions particleOptions) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(25, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }
        mobList.forEach(mob -> {
            if (isAd) {
                Damage.AttackDamageToMonster_AdDamage(player, mob, damage);
            } else {
                Damage.ManaDamageToMonster_ApDamage(player, mob, damage);
            }
        });
        return mobList;
    }

    public static double playerExHarvest(Player player) {
        double rate = 0;
        rate += LabourDayIronHoe.playerExHarvest(player);
        rate += LabourDayIronPickaxe.playerExHarvest(player);
        rate += NewPotionEffects.exHarvestEnhance(player);
        int tier = 0;
        try {
            tier = PlanPlayer.getPlayerTier(player);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        rate += new double[]{0, 0.15, 0.3, 0.5}[tier];
        rate += SummerEvent.exHarvest(player);
        if (CompensateCommand.singleReward.equals("singleReward13")) rate += 0.5;
        return rate;
    }

    public static boolean exHarvestItemGive(Player player, ItemStack itemStack, double baseRate) {
        Random random = new Random();
        if (random.nextDouble() < baseRate * Compute.playerExHarvest(player)) {
            Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
            Compute.itemStackGive(player, itemStack);
            return true;
        }
        return false;
    }

    public static void playerDeadModule(Player player) {
        Tower.playerInChallengingDeadOrLogout(player);
        Utils.PlayerDeadTimeMap.put(player.getName().getString(), player.getServer().getTickCount() + 6000);
    }

    public static void manaDamageExEffect(Player player, Mob mob, double damage) {
        CastleSceptre.ExDamage(player, mob, damage);
    }

    public static void itemTrade(Player player, ItemStack needItem, ItemStack targetItem) throws IOException {
        int playerInventoryNeedItemCount = Compute.itemStackCount(player, needItem.getItem());
        if (playerInventoryNeedItemCount >= needItem.getCount()) {
            Compute.itemStackRemoveIgnoreVB(player.getInventory(), needItem.getItem(), needItem.getCount());
            Compute.itemStackGive(player, new ItemStack(targetItem.getItem(), targetItem.getCount()));
        } else {
            Compute.sendFormatMSG(player, Component.literal("交易").withStyle(ChatFormatting.GOLD),
                    Component.literal("背包中似乎没有足够数量的 ").withStyle(ChatFormatting.WHITE).
                            append(needItem.getDisplayName()));
        }
    }

    public static boolean playerIsInBattle(Player player) {
        return StarBottle.playerLastBattleTick.containsKey(player) && StarBottle.playerLastBattleTick.get(player) + 100 > player.getServer().getTickCount();
    }

    public static boolean playerIsInBattle(Player player, int tick) {
        return StarBottle.playerLastBattleTick.containsKey(player) && StarBottle.playerLastBattleTick.get(player) + tick > player.getServer().getTickCount();
    }

/*    public static String getItemStackString(ItemStack itemStack) {
        String string = itemStack.serializeNBT().getAsString();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (ch == '"' || ch == '\'') {
                stringBuilder.append("\\").append(ch);
            } else stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }*/

    public static String getItemStackString(ItemStack itemStack) {
        return itemStack.serializeNBT().getAsString();
    }

    public static ItemStack getItemFromString(String string) throws CommandSyntaxException {
        CompoundTag compoundTag;
        compoundTag = TagParser.parseTag(string);
        return ItemStack.of(compoundTag);
    }

    public static void sendActionBarMSG(Player player, Component component) {
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(component);
        ((ServerPlayer) player).connection.send(clientboundSetActionBarTextPacket);
    }

    @Nullable
    public static ServerPlayer getPlayerByName(Level level, String name) {
        return level.getServer().getPlayerList().getPlayerByName(name);
    }

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle,
                                                 int fadeIn, int stay, int fadeOut) {
        serverPlayer.connection.send(new ClientboundSetTitleTextPacket(title));
        serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(subTitle));
        serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut));
    }

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle(serverPlayer, title, subTitle, 20, 60, 20);
    }

    public static List<? extends Entity> getNearEntity(Entity center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = center.level().getEntitiesOfClass(type, AABB.ofSize(center.position(), distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.distanceTo(center) <= distance).toList();
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, Item icon, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), icon.getDefaultInstance(), tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void decreaseCoolDownLeftTick(Player player, Map<Item, Integer> playerEachItemCoolDownMap, int decreaseTick) {
        playerEachItemCoolDownMap.forEach((power, coolDownTick) -> {
            double percent = player.getCooldowns().getCooldownPercent(power, 0);
            int leftTick = (int) (percent * coolDownTick);
            leftTick = Math.max(0, leftTick - decreaseTick);
            player.getCooldowns().addCooldown(power, leftTick);
            playerEachItemCoolDownMap.put(power, leftTick);
        });
    }
}
