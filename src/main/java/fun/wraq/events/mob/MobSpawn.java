package fun.wraq.events.mob;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.chapter1.AreaForestMobSpawnController;
import fun.wraq.events.mob.chapter1.ForestZombieSpawnController;
import fun.wraq.events.mob.chapter1.GraySlimeSpawnController;
import fun.wraq.events.mob.chapter1.PlainZombieSpawnController;
import fun.wraq.events.mob.chapter2.*;
import fun.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.server.LoginInEvent;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.data.PersistentDataUtil;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.forge.EquipPiece;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.pet.allay.skill.AllaySkills;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.Recall;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.dragonboat.DragonBoatFes;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import fun.wraq.series.events.qingMing.QingTuan;
import fun.wraq.series.overworld.chapter1.plain.PlainCrest;
import fun.wraq.series.overworld.cold.sc3.aurora.AuroraSheepSpawnController;
import fun.wraq.series.overworld.sun.network.TotalKillCountS2CPacket;
import fun.wraq.series.overworld.wind.mob.WindSkeletonSpawnController;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class MobSpawn {

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = Tick.get();
            if (event.level.dimension().equals(Level.OVERWORLD)) {
                if (overWolrdList.isEmpty()) {
                    setOverWorldList(event.level);
                }
                overWolrdList.forEach(mobSpawnController -> {
                    int interval = mobSpawnController.getSpawnInterval();
                    if (tick % interval == (overWolrdList.indexOf(mobSpawnController) % interval)) {
                        mobSpawnController.spawnFlag = true;
                    }
                    mobSpawnController.handleTick();
                });
                AreaMobSpawnController.tickAll(event.level, event.level.dimension());
                mountsMap.forEach((k, v) -> {
                    if (v.isRemoved()) {
                        if (k instanceof Animal) {
                            k.setHealth(0);
                        } else if (k instanceof Mob) {
                            k.kill();
                        } else {
                            k.remove(Entity.RemovalReason.KILLED);
                        }
                    }
                });
                mountsMap.entrySet()
                        .removeIf(entry -> entry.getKey().isDeadOrDying() && entry.getValue().isDeadOrDying());
            }
            if (event.level.dimension().equals(Level.NETHER)) {
                if (netherList.isEmpty()) setNetherList(event.level);
                netherList.forEach(mobSpawnController -> {
                    int interval = mobSpawnController.getSpawnInterval();
                    if (tick % interval == ((netherList.indexOf(mobSpawnController) + 50) % interval)) {
                        mobSpawnController.spawnFlag = true;
                    }
                    mobSpawnController.handleTick();
                });
                AreaMobSpawnController.tickAll(event.level, event.level.dimension());
            }
            if (event.level.dimension().equals(Level.END)) {
                if (endList.isEmpty()) setEndList(event.level);
                endList.forEach(mobSpawnController -> {
                    int interval = mobSpawnController.getSpawnInterval();
                    if (tick % interval == ((endList.indexOf(mobSpawnController) + 25) % interval)) {
                        mobSpawnController.spawnFlag = true;
                    }
                    mobSpawnController.handleTick();
                });
                AreaMobSpawnController.tickAll(event.level, event.level.dimension());
            }
        }
    }

    public static List<MobSpawnController> overWolrdList = new ArrayList<>();

    public static void setOverWorldList(Level overWorld) {
        overWolrdList.add(PlainZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(ForestZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(GraySlimeSpawnController.getInstance(overWorld));
        AreaForestMobSpawnController.getInstance(overWorld);
/*        overWolrdList.add(PlainZombieSpawnController.getInstance(overWorld));
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
        overWolrdList.add(SnowStraySpawnController.getInstance(overWorld));
        overWolrdList.add(SakuraMobSpawnController.getInstance(overWorld));
        overWolrdList.add(SakuraMob2SpawnController.getInstance(overWorld));
        overWolrdList.add(EarthManaSpawnController.getInstance(overWorld));
        overWolrdList.add(BloodManaSpawnController.getInstance(overWorld));
        overWolrdList.add(PillagerSpawnController.getInstance(overWorld));
        overWolrdList.add(Pillager2SpawnController.getInstance(overWorld));
        overWolrdList.add(BeaconSpawnController.getInstance(overWorld));
        overWolrdList.add(BlazeSpawnController.getInstance(overWorld));
        overWolrdList.add(TreeSpawnController.getInstance(overWorld));
        overWolrdList.add(StarVexSpawnController.getInstance(overWorld));
        overWolrdList.add(StarRabbitSpawnController.getInstance(overWorld));
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
        overWolrdList.add(DivineGolemSpawnController.getInstance(overWorld));
        overWolrdList.add(GhastlyCreeperSpawnController.getInstance(overWorld));
        overWolrdList.add(GhastlyHuskSpawnController.getInstance(overWorld));
        overWolrdList.add(BunkerBowMobSpawnController.getInstance(overWorld));
        overWolrdList.add(BunkerAttackMobSpawnController.getInstance(overWorld));
        overWolrdList.add(BunkerBlazeSpawnController.getInstance(overWorld));
        overWolrdList.add(StoneSpiderSpawnController.getInstance(overWorld));
        overWolrdList.add(SuperColdStraySpawnController.getInstance(overWorld));
        overWolrdList.add(AuroraSheepSpawnController.getInstance(overWorld));
        overWolrdList.add(FirElfSpawnController.getInstance(overWorld));
        overWolrdList.add(MapleHunterSpawnController.getInstance(overWorld));
        overWolrdList.add(SuperColdIronGolemSpawnController.getInstance(overWorld));
        overWolrdList.add(SuperColdSnowGolemSpawnController.getInstance(overWorld));*/
        overWolrdList.forEach(mobSpawnController -> {
            if (mobSpawnController.level != overWorld) {
                mobSpawnController.level = overWorld;
            }
        });
    }

    public static List<MobSpawnController> netherList = new ArrayList<>();

    public static void setNetherList(Level nether) {
/*        netherList.add(WitherSkeletonSpawnController.getInstance(nether));
        netherList.add(NetherSkeletonSpawnController.getInstance(nether));
        netherList.add(MagmaSpawnController.getInstance(nether));
        netherList.add(PiglinSpawnController.getInstance(nether));*/
        netherList.forEach(mobSpawnController -> {
            if (mobSpawnController.level != nether) {
                mobSpawnController.level = nether;
            }
        });
    }

    public static List<MobSpawnController> endList = new ArrayList<>();

    public static void setEndList(Level end) {
/*        endList.add(EnderManSpawnController.getInstance(end));
        endList.add(EndermiteSpawnController.getInstance(end));
        endList.add(ShulkerSpawnController.getInstance(end));*/
        endList.forEach(mobSpawnController -> {
            if (mobSpawnController.level != end) {
                mobSpawnController.level = end;
            }
        });
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
            AreaMobSpawnController.getAllControllers().forEach(controller -> {
                mobNameChineseToDataKeyMap.put(controller.mobName.getString(),
                        controller.getKillCountDataKey());
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

    public static void onServerStop() {
        LogUtils.getLogger().info("Remove all mobs.");
        overWolrdList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
        overWolrdList.clear();
        netherList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
        netherList.clear();
        endList.forEach(mobSpawnController -> {
            mobSpawnController.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            mobSpawnController.mobList.clear();
        });
        endList.clear();
        AreaMobSpawnController.onServerStop();
        LogUtils.getLogger().info("done.");
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
            itemStacks[i].enchant(Enchantments.UNBREAKING, 10);
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

        public static Map<String, Map<Integer, Double>> mobXpDropRateMap = new HashMap<>();

        public static Map<String, Map<String, Double>> fromCSVAttributes = new HashMap<>();

        public static double getMobBaseAttribute(Mob mob, Map<String, Double> map) {
            return map.getOrDefault(mob.getName().getString(), 0d);
        }

        public static void setMobBaseAttributes(Mob mob, MobAttributes attributes) {
            setMobBaseAttributes(mob, attributes, 1);
        }

        public static void setMobBaseAttributes(Mob mob, MobAttributes attributes, double rate) {
            String mobName = mob.getName().getString();
            MobBaseAttributes.attackDamage.put(mobName, attributes.attackDamage * rate);
            MobBaseAttributes.defence.put(mobName, attributes.defence * rate);
            MobBaseAttributes.manaDefence.put(mobName, attributes.manaDefence * rate);
            MobBaseAttributes.critRate.put(mobName, attributes.critRate * rate);
            MobBaseAttributes.critDamage.put(mobName, attributes.critDamage * rate);
            MobBaseAttributes.defencePenetration.put(mobName, attributes.defencePenetration * rate);
            MobBaseAttributes.defencePenetration0.put(mobName, attributes.defencePenetration0 * rate);
            MobBaseAttributes.healthSteal.put(mobName, attributes.healthSteal * rate);
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(attributes.maxHealth * rate);
            mob.setHealth(mob.getMaxHealth());
            MobBaseAttributes.movementSpeed.put(mobName, attributes.movementSpeed * rate);
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(attributes.movementSpeed * rate);
            mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(rate);
        }

        public static void setMobBaseAttributes(Mob mob, double attackDamage, double defence, double manaDefence,
                                                double critRate, double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth,
                                                double movementSpeed) {
            String mobName = getMobOriginName(mob);

            if (fromCSVAttributes.isEmpty()) {
                MobBaseAttributes.attackDamage.put(mobName, attackDamage);
                MobBaseAttributes.defence.put(mobName, defence);
                MobBaseAttributes.manaDefence.put(mobName, manaDefence);
                MobBaseAttributes.critRate.put(mobName, critRate);
                MobBaseAttributes.critDamage.put(mobName, critDamage);
                MobBaseAttributes.defencePenetration.put(mobName, defencePenetration);
                MobBaseAttributes.defencePenetration0.put(mobName, defencePenetration0);
                MobBaseAttributes.healthSteal.put(mobName, healthSteal);
                mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
                mob.setHealth(mob.getMaxHealth());
                MobBaseAttributes.movementSpeed.put(mobName, movementSpeed);
                mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
                mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
            } else {
                if (fromCSVAttributes.containsKey(mobName)) {
                    Map<String, Double> attributeMap = fromCSVAttributes.get(mobName);
                    MobBaseAttributes.attackDamage.put(mobName, attributeMap.get("attack"));
                    MobBaseAttributes.defence.put(mobName, attributeMap.get("defence"));
                    MobBaseAttributes.manaDefence.put(mobName, attributeMap.get("manaDefence"));
                    MobBaseAttributes.critRate.put(mobName, attributeMap.get("critRate"));
                    MobBaseAttributes.critDamage.put(mobName, attributeMap.get("critDamage"));
                    MobBaseAttributes.defencePenetration.put(mobName, attributeMap.get("defencePenetration"));
                    MobBaseAttributes.defencePenetration0.put(mobName, attributeMap.get("defencePenetration0"));
                    MobBaseAttributes.healthSteal.put(mobName, attributeMap.get("healthSteal"));
                    mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(attributeMap.get("health"));
                    mob.setHealth(mob.getMaxHealth());
                    MobBaseAttributes.movementSpeed.put(mobName, attributeMap.get("movementSpeed"));
                    mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(attributeMap.get("movementSpeed"));
                    mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
                }
            }
        }

        public static void setMobBaseAttributes(Mob mob, int xpLevel, double attackDamage, double defence, double manaDefence,
                                                double critRate, double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth, double movementSpeed) {
            MobBaseAttributes.xpLevel.put(mob.getName().getString(), xpLevel);
            setMobBaseAttributes(mob, attackDamage, defence, manaDefence, critRate, critDamage, defencePenetration,
                    defencePenetration0, healthSteal, maxHealth, movementSpeed);
        }

        public static void setMobBaseAttributes(Mob mob, int xpLevel, MobAttributes attributes) {
            MobBaseAttributes.xpLevel.put(mob.getName().getString(), xpLevel);
            setMobBaseAttributes(mob, attributes);
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

        public static void setMobBaseAttributes(Mob mob, Component mobName, int xpLevel, MobAttributes attributes) {
            MobSpawn.setMobCustomName(mob, mobName, xpLevel);
            setMobBaseAttributes(mob, xpLevel, attributes);
        }
    }

    public static double getNum(Player player) {
        double num = 1;
        num += PlayerAttributes.playerExHarvest(player);
        return num;
    }

    public static int getMobXpLevel(Mob mob) {
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
        String mobOriginName = MobSpawn.getMobOriginName(mob);
        String mobName = mob.getName().getString();
        if (RandomUtils.nextInt(0, 10000) < 100) {
            ItemAndRate.send(player, ModItems.REFINED_PIECE.get().getDefaultInstance());
        }
        if (RandomUtils.nextDouble(0, 1) < AllaySkills.getExGemPieceRate((ServerPlayer) player)) {
            ItemAndRate.send(player, ModItems.GEM_PIECE.get().getDefaultInstance());
            AllayPet.sendMSG(player,
                    Te.s(AllayPetPlayerData.getAllayName(player), "给你带来了一个", ModItems.GEM_PIECE));
        }
        if (QingTuan.isInActivityDate() && RandomUtils.nextInt(0, 10000) < 25) {
            ItemAndRate.send(player, SpecialEventItems.QING_TUAN.get().getDefaultInstance());
        }
        LabourDayOldCoin.onPlayerKillMob(player);
        DragonBoatFes.onKillMob(mob, player);
        recall(mob, player);
        EquipPiece.onKillMob(player, mob);
        if (!MobSpawn.dropList.containsKey(mobOriginName)) {
            return;
        }

        // 如果掉落物表里，含怪物的全名（带等级），那么以带等级的掉落表为准
        List<ItemAndRate> list = new ArrayList<>(MobSpawn.dropList.get(mobOriginName));
        if (MobSpawn.dropList.containsKey(mobName)) {
            list = new ArrayList<>(MobSpawn.dropList.get(mobName));
        }

        // 合并多个 ofExp 创建的经验给予项
        ItemAndRate.mergeExpEntries(list);

        AuroraSheepSpawnController.handleColorItemDrop(mob, list);
        // 引导系统：击杀计数
        if (mobOriginName.equals(PlainZombieSpawnController.mobName)) {
            Guide.incrementMobKill(player, "plainZombie");
            if (Guide.getMobKillCount(player, "plainZombie") >= 10) {
                Guide.trigV2(player, Guide.StageV2.KILL_10_PLAIN_ZOMBIE);
            }
        }
        if (mobOriginName.equals(ForestZombieSpawnController.mobName)) {
            Guide.incrementMobKill(player, "forestZombie");
            if (Guide.getMobKillCount(player, "forestZombie") >= 20) {
                Guide.trigV2(player, Guide.StageV2.KILL_20_FOREST_ZOMBIE);
            }
        }
        if (mobOriginName.equals(GraySlimeSpawnController.mobName)) {
            Guide.incrementMobKill(player, "graySlime");
            if (Guide.getMobKillCount(player, "graySlime") >= 20) {
                Guide.trigV2(player, Guide.StageV2.KILL_20_GRAY_SLIME);
            }
        }

        // 根据怪物等级的掉落概率调整
        double num = getNum(player) * MobBaseAttributes.mobXpDropRateMap
                .getOrDefault(mobOriginName, Collections.emptyMap())
                .getOrDefault(xpLevel, 1d);

        // 直接送至背包
        list.forEach(itemAndRate -> {
            itemAndRate.send(player, num);
        });

        computeKillCount(player);
        incrementPlayerKillCount(player, MobSpawn.getMobOriginName(mob));
        MissionV2Helper.onKillMob(player, mob);
        oldVersionMaterial(mob, player);
        Random rand = new Random();
        if (rand.nextDouble() < 0.1 * num) {
            ItemAndRate itemAndRate = new ItemAndRate(ModItems.WORLD_SOUL_1.get(), 1);
            itemAndRate.send(player, 1);
        }
        PlainCrest.onKillMob(player, mob);
    }

    public static Map<String, Integer> totalKillCountCache = new HashMap<>();

    public static String KILL_COUNT_DATA_KEY = "KillCountDataV2";
    public static CompoundTag getKillCountData(Player player) {
        return PersistentDataUtil.getPlayerSpecificKeyCompoundTagData(player, KILL_COUNT_DATA_KEY);
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
        Set<String> mobNameSet = new HashSet<>();
        for (MobSpawnController controller : getAllControllers(true)) {
            if (!mobNameSet.contains(controller.mobName.getString())) {
                mobNameSet.add(controller.mobName.getString());
                totalCount += getPlayerKillCount(player, controller.mobName.getString());
            }
        }
        for (NoTeamInstance noTeamInstance : NoTeamInstanceModule.getAllInstance()) {
            if (!mobNameSet.contains(noTeamInstance.name.getString())) {
                mobNameSet.add(noTeamInstance.name.getString());
                totalCount += getPlayerKillCount(player, noTeamInstance.name.getString());
            }
        }
        for (NewTeamInstance instance : NewTeamInstanceHandler.getInstances()) {
            if (!mobNameSet.contains(instance.description.getString())) {
                mobNameSet.add(instance.description.getString());
                totalCount += getPlayerKillCount(player, instance.description.getString());
            }
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
                ModItems.ARMOR_KAZE_RECALL.get(), ModItems.ARMOR_SPIDER_RECALL.get(),
                ModItems.ARMOR_HUSK_RECALL.get(), ModItems.ARMOR_SEA_RECALL.get(),
                ModItems.ARMOR_LIGHTNING_RECALL.get(), ModItems.ARMOR_NETHER_RECALL.get(),
                ModItems.ARMOR_SNOW_RECALL.get(), ModItems.ARMOR_VOLCANO_RECALL.get()
        };
        for (int i = 0; i < recalls.length; i++) {
            recallSuccess(recalls[i], player, mob, items[i]);
        }
        if (Utils.forestRecall.recallPlayer != null && mob.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(ModItems.ARMOR_FOREST_RECALL.get())
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
            Item[] items = {ModItems.SUN_POWER.get(), ModItems.LAKE_CORE.get(), ModItems.VOLCANO_CORE.get()};
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
                            InventoryOperation.giveItemStack(player, item.getDefaultInstance());
                        }
                    }
                }
            }
        }
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

    public static Map<LivingEntity, Mob> mountsMap = new HashMap<>();

    public static Set<String> canNotBeAddedSlowDownOrImprisonEffectMobSet = new HashSet<>();

    public static boolean canAddSlowDownOrImprison(Mob mob) {
        return !canNotBeAddedSlowDownOrImprisonEffectMobSet.contains(MobSpawn.getMobOriginName(mob));
    }

    public static void setCanNotAddSlowDownOrImprison(Mob mob) {
        canNotBeAddedSlowDownOrImprisonEffectMobSet.add(MobSpawn.getMobOriginName(mob));
    }
}
