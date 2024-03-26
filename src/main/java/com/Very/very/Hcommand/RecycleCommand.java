package com.Very.very.Hcommand;

import com.Very.very.Files.FileHandler;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.util.List;

public class RecycleCommand implements Command<CommandSourceStack> {
    public static RecycleCommand instance = new RecycleCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
        if (item.equals(ModItems.SeaSword1.get()) || item.equals(ModItems.SeaSword2.get())
                || item.equals(ModItems.SeaSword3.get())) item = ModItems.SeaSword0.get();
        if (item.equals(ModItems.BlackForestSword1.get()) || item.equals(ModItems.BlackForestSword2.get())
                || item.equals(ModItems.BlackForestSword3.get())) item = ModItems.BlackForestSword0.get();

        if (Utils.ForgeDrawRecipe.containsKey(item)) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player,true);
                Compute.FormatMSGSend(player,Component.literal("回收").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                List<ItemStack> itemStackList = Utils.ForgeDrawRecipe.get(item);
                itemStackList.forEach(itemStack1 -> {
                    try {
                        Compute.ItemStackGive(player,new ItemStack(itemStack1.getItem(),(itemStack1.getCount() + 1) / 2));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                Compute.FormatBroad(player.level() ,Component.literal("回收").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 回收了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                append(itemStack.getDisplayName()));
                try {
                    Compute.PlayerItemUseWithRecord(player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Utils.playerRecycleMap.put(player,false);
            }
        }

        if (itemStack.is(ModItems.PurpleIronSword.get())
                || itemStack.is(ModItems.PurpleIronBow.get())
                || itemStack.is(ModItems.PurpleIronSceptre.get())) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player,true);
                Compute.FormatMSGSend(player,Component.literal("回收").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                try {
                    Compute.ItemStackGive(player,new ItemStack(ModItems.PurpleIronBud2.get(),1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Compute.FormatBroad(player.level() ,Component.literal("回收").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 回收了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                append(itemStack.getDisplayName()));
                try {
                    Compute.PlayerItemUseWithRecord(player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Utils.playerRecycleMap.put(player,false);
            }
        }

        return 0;
    }
}
