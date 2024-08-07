package com.very.wraq.events.mob;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.events.mob.chapter6_castle.BeaconSpawnController;
import com.very.wraq.events.mob.chapter6_castle.BlazeSpawnController;
import com.very.wraq.events.mob.chapter6_castle.TreeSpawnController;
import com.very.wraq.events.mob.chapter1.*;
import com.very.wraq.events.mob.chapter2.*;
import com.very.wraq.events.mob.chapter5.*;
import com.very.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import com.very.wraq.events.mob.chapter3_nether.NetherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter3_nether.PiglinSpawnController;
import com.very.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter4_end.EnderManSpawnController;
import com.very.wraq.events.mob.chapter4_end.EndermiteSpawnController;
import com.very.wraq.events.mob.chapter4_end.ShulkerSpawnController;
import com.very.wraq.events.mob.chapter7.BoneImpSpawnController;
import com.very.wraq.events.mob.chapter7.StarSpawnController;
import com.very.wraq.events.mob.chapter7.TorturedSoulSpawnController;
import com.very.wraq.events.mob.loot.RandomLootEquip;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.end.Recall;
import com.very.wraq.series.end.runes.EndRune;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MobSpawn {

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            Level level = event.level;
            int tick = level.getServer().getTickCount();

            if (event.level.dimension().equals(Level.OVERWORLD)) {
                if (overWolrdList.isEmpty()) setOverWorldList(event.level);
                overWolrdList.forEach(mobSpawnController -> {
                    if (tick % 100 == overWolrdList.indexOf(mobSpawnController)) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }

            if (event.level.dimension().equals(Level.NETHER)) {
                if (netherList.isEmpty()) setNetherList(event.level);
                netherList.forEach(mobSpawnController -> {
                    if (tick % 100 == netherList.indexOf(mobSpawnController) + 50) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }

            if (event.level.dimension().equals(Level.END)) {
                if (endList.isEmpty()) setEndList(event.level);
                endList.forEach(mobSpawnController -> {
                    if (tick % 100 == endList.indexOf(mobSpawnController) + 25) {
                        mobSpawnController.detectAndSpawn();
                    }
                    mobSpawnController.tick();
                });
            }
        }
    }

    public static Map<String, Map<String, Integer>> tempKillCount = new HashMap<>();
    public static Map<String, Map<String, Integer>> totalKillCount = new HashMap<>();

    public static List<MobSpawnController> overWolrdList = new ArrayList<>();

    public static void setOverWorldList(Level overWorld) {
        overWolrdList.add(PlainZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(ForestZombieSpawnController.getInstance(overWorld));
        overWolrdList.add(LakeDrownSpawnController.getInstance(overWorld));
        overWolrdList.add(MineSkeletonSpawnController.getInstance(overWorld));

        overWolrdList.add(SkyVexSpawnController.getInstance(overWorld));
        overWolrdList.add(FireLight2SpawnController.getInstance(overWorld));
        overWolrdList.add(SearedSpiritSpawnController.getInstance(overWorld));
        overWolrdList.add(SearedSpirit2SpawnController.getInstance(overWorld));
        overWolrdList.add(EvokerSpawnController.getInstance(overWorld));
        overWolrdList.add(HuskSpawnController.getInstance(overWorld));
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

    public static Map<String, List<ItemAndRate>> dropList = new HashMap<>();
    public static Map<String, Boolean> dropsDirectToInventory = new HashMap<>();

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

        public static double getMobBaseAttribute(Mob mob, Map<String, Double> map) {
            return map.getOrDefault(getMobOriginName(mob), 0d);
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

        public static void setMobBaseAttributes(Mob mob, double attackDamage, double defence, double manaDefence,
                                                double critRate, double critDamage, double defencePenetration,
                                                double defencePenetration0, double healthSteal, double maxHealth, double movementSpeed) {
            String mobOriginName = getMobOriginName(mob);
            MobBaseAttributes.attackDamage.put(mobOriginName, attackDamage);
            MobBaseAttributes.defence.put(mobOriginName, defence);
            MobBaseAttributes.manaDefence.put(mobOriginName, manaDefence);
            MobBaseAttributes.critRate.put(mobOriginName, critRate);
            MobBaseAttributes.critDamage.put(mobOriginName, critDamage);
            MobBaseAttributes.defencePenetration.put(mobOriginName, defencePenetration);
            MobBaseAttributes.defencePenetration0.put(mobOriginName, defencePenetration0);
            MobBaseAttributes.healthSteal.put(mobOriginName, healthSteal);
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
            mob.setHealth(mob.getMaxHealth());
            MobBaseAttributes.movementSpeed.put(mobOriginName, movementSpeed);
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
            mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        }
    }

    public static double getNum(Player player) {
        double num = 1;
        num += Compute.playerExHarvest(player);
        return num;
    }

    public static void drop(Mob mob, Player player) {
        int xpLevel = MobBaseAttributes.xpLevel.getOrDefault(MobSpawn.getMobOriginName(mob), 0);

        recall(mob, player);
        if (!MobSpawn.dropList.containsKey(MobSpawn.getMobOriginName(mob))) return;
        List<ItemAndRate> list = MobSpawn.dropList.get(MobSpawn.getMobOriginName(mob));

        if (MobSpawn.getMobOriginName(mob).equals(PlainZombieSpawnController.mobName)) Guide.trig(player, 3);
        if (MobSpawn.getMobOriginName(mob).equals(PlainZombieSpawnController.mobName))
            DailyMission.addCount(player, DailyMission.plainZombieKillCountMap);
        if (MobSpawn.getMobOriginName(mob).equals(DreadHoundSpawnController.mobName))
            DailyMission.addCount(player, DailyMission.dreadHoundKillCountMap);
        if (MobSpawn.getMobOriginName(mob).equals(SakuraMobSpawnController.mobName))
            DailyMission.addCount(player, DailyMission.sakuraMobKillCountMap);
        if (MobSpawn.getMobOriginName(mob).equals(WindSkeletonSpawnController.mobName))
            DailyMission.addCount(player, DailyMission.windSkeletonKillCountMap);
        if (MobSpawn.getMobOriginName(mob).equals(EndermiteSpawnController.mobName))
            DailyMission.addCount(player, DailyMission.endermiteKillCountMap);

        double num = getNum(player);

        // 直接送至背包或掉落
        if (dropsDirectToInventory.containsKey(MobSpawn.getMobOriginName(mob))
                || WraqCurios.isOn(EndRune.class, player)) {
            Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), xpLevel);

            list.forEach(itemAndRate -> {
                RandomLootEquip.handleItemAndRate(itemAndRate);
                try {
                    itemAndRate.send(player, num);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } else {
            Random rand = new Random();
            int orbNum = rand.nextInt(5);
            for (int i = 0; i < orbNum; i++) {
                ExperienceOrb orb = new ExperienceOrb(EntityType.EXPERIENCE_ORB, mob.level());
                orb.getPersistentData().putBoolean(fromMobSpawnTag, true);
                if (MobSpawn.getMobOriginName(mob).equals(MagmaSpawnController.mobName) || MobSpawn.getMobOriginName(mob).equals(SlimeSpawnController.mobName)) {
                    orb.getPersistentData().putBoolean(fromSlime, true);
                }
                orb.value = xpLevel;
                orb.setPos(mob.position().add(0, 0.5, 0).add(rand.nextDouble(0.5) - 0.25, 0, rand.nextDouble(0.5) - 0.25));
                orb.setDeltaMovement((rand.nextDouble() * 0.20000000298023224 - 0.10000000149011612) * 2.0,
                        rand.nextDouble() * 0.2 * 2.0, (rand.nextDouble() * 0.20000000298023224 - 0.10000000149011612) * 2.0);
                mob.level().addFreshEntity(orb);
            }

            list.forEach(itemAndRate -> {
                RandomLootEquip.handleItemAndRate(itemAndRate);
                itemAndRate.dropWithoutBounding(mob, num, player);
            });
        }

        Compute.Proficiency(player, xpLevel);

        if (!tempKillCount.containsKey(player.getName().getString()))
            tempKillCount.put(player.getName().getString(), new HashMap<>());
        Map<String, Integer> map = tempKillCount.get(player.getName().getString());
        map.put(getMobOriginName(mob), map.getOrDefault(getMobOriginName(mob), 0) + 1);

        oldVersionMaterial(mob, player);
        Random rand = new Random();
        if (rand.nextDouble() < 0.1 * num) {
            if (WraqCurios.isOn(EndRune.class, player)) {
                Compute.itemStackGive(player, new ItemStack(ModItems.WorldSoul1.get()));
            } else ItemAndRate.summonBoundingItemEntity(mob, new ItemStack(ModItems.WorldSoul1.get()), player);
        }
    }

    public static String fromMobSpawnTag = "fromMobSpawn";
    public static String fromSlime = "fromSlime";

    public static String getMobOriginName(Mob mob) {
        String s = mob.getName().getString();
        return s.substring(s.indexOf(" ") + 1);
    }

    public static void writeToSQL() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        writeModule(statement);
        statement.close();
        connection.close();
    }

    public static void writeToSQL(Statement statement) {
        writeModule(statement);
    }

    private static void writeModule(Statement statement) {
        tempKillCount.forEach((playerName, map) -> {
            if (!totalKillCount.containsKey(playerName)) totalKillCount.put(playerName, new HashMap<>());
            Map<String, Integer> totalKillCountMap = totalKillCount.get(playerName);

            map.forEach((mobName, count) -> {
                String originCountString = null;

                try {
                    originCountString = DataBase.get(statement, playerName, mobName, "killcount");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int originCount = 0;
                if (originCountString != null) originCount = Integer.parseInt(originCountString);

                try {
                    DataBase.put(statement, playerName, mobName, String.valueOf(originCount + count), "killcount");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                totalKillCountMap.put(mobName, originCount + count);
            });
        });
        tempKillCount.clear();
    }


    public static int getKillCount(Player player, Mob mob) throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        int count = getKillCount(statement, player, mob);
        statement.close();
        connection.close();
        return count;
    }

    public static int getKillCount(Statement statement, Player player, Mob mob) throws SQLException {
        String countS = DataBase.get(statement, player.getName().getString(), getMobOriginName(mob));
        int count = 0;
        if (countS != null) {
            count += Integer.parseInt(countS);
            if (tempKillCount.containsKey(player.getName().getString())) {
                Map<String, Integer> map = tempKillCount.get(player.getName().getString());
                if (map.containsKey(getMobOriginName(mob))) count += map.get(getMobOriginName(mob));
            }
        }
        return count;
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
        Element.Unit unit = Element.entityElementUnit.getOrDefault(mob.getId(), new Element.Unit(Element.life, 0));
        if (unit.value() > 0) {
            String[] elementType = {Element.life, Element.water, Element.fire};
            Item[] items = {ModItems.SunPower.get(), ModItems.LakeCore.get(), ModItems.VolcanoCore.get()};
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
                            if (WraqCurios.isOn(EndRune.class, player)) {
                                Compute.itemStackGive(player, item.getDefaultInstance());
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
        connection.close();
    }
}
