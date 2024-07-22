package com.very.wraq.networking.misc.USE;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionResetS2CPacket {
    public MissionResetS2CPacket() {

    }

    public MissionResetS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.MissionReset();
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
