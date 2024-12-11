package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Illustrate;
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
            itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(Illustrate.DISPLAY_FLAG, true);
            Compute.forgingHoverName(itemStack);
        }
        Compute.formatBroad(Te.s("展示", CustomStyle.styleOfStone),
                Te.s(player.getDisplayName(), " 展示了 ", itemStack.getDisplayName()));
        if (Utils.mainHandTag.containsKey(itemStack.getItem()) || Utils.armorTag.containsKey(itemStack.getItem())) {
            itemStack.getOrCreateTagElement(Utils.MOD_ID).remove(Illustrate.DISPLAY_FLAG);
            Compute.forgingHoverName(itemStack);
        }
        return 0;
    }
}
