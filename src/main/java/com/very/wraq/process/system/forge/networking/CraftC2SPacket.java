package com.very.wraq.process.system.forge.networking;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.blocks.entity.ForgingBlockEntity;
import com.very.wraq.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftC2SPacket {

    private final BlockPos blockPos;

    public CraftC2SPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public CraftC2SPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

            Level level = serverPlayer.level();
            if (level.getBlockState(blockPos).getBlock().equals(ModBlocks.FORGING_BLOCK.get())) {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity instanceof ForgingBlockEntity forgingBlockEntity) {
                    try {
                        forgingBlockEntity.craft();
                    } catch (CommandSyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return true;
    }
}
