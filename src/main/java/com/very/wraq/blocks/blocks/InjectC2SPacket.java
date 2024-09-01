package com.very.wraq.blocks.blocks;

import com.very.wraq.blocks.entity.InjectBlockEntity;
import com.very.wraq.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class InjectC2SPacket {

    private final BlockPos blockPos;

    public InjectC2SPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public InjectC2SPacket(FriendlyByteBuf buf) {
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

            if (level.getBlockState(blockPos).getBlock().equals(ModBlocks.Inject_Block.get())) {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity instanceof InjectBlockEntity injectBlockEntity) {
                    injectBlockEntity.craft();
                }
            }
        });
        return true;
    }
}
