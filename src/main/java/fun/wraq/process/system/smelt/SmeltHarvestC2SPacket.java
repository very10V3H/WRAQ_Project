package fun.wraq.process.system.smelt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.process.system.smelt.Smelt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.function.Supplier;

public class SmeltHarvestC2SPacket {

    private final int index;

    public SmeltHarvestC2SPacket(int index) {
        this.index = index;
    }

    public SmeltHarvestC2SPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                fun.wraq.process.system.smelt.Smelt.checkFinishAndGiveItem(context.getSender(), index + 1);
            } catch (ParseException | CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            Smelt.sendDataToClient(context.getSender());
        });
        return true;
    }
}
