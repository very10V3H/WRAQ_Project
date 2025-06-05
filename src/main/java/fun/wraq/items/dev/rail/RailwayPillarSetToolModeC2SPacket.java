package fun.wraq.items.dev.rail;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RailwayPillarSetToolModeC2SPacket {

    public RailwayPillarSetToolModeC2SPacket() {}

    public RailwayPillarSetToolModeC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            if (player == null) return;
            if (player.isShiftKeyDown()) {
                RailwayPillarSetTool.mode--;
                if (RailwayPillarSetTool.mode < 0) {
                    RailwayPillarSetTool.mode = 8;
                }
            } else {
                RailwayPillarSetTool.mode = (RailwayPillarSetTool.mode + 1) % 9;
            }
            switch (RailwayPillarSetTool.mode) {
                case 0 -> player.sendSystemMessage(Te.s("标准模式"));
                case 1 -> player.sendSystemMessage(Te.s("仅传送标准模式"));
                case 2 -> player.sendSystemMessage(Te.s("标准模式(轨上)"));
                case 3 -> player.sendSystemMessage(Te.s("斜铺模式"));
                case 4 -> player.sendSystemMessage(Te.s("仅传送斜铺模式"));
                case 5 -> player.sendSystemMessage(Te.s("撤销模式"));
                case 6 -> player.sendSystemMessage(Te.s("清理模式"));
                case 7 -> player.sendSystemMessage(Te.s("切弯模式 - 90°"));
                case 8 -> player.sendSystemMessage(Te.s("设置落差模式"));
            }
            MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
            player.getMainHandItem().resetHoverName();
        });
        return true;
    }
}
