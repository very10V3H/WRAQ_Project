package com.very.wraq.commands.stable.players;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ConfirmCommand implements Command<CommandSourceStack> {
    public static ConfirmCommand instance = new ConfirmCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            if (data.contains("NSConfirm") && data.getBoolean("NSConfirm")) {
                data.putBoolean("NSConfirm", false);
                Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                        Component.literal("已取消确认！").withStyle(ChatFormatting.WHITE));
                Utils.NSPlayerController.remove(player);
            } else {
                if (Utils.NSPlayerController.contains(player) && Utils.NSController == -1) {
                    if (player.level().equals(player.getServer().getLevel(Level.NETHER)) &&
                            player.getX() > 27 && player.getX() < 30 &&
                            player.getZ() > 266 && player.getZ() < 269 && player.getY() == 32) {
                        data.putBoolean("NSConfirm", true);
                        boolean flag = true;
                        for (Player player1 : Utils.NSPlayerController) {
                            CompoundTag TmpData = player1.getPersistentData();
                            if (!TmpData.getBoolean("NSConfirm")) flag = false;
                        }
                        if (!flag) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                    Component.literal("已确认激活副本，请等待其他玩家确认！").withStyle(ChatFormatting.WHITE));
                        }
                    }
                }
            }
        }
        return 0;
    }
}
