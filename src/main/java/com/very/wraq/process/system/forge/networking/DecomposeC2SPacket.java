package com.very.wraq.process.system.forge.networking;

import com.very.wraq.blocks.entity.ForgingBlockEntity;
import com.very.wraq.common.MySound;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DecomposeC2SPacket {

    private final BlockPos blockPos;

    public DecomposeC2SPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public DecomposeC2SPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public static Map<String, Integer> doubleClickTick = new HashMap<>();

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            String name = serverPlayer.getName().getString();
            if (!doubleClickTick.containsKey(name) || doubleClickTick.get(name) < tick) {
                doubleClickTick.put(name, tick + 20);
                ModNetworking.sendToClient(new DecomposeDoubleClickTickS2CPacket(), serverPlayer);
                return;
            }

            if (doubleClickTick.get(name) > tick) {
                Level level = serverPlayer.level();
                if (level.getBlockState(blockPos).getBlock().equals(ModBlocks.FORGING_BLOCK.get())) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity instanceof ForgingBlockEntity forgingBlockEntity) {
                        if (forgingBlockEntity.decompose()) {
                            doubleClickTick.remove(name);
                            MySound.soundToPlayer(serverPlayer, SoundEvents.ANVIL_BREAK);
                            Compute.sendFormatMSG(serverPlayer, Component.literal("分解").withStyle(ChatFormatting.RED),
                                    Component.literal("分解成功!").withStyle(ChatFormatting.WHITE));
                        } else {
                            ModNetworking.sendToClient(new DecomposeRecipeLossS2CPacket(), serverPlayer);
                        }
                    }
                }
            }

        });
        return true;
    }
}
