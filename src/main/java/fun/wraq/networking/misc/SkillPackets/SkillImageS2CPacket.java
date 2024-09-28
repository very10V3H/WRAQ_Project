package fun.wraq.networking.misc.SkillPackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.SkillImage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillImageS2CPacket {
    private final int Index;
    private final int MaxTime;
    private final int TickTime;
    private final int Num;
    private final int Type;

    public SkillImageS2CPacket(int Index, int MaxTime, int TickTime, int Num, int Type) {
        this.Index = Index;
        this.MaxTime = MaxTime;
        this.TickTime = TickTime;
        this.Num = Num;
        this.Type = Type;
    }

    public SkillImageS2CPacket(FriendlyByteBuf buf) {
        this.Index = buf.readInt();
        this.MaxTime = buf.readInt();
        this.TickTime = buf.readInt();
        this.Num = buf.readInt();
        this.Type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Index);
        buf.writeInt(this.MaxTime);
        buf.writeInt(this.TickTime);
        buf.writeInt(this.Num);
        buf.writeInt(this.Type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            switch (this.Type) {
                case 0 -> ClientUtils.Sword_Image[Index] = new SkillImage(MaxTime * 20, TickTime * 20, Num);
                case 1 -> ClientUtils.Bow_Image[Index] = new SkillImage(MaxTime * 20, TickTime * 20, Num);
                case 2 -> ClientUtils.Mana_Image[Index] = new SkillImage(MaxTime * 20, TickTime * 20, Num);
                case 3 -> ClientUtils.Demon_Image[Index] = new SkillImage(MaxTime * 20, TickTime * 20, Num);
                case 4 -> ClientUtils.Rune_Image[Index] = new SkillImage(MaxTime, TickTime, Num);
            }
        });
        return true;
    }
}
