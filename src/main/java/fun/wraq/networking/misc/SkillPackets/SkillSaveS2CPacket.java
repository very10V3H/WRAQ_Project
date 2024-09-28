package fun.wraq.networking.misc.SkillPackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillSaveS2CPacket {
    private final String SwordSkills;
    private final String BowSkills;
    private final String ManaSkills;

    public SkillSaveS2CPacket(String swordSkills, String bowSkills, String manaSkills) {
        SwordSkills = swordSkills;
        BowSkills = bowSkills;
        ManaSkills = manaSkills;
    }

    public SkillSaveS2CPacket(FriendlyByteBuf buf) {
        SwordSkills = buf.readUtf();
        BowSkills = buf.readUtf();
        ManaSkills = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(SwordSkills);
        buf.writeUtf(BowSkills);
        buf.writeUtf(ManaSkills);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            for (int i = 1; i <= 15; i++) {
                if (SwordSkills.charAt(i - 1) == 'X') ClientUtils.SwordSkillPoint.Point[i] = 10;
                else ClientUtils.SwordSkillPoint.Point[i] = SwordSkills.charAt(i - 1) - 48;
                ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
                if (BowSkills.charAt(i - 1) == 'X') ClientUtils.BowSkillPoint.Point[i] = 10;
                else ClientUtils.BowSkillPoint.Point[i] = BowSkills.charAt(i - 1) - 48;
                ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
                if (ManaSkills.charAt(i - 1) == 'X') ClientUtils.ManaSkillPoint.Point[i] = 10;
                else ClientUtils.ManaSkillPoint.Point[i] = ManaSkills.charAt(i - 1) - 48;
                ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            }
        });
        return true;
    }
}
