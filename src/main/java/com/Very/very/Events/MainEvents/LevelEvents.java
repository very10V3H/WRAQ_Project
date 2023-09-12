package com.Very.very.Events.MainEvents;

import com.Very.very.Files.ConfigTest;
import com.Very.very.Files.FileHandler;
import com.Very.very.Files.MarketPlayerInfo;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Struct.Gather;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Items.DevelopmentTools.World.IsLandBarrier.BarrierBuild;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

@Mod.EventBusSubscriber
public class LevelEvents {
    @SubscribeEvent
    public static void BroadAndSecurity(TickEvent.LevelTickEvent event) throws IOException {
        if(event.side.isServer())
        {
            if(Utils.MarketFlag){
                Utils.MarketFlag = false;
                FileHandler.MarketInfoRead();
                FileHandler.MarketPlayerInfoRead();
            }
            if(Utils.SecurityInitFlag) {
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
                            mob.setDeltaMovement(gather.getPos().subtract(mob.getPosition(1.0f)).scale(0.2));
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
                                    new ClientboundSetEntityMotionPacket(mob.getId(),gather.getPos().subtract(mob.getPosition(1.0f)).scale(0.2));
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
                boolean flag = false;
                MarketPlayerInfo RemovemarketPlayerInfo = null;
                Iterator<MarketPlayerInfo> iterator = Utils.MarketPlayerInfo.iterator();
                while(iterator.hasNext()){
                    MarketPlayerInfo marketPlayerInfo = iterator.next();
                    if(marketPlayerInfo.getGet() == 0){
                        flag = true;
                        RemovemarketPlayerInfo = marketPlayerInfo;
                        break;
                    }
                }
                if(flag) Utils.MarketPlayerInfo.remove(RemovemarketPlayerInfo);
            }
            if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.getServer().getTickCount() % 20 == 0 && event.phase == TickEvent.Phase.START)
            {
                Random r = new Random();
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,event.level);
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(r.nextInt(1182,1523),63,r.nextInt(371,633));
                lightningBolt.setSilent(true);
                event.level.addFreshEntity(lightningBolt);
            }
            if(event.level.getServer().getTickCount() % 20 == 0)
            {
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.isThundering() && Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = false;
                    BarrierBuild.Destroy(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),
                            Component.literal("唤雷塔的唤雷力量被天空的雷电削弱了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && !event.level.isThundering() && !Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = true;
                    BarrierBuild.Build(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),
                            Component.literal("天空的响雷停息了，唤雷塔的唤雷力量恢复了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
            }
            if(!Utils.MonsterAttributeDataProvider.isEmpty() && Utils.AttributeDataTick != event.level.getServer().getTickCount())
            {
                boolean flag = false;
                LivingEntity RemoveMonster = null;
                Utils.AttributeDataTick = event.level.getServer().getTickCount();
                for (LivingEntity monster : Utils.MonsterAttributeDataProvider) {
                    CompoundTag data = monster.getPersistentData();
                    if(data.getInt("ManaRune2") > 0) data.putInt("ManaRune2", data.getInt("ManaRune2") - 1);
                    if (data.getInt("ManaRune2") == 0) {
                        flag = true;
                        RemoveMonster = monster;
                    }
                    if (monster.isDeadOrDying()) {
                        flag = true;
                        RemoveMonster = monster;
                    }
                }
                if(flag) Utils.MonsterAttributeDataProvider.remove(RemoveMonster);
            }

            if (!Utils.SnowRune2MobController.isEmpty() && Utils.SnowRune2Tick != event.level.getServer().getTickCount()) {
                boolean flag = false;
                LivingEntity RemoveMonster = null;
                for (Mob mob : Utils.SnowRune2MobController) {
                    CompoundTag data = mob.getPersistentData();
                    if (data.getInt("snowRune2Defence") > 0) data.putInt("snowRune2Defence",data.getInt("snowRune2Defence") - 1);
                    if (data.getInt("snowRune2Defence") == 0) {
                        flag = true;
                        RemoveMonster = mob;
                    }
                    if (mob.isDeadOrDying()) {
                        flag = true;
                        RemoveMonster = mob;
                    }
                }
                if (flag) Utils.SnowRune2MobController.remove(RemoveMonster);
            }
            if(!Utils.witherBonePowerCCMonster.isEmpty() && Utils.witherBonePowerCount != event.level.getServer().getTickCount())
            {
                boolean flag = false;
                Mob RemoveMonster = null;
                Utils.witherBonePowerCount = event.level.getServer().getTickCount();
                for(Mob monster : Utils.witherBonePowerCCMonster) {
                    CompoundTag data = monster.getPersistentData();
                    data.putInt("witherBonePower",data.getInt("witherBonePower")-1);
                    if (data.getInt("witherBonePower") == 0) {
                        flag = true;
                        RemoveMonster = monster;

                    }
                    if (monster.isDeadOrDying()) {
                        flag = true;
                        RemoveMonster = monster;
                    }
                }
                if(flag) Utils.witherBonePowerCCMonster.remove(RemoveMonster);
            }
            if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)))
            {
                if(!Utils.OverWorldLevelIsNight &&
                        event.level.getDayTime() % 24000 >= 12000) {
                    event.level.getDayTime();
                    Utils.OverWorldLevelIsNight = true;
                    Compute.FormatBroad(event.level,Component.literal("时间").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD),
                            Component.literal("天渐暗，怪物们得到了来自月光的强化。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    Random r = new Random();
                    Utils.netherMobSpawn = r.nextInt(1,6);
                    switch (Utils.netherMobSpawn) {
                        case 1 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        }
                        case 2 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("下界猪灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        }
                        case 3 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("下界遗骸").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        }
                        case 4 -> {
                            Compute.FormatBroad(event.level, Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                    Component.literal("击杀下界的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("下界熔岩能量聚合物").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                            append(Component.literal("以获取额外掉落。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        }
                    }

                }
                if(Utils.OverWorldLevelIsNight &&
                        event.level.dayTime() % 24000 == 1) {
                    Utils.OverWorldLevelIsNight = false;
                    Compute.FormatBroad(event.level,Component.literal("时间").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD),
                            Component.literal("维瑞阿契迎来的新的一天。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    if(Utils.netherMobSpawn >= 1 && Utils.netherMobSpawn <= 3)
                    {
                        Compute.FormatBroad(event.level, Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal("额外奖励事件结束").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                    Utils.netherMobSpawn = 0;
                }
                if(event.level.getServer().getTickCount() % 8000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount1 && Utils.Security)
                {
                    Utils.securityCount1 = event.level.getServer().getTickCount();
                    double sec0 = ConfigTest.Security0.get();
                    double sec1 = ConfigTest.Security1.get();
                    double sec2 = ConfigTest.Security2.get();
                    double sec3 = ConfigTest.Security3.get();
                    Utils.security0 = (float) sec0;
                    Utils.security1 = (float) sec1;
                    Utils.security2 = (float) sec2;
                    Utils.security3 = (float) sec3;
                    Compute.Broad(event.level,Component.literal("当前股市行情:").withStyle(ChatFormatting.GOLD));
                    Compute.Broad(event.level,Component.literal("——————————").withStyle(ChatFormatting.AQUA));
                    Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)));
                    Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)));
                }
                if(event.level.getServer().getTickCount() % 24000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount && Utils.Security)
                {
                    /*                LogUtils.getLogger().info("log");*/
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

                    float r1 = r.nextFloat(0.1F);
                    Utils.security0 *= (0.9525+r1);
                    if(r1 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal(String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal("+"+String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                    float r2 = r.nextFloat(0.1F);
                    Utils.security1 *= (0.9525+r2);
                    if(r2 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal(String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal("+"+String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                    float r3 = r.nextFloat(0.1F);
                    Utils.security2 *= (0.9525+r3);
                    if(r3 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal(String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal("+"+String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                    float r4 = r.nextFloat(0.1F);
                    Utils.security3 *= (0.9525+r4);
                    if(r4 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal(String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                    else Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal("+"+String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                    ConfigTest.Security0.set((double) Utils.security0);
                    ConfigTest.Security1.set((double) Utils.security1);
                    ConfigTest.Security2.set((double) Utils.security2);
                    ConfigTest.Security3.set((double) Utils.security3);
                }
            }
            int BroadCount = event.level.getServer().getTickCount()%14400;
            Utils.tick = event.level.getServer().getTickCount();
            if(event.phase == TickEvent.Phase.START)
            {
                if(BroadCount == 0 && Utils.Count != 0)
                {
                    Utils.Count = 0;
                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("你知道吗，每日签到的刷新时间是22小时。").withStyle(ChatFormatting.WHITE)));
                }
                else
                {
                    if(BroadCount == 1200 && Utils.Count != 1200)
                    {
                        Utils.Count = 1200;
                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                append(Component.literal("你知道吗，码头的浮标将指引你去到唤雷岛和神殿。").withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        if(BroadCount == 2400 && Utils.Count != 2400)
                        {
                            Utils.Count = 2400;
                            Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                    append(Component.literal("据说，符石是维瑞阿契世界最为强大的物品，在世界各地均有符石祭坛。").withStyle(ChatFormatting.WHITE)));
                        }
                        else
                        {
                            if(BroadCount == 3600 && Utils.Count != 3600)
                            {
                                Utils.Count = 3600;
                                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                        append(Component.literal("你知道吗，在移速加成达到100%后，更高的移速加成不会使角视场发生变化，但是移速加成仍然有效。").withStyle(ChatFormatting.WHITE)));
                            }
                            else
                            {
                                if(BroadCount == 4800 && Utils.Count != 4800)
                                {
                                    Utils.Count = 4800;
                                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                            append(Component.literal("当有两堆同种物品无法堆叠时，将他们放在手上后，就可以堆叠了，是不是很神奇呢？(该bug已于2023.8.23根除)").withStyle(ChatFormatting.WHITE)));
                                }
                                else
                                {
                                    if(BroadCount == 6000 && Utils.Count != 6000)
                                    {
                                        Utils.Count = 6000;
                                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                append(Component.literal("悄悄告诉你，维瑞阿契是一直在更新的哦！").withStyle(ChatFormatting.WHITE)));
                                    }
                                    else
                                    {
                                        if(BroadCount == 7200 && Utils.Count != 7200)
                                        {
                                            Utils.Count = 7200;
                                            Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                    append(Component.literal("悄悄告诉你，弓在蓄力1.5s后伤害达最大值，最大伤害的箭矢会附带粒子特效。所以，多瞄准一会儿吧！").withStyle(ChatFormatting.WHITE)));
                                        }
                                        else
                                        {
                                            if(BroadCount == 8400 && Utils.Count != 8400)
                                            {
                                                Utils.Count = 8400;
                                                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                        append(Component.literal("觉得爬山难？游泳慢？想要各种除战斗属性外的增益？积攒宝石碎片，找天空城珠宝商人兑换珠宝吧！").withStyle(ChatFormatting.WHITE)));
                                            }
                                            else
                                            {
                                                if(BroadCount == 9600 && Utils.Count != 9600)
                                                {
                                                    Utils.Count = 9600;
                                                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                            append(Component.literal("护甲穿透属性，是先进行固定穿透计算后再进行百分比穿透的计算哦。").withStyle(ChatFormatting.WHITE)));
                                                }
                                                else
                                                {
                                                    if(BroadCount == 10800 && Utils.Count != 10800)
                                                    {
                                                        Utils.Count = 10800;
                                                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                                append(Component.literal("护甲穿透、法术穿透以及冷却缩减，均是乘算的哦！").withStyle(ChatFormatting.WHITE)));
                                                    }
                                                    else
                                                    {
                                                        if(BroadCount == 12000 && Utils.Count != 12000)
                                                        {
                                                            Utils.Count = 12000;
                                                            Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                                    append(Component.literal("你可以在维瑞阿契放置与破坏方块！别担心，very_H会帮你还原地图的！").withStyle(ChatFormatting.WHITE)));
                                                        }
                                                        else
                                                        {
                                                            if(BroadCount == 13200 && Utils.Count != 13200)
                                                            {
                                                                Utils.Count = 13200;
                                                                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                                        append(Component.literal("觉得提示信息过多？使用/vmd ignore [Fight/Rune/Exp/QuickUse]来屏蔽一些信息吧！").withStyle(ChatFormatting.WHITE)));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
