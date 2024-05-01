package com.very.wraq.commands.stable;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class ResetTagCommand implements Command<CommandSourceStack> {
    public static ResetTagCommand instance = new ResetTagCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        ItemStack itemStack = player.getMainHandItem();
        Item item = itemStack.getItem();
        if (!(Utils.MainHandTag.containsKey(item) || Utils.ArmorTag.containsKey(item)
                || Utils.CuriosList.contains(item) || Utils.PassiveEquipTag.containsKey(item))) {
            ItemStack newItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
            Compute.FormatMSGSend(player, Component.literal("重置").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("已为 ").withStyle(ChatFormatting.WHITE).
                            append(newItemStack.getDisplayName()).
                            append(Component.literal(" 重置nbt标签").withStyle(ChatFormatting.WHITE)));
            player.setItemInHand(InteractionHand.MAIN_HAND,newItemStack);
        }
        else {
            Compute.FormatMSGSend(player, Component.literal("重置").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("这件物品不能被重置标签").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
