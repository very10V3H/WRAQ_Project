package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;

import java.util.UUID;

/**
 * AI-Generated, 2026-10-18
 * {@code /vmd setBackpackUuid <uuid>}：把主手精妙背包的「内容 UUID」改成指定值。
 *
 * <p>用途：修复内容 UUID 丢失/被改写的背包。精妙背包的物品本身只保存一个
 * {@code contentsUuid}，真实内容存在世界的 {@code BackpackStorage} 里；
 * 只要把 UUID 改回原来那个，内容就会重新出现（旧内容不会因为 UUID 丢失而被删除）。
 *
 * <p>例如审计界面曾被 SB 的去重逻辑误抹 UUID 的背包：用当初审计的 UUID 执行本命令即可找回。
 */
public class SetBackpackUuidCommand implements Command<CommandSourceStack> {

    public static final SetBackpackUuidCommand instance = new SetBackpackUuidCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Te.s("仅玩家可执行该命令。"));
            return 0;
        }
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof BackpackItem)) {
            context.getSource().sendFailure(Te.s("请把要修复的精妙背包拿在主手。"));
            return 0;
        }
        UUID targetUuid = context.getArgument("uuid", UUID.class);
        BackpackWrapper wrapper = new BackpackWrapper(stack);
        UUID oldUuid = wrapper.getContentsUuid().orElse(null);
        wrapper.setContentsUuid(targetUuid);
        player.setItemInHand(InteractionHand.MAIN_HAND, wrapper.getBackpack());
        player.inventoryMenu.broadcastChanges();
        if (player.containerMenu != null && player.containerMenu != player.inventoryMenu) {
            player.containerMenu.broadcastChanges();
        }
        Compute.sendFormatMSG(player, Te.s("背包审计", ChatFormatting.AQUA),
                Te.s("已把主手背包的内容 UUID 从 ", ChatFormatting.WHITE, String.valueOf(oldUuid),
                        " 改为 ", ChatFormatting.WHITE, targetUuid.toString(),
                        "；重新右键打开即可检查内容。"));
        return 1;
    }
}
