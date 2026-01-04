package fun.wraq.networking.misc.SkillPackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.SkillImage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillImageS2CPacket {
    private final int index;
    private final int maxTime;
    private final int tickTime;
    private final int num;
    private final int type;

    public SkillImageS2CPacket(int Index, int MaxTime, int TickTime, int Num, int Type) {
        this.index = Index;
        this.maxTime = MaxTime;
        this.tickTime = TickTime;
        this.num = Num;
        this.type = Type;
    }

    public SkillImageS2CPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
        this.maxTime = buf.readInt();
        this.tickTime = buf.readInt();
        this.num = buf.readInt();
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeInt(this.maxTime);
        buf.writeInt(this.tickTime);
        buf.writeInt(this.num);
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            switch (this.type) {
                case 0 -> ClientUtils.Sword_Image[index] = new SkillImage(maxTime * 20, tickTime * 20, num);
                case 1 -> ClientUtils.Bow_Image[index] = new SkillImage(maxTime * 20, tickTime * 20, num);
                case 2 -> ClientUtils.Mana_Image[index] = new SkillImage(maxTime * 20, tickTime * 20, num);
                case 3 -> ClientUtils.Demon_Image[index] = new SkillImage(maxTime * 20, tickTime * 20, num);
                case 4 -> ClientUtils.Rune_Image[index] = new SkillImage(maxTime, tickTime, num);
            }
        });
        return true;
    }
}
