package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ForgeCommand implements Command<CommandSourceStack> {
    public static ForgeCommand instance = new ForgeCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String second = StringArgumentType.getString(context, "level");
        int level = Integer.parseInt(second);
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        data.putInt(StringUtils.ForgeLevel, level);
        itemStack.resetHoverName();
        player.sendSystemMessage(Component.literal("手上的物品已附加" + level + "强化等级标签。"));
        return 0;
    }
}
