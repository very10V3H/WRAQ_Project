package fun.wraq.series.overworld.chapter2.dimension;

import fun.wraq.common.Compute;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToEnd extends Item {

    public static Vec3 endSpawnPos = new Vec3(137.5, 50, 0.5);
    public static Vec2 spawnPosRot = new Vec2(90, -45);

    public ToEnd(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag p_41424_) {
        if (level != null) {
            if (level.dimension().equals(ClientLevel.END)) {
                components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("碾碎").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("此物，前往").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("终界寂域").withStyle(CustomStyle.styleOfEnd)));
            } else {
                components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("碾碎").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("此物，前往").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("终界").withStyle(CustomStyle.styleOfEnd)));
            }
        }
        super.appendHoverText(stack, level, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (player.experienceLevel >= 75) {
                ServerLevel end = level.getServer().getLevel(Level.END);
                ServerPlayer serverPlayer = (ServerPlayer) player;

                Compute.playerItemUseWithRecord(player);

                if (!serverPlayer.level().dimension().equals(Level.END)) {
                    toEndSpawnPos(serverPlayer);
                } else {
                    serverPlayer.teleportTo(end, 24.5, 88, -140.5, 180, 0);
                }
            } else {
                Compute.sendFormatMSG(player, Component.literal("终界").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("需要达到75级才能前往").withStyle(ChatFormatting.WHITE));
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void toEndSpawnPos(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        ServerLevel end = serverPlayer.getServer().getLevel(Level.END);
        serverPlayer.teleportTo(end, endSpawnPos.x, endSpawnPos.y, endSpawnPos.z, spawnPosRot.x, spawnPosRot.y);
    }
}
