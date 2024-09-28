package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.events.core.InventoryCheck;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
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
                ItemStack itemStack = playerInventory.getItem(i).copy();
                if (itemStack.getItem() instanceof BackpackItem) {
                    player.sendSystemMessage(Component.literal("跳过了一个背包物品"));
                    continue;
                }
                if (InventoryCheck.containOwnerTag(itemStack)) InventoryCheck.removeOwnerTagDirect(itemStack);
                myInventory.setItem(i, itemStack);
            }

            // 2.复制persistentData
            player.getPersistentData().merge(target.getPersistentData());

            // 3.复制饰品栏
            CuriosApi.getCuriosInventory(target).ifPresent(inv -> {
                CuriosApi.getCuriosInventory(player).ifPresent(inv1 -> {
                    for (int i = 0 ; i < inv.getEquippedCurios().getSlots() ; i ++) {
                        ItemStack itemStack = inv.getEquippedCurios().getStackInSlot(i).copy();
                        if (InventoryCheck.containOwnerTag(itemStack)) InventoryCheck.removeOwnerTagDirect(itemStack);
                        inv1.getEquippedCurios().setStackInSlot(i, itemStack);
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
