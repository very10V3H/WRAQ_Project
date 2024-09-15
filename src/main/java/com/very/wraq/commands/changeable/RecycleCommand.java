package com.very.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.blocks.blocks.forge.ForgeRecipe;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
        if (item.equals(ModItems.huskSword1.get()) || item.equals(ModItems.huskSword2.get())
                || item.equals(ModItems.huskSword3.get())) item = ModItems.huskSword0.get();

        if (ForgeRecipe.forgeDrawRecipe.containsKey(item)) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player, true);
                Compute.sendFormatMSG(player, Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.WHITE));
            } else {
                List<ItemStack> itemStackList = ForgeRecipe.forgeDrawRecipe.get(item);
                itemStackList.forEach(itemStack1 -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(itemStack1.getItem(), (itemStack1.getCount() + 1) / 2));
                });
                Compute.formatBroad(player.level(), Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 回收了:").withStyle(ChatFormatting.GOLD)).
                                append(itemStack.getDisplayName()));
                Compute.playerItemUseWithRecord(player);
                Utils.playerRecycleMap.put(player, false);
            }
        }

        if (itemStack.is(ModItems.PurpleIronSword.get())
                || itemStack.is(ModItems.PurpleIronBow.get())
                || itemStack.is(ModItems.PurpleIronSceptre.get())) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player, true);
                Compute.sendFormatMSG(player, Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.WHITE));
            } else {
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud2.get(), 1));
                Compute.formatBroad(player.level(), Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 回收了:").withStyle(ChatFormatting.GOLD)).
                                append(itemStack.getDisplayName()));
                Compute.playerItemUseWithRecord(player);
                Utils.playerRecycleMap.put(player, false);
            }
        }

        return 0;
    }
}
