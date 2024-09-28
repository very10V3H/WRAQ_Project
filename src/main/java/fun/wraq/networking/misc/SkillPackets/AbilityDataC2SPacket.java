package fun.wraq.networking.misc.SkillPackets;

import fun.wraq.common.util.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityDataC2SPacket {
    private final int Power;
    private final int Intelligent;
    private final int Flexibility;
    private final int Lucky;
    private final int Vitality;

    public AbilityDataC2SPacket(int Power, int Intelligent, int Flexibility, int Lucky, int Vitality) {
        this.Power = Power;
        this.Intelligent = Intelligent;
        this.Flexibility = Flexibility;
        this.Lucky = Lucky;
        this.Vitality = Vitality;
    }

    public AbilityDataC2SPacket(FriendlyByteBuf buf) {
        this.Power = buf.readInt();
        this.Intelligent = buf.readInt();
        this.Flexibility = buf.readInt();
        this.Lucky = buf.readInt();
        this.Vitality = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Power);
        buf.writeInt(this.Intelligent);
        buf.writeInt(this.Flexibility);
        buf.writeInt(this.Lucky);
        buf.writeInt(this.Vitality);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.Ability.Power, Power);
            data.putInt(StringUtils.Ability.Intelligent, Intelligent);
            data.putInt(StringUtils.Ability.Flexibility, Flexibility);
            data.putInt(StringUtils.Ability.Lucky, Lucky);
            data.putInt(StringUtils.Ability.Vitality, Vitality);
            data.putInt(StringUtils.AbilityPoint_Used, Power + Intelligent + Flexibility + Lucky + Vitality);
        });
        return true;
    }
}
