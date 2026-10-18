package fun.wraq.networking.misc.dialog;

import fun.wraq.process.system.dialog.DialogManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-17
 * C2S：玩家点击了对话里的某个选项。
 * <p>
 * 只携带「NPC + 会话 + 动作标识」三个字符串，不含任何业务参数——
 * 所有参数由服务端按当前状态重新计算，这是与「命令字符串携带参数」的关键差异。
 */
public class DialogSelectC2SPacket {

    private final String npcId;
    private final String sessionId;
    private final String actionId;

    public DialogSelectC2SPacket(String npcId, String sessionId, String actionId) {
        this.npcId = npcId;
        this.sessionId = sessionId;
        this.actionId = actionId;
    }

    public DialogSelectC2SPacket(FriendlyByteBuf buf) {
        this.npcId = buf.readUtf();
        this.sessionId = buf.readUtf();
        this.actionId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(npcId);
        buf.writeUtf(sessionId);
        buf.writeUtf(actionId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            DialogManager.select(player, npcId, sessionId, actionId);
        });
        return true;
    }
}
