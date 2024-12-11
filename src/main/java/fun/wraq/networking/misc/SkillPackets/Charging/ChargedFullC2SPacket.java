package fun.wraq.networking.misc.SkillPackets.Charging;

import fun.wraq.common.util.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargedFullC2SPacket {
    private final int Num;

    public ChargedFullC2SPacket(int Num) {
        this.Num = Num;
    }

    public ChargedFullC2SPacket(FriendlyByteBuf buf) {
        this.Num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            String name = player.getName().getString();
            switch (Num) {
                case 0 -> Utils.SwordSkill12.put(name, true);
                case 1 -> Utils.BowSkill12.put(name, true);
                case 2 -> Utils.ManaSkill12.put(name, true);
                case 4 -> Utils.SakuraDemonSword.put(name, true);
                case 5 -> Utils.ZeusSword.put(name, true);
            }
        });
        return true;
    }
}
