package com.very.wraq.networking.misc.SkillPackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillDataS2CPacket {
    private final int Sword;
    private final int Bow;
    private final int Mana;

    public SkillDataS2CPacket(int Sword, int Bow, int Mana) {
        this.Sword = Sword;
        this.Bow = Bow;
        this.Mana = Mana;

    }

    public SkillDataS2CPacket(FriendlyByteBuf buf) {
        this.Sword = buf.readInt();
        this.Bow = buf.readInt();
        this.Mana = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Sword);
        buf.writeInt(this.Bow);
        buf.writeInt(this.Mana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.SkillChangeCache.Sword = Sword;
            ClientUtils.SkillChangeCache.Bow = Bow;
            ClientUtils.SkillChangeCache.Mana = Mana;
            ClientUtils.SkillCache.Sword = Sword;
            ClientUtils.SkillCache.Bow = Bow;
            ClientUtils.SkillCache.Mana = Mana;
        });
        return true;
    }
}
