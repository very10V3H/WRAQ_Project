package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2CooldownS2CPacket {

    private final int skillType;
    private final int originCooldownTick;
    private final int leftCooldownTick;

    public SkillV2CooldownS2CPacket(int skillType, int originCooldownTick, int leftCooldownTick) {
        this.skillType = skillType;
        this.originCooldownTick = originCooldownTick;
        this.leftCooldownTick = leftCooldownTick;
    }

    public SkillV2CooldownS2CPacket(FriendlyByteBuf buf) {
        this.skillType = buf.readInt();
        this.originCooldownTick = buf.readInt();
        this.leftCooldownTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(skillType);
        buf.writeInt(originCooldownTick);
        buf.writeInt(leftCooldownTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SkillV2.clientOriginCooldownTick.put(skillType, originCooldownTick);
            SkillV2.clientLeftCooldownTick.put(skillType, leftCooldownTick);
        });
        return true;
    }
}
