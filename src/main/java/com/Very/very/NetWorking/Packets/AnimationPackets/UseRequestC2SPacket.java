package com.Very.very.NetWorking.Packets.AnimationPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class UseRequestC2SPacket {
    private final int count;
    public UseRequestC2SPacket(int count)
    {
        this.count = count;
    }
    public UseRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            Compute.USE(serverPlayer,serverPlayer.getMainHandItem().getItem());
        });
        return true;
    }
}
