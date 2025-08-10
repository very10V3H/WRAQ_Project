package fun.wraq.render.gui;

import fun.wraq.common.fast.Tick;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenInfoS2CPacket {

    private final Component info;

    public ScreenInfoS2CPacket(Component info) {
        this.info = info;
    }

    public ScreenInfoS2CPacket(FriendlyByteBuf buf) {
        this.info = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(info);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            WraqScreen.clientDisplayInfo = info;
            WraqScreen.infoLifeCycleTicks = Tick.s(2);
        });
        return true;
    }
}
