package fun.wraq.common;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.HudIcon;
import fun.wraq.common.util.struct.ItemEntityAndResetTime;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.customized.uniform.attack.AttackCurios5;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.hud.CoolDownTimeS2CPacket;
import fun.wraq.networking.hud.DebuffTimeS2CPacket;
import fun.wraq.networking.hud.EffectLastTimeS2CPacket;
import fun.wraq.networking.hud.RemoveDebuffTimeS2CPacket;
import fun.wraq.networking.misc.EntropyPackets.EntropyS2CPacket;
import fun.wraq.networking.misc.RemoveEffectLastTimeByItemIdS2CPacket;
import fun.wraq.networking.misc.RemoveEffectLastTimeS2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.BowSkill12S2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.ManaSkill12S2CPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.SwordSkill12S2CPacket;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.misc.USE.MobEffectHudS2CPacket;
import fun.wraq.networking.reputation.ReputationValueS2CPacket;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Color;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.endlessinstance.item.special.HoursExHarvestPotion;
import fun.wraq.process.system.estate.EstateUtil;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.process.system.tp.TpPass;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.hud.ColdData;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.hud.networking.ExpGetS2CPacket;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.ForgePaper;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.labourDay.LabourDayIronHoe;
import fun.wraq.series.events.labourDay.LabourDayIronPickaxe;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import fun.wraq.series.events.qingMing.QingMingCommonRing;
import fun.wraq.series.events.summer2025.Summer2025;
import fun.wraq.series.holy.ice.FrostInstance;
import fun.wraq.series.instance.blade.WraqBlade;
import fun.wraq.series.instance.series.castle.CastleSceptre;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.instance.series.warden.gem.AncientEchoGem;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import fun.wraq.series.overworld.cold.sc5.dragon.SuperColdCarrot;
import fun.wraq.series.overworld.sakura.bunker.armor.BunkerArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.CuriosApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        MutableComponent suffix = Component.literal("");
        MutableComponent prefix = Component.literal("");
        Component defaultName = stack.getItem().getDefaultInstance().getHoverName();
        stack.setHoverName(Component.literal("")
                .append(prefix)
                .append(suffix)
                .append(defaultName));
    }

    public static int levelUpperLimit = 300;
    public static int expGetUpperLimit = 125;

    public static double getCurrentXpLevelUpNeedXpPoint(int xpLevel) {
        return Math.pow(Math.E, 3 + (xpLevel / 100d) * 7);
    }

    public static void givePercentExpToPlayer(Player player, double num, double expUp, int expLevel,
                                              boolean sendMSG, Component sourceType) {
        if (player.experienceLevel >= levelUpperLimit) return;
        if (expLevel >= expGetUpperLimit) {
            num *= (double) expLevel / expGetUpperLimit;
            expLevel = expGetUpperLimit;
        }
        if (expLevel - player.experienceLevel > 8) expLevel = player.experienceLevel;
        CompoundTag data = player.getPersistentData();
        double expLevelXp = getCurrentXpLevelUpNeedXpPoint(expLevel);
        double xpBeforeUp = (expLevelXp * num);
        double xpUp = (expLevelXp * num) * expUp;
        double xp = xpBeforeUp + xpUp;
        if (data.contains("Xp")) {
            data.putDouble("Xp", data.getDouble("Xp") + xp);
        } else {
            data.putDouble("Xp", xp);
        }
        ModNetworking.sendToClient(new ExpGetS2CPacket(xp), (ServerPlayer) player);
        if (sendMSG) {
            Compute.getValueIncreaseMSG(String.format("%.0f", xp), ChatFormatting.LIGHT_PURPLE,
                    String.format("%.0f", data.getDouble("Xp")), ChatFormatting.GRAY, sourceType);
        }
    }

    public static void givePercentExpToPlayer(Player player, double num, double expUp, int expLevel) {
        givePercentExpToPlayer(player, num, expUp, expLevel, false, null);
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
        if (SpecialEffectOnPlayer.inSilent(player)) {
            return;
        }

        if (player.getCooldowns().isOnCooldown(tool)) {
            return;
        }
        if (Utils.levelRequire.getOrDefault(tool, 0) > player.experienceLevel) {
            return;
        }

        if (tool instanceof ActiveItem activeItem) {
            if (playerManaCost(player, activeItem.manaCost(player))) {
                activeItem.active(player);
                VdWeaponCommon.onReleaseActive(player, tool);
            }
        }
    }

    public static void addManaDefenceDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = livingEntity.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(livingEntity.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addDefenceDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = livingEntity.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(livingEntity.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addDamageDecreaseEffectParticle(Mob mob, int Tick) {
/*        List<ServerPlayer> playerList = livingEntity.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(livingEntity.getId(), Tick), serverPlayer);
        });*/
    }

    public static void addSlowDownEffect(Mob mob, int tick, int tier) {
        if (MobSpawn.canAddSlowDownOrImprison(mob)) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, tick, tier, false, false, false));
        }
/*        List<ServerPlayer> playerList = livingEntity.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(livingEntity.getId(), Tick), serverPlayer);
        });*/
    }

    public static void playerItemCoolDown(Player player, Item item, double Seconds) {
        double coolDownDecrease = PlayerAttributes.coolDownDecrease(player);
        int cooldownTick = (int) (Seconds * 20 * (1 - coolDownDecrease));
        player.getCooldowns().addCooldown(item, cooldownTick);
        if (Utils.powerTag.containsKey(item)) {
            if (!PowerLogic.playerPowerCoolDownRecord.containsKey(Name.get(player)))
                PowerLogic.playerPowerCoolDownRecord.put(Name.get(player), new HashMap<>());
            Map<Item, Integer> map = PowerLogic.playerPowerCoolDownRecord.get(Name.get(player));
            map.put(item, cooldownTick);
            PowerLogic.playerLastTimeReleasePowerCoolDownTime.put(player, cooldownTick);
        }
        if (item instanceof WraqBlade) {
            if (!WraqBlade.itemBladeCooldownRecord.containsKey(player)) {
                WraqBlade.itemBladeCooldownRecord.put(player, new HashMap<>());
            }
            Map<Item, Integer> map = WraqBlade.itemBladeCooldownRecord.get(player);
            map.put(item, cooldownTick);
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
            Mana.addOrCostPlayerMana(player, -manaCost);
        }
        return true;
    }

    public static void formatBroad(Level level, Component type, Component content) {
        formatBroad(type, content);
    }

    public static void formatBroad(Component content) {
        formatBroad(Te.s("维瑞阿契", ChatFormatting.AQUA), content);
    }

    public static void formatBroad(Component type, Component content) {
        List<ServerPlayer> playerList = Tick.server.getPlayerList().getPlayers();
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

    public static void sendBlankLine(Player player, int lines) {
        for (int i = 0; i < lines; i++) {
            player.sendSystemMessage(Component.literal(""));
        }
    }

    public static void msgSendToPlayer(Player player, Component content, int blank) {
        String blankString = " ".repeat(blank);
        player.sendSystemMessage(Component.literal(blankString).
                append(content));
    }

    public static MutableComponent getFormatMSG(Component type, Component content) {
        return Component.literal("[").withStyle(ChatFormatting.GRAY)
                .append(type).append("] ").withStyle(ChatFormatting.GRAY)
                .append(content);
    }


    public static void broad(Level level, Component component) {
        PlayerList list = level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        for (Player player : list1) {
            player.sendSystemMessage(component);
        }
    }

    public static void broad(Component component, int blank) {
        PlayerList list = Tick.server.getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        String blankString = " ".repeat(blank);
        for (Player player : list1) {
            player.sendSystemMessage(Component.literal(blankString).append(component));
        }
    }

    public static void RandomPotionBagProvider(Player player, int MaxNum, double Rate) {
        Random random = new Random();
        ItemStack[] itemStack = new ItemStack[13];
        itemStack[0] = ModItems.ATTACK_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[1] = ModItems.DEFENCE_PENETRATION_POTION_BAG.get().getDefaultInstance();
        itemStack[2] = ModItems.POWER_RELEASE_SPEED_POTION_BAG.get().getDefaultInstance();
        itemStack[3] = ModItems.CRIT_DAMAGE_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[4] = ModItems.CRIT_RATE_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[5] = ModItems.DEFENCE_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[6] = ModItems.HEALTH_STEAL_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[7] = ModItems.MANA_PENETRATION_POTION_BAG.get().getDefaultInstance();
        itemStack[8] = ModItems.MANA_DAMAGE_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[9] = ModItems.MANA_DEFENCE_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[10] = ModItems.MANA_RECOVER_POTION_BAG.get().getDefaultInstance();
        itemStack[11] = ModItems.MOVEMENT_SPEED_UP_POTION_BAG.get().getDefaultInstance();
        itemStack[12] = ModItems.HEALTH_RECOVER_POTION_BAG.get().getDefaultInstance();
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
                Compute.sendFormatMSG(player, Component.literal("酿造").withStyle(CustomStyle.styleOfBrew),
                        Component.literal("你的酿造经验为你节省了这次酿造的材料消耗并为你提供了经验值。"));
                Compute.givePercentExpToPlayer(player, 0.02, ExpUp, player.experienceLevel);
            }
            return true;
        }
        return false;
    }

    public static class PlayerIgnore {
        public static boolean ignoreItemGet(Player player) {
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

    public static double getCurrentVB(Player player) {
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
        int tier = 0;
        String SkillData = data.getString(StringUtils.SkillData.Mana);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') tier = 10;
            else tier = SkillData.charAt(index) - 48;
        }
        return tier;
    }

    public static void ChargingModule(CompoundTag data, Player player) {
        if (Compute.getSwordSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new SwordSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.getManaSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new ManaSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.getBowSkillLevel(data, 12) > 0)
            ModNetworking.sendToClient(new BowSkill12S2CPacket(8), (ServerPlayer) player);
    }

    public static Boolean isOnSky(LivingEntity entity) {
        int X = entity.getBlockX();
        int Y = entity.getBlockY();
        int Z = entity.getBlockZ();
        return entity.level().getBlockState(new BlockPos(X, Y - 2, Z)) == Blocks.AIR.defaultBlockState()
                || entity.level().getBlockState(new BlockPos(X, Y - 1, Z)) == Blocks.AIR.defaultBlockState();
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
        for (ForgePaper forgePaper : ForgePaper.forgePapers) {
            if (data.contains(forgePaper.getTag())) {
                ++forgingLevel;
            }
        }
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
                return 0.2;
            }
            case 2 -> {
                return 0.5;
            }
            case 3 -> {
                return 0.7;
            }
            case 4 -> {
                return 1;
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

    public static void playerHeal(Player player, double num) {
        if (num <= 0) return;
        double healNum = num * (PlayerAttributes.getHealingAmplification(player));
        if (AttackCurios5.onHealHealthRecover(player, healNum)) return;
        healNum = Math.min(healNum, player.getMaxHealth() - player.getHealth());
        LifeElementSword.StoreToList(player, healNum);
        player.heal((float) healNum);
    }

    public static void mobHeal(Mob mob, double num) {
        if (num < 0) return;
        double healNum = num * (MobAttributes.getMobHealAmplifier(mob));
        healNum = Math.min(healNum, mob.getMaxHealth() - mob.getHealth());
        mob.heal((float) healNum);
    }

    public static void mobHealthRecover(Mob mob, double percent) {
        if (mob.tickCount % 20 == 5) {
            mobHeal(mob, mob.getMaxHealth() * percent);
        }
    }

    public static Map<String, Integer> nextAllowSendMSGTickMap = new HashMap<>();
    public static void healByHealthSteal(Player player, Mob mob, double damage) {
        double rate = PlayerAttributes.healthSteal(player);
        double distance = player.distanceTo(mob);
        if (distance > 5) {
            rate *= (1 - (Math.min(20, distance) - 5) / 15);
        }
        if (MobSpawn.getMobOriginName(mob).equals(FrostInstance.mobName)) {
            rate = Math.max(0, rate - 0.2);
        }
        double healNum = damage * rate * 0.1;
        if (healNum > player.getMaxHealth() * 0.02) {
            healNum = Math.min(healNum, player.getMaxHealth() * 0.02);
            if (nextAllowSendMSGTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                sendFormatMSG(player, Te.s("治疗承受", CustomStyle.styleOfHealth),
                        Te.s("单次生命偷取的数额将不会超过",
                                ComponentUtils.AttributeDescription.maxHealth("2%")));
                nextAllowSendMSGTickMap.put(Name.get(player), Tick.get() + Tick.min(10));
            }
        }
        playerHeal(player, healNum);
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
        LogUtils.getLogger().info("{} {} {}", player.getName().getString(),
                Utils.LogTypes.itemUsed, itemStack.getItem());
        itemStack.shrink(1);
    }

    public static void PlayerItemUseOffHandWithRecord(Player player) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        LogUtils.getLogger().info("{} {} {}", player.getName().getString(),
                Utils.LogTypes.itemUsed, itemStack.getItem());
        itemStack.shrink(1);
    }

    public static void PlayerItemDeleteWithRecord(Player player, ItemStack itemStack) {

    }

    public static void playerItemUseWithRecord(Player player, int Num) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        itemStack.setCount(itemStack.getCount() - Num);
    }

    public static boolean IsSoulEquip(ItemStack itemStack) {
        return itemStack.is(ModItems.SOUL_SWORD.get()) || itemStack.is(ModItems.SOUL_BOW.get())
                || itemStack.is(ModItems.SOUL_SCEPTRE.get());
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

    public static Calendar castStringToCalendar(String DateString) {
        try {
            return StringToCalendar(DateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String CalendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        return tmpDate.format(date);
    }

    public static String castCalendarToString(Calendar calendar) {
        return CalendarToString(calendar);
    }

    public static int getPlayerReputation(Player player) {
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

    public static boolean addOrCostReputation(Player player, int num) {
        CompoundTag data = player.getPersistentData();
        ChatFormatting chatFormatting = ChatFormatting.GREEN;
        if (num < 0) {
            if (getPlayerReputation(player) + num < 0) {
                Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                        Component.literal("当前声望不足。").withStyle(ChatFormatting.WHITE));
                return false;
            }
            chatFormatting = ChatFormatting.RED;
        }
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + num);
        Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                Component.literal("你的声望值:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + getPlayerReputation(player)).withStyle(ChatFormatting.YELLOW)).
                        append(Component.literal(" (" + num + ")").withStyle(chatFormatting)));
        ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)), (ServerPlayer) player);
        return true;
    }

    public static void giveReputation(Player player, int reputation, Component type) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + reputation);
        data.putInt(StringUtils.ReputationCalculate, data.getInt(StringUtils.ReputationCalculate) + reputation);
        sendFormatMSG(player, Te.s("声望", ChatFormatting.YELLOW),
                getValueIncreaseMSG(String.valueOf(reputation), ChatFormatting.YELLOW,
                        String.valueOf(data.getInt(StringUtils.Reputation)), CustomStyle.styleOfStone, type));
    }

    public static void giveReputation(Player player, double reputation, Component type) {
        giveReputation(player, (int) reputation, type);
    }

    public static <T> Component getValueIncreaseMSG(String increaseValue, T increaseStyle,
                                                    String totalValue, T totalValueStyle, Component sourceType) {
        return Te.s(" + ", ChatFormatting.GREEN, increaseValue, increaseStyle, " ",
                "(" + totalValue + ")", totalValueStyle, " <- ", ChatFormatting.AQUA, sourceType);
    }

    public static double playerFantasyAttributeEnhance(Player player) {
        double enhance = 0;
        Set<Item> curioSet = Compute.CuriosAttribute.getDistinctCuriosSet(player);
        if (curioSet.contains(ModItems.FANTASY_MEDAL_2.get())) {
            enhance += 0.05;
        } else if (curioSet.contains(ModItems.FANTASY_MEDAL_1.get())) {
            enhance += 0.04;
        } else if (curioSet.contains(ModItems.FANTASY_MEDAL.get())) {
            enhance += 0.03;
        }
        if (curioSet.contains(ModItems.FANTASY_BRACELET_2.get())) {
            enhance += 0.05;
        } else if (curioSet.contains(ModItems.FANTASY_BRACELET_1.get())) {
            enhance += 0.04;
        } else if (curioSet.contains(ModItems.FANTASY_BRACELET.get())) {
            enhance += 0.03;
        }
        if (curioSet.contains(SpecialEventItems.SCALE_2025_0.get())) {
            enhance += 0.01;
        } else if (Compute.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_1.get())) {
            enhance += 0.02;
        } else if (Compute.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_2.get())) {
            enhance += 0.03;
        } else if (Compute.CuriosAttribute.getDistinctCuriosSet(player)
                .contains(SpecialEventItems.SCALE_2025_3.get())) {
            enhance += 0.04;
        }
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
        if (ParticleProvider.stop()) {
            return;
        }
        if (Utils.valueItemEntity.size() > 100) {
            for (int i = 0; i < 50; i++) {
                ItemEntityAndResetTime itemEntityAndResetTime = Utils.valueItemEntity.poll();
                if (itemEntityAndResetTime != null) {
                    itemEntityAndResetTime.getItemEntity().remove(Entity.RemovalReason.KILLED);
                }
            }
        }
        Vec3 delta = player.position().subtract(mob.position());
        Vec3 delta0 = new Vec3(delta.x, 0, delta.z);
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemEntity.setItem(ModItems.VALUE.get().getDefaultInstance());
        itemEntity.setCustomName(component);
        itemEntity.setCustomNameVisible(true);
        itemEntity.setNoGravity(true);
        Vec3 pos = mob.getEyePosition();
        Random r = new Random();
        if (type == 0)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(r.nextDouble()));
        if (type == 1)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(-1 * r.nextDouble()));
        itemEntity.moveTo(pos.add(r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25));
        itemEntity.setPickUpDelay(200);
        itemEntity.setDeltaMovement(new Vec3(delta0.normalize().scale(0.1).x, 0.1, delta0.normalize().scale(0.1).z));
        Utils.valueItemEntity.add(new ItemEntityAndResetTime(itemEntity, Tick.get() + 12));
        level.addFreshEntity(itemEntity);
    }

    public static void additionEffects(Player player, Mob mob, double damage, int type) {
        if (!Element.ElementPieceOnWeapon(player)) {
            Element.giveResonanceElement(player);
        }
        SuperColdCarrot.onHit(player, mob);
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
        ModNetworking.sendToClient(new RemoveEffectLastTimeS2CPacket("item/" + item), (ServerPlayer) player);
    }

    public static void removeEffectLastTime(Player player, String url) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeS2CPacket(url), (ServerPlayer) player);
    }

    public static void removeEffectLastTimeByItemId(Player player, String itemId) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeByItemIdS2CPacket(itemId), (ServerPlayer) player);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket("item/" + itemStack.getItem(), tickCount), (ServerPlayer) player);
    }

    public static void sendEffectLastTimeToClientPlayer(Item item, int level, int tick, boolean noTime) {
        ClientUtils.effectTimeLasts.removeIf(hudIcon -> hudIcon.url.equals("item/" + item.toString()));
        if (noTime) {
            ClientUtils.effectTimeLasts.add(new HudIcon("item/" + item, tick, tick, level, true));
        } else {
            ClientUtils.effectTimeLasts.add(new HudIcon("item/" + item, tick, tick, level));
        }
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount) {
        sendEffectLastTime(player, item, tickCount, 0, false);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount, int level, boolean forever) {
        sendEffectLastTime(player, itemStack.getItem(), tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int level, boolean forever) {
        sendEffectLastTime(player, item, 25565, level, forever);
    }

    public static void sendEffectLastTimeByItemId(Player player, String itemId, int level, boolean forever) {
        sendEffectLastTime(player, "item/" + itemId, 25565, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount, int level, boolean forever) {
        sendEffectLastTime(player, "item/" + item, tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int level, boolean forever) {
        sendEffectLastTime(player, url, 25565, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int tickCount, int level, boolean forever) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(url, tickCount, level, forever), (ServerPlayer) player);
    }

    public static void sendCoolDownTime(Player player, Item item, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket("item/" + item, tickCount), (ServerPlayer) player);
    }

    public static void sendCoolDownTime(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket("item/" + itemStack.getItem(), tickCount), (ServerPlayer) player);
    }

    public static void sendCoolDownTime(Player player, String url, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket(url, tickCount), (ServerPlayer) player);
    }

    public static void sendDebuffTime(Player player, String url, int tickCount, int level, boolean forever) {
        ModNetworking.sendToClient(new DebuffTimeS2CPacket(url, tickCount, level, forever), (ServerPlayer) player);
    }

    public static void sendDebuffTime(Player player, Item item, int tickCount, int level) {
        sendDebuffTime(player, "item/" + item, tickCount, level, false);
    }

    public static void sendDebuffTime(Player player, String url, int tickCount) {
        sendDebuffTime(player, url, tickCount, 0, false);
    }

    public static void removeDebuffTime(Player player, String url) {
        ModNetworking.sendToClient(new RemoveDebuffTimeS2CPacket(url), (ServerPlayer) player);
    }

    public static void removeDebuffTime(Player player, Item item) {
        ModNetworking.sendToClient(new RemoveDebuffTimeS2CPacket("item/" + item), (ServerPlayer) player);
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
        return false;
    }

    public static void repelMob(Player player, Vec3 StartPos, double range, double rate, double scaleLimit) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(StartPos, 20, 20, 20));
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(StartPos);
            if (PosVec.length() <= range) {
                if (!MonsterCantBeMove(mob)) {
                    mob.setDeltaMovement(PosVec.normalize().scale(Math.min(scaleLimit, rate / PosVec.length())));
                }
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

    public static Set<Mob> getPlayerRayMobList(Player player, double detectStep, double detectRange, double maxDistance) {
        Level level = player.level();
        Vec3 targetPos = player.pick(25, 0, false).getLocation();
        Vec3 startPos = player.pick(0.5, 0, false).getLocation();
        Vec3 posVec = targetPos.subtract(startPos).normalize();
        Set<Mob> mobs = new HashSet<>();
        for (double i = detectStep; i <= maxDistance; i += detectStep) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(startPos.add(posVec.scale(i)),
                    detectRange, detectRange, detectRange));
            mobs.addAll(mobList1);
        }
        return mobs;
    }

    public static void Laser(Player player, ParticleOptions particleOptions, double rate, int TickCoolDown, boolean isPower) {
        Level level = player.level();
        int TickCount = Tick.get();
        Vec3 TargetPos = player.pick(25, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.createLineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }

        if (!Utils.playerLaserCoolDown.containsKey(Name.get(player)))
            Utils.playerLaserCoolDown.put(Name.get(player), new HashMap<>());
        Map<Mob, Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(Name.get(player));

        mobList.forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob, TickCount + TickCoolDown);
                if (isPower) {
                    Damage.causeManaDamageToMonster(player, mob, rate, true);
                } else {
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                            rate, PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                            StringUtils.ParticleTypes.Lava);
                    ManaAttackModule.causeBaseAttack(player, mob, PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaPenetration0(player), level, newArrow, true);
                }
            }
        });
    }

    public static void TargetLocationLaser(Player player, Vec3 location, ParticleOptions particleOptions, double rate, int tickCoolDown) {
        Level level = player.level();
        int TickCount = Tick.get();
        Vec3 targetPos = location;
        Vec3 startPos = getPlayerHandItemPos(player, true);
        double distance = targetPos.distanceTo(startPos);
        ParticleProvider.createLineParticle(level, (int) distance * 5, startPos, targetPos, particleOptions);
        if (!Utils.playerLaserCoolDown.containsKey(Name.get(player))) {
            Utils.playerLaserCoolDown.put(Name.get(player), new HashMap<>());
        }
        Map<Mob, Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(Name.get(player));

        getPlayerRayMobList(player, 0.5, 0.5, distance).forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob, TickCount + tickCoolDown);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                        rate, PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Lava);
                ManaAttackModule.causeBaseAttack(player, mob, PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player), level, newArrow, true);
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

        public static Map<Player, List<ItemStack>> curiosListCache = new HashMap<>();

        /**
         * 获取玩家去重饰品列表
         */
        public static List<ItemStack> getDistinctCuriosList(Player player) {
            if (!curiosListCache.containsKey(player)) {
                List<ItemStack> curiosList = new ArrayList<>();
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    int size = iCuriosItemHandler.getEquippedCurios().getSlots();
                    Set<Item> curiosItemSet = new HashSet<>();
                    for (int i = 0; i < size; i++) {
                        ItemStack stack = iCuriosItemHandler.getEquippedCurios().getStackInSlot(i);
                        if (stack.is(Items.AIR)) continue;
                        if (!curiosItemSet.contains(stack.getItem())) {
                            if (!(stack.getItem() instanceof RepeatableCurios)) {
                                curiosItemSet.add(stack.getItem());
                            }
                            curiosList.add(stack);
                        }
                    }
                });
                curiosListCache.put(player, curiosList);
            }
            return curiosListCache.get(player);
        }

        @OnlyIn(Dist.CLIENT)
        public static Set<Item> getClientCuriosSet(Player player) {
            Set<Item> set = new HashSet<>();
            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                int size = iCuriosItemHandler.getEquippedCurios().getSlots();
                for (int i = 0; i < size; i++) {
                    ItemStack stack = iCuriosItemHandler.getEquippedCurios().getStackInSlot(i);
                    set.add(stack.getItem());
                }
            });
            return set;
        }

        public static Map<Player, Set<Item>> curiosSetCache = new HashMap<>();

        public static Set<Item> getDistinctCuriosSet(Player player) {
            if (!curiosSetCache.containsKey(player)) {
                Set<Item> set = new HashSet<>(getDistinctCuriosList(player)
                        .stream().map(itemStack -> (Item) itemStack.getItem())
                        .toList());
                curiosSetCache.put(player, set);
            }
            return curiosSetCache.get(player);
        }

        public static double attributeValue(Player player, Map<Item, Double> attributeMap, String attributeName) {
            if (attributeMap.equals(Utils.defencePenetration) || attributeMap.equals(Utils.manaPenetration)) {
                double rate = 1;
                for (ItemStack curioStack : getDistinctCuriosList(player)) {
                    Item curiosItem = curioStack.getItem();
                    if (attributeMap.containsKey(curiosItem)
                            && player.experienceLevel >= Utils.levelRequire.getOrDefault(curiosItem, 0)) {
                        rate *= (1 - attributeMap.get(curiosItem));
                    }
                    if (attributeName != null) {
                        CompoundTag data = curioStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(attributeName)) {
                            if (curiosItem instanceof RandomCurios) {
                                rate *= (1 - data.getDouble(attributeName)
                                        * RandomCuriosAttributesUtil.attributeValueMap.get(attributeName));
                            } else {
                                rate *= (1 - data.getInt(attributeName));
                            }
                        }
                    }
                }
                return 1 - rate;
            } else {
                return getDistinctCuriosList(player).stream()
                        .mapToDouble(stack -> {
                            double value = 0;
                            Item curiosItem = stack.getItem();
                            if (attributeMap.containsKey(curiosItem)
                                    && player.experienceLevel >= Utils.levelRequire.getOrDefault(curiosItem, 0)) {
                                value += attributeMap.get(curiosItem);
                            }
                            if (attributeName != null) {
                                CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
                                if (data.contains(attributeName)) {
                                    if (curiosItem instanceof RandomCurios) {
                                        value += data.getDouble(attributeName)
                                                * RandomCuriosAttributesUtil.attributeValueMap.get(attributeName);
                                    } else {
                                        value += data.getInt(attributeName);
                                    }
                                }
                            }
                            return value;
                        }).sum();
            }
        }
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

    public static boolean hasCurios(Player player, Item curios) {
        return CuriosAttribute.getDistinctCuriosSet(player).contains(curios);
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

    public static void sendActionBarTextContentToPlayer(Player player, Component content) {
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket
                = new ClientboundSetActionBarTextPacket(content);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static boolean PlayerCanChallengeThisInstance(Player player, int instanceNum) {
        CompoundTag data = player.getPersistentData();
        if (instanceNum > 9) return true;
        if (data.getInt(StringUtils.PlayerInstanceProgress) >= instanceNum) return true;
        return false;
    }

    public record GatherEntity(LivingEntity livingEntity, int tick, Vec3 pos) {
    }

    public static List<GatherEntity> gatherEntityList = new ArrayList<>();

    public static void gather(int TickCount) {
        gatherEntityList.removeIf(gatherEntity -> gatherEntity.tick < TickCount);
        gatherEntityList.forEach(gatherEntity -> {
            if (gatherEntity.livingEntity instanceof Mob mob) {
                if (!MonsterCantBeMove(mob)) {
                    gatherEntity.livingEntity.setDeltaMovement(
                            gatherEntity.pos.subtract(gatherEntity.livingEntity.position()).scale(0.2));
                }
            } else if (gatherEntity.livingEntity instanceof Player player) {
                sendMotionPacketToPlayer(player,
                        gatherEntity.pos.subtract(gatherEntity.livingEntity.position()).scale(0.2));
            }
        });
    }

    public static void causeGatherEffect(LivingEntity livingEntity, int lastTick, Vec3 gatherPos) {
        gatherEntityList.add(new GatherEntity(livingEntity, Tick.get() + lastTick, gatherPos));
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

    public static Vec3 getLivingEntityBackOffsetPos(LivingEntity livingEntity) {
        Vec3 vec3 = livingEntity.pick(-1, 0, false).getLocation();
        return vec3.add(livingEntity.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(Math.sqrt(4)));
    }

    public static Vec3 getLivingEntityFrontOffsetPos(LivingEntity livingEntity) {
        Vec3 vec3 = livingEntity.pick(1, 0, false).getLocation();
        return vec3.add(livingEntity.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(Math.sqrt(4)));
    }

    public static Vec3 GetPlayerBackPos(Player player, int type) {
        Vec3 vec3 = player.pick(-1, 0, false).getLocation();
        switch (type) {
            case 0 -> {
                return vec3.add(0, 2, 0);
            }
            case 1 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(Math.sqrt(4)));
            }
            case 2 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(-Math.sqrt(4)));
            }
            case 3 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(4)).add(0, -1, 0);
            }
            case 4 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(-4)).add(0, -1, 0);
            }
        }
        vec3 = player.pick(-1, 0, false).getLocation().
                add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()));
        return vec3;
    }

    public static Vec3 getPlayerHandItemPos(LivingEntity livingEntity, boolean isRight) {
        return livingEntity.pick(0.5, 0, false).getLocation().
                add(livingEntity.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(isRight ? 1 : -1));
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
                        && (!Utils.levelRequire.containsKey(item)
                        || Utils.levelRequire.get(item) <= player.experienceLevel)) {
                    if (item instanceof WraqMainHandOrPassiveEquip wraqMainHandOrPassiveEquip) {
                        if (!(player.getMainHandItem().is(item) && player.getInventory().selected >= 3)) {
                            double computeValue = 0;
                            double baseValue = 0;
                            baseValue += ForgeEquipUtils
                                    .getTraditionalEquipBaseValue(equip, map, player,
                                            map.equals(Utils.attackDamage)
                                                    || map.equals(Utils.manaDamage)
                                                    || map.equals(Utils.maxHealth));
                            computeValue += baseValue;
                            // 只有能被强化的属性才能用这个公式去计算数值
                            if (map.equals(Utils.attackDamage)
                                    || map.equals(Utils.manaDamage)
                                    || map.equals(Utils.maxHealth)) {
                                computeValue += Compute.forgingValue(equip, baseValue);
                            }
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

    public static void decreasePlayerHealth(Player player, double value, Component component) {
        if (player.isDeadOrDying()) {
            return;
        }
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

    public static void decreasePlayerHealth(Player player, double value, double threshold) {
        if ((player.getHealth() - value) / player.getMaxHealth() < threshold) {
            player.setHealth((float) (player.getMaxHealth() * threshold));
        } else player.setHealth((float) (player.getHealth() - value));
    }

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, WeakHashMap<Player, Integer> lastTickMap, int lastTick, int manaCost, int coolDownSeconds) {
        int tickCount = Tick.get();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            Compute.sendCoolDownTime(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            lastTickMap.put(player, tickCount + lastTick);
            Compute.sendEffectLastTime(player, item.getDefaultInstance(), lastTick);
            return true;
        }
        return false;
    }

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, int manaCost, int coolDownSeconds) {
        int tickCount = Tick.get();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            Compute.sendCoolDownTime(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            return true;
        }
        return false;
    }

    public static List<Item> mobArmorList = new ArrayList<>();

    public static void setMobArmorList() {
        mobArmorList.add(ModItems.MOB_ARMOR_CASTLE_KNIGHT_HELMET.get());
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
        ParticleProvider.createLineParticle(player.level(), (int) NearestMob.distanceTo(player),
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
        ParticleProvider.createLineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }
        mobList.forEach(mob -> {
            if (isAd) {
                Damage.causeAttackDamageToMonster(player, mob, damage);
            } else {
                Damage.causeManaDamageToMonster(player, mob, damage);
            }
        });
        return mobList;
    }

    public static double playerExHarvest(Player player) {
        double rate = 0;
        rate += LabourDayIronHoe.playerExHarvest(player);
        rate += LabourDayIronPickaxe.playerExHarvest(player);
        int tier = PlanPlayer.getPlayerTier(player);
        rate += new double[]{0, 0.15, 0.3, 0.5}[tier];
        rate += Compute.getPlayerPotionEffectRate(player, ModEffects.EX_HARVEST.get(), 0.15, 0.25);
        rate += HoursExHarvestPotion.getHoursExHarvestPotionEnhanceRate(player);
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerExHarvestModifier);
        rate += QingMingCommonRing.getExHarvest(player);
        rate += RankData.getExHarvestRate(player);
        rate += LabourDayOldCoin.getExHarvest();
        rate += EstateUtil.getExHarvestRate(player);
        rate += Summer2025.getExHarvestRate();
        return rate;
    }

    public static boolean exHarvestItemGive(Player player, ItemStack itemStack, double baseRate) {
        Random random = new Random();
        if (random.nextDouble() < baseRate * Compute.playerExHarvest(player)) {
            Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
            InventoryOperation.giveItemStack(player, itemStack);
            return true;
        }
        return false;
    }

    public static void playerDeadModule(Player player) {
        Tower.playerInChallengingDeadOrLogout(player);
        Utils.PlayerDeadTimeMap.put(player.getName().getString(), Tick.get() + 6000);
        NoTeamInstanceModule.onDead(player);
        AncientEchoGem.lastRecordSumMap.remove(player);
        AncientEchoGem.withstandDamageSumMap.remove(player);
        BunkerArmor.onPlayerDead(player);
        ColdData.addPlayerColdValue(player, -100);
    }

    public static void manaDamageExEffect(Player player, Mob mob, double damage) {
        CastleSceptre.exDamage(player, mob, damage);
    }

    public static boolean playerIsInBattle(Player player) {
        return StarBottle.playerLastBattleTick.containsKey(player)
                && StarBottle.playerLastBattleTick.get(player) + 100 > Tick.get();
    }

    public static boolean playerIsInBattle(Player player, int tick) {
        return StarBottle.playerLastBattleTick.containsKey(player)
                && StarBottle.playerLastBattleTick.get(player) + tick > Tick.get();
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
    public static ServerPlayer getPlayerByName(String name) {
        return Tick.server.getPlayerList().getPlayerByName(name);
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

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, 20, 60, 20);
    }

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle,
                                                 int fadeIn, int stay, int fadeOut) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setPlayerShortTitleAndSubTitle(Player player, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, 0, 20, 10);
    }

    public static List<? extends Entity> getNearEntity(Entity center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = center.level().getEntitiesOfClass(type, AABB.ofSize(center.position(), distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.distanceTo(center) <= distance).toList();
    }

    public static List<? extends Entity> getNearEntity(Level level, Vec3 center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = level.getEntitiesOfClass(type, AABB.ofSize(center, distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.position().distanceTo(center) <= distance).toList();
    }

    public static List<Mob> getNearMob(Entity center, double distance) {
        return getNearEntity(center, Mob.class, distance).stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .toList();
    }

    public static List<Mob> getNearMob(Level level, Vec3 pos, double distance) {
        return getNearEntity(level, pos, Mob.class, distance).stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .toList();
    }

    public static Set<Player> getNearPlayer(Level level, Vec3 center, double radius) {
        return getNearEntity(level, center, Player.class, radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toSet());
    }

    public static Player getNearestPlayer(LivingEntity livingEntity, double radius) {
        return getNearPlayer(livingEntity.level(), livingEntity.position(), radius).stream().min(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.distanceTo(livingEntity) - o2.distanceTo(livingEntity));
            }
        }).orElse(null);
    }

    public static Mob getNearestMob(Player player, double radius) {
        return getNearMob(player.level(), player.position(), radius).stream().min(new Comparator<Mob>() {
            @Override
            public int compare(Mob o1, Mob o2) {
                return (int) (o1.distanceTo(player) - o2.distanceTo(player));
            }
        }).orElse(null);
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, Item icon, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), "item/" + icon, tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, String url, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), url, tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void removeMobEffectHudToNearPlayer(Mob mob, Item icon, String tag) {
        removeMobEffectHudToNearPlayer(mob, "item" + icon, tag);
    }

    public static void removeMobEffectHudToNearPlayer(Mob mob, String url, String tag) {
        List<? extends Entity> list = getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), url, tag, 0, 0, false), serverPlayer);
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

    public static void decreaseCooldownLeftTick(Player player, Item item, Map<Player, Integer> itemCooldownMap, int decreaseTick) {
        if (itemCooldownMap.containsKey(player)) {
            double percent = player.getCooldowns().getCooldownPercent(item, 0);
            int leftTick = (int) (percent * itemCooldownMap.get(player));
            leftTick = Math.max(0, leftTick - decreaseTick);
            player.getCooldowns().addCooldown(item, leftTick);
            itemCooldownMap.put(player, leftTick);
        }
    }

    public record LowGravityZone(ResourceKey<Level> dimension, Pair<Vec3, Vec3> space) {
    }

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
     *
     * @param value 数值
     * @return 简化后字符串形式数值
     */
    public static String getSimplifiedNumberDescription(double value) {
        if (value < 1000) {
            // 正常显示 999
            return String.format("%.0f", value);
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

    public static void clearPlayerScreen(Player player) {
        ModNetworking.sendToClient(new ScreenSetS2CPacket(0), (ServerPlayer) player);
    }

    public static boolean isEntityInTwoPoint(Entity entity, Vec3 downPos, Vec3 upPos) {
        return entity.getX() > downPos.x
                && entity.getY() > downPos.y
                && entity.getZ() > downPos.z
                && entity.getX() < upPos.x
                && entity.getY() < upPos.y
                && entity.getZ() < upPos.z;
    }

    public static void sendMotionPacketToPlayer(Player player, Vec3 vec3) {
        ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                new ClientboundSetEntityMotionPacket(player.getId(), vec3);
        ((ServerPlayer) player).connection.send(clientboundSetEntityMotionPacket);
    }

    public static void sendForwardMotionPacketToPlayer(Player player, double scale) {
        sendMotionPacketToPlayer(player, player.pick(1, 0, false)
                .getLocation().subtract(player.getEyePosition()).normalize().scale(scale));
    }

    public static double getPlayerPotionEffectRate(Player player, MobEffect effect, double tier1Rate, double tier2Rate) {
        if (player.hasEffect(effect)) {
            int amplifier = player.getEffect(effect).getAmplifier();
            if (amplifier == 0) {
                return tier1Rate;
            } else {
                return tier2Rate;
            }
        }
        return 0;
    }

    private final String notePaperExpiredTime = "notePaperExpiredTime";

    public static boolean notePaperExpired(ItemStack stack) {
        return true;
    }

    public static String getPercent(double value) {
        return String.format("%.0f%%", value * 100);
    }

    public static CompoundTag getPlayerSpecificKeyCompoundTagData(Player player, String dataKey) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(dataKey)) {
            tag.put(dataKey, new CompoundTag());
        }
        return tag.getCompound(dataKey);
    }

    public static int getSpecificKeyDataIntValue(Player player, String dataKey, String valueKey) {
        return getPlayerSpecificKeyCompoundTagData(player, dataKey).getInt(valueKey);
    }

    public static void setSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int value) {
        getPlayerSpecificKeyCompoundTagData(player, dataKey).putInt(valueKey, value);
    }

    public static void incrementSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int increment) {
        CompoundTag data = getPlayerSpecificKeyCompoundTagData(player, dataKey);
        data.putInt(valueKey, data.getInt(valueKey) + increment);
    }

    public static void incrementDataIntValue(Player player, String key, int increment) {
        CompoundTag data = player.getPersistentData();
        data.putInt(key, data.getInt(key) + increment);
    }

    public static int getDataIntValue(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(key);
    }

    public static void setDataBooleanValue(Player player, String key, boolean value) {
        CompoundTag data = player.getPersistentData();
        data.putBoolean(key, value);
    }

    public static boolean getDataBooleanValue(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        return data.getBoolean(key);
    }

    public static Set<Mob> getPlayerVisionConicalMobs(Player player, int maxDistance) {
        Set<Mob> mobSet = new HashSet<>();
        for (int i = 0; i < maxDistance; i++) {
            Vec3 pickPos = player.pick(i, 0, false).getLocation();
            int finalI = i;
            player.level().getEntitiesOfClass(Mob.class,
                            AABB.ofSize(pickPos, i * 2, i * 2, i * 2))
                    .stream().filter(mob -> mob.position().distanceTo(pickPos) < finalI)
                    .forEach(mobSet::add);
        }
        return mobSet;
    }

    public static ItemStack getSkullByName(String skullName) {
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        stack.getOrCreateTag().putString("SkullOwner", skullName);
        return stack;
    }

    public static ItemStack getSimpleFoiledItemStack(Item item) {
        ItemStack itemStack = new ItemStack(item);
        itemStack.enchant(Enchantments.UNBREAKING, 1);
        return itemStack;
    }

    public static void openTradeScreenByVillagerName(Player player, String villagerName) {
        ModNetworking.sendToClient(new VillagerTradeScreenS2CPacket(villagerName), (ServerPlayer) player);
    }

    public static void sendErrorTips(Player player, Component content) {
        sendFormatMSG(player, Te.s("错误", ChatFormatting.RED), content);
    }

    public static double getHorizonDistance(Vec3 pos1, Vec3 pos2) {
        Vec2 pos11 = new Vec2((float) pos1.x, (float) pos1.z);
        Vec2 pos22 = new Vec2((float) pos2.x, (float) pos2.z);
        return Math.sqrt(pos11.distanceToSqr(pos22));
    }

    public static void teleportPlayerToPos(Player player, Vec3 pos) {
        teleportPlayerToPos(player, pos, 0, 0);
    }

    public static void teleportPlayerToPos(Player player, Vec3 pos, float rotX, float rotY) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.teleportTo(serverPlayer.serverLevel(), pos.x, pos.y, pos.z, rotX, rotY);
    }

    public static final String CHALLENGE_RECORD_KEY = "ChallengeRecord";

    public static CompoundTag getChallengeRecordData(Player player) {
        return getPlayerSpecificKeyCompoundTagData(player, CHALLENGE_RECORD_KEY);
    }

    public static Vec3 getPickLocationIgnoreBlock(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 shortPickPos = player.pick(0.5, 0, false).getLocation();
        return eyePos.add(shortPickPos.subtract(eyePos).normalize().scale(distance));
    }

    public static void summonArmorStand(Level level, Vec3 pos, Component name) {
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        armorStand.setNoGravity(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.noPhysics = true;
        armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
        armorStand.moveTo(pos);
        level.addFreshEntity(armorStand);
    }

    public static void removeNearArmorStand(Level level, Vec3 pos, double radius) {
        level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(pos, radius * 2, radius * 2, radius * 2))
                .forEach(armorStand -> {
                    armorStand.remove(Entity.RemovalReason.KILLED);
                });
    }

    public interface MobCauseDamageToPlayer {
        void causeDamage(Mob mob, Player player);
    }

    public static void createRangeEffectDot(Mob boss, Vec3 pos, double radius,
                                            MobCauseDamageToPlayer cause, Style style,
                                            int trigTick, int lastTick) {
        // 造成伤害
        PersistentRangeEffect.addEffect(boss, pos, radius, (effect -> {
            Compute.getNearEntity(boss.level(), effect.center, Player.class, radius)
                    .stream().filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .forEach(eachPlayer -> {
                        cause.causeDamage(boss, eachPlayer);
                    });
        }), trigTick, lastTick);

        // 制造粒子
        ParticleProvider.createSpaceEffectParticle(boss.level(), pos, radius, 100, style, lastTick);
    }

    public interface CauseDamageToPlayer {
        void causeDamage(Player player);
    }

    public static void createRangeEffectDot(Level level, Vec3 pos, double radius,
                                            CauseDamageToPlayer cause, Style style,
                                            int startTick, int trigTick, int lastTick) {
        // 造成伤害
        PersistentRangeEffect.addEffect(level, pos, radius, (effect -> {
            Compute.getNearEntity(level, effect.center, Player.class, radius)
                    .stream().filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .forEach(cause::causeDamage);
        }), startTick, trigTick, lastTick);
        // 制造粒子
        ParticleProvider.createSpaceEffectParticle(level, pos, radius,
                100, style, lastTick + (startTick - Tick.get()));
    }

    public static final String TEMP_TAG_KEY = "tempTagKey";
    public static CompoundTag getTempTag(Player player) {
        return getPlayerSpecificKeyCompoundTagData(player, TEMP_TAG_KEY);
    }

    public static boolean isOverworldNight() {
        return Tick.server.overworld().isNight();
    }

    public static void sendCommandOpMSG(Player player, String content) {
        if (player == null) {
            LogUtils.getLogger().info(content);
        } else {
            player.sendSystemMessage(Te.s(content));
        }
    }

    public static @Nullable Mob getDefaultTarget(Player player) {
        Set<Mob> set = Compute.getPlayerRayMobList(player, 0.5, 1, 32);
        if (!set.isEmpty()) {
            return set.stream().min(new Comparator<Mob>() {
                @Override
                public int compare(Mob o1, Mob o2) {
                    return (int) (o1.distanceTo(player) - o2.distanceTo(player));
                }
            }).orElse(null);
        }
        return null;
    }

    public static void addImprisonEffectToMob(Mob mob, int lastTick) {
        if (MobSpawn.canAddSlowDownOrImprison(mob)) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, lastTick, 99, false, false, false));
            Compute.sendMobEffectHudToNearPlayer(mob, "hud/imprison", "imprison", lastTick, 0, false);
        }
    }

    public static final String EXPIRED_DATE_DATA_KEY = "ExpiredDate";

    public static void setStackExpiredDate(ItemStack stack, Calendar expiredDate) {
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        tag.putString(EXPIRED_DATE_DATA_KEY, Compute.castCalendarToString(expiredDate));
    }

    public static @Nullable Calendar getStackExpiredDate(ItemStack stack) {
        if (stack.getTagElement(EXPIRED_DATE_DATA_KEY) == null) {
            return null;
        }
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (!tag.contains(EXPIRED_DATE_DATA_KEY)) {
            return null;
        }
        return Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
    }

    public static boolean isExpiredDateValid(ItemStack stack) {
        Calendar calendar = Calendar.getInstance();
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            Calendar recordDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            return recordDate.after(calendar);
        } else {
            return true;
        }
    }

    public static void addExpiredDateTooltips(ItemStack stack, List<Component> components) {
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (stack.getItem() instanceof TpPass) {
            return;
        }
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar expiredDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            if (isExpiredDateValid(stack)) {
                components.add(Te.s(" 在",
                        dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, "前有效"));
            } else {
                components.add(Te.s(" 在", ChatFormatting.STRIKETHROUGH,
                        dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, ChatFormatting.STRIKETHROUGH,
                        "前有效", ChatFormatting.STRIKETHROUGH));
                components.add(Te.s(" 已失效.", ChatFormatting.RED));
            }
        }
    }
}
