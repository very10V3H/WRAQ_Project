package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.customized.UniformItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;

public class CompensateCommand implements Command<CommandSourceStack> {
    public static CompensateCommand instance = new CompensateCommand();

    public static int rewardNum = 26;
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
                try {
                    rollReward(player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("你成功领取了补偿!").withStyle(ChatFormatting.AQUA));
            return 0;
        }
        Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));
        return 0;
    }

    private void rollReward(Player player) throws SQLException {
        String name = player.getName().getString();
        switch (name.toLowerCase()) {
            case "mlhchre" -> {
                Tower.givePlayerStar(player, 800, "春节抽奖");
                InventoryOperation.giveItemStackWithMSG(player, ModItems.supplyBoxTier2.get());
            }
            case "fhyxx" -> {
                Tower.givePlayerStar(player, 600, "春节抽奖");
                InventoryOperation.giveItemStackWithMSG(player, ModItems.supplyBoxTier2.get());
            }
            case "opamber" -> {
                InventoryOperation.giveItemStackWithMSG(player, ModItems.supplyBoxTier3.get());
            }
            case "kiruo904" -> {
                InventoryOperation.giveItemStackWithMSG(player, ModItems.supplyBoxTier3.get());
                InventoryOperation.giveItemStackWithMSG(player, UniformItems.LifeCurios0.get());
            }
            case "wojile" -> {
                Tower.givePlayerStar(player, 1000, "春节抽奖");
            }
            case "dy_wanshe" -> {
                InventoryOperation.giveItemStackWithMSG(player, ModItems.supplyBoxTier2.get());
            }
            case "dev" -> {
                Tower.givePlayerStar(player, 1000, "春节抽奖");
            }
        }
    }
}
