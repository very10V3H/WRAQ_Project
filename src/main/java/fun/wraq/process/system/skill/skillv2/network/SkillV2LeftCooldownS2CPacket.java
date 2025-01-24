package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2LeftCooldownS2CPacket {

    private final int skillType;
    private final int leftCooldownTick;

    public SkillV2LeftCooldownS2CPacket(int skillType, int leftCooldownTick) {
        this.skillType = skillType;
        this.leftCooldownTick = leftCooldownTick;
    }

    public SkillV2LeftCooldownS2CPacket(FriendlyByteBuf buf) {
        this.skillType = buf.readInt();
        this.leftCooldownTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(skillType);
        buf.writeInt(leftCooldownTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SkillV2.clientLeftCooldownTick.put(skillType, leftCooldownTick);
        });
        return true;
    }
}
