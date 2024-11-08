package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.hud.main.QuickUseHud;
import fun.wraq.render.hud.networking.QuickUseDisplayS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class QuickUseDisplayCommand implements Command<CommandSourceStack> {
    public static QuickUseDisplayCommand instance = new QuickUseDisplayCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (!data.contains(QuickUseHud.DISPLAY_KEY)) {
            data.putBoolean(QuickUseHud.DISPLAY_KEY, false);
        } else {
            data.putBoolean(QuickUseHud.DISPLAY_KEY, !data.getBoolean(QuickUseHud.DISPLAY_KEY));
        }

        if (data.getBoolean(QuickUseHud.DISPLAY_KEY)) {
            Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                    Te.s("已开启", ChatFormatting.GREEN, "战斗快捷使用显示"));
        } else {
            Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                    Te.s("已关闭", ChatFormatting.RED, "战斗快捷使用显示"));
        }
        ModNetworking.sendToClient(new QuickUseDisplayS2CPacket(data.getBoolean(QuickUseHud.DISPLAY_KEY)), player);
        return 0;
    }
}
