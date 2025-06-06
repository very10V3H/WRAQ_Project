package fun.wraq.networking.misc;

import fun.wraq.render.hud.Mana;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSyncS2CPacket {
    private final double MaxMana;
    private final double CurrentMana;

    public ManaSyncS2CPacket(double MaxMana, double CurrentMana) {
        this.MaxMana = MaxMana;
        this.CurrentMana = CurrentMana;
    }

    public ManaSyncS2CPacket(FriendlyByteBuf buf) {
        this.MaxMana = buf.readDouble();
        this.CurrentMana = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.MaxMana);
        buf.writeDouble(this.CurrentMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Mana.setMaxMana(MaxMana);
            Mana.setCurrentMana(CurrentMana);
        });
        return true;
    }
}
