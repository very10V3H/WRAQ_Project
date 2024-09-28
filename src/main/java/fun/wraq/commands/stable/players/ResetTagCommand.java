package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
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
        CompoundTag tag = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (tag.contains(InventoryCheck.owner)) {
            Compute.sendFormatMSG(player, Component.literal("重置").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("绑定物品不能被重置标签").withStyle(ChatFormatting.WHITE));
            return 0;
        }

        if (!(Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)
                || Utils.customizedList.contains(item) || Utils.passiveEquipTag.containsKey(item))
                || !InventoryCheck.getBoundingList().contains(item)) {
            ItemStack newItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
            Compute.sendFormatMSG(player, Component.literal("重置").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("已为 ").withStyle(ChatFormatting.WHITE).
                            append(newItemStack.getDisplayName()).
                            append(Component.literal(" 重置nbt标签").withStyle(ChatFormatting.WHITE)));
            player.setItemInHand(InteractionHand.MAIN_HAND, newItemStack);
        } else {
            Compute.sendFormatMSG(player, Component.literal("重置").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("这件物品不能被重置标签").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
