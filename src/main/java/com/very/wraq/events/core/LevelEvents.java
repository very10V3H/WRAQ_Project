package com.very.wraq.events.core;

import com.very.wraq.Items.Prefix.PrefixInfo;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import com.very.wraq.customized.players.bow.MyMission.MyMissionBow;
import com.very.wraq.customized.players.sceptre.Eliaoi.Eliaoi;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios2;
import com.very.wraq.customized.uniform.bow.BowCurios1;
import com.very.wraq.events.fight.MonsterAttackEvent;
import com.very.wraq.files.FileHandler;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.PrefixPackets.PrefixS2CPacket;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.originSummon.OriginSummon;
import com.very.wraq.process.instance.MobEffectAndDamageMethods;
import com.very.wraq.process.labourDay.LabourDayMobSummon;
import com.very.wraq.process.tower.Tower;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overWorld.SakuraSeries.EarthMana.EarthPower;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.Boss2Damage;
import com.very.wraq.valueAndTools.Utils.Struct.Gather;
import com.very.wraq.valueAndTools.Utils.Struct.LastDamage;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class LevelEvents {
    @SubscribeEvent
    public static void BroadAndSecurity(TickEvent.LevelTickEvent event) throws IOException, SQLException {
        timeEvent(event);
        WorldBossEvent(event);
        Broad(event);
        Stop(event);
        OriginSummon.DetectElementPiece(event);
        LabourDayMobSummon.levelTick(event);
        BowCurios1.tick(event);
        Tower.tick(event);


        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.getServer().getTickCount() % 18000 == 0) {
            ServerLevel serverLevel = (ServerLevel) event.level;
            List<Entity> projectileList = new ArrayList<>();
            serverLevel.getAllEntities().forEach(entity -> {
                if (entity instanceof Projectile) projectileList.add(entity);
            });
            if (projectileList.size() > 1000) projectileList.forEach(entity -> entity.remove(Entity.RemovalReason.KILLED));
        }// 尝试清理

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.NETHER))) {
            BlockPos blockPos1 = new BlockPos(225,200,622);
            BlockPos blockPos2 = new BlockPos(-80,90,419);
            int TickCount = event.level.getServer().getTickCount();

/*            if (TickCount % 6000 == 256) new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int h = 90 ; h < 201 ; h ++) {
                        for (int i = blockPos2.getX(); i < blockPos1.getX(); i ++) {
                            for (int j = blockPos2.getZ(); j < blockPos1.getZ(); j ++) {
                                BlockPos blockPos = new BlockPos(i,h,j);
                                if (event.level.getBlockState(blockPos).is(Blocks.FIRE))
                                    event.level.destroyBlock(blockPos,false);
                            }
                        }
                    }
                }
            }).start();*/


            if (TickCount % 36000 < 18200 && TickCount % 36000 > 18090) {
                int index = TickCount % 36000;
                for (int i = blockPos2.getX(); i < blockPos1.getX(); i ++) {
                    for (int j = blockPos2.getZ(); j < blockPos1.getZ(); j ++) {
                        BlockPos blockPos = new BlockPos(i,index,j);
                        if (event.level.getBlockState(blockPos).is(Blocks.FIRE))
                            event.level.destroyBlock(blockPos,false);
                    }
                }
            }
        }

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            MobEffectAndDamageMethods.Tick(event.level);
            int TickCount = event.level.getServer().getTickCount();
            if (TickCount % 100 == 0) TryToRemoveMobInMap();
            if (TickCount % 20 == 0) Element.Tick(event.level);

            BlockPos blockPos1 = new BlockPos(1020,128,1089);
            BlockPos blockPos2 = new BlockPos(702,59,808);

/*            if (TickCount % 6000 == 128) new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int h = 59 ; h < 129 ; h ++) {
                        for (int i = blockPos2.getX(); i < blockPos1.getX(); i ++) {
                            for (int j = blockPos2.getZ(); j < blockPos1.getZ(); j ++) {
                                BlockPos blockPos = new BlockPos(i,h,j);
                                if (event.level.getBlockState(blockPos).is(Blocks.FIRE))
                                    event.level.destroyBlock(blockPos,false);
                            }
                        }
                    }
                }
            }).start();*/

            if (TickCount % 36000 < 128 && TickCount % 36000 > 59) {
                int index = TickCount % 36000;
                for (int i = blockPos2.getX(); i < blockPos1.getX(); i ++) {
                    for (int j = blockPos2.getZ(); j < blockPos1.getZ(); j ++) {
                        BlockPos blockPos = new BlockPos(i,index,j);
                        if (event.level.getBlockState(blockPos).is(Blocks.FIRE))
                            event.level.destroyBlock(blockPos,false);
                    }
                }
            }



            Compute.Gather(TickCount); // 聚集

            List<LastDamage> removeList = new ArrayList<>();
            for (LastDamage lastDamage : Utils.PlayerLastDamageToMonster) {
                if (lastDamage != null) {
                    if (TickCount % lastDamage.PerTick == 0) {
                        lastDamage.Tick -= lastDamage.PerTick;
                        if (lastDamage.IsAd) {
                            Compute.Damage.AttackDamageToMonster_RateAdDamage(lastDamage.player, lastDamage.mob, lastDamage.Rate);
                        } else {
                            Compute.Damage.ManaDamageToMonster_RateApDamage(lastDamage.player, lastDamage.mob, lastDamage.Rate, false);
                        }
                    }
                    if (lastDamage.Tick <= 0) removeList.add(lastDamage);
                }
            }
            Utils.PlayerLastDamageToMonster.removeAll(removeList);

            List<LastDamage> removeList1 = new ArrayList<>();
            for (LastDamage lastDamage : Utils.PlayerLastXpStrengthDamageToMonster) {
                if (lastDamage != null) {
                    if (TickCount % lastDamage.PerTick == 0) {
                        lastDamage.Tick -= lastDamage.PerTick;
                        if (lastDamage.IsAd) {
                            Compute.Damage.AttackDamageToMonster_AdDamage(lastDamage.player, lastDamage.mob, Compute.XpStrengthADDamage(lastDamage.player, lastDamage.Rate));
                        } else {
                            Compute.Damage.ManaDamageToMonster_ApDamage(lastDamage.player, lastDamage.mob, Compute.XpStrengthAPDamage(lastDamage.player, lastDamage.Rate));
                        }
                    }
                    if (lastDamage.Tick <= 0) removeList1.add(lastDamage);
                }
            }
            Utils.PlayerLastXpStrengthDamageToMonster.removeAll(removeList1);
        }

        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))
                    && event.level.getServer().getTickCount() % 100 == 0) {
                Compute.TransformMapToSortedList();
                List<ArmorStand> list0 = event.level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(new Vec3(451,67,920),20,20,20));
                for (ArmorStand armorStand : list0) {
                    if (armorStand != null && armorStand.isAlive()) armorStand.remove(Entity.RemovalReason.KILLED);
                }
                List<ArmorStand> list1 = event.level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(new Vec3(423,67,920),20,20,20));
                for (ArmorStand armorStand : list1) {
                    if (armorStand != null && armorStand.isAlive()) armorStand.remove(Entity.RemovalReason.KILLED);
                }
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayKillCountDisplayList,Utils.dayKillCountList, StringUtils.Rank.Kill,new Vec3(423.5,69,928.5));
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayFishCountDisplayList,Utils.dayFishCountList, StringUtils.Rank.Fish,new Vec3(423.5,69,922));
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayMineCountDisplayList,Utils.dayMineCountList, StringUtils.Rank.Mine,new Vec3(451.5,69,922));
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayCropCountDisplayList,Utils.dayCropCountList, StringUtils.Rank.Crop,new Vec3(451.5,69,928.5));
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayLopCountDisplayList,Utils.dayLopCountList, StringUtils.Rank.Lop,new Vec3(426.5,69,920.5));
                Compute.SummonArmorStandForDisplay(event.level,Utils.dayOnlineCountDisplayList,Utils.dayOnlineCountList, StringUtils.Rank.Online,new Vec3(448.5,69,920.5));
                Compute.SummonRecordTimeDisplay(event.level,new Vec3(423.5,67,925.5),true);
                Compute.SummonRecordTimeDisplay(event.level,new Vec3(451.5,67,925.5),false);
                Compute.SummonWoodenStake(event.level);
            }

            if (event.level.getServer().getTickCount() % 400 == 0) Compute.HealWoodenStake();

            if (Utils.countBeginToRecordTime == null) Utils.countBeginToRecordTime = Calendar.getInstance();
            if (Utils.countBeginToRecordTime.get(Calendar.DAY_OF_MONTH) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                Utils.countBeginToRecordTime = Calendar.getInstance();
                Utils.dayKillCount.clear();
                Utils.dayFishCount.clear();
                Utils.dayMineCount.clear();
                Utils.dayCropCount.clear();
                Utils.dayLopCount.clear();
                Utils.dayOnlineCount.clear();
            }

            while (Utils.valueItemEntity.peek() != null) {
                if (Utils.valueItemEntity.peek().getResetTick() < event.level.getServer().getTickCount())
                    Utils.valueItemEntity.poll().getItemEntity().remove(Entity.RemovalReason.KILLED);
                else break;
            }

            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))
                     && event.level.getServer().getTickCount() % 100 == 0) {
                List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();

                for (ServerPlayer serverPlayer : playerList) {
                    CompoundTag TmpData = serverPlayer.getPersistentData();
                    String Prefix = "初来乍到";
                    if (TmpData.contains("Prefix")) Prefix = TmpData.getString("Prefix");
                    PrefixInfo prefixInfo = new PrefixInfo(Prefix, serverPlayer.experienceLevel);
                    Utils.prefixInfoMap.put(serverPlayer.getName().getString(), prefixInfo);
                }

                for (ServerPlayer serverPlayer : playerList) {
                    for (String playerName : Utils.prefixInfoMap.keySet()) {
                        ModNetworking.sendToClient(new PrefixS2CPacket(playerName, Utils.prefixInfoMap.get(playerName).getPrefix(), Utils.prefixInfoMap.get(playerName).getLevel()), serverPlayer);
                    }
                }

                playerList.forEach(Player::refreshDisplayName);
                playerList.forEach(ServerPlayer::refreshTabListName);
            }

            if (Utils.MarketFlag){
                Utils.MarketFlag = false;
                FileHandler.MarketInfoRead();
                FileHandler.MarketPlayerInfoRead();
                /*FileHandler.EntropyInfoRead();*/
                FileHandler.ForgeNumRead();
            }
            if (Utils.SecurityInitFlag) {
                Utils.SecurityInitFlag = false;
                Utils.SecurityInit();
            }
            if (event.level.getServer().getTickCount() != Utils.GatherTickCount) {
                Utils.GatherTickCount = event.level.getServer().getTickCount();
                List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
                boolean flag = false;
                Gather RemoveGater = null;
                for (Gather gather : Utils.GatherMobMap.keySet()) {
                    if(gather.getTime() > 0) {
                        gather.setTime(gather.getTime() - 1);
                        Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                        for (Mob mob : mobQueue) {
                            mob.setDeltaMovement(gather.getPos().subtract(mob.position()).scale(0.2));
                        }
                    }
                    if (gather.getTime() == 0) {
                        flag = true;
                        RemoveGater = gather;
                    }
                }
                if (flag) Utils.GatherMobMap.remove(RemoveGater);
                flag = false;
                RemoveGater = null;
                for (Gather gather : Utils.GatherPlayerMap.keySet()) {
                    if (gather.getTime() > 0) {
                        gather.setTime(gather.getTime() - 1);
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        for (Player mob : mobQueue) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(mob.getId(),gather.getPos().subtract(mob.position()).scale(0.2));
                            for (ServerPlayer serverPlayer : playerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }
                        }
                    }
                    if (gather.getTime() == 0) {
                        flag = true;
                        RemoveGater = gather;
                    }
                }
                if (flag) Utils.GatherPlayerMap.remove(RemoveGater);
            }
            if(event.level.getServer().getTickCount() % 100 == 0 && event.level.getServer().getTickCount() != Utils.MarketTickCount){
                Utils.MarketTickCount = event.level.getServer().getTickCount();
                FileHandler.ItemInfoWrite();
                FileHandler.PlayerInfoWrite();
                Utils.MarketPlayerInfo.removeIf(marketPlayerInfo -> marketPlayerInfo.getGet() == 0);
            }
            if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.getServer().getTickCount() % 20 == 0) {
                List<Player> playerList = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1352,80,502),150,60,100));
                if (playerList.size() > 0) {
                    Random r = new Random();
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,event.level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(r.nextInt(1182,1523),63,r.nextInt(371,633));
                    lightningBolt.setSilent(true);
                    event.level.addFreshEntity(lightningBolt);
                }
            }
/*            if(event.level.getServer().getTickCount() % 20 == 0)
            {
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.isThundering() && Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = false;
                    BarrierBuild.Destroy(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland),
                            Component.literal("唤雷塔的唤雷力量被天空的雷电削弱了。").withStyle(ChatFormatting.WHITE));
                }
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && !event.level.isThundering() && !Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = true;
                    BarrierBuild.Build(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland),
                            Component.literal("天空的响雷停息了，唤雷塔的唤雷力量恢复了。").withStyle(ChatFormatting.WHITE));
                }
            }*/
            if(!Utils.MonsterAttributeDataProvider.isEmpty() && Utils.AttributeDataTick != event.level.getServer().getTickCount())
            {
                Utils.AttributeDataTick = event.level.getServer().getTickCount();
                Utils.MonsterAttributeDataProvider.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if(data.getInt("ManaRune2") > 0)
                        data.putInt("ManaRune2", data.getInt("ManaRune2") - 1);
                });
                Utils.MonsterAttributeDataProvider.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("ManaRune2") == 0 || monster.isDeadOrDying();
                });
            }

            if (!Utils.SnowRune2MobController.isEmpty() && Utils.SnowRune2Tick != event.level.getServer().getTickCount()) {
                Utils.SnowRune2Tick = event.level.getServer().getTickCount();
                Utils.SnowRune2MobController.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if(data.getInt("snowRune2Defence") > 0)
                        data.putInt("snowRune2Defence", data.getInt("snowRune2Defence") - 1);
                });
                Utils.SnowRune2MobController.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("snowRune2Defence") == 0 || monster.isDeadOrDying();
                });
            }
            if(!Utils.witherBonePowerCCMonster.isEmpty() && Utils.witherBonePowerCount != event.level.getServer().getTickCount())
            {
                Utils.witherBonePowerCount = event.level.getServer().getTickCount();
                Utils.witherBonePowerCCMonster.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if(data.getInt("witherBonePower") > 0)
                        data.putInt("witherBonePower", data.getInt("witherBonePower") - 1);
                });
                Utils.witherBonePowerCCMonster.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("witherBonePower") == 0 || monster.isDeadOrDying();
                });
            }
            if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)))
            {
                if(!Utils.OverWorldLevelIsNight &&
                        event.level.getDayTime() % 24000 >= 12000) {
                    Utils.OverWorldLevelIsNight = true;
                    Compute.FormatBroad(event.level,Component.literal("时间").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD),
                            Component.literal("天渐暗，怪物们得到了来自月光的强化。").withStyle(ChatFormatting.WHITE));
                    Random r = new Random();
                    Utils.netherMobSpawn = r.nextInt(1,6);
                    switch (Utils.netherMobSpawn) {
                        case 1 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.WHITE).append(Component.literal("下界凋零骷髅").withStyle(CustomStyle.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.WHITE)));
                        }
                        case 2 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.WHITE).append(Component.literal("下界猪灵").withStyle(CustomStyle.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.WHITE)));
                        }
                        case 3 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.WHITE).append(Component.literal("下界遗骸").withStyle(CustomStyle.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.WHITE)));
                        }
                        case 4 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.WHITE).append(Component.literal("下界熔岩能量聚合物").withStyle(CustomStyle.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.WHITE)));
                        }
                    }

                }
                if(Utils.OverWorldLevelIsNight &&
                        event.level.dayTime() % 24000 == 1) {
                    Utils.OverWorldLevelIsNight = false;
                    Compute.FormatBroad(event.level,Component.literal("时间").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD),
                            Component.literal("维瑞阿契迎来的新的一天。").withStyle(ChatFormatting.WHITE));
                    if(Utils.netherMobSpawn >= 1 && Utils.netherMobSpawn <= 3)
                    {
                        Compute.FormatBroad(event.level, Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                                Component.literal("额外奖励事件结束").withStyle(ChatFormatting.WHITE));
                    }
                    Utils.netherMobSpawn = 0;
                }

/*                if(event.level.getServer().getTickCount() % 8000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount1 && Utils.Security)
                {
                    Utils.securityCount1 = event.level.getServer().getTickCount();
                    double sec0 = ConfigTest.Security0.get();
                    double sec1 = ConfigTest.Security1.get();
                    double sec2 = ConfigTest.Security2.get();
                    double sec3 = ConfigTest.Security3.get();
                    Utils.security0 =  sec0;
                    Utils.security1 =  sec1;
                    Utils.security2 =  sec2;
                    Utils.security3 = (float) sec3;
                    Compute.Broad(event.level,Component.literal("当前股市行情:").withStyle(ChatFormatting.GOLD));
                    Compute.Broad(event.level,Component.literal("——————————").withStyle(ChatFormatting.AQUA));
                    Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)));
                }
                if(event.level.getServer().getTickCount() % 24000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount && Utils.Security) {
                    *//*                LogUtils.getLogger().info("log");*//*
                    Utils.securityCount = event.level.getServer().getTickCount();
                    Compute.Broad(event.level,Component.literal("今日股市行情:").withStyle(ChatFormatting.GOLD));
                    Compute.Broad(event.level,Component.literal("——————————").withStyle(ChatFormatting.AQUA));
                    Random r = new Random();
                    double sec0 = ConfigTest.Security0.get();
                    double sec1 = ConfigTest.Security1.get();
                    double sec2 = ConfigTest.Security2.get();
                    double sec3 = ConfigTest.Security3.get();
                    Utils.security0 = (float) sec0;
                    Utils.security1 = (float) sec1;
                    Utils.security2 = (float) sec2;
                    Utils.security3 = (float) sec3;

                    float r1 = r.nextDouble(0.1F);
                    Utils.security0 *= (0.9525+r1);
                    if(r1 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal(String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal("+"+String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                    float r2 = r.nextDouble(0.1F);
                    Utils.security1 *= (0.9525+r2);
                    if(r2 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal(String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal("+"+String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                    float r3 = r.nextDouble(0.1F);
                    Utils.security2 *= (0.9525+r3);
                    if(r3 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal(String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal("+"+String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                    float r4 = r.nextDouble(0.1F);
                    Utils.security3 *= (0.9525+r4);
                    if(r4 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal(String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal("+"+String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                    ConfigTest.Security0.set( Utils.security0);
                    ConfigTest.Security1.set( Utils.security1);
                    ConfigTest.Security2.set( Utils.security2);
                    ConfigTest.Security3.set( Utils.security3);
                }*/
            }
/*            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
                int TickCount = event.level.getServer().getTickCount();
                Random random = new Random();
                if (TickCount % 36000 == 0) {
                    double OriginalNum = Utils.WorldEntropyIncreaseSpeed;
                    Utils.WorldEntropyIncreaseSpeed += 0.01 - (random.nextDouble(0.02));
                    if (Utils.WorldEntropyIncreaseSpeed >= 0.75) Utils.WorldEntropyIncreaseSpeed = 0.75;
                    if (Utils.WorldEntropyIncreaseSpeed <= 0.25) Utils.WorldEntropyIncreaseSpeed = 0.25;
                    Compute.FormatBroad(event.level,Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("世界相对熵增速度发生了变化:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(String.format("%.2f%%",OriginalNum * 100)).withStyle(ChatFormatting.GRAY)).
                                    append(Component.literal(" -> ").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f%%",Utils.WorldEntropyIncreaseSpeed * 100)).withStyle(CustomStyle.styleOfWorld)));
                    Compute.FormatBroad(event.level,Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("世界不同地点本源解析场的相对介导常数发生了变化:").withStyle(ChatFormatting.WHITE));
                    Utils.WorldEntropyPos.forEach(worldEntropy -> {
                        double OriginalNum1 = worldEntropy.getMediumNum();
                        worldEntropy.setMediumNum(random.nextDouble(0.25));
                        Compute.FormatBroad(event.level,Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                                Component.literal(worldEntropy.getDesName()).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(String.format("%.2f%%",OriginalNum1 * 100)).withStyle(ChatFormatting.GRAY)).
                                        append(Component.literal(" -> ").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f%%",worldEntropy.getMediumNum() * 100)).withStyle(CustomStyle.styleOfWorld)));
                    });
                    FileHandler.EntropyInfoWrite();
                }
            }*/
            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
                int TickCount = event.level.getServer().getTickCount();
                if (TickCount % 36000 == 18000) {
                    Compute.purpleMineBlockPosInit(event.level,false);
                }
            }
        }
    }
    public static void timeEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Calendar calendar = Calendar.getInstance();
            Level level = event.level;
            List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
            if (event.level.getServer().getTickCount() % 200 == 0) {
                if (calendar.get(Calendar.HOUR_OF_DAY) == 9 && Utils.TimeEventFlag != 9) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("早上好！新的一天有新的开始！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 9;
                    playerList.forEach(serverPlayer -> {
                        Compute.ExpPercentGetAndMSGSend(serverPlayer,0.2,0,serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 12 && Utils.TimeEventFlag != 12) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("午餐时间到了，吃完饭后小憩一会儿吧！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 12;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBgetAndMSGSend(serverPlayer,serverPlayer.experienceLevel * 5);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 15 && Utils.TimeEventFlag != 15) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("三点几嘞！饮茶先咯！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 15;
                    playerList.forEach(serverPlayer -> {
                        Compute.ExpPercentGetAndMSGSend(serverPlayer,0.2,0,serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 18 && Utils.TimeEventFlag != 18) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("今晚吃什么呢？").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 18;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBgetAndMSGSend(serverPlayer,serverPlayer.experienceLevel * 5);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 21 && Utils.TimeEventFlag != 21) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("夜生活才刚刚开始！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 21;
                    playerList.forEach(serverPlayer -> {
                        Compute.ExpPercentGetAndMSGSend(serverPlayer,0.2,0,serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && Utils.TimeEventFlag != 0) {
                    Compute.FormatBroad(level,Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("夜深了，似乎该洗澡休息了呢。").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 0;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBgetAndMSGSend(serverPlayer,serverPlayer.experienceLevel * 5);
                    });
                    Utils.Boss2DeadTimes = 0;
                    Compute.FormatBroad(level,Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                            Component.literal(" 突见忍的实力重回到了初始状态！").withStyle(CustomStyle.styleOfSakura));
                }
            }
        }
    }
    public static void WorldBossEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Calendar calendar = Calendar.getInstance();
            Level level = event.level;
            if (Utils.giant != null && Utils.giant.isAlive()) Utils.GiantBossInfo.setProgress(Utils.giant.getHealth() / Utils.giant.getMaxHealth());
            if (level.getServer().getTickCount() % 20 == 0) {
                if (calendar.get(Calendar.SECOND) == 1 && calendar.get(Calendar.MINUTE) == 59
                        && (calendar.get(Calendar.HOUR_OF_DAY) == 9
                        || calendar.get(Calendar.HOUR_OF_DAY) == 14
                        || calendar.get(Calendar.HOUR_OF_DAY) == 18
                        || calendar.get(Calendar.HOUR_OF_DAY) == 20
                        || calendar.get(Calendar.HOUR_OF_DAY) == 22)) {
                    Compute.FormatBroad(level,Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(" 巨人僵尸还有1分钟就要出现了！").withStyle(ChatFormatting.WHITE));
                    Utils.GiantPlayerList.forEach(playerName -> {
                        if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                            serverPlayer.teleportTo((ServerLevel) level,638,114,1357,0,0);
                        }
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 10
                        || calendar.get(Calendar.HOUR_OF_DAY) == 15
                        || calendar.get(Calendar.HOUR_OF_DAY) == 19
                        || calendar.get(Calendar.HOUR_OF_DAY) == 21
                        || calendar.get(Calendar.HOUR_OF_DAY) == 23) {
                    if (Utils.GiantHour != calendar.get(Calendar.HOUR_OF_DAY)) {
                        Utils.GiantHour = calendar.get(Calendar.HOUR_OF_DAY);
                        SummonGiant(level);
                    }
                }
                if (Utils.GiantFlag && Utils.giant != null && Utils.giant.isDeadOrDying() && Utils.GiantDamageList.size() > 0) {
                    Utils.GiantFlag = false;
                    Compute.FormatBroad(level,Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));
                    Utils.GiantDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                    AtomicInteger index = new AtomicInteger(1);

                    if (Utils.GiantBossInfo != null) Utils.GiantPlayerList.forEach(playerName -> {
                        if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                            Utils.GiantBossInfo.removePlayer(serverPlayer);
                        }
                    });

                    Utils.GiantDamageList.forEach(boss2Damage -> {
                        if (boss2Damage.getPlayer() != null) {
                            Player player1 = boss2Damage.getPlayer();
                            double damage = boss2Damage.getDamage();
                            Compute.FormatBroad(level,Component.literal(index + ".").withStyle(ChatFormatting.RED),
                                    Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                            append(player1.getDisplayName()).
                                            append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f",damage * 100 / (Utils.giant.getMaxHealth())) + "%]").withStyle(ChatFormatting.WHITE)));
                            if (Utils.GiantCommonReward.isEmpty()) Utils.setGiantCommonReward();
                            if (boss2Damage.getDamage() / (Utils.giant.getMaxHealth()) > 0.005) {
                                Utils.GiantCommonReward.forEach(itemStack -> {
                                    try {
                                        Compute.ItemStackGive(player1,new ItemStack(itemStack.getItem(),itemStack.getCount()));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                if (index.get() <= 3) {
                                    try {
                                        Compute.ItemStackGive(player1,ModItems.GiantMedal.get().getDefaultInstance());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            index.incrementAndGet();
                        }
                    });
                    Utils.GiantPlayerList.clear();
                    Utils.GiantDamageList.clear();
                }
            }
        }
    }
    public static void SummonGiant(Level level) {

        if (Utils.GiantPlayerList.size() != 0) {
            if (Utils.giant != null) Utils.giant.remove(Entity.RemovalReason.KILLED);
            Utils.giant = new Giant(EntityType.GIANT,level);

            Compute.SetMobCustomName(Utils.giant,ModItems.MobArmorGiant.get(),Component.literal("悲催的巨人").withStyle(ChatFormatting.GREEN));
            Utils.giant.setItemSlot(EquipmentSlot.HEAD,ModItems.MobArmorGiant.get().getDefaultInstance());
            Random r = new Random();
            Utils.giant.getAttribute(Attributes.MAX_HEALTH).setBaseValue(r.nextInt(100,200) * Math.pow(10,8));
            Utils.giant.setHealth(Utils.giant.getMaxHealth());
            Utils.giant.moveTo(638,114,1357);
            level.addFreshEntity(Utils.giant);
            Compute.FormatBroad(level,Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(" 巨人僵尸已经出现！").withStyle(ChatFormatting.WHITE));
            Utils.GiantFlag = true;
            Utils.GiantPlayerList.forEach(playerName -> {
                if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                    ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                    serverPlayer.teleportTo((ServerLevel) level,638,114,1357,0,0);
                }
            });
            Utils.GiantBossInfo = (ServerBossEvent) (new ServerBossEvent(Utils.giant.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

            Utils.GiantPlayerList.forEach(playerName -> {
                if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                    ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                    Utils.GiantBossInfo.addPlayer( serverPlayer);
                }
            });
        }
        else {
            Compute.FormatBroad(level,Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(" 没有人来找巨人僵尸玩，巨人僵尸伤心欲绝。").withStyle(ChatFormatting.WHITE));
        }
    }
    public static void TryToRemoveMobInMap() {
        Utils.SlimeRewardPlayer.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.LakePowerEffectMobMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringAttackTick.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringAttackEffect.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringSwiftTick.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringSwiftEffect.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringManaTick.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.MobSpringManaEffect.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.NetherBoneMealPowerEffectMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.ShangMengLiSkillEffectMob.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.CrushDefenceDecreaseMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.FengxiaojuPassiveMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.FengxiaojuPassiveEffecetMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.GuangYiMobDefenceDecreaseMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.GuangYiMobDefenceDecreaseTime.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.SnowShieldMobEffectMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.WitherBookMobEffectTick.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.IceBookMobEffectTickMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.IceBookMobEffectPlayerMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Eliaoi.EliaoiMobMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Eliaoi.EliaoiMobCountMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        Utils.playerLaserCoolDown.forEach((player, mobIntegerMap) -> {
            if (mobIntegerMap != null) {
                mobIntegerMap.entrySet().removeIf(mobIntegerEntry -> mobIntegerEntry.getKey().isDeadOrDying());
            }
        });
        EarthPower.Plain_MobDamageDecrease.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        EarthPower.Lake_MobManaDefenceDecrease.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        MyMissionBow.mobArrowCountMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());

        MonsterAttackEvent.mobDamageLimitTickMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        LeiyanBow.Active2MobCount.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        LeiyanBow.Active2MobTick.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());
        EliaoiCurios2.mobCountsMap.entrySet().removeIf(slimePlayerEntry -> slimePlayerEntry.getKey().isDeadOrDying());

    }

    public static void Broad(TickEvent.LevelTickEvent event) {
        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            String[] BroadCastContent = {
                    "对于物品描述下方有[可灌注/增幅]标签的物品，可以将其放置在能量灌注器灌注位查看灌注配方。",
                    "维瑞阿契的铁道交通正在建设当中，对于想要前往绯樱岛与唤雷岛的玩家来说，铁路是个不错的方式。",
                    "你知道吗，每日任务的冷却时间是22小时。",
                    "据说，在世界各地均有符石祭坛。",
                    "维瑞阿契一直在努力更新。",
                    "觉得爬山难？游泳慢？想要各种除战斗属性外的增益？积攒宝石碎片，找天空城珠宝商人兑换珠宝吧！",
                    "当穿透属性使得抗性在计算中成为负值，负值部分将为本次伤害提供增幅。",
                    "百分比护甲穿透、百分比法术穿透，均是乘算的哦！",
                    "觉得提示信息过多？使用/vmd ignore [Fight/Exp/QuickUse/ItemGet/Rune/Mana/Instance]来屏蔽一些信息吧！",
                    "幸运属性将会影响物品的掉落概率以及各种看脸的概率。",
                    "注意，当你以鞘翅开启状态进入水中，鞘翅可能不会关闭，这将影响你在水中的运动。脱下鞘翅或者找到站立点是解决方法。",
                    "能力点数的配置将大幅影响你的游戏体验。",
                    "快捷使用将使你的主动释放没有前后摇。赶紧配置你的快捷使用键位吧！",
                    "现在，世界各地出现了本源学者。他们带着他们的本源解析器来到了维瑞阿契，将解析出来的本源交给他们，可以换取他们的一些研究成果。",
                    "下界维度充满着危险，因此需要你达到50级才可以前往，终界维度同样需要75级。",
                    "在世界各处散落着奖励箱，每隔一段时间，你便能够从中获取一些稀有奖励。（测试阶段）",
                    "经测试，若mod版本与服务器不一，则会出现部分数据缺失的现象，请及时更新mod。",
                    "在体力充足的情况下，按下翻滚（默认Z键）可以让跑图更加顺畅、战斗节奏更加紧凑",
                    "你可以通过/vmd bow来开关弓的视角拉伸",
                    "不知道做什么了？打开身份卡，查看图鉴，找到你喜爱的装备，努力制作吧！",
                    "欢迎加入维瑞阿契群:184453807 维瑞阿契更新通知群:693292427",
                    "觉得移动速度过快？使用/vmd speed [效能]来手动调整你的移动速度加成效能吧！（手动调整仅会影响你的移动速度加成属性在玩家移速上的体现）",
                    "你知道吗？身份卡的图鉴可以查看装备的获取方式。",
                    "你知道吗？如果没有归一化元素强度，元素共鸣提供的反应元素量将会忽略不计。",
                    "默认P打开任务列表，查看进行中的任务，完成获得奖励吧！"
            };
            int TickCount = event.level.getServer().getTickCount();

            if (TickCount % 6000 == 0) {
                Random random = new Random();
                Compute.FormatBroad(event.level,Component.literal("公告").withStyle(ChatFormatting.AQUA),
                        Component.literal(BroadCastContent[random.nextInt(BroadCastContent.length)]).withStyle(ChatFormatting.WHITE));
            }
            if (TickCount % 12000 == 3333 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19) {
                Compute.FormatBroad(event.level, Component.literal("公告").withStyle(ChatFormatting.AQUA),
                        Component.literal("每天的19:00 ~ 24:00，大多数怪物的物品掉落概率将会翻倍!").withStyle(ChatFormatting.AQUA));
            }

            if (TickCount % 12000 == 6666 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19) {
                Compute.FormatBroad(event.level, Component.literal("公告").withStyle(ChatFormatting.AQUA),
                        Component.literal("现在，大多数怪物的物品掉落概率已经翻倍!").withStyle(ChatFormatting.AQUA));
            }
        }
    }

    public static void Stop(TickEvent.LevelTickEvent event) {
        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            if (Utils.ServerStopTick != -1) {
                Utils.ServerStopTick --;
            }
            if (Utils.ServerStopTick % 20 == 0) {
                if (Utils.ServerStopTick == 0) {
                    event.level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.connection.disconnect(Component.literal("服务器正在进行更新维护...").withStyle(ChatFormatting.AQUA));
                    });
                }
                else {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("服务器即将进行更新维护").withStyle(ChatFormatting.AQUA));
                    ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                            new ClientboundSetSubtitleTextPacket(Component.literal("请不要锻造、打本，并尽快退出服务器..." + (Utils.ServerStopTick / 20) + "s").withStyle(ChatFormatting.AQUA));
                    event.level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6),serverPlayer);
                        if (Utils.ServerStopTick % 100 == 0) serverPlayer.sendSystemMessage(Component.literal("更新群号：693292427").withStyle(ChatFormatting.AQUA));
                    });
                }
            }
        }
    }
}
