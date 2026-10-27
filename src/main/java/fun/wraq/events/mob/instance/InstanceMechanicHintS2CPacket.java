package fun.wraq.events.mob.instance;

import fun.wraq.render.hud.main.InstanceMechanicHintHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-27
 * S2C：玩家被副本怪物击杀后，下发该副本的机制说明，显示在左下角 HUD。
 */
public class InstanceMechanicHintS2CPacket {

    private final Component instanceName;
    private final List<Component> lines;

    public InstanceMechanicHintS2CPacket(Component instanceName, List<Component> lines) {
        this.instanceName = instanceName;
        this.lines = List.copyOf(lines);
    }

    public InstanceMechanicHintS2CPacket(FriendlyByteBuf buf) {
        this.instanceName = buf.readComponent();
        this.lines = buf.readList(FriendlyByteBuf::readComponent);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(instanceName);
        buf.writeCollection(lines, FriendlyByteBuf::writeComponent);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> InstanceMechanicHintHud.show(instanceName, lines));
        return true;
    }
}
