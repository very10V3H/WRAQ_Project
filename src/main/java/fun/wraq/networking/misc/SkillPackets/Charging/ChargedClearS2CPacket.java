package fun.wraq.networking.misc.SkillPackets.Charging;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargedClearS2CPacket {
    private final int Num;

    public ChargedClearS2CPacket(int Num) {
        this.Num = Num;
    }

    public ChargedClearS2CPacket(FriendlyByteBuf buf) {
        this.Num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            switch (Num) {
                case 0 -> {
                    ClientUtils.ChargedCountsSwordSkill12 = 0;
                    ClientUtils.ChargedFlagSwordSkill12 = true;
                }
                case 1 -> {
                    ClientUtils.ChargedCountsBowSkill12 = 0;
                    ClientUtils.ChargedFlagBowSkill12 = true;
                }
                case 2 -> {
                    ClientUtils.ChargedCountsManaSkill12 = 0;
                    ClientUtils.ChargedFlagManaSkill12 = true;
                }
                case 4 -> {
                    ClientUtils.ChargedCountsSakuraDemonSword = 0;
                    ClientUtils.ChargedFlagSakuraDemonSword = true;
                }
                case 5 -> {
                    ClientUtils.ChargedCountsZeusSword = 0;
                    ClientUtils.ChargedFlagZeusSword = true;
                }
            }
        });
        return true;
    }
}
