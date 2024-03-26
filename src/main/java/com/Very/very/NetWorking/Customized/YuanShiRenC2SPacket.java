package com.Very.very.NetWorking.Customized;

import com.Very.very.Customize.Players.YuanShiRen.YuanShiRen;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class YuanShiRenC2SPacket {
    public YuanShiRenC2SPacket()
    {

    }
    public YuanShiRenC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()->{
            YuanShiRen.Active(serverPlayer);
        });
        return true;
    }
}
