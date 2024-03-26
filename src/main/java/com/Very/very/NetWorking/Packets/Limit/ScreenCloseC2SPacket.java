package com.Very.very.NetWorking.Packets.Limit;

import com.Very.very.Blocks.Entity.ForgingBlockEntity;
import com.Very.very.Blocks.Entity.FurnaceEntity;
import com.Very.very.Blocks.Entity.HBrewingEntity;
import com.Very.very.Blocks.Entity.InjectBlockEntity;
import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class ScreenCloseC2SPacket {

    public ScreenCloseC2SPacket()
    {

    }
    public ScreenCloseC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            for (PlayerCallBack playerCallBack : Utils.playerCallBackQueue) {
                if (playerCallBack.getPlayer().getName().getString().equals(player.getName().getString())) {
                    BlockEntity blockEntity = player.level().getBlockEntity(playerCallBack.getBlockPos());
                    if (blockEntity instanceof ForgingBlockEntity forgingBlockEntity) forgingBlockEntity.drops(player);
                    if (blockEntity instanceof HBrewingEntity hBrewingEntity) hBrewingEntity.drops(player);
                    if (blockEntity instanceof InjectBlockEntity injectBlockEntity) injectBlockEntity.drops(player);
                    if (blockEntity instanceof FurnaceEntity furnaceEntity) furnaceEntity.drops(player);
                    Utils.playerCallBackQueue.remove(playerCallBack);
                    break;
                }

            }
        });
        return true;
    }
}
