package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashSet;
import java.util.Set;


public class ResetAccountCommand implements Command<CommandSourceStack> {
    public static ResetAccountCommand instance = new ResetAccountCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag tag = player.getPersistentData();
        Set<String> set = new HashSet<>(tag.getAllKeys());
        set.forEach(tag::remove);
        player.experienceLevel = 0;
        player.experienceProgress = 0;
        player.sendSystemMessage(Te.s("已重置Tag与物品/饰品栏，请重新登录"));
        player.getInventory().clearContent();
        CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
            IItemHandlerModifiable iItemHandlerModifiable = iCuriosItemHandler.getEquippedCurios();
            for (int i = 0 ; i < iItemHandlerModifiable.getSlots() ; i ++) {
                iItemHandlerModifiable.setStackInSlot(i, Items.AIR.getDefaultInstance());
            }
        });
        return 0;
    }
}
