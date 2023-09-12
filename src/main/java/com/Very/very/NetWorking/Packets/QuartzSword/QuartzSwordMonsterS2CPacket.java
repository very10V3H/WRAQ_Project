package com.Very.very.NetWorking.Packets.QuartzSword;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class QuartzSwordMonsterS2CPacket {
    private final UUID uuid;
    private final int index;
    public QuartzSwordMonsterS2CPacket(UUID uuid, int index)
    {
        this.uuid = uuid;
        this.index = index;
    }
    public QuartzSwordMonsterS2CPacket(FriendlyByteBuf buf)
    {
        this.uuid = buf.readUUID();
        this.index = buf.readerIndex();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUUID(this.uuid);
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.QuartzSabreClientPlayerUUIDINDEX.put(uuid,index);
        });
        return true;
    }
}
