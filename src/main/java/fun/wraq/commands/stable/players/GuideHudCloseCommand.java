package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.guide.Guide;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class GuideHudCloseCommand implements Command<CommandSourceStack> {
    public static GuideHudCloseCommand instance = new GuideHudCloseCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        CompoundTag data = player.getPersistentData();
        if (data.contains(Guide.GUIDE_HUD_CLOSE_DATA_KEY)) {
            data.putBoolean(Guide.GUIDE_HUD_CLOSE_DATA_KEY, !data.getBoolean(Guide.GUIDE_HUD_CLOSE_DATA_KEY));
        } else {
            data.putBoolean(Guide.GUIDE_HUD_CLOSE_DATA_KEY, true);
        }
        if (data.getBoolean(Guide.GUIDE_HUD_CLOSE_DATA_KEY)) {
            Guide.sendFormatMSG(player, Te.s("已", "永久关闭", ChatFormatting.RED, "指引界面"));
        } else {
            Guide.sendFormatMSG(player, Te.s("已", "继续显示", ChatFormatting.GREEN, "指引界面"));
        }
        Guide.sendGuideCloseStatusToClient(player);
        return 0;
    }
}
