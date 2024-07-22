package com.very.wraq.networking.misc.QuartzSword;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QuartzSwordGetMonsterS2CPacket {
    private final boolean flag;

    public QuartzSwordGetMonsterS2CPacket(boolean flag) {
        this.flag = flag;
    }

    public QuartzSwordGetMonsterS2CPacket(FriendlyByteBuf buf) {
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.GetQuartzSabreMonster = flag;
        });
        return true;
    }
}
