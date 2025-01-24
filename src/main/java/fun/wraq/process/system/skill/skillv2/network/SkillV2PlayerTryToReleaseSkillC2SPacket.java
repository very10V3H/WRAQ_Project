package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2PlayerTryToReleaseSkillC2SPacket {

    private final int skillType;

    public SkillV2PlayerTryToReleaseSkillC2SPacket(int skillType) {
        this.skillType = skillType;
    }

    public SkillV2PlayerTryToReleaseSkillC2SPacket(FriendlyByteBuf buf) {
        this.skillType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(skillType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            SkillV2.onPlayerTryToReleaseSkillBySkillType(player, skillType);
        });
        return true;
    }
}
