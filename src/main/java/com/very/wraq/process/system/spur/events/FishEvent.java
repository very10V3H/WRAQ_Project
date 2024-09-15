package com.very.wraq.process.system.spur.events;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.system.spur.Items.SpurItems;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Random;

@Mod.EventBusSubscriber
public class FishEvent {

    public static String seaPieceGetTimes = "seaPieceGetTimes";

    @SubscribeEvent
    public static void fish(ItemFishedEvent event) throws IOException {
        ItemStack Fished = event.getDrops().get(0);
        event.getDrops().set(0, Fished);
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        Random r = new Random();
        CompoundTag data = player.getPersistentData();
        if (r.nextDouble() < 0.1) {
            data.putInt(seaPieceGetTimes, data.getInt(seaPieceGetTimes) + 1);
            InventoryOperation.itemStackGive(player, new ItemStack(SpurItems.seaPiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.seaPiece.get()), 0.1)) {
            data.putInt(seaPieceGetTimes, data.getInt(seaPieceGetTimes) + 1);
        }

        if (!data.contains("FishCount")) data.putInt("FishCount", 1);
        else data.putInt("FishCount", data.getInt("FishCount") + 1);
        int RodLevel = data.getInt("FishCount");
        ItemStack GoldCoin = ModItems.goldCoin.get().getDefaultInstance();
        ItemStack SilverCoin = ModItems.silverCoin.get().getDefaultInstance();
        ItemStack SeaSoul = ModItems.SeaSoul.get().getDefaultInstance();
        Utils.dayFishCount.put(player.getName().getString(), Utils.dayFishCount.getOrDefault(player.getName().getString(), 0) + 1);
        LabourDayMission.count(player, LabourDayMission.fishCounts);
        double rateEnhance = Compute.playerExHarvest(player);
        if (RodLevel > 2000) {
            double rate = 0.07 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 50);
        } else if (RodLevel > 1000) {
            double rate = 0.06 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 40);
        } else if (RodLevel > 500) {
            double rate = 0.05 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 30);
        } else if (RodLevel > 200) {
            double rate = 0.04 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 20);
        } else if (RodLevel > 100) {
            double rate = 0.03 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 15);
        } else if (RodLevel > 50) {
            double rate = 0.02 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 10);
        } else if (RodLevel > 20) {
            double rate = 0.01 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.itemStackGive(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 5);
        } else {
            Compute.givePercentExpToPlayer(player, 0.05, 0, 3);
            if (r.nextDouble() < 0.01) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.itemStackGive(player, SilverCoin);
            }
        }
        Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                Component.literal("当前钓鱼熟练度为: " + RodLevel));
        if (RodLevel > 1000 && RodLevel < 2000) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (2000 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else if (RodLevel > 500) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (1000 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else if (RodLevel > 200) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (500 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else if (RodLevel > 100) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (200 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else if (RodLevel > 50) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (100 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else if (RodLevel > 20) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (50 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        } else {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" " + (20 - RodLevel)).withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal("熟练度。")));
        }
        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && player.getX() < 2039 && player.getZ() < 1115 && player.getX() > 1940 && player.getZ() > 1052) {
            if (r.nextDouble() <= 0.005 || data.getInt(StringUtils.ShipFishTimes) == 199) {
                ItemStack[] itemStacks = {
                        new ItemStack(ModItems.ShipSwordForgeDraw.get()),
                        new ItemStack(ModItems.ShipBowForgeDraw.get()),
                        new ItemStack(ModItems.ShipSceptreForgeDraw.get())
                };
                ItemStack itemStack = itemStacks[r.nextInt(3)];
                Compute.formatBroad(player.level(), Component.literal("打捞").withStyle(CustomStyle.styleOfShip),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("在").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("废旧船厂").withStyle(CustomStyle.styleOfShip)).
                                append(Component.literal("成功打捞到了:").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                InventoryOperation.itemStackGive(player, itemStack);
                if (data.contains(StringUtils.ShipFishTimes)) data.putInt(StringUtils.ShipFishTimes, 1);
            } else {
                data.putInt(StringUtils.ShipFishTimes, data.getInt(StringUtils.ShipFishTimes) + 1);
                Compute.sendFormatMSG(player, Component.literal("打捞").withStyle(CustomStyle.styleOfShip),
                        Component.literal("你已经打捞了").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + data.getInt(StringUtils.ShipFishTimes)).withStyle(CustomStyle.styleOfShip)).
                                append(Component.literal("次都没有发现废弃船厂遗物了。").withStyle(ChatFormatting.WHITE)));
            }
        }
    }
}
