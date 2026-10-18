package fun.wraq.networking.misc.dialog;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.ClientDialogData;
import fun.wraq.process.system.dialog.DialogOption;
import fun.wraq.process.system.dialog.DialogPage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-17
 * S2C：下发一页对话。客户端在 ClientPlayerTickEvent 中据此打开 DialogScreen。
 * <p>
 * 选项的动作标识随包传入，点击时由 DialogSelectC2SPacket 原样回传，
 * 服务端再用「重建当前页」校验其是否合法。
 */
public class DialogOpenS2CPacket {

    private final String npcId;
    private final String sessionId;
    private final Component speaker;
    private final List<Component> lines;
    private final List<Component> optionLabels;
    private final List<Component> optionHovers;
    private final List<String> optionActionIds;
    private final int entityId;

    public DialogOpenS2CPacket(DialogPage page) {
        this.npcId = page.npcId();
        this.sessionId = page.sessionId();
        this.speaker = page.speaker();
        this.lines = page.lines();
        List<Component> labels = new ArrayList<>();
        List<Component> hovers = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (DialogOption option : page.options()) {
            labels.add(option.label());
            hovers.add(option.hover());
            ids.add(option.actionId());
        }
        this.optionLabels = labels;
        this.optionHovers = hovers;
        this.optionActionIds = ids;
        this.entityId = page.entityId();
    }

    public DialogOpenS2CPacket(FriendlyByteBuf buf) {
        this.npcId = buf.readUtf();
        this.sessionId = buf.readUtf();
        this.speaker = buf.readComponent();
        this.lines = buf.readList(buffer -> buffer.readComponent());
        this.optionLabels = buf.readList(buffer -> buffer.readComponent());
        this.optionHovers = buf.readList(buffer -> buffer.readComponent());
        this.optionActionIds = buf.readList(buffer -> buffer.readUtf());
        this.entityId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(npcId);
        buf.writeUtf(sessionId);
        buf.writeComponent(speaker);
        buf.writeCollection(lines, (buffer, line) -> buffer.writeComponent(line));
        buf.writeCollection(optionLabels, (buffer, label) -> buffer.writeComponent(label));
        buf.writeCollection(optionHovers, (buffer, hover) -> buffer.writeComponent(hover));
        buf.writeCollection(optionActionIds, (buffer, id) -> buffer.writeUtf(id));
        buf.writeInt(entityId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.pendingDialog = new ClientDialogData(npcId, sessionId, speaker, lines,
                    optionLabels, optionHovers, optionActionIds, entityId);
            ClientUtils.openDialogFlag = true;
        });
        return true;
    }
}
