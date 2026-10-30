package fun.wraq.process.system.element.networking;

import fun.wraq.process.system.element.Element;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResonanceC2SPacket {

    private final String elementType;

    public ResonanceC2SPacket(String elementType) {
        this.elementType = elementType;
    }

    public ResonanceC2SPacket(FriendlyByteBuf buf) {
        this.elementType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(elementType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            if (player.experienceLevel < Element.ROULETTE_REQUIRED_LEVEL) {
                player.sendSystemMessage(Component.literal("[元素]").withStyle(ChatFormatting.GRAY)
                        .append(Component.literal("需要达到 135 级才能选择元素。")
                                .withStyle(ChatFormatting.RED)));
                return;
            }
            Element.resonance(player, elementType);
        });
        return true;
    }
}
