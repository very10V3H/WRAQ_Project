package fun.wraq.networking.misc.dialog;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-18
 * S2C：关闭当前对话界面（对应对话中的「稍后」「告辞」选项）。
 * <p>
 * 客户端不直接引用界面类，只置一个标志，由 {@code ClientPlayerTickEvent} 统一关闭，
 * 避免网络包在服务端加载客户端类。
 */
public class DialogCloseS2CPacket {

    public DialogCloseS2CPacket() {
    }

    public DialogCloseS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientUtils.closeDialogFlag = true);
        return true;
    }
}
