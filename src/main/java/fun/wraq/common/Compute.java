package fun.wraq.common;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.process.system.worldtext.WorldTextDataManager;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.BowAttribute;
import fun.wraq.common.equip.SceptreAttribute;
import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.*;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.DamageNumberS2CPacket;
import fun.wraq.networking.misc.EntropyPackets.EntropyS2CPacket;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.system.buff.BuffSystem;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.element.render.Color;
import fun.wraq.render.hud.Mana;
import fun.wraq.series.events.ForgePaper;
import fun.wraq.series.instance.blade.WraqBlade;
import fun.wraq.series.instance.series.castle.CastleSceptre;
import fun.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import fun.wraq.series.overworld.cold.sc5.dragon.SuperColdCarrot;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.acos;


public class Compute {

    public static double getSwordSkill1And4(CompoundTag data, Player player) {
        double withStandDamageRate = 0;
        if (SwordAttribute.isHandling(player)) {
            withStandDamageRate -= Compute.getSwordSkillLevel(data, 1) * 0.01;
            withStandDamageRate += Compute.getSwordSkillLevel(data, 4) * 0.015;
        }
        return withStandDamageRate;
    }

    public static double getSwordSkill14(CompoundTag data, Player player, LivingEntity monster) {
        double damageRate = 0;
        if (getSwordSkillLevel(data, 14) > 0) {
            double playerHealthRate = player.getHealth() / player.getMaxHealth();
            double monsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (playerHealthRate < monsterHealthRate) {
                damageRate -= 0.2 * Math.min(1.0, (monsterHealthRate - playerHealthRate) / 0.66);
            }
        }
        return damageRate;
    }

    public static double getBowSkill4(CompoundTag data, Player player) {
        double damageRate = 0;
        if (BowAttribute.isHandling(player)) {
            damageRate += Compute.getBowSkillLevel(data, 4) * 0.015;
        }
        return damageRate;
    }

    public static double getManaSkill4(CompoundTag data, Player player) {
        double damageRate = 0;
        if (SceptreAttribute.isHandling(player)) {
            damageRate += Compute.getManaSkillLevel(data, 4) * 0.015;
        }
        return damageRate;
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
            if (Mana.playerManaCost(player, activeItem.manaCost(player))) {
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

    public static void formatBroad(Level level, Component type, Component content) {
        MessageUtil.formatBroad(level, type, content);
    }

    public static void formatBroad(Component content) {
        MessageUtil.formatBroad(content);
    }

    public static void formatBroad(Component type, Component content) {
        MessageUtil.formatBroad(type, content);
    }

    public static void sendFormatMSG(Player player, Component type, Component content) {
        MessageUtil.sendFormatMSG(player, type, content);
    }

    public static void sendBlankLine(Player player, int lines) {
        MessageUtil.sendBlankLine(player, lines);
    }

    public static void msgSendToPlayer(Player player, Component content, int blank) {
        MessageUtil.msgSendToPlayer(player, content, blank);
    }

    public static MutableComponent getFormatMSG(Component type, Component content) {
        return MessageUtil.getFormatMSG(type, content);
    }


    public static void broad(Level level, Component component) {
        MessageUtil.broad(level, component);
    }

    public static void broad(Component component, int blank) {
        MessageUtil.broad(component, blank);
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

    public static class PlayerIgnore {
        public static boolean ignoreItemGet(Player player) {
            return player.getPersistentData().contains(StringUtils.IgnoreType.ItemGet)
                    && player.getPersistentData().getBoolean(StringUtils.IgnoreType.ItemGet);
        }
    }

    public static void VBIncomeAndMSGSend(Player player, double num) {
        CurrencyUtil.VBIncomeAndMSGSend(player, num);
    }

    public static void VBExpenseAndMSGSend(Player player, double num) {
        CurrencyUtil.VBExpenseAndMSGSend(player, num);
    }

    public static double getCurrentVB(Player player) {
        return CurrencyUtil.getCurrentVB(player);
    }

    public static boolean RecallPlayerCheck(ServerPlayer serverPlayer) {
        if (Utils.kazeRecall.recallPlayer != null && Utils.kazeRecall.recallPlayer.equals(serverPlayer)) return true;
        return Utils.spiderRecall.recallPlayer != null && Utils.spiderRecall.recallPlayer.equals(serverPlayer);
    }

    public static double Vec3Angle(Vec3 VecA, Vec3 VecB) {
        return acos(abs(VecA.dot(VecB)) / (VecA.length() * VecB.length()));
    }

    public static int getSwordSkillLevel(CompoundTag data, int index) {
        return SkillDataUtil.getSwordSkillLevel(data, index);
    }

    public static int getBowSkillLevel(CompoundTag data, int index) {
        return SkillDataUtil.getBowSkillLevel(data, index);
    }

    public static int getManaSkillLevel(CompoundTag data, int index) {
        return SkillDataUtil.getManaSkillLevel(data, index);
    }

    public static void ChargingModule(CompoundTag data, Player player) {
        SkillDataUtil.ChargingModule(data, player);
    }

    public static Boolean isOnSky(LivingEntity entity) {
        int X = entity.getBlockX();
        int Y = entity.getBlockY();
        int Z = entity.getBlockZ();
        return entity.level().getBlockState(new BlockPos(X, Y - 2, Z)).is(Blocks.AIR)
                || entity.level().getBlockState(new BlockPos(X, Y - 1, Z)).is(Blocks.AIR);
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
        TooltipUtil.RuneAttributeDescription(components);
    }

    public static void DescriptionPassive(List<Component> components, Component name) {
        TooltipUtil.DescriptionPassive(components, name);
    }

    public static void solePassiveDescription(List<Component> components, Component name) {
        TooltipUtil.solePassiveDescription(components, name);
    }

    public static void DescriptionActive(List<Component> components, Component name) {
        TooltipUtil.DescriptionActive(components, name);
    }

    public static double forgingValue(ItemStack itemStack, double baseValue) {
        if (itemStack.getTagElement(Utils.MOD_ID) == null) return 0;
        return forgingValue(itemStack.getOrCreateTagElement(Utils.MOD_ID), baseValue);
    }

    public static double forgingValue(CompoundTag data, double baseValue) {
        int forgingLevel = getForgingLevel(data);
        return baseValue * getForgingValueRate(forgingLevel);
    }

    public static double getForgingValueRate(CompoundTag data) {
        return getForgingValueRate(getForgingLevel(data));
    }

    public static int getForgingLevel(CompoundTag data) {
        int forgingLevel = data.getInt("Forging");
        for (ForgePaper forgePaper : ForgePaper.forgePapers) {
            if (data.contains(forgePaper.getTag())) {
                ++forgingLevel;
            }
        }
        return forgingLevel;
    }

    public static double getForgingValueRate(int forgingLevel) {
        double baseValueUnder10 = 0.01;
        double baseValueUnder20 = 0.015;
        double baseValueUnder24 = 0.02;
        double baseValueUpper24 = 0.03;
        if (forgingLevel <= 10) {
            return baseValueUnder10 * forgingLevel;
        } else if (forgingLevel <= 20) {
            return baseValueUnder20 * (forgingLevel - 10) + 10 * baseValueUnder10;
        } else if (forgingLevel <= 24) {
            return baseValueUnder24 * (forgingLevel - 20) + 10 * baseValueUnder10 + 10 * baseValueUnder20;
        } else {
            return baseValueUpper24 * (forgingLevel - 24) + 10 * baseValueUnder10 + 10 * baseValueUnder20 + 4 * baseValueUnder24;
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

    public static void playerHeal(Player player, double num) {
        HealUtil.playerHeal(player, num);
    }

    public static void mobHeal(Mob mob, double num) {
        HealUtil.mobHeal(mob, num);
    }

    public static void mobHealthRecover(Mob mob, double percent) {
        HealUtil.mobHealthRecover(mob, percent);
    }

    public static Map<String, Integer> nextAllowSendMSGTickMap = HealUtil.nextAllowSendMSGTickMap;
    public static void healByHealthSteal(Player player, Mob mob, double damage) {
        HealUtil.healByHealthSteal(player, mob, damage);
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, Style MainStyle) {
        return TooltipUtil.SuitItemVision(player, item, equipmentSlot, components, MainStyle);
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, ChatFormatting MainStyle) {
        return TooltipUtil.SuitItemVision(player, item, equipmentSlot, components, MainStyle);
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

    public static void ManaCoreDescription(List<Component> components) {
        TooltipUtil.ManaCoreDescription(components);
    }

    public static Calendar StringToCalendar(String DateString) throws ParseException {
        return CalendarUtil.StringToCalendar(DateString);
    }

    public static Calendar castStringToCalendar(String DateString) {
        return CalendarUtil.castStringToCalendar(DateString);
    }

    public static String CalendarToString(Calendar calendar) {
        return CalendarUtil.CalendarToString(calendar);
    }

    public static String castCalendarToString(Calendar calendar) {
        return CalendarUtil.castCalendarToString(calendar);
    }

    public static long calenderDateDifference(Calendar cal1, Calendar cal2) {
        return CalendarUtil.calenderDateDifference(cal1, cal2);
    }

    public static long calenderMinuteDifference(Calendar cal1, Calendar cal2) {
        return CalendarUtil.calenderMinuteDifference(cal1, cal2);
    }

    public static String getDifferenceFormatText(Calendar cal1, Calendar cal2) {
        return CalendarUtil.getDifferenceFormatText(cal1, cal2);
    }

    public static <T> Component getValueIncreaseMSG(String increaseValue, T increaseStyle,
                                                    String totalValue, T totalValueStyle, Component sourceType) {
        return Te.s(" + ", ChatFormatting.GREEN, increaseValue, increaseStyle, " ",
                "(" + totalValue + ")", totalValueStyle, " <- ", ChatFormatting.AQUA, sourceType);
    }

    public static boolean stopSummon = false;

    public static void summonValue(Player player, Mob mob, double value, Style style, int type) {
        if (stopSummon || value < 1) {
            return;
        }
        Component component = Component.literal(String.format("%.0f", value)).withStyle(style);
        Vec3 pos = mob.getEyePosition();
        Random r = new Random();
        if (type == 0)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(r.nextDouble()));
        if (type == 1)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(-1 * r.nextDouble()));
        pos = pos.add(r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25);
        DamageNumberS2CPacket packet = new DamageNumberS2CPacket(pos, component, 1000);
        ModNetworking.sendToClientsTrackingEntity(packet, mob);
    }

    public static void summonValue(Player player, Mob mob, Component component, int type) {
        if (stopSummon) {
            return;
        }
        Vec3 pos = mob.getEyePosition();
        Random r = new Random();
        if (type == 0)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(r.nextDouble()));
        if (type == 1)
            pos = pos.add(player.getHandHoldingItemAngle(ModItems.PLAIN_SWORD_0.get()).scale(-1 * r.nextDouble()));
        pos = pos.add(r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25, r.nextDouble(0.5) - 0.25);
        DamageNumberS2CPacket packet = new DamageNumberS2CPacket(pos, component, 1000);
        ModNetworking.sendToClientsTrackingEntity(packet, mob);
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

    public static void repelPlayer(Mob mob, Vec3 StartPos, double range, double rate, double scaleLimit) {
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
        return EntityQueryUtil.getPlayerRayMobList(player, detectStep, detectRange, maxDistance);
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

    public static Mob detectPlayerPickMob(Player player) {
        return EntityQueryUtil.detectPlayerPickMob(player);
    }

    public static Entity detectPlayerPickEntity(Player player, double distance, double range, Class<? extends Entity> clazz) {
        return EntityQueryUtil.detectPlayerPickEntity(player, distance, range, clazz);
    }

    public static void ParticleEffectAddOnEntity(Entity entity, ParticleOptions particleOptions) {
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1.25, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.75, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.5, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.25, 0.4, 8, particleOptions, 0);
    }

    public static void EndTp(List<Player> playerList, Vec3 pos) {
        playerList.forEach(player -> {
            ((ServerPlayer) player).teleportTo((ServerLevel) player.level(), pos.x, pos.y, pos.z, 0, 0);
        });
    }

    public static void sendActionBarTextContentToPlayer(Player player, Component content) {
        HudUtil.sendActionBarTextContentToPlayer(player, content);
    }

    public static boolean PlayerCanChallengeThisInstance(Player player, int instanceNum) {
        CompoundTag data = player.getPersistentData();
        if (instanceNum > 9) return true;
        if (data.getInt(StringUtils.PlayerInstanceProgress) >= instanceNum) return true;
        return false;
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

    public static void LevelRequire(List<Component> components, int Level) {
        TooltipUtil.LevelRequire(components, Level);
    }

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, WeakHashMap<Player, Integer> lastTickMap, int lastTick, int manaCost, int coolDownSeconds) {
        int tickCount = Tick.get();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Mana.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            BuffSystem.sendCoolDownTime(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            lastTickMap.put(player, tickCount + lastTick);
            BuffSystem.sendEffectLastTime(player, item.getDefaultInstance(), lastTick);
            return true;
        }
        return false;
    }

    public static boolean PlayerUseWithHud(Player player, WeakHashMap<Player, Integer> coolDownMap, Item item, int manaCost, int coolDownSeconds) {
        int tickCount = Tick.get();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Mana.playerManaCost(player, manaCost);
            coolDownMap.put(player, tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
            BuffSystem.sendCoolDownTime(player, item.getDefaultInstance(), (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.coolDownDecrease(player))));
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

    public static void igniteMob(Player player, Mob mob, int lastTick) {
        FireEquip.PlayerIgniteMobEffect(player, mob);
        mob.setRemainingFireTicks(lastTick);
    }

    public static List<Mob> shootOneLaser(Player player, boolean isAd, double damage, ParticleOptions particleOptions) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(25, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.createLineParticle(level, (int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class,
                    AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                if (Compute.isWraqMob(mob) && !mobList.contains(mob)) {
                    mobList.add(mob);
                }
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

    public static boolean exHarvestItemGive(Player player, ItemStack itemStack, double baseRate) {
        Random random = new Random();
        if (random.nextDouble() < baseRate * PlayerAttributes.playerExHarvest(player)) {
            Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
            InventoryOperation.giveItemStack(player, itemStack);
            return true;
        }
        return false;
    }

    public static void manaDamageExEffect(Player player, Mob mob, double damage) {
        CastleSceptre.exDamage(player, mob, damage);
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
        HudUtil.sendActionBarMSG(player, component);
    }

    @Nullable
    public static ServerPlayer getPlayerByName(String name) {
        return EntityQueryUtil.getPlayerByName(name);
    }

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle,
                                                 int fadeIn, int stay, int fadeOut) {
        TitleUtil.setPlayerTitleAndSubTitle(serverPlayer, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle) {
        TitleUtil.setPlayerTitleAndSubTitle(serverPlayer, title, subTitle);
    }

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle) {
        TitleUtil.setPlayerTitleAndSubTitle(player, title, subTitle);
    }

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle,
                                                 int fadeIn, int stay, int fadeOut) {
        TitleUtil.setPlayerTitleAndSubTitle(player, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setPlayerShortTitleAndSubTitle(Player player, Component title, Component subTitle) {
        TitleUtil.setPlayerShortTitleAndSubTitle(player, title, subTitle);
    }

    public static List<? extends Entity> getNearEntity(Entity center, Class<? extends Entity> type, double distance) {
        return EntityQueryUtil.getNearEntity(center, type, distance);
    }

    public static List<? extends Entity> getNearEntity(Level level, Vec3 center, Class<? extends Entity> type, double distance) {
        return EntityQueryUtil.getNearEntity(level, center, type, distance);
    }

    public static List<Mob> getNearMob(Entity center, double distance) {
        return EntityQueryUtil.getNearMob(center, distance);
    }

    public static List<Mob> getNearMob(Level level, Vec3 pos, double distance) {
        return EntityQueryUtil.getNearMob(level, pos, distance);
    }

    public static Set<Player> getNearPlayer(Level level, Vec3 center, double radius) {
        return EntityQueryUtil.getNearPlayer(level, center, radius);
    }

    public static Set<Player> getNearPlayer(Entity entity, double radius) {
        return EntityQueryUtil.getNearPlayer(entity, radius);
    }

    public static Player getNearestPlayer(LivingEntity livingEntity, double radius) {
        return EntityQueryUtil.getNearestPlayer(livingEntity, radius);
    }

    public static Mob getNearestMob(Player player, double radius) {
        return EntityQueryUtil.getNearestMob(player, radius);
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

    public static void clearPlayerScreen(Player player) {
        TitleUtil.clearPlayerScreen(player);
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

    public static Set<Mob> getPlayerVisionConicalMobs(Player player, int maxDistance) {
        return EntityQueryUtil.getPlayerVisionConicalMobs(player, maxDistance);
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
        MessageUtil.sendErrorTips(player, content);
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

    public static final String CHALLENGE_RECORD_KEY = PlayerDataUtil.CHALLENGE_RECORD_KEY;

    public static CompoundTag getChallengeRecordData(Player player) {
        return PlayerDataUtil.getChallengeRecordData(player);
    }

    public static Vec3 getPickLocationIgnoreBlock(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 shortPickPos = player.pick(0.5, 0, false).getLocation();
        return eyePos.add(shortPickPos.subtract(eyePos).normalize().scale(distance));
    }

    public static void renderTextInLevel(Level level, Vec3 pos, Component name) {
        // [WorldText 渲染迁移] 注册到客户端渲染管线，后续替换盔甲架方案
        if (level instanceof ServerLevel serverLevel) {
            WorldTextDataManager.addEntry(serverLevel, pos.add(0, 1.5, 0), name);
        }
    }

    public static void removeNearArmorStand(Level level, Vec3 pos, double radius) {
        level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(pos, radius * 2, radius * 2, radius * 2))
                .forEach(armorStand -> {
                    armorStand.remove(Entity.RemovalReason.KILLED);
                });

        // [WorldText 渲染迁移] 同步从客户端渲染管线中移除
        if (level instanceof ServerLevel serverLevel) {
            WorldTextDataManager.removeEntriesInRadius(serverLevel, pos, radius);
        }
    }

    public static final String TEMP_TAG_KEY = PlayerDataUtil.TEMP_TAG_KEY;
    public static CompoundTag getTempTag(Player player) {
        return PlayerDataUtil.getTempTag(player);
    }

    public static boolean isOverworldNight() {
        return Tick.server.overworld().isNight();
    }

    public static void sendCommandOpMSG(Player player, String content) {
        MessageUtil.sendCommandOpMSG(player, content);
    }

    public static @Nullable Mob getDefaultTarget(Player player, double maxDistance) {
        return EntityQueryUtil.getDefaultTarget(player, maxDistance);
    }

    public static @Nullable Mob getDefaultTarget(Player player) {
        return EntityQueryUtil.getDefaultTarget(player);
    }

    public static void addImprisonEffectToMob(Mob mob, int lastTick) {
        if (MobSpawn.canAddSlowDownOrImprison(mob)) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, lastTick, 99, false, false, false));
            BuffSystem.sendMobEffectHudToNearPlayer(mob, "hud/imprison", "imprison", lastTick, 0, false);
        }
    }

    public static boolean isWraqMob(Mob mob) {
        return EntityQueryUtil.isWraqMob(mob) && !mob.isDeadOrDying();
    }

    public static Set<EntityType<?>> getBornInChaosMobType() {
        return EntityQueryUtil.getBornInChaosMobType();
    }

    public static void sendInfoToScreen(Player player, Component info) {
        MessageUtil.sendInfoToScreen(player, info);
    }

    public static int getPlayerDailyKillCount(Player player) {
        return PlayerDataUtil.getPlayerDailyKillCount(player);
    }

    public static void incrementPlayerDailyKillCount(Player player) {
        PlayerDataUtil.incrementPlayerDailyKillCount(player);
    }

    public static void setPlayerDailyKillCount(Player player, int value) {
        PlayerDataUtil.setPlayerDailyKillCount(player, value);
    }

    public static List<ServerPlayer> getPlayers() {
        return EntityQueryUtil.getPlayers();
    }

    public static void onPlayerMainAttack(Player player, Mob mob) {
        EntityQueryUtil.onPlayerMainAttack(player, mob);
    }

    public static @Nullable Mob getPlayerMainAttackTarget(Player player) {
        return EntityQueryUtil.getPlayerMainAttackTarget(player);
    }
}
