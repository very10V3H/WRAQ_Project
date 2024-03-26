package com.Very.very.Hcommand;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.Files.MarketPlayerInfo;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Iterator;

public class SellCommand implements Command<CommandSourceStack> {
    public static SellCommand instance = new SellCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String string = StringArgumentType.getString(context, "price");
        double price = Double.parseDouble(string);
        ServerPlayer player = context.getSource().getPlayer();
        if (price <= 0 || price > 10000000) {
            Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("需要一个合理的价格喔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            return 0;
        }
        Iterator<MarketItemInfo> iterator1 = Utils.MarketInfo.iterator();
        int Count = 0;
        while (iterator1.hasNext()) {
            MarketItemInfo marketItemInfo = iterator1.next();
            if (marketItemInfo.getPlayer().equals(player.getName().getString())) Count++;
        }
        if (Count >= 5) {
            Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("超出了限制的出售数量(5)。"));
        } else {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            boolean flag = false;
            if (itemStack.is(Items.POTION)) {
                itemStack.getOrCreateTag().getString("Potion");
                if (Utils.PotionMap.isEmpty()) Utils.PotionMapInit();
                if (Utils.PotionMap.containsKey(itemStack.getOrCreateTag().getString("Potion"))) {
                    flag = true;
                    MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(), itemStack.getCount() + " " + itemStack.getOrCreateTag().getString("Potion"), price);
                    Utils.MarketInfo.add(marketItemInfo);
                    player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
                    Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("上架成功。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    Compute.FormatBroad(player.level(),Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("以").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format(" %.1f ",price)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                    append(Component.literal("的价格出售了 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()));

                }
            } else {
                MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(), itemStack.toString(), price);
                if (Utils.itemStackMap.containsKey(marketItemInfo.getItemStackName())) {
                    flag = true;
                    Utils.MarketInfo.add(marketItemInfo);
                    player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
                    Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("上架成功。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    Compute.FormatBroad(player.level(),Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("以").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format(" %.1f ",price)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                    append(Component.literal("的价格出售了 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()));
                }
            }
            if (!flag) {
                Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("这件物品似乎不可以出售。"));
            }
        }
        return 0;
    }
}
