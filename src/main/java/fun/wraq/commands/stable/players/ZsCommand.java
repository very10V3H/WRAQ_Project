package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ZsCommand implements Command<CommandSourceStack> {
    public static ZsCommand instance = new ZsCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (Utils.mainHandTag.containsKey(itemStack.getItem()) || Utils.armorTag.containsKey(itemStack.getItem())) {
            Compute.forgingHoverName(itemStack);
        }
        Compute.formatBroad(Te.s("分解", CustomStyle.styleOfStone),
                Te.s(player.getDisplayName(), " 展示了 ", itemStack.getDisplayName()));
        return 0;
    }
}
