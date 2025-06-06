package fun.wraq.process.system.spur.events;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class FishEvent {

    public static String seaPieceGetTimes = "seaPieceGetTimes";

    @SubscribeEvent
    public static void fish(ItemFishedEvent event) {
        ItemStack dropItem = event.getDrops().get(0);
        event.getDrops().forEach(stack -> {
            stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        });
        event.getDrops().set(0, dropItem);
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        Random r = new Random();
        CompoundTag data = player.getPersistentData();
        if (r.nextDouble() < 0.1) {
            data.putInt(seaPieceGetTimes, data.getInt(seaPieceGetTimes) + 1);
            InventoryOperation.giveItemStack(player, new ItemStack(SpurItems.SEA_PIECE.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.SEA_PIECE.get()), 0.1)) {
            data.putInt(seaPieceGetTimes, data.getInt(seaPieceGetTimes) + 1);
        }

        if (!data.contains("FishCount")) data.putInt("FishCount", 1);
        else data.putInt("FishCount", data.getInt("FishCount") + 1);
        int RodLevel = data.getInt("FishCount");
        ItemStack GoldCoin = ModItems.GOLD_COIN.get().getDefaultInstance();
        ItemStack SilverCoin = ModItems.SILVER_COIN.get().getDefaultInstance();
        ItemStack SeaSoul = ModItems.SEA_SOUL.get().getDefaultInstance();
        Utils.dayFishCount.put(player.getName().getString(), Utils.dayFishCount.getOrDefault(player.getName().getString(), 0) + 1);
        double rateEnhance = Compute.playerExHarvest(player);
        if (RodLevel > 2000) {
            double rate = 0.07 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 50);
        } else if (RodLevel > 1000) {
            double rate = 0.06 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 40);
        } else if (RodLevel > 500) {
            double rate = 0.05 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 30);
        } else if (RodLevel > 200) {
            double rate = 0.04 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 20);
        } else if (RodLevel > 100) {
            double rate = 0.03 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 15);
        } else if (RodLevel > 50) {
            double rate = 0.02 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 10);
        } else if (RodLevel > 20) {
            double rate = 0.01 * (1 + rateEnhance);
            if (r.nextDouble() < rate) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, GoldCoin);
            }
            if (r.nextDouble() < rate * 2) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
            if (r.nextDouble() < rate) {
                InventoryOperation.giveItemStack(player, SeaSoul);
            }
            Compute.givePercentExpToPlayer(player, 0.05, 0, 5);
        } else {
            Compute.givePercentExpToPlayer(player, 0.05, 0, 3);
            if (r.nextDouble() < 0.01) {
                Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                InventoryOperation.giveItemStack(player, SilverCoin);
            }
        }
        Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                Component.literal("当前钓鱼熟练度为: " + RodLevel));
        if (RodLevel >= 2000) {

        } else if (RodLevel > 1000) {
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
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("钓鱼", CustomStyle.styleOfSea), content);
    }
}
