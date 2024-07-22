package com.very.wraq.commands.changeable;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompensateCommand implements Command<CommandSourceStack> {
    public static CompensateCommand instance = new CompensateCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String type = StringArgumentType.getString(context, "type");
        CompoundTag data = player.getPersistentData();
        String version = "version:1.0.3";
        if (true) {
            Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));
            return 0;
        }
        if (!data.contains(version) && player.experienceLevel >= 200) {
            switch (type) {
                case "ice" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 64));
                }
                case "devil" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 16));
                }
                case "taboo" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 24));
                }
                case "moon" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 64));
                }
                case "castle" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 64));
                }
                case "purple" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                }
                case "life" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.LifeElementPiece1.get(), 21));
                }
                case "water" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.WaterElementPiece1.get(), 21));
                }
                case "fire" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.FireElementPiece1.get(), 21));
                }
                case "stone" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.StoneElementPiece1.get(), 21));
                }
                case "iceElement" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.IceElementPiece1.get(), 21));
                }
                case "wind" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.WindElementPiece1.get(), 21));
                }
                case "lightning" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.LightningElementPiece1.get(), 21));
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
                    Compute.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 64));
                }
                case "devil" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 16));
                }
                case "taboo" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 24));
                }
                case "moon" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 64));
                }
                case "castle" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 64));
                }
                case "purple" -> {
                    Compute.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud3.get(), 2));
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

        Compute.formatMSGSend(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                Component.literal("似乎已经领取过/没有资格领取补偿呢").withStyle(ChatFormatting.AQUA));


        return 0;
    }
}
