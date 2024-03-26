package com.Very.very.ValueAndTools.Utils.Struct;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ItemAndRate;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drops {
    public static void PlainZombie (Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.PlainSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),2 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.PlainCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.PlainCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.PlainCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.PlainCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02 * Num,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.PlainZombie).MobLevel);
        if(!data.contains("KillCountOfPlainZombie")) data.putInt("KillCountOfPlainZombie", Num);
        else data.putInt("KillCountOfPlainZombie",data.getInt("KillCountOfPlainZombie") + Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+ Num);
        if(data.contains("DailyMission1")) data.putInt("DailyMission1",data.getInt("DailyMission1")+ Num);
    }
    public static void ForestSkeleton (Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.ForestSoul.get().getDefaultInstance(),0.6 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),2 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.ForestCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.ForestCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.ForestCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.ForestCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ForestSkeleton).MobLevel);
        if(!data.contains("KillCountOfForestSkeleton")) data.putInt("KillCountOfForestSkeleton",Num);
        else data.putInt("KillCountOfForestSkeleton",data.getInt("KillCountOfForestSkeleton")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission2")) data.putInt("DailyMission2",data.getInt("DailyMission2")+Num);
    }
    public static void ForestZombie(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.ForestSoul.get().getDefaultInstance(),0.9 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.ForestCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.ForestCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.ForestCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.ForestCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ForestZombie).MobLevel);
        if(!data.contains("KillCountOfForestZombie")) data.putInt("KillCountOfForestZombie",Num);
        else data.putInt("KillCountOfForestZombie",data.getInt("KillCountOfForestZombie")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission3")) data.putInt("DailyMission3",data.getInt("DailyMission3")+Num);
    }
    public static void LakeDrown(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.LakeSoul.get().getDefaultInstance(),0.9 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.25 * Num));
            add(new ItemAndRate(ModItems.LakeCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.LakeCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.LakeCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.LakeCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LakeDrowned).MobLevel);
        if(!data.contains("KillCountOfLakeDrowned")) data.putInt("KillCountOfLakeDrowned",Num);
        else data.putInt("KillCountOfLakeDrowned",data.getInt("KillCountOfLakeDrowned")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission4")) data.putInt("DailyMission4",data.getInt("DailyMission4")+Num);
    }
    public static void VolcanoBlaze(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.VolcanoSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),5 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.VolcanoCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.VolcanoCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.VolcanoCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.VolcanoCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.VolcanoBlaze).MobLevel);
        if(!data.contains("KillCountOfVolcanoBlazw")) data.putInt("KillCountOfVolcanoBlazw",Num);
        else data.putInt("KillCountOfVolcanoBlazw",data.getInt("KillCountOfVolcanoBlazw")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission5")) data.putInt("DailyMission5",data.getInt("DailyMission5")+Num);
    }
    public static void Mine(Player player, int Num, boolean IsAttackKill) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.MineSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.MineSoul1.get().getDefaultInstance(),0.05 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),5 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.MineCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.MineCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.MineCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.MineCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.MineZombie).MobLevel);
        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);

        if (!IsAttackKill) {
            Random random = new Random();
            int KillNum = random.nextInt(Num);
            if (!data.contains("KillCountOfMineZombie")) data.putInt("KillCountOfMineZombie", KillNum);
            else data.putInt("KillCountOfMineZombie", data.getInt("KillCountOfMineZombie") + KillNum);
            data.putInt("DailyMission6", data.getInt("DailyMission6") + KillNum);
            if (!data.contains("KillCountOfMineSkeleton")) data.putInt("KillCountOfMineSkeleton", Num - KillNum);
            else data.putInt("KillCountOfMineSkeleton", data.getInt("KillCountOfMineSkeleton") + Num - KillNum);
            data.putInt("DailyMission7", data.getInt("DailyMission7") + Num - KillNum);
        }
    }
    public static void Field(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.FieldSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),5 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.FieldZombie).MobLevel);
        if(!data.contains("KillCountOfFeildZombie")) data.putInt("KillCountOfFeildZombie",Num);
        else data.putInt("KillCountOfFeildZombie",data.getInt("KillCountOfFeildZombie")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
    }
    public static void SnowStray(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SnowSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),10 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.SnowCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.SnowCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.SnowCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.SnowCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SnowStray).MobLevel);
        if(!data.contains("KillCountOfSnowStray")) data.putInt("KillCountOfSnowStray",Num);
        else data.putInt("KillCountOfSnowStray",data.getInt("KillCountOfSnowStray")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission8")) data.putInt("DailyMission8",data.getInt("DailyMission8")+Num);
    }
    public static void SkyVex(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SkySoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),10 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.SkyCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.SkyCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.SkyCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.SkyCrest3.get().getDefaultInstance(),0.0002 * Num));
            add(new ItemAndRate(ModItems.SkyHelmetForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.SkyChestForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.SkyLeggingsForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.SkyBootsForgeDraw.get().getDefaultInstance(),0.00025 * Num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SkyVex).MobLevel);
        if(!data.contains("KillCountOfVex")) data.putInt("KillCountOfVex",Num);
        else data.putInt("KillCountOfVex",data.getInt("KillCountOfVex")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission9")) data.putInt("DailyMission9",data.getInt("DailyMission9")+Num);
    }
    public static void Evoker(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.EvokerSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.ManaCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.ManaCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.ManaCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.ManaCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Evoker).MobLevel);
        if(!data.contains("KillCountOfEvoker")) data.putInt("KillCountOfEvoker",Num);
        else data.putInt("KillCountOfEvoker",data.getInt("KillCountOfEvoker")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission10")) data.putInt("DailyMission10",data.getInt("DailyMission10")+Num);
    }
    public static void EvokerMaster(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.EvokerSoul.get().getDefaultInstance(),4 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.ManaCrest0.get().getDefaultInstance(),0.02 * Num));
            add(new ItemAndRate(ModItems.ManaCrest1.get().getDefaultInstance(),0.005 * Num));
            add(new ItemAndRate(ModItems.ManaCrest2.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.ManaCrest3.get().getDefaultInstance(),0.0002 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EvokerMaster).MobLevel);
        if(!data.contains("KillCountOfEvokerMaster")) data.putInt("KillCountOfEvokerMaster",Num);
        else data.putInt("KillCountOfEvokerMaster",data.getInt("KillCountOfEvokerMaster")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission10")) data.putInt("DailyMission10",data.getInt("DailyMission10")+Num);
    }
    public static void SeaGuardian(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SeaSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.SeaSwordForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.SeaManaCoreForgeDraw.get().getDefaultInstance(),0.001 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SeaGuardian).MobLevel);
        if(!data.contains("KillCountOfGuardian")) data.putInt("KillCountOfGuardian",Num);
        else data.putInt("KillCountOfGuardian",data.getInt("KillCountOfGuardian")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission12")) data.putInt("DailyMission12",data.getInt("DailyMission12")+Num);
    }
    public static void LightingZombie(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.LightningSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.IslandHelmetForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.IslandChestForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.IslandLeggingsForgeDraw.get().getDefaultInstance(),0.00025 * Num));
            add(new ItemAndRate(ModItems.IslandBootsForgeDraw.get().getDefaultInstance(),0.00025 * Num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LightingZombie).MobLevel);
        if(!data.contains("KillCountOfLZ")) data.putInt("KillCountOfLZ",Num);
        else data.putInt("KillCountOfLZ",data.getInt("KillCountOfLZ")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission13")) data.putInt("DailyMission13",data.getInt("DailyMission13")+Num);
    }
    public static void Husk(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.BlackForestSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.BlackForestSwordForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.BlackForestManaCoreForgeDraw.get().getDefaultInstance(),0.001 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Husk).MobLevel);
        if(!data.contains("KillCountOfBFHusk")) data.putInt("KillCountOfBFHusk",Num);
        else data.putInt("KillCountOfBFHusk",data.getInt("KillCountOfBFHusk")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission11")) data.putInt("DailyMission11",data.getInt("DailyMission11")+Num);
    }
    public static void Kaze(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.KazeSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.KazeSwordForgeDraw.get().getDefaultInstance(),0.0005 * Num));
            add(new ItemAndRate(ModItems.KazeBootsForgeDraw.get().getDefaultInstance(),0.0005 * Num));
            add(new ItemAndRate(ModItems.KazeManaCoreForgeDraw.get().getDefaultInstance(),0.001 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.KazeZombie).MobLevel);
        if(!data.contains("KillCountOfKaze")) data.putInt("KillCountOfKaze",Num);
        else data.putInt("KillCountOfKaze",data.getInt("KillCountOfKaze")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission18")) data.putInt("DailyMission18",data.getInt("DailyMission18")+Num);
    }
    public static void Spider(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SpiderSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Spider).MobLevel);
        if(!data.contains("KillCountOfSpider")) data.putInt("KillCountOfSpider",Num);
        else data.putInt("KillCountOfSpider",data.getInt("KillCountOfSpider")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission19")) data.putInt("DailyMission19",data.getInt("DailyMission19")+Num);
    }
    public static void SilverFish(Player player,int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SilverFishSoul.get().getDefaultInstance(),0.8 * Num));
            add(new ItemAndRate(ModItems.SilverCoin.get().getDefaultInstance(),15 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.03 * Num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(),0.3 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SilverFish).MobLevel);
        if(!data.contains("KillCountOfSilverfish")) data.putInt("KillCountOfSilverfish",Num);
        else data.putInt("KillCountOfSilverfish",data.getInt("KillCountOfSilverfish")+Num);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,Num);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+Num);
        if(data.contains("DailyMission20")) data.putInt("DailyMission20",data.getInt("DailyMission20")+Num);
    }
    public static void WitherSkeleton(Player player,int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(Items.COAL.getDefaultInstance(), Num));
            add(new ItemAndRate(ModItems.witherBone.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.04 * Num));
            add(new ItemAndRate(ModItems.NetherHelmetForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherManaHelmetForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(),0.0005 * Num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.WitherSkeleton).MobLevel);
        if(!data.contains("KillCountOfWitherSkeleton")) data.putInt("KillCountOfWitherSkeleton",1);
        else data.putInt("KillCountOfWitherSkeleton",data.getInt("KillCountOfWitherSkeleton")+1);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(data.contains("DailyMission14")) data.putInt("DailyMission14",data.getInt("DailyMission14")+1);

        Random r = new Random();
        if(Utils.netherMobSpawn == 1 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack4);
        }
    }
    public static void Piglin(Player player,int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.PigLinSoul.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.04 * Num));
            add(new ItemAndRate(ModItems.NetherBootsForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherManaBootsForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(),0.0005 * Num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Piglin).MobLevel);
        if(!data.contains("KillCountOfZombiePigLin")) data.putInt("KillCountOfZombiePigLin",1);
        else data.putInt("KillCountOfZombiePigLin",data.getInt("KillCountOfZombiePigLin")+1);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(data.contains("DailyMission15")) data.putInt("DailyMission15",data.getInt("DailyMission15")+1);

        Random r = new Random();
        if(Utils.netherMobSpawn == 2 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack4);
        }
    }
    public static void NetherSkeleton(Player player,int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.netherbonemeal.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.04 * Num));
            add(new ItemAndRate(ModItems.NetherChestForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherManaChestForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(),0.0005 * Num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.NetherSkeleton).MobLevel);
        if(!data.contains("KillCountOfNetherSkeleton")) data.putInt("KillCountOfNetherSkeleton",1);
        else data.putInt("KillCountOfNetherSkeleton",data.getInt("KillCountOfNetherSkeleton")+1);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(data.contains("DailyMission16")) data.putInt("DailyMission16",data.getInt("DailyMission16")+1);

        Random r = new Random();
        if(Utils.netherMobSpawn == 3 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack4);
        }
    }
    public static void NetherMagma(Player player,int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.NetherMagmaPower.get().getDefaultInstance(),0.5 * Num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.04 * Num));
            add(new ItemAndRate(ModItems.NetherLeggingsForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherManaLeggingsForgeDraw.get().getDefaultInstance(),0.001 * Num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(),0.0005 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.NetherMagma).MobLevel);
        if(!data.contains("KillCountOfNetherMagma")) data.putInt("KillCountOfNetherMagma",1);
        else data.putInt("KillCountOfNetherMagma",data.getInt("KillCountOfNetherMagma")+1);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(data.contains("DailyMission17")) data.putInt("DailyMission17",data.getInt("DailyMission17")+1);

        Random r = new Random();
        if(Utils.netherMobSpawn == 4 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack4);
        }
    }
    public static void EnderMan(Player player,int Num){
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.RecallPiece.get().getDefaultInstance(),0.25 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.08 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EnderMan).MobLevel);
        if(!data.contains("KillCountOfEnderMan")) data.putInt("KillCountOfEnderMan",1);
        else data.putInt("KillCountOfEnderMan",data.getInt("KillCountOfEnderMan")+1);
        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(data.contains("DailyMission21")) data.putInt("DailyMission21",data.getInt("DailyMission21")+1);
    }

    public static void SakuraMob(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SakuraPetal.get().getDefaultInstance(),0.25 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.1 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SakuraMob).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.SakuraMob)) data.putInt(StringUtils.KillCount.SakuraMob,1);
        else data.putInt(StringUtils.KillCount.SakuraMob,data.getInt(StringUtils.KillCount.SakuraMob)+1);
    }
    public static void ScareCrow(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.Wheat.get().getDefaultInstance(),0.25 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.1 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Scarecrow).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.Scarecrow)) data.putInt(StringUtils.KillCount.Scarecrow,1);
        else data.putInt(StringUtils.KillCount.Scarecrow,data.getInt(StringUtils.KillCount.Scarecrow)+1);
    }
    public static void MineWorker(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.PurpleIronPiece.get().getDefaultInstance(),4 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.3 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.1 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.MineWorker).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.MineWorker)) data.putInt(StringUtils.KillCount.MineWorker,1);
        else data.putInt(StringUtils.KillCount.MineWorker,data.getInt(StringUtils.KillCount.MineWorker)+1);

        Compute.FormatMSGSend(player,Component.literal("紫晶铁矿").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron),
                Component.literal("你从紫晶铁矿工习得的技艺愈发成熟").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal("(" + data.getInt(StringUtils.KillCount.MineWorker) + ")").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));

        if (data.getInt(StringUtils.KillCount.MineWorker) == 200) Compute.ItemStackGive(player,ModItems.PurpleIronPickaxe0.get().getDefaultInstance());
    }
    public static void IceHunter(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.LeatherSoul.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.1 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.IceHunter).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.IceHunter)) data.putInt(StringUtils.KillCount.IceHunter,1);
        else data.putInt(StringUtils.KillCount.IceHunter,data.getInt(StringUtils.KillCount.IceHunter)+1);
    }
    public static void ShipWorker(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.ShipPiece.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ShipWorker).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.ShipWorker)) data.putInt(StringUtils.KillCount.ShipWorker,1);
        else data.putInt(StringUtils.KillCount.ShipWorker,data.getInt(StringUtils.KillCount.ShipWorker)+1);
    }
    public static void EarthMana(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.EarthManaSoul.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EarthMana).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.EarthMana)) data.putInt(StringUtils.KillCount.EarthMana,1);
        else data.putInt(StringUtils.KillCount.EarthMana,data.getInt(StringUtils.KillCount.EarthMana)+1);

        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && player.level().getDayTime() % 24000 >= 12000) {
            Random random = new Random();
            if (random.nextDouble() < 0.01 && !data.contains(StringUtils.getEarthCurios)) {
                Compute.ItemStackGive(player,ModItems.EarthManaCurios.get().getDefaultInstance());
                data.putBoolean(StringUtils.getEarthCurios,true);
                Compute.FormatBroad(player.level(),Component.literal("旧世魔力").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana),
                        Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("获得了：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(ModItems.EarthManaCurios.get().getDefaultInstance().getDisplayName()));
            }
        }
    }
    public static void BloodMana(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.BloodManaSoul.get().getDefaultInstance(),0.2 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.BloodMana).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.BloodMana)) data.putInt(StringUtils.KillCount.BloodMana,1);
        else data.putInt(StringUtils.KillCount.BloodMana,data.getInt(StringUtils.KillCount.BloodMana)+1);

        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && player.level().getDayTime() % 24000 >= 12000) {
            Random random = new Random();
            if (random.nextDouble() < 0.01 && !data.contains(StringUtils.getBloodCurios)) {
                Compute.ItemStackGive(player,ModItems.BloodManaCurios.get().getDefaultInstance());
                data.putBoolean(StringUtils.getBloodCurios,true);
                Compute.FormatBroad(player.level(),Component.literal("旧世魔力").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana),
                        Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("获得了：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(ModItems.BloodManaCurios.get().getDefaultInstance().getDisplayName()));
            }
        }

    }
    public static void Beacon(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.BeaconSoul.get().getDefaultInstance(),0.5 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Beacon).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.Beacon)) data.putInt(StringUtils.KillCount.Beacon,1);
        else data.putInt(StringUtils.KillCount.Beacon,data.getInt(StringUtils.KillCount.Beacon)+1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getBeaconCurios)) {
            Compute.ItemStackGive(player, ModItems.BeaconBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getBeaconCurios, true);
            Compute.FormatBroad(player.level(), Component.literal("暗黑城堡").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(ModItems.BeaconBracelet.get().getDefaultInstance().getDisplayName()));
        }
    }
    public static void Blaze(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.BlazeSoul.get().getDefaultInstance(),0.5 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Blaze).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.Blaze)) data.putInt(StringUtils.KillCount.Blaze,1);
        else data.putInt(StringUtils.KillCount.Blaze,data.getInt(StringUtils.KillCount.Blaze)+1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getBlazeCurios)) {
            Compute.ItemStackGive(player, ModItems.BlazeBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getBlazeCurios, true);
            Compute.FormatBroad(player.level(), Component.literal("暗黑城堡").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(ModItems.BlazeBracelet.get().getDefaultInstance().getDisplayName()));
        }
    }
    public static void Tree(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.TreeSoul.get().getDefaultInstance(),0.5 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Tree).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.Tree)) data.putInt(StringUtils.KillCount.Tree,1);
        else data.putInt(StringUtils.KillCount.Tree,data.getInt(StringUtils.KillCount.Tree)+1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getTreeCurios)) {
            Compute.ItemStackGive(player, ModItems.TreeBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getTreeCurios, true);
            Compute.FormatBroad(player.level(), Component.literal("暗黑城堡").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(ModItems.TreeBracelet.get().getDefaultInstance().getDisplayName()));
        }

    }
    public static void Slime(Player player, int Num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>(){{
            add(new ItemAndRate(ModItems.SlimeBall.get().getDefaultInstance(),0.35 * Num));
            add(new ItemAndRate(ModItems.GoldCoin.get().getDefaultInstance(),0.4 * Num));
            add(new ItemAndRate(ModItems.GemPiece.get().getDefaultInstance(),0.09 * Num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.Give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.ExpPercentGetAndMSGSend(player,0.02,Compute.PlayerExpUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Slime).MobLevel);

        if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
        else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
        if(!data.contains(StringUtils.KillCount.Slime)) data.putInt(StringUtils.KillCount.Slime,1);
        else data.putInt(StringUtils.KillCount.Slime,data.getInt(StringUtils.KillCount.Slime)+1);
    }

    public static void KillPaper (Player player, String MobName) throws IOException {
        if (MobName.equals(StringUtils.MobName.PlainZombie)) PlainZombie(player,1);
        if (MobName.equals(StringUtils.MobName.ForestSkeleton)) ForestSkeleton(player,1);
        if (MobName.equals(StringUtils.MobName.ForestZombie)) ForestZombie(player,1);
        if (MobName.equals(StringUtils.MobName.LakeDrowned)) LakeDrown(player,1);
        if (MobName.equals(StringUtils.MobName.VolcanoBlaze)) VolcanoBlaze(player,1);
        if (MobName.equals(StringUtils.MobName.MineZombie)) Mine(player,1,false);
        if (MobName.equals(StringUtils.MobName.MineSkeleton)) Mine(player,1,false);
        if (MobName.equals(StringUtils.MobName.FieldZombie)) Field(player,1);
        if (MobName.equals(StringUtils.MobName.SnowStray)) SnowStray(player,1);
        if (MobName.equals(StringUtils.MobName.SkyVex)) SkyVex(player,1);
        if (MobName.equals(StringUtils.MobName.Evoker)) Evoker(player,1);
        if (MobName.equals(StringUtils.MobName.EvokerMaster)) EvokerMaster(player,1);
        if (MobName.equals(StringUtils.MobName.SeaGuardian)) SeaGuardian(player,1);
        if (MobName.equals(StringUtils.MobName.LightingZombie)) LightingZombie(player,1);
        if (MobName.equals(StringUtils.MobName.Husk)) Husk(player,1);
        if (MobName.equals(StringUtils.MobName.KazeZombie)) Kaze(player,1);
        if (MobName.equals(StringUtils.MobName.Spider)) Spider(player,1);
        if (MobName.equals(StringUtils.MobName.SilverFish)) SilverFish(player,1);
        if (MobName.equals(StringUtils.MobName.WitherSkeleton)) WitherSkeleton(player,1);
        if (MobName.equals(StringUtils.MobName.Piglin)) Piglin(player,1);
        if (MobName.equals(StringUtils.MobName.NetherSkeleton)) NetherSkeleton(player,1);
        if (MobName.equals(StringUtils.MobName.NetherMagma)) NetherMagma(player,1);
        if (MobName.equals(StringUtils.MobName.EnderMan)) EnderMan(player,1);
        if (MobName.equals(StringUtils.MobName.SakuraMob)) SakuraMob(player,1);
        if (MobName.equals(StringUtils.MobName.Scarecrow)) ScareCrow(player,1);
        if (MobName.equals(StringUtils.MobName.MineWorker)) MineWorker(player,1);
        if (MobName.equals(StringUtils.MobName.ShipWorker)) ShipWorker(player,1);
        if (MobName.equals(StringUtils.MobName.IceHunter)) IceHunter(player,1);
        if (MobName.equals(StringUtils.MobName.EarthMana)) EarthMana(player,1);
        if (MobName.equals(StringUtils.MobName.BloodMana)) BloodMana(player,1);
        if (MobName.equals(StringUtils.MobName.Slime)) Slime(player,1);

    }

}
