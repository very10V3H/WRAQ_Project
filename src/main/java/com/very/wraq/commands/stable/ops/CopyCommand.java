package com.very.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collection;

public class CopyCommand implements Command<CommandSourceStack> {
    public static CopyCommand instance = new CopyCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "name");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }

            // target -> player

            // 1.复制物品栏
            Inventory myInventory = player.getInventory();
            Inventory playerInventory = target.getInventory();
            for (int i = 0 ; i < myInventory.getContainerSize() ; i ++) {
                myInventory.setItem(i, playerInventory.getItem(i).copy());
            }

            // 2.复制persistentData
            player.getPersistentData().merge(target.getPersistentData());

            // 3.复制饰品栏
            CuriosApi.getCuriosInventory(target).ifPresent(inv -> {
                CuriosApi.getCuriosInventory(player).ifPresent(inv1 -> {
                    for (int i = 0 ; i < inv.getEquippedCurios().getSlots() ; i ++) {
                        inv1.getEquippedCurios().setStackInSlot(i, inv.getEquippedCurios().getStackInSlot(i));
                    }
                });
            });

            player.sendSystemMessage(Component.literal("成功复制 ").withStyle(ChatFormatting.WHITE).
                    append(target.getDisplayName()).
                    append(Component.literal(" 所有数据").withStyle(ChatFormatting.WHITE)));
        }

        return 0;
    }


}
