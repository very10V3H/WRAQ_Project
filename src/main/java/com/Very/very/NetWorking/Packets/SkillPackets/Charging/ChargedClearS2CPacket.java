package com.Very.very.NetWorking.Packets.SkillPackets.Charging;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargedClearS2CPacket {
    private final int Num;
    public ChargedClearS2CPacket(int Num)
    {
        this.Num = Num;
    }
    public ChargedClearS2CPacket(FriendlyByteBuf buf)
    {
        this.Num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.Num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
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
                case 3 -> {
                    ClientUtils.ChargedCountsManaSkill13 = 0;
                    ClientUtils.ChargedFlagManaSkill13 = true;
                }
            }
        });
        return true;
    }
}
