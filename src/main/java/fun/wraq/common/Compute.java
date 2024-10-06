package fun.wraq.common;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import fun.wraq.commands.changeable.CompensateCommand;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.CrestItem;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.oncostmana.OnCostManaEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.EffectTimeLast;
import fun.wraq.common.util.struct.ItemEntityAndResetTime;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.core.MyArrow;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.EffectLastTimeS2CPacket;
import fun.wraq.networking.misc.EntropyPackets.EntropyS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.networking.misc.PsValueS2CPacket;
import fun.wraq.networking.misc.RemoveEffectLastTimeS2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.BowSkill12S2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.ManaSkill12S2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.ManaSkill13S2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.SwordSkill12S2CPacket;
import fun.wraq.networking.misc.ToolTipPackets.CoolDownTimeS2CPacket;
import fun.wraq.networking.misc.USE.MobEffectHudS2CPacket;
import fun.wraq.networking.reputation.ReputationValueS2CPacket;
import fun.wraq.networking.unSorted.DebuffTimeS2CPacket;
import fun.wraq.process.func.SpecialEffectOnPlayer;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Color;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.potion.NewPotionEffects;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.hud.networking.ExpGetS2CPacket;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.CastleCurios;
import fun.wraq.series.instance.series.castle.CastleSceptre;
import fun.wraq.series.overworld.chapter1.forest.bossItems.ForestBossSword;
import fun.wraq.series.overworld.chapter1.volcano.bossItems.VolcanoBossSword;
import fun.wraq.series.overworld.chapter1.waterSystem.bossItems.LakeBoss;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import fun.wraq.series.specialevents.labourDay.LabourDayIronHoe;
import fun.wraq.series.specialevents.labourDay.LabourDayIronPickaxe;
import fun.wraq.series.specialevents.summer.SummerEvent;
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
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.abs;
import static java.lang.Math.acos;


public class Compute {

    public static double getSwordSkill1And4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.swordTag.containsKey(mainhand)) {
            Decrease += Compute.getSwordSkillLevel(data, 1) * 0.01;
            Decrease -= Compute.getSwordSkillLevel(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double getSwordSkill14(CompoundTag data, Player player, LivingEntity monster) {
        double Enhance = 0;
        if (getSwordSkillLevel(data, 14) > 0) {
            double PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            double MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate < MonsterHealthRate) {
                Enhance += 0.2 * Math.min(1.0, (MonsterHealthRate - PlayerHealthRate) / 0.66);
            }
        }
        return Enhance;
    }

    public static double getBowSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.bowTag.containsKey(mainhand)) {
            Decrease -= Compute.getBowSkillLevel(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double getManaSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.sceptreTag.containsKey(mainhand)) {
            Decrease -= Compute.getManaSkillLevel(data, 4) * 0.015;
        }
        return Decrease;
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

        MutableComponent quality = Te.m("");

        if (Utils.armorTag.containsKey(stack.getItem()) || Utils.mainHandTag.containsKey(stack.getItem())) {
            int forgeQuality = ForgeEquipUtils.getForgeQualityOnEquip(stack);

            quality = Te.m("").append(ForgeEquipUtils.getDescription(forgeQuality)).
                    append(Te.m(" - ", ForgeEquipUtils.getStyle(forgeQuality)));
        }

        Component defaultName = stack.getItem().getDefaultInstance().getHoverName();
        stack.setHoverName(Component.literal("")
                .append(prefix)
                .append(quality)
                .append(suffix)
                .append(defaultName));
    }

    public static int levelUpperLimit = 250;
    public static int expGetUpperLimit = 125;

    public static double getCurrentXpLevelUpNeedXpPoint(int xpLevel) {
        return Math.pow(Math.E, 3 + (xpLevel / 100d) * 7);
    }

    public static void givePercentExpToPlayer(Player player, double num, double expUp, int expLevel) {
        if (player.experienceLevel >= levelUpperLimit) return;
        if (expLevel >= expGetUpperLimit) {
            num *= (double) expLevel / expGetUpperLimit;
            expLevel = expGetUpperLimit;
        }
        if (expLevel - player.experienceLevel > 8) expLevel = player.experienceLevel;

        CompoundTag data = player.getPersistentData();
        double levelUpNeedXp = getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
        double expLevelXp = getCurrentXpLevelUpNeedXpPoint(expLevel);
        double xpBeforeUp = (expLevelXp * num);
        double xpUp = (expLevelXp * num) * expUp;
        double xp = xpBeforeUp + xpUp;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + xp);
        else data.putDouble("Xp", xp);
/*        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.sendFormatMSG(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", XpBeforeUp)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));*/
        ModNetworking.sendToClient(new ExpGetS2CPacket(xp), (ServerPlayer) player);
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
                MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
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
                        Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                        player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                                ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));
                    }
                }
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Player player1 : playerList) {
                    if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Damage.causeAttackDamageToPlayer_RateAdDamage(player, player1, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                    }
                }
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1, 6, 100, ModParticles.LONG_ENTROPY.get());
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1.5, 6, 100, ModParticles.LONG_ENTROPY.get());

                MySound.soundToNearPlayer(player, ModSounds.Lava.get());
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

    public static void addManaDefenceDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addDefenceDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addDamageDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addSlowDownEffect(Mob mob, int Tick, int Tier) {
        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Tick, Tier, false, false, false));
/*        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });*/
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
        if (Mana.getPlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            if (SuitCount.getEarthManaSuitCount(player) > 0) {
                playerHeal(player, manaCost * SuitCount.getEarthManaSuitCount(player));
            }
            OnCostManaEquip.costMana(player, manaCost);
            Mana.addOrCostPlayerMana(player, -manaCost);
        }
        return true;
    }

    public static boolean playerManaCost(Player player, double manaCost, boolean IsMana) {
        if (Mana.getPlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            PowerLogic.playerLastTimeReleasePowerManaCost.put(player, manaCost);
            if (SuitCount.getEarthManaSuitCount(player) > 0) {
                playerHeal(player, manaCost * SuitCount.getEarthManaSuitCount(player));
            }
            OnCostManaEquip.costMana(player, manaCost);
            Mana.addOrCostPlayerMana(player, -manaCost);
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

    public static void RandomPotionBagProvider(Player player, int MaxNum, double Rate) {
        Random random = new Random();
        ItemStack[] itemStack = new ItemStack[13];
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

    public static class PlayerIgnore {
        public static boolean IgnoreItemGet(Player player) {
            return player.getPersistentData().contains(StringUtils.IgnoreType.ItemGet)
                    && player.getPersistentData().getBoolean(StringUtils.IgnoreType.ItemGet);
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
            Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, mob, 3, true,
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
    
    public static boolean RecallPlayerCheck(ServerPlayer serverPlayer) {
        if (Utils.kazeRecall.recallPlayer != null && Utils.kazeRecall.recallPlayer.equals(serverPlayer)) return true;
        return Utils.spiderRecall.recallPlayer != null && Utils.spiderRecall.recallPlayer.equals(serverPlayer);
    }

    public static double Vec3Angle(Vec3 VecA, Vec3 VecB) {
        return acos(abs(VecA.dot(VecB)) / (VecA.length() * VecB.length()));
    }

    public static int getSwordSkillLevel(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Sword);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int getBowSkillLevel(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Bow);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int getManaSkillLevel(CompoundTag data, int index) {
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
        if (Compute.getSwordSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new SwordSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.getManaSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new ManaSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.getManaSkillLevel(data, 13) > 0)
            ModNetworking.sendToClient(new ManaSkill13S2CPacket(8), (ServerPlayer) player);
        if (Compute.getBowSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new BowSkill12S2CPacket(8), (ServerPlayer) player);
    }

    public static ItemStack foilAddItemStack(ItemStack itemStack) {
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

    public static class AttributeDescription {

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

        public static Component ManaHealSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(CustomStyle.styleOfMana);
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
    }

    public static void SuitDescription(List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.AQUA));
    }

    public static void SuitDoubleDescription(List<Component> components, int Count) {
        if (Count >= 2)
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
        else
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuitQuadraDescription(List<Component> components, int Count) {
        if (Count >= 4)
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
        else
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static String getRGB(int r, int g, int b) {
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

    public static double forgingValue(CompoundTag data, double baseValue) {
        int forgingLevel = data.getInt("Forging");
        if (data.contains(StringUtils.QingMingForgePaper)) ++forgingLevel;
        if (data.contains(StringUtils.LabourDayForgePaper)) ++forgingLevel;

        if (forgingLevel <= 10) {
            return baseValue * 0.04 * forgingLevel;
        } else if (forgingLevel <= 20) {
            return baseValue * (0.08 * (forgingLevel - 10) + 0.4);
        } else if (forgingLevel <= 24) {
            return baseValue * (0.16 * (forgingLevel - 20) + 1.2);
        } else {
            return baseValue * (0.32 * (forgingLevel - 24) + 1.84);
        }
    }

    public static double SkySuitEffectRate(Player player) {
        int Count = SuitCount.getSkySuitCount(player);
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
        int Count = SuitCount.getNetherSuitCount(player);
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

    public static long calenderMinuteDifference(Calendar cal1, Calendar cal2) {
        return ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (60 * 60 * 1000));
    }

    public static String getDifferenceFormatText(Calendar cal1, Calendar cal2) {
        long delta = (cal1.getTimeInMillis() - cal2.getTimeInMillis());
        if (delta <= 0) return "00:00:00";
        long seconds = delta / 1000 % 60;
        long hours = delta / (1000 * 3600);
        long minute = delta / (1000 * 60) % 60;
        SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
        Calendar deltaTime = Calendar.getInstance();
        deltaTime.set(Calendar.HOUR_OF_DAY, (int) hours);
        deltaTime.set(Calendar.MINUTE, (int) minute);
        deltaTime.set(Calendar.SECOND, (int) seconds);
        return tmpDate.format(deltaTime.getTime());
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

    public static void summonValueItemEntity(Level level, Player player, Mob mob, Component component, int type) {
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

    public static void AdditionEffects(Player player, Mob mob, double damage, int type) {
        if (!Element.ElementPieceOnWeapon(player)) Element.ResonanceEffectGive(player);
    }

    public static boolean thisTeamIsChallenging(PlayerTeam playerTeam) {
        return Utils.ChallengingPlayerTeam.contains(playerTeam);
    }

    public static void summonFireWork(Player player, Mob monster) {
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
        if (Element.elementItemList.isEmpty()) Element.setElementList();
        if (Element.elementItemList.contains(item)) {
            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> Element.elementItemList.contains(effectTimeLast.itemStack.getItem()));
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

    public static void createIceParticle(Entity entity) {
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
        for (double i = detectStep; i <= maxDistance; i += detectStep) {
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
                    Damage.causeManaDamageToMonster_ApDamage(player, mob, BaseDamage, true);
                } else {
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                            PlayerAttributes.manaDamage(player),
                            PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Lava);
                    ManaAttackModule.BasicAttack(player, mob, BaseDamage,
                            PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaPenetration0(player),
                            level, newArrow, true);
                }
            }
        });
    }

    public static void TargetLocationLaser(Player player, Vec3 location, ParticleOptions particleOptions, double baseDamage, int tickCoolDown) {
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
                laserCoolDownMap.put(mob, TickCount + tickCoolDown);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                        PlayerAttributes.manaDamage(player),
                        PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Lava);
                ManaAttackModule.BasicAttack(player, mob, baseDamage,
                        PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player),
                        level, newArrow, true);
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

        /**
         * 获取玩家去重饰品列表
         */
        public static List<ItemStack> getDistinctCuriosList(Player player) {
            if (!Utils.playerCuriosListMap.containsKey(player)) return List.of();
            List<ItemStack> originCuriosList = Utils.playerCuriosListMap.get(player);
            Set<Item> curiosItemSet = new HashSet<>();
            List<ItemStack> curiosList = new ArrayList<>();
            originCuriosList.forEach(stack -> {
                if (!curiosItemSet.contains(stack.getItem())) {
                    curiosItemSet.add(stack.getItem());
                    curiosList.add(stack);
                }
            });
            return curiosList;
        }

        public static double attributeValue(Player player, Map<Item, Double> attributeMap, String attributeName) {
            if (Utils.playerCuriosListMap.containsKey(player)) {
                AtomicReference<Double> value = new AtomicReference<>((double) 0);
                List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
                Set<Item> itemSet = new HashSet<>();
                curiosList.forEach(curiosStack -> {
                    Item curiosItem = curiosStack.getItem();
                    if (!itemSet.contains(curiosItem)
                            && (!Utils.levelRequire.containsKey(curiosItem)
                            || player.experienceLevel >= Utils.levelRequire.get(curiosItem))) {
                        if (attributeMap.containsKey(curiosItem)) {
                            value.set(value.get() + attributeMap.get(curiosItem));
                        }
                        CompoundTag data = curiosStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(attributeName)) {
                            if (curiosItem instanceof RandomCurios) {
                                value.set(value.get() + data.getDouble(attributeName)
                                        * CastleCurios.attributeValueMap.get(attributeName));
                            }
                            else {
                                value.set(value.get() + data.getInt(attributeName));
                            }
                        }
                        if (!(curiosItem instanceof CrestItem)) {
                            itemSet.add(curiosStack.getItem());
                        }
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
            double value = 0;
            for (int i = 3; i < 9; i++) {
                ItemStack equip = player.getInventory().getItem(i);
                Item item = equip.getItem();
                if (!set.contains(item.getClass()) && Utils.passiveEquipTag.containsKey(item)
                        && map.containsKey(item)
                        && (!Utils.levelRequire.containsKey(item) || Utils.levelRequire.get(item) <= player.experienceLevel)) {
                    if (item instanceof WraqMainHandOrPassiveEquip wraqMainHandOrPassiveEquip) {
                        if (!(player.getMainHandItem().is(item) && player.getInventory().selected >= 3)) {
                            double computeValue = 0;
                            double baseValue = 0;
                            baseValue += ForgeEquipUtils.getTraditionalEquipBaseValue(equip, map);
                            computeValue += baseValue;
                            computeValue += Compute.forgingValue(equip, baseValue);
                            computeValue *= wraqMainHandOrPassiveEquip.rate();
                            value += computeValue;
                        }
                    } else {
                        value += map.get(item);
                    }
                    set.add(item.getClass());
                }
            }
            return value;
        }
    }

    public static void LevelRequire(List<Component> components, int Level) {
        components.add(Component.literal(" 等级需求:").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("" + Level).withStyle(Utils.levelStyleList.get(Level / 25))));
    }

    public static void PlayerHealthDecrease(Player player, double value, Component component) {
        if (player.getHealth() <= value) {
            formatBroad(player.level(), Component.literal("孽").withStyle(ChatFormatting.RED),
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

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, WeakHashMap<Player, Integer> lastTickMap, int lastTick, int manaCost, int coolDownSeconds) {
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

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, int manaCost, int coolDownSeconds) {
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
                Damage.causeAttackDamageToMonster_AdDamage(player, mob, damage);
            } else {
                Damage.causeManaDamageToMonster_ApDamage(player, mob, damage);
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
            InventoryOperation.itemStackGive(player, itemStack);
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

    public static List<? extends Entity> getNearEntity(Level level, Vec3 center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = level.getEntitiesOfClass(type, AABB.ofSize(center, distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.position().distanceTo(center) <= distance).toList();
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, Item icon, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), icon.getDefaultInstance(), tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void removeMobEffectHudToNearPlayer(Mob mob, Item icon, String tag) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), icon.getDefaultInstance(), tag, 0, 0, false), serverPlayer);
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

    public record LowGravityZone(ResourceKey<Level> dimension, Pair<Vec3, Vec3> space) {}
    public static final List<Pair<Vec3, Vec3>> lowGravityZone = new ArrayList<>() {{
        add(new Pair<>(new Vec3(876, 180, 491), new Vec3(1242, 280, 724)));
        add(new Pair<>(new Vec3(898, 186, -62), new Vec3(1042, 318, 126)));
    }};
    public static boolean inLowGravityEnvironment(Player player) {
        return lowGravityZone.stream().anyMatch(pair -> {
            return player.getX() > pair.getFirst().x && player.getY() > pair.getFirst().y && player.getZ() > pair.getFirst().z
                    && player.getX() < pair.getSecond().x && player.getY() < pair.getSecond().y && player.getZ() < pair.getSecond().z;
        });
    }

    public static void setDownDeltaInLowGravityEnvironment(Player player) {
        if (inLowGravityEnvironment(player) && player.isShiftKeyDown()) {
            player.setDeltaMovement(player.getDeltaMovement().add(0, -0.05, 0));
        }
    }

    /**
     * 将数值转换为长度较短的字符串以更好地展示 保证最多占用五位
     * @param value 数值
     * @return 简化后字符串形式数值
     */
    public static String getSimplifiedNumberDescription(double value) {
        if (value < 1000) {
            // 正常显示 999.9
            return String.format("%.1f", value);
        }
        if (value < 10000) {
            // 显示99.99k
            return String.format("%.2fk", value / 1000);
        }
        if (value < 10000000) {
            // 显示99.99w
            if (value > 1000000) {
                return String.format("%.1fw", value / 10000);
            }
            return String.format("%.2fw", value / 10000);
        }
        if (value < 100000000) {
            // 显示99.9kw
            return String.format("%.1fkw", value / 10000000);
        }
        // 显示99.99e
        return String.format("%.2fe", value / 100000000);
    }
}
