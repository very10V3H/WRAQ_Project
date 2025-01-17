package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.process.system.skill.skillv2.SkillV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SkillV2InfoS2CPacket {

    private final int professionType;
    private final List<Integer> swordSkillLevel;
    private final List<Integer> bowSkillLevel;
    private final List<Integer> manaSkillLevel;
    private final List<Integer> currentSkillSerial;

    public SkillV2InfoS2CPacket(int professionType, List<Integer> swordSkillLevel,
                                List<Integer> bowSkillLevel, List<Integer> manaSkillLevel,
                                List<Integer> currentSkillSerial) {
        this.professionType = professionType;
        this.swordSkillLevel = swordSkillLevel;
        this.bowSkillLevel = bowSkillLevel;
        this.manaSkillLevel = manaSkillLevel;
        this.currentSkillSerial = currentSkillSerial;
    }

    public SkillV2InfoS2CPacket(FriendlyByteBuf buf) {
        this.professionType = buf.readInt();
        this.swordSkillLevel = buf.readList(FriendlyByteBuf::readInt);
        this.bowSkillLevel = buf.readList(FriendlyByteBuf::readInt);
        this.manaSkillLevel = buf.readList(FriendlyByteBuf::readInt);
        this.currentSkillSerial = buf.readList(FriendlyByteBuf::readInt);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(professionType);
        buf.writeCollection(swordSkillLevel, FriendlyByteBuf::writeInt);
        buf.writeCollection(bowSkillLevel, FriendlyByteBuf::writeInt);
        buf.writeCollection(manaSkillLevel, FriendlyByteBuf::writeInt);
        buf.writeCollection(currentSkillSerial, FriendlyByteBuf::writeInt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SkillV2.clientProfessionType = professionType;
            SkillV2.clientSwordSkillLevel = swordSkillLevel;
            SkillV2.clientBowSkillLevel = bowSkillLevel;
            SkillV2.clientManaSkillLevel = manaSkillLevel;
            SkillV2.clientCurrentSkillSerial = currentSkillSerial;
            SkillV2.screenRefreshFlag = true;
        });
        return true;
    }
}
