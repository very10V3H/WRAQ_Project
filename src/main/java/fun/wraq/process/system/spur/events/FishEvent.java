package fun.wraq.process.system.spur.events;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class FishEvent {

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
        incrementExp(player);
        ItemStack goldCoin = ModItems.GOLD_COIN.get().getDefaultInstance();
        ItemStack silverCoin = ModItems.SILVER_COIN.get().getDefaultInstance();
        ItemStack seaSoul = ModItems.SEA_SOUL.get().getDefaultInstance();
        Utils.dayFishCount.put(player.getName().getString(),
                Utils.dayFishCount.getOrDefault(player.getName().getString(), 0) + 1);
        double rateEnhance = Compute.playerExHarvest(player);
        double rate = getLevel(player) * 0.01 * (1 + rateEnhance);
        if (r.nextDouble() < rate) {
            Compute.sendFormatMSG(player, Component.literal("钓鱼").withStyle(CustomStyle.styleOfSea),
                    Component.literal("你通过钓鱼额外获得了:").append(goldCoin.getDisplayName()).append(Component.literal("*1")));
            InventoryOperation.giveItemStack(player, goldCoin);
        }
        if (r.nextDouble() < rate * 2) {
            InventoryOperation.giveItemStack(player, silverCoin);
        }
        if (r.nextDouble() < rate) {
            InventoryOperation.giveItemStack(player, seaSoul);
        }
        Compute.givePercentExpToPlayer(player, 0.05, 0, Math.min(player.experienceLevel, 50));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("钓鱼", CustomStyle.styleOfSea), content);
    }

    public static void handleTick(Player player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof FishingRodItem && player.tickCount % 5 == 1) {
            int exp = getExp(player);
            int level = getLevel(player);
            Compute.sendActionBarTextContentToPlayer(player,
                    Te.s("钓鱼经验 ", CustomStyle.styleOfSea, exp, ChatFormatting.GRAY,
                            "  钓鱼等级 ", CustomStyle.styleOfSea, level, ChatFormatting.LIGHT_PURPLE));
        }
    }

    public static String seaPieceGetTimes = "seaPieceGetTimes";
    public static final String EXP_DATA_KEY = "FishCount";
    public static int getExp(Player player) {
        return player.getPersistentData().getInt(EXP_DATA_KEY);
    }

    public static void incrementExp(Player player) {
        player.getPersistentData().putInt(EXP_DATA_KEY, getExp(player) + 1);
    }

    public static int getLevel(Player player) {
        int rodExp = getExp(player);
        if (rodExp < 20) {
            return 0;
        } else if (rodExp < 50) {
            return 1;
        } else if (rodExp < 100) {
            return 2;
        } else if (rodExp < 200) {
            return 3;
        } else if (rodExp < 500) {
            return 4;
        } else if (rodExp < 1000) {
            return 5;
        } else if (rodExp < 2000) {
            return 6;
        } else {
            return 7;
        }
    }
}
