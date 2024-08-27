package com.very.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.files.MarketItemInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;
import java.util.List;

public class SellCommand implements Command<CommandSourceStack> {
    public static SellCommand instance = new SellCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        String string = StringArgumentType.getString(context, "price");
        if (string.length() > 10) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("需要一个合理的价格喔").withStyle(ChatFormatting.WHITE));
            return 0;
        }

        double price = Double.parseDouble(string);
        List<Villager> villagerList = player.level().getEntitiesOfClass(Villager.class, AABB.ofSize(player.position(), 10, 10, 10));
        if (villagerList.isEmpty()) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("附近需要有村民才能出售物品").withStyle(ChatFormatting.WHITE));
            return 0;
        }
        if (price <= 0 || price > 10000000) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("需要一个合理的价格喔").withStyle(ChatFormatting.WHITE));
            return 0;
        }
        Iterator<MarketItemInfo> iterator1 = Utils.marketItemInfos.iterator();
        int Count = 0;
        while (iterator1.hasNext()) {
            MarketItemInfo marketItemInfo = iterator1.next();
            if (marketItemInfo.getPlayer().equals(player.getName().getString())) Count++;
        }
        if (Count >= 15 && !player.isCreative()) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("超出了限制的出售数量(15)。"));
        } else {
            boolean flag = false;
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!InventoryCheck.getBoundingList().contains(itemStack.getItem())) InventoryCheck.removeOwnerTag(player, itemStack);
            MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(), itemStack, price);

            if (!itemStack.is(Items.AIR) && MarketItemInfo.itemCanBeSold(itemStack)) {
                flag = true;
                Utils.marketItemInfos.add(marketItemInfo);
                player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
                Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("上架成功。").withStyle(ChatFormatting.WHITE));
                Compute.formatBroad(player.level(), Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("以").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(String.format(" %.1f ", price)).withStyle(ChatFormatting.GOLD)).
                                append(Component.literal("的价格出售了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                LogUtils.getLogger().info("市场 {} 以 {} 出售了 {}", player.getName().getString(), price, itemStack);
            }

            if (!flag) {
                Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("这件物品似乎不可以出售。"));
            }
        }
        return 0;
    }

    public String getItemStackName(ItemStack itemStack) {
        String itemStackName = itemStack.toString();
        for (int i = 0; i < itemStackName.length(); i++) {
            if (itemStackName.charAt(i) == ' ') {
                return itemStackName.substring(i + 1);
            }
        }
        return " ";
    }
}
