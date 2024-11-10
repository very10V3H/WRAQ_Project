package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
        int mode = IntegerArgumentType.getInteger(context, "mode");
        CompoundTag data = player.getPersistentData();
        data.putInt(QuickUseHud.DISPLAY_KEY, mode);
        switch (mode) {
            case -1 -> {
                Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                        Te.s("已关闭", ChatFormatting.RED, "战斗快捷使用显示"));
            }
            case 0 -> {
                Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                        Te.s("战斗快捷使用提示，现在使用", " 模式0", ChatFormatting.AQUA));
            }
            case 1 -> {
                Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                        Te.s("战斗快捷使用提示，现在使用", " 模式1", ChatFormatting.AQUA));
            }
        }
        ModNetworking.sendToClient(new QuickUseDisplayS2CPacket(data.getInt(QuickUseHud.DISPLAY_KEY)), player);
        return 0;
    }
}
