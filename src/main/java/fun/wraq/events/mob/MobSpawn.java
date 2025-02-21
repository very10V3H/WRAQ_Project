package fun.wraq.events.mob;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.chapter1.ForestZombieSpawnController;
import fun.wraq.events.mob.chapter1.LakeDrownSpawnController;
import fun.wraq.events.mob.chapter1.MineSkeletonSpawnController;
import fun.wraq.events.mob.chapter1.PlainZombieSpawnController;
import fun.wraq.events.mob.chapter2.*;
import fun.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import fun.wraq.events.mob.chapter3_nether.NetherSkeletonSpawnController;
import fun.wraq.events.mob.chapter3_nether.PiglinSpawnController;
import fun.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import fun.wraq.events.mob.chapter4_end.EnderManSpawnController;
import fun.wraq.events.mob.chapter4_end.EndermiteSpawnController;
import fun.wraq.events.mob.chapter4_end.ShulkerSpawnController;
import fun.wraq.events.mob.chapter5.*;
import fun.wraq.events.mob.chapter6_castle.BeaconSpawnController;
import fun.wraq.events.mob.chapter6_castle.BlazeSpawnController;
import fun.wraq.events.mob.chapter6_castle.TreeSpawnController;
import fun.wraq.events.mob.chapter7.BoneImpSpawnController;
import fun.wraq.events.mob.chapter7.MushroomLinSpawnController;
import fun.wraq.events.mob.chapter7.StarSpawnController;
import fun.wraq.events.mob.chapter7.TorturedSoulSpawnController;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.moontain.*;
import fun.wraq.events.mob.ore.Ore2SpawnController;
import fun.wraq.events.mob.ore.Ore3SpawnController;
import fun.wraq.events.server.LoginInEvent;
import fun.wraq.files.dataBases.DataBase;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.pet.allay.skill.AllaySkills;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.Recall;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.spring2024.FireworkGun;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.divine.mob.DivineSentrySpawnController;
import fun.wraq.series.overworld.sun.network.TotalKillCountS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import org.apache.commons.lang3.RandomUtils;

import java.sql.*;
import java.util.*;

public class MobSpawn {

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = Tick.get();

            if (event.level.dimension().equals(Level.OVERWORLD)) {
                if (overWolrdList.isEmpty()) setOverWorldList(event.level);
                overWolrdList.forEach(mobSpawnController -> {
                    if (tick % 160 == overWolrdList.indexOf(mobSpawnController)) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }

            if (event.level.dimension().equals(Level.NETHER)) {
                if (netherList.isEmpty()) setNetherList(event.level);
                netherList.forEach(mobSpawnController -> {
                    if (tick % 160 == netherList.indexOf(mobSpawnController) + 50) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }

            if (event.level.dimension().equals(Level.END)) {
                if (endList.isEmpty()) setEndList(event.level);
                endList.forEach(mobSpawnController -> {
                    if (tick % 160 == endList.indexOf(mobSpawnController) + 25) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }
        }
    }

    public static List<MobSpawnController> overWolrdList = new ArrayList<>();

    public static void setOverWorldList(Level overWorld) {
        overWolrdList.add(PlainZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(ForestZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(LakeDrownSpawnController.getInstance(overWorld));
        overWolrdList.add(MineSkeletonSpawnController.getInstance(overWorld));
        overWolrdList.add(SkyVexSpawnController.getInstance(overWorld));
        overWolrdList.add(SkySkeletonSpawnController.getInstance(overWorld));
        overWolrdList.add(FireLightSpawnController.getInstance(overWorld));
        overWolrdList.add(SearedSpiritSpawnController.getInstance(overWorld));
        overWolrdList.add(SearedSpirit2SpawnController.getInstance(overWorld));
        overWolrdList.add(EvokerSpawnController.getInstance(overWorld));
        overWolrdList.add(HuskSpawnController.getInstance(overWorld));
        overWolrdList.add(HuskEx0SpawnController.getInstance(overWorld));
        overWolrdList.add(HuskEx1SpawnController.getInstance(overWorld));
        overWolrdList.add(HuskEx2SpawnController.getInstance(overWorld));
        overWolrdList.add(LumberJackSpawnController.getInstance(overWorld));
        overWolrdList.add(DreadHoundSpawnController.getInstance(overWorld));
        overWolrdList.add(GuardianSpawnController.getInstance(overWorld));
        overWolrdList.add(SpiderSpawnController.getInstance(overWorld));
        overWolrdList.add(LightningZombieController.getInstance(overWorld));
        overWolrdList.add(WindSkeletonSpawnController.getInstance(overWorld));
        overWolrdList.add(SlimeSpawnController.getInstance(overWorld));
        overWolrdList.add(JorogumoSpawnController.getInstance(overWorld));
        overWolrdList.add(SnowStraySpawnController.getInstance(overWorld));
        overWolrdList.add(SakuraMobSpawnController.getInstance(overWorld));
        overWolrdList.add(SakuraMob2SpawnController.getInstance(overWorld));
        overWolrdList.add(EarthManaSpawnController.getInstance(overWorld));
        overWolrdList.add(BloodManaSpawnController.getInstance(overWorld));
        overWolrdList.add(PillagerSpawnController.getInstance(overWorld));
        overWolrdList.add(BeaconSpawnController.getInstance(overWorld));
        overWolrdList.add(BlazeSpawnController.getInstance(overWorld));
        overWolrdList.add(TreeSpawnController.getInstance(overWorld));
        overWolrdList.add(StarSpawnController.getInstance(overWorld));
        overWolrdList.add(BoneImpSpawnController.getInstance(overWorld));
        overWolrdList.add(TorturedSoulSpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon1SpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon2SpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon3SpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon1ExSpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon2ExSpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainCommon3ExSpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainChickenSpawnController.getInstance(overWorld));
        overWolrdList.add(MoontainMinerSpawnController.getInstance(overWorld));
        overWolrdList.add(Ore2SpawnController.getInstance(overWorld));
        overWolrdList.add(Ore3SpawnController.getInstance(overWorld));
        overWolrdList.add(MushroomLinSpawnController.getInstance(overWorld));
        overWolrdList.add(DivineSentrySpawnController.getInstance(overWorld));
    }

    public static List<MobSpawnController> netherList = new ArrayList<>();

    public static void setNetherList(Level nether) {
        netherList.add(WitherSkeletonSpawnController.getInstance(nether));
        netherList.add(NetherSkeletonSpawnController.getInstance(nether));
        netherList.add(MagmaSpawnController.getInstance(nether));
        netherList.add(PiglinSpawnController.getInstance(nether));
    }

    public static List<MobSpawnController> endList = new ArrayList<>();

    public static void setEndList(Level end) {
        endList.add(EnderManSpawnController.getInstance(end));
        endList.add(EndermiteSpawnController.getInstance(end));
        endList.add(ShulkerSpawnController.getInstance(end));
    }

    public static List<MobSpawnController> getAllControllers(boolean isServer) {
        if (overWolrdList.isEmpty()) {
            setOverWorldList(isServer ? Tick.server.getLevel(Level.OVERWORLD) : ClientUtils.clientLevel);
        }
        if (netherList.isEmpty()) {
            setNetherList(isServer ? Tick.server.getLevel(Level.NETHER) : ClientUtils.clientLevel);
        }
        if (endList.isEmpty()) {
            setEndList(isServer ? Tick.server.getLevel(Level.END) : ClientUtils.clientLevel);
        }
        List<MobSpawnController> controllers = new ArrayList<>();
        controllers.addAll(overWolrdList);
        controllers.addAll(netherList);
        controllers.addAll(endList);
        return controllers;
    }

    public static Map<String, String> mobNameChineseToDataKeyMap = new HashMap<>();
    public static Map<String, String> getMobNameChineseToDataKeyMap() {
        if (mobNameChineseToDataKeyMap.isEmpty()) {
            getAllControllers(true).forEach(mobSpawnController -> {
                mobNameChineseToDataKeyMap.put(mobSpawnController.mobName.getString(),
                        mobSpawnController.getKillCountDataKey());
            });
            NoTeamInstanceModule.getAllInstance().forEach(instance -> {
                mobNameChineseToDataKeyMap.put(instance.name.getString(), instance.getKillCountDataKey());
            });
            NewTeamInstanceHandler.getInstances().forEach(instance -> {
                mobNameChineseToDataKeyMap.put(instance.description.getString(), instance.getKillCountDataKey());
            });
        }
        return mobNameChineseToDataKeyMap;
    }

    public static Map<String, Component> mobNameMap = new HashMap<>();
    public static Map<String, Component> getMobNameMap() {
        if (mobNameMap.isEmpty()) {
            getAllControllers(false).forEach(mobSpawnController -> {
                mobNameMap.put(mobSpawnController.mobName.getString(), mobSpawnController.mobName);
            });
        }
        return mobNameMap;
    }

    public static void removeAllMob() {
        overWolrdList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
        netherList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
        endList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
    }

    public static void setMobDropList(Mob mob, List<ItemAndRate> list) {
        dropList.put(getMobOriginName(mob), list);
    }

    public static Map<String, List<ItemAndRate>> dropList = new HashMap<>();
    public static Map<String, Boolean> dropsDirectToInventory = new HashMap<>();

    public static void setStainArmorOnMob(Mob mob, Style style) {
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            mob.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
    }

    public static class MobBaseAttributes {
        public static Map<String, Integer> xpLevel = new HashMap<>();
        public static Map<String, Double> attackDamage = new HashMap<>();
        public static Map<String, Double> defence = new HashMap<>();
        public static Map<String, Double> manaDefence = new HashMap<>();
        public static Map<String, Double> critRate = new HashMap<>();
        public static Map<String, Double> critDamage = new HashMap<>();
        public static Map<String, Double> defencePenetration = new HashMap<>();
        public static Map<String, Double> defencePenetration0 = new HashMap<>();
        public static Map<String, Double> healthSteal = new HashMap<>();
        public static Map<String, Double> movementSpeed = new HashMap<>();

        public static Map<String, Map<String, Double>> fromCSVAttributes = new HashMap<>();

        public static double getMobBaseAttribute(Mob mob, Map<String, Double> map) {
            return map.getOrDefault(getMobOriginName(mob), 0d);
        }

        public static void setMobBaseAttributes(Mob mob, double attackDamage, double defence, double manaDefence,
                                                double critRate, double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth, double movementSpeed) {
            String mobOriginName = getMobOriginName(mob);

            if (fromCSVAttributes.isEmpty()) {
                MobBaseAttributes.attackDamage.put(mobOriginName, attackDamage * 2);
                MobBaseAttributes.defence.put(mobOriginName, defence);
                MobBaseAttributes.manaDefence.put(mobOriginName, manaDefence);
                MobBaseAttributes.critRate.put(mobOriginName, critRate);
                MobBaseAttributes.critDamage.put(mobOriginName, critDamage);
                MobBaseAttributes.defencePenetration.put(mobOriginName, defencePenetration);
                MobBaseAttributes.defencePenetration0.put(mobOriginName, defencePenetration0);
                MobBaseAttributes.healthSteal.put(mobOriginName, healthSteal * 0.2);
                mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
                mob.setHealth(mob.getMaxHealth());
                MobBaseAttributes.movementSpeed.put(mobOriginName, movementSpeed);
                mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
                mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
            } else {
                if (fromCSVAttributes.containsKey(mobOriginName)) {
                    Map<String, Double> attributeMap = fromCSVAttributes.get(mobOriginName);
                    MobBaseAttributes.attackDamage.put(mobOriginName, attributeMap.get("attack"));
                    MobBaseAttributes.defence.put(mobOriginName, attributeMap.get("defence"));
                    MobBaseAttributes.manaDefence.put(mobOriginName, attributeMap.get("manaDefence"));
                    MobBaseAttributes.critRate.put(mobOriginName, attributeMap.get("critRate"));
                    MobBaseAttributes.critDamage.put(mobOriginName, attributeMap.get("critDamage"));
                    MobBaseAttributes.defencePenetration.put(mobOriginName, attributeMap.get("defencePenetration"));
                    MobBaseAttributes.defencePenetration0.put(mobOriginName, attributeMap.get("defencePenetration0"));
                    MobBaseAttributes.healthSteal.put(mobOriginName, attributeMap.get("healthSteal"));
                    mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(attributeMap.get("health"));
                    mob.setHealth(mob.getMaxHealth());
                    MobBaseAttributes.movementSpeed.put(mobOriginName, attributeMap.get("movementSpeed"));
                    mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(attributeMap.get("movementSpeed"));
                    mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
                }
            }
        }

        public static void setMobBaseAttributes(Mob mob, int xpLevel, double attackDamage, double defence, double manaDefence,
                                                double critRate, double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth, double movementSpeed) {
            MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
            setMobBaseAttributes(mob, attackDamage, defence, manaDefence, critRate, critDamage, defencePenetration,
                    defencePenetration0, healthSteal, maxHealth, movementSpeed);
        }

        public static void setMobBaseAttributes(Mob mob, double attackDamage, double defence, double manaDefence,
                                                double maxHealth, double movementSpeed) {
            setMobBaseAttributes(mob, attackDamage, defence, manaDefence, 0, 0, 0, 0, 0, maxHealth, movementSpeed);
        }

        public static void setMobBaseAttributes(Mob mob, Component mobName, int xpLevel, double attackDamage,
                                                double defence, double manaDefence, double critRate,
                                                double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth,
                                                double movementSpeed) {
            MobSpawn.setMobCustomName(mob, mobName, xpLevel);
            setMobBaseAttributes(mob, xpLevel, attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed);
        }
    }

    public static double getNum(Player player) {
        double num = 1;
        num += Compute.playerExHarvest(player);
        return num;
    }

    private static int getMobXpLevel(Mob mob) {
        String name = mob.getName().getString();
        int start = name.indexOf('.');
        int end = name.indexOf(' ');
        if (end <= start + 1) {
            return MobBaseAttributes.xpLevel.getOrDefault(getMobOriginName(mob), 0);
        }
        String xpLevelString = name.substring(start + 1, end);
        if (org.apache.commons.lang3.StringUtils.isNumeric(xpLevelString)) {
            return Integer.parseInt(xpLevelString);
        }
        return 0;
    }

    public static void drop(Mob mob, Player player) {
        int xpLevel = getMobXpLevel(mob);

        if (RandomUtils.nextDouble(0, 1) < 0.01) {
            InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.MONEY.get());
            FireworkGun.summonFireWork(mob.level(), mob.getEyePosition());
        }
        if (RandomUtils.nextInt(0, 10000) < 100) {
            InventoryOperation.giveItemStack(player, ModItems.REFINED_PIECE.get().getDefaultInstance());
        }
        if (RandomUtils.nextDouble(0, 1) < AllaySkills.getExGemPieceRate((ServerPlayer) player)) {
            InventoryOperation.giveItemStack(player, ModItems.GEM_PIECE.get());
            AllayPet.sendMSG(player,
                    Te.s(AllayPetPlayerData.getAllayName(player), "给你带来了一个", ModItems.GEM_PIECE));
        }

        recall(mob, player);
        if (!MobSpawn.dropList.containsKey(MobSpawn.getMobOriginName(mob))) return;
        List<ItemAndRate> list = MobSpawn.dropList.get(MobSpawn.getMobOriginName(mob));

        if (MobSpawn.getMobOriginName(mob).equals(PlainZombieSpawnController.mobName)) {
            Guide.trigV2(player, Guide.StageV2.FIRST_KILL);
        }

        double num = getNum(player);

        // 直接送至背包或掉落
        if (dropsDirectToInventory.containsKey(MobSpawn.getMobOriginName(mob))
                || Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(NewRuneItems.endNewRune.get())) {
            Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), xpLevel);
            list.forEach(itemAndRate -> {
                itemAndRate.send(player, num);
            });
        } else {
            double rate = MobSpawn.getMobOriginName(mob).equals(MagmaSpawnController.mobName)
                    || MobSpawn.getMobOriginName(mob).equals(SlimeSpawnController.mobName) ? 0.003 : 0.01;
            ItemAndRate.dropOrbs(xpLevel, rate, mob.level(), mob.position(), fromMobSpawnTag);
            list.forEach(itemAndRate -> {
                itemAndRate.dropWithoutBounding(mob, num, player);
            });
        }

        computeKillCount(player);

        incrementPlayerKillCount(player, MobSpawn.getMobOriginName(mob));
        MissionV2Helper.onKillMob(player, mob);

        oldVersionMaterial(mob, player);
        Random rand = new Random();
        if (rand.nextDouble() < 0.1 * num) {
            if (Compute.hasCurios(player, NewRuneItems.endNewRune.get())) {
                InventoryOperation.giveItemStack(player, new ItemStack(ModItems.WORLD_SOUL_1.get()));
            } else {
                ItemAndRate.summonBoundingItemEntity(mob, new ItemStack(ModItems.WORLD_SOUL_1.get()), player);
            }
        }
    }

    public static Map<String, Map<String, Integer>> totalKillCount = new HashMap<>();
    public static Map<String, Integer> totalKillCountCache = new HashMap<>();

    public static String KILL_COUNT_DATA_KEY = "KillCountDataV2";
    public static CompoundTag getKillCountData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, KILL_COUNT_DATA_KEY);
    }

    public static String SYNC_FLAG_KEY = "SyncFlag";
    public static void onPlayerLoginSync(Player player) {
        CompoundTag data = getKillCountData(player);
        if (!data.contains(SYNC_FLAG_KEY)) {
            data.putBoolean(SYNC_FLAG_KEY, true);
            player.getPersistentData().remove("KillCountData");
            if (totalKillCount.containsKey(Name.get(player))) {
                Map<String, Integer> map = totalKillCount.get(Name.get(player));
                Map<String, String> cnToKeyMap = getMobNameChineseToDataKeyMap();
                map.forEach((k, v) -> {
                    if (cnToKeyMap.containsKey(k)) {
                        data.putInt(cnToKeyMap.get(k), v);
                    }
                });
                Compute.sendFormatMSG(player, Te.s("安全", CustomStyle.styleOfFlexible),
                        Te.s("击杀数已同步至新版数据存储"));
            }
        }
    }

    public static int getPlayerKillCount(Player player, String mobName) {
        Map<String, String> cnToKeyMap = getMobNameChineseToDataKeyMap();
        if (cnToKeyMap.containsKey(mobName)) {
            return getKillCountData(player).getInt(cnToKeyMap.get(mobName));
        }
        return 0;
    }

    public static void incrementPlayerKillCount(Player player, String mobName) {
        Map<String, String> cnToKeyMap = getMobNameChineseToDataKeyMap();
        if (cnToKeyMap.containsKey(mobName)) {
            CompoundTag data = getKillCountData(player);
            String dataKey = cnToKeyMap.get(mobName);
            data.putInt(dataKey, data.getInt(dataKey) + 1);
        }
    }

    public static int getTotalKillCount(Player player) {
        int totalCount = 0;
        for (MobSpawnController controller : getAllControllers(true)) {
            totalCount += getPlayerKillCount(player, controller.mobName.getString());
        }
        for (NoTeamInstance noTeamInstance : NoTeamInstanceModule.getAllInstance()) {
            totalCount += getPlayerKillCount(player, noTeamInstance.name.getString());
        }
        for (NewTeamInstance instance : NewTeamInstanceHandler.getInstances()) {
            totalCount += getPlayerKillCount(player, instance.description.getString());
        }
        return totalCount;
    }

    public static void handlePlayerTick(Player player) {
        if (player.tickCount % 100 == 9) {
            int totalCount = getTotalKillCount(player);
            totalKillCountCache.put(Name.get(player), totalCount);
            ModNetworking.sendToClient(new TotalKillCountS2CPacket(totalCount), player);
        }
    }

    public static String fromMobSpawnTag = "fromMobSpawn";
    public static String fromSlime = "fromSlime";

    public static String getMobOriginName(Mob mob) {
        String s = mob.getName().getString();
        return s.substring(s.indexOf(" ") + 1);
    }

    public static void recall(Mob mob, Player player) {
        if (getMobOriginName(mob).equals(HuskSpawnController.mobName)) killCountAdd(Utils.huskRecall, player, mob);
        if (getMobOriginName(mob).equals(ForestZombieSpawnController.mobName))
            killCountAdd(Utils.forestRecall, player, mob);
        if (getMobOriginName(mob).equals(WindSkeletonSpawnController.mobName))
            killCountAdd(Utils.kazeRecall, player, mob);
        if (getMobOriginName(mob).equals(SpiderSpawnController.mobName)) killCountAdd(Utils.spiderRecall, player, mob);
        if (getMobOriginName(mob).equals(GuardianSpawnController.mobName)) killCountAdd(Utils.seaRecall, player, mob);
        if (getMobOriginName(mob).equals(LightningZombieController.mobName))
            killCountAdd(Utils.lightningRecall, player, mob);
        if (getMobOriginName(mob).equals(WitherSkeletonSpawnController.mobName))
            killCountAdd(Utils.netherRecall, player, mob);
        if (getMobOriginName(mob).equals(SearedSpiritSpawnController.mobName))
            killCountAdd(Utils.volcanoRecall, player, mob);

        // 欠缺冰川
        Recall[] recalls = {
                Utils.kazeRecall, Utils.spiderRecall, Utils.huskRecall, Utils.seaRecall,
                Utils.lightningRecall, Utils.netherRecall, Utils.snowRecall, Utils.volcanoRecall
        };
        Item[] items = {
                ModItems.ArmorKazeRecall.get(), ModItems.ArmorSpiderRecall.get(),
                ModItems.ArmorHuskRecall.get(), ModItems.ArmorSeaRecall.get(),
                ModItems.ArmorLightningRecall.get(), ModItems.ArmorNetherRecall.get(),
                ModItems.ArmorSnowRecall.get(), ModItems.ArmorVolcanoRecall.get()
        };
        for (int i = 0; i < recalls.length; i++) {
            recallSuccess(recalls[i], player, mob, items[i]);
        }
        if (Utils.forestRecall.recallPlayer != null && mob.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(ModItems.ArmorForestRecall.get())
                && Utils.forestRecall.recallPlayer.equals(player)) {
            Utils.ForestRecallBossKillCount++;
            if (Utils.ForestRecallBossKillCount == 2) Utils.forestRecall.recallSuccess = true;
        }
    }

    public static void killCountAdd(Recall recall, Player player, Mob mob) {
        if (recall.recallPlayer != null && recall.recallPlayer.equals(player) && recall.killCount != -1) {
            recall.killCount--;
            Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                    Component.literal(getMobOriginName(mob) + "击杀数:" + (20 - recall.killCount) + "/"
                            + "20").withStyle(CustomStyle.styleOfEnd));
        }
    }

    public static void recallSuccess(Recall recall, Player player, Mob mob, Item mobHelmet) {
        if (recall.recallPlayer != null && mob.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(mobHelmet) && recall.recallPlayer.equals(player)) {
            recall.recallSuccess = true;
        }
    }

    public static void oldVersionMaterial(Mob mob, Player player) {
        Element.Unit unit = Element.entityElementUnit.getOrDefault(mob, new Element.Unit(Element.life, 0));
        if (unit.value() > 0) {
            String[] elementType = {Element.life, Element.water, Element.fire};
            Item[] items = {ModItems.SunPower.get(), ModItems.LAKE_CORE.get(), ModItems.VOLCANO_CORE.get()};
            List<Map<String, Integer>> maps = new ArrayList<>() {{
                add(LoginInEvent.sunPowerGetCount);
                add(LoginInEvent.lakeCoreGetCount);
                add(LoginInEvent.volcanoCoreGetCount);
            }};
            for (int i = 0; i < elementType.length; i++) {
                String type = elementType[i];
                Item item = items[i];
                Random random = new Random();
                if (unit.type().equals(type)) {
                    if (random.nextDouble() < 0.03) {
                        Map<String, Integer> getMap = maps.get(i);
                        String name = player.getName().getString();
                        if (!getMap.containsKey(name) || getMap.get(name) <= 36) {
                            getMap.put(name, getMap.getOrDefault(name, 0) + 1);
                            if (Compute.hasCurios(player, NewRuneItems.endNewRune.get())) {
                                InventoryOperation.giveItemStack(player, item.getDefaultInstance());
                            } else ItemAndRate.summonBoundingItemEntity(mob, item.getDefaultInstance(), player);
                        }
                    }
                }
            }
        }
    }

    public static void readKillCount() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        String sql = "select * from killcount";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            String playerName = resultSet.getString("name");
            if (!totalKillCount.containsKey(playerName)) totalKillCount.put(playerName, new HashMap<>());
            Map<String, Integer> killCountMap = totalKillCount.get(playerName);
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String mobName = resultSetMetaData.getColumnName(i);
                if (!mobName.equals("name")) {
                    String counts = resultSet.getString(mobName);
                    int count = 0;
                    if (counts != null) count = Integer.parseInt(counts);
                    killCountMap.put(mobName, count);
                }
            }
        }
        statement.close();
    }

    public static void computeKillCount(Player player) {
        InventoryOperation.getAllEquipSlotItems(player).forEach(equip -> {
            incrementKillCount(equip, 1);
        });
    }

    public static int getKillCount(ItemStack equip) {
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            equip.getOrCreateTagElement(Utils.MOD_ID).getInt(StringUtils.KillCount.NEW_KILL_COUNT);
        }
        return 0;
    }

    private static void incrementKillCount(ItemStack equip, int num) {
        CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
        data.putInt(StringUtils.KillCount.NEW_KILL_COUNT,
                data.getInt(StringUtils.KillCount.NEW_KILL_COUNT) + num);
    }

    public static void setMobCustomName(Mob mob, Item ArmorItem, Component mobName) {
        int xpLevel = Utils.mobLevel.get(ArmorItem).intValue();
        setMobCustomName(mob, mobName, xpLevel);
    }

    public static void setMobCustomName(Mob mob, Component mobName, int level) {
        mob.setCustomName(Te.s("Lv." + level + " ", getMobLevelStyle(level), mobName));
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0);
        mob.getAttribute(Attributes.ARMOR).setBaseValue(0);
    }

    public static Style getMobLevelStyle(int level) {
        return Utils.getLevelStyle(level);
    }
}
