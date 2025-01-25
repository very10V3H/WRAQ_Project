package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2PlayerTryToSetSkillElementC2SPacket {

    private final int skillType;
    private final String elementType;

    public SkillV2PlayerTryToSetSkillElementC2SPacket(int skillType, String elementType) {
        this.skillType = skillType;
        this.elementType = elementType;
    }

    public SkillV2PlayerTryToSetSkillElementC2SPacket(FriendlyByteBuf buf) {
        this.skillType = buf.readInt();
        this.elementType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(skillType);
        buf.writeUtf(elementType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            SkillV2.onPlayerTryToSetSkillElementType(player, skillType, elementType);
        });
        return true;
    }
}
