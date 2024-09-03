package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RuneHud0S2CPacket {
    private final int PlainRune;
    private final int ForestRune;
    private final int VolcanoRune;
    private final int ManaRune;
    private final int NetherRune;
    private final int SnowRune;
    private final int EndRune;
    private final int LakeRune;

    public RuneHud0S2CPacket(int plainRune, int forestRune, int volcanoRune, int manaRune, int netherRune, int snowRune, int endRune, int lakeRune) {
        this.PlainRune = plainRune;
        this.ForestRune = forestRune;
        this.VolcanoRune = volcanoRune;
        this.ManaRune = manaRune;
        this.NetherRune = netherRune;
        this.SnowRune = snowRune;
        this.EndRune = endRune;
        this.LakeRune = lakeRune;
    }

    public RuneHud0S2CPacket(FriendlyByteBuf buf) {
        this.PlainRune = buf.readInt();
        this.ForestRune = buf.readInt();
        this.VolcanoRune = buf.readInt();
        this.ManaRune = buf.readInt();
        this.NetherRune = buf.readInt();
        this.SnowRune = buf.readInt();
        this.EndRune = buf.readInt();
        this.LakeRune = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.PlainRune);
        buf.writeInt(this.ForestRune);
        buf.writeInt(this.VolcanoRune);
        buf.writeInt(this.ManaRune);
        buf.writeInt(this.NetherRune);
        buf.writeInt(this.SnowRune);
        buf.writeInt(this.EndRune);
        buf.writeInt(this.LakeRune);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.PlainRune = this.PlainRune;
            ClientUtils.ForestRune = this.ForestRune;
            ClientUtils.VolcanoRune = this.VolcanoRune;
            ClientUtils.ManaRune = this.ManaRune;
            ClientUtils.NetherRune = this.NetherRune;
            ClientUtils.SnowRune = this.SnowRune;
            ClientUtils.EndRune = this.EndRune;
            ClientUtils.LakeRune = this.LakeRune;
        });
        return true;
    }
}
