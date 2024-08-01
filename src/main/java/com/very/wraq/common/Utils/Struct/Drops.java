package com.very.wraq.common.Utils.Struct;

import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
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
    public static void PlainZombie(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PlainSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 2 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.PlainCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.PlainCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.PlainCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.PlainCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02 * num, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.PlainZombie).MobLevel);
        KillCount(data, "KillCountOfPlainZombie");
        DailyMission.addCount(player, DailyMission.plainZombieKillCountMap);
    }

    public static void ForestSkeleton(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ForestSoul.get().getDefaultInstance(), 0.6 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 2 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.ForestCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.ForestCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.ForestCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.ForestCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ForestSkeleton).MobLevel);
        KillCount(data, "KillCountOfForestSkeleton");
    }

    public static void ForestZombie(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ForestSoul.get().getDefaultInstance(), 0.9 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.ForestCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.ForestCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.ForestCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.ForestCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ForestZombie).MobLevel);
        KillCount(data, "KillCountOfForestZombie");
    }

    public static void LakeDrown(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LakeSoul.get().getDefaultInstance(), 0.9 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.25 * num));
            add(new ItemAndRate(ModItems.LakeCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.LakeCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.LakeCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.LakeCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LakeDrowned).MobLevel);
        KillCount(data, "KillCountOfLakeDrowned");
    }

    public static void VolcanoBlaze(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.VolcanoSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.VolcanoCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.VolcanoCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.VolcanoCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.VolcanoCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.VolcanoBlaze).MobLevel);
        KillCount(data, "KillCountOfVolcanoBlazw");
    }

    public static void Mine(Player player, double num, boolean IsAttackKill) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.MineSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.MineSoul1.get().getDefaultInstance(), 0.05 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.MineCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.MineCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.MineCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.MineCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.MineZombie).MobLevel);
        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, (int) num);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + (int) num);

        if (!IsAttackKill) {
            Random random = new Random();
            int Killnum = random.nextInt((int) num);
            if (!data.contains("KillCountOfMineZombie")) data.putInt("KillCountOfMineZombie", Killnum);
            else data.putInt("KillCountOfMineZombie", data.getInt("KillCountOfMineZombie") + Killnum);
            ;
            if (!data.contains("KillCountOfMineSkeleton")) data.putInt("KillCountOfMineSkeleton", (int) num - Killnum);
            else data.putInt("KillCountOfMineSkeleton", data.getInt("KillCountOfMineSkeleton") + (int) num - Killnum);
            ;
        }
    }

    public static void Field(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.FieldSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.FieldZombie).MobLevel);
        KillCount(data, "KillCountOfFeildZombie");
    }

    public static void SnowStray(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SnowSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 10 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.SnowCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.SnowCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.SnowCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.SnowCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.IceElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SnowStray).MobLevel);
        KillCount(data, "KillCountOfSnowStray");
        DailyMission.addCount(player, DailyMission.dreadHoundKillCountMap);
    }

    public static void SkyVex(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SkySoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 10 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.SkyCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.SkyCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.SkyCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.SkyCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.SkyHelmetForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.SkyChestForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.SkyLeggingsForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.SkyBootsForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.WindElementPiece0.get().getDefaultInstance(), 0.2 * num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SkyVex).MobLevel);
        KillCount(data, "KillCountOfVex");
    }

    public static void Evoker(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EvokerSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.ManaCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.ManaCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.ManaCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.ManaCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.LightningElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Evoker).MobLevel);
        KillCount(data, "KillCountOfEvoker");
    }

    public static void EvokerMaster(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EvokerSoul.get().getDefaultInstance(), 4 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.ManaCrest0.get().getDefaultInstance(), 0.02 * num));
            add(new ItemAndRate(ModItems.ManaCrest1.get().getDefaultInstance(), 0.005 * num));
            add(new ItemAndRate(ModItems.ManaCrest2.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.ManaCrest3.get().getDefaultInstance(), 0.0002 * num));
            add(new ItemAndRate(ModItems.LightningElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EvokerMaster).MobLevel);
        KillCount(data, "KillCountOfEvokerMaster");
    }

    public static void SeaGuardian(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SeaSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.SeaSwordForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.SeaManaCoreForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.SeaBowForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SeaGuardian).MobLevel);
        KillCount(data, "KillCountOfGuardian");
    }

    public static void LightingZombie(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LightningSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.IslandHelmetForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.IslandChestForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.IslandLeggingsForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.IslandBootsForgeDraw.get().getDefaultInstance(), 0.00025 * num));
            add(new ItemAndRate(ModItems.LightningElementPiece0.get().getDefaultInstance(), 0.2 * num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LightingZombie).MobLevel);
        KillCount(data, "KillCountOfLZ");
    }

    public static void Husk(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.huskSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.BlackForestSwordForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.BlackForestManaCoreForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.SeaBowForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Husk).MobLevel);
        KillCount(data, "KillCountOfBFHusk");
    }

    public static void Kaze(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.KazeSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.KazeSwordForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.KazeBootsForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.KazeManaCoreForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.WindElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.KazeZombie).MobLevel);
        KillCount(data, "KillCountOfKaze");
    }

    public static void Spider(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SpiderSoul.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Spider).MobLevel);
        KillCount(data, "KillCountOfSpider");
    }

    public static void SilverFish(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.toEnd.get().getDefaultInstance(), 0.8 * num));
            add(new ItemAndRate(ModItems.silverCoin.get().getDefaultInstance(), 15 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.03 * num));
            add(new ItemAndRate(ModItems.RunePiece.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SilverFish).MobLevel);
        KillCount(data, "KillCountOfSilverfish");
    }

    public static void WitherSkeleton(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(Items.COAL.getDefaultInstance(), num));
            add(new ItemAndRate(ModItems.witherSkeletonSoul.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.04 * num));
            add(new ItemAndRate(ModItems.NetherHelmetForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherManaHelmetForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.WitherSkeleton).MobLevel);
        KillCount(data, "KillCountOfWitherSkeleton");

        Random r = new Random();
        if (Utils.netherMobSpawn == 1 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.itemStackGive(player, itemStack4);
        }
    }

    public static void Piglin(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PigLinSoul.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.04 * num));
            add(new ItemAndRate(ModItems.NetherBootsForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherManaBootsForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Piglin).MobLevel);
        KillCount(data, "KillCountOfZombiePigLin");

        Random r = new Random();
        if (Utils.netherMobSpawn == 2 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.itemStackGive(player, itemStack4);
        }
    }

    public static void NetherSkeleton(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.netherSkeletonSoul.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.04 * num));
            add(new ItemAndRate(ModItems.NetherChestForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherManaChestForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));

        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.NetherSkeleton).MobLevel);
        KillCount(data, "KillCountOfNetherSkeleton");

        Random r = new Random();
        if (Utils.netherMobSpawn == 3 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.itemStackGive(player, itemStack4);
        }
    }

    public static void NetherMagma(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.magmaSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.Ruby.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.04 * num));
            add(new ItemAndRate(ModItems.NetherLeggingsForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherManaLeggingsForgeDraw.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance(), 0.0005 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.NetherMagma).MobLevel);
        KillCount(data, "KillCountOfNetherMagma");

        Random r = new Random();
        if (Utils.netherMobSpawn == 4 && r.nextDouble(1.0d) < 0.3) {
            ItemStack itemStack4 = ModItems.NetherQuartz.get().getDefaultInstance();
            Compute.itemStackGive(player, itemStack4);
        }
    }

    public static void EnderMan(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.RecallPiece.get().getDefaultInstance(), 0.25 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.08 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EnderMan).MobLevel);
        KillCount(data, "KillCountOfEnderMan");
    }

    public static void SakuraMob(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SakuraPetal.get().getDefaultInstance(), 0.25 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.SakuraMob).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.SakuraMob)) data.putInt(StringUtils.KillCount.SakuraMob, 1);
        else data.putInt(StringUtils.KillCount.SakuraMob, data.getInt(StringUtils.KillCount.SakuraMob) + 1);

        DailyMission.addCount(player, DailyMission.sakuraMobKillCountMap);
    }

    public static void ScareCrow(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.Wheat.get().getDefaultInstance(), 0.25 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Scarecrow).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Scarecrow)) data.putInt(StringUtils.KillCount.Scarecrow, 1);
        else data.putInt(StringUtils.KillCount.Scarecrow, data.getInt(StringUtils.KillCount.Scarecrow) + 1);
    }

    public static void MineWorker(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PurpleIronPiece.get().getDefaultInstance(), 4 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.3 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.MineWorker).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.MineWorker)) data.putInt(StringUtils.KillCount.MineWorker, 1);
        else data.putInt(StringUtils.KillCount.MineWorker, data.getInt(StringUtils.KillCount.MineWorker) + 1);

        Compute.sendFormatMSG(player, Component.literal("紫晶铁矿").withStyle(CustomStyle.styleOfPurpleIron),
                Component.literal("你从紫晶铁矿工习得的技艺愈发成熟").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("(" + data.getInt(StringUtils.KillCount.MineWorker) + ")").withStyle(CustomStyle.styleOfPurpleIron)));

        if (data.getInt(StringUtils.KillCount.MineWorker) == 200)
            Compute.itemStackGive(player, ModItems.PurpleIronPickaxe0.get().getDefaultInstance());
    }

    public static void IceHunter(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LeatherSoul.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
            add(new ItemAndRate(ModItems.IceElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.IceHunter).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.IceHunter)) data.putInt(StringUtils.KillCount.IceHunter, 1);
        else data.putInt(StringUtils.KillCount.IceHunter, data.getInt(StringUtils.KillCount.IceHunter) + 1);
    }

    public static void ShipWorker(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ShipPiece.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.WaterElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.ShipWorker).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.ShipWorker)) data.putInt(StringUtils.KillCount.ShipWorker, 1);
        else data.putInt(StringUtils.KillCount.ShipWorker, data.getInt(StringUtils.KillCount.ShipWorker) + 1);
    }

    public static void EarthMana(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EarthManaSoul.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EarthMana).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.EarthMana)) data.putInt(StringUtils.KillCount.EarthMana, 1);
        else data.putInt(StringUtils.KillCount.EarthMana, data.getInt(StringUtils.KillCount.EarthMana) + 1);

        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && player.level().getDayTime() % 24000 >= 12000) {
            Random random = new Random();
            if (random.nextDouble() < 0.01 && !data.contains(StringUtils.getEarthCurios)) {
                Compute.itemStackGive(player, ModItems.EarthManaCurios.get().getDefaultInstance());
                data.putBoolean(StringUtils.getEarthCurios, true);
                Compute.formatBroad(player.level(), Component.literal("旧世魔力").withStyle(CustomStyle.styleOfBloodMana),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("获得了：").withStyle(ChatFormatting.WHITE)).
                                append(ModItems.EarthManaCurios.get().getDefaultInstance().getDisplayName()));
            }
        }
    }

    public static void BloodMana(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BloodManaSoul.get().getDefaultInstance(), 0.2 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.BloodMana).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.BloodMana)) data.putInt(StringUtils.KillCount.BloodMana, 1);
        else data.putInt(StringUtils.KillCount.BloodMana, data.getInt(StringUtils.KillCount.BloodMana) + 1);

        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && player.level().getDayTime() % 24000 >= 12000) {
            Random random = new Random();
            if (random.nextDouble() < 0.01 && !data.contains(StringUtils.getBloodCurios)) {
                Compute.itemStackGive(player, ModItems.BloodManaCurios.get().getDefaultInstance());
                data.putBoolean(StringUtils.getBloodCurios, true);
                Compute.formatBroad(player.level(), Component.literal("旧世魔力").withStyle(CustomStyle.styleOfBloodMana),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("获得了：").withStyle(ChatFormatting.WHITE)).
                                append(ModItems.BloodManaCurios.get().getDefaultInstance().getDisplayName()));
            }
        }

    }

    public static void Beacon(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BeaconSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Beacon).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Beacon)) data.putInt(StringUtils.KillCount.Beacon, 1);
        else data.putInt(StringUtils.KillCount.Beacon, data.getInt(StringUtils.KillCount.Beacon) + 1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getBeaconCurios)) {
            Compute.itemStackGive(player, ModItems.BeaconBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getBeaconCurios, true);
            Compute.formatBroad(player.level(), Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.WHITE)).
                            append(ModItems.BeaconBracelet.get().getDefaultInstance().getDisplayName()));
        }
    }

    public static void Blaze(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BlazeSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Blaze).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Blaze)) data.putInt(StringUtils.KillCount.Blaze, 1);
        else data.putInt(StringUtils.KillCount.Blaze, data.getInt(StringUtils.KillCount.Blaze) + 1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getBlazeCurios)) {
            Compute.itemStackGive(player, ModItems.BlazeBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getBlazeCurios, true);
            Compute.formatBroad(player.level(), Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.WHITE)).
                            append(ModItems.BlazeBracelet.get().getDefaultInstance().getDisplayName()));
        }
        DailyMission.addCount(player, DailyMission.windSkeletonKillCountMap);
    }

    public static void Tree(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.TreeSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.LightningElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Tree).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Tree)) data.putInt(StringUtils.KillCount.Tree, 1);
        else data.putInt(StringUtils.KillCount.Tree, data.getInt(StringUtils.KillCount.Tree) + 1);

        Random random = new Random();
        if (random.nextDouble() < 0.00025 && !data.contains(StringUtils.getTreeCurios)) {
            Compute.itemStackGive(player, ModItems.TreeBracelet.get().getDefaultInstance());
            data.putBoolean(StringUtils.getTreeCurios, true);
            Compute.formatBroad(player.level(), Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("获得了：").withStyle(ChatFormatting.WHITE)).
                            append(ModItems.TreeBracelet.get().getDefaultInstance().getDisplayName()));
        }

    }

    public static void Slime(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SlimeBall.get().getDefaultInstance(), 0.35 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 0.2 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Slime).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Slime)) data.putInt(StringUtils.KillCount.Slime, 1);
        else data.putInt(StringUtils.KillCount.Slime, data.getInt(StringUtils.KillCount.Slime) + 1);
    }

    public static void Star(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.StarSoul.get().getDefaultInstance(), 0.35 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Star).MobLevel);

        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(StringUtils.KillCount.Star)) data.putInt(StringUtils.KillCount.Star, 1);
        else data.putInt(StringUtils.KillCount.Star, data.getInt(StringUtils.KillCount.Star) + 1);
    }

    public static void Star1(Player player, double num) throws IOException {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.StarRune.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.StarBottleForgeDraw.get().getDefaultInstance(), 0.005 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Star1).MobLevel);
        KillCount(data, StringUtils.MobName.Star1);
    }

    public static void LifeElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LifeElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LifeElement).MobLevel);
        KillCount(data, StringUtils.MobName.LifeElement);
    }

    public static void WindElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.WindElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.WindElement).MobLevel);
        KillCount(data, StringUtils.MobName.WindElement);
    }

    public static void StoneElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.StoneElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.StoneElement).MobLevel);
        KillCount(data, StringUtils.MobName.StoneElement);
    }

    public static void WaterElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.WaterElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.WaterElement).MobLevel);
        KillCount(data, StringUtils.MobName.WaterElement);
    }

    public static void LightningElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LightningElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.LightningElement).MobLevel);
        KillCount(data, StringUtils.MobName.LightningElement);
    }

    public static void FireElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.FireElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.FireElement).MobLevel);
        KillCount(data, StringUtils.MobName.FireElement);
    }

    public static void IceElement(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.IceElementPiece0.get().getDefaultInstance(), 1 * num));
            add(new ItemAndRate(ModItems.RainbowPowder.get().getDefaultInstance(), 0.001 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.1 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.IceElement).MobLevel);
        KillCount(data, StringUtils.MobName.IceElement);
    }

    public static void Shulker(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ShulkerSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
        }};
        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.Shulker).MobLevel);

        KillCount(data, StringUtils.MobName.Shulker);
    }


    public static void Endermite(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        List<ItemAndRate> list = new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EnderMiteSoul.get().getDefaultInstance(), 0.5 * num));
            add(new ItemAndRate(ModItems.goldCoin.get().getDefaultInstance(), 0.4 * num));
            add(new ItemAndRate(ModItems.gemPiece.get().getDefaultInstance(), 0.09 * num));
        }};

        list.forEach(itemAndRate -> {
            try {
                itemAndRate.give(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player),
                (int) MobArmorNum.mobArmorNumHashMap.get(StringUtils.MobName.EnderMite).MobLevel);

        KillCount(data, StringUtils.MobName.EnderMite);
        DailyMission.addCount(player, DailyMission.endermiteKillCountMap);
    }


    public static void KillCount(CompoundTag data, String s) {
        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(s)) data.putInt(s, 1);
        else data.putInt(s, data.getInt(s) + 1);
    }

    public static void KillPaper(Player player, String MobName) throws IOException {
        if (MobName.equals(StringUtils.MobName.PlainZombie)) PlainZombie(player, 1);
        if (MobName.equals(StringUtils.MobName.ForestSkeleton)) ForestSkeleton(player, 1);
        if (MobName.equals(StringUtils.MobName.ForestZombie)) ForestZombie(player, 1);
        if (MobName.equals(StringUtils.MobName.LakeDrowned)) LakeDrown(player, 1);
        if (MobName.equals(StringUtils.MobName.VolcanoBlaze)) VolcanoBlaze(player, 1);
        if (MobName.equals(StringUtils.MobName.MineZombie)) Mine(player, 1, false);
        if (MobName.equals(StringUtils.MobName.MineSkeleton)) Mine(player, 1, false);
        if (MobName.equals(StringUtils.MobName.FieldZombie)) Field(player, 1);
        if (MobName.equals(StringUtils.MobName.SnowStray)) SnowStray(player, 1);
        if (MobName.equals(StringUtils.MobName.SkyVex)) SkyVex(player, 1);
        if (MobName.equals(StringUtils.MobName.Evoker)) Evoker(player, 1);
        if (MobName.equals(StringUtils.MobName.EvokerMaster)) EvokerMaster(player, 1);
        if (MobName.equals(StringUtils.MobName.SeaGuardian)) SeaGuardian(player, 1);
        if (MobName.equals(StringUtils.MobName.LightingZombie)) LightingZombie(player, 1);
        if (MobName.equals(StringUtils.MobName.Husk)) Husk(player, 1);
        if (MobName.equals(StringUtils.MobName.KazeZombie)) Kaze(player, 1);
        if (MobName.equals(StringUtils.MobName.Spider)) Spider(player, 1);
        if (MobName.equals(StringUtils.MobName.SilverFish)) SilverFish(player, 1);
        if (MobName.equals(StringUtils.MobName.WitherSkeleton)) WitherSkeleton(player, 1);
        if (MobName.equals(StringUtils.MobName.Piglin)) Piglin(player, 1);
        if (MobName.equals(StringUtils.MobName.NetherSkeleton)) NetherSkeleton(player, 1);
        if (MobName.equals(StringUtils.MobName.NetherMagma)) NetherMagma(player, 1);
        if (MobName.equals(StringUtils.MobName.EnderMan)) EnderMan(player, 1);
        if (MobName.equals(StringUtils.MobName.SakuraMob)) SakuraMob(player, 1);
        if (MobName.equals(StringUtils.MobName.Scarecrow)) ScareCrow(player, 1);
        if (MobName.equals(StringUtils.MobName.MineWorker)) MineWorker(player, 1);
        if (MobName.equals(StringUtils.MobName.ShipWorker)) ShipWorker(player, 1);
        if (MobName.equals(StringUtils.MobName.IceHunter)) IceHunter(player, 1);
        if (MobName.equals(StringUtils.MobName.EarthMana)) EarthMana(player, 1);
        if (MobName.equals(StringUtils.MobName.BloodMana)) BloodMana(player, 1);
        if (MobName.equals(StringUtils.MobName.Slime)) Slime(player, 1);

        if (MobName.equals(StringUtils.MobName.Beacon)) Beacon(player, 1);
        if (MobName.equals(StringUtils.MobName.Tree)) Tree(player, 1);
        if (MobName.equals(StringUtils.MobName.Blaze)) Blaze(player, 1);
        if (MobName.equals(StringUtils.MobName.Star)) Star(player, 1);
        if (MobName.equals(StringUtils.MobName.LifeElement)) LifeElement(player, 1);
        if (MobName.equals(StringUtils.MobName.WaterElement)) WaterElement(player, 1);
        if (MobName.equals(StringUtils.MobName.FireElement)) FireElement(player, 1);
        if (MobName.equals(StringUtils.MobName.StoneElement)) StoneElement(player, 1);
        if (MobName.equals(StringUtils.MobName.IceElement)) IceElement(player, 1);
        if (MobName.equals(StringUtils.MobName.LightningElement)) LightningElement(player, 1);
        if (MobName.equals(StringUtils.MobName.WindElement)) WindElement(player, 1);

        if (MobName.equals(StringUtils.MobName.Shulker)) Shulker(player, 1);
        if (MobName.equals(StringUtils.MobName.EnderMite)) Endermite(player, 1);

    }

}
