package com.Very.very.Events.MainEvents;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpeedSetEvent {
    @SubscribeEvent
    public static void SpeedSet(TickEvent.PlayerTickEvent event)
    {
        if (event.side.isServer()) {
            Player player = event.player;
            float speedup = Compute.PlayerSpeedImprove(player);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D+0.1*speedup);
            CompoundTag data = player.getPersistentData();
            if (event.player.tickCount % 200 == 0 && data.contains("Green") && data.getInt("Green") == 2 && event.phase == TickEvent.Phase.START) {
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原的风为你指引道路").withStyle(ChatFormatting.GREEN));
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原符石-临危制变为你提供了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f", 100 * (((player.getMaxHealth() - player.getHealth()) * 1.0 / player.getMaxHealth()) / 2.0))).withStyle(ChatFormatting.GREEN)).append(Component.literal("%").withStyle(ChatFormatting.RESET)).append(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RESET)));
            }
        }
    }
}
