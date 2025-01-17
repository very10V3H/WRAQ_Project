package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2PlayerTryToChooseProfessionTypeC2SPacket {

    private final int professionType;

    public SkillV2PlayerTryToChooseProfessionTypeC2SPacket(int professionType) {
        this.professionType = professionType;
    }

    public SkillV2PlayerTryToChooseProfessionTypeC2SPacket(FriendlyByteBuf buf) {
        this.professionType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(professionType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            SkillV2.setProfessionType(player, professionType);
            SkillV2.sendInfoToClient(player);
        });
        return true;
    }
}
