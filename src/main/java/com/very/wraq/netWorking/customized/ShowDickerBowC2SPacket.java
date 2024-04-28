package com.very.wraq.netWorking.customized;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShowDickerBowC2SPacket {
    public ShowDickerBowC2SPacket()
    {

    }
    public ShowDickerBowC2SPacket(FriendlyByteBuf buf)
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
            if (Utils.ShowDickerCount == 10) {
                Utils.ShowDickerCount = 0;
                Utils.ShowDickerArrowCount = 8;
                Compute.EffectLastTimeSend(serverPlayer,ModItems.ShowDickerBow.get().getDefaultInstance(),8888,0,true);
            }
        });
        return true;
    }
}
