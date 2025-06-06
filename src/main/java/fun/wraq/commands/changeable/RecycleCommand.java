package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
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
        if (item.equals(ModItems.SEA_SWORD_1.get()) || item.equals(ModItems.SEA_SWORD_2.get())
                || item.equals(ModItems.SEA_SWORD_3.get())) item = ModItems.SEA_SWORD_0.get();
        if (item.equals(ModItems.HUSK_SWORD_1.get()) || item.equals(ModItems.HUSK_SWORD_2.get())
                || item.equals(ModItems.HUSK_SWORD_3.get())) item = ModItems.HUSK_SWORD_0.get();

        if (ForgeRecipe.recipes.containsKey(item)) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player, true);
                Compute.sendFormatMSG(player, Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.WHITE));
            } else {
                List<ItemStack> itemStackList = ForgeRecipe.recipes.get(item);
                itemStackList.forEach(itemStack1 -> {
                    InventoryOperation.giveItemStack(player, new ItemStack(itemStack1.getItem(), (itemStack1.getCount() + 1) / 2));
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

        if (itemStack.is(ModItems.PURPLE_IRON_SWORD.get())
                || itemStack.is(ModItems.PURPLE_IRON_BOW.get())
                || itemStack.is(ModItems.PURPLE_IRON_SCEPTRE.get())) {
            if (!Utils.playerRecycleMap.containsKey(player)
                    || !Utils.playerRecycleMap.get(player)) {
                Utils.playerRecycleMap.put(player, true);
                Compute.sendFormatMSG(player, Component.literal("回收").withStyle(ChatFormatting.GOLD),
                        Component.literal(" 再次输入指令确定回收！").withStyle(ChatFormatting.WHITE));
            } else {
                InventoryOperation.giveItemStack(player, new ItemStack(ModItems.PURPLE_IRON_BUD_2.get(), 1));
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
