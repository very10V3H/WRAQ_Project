package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2PlayerTryToUpgradeSkillC2SPacket {

    private final int professionType;
    private final int skillType;
    private final int serial;

    public SkillV2PlayerTryToUpgradeSkillC2SPacket(int professionType, int skillType, int serial) {
        this.professionType = professionType;
        this.skillType = skillType;
        this.serial = serial;
    }

    public SkillV2PlayerTryToUpgradeSkillC2SPacket(FriendlyByteBuf buf) {
        this.professionType = buf.readInt();
        this.skillType = buf.readInt();
        this.serial = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(professionType);
        buf.writeInt(skillType);
        buf.writeInt(serial);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            SkillV2.onPlayerTryToUpgradeSkillByTypeAndSerial(player, professionType, skillType, serial);
        });
        return true;
    }
}
