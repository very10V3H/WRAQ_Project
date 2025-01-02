package fun.wraq.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VersionC2SPacket {

    private final String Version;

    public VersionC2SPacket(String version) {
        this.Version = version;
    }

    public VersionC2SPacket(FriendlyByteBuf buf) {
        this.Version = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.Version);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (!Version.equals("2.0.42b")) {
                serverPlayer.connection.disconnect(Component.literal("请使用更新程序更新mod，或在群文件中获取最新版本mod用于替换。"));
            }
        });
        return true;
    }

}
