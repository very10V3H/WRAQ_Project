package fun.wraq.networking.afk;

import fun.wraq.process.system.afk.AfkSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端发送AFK操作（选择怪物/收获）
 * AI-Generated, 2026-05-17
 */
public class AfkOperationC2SPacket {
    public static final int OP_SELECT_MOB = 0;
    public static final int OP_HARVEST = 1;
    public static final int OP_REFRESH = 2;

    private final int operation;
    private final String mobTypeId;

    /** 选择怪物 */
    public AfkOperationC2SPacket(String mobTypeId) {
        this.operation = OP_SELECT_MOB;
        this.mobTypeId = mobTypeId;
    }

    /** 收获或刷新 */
    public AfkOperationC2SPacket(int operation) {
        this.operation = operation;
        this.mobTypeId = "";
    }

    public AfkOperationC2SPacket(FriendlyByteBuf buf) {
        this.operation = buf.readInt();
        this.mobTypeId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.operation);
        buf.writeUtf(this.mobTypeId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            switch (operation) {
                case OP_SELECT_MOB -> AfkSystem.handleSelectMob(player, mobTypeId);
                case OP_HARVEST -> AfkSystem.handleHarvest(player);
                case OP_REFRESH -> AfkSystem.syncToClient(player);
            }
        });
        return true;
    }
}
