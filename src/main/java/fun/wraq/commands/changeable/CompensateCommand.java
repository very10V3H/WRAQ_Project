package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompensateCommand implements Command<CommandSourceStack> {
    public static CompensateCommand instance = new CompensateCommand();

    public static int rewardNum = 36;
    public static String singleReward = "singleReward" + rewardNum;

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        CompoundTag data = player.getPersistentData();
        if (!data.contains(singleReward)) {
            data.putBoolean(singleReward, true);
            if (player.experienceLevel >= 75) {
                ItemStack itemStack = new ItemStack(ModItems.supplyBoxTier3.get(), 1);
                InventoryOperation.giveItemStackWithMSG(player, itemStack);
            }
            Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("你成功领取了补偿!").withStyle(ChatFormatting.AQUA));
            return 0;
        }
        Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));
        return 0;
    }
}
