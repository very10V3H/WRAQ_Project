package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.specialevents.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class CompensateCommand implements Command<CommandSourceStack> {
    public static CompensateCommand instance = new CompensateCommand();

    public static String singleReward = "singleReward13";

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();

        if (!data.contains(singleReward)) {
            data.putBoolean(singleReward, true);
            if (player.experienceLevel >= 60) {
                try {
                    Tower.givePlayerStar(player, 400, "服务提供中断补偿");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<ItemStack> itemStackList = List.of(new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 20));
                for (ItemStack itemStack : itemStackList) {
                    InventoryOperation.itemStackGive(player, itemStack);
                }

                Compute.sendFormatMSG(player, Component.literal("更新补偿").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你收到了来自铁头的补偿!").withStyle(ChatFormatting.WHITE));
            }
            return 0;
        }
        Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));

/*        String version = "version:1.0.3";
        if (true) {
            Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));
            return 0;
        }
        if (!data.contains(version) && player.experienceLevel >= 200) {
            switch (type) {
                case "ice" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 64));
                }
                case "devil" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 16));
                }
                case "taboo" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 24));
                }
                case "moon" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 64));
                }
                case "castle" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 64));
                }
                case "purple" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                }
                case "life" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.LifeElementPiece1.get(), 21));
                }
                case "water" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.WaterElementPiece1.get(), 21));
                }
                case "fire" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.FireElementPiece1.get(), 21));
                }
                case "stone" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.StoneElementPiece1.get(), 21));
                }
                case "iceElement" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceElementPiece1.get(), 21));
                }
                case "wind" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.WindElementPiece1.get(), 21));
                }
                case "lightning" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.LightningElementPiece1.get(), 21));
                }
                default -> {
                    Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                            Component.literal("拼写错误，请使用/vmd compensate [life/water/fire/stone/ice/lightning/wind]领取补偿！").withStyle(ChatFormatting.AQUA));
                    return 0;
                }
            }

            Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("你成功领取了补偿！").withStyle(ChatFormatting.AQUA));

            data.putBoolean(version, true);
            return 0;
        }
        if (!data.contains(version) && player.experienceLevel >= 160) {
            switch (type) {
                case "ice" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 64));
                }
                case "devil" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 16));
                }
                case "taboo" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 24));
                }
                case "moon" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 64));
                }
                case "castle" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 64));
                }
                case "purple" -> {
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                }
                default -> {
                    Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                            Component.literal("拼写错误，请使用/vmd compensate [ice/devil/taboo/moon/castle/purple]领取补偿！").withStyle(ChatFormatting.AQUA));
                    return 0;
                }
            }

            Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("你成功领取了补偿！").withStyle(ChatFormatting.AQUA));

            data.putBoolean(version, true);
            return 0;
        }
        */
        return 0;
    }
}