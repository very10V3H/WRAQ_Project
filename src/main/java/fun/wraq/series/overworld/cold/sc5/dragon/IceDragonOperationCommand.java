package fun.wraq.series.overworld.cold.sc5.dragon;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class IceDragonOperationCommand implements Command<CommandSourceStack> {
    public static IceDragonOperationCommand instance = new IceDragonOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "simulate" -> {
                Compute.sendBlankLine(player, 8);
                Compute.teleportPlayerToPos(player, IceDragonTpUtil.PRACTICE_DRAGON_TP_POS, 175, 0);
                IceDragonTpUtil.sendMSG(player, Te.s("前哨所已将你送至",
                        "极寒冰龙模拟挑战区域", CustomStyle.styleOfIce, "."));
            }
            case "goto" -> {
                if (player.getPersistentData().contains(SimulateIceDragonSpawnController.DRAGON_MEDAL_DATA_KEY)) {
                    Compute.sendBlankLine(player, 8);
                    Compute.teleportPlayerToPos(player, IceDragonTpUtil.DRAGON_TP_POS, 110, 0);
                    IceDragonTpUtil.sendMSG(player, Te.s("前哨所已将你送至",
                            "极寒冰龙挑战区域", CustomStyle.styleOfIce, "，尽全力征服它!"));
                } else {
                    IceDragonTpUtil.sendMSG(player, Te.s("必须完成模拟训练后，才能将你带至挑战区域."));
                }
            }
        }
        return 0;
    }
}
