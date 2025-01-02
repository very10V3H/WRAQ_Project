package fun.wraq.Items.MainStory_1;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NearestSpawnPointS2CPacket {

    private final Component component;

    public NearestSpawnPointS2CPacket(Component component) {
        this.component = component;
    }

    public NearestSpawnPointS2CPacket(FriendlyByteBuf buf) {
        this.component = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(component);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BackSpawn.clientNearestSpawnPoint = component;
        });
        return true;
    }
}
