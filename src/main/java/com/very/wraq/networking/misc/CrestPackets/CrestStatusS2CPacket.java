package com.very.wraq.networking.misc.CrestPackets;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.StringUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CrestStatusS2CPacket {
    private final int type;
    private final boolean flag;

    public CrestStatusS2CPacket(int type, boolean flag) {
        this.type = type;
        this.flag = flag;
    }

    public CrestStatusS2CPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            switch (type) {
                case 1 -> ClientUtils.CrestMap.put(StringUtils.Crest.Plain.Crest, flag);
                case 2 -> ClientUtils.CrestMap.put(StringUtils.Crest.Forest.Crest, flag);
                case 3 -> ClientUtils.CrestMap.put(StringUtils.Crest.Lake.Crest, flag);
                case 4 -> ClientUtils.CrestMap.put(StringUtils.Crest.Volcano.Crest, flag);
                case 5 -> ClientUtils.CrestMap.put(StringUtils.Crest.Mine.Crest, flag);
                case 6 -> ClientUtils.CrestMap.put(StringUtils.Crest.Snow.Crest, flag);
                case 7 -> ClientUtils.CrestMap.put(StringUtils.Crest.Sky.Crest, flag);
                case 8 -> ClientUtils.CrestMap.put(StringUtils.Crest.Mana.Crest, flag);
                case 9 -> ClientUtils.CrestMap.put(StringUtils.Crest.Nether, flag);
            }
        });
        return true;
    }
}
