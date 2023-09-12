package com.Very.very.NetWorking.Packets.SkillPackets.Charging;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SkillPackets.*;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargedFullC2SPacket {
    private final int Num;
    public ChargedFullC2SPacket(int Num)
    {
        this.Num = Num;
    }
    public ChargedFullC2SPacket(FriendlyByteBuf buf)
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
            ServerPlayer player = context.getSender();
            switch (Num) {
                case 0 -> Utils.SwordSkill12.put(player, true);
                case 1 -> Utils.BowSkill12.put(player, true);
                case 2 -> Utils.ManaSkill12.put(player, true);
                case 3 -> Utils.ManaSkill13.put(player, true);
            }
        });
        return true;
    }
}
