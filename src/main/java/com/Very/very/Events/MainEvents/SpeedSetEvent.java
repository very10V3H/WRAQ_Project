package com.Very.very.Events.MainEvents;

import com.Very.very.Process.Parkour.Parkour;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Breath;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
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
            double SpeedUp = Compute.PlayerAttributes.PlayerMovement(player);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D+0.1* (float) SpeedUp);
            if (Parkour.PlayerIsInParkourRange(player)) player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
            CompoundTag data = player.getPersistentData();
            if (event.player.tickCount % 200 == 0 && data.contains("Green") && data.getInt("Green") == 2 && event.phase == TickEvent.Phase.START) {
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原的风为你指引道路").withStyle(ChatFormatting.GREEN));
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原符石-临危制变为你提供了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f", 100 * (((player.getMaxHealth() - player.getHealth()) * 1.0 / player.getMaxHealth()) / 2.0))).withStyle(ChatFormatting.GREEN)).append(Component.literal("%").withStyle(ChatFormatting.RESET)).append(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RESET)));
            }

            double ExSwimSpeed = 0;
            if (data.contains(StringUtils.LakeRune) && data.getInt(StringUtils.LakeRune) == 2) ExSwimSpeed += 2;
            if (Breath.onPlayerMap.containsKey(player) && Breath.onPlayerMap.get(player)) ExSwimSpeed += 1;
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(1 + ExSwimSpeed);

        }
    }
}
