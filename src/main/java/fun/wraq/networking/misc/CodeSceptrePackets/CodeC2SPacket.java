package fun.wraq.networking.misc.CodeSceptrePackets;

import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Power;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CodeC2SPacket {
    private final int power1;
    private final int power2;
    private final int power3;
    private final int power4;

    public CodeC2SPacket(int power1, int power2, int power3, int power4) {
        this.power1 = power1;
        this.power2 = power2;
        this.power3 = power3;
        this.power4 = power4;
    }

    public CodeC2SPacket(FriendlyByteBuf buf) {
        this.power1 = buf.readInt();
        this.power2 = buf.readInt();
        this.power3 = buf.readInt();
        this.power4 = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.power1);
        buf.writeInt(this.power2);
        buf.writeInt(this.power3);
        buf.writeInt(this.power4);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Power power = new Power(this.power1, this.power2, this.power3, this.power4);
            Utils.PowerMap.put(player, power);
        });
        return true;
    }
}
