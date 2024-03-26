package com.Very.very.NetWorking.Packets;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

public class ResetC2SPacket {
    public ResetC2SPacket()
    {

    }
    public ResetC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
/*
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getServer().getLevel(Level.OVERWORLD);
            List<BlockPos> queue = Utils.blockPosQueue;
            List <BlockPos> posQueue = Utils.blockPosBreakQueue;
            List <BlockState> stateQueue = Utils.blockStateQueue;
            while(!queue.isEmpty()) level.destroyBlock(queue.remove(),false);
            while(!posQueue.isEmpty()) level.setBlockAndUpdate(posQueue.remove(),stateQueue.remove());

            ServerLevel level1 = player.getServer().getLevel(Level.NETHER);
            List<BlockPos> nqueue = Utils.netherBlockPosQueue;
            List <BlockPos> nposQueue = Utils.netherBlockPosBreakQueue;
            List <BlockState> nstateQueue = Utils.netherBlockStateQueue;
            while(!nqueue.isEmpty()) level1.destroyBlock(nqueue.remove(),false);
            while(!nposQueue.isEmpty()) level1.setBlockAndUpdate(nposQueue.remove(),nstateQueue.remove());
*/
        });
        return true;
    }
}
