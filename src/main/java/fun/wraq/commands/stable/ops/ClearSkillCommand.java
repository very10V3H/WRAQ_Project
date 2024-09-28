package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class ClearSkillCommand implements Command<CommandSourceStack> {
    public static ClearSkillCommand instance = new ClearSkillCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player.isCreative()) {
            Compute.resetSkillAndAbility(player);
        } else {
            if (!player.isCreative()) {
                Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                        Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
            }
        }
        return 0;
    }
}
