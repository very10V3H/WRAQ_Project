package com.Very.very.NetWorking.Packets.AttributePackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
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
    public RuneHud0S2CPacket(int plainRune, int forestRune, int volcanoRune, int manaRune, int netherRune, int snowRune)
    {
        this.PlainRune = plainRune;
        this.ForestRune = forestRune;
        this.VolcanoRune = volcanoRune;
        this.ManaRune = manaRune;
        this.NetherRune = netherRune;
        this.SnowRune = snowRune;
    }
    public RuneHud0S2CPacket(FriendlyByteBuf buf)
    {
        this.PlainRune = buf.readInt();
        this.ForestRune = buf.readInt();
        this.VolcanoRune = buf.readInt();
        this.ManaRune = buf.readInt();
        this.NetherRune = buf.readInt();
        this.SnowRune = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.PlainRune);
        buf.writeInt(this.ForestRune);
        buf.writeInt(this.VolcanoRune);
        buf.writeInt(this.ManaRune);
        buf.writeInt(this.NetherRune);
        buf.writeInt(this.SnowRune);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.PlainRune = this.PlainRune;
            ClientUtils.ForestRune = this.ForestRune;
            ClientUtils.VolcanoRune = this.VolcanoRune;
            ClientUtils.ManaRune = this.ManaRune;
            ClientUtils.NetherRune = this.NetherRune;
            ClientUtils.SnowRune = this.SnowRune;
        });
        return true;
    }
}
