package com.Very.very.NetWorking.Packets.SkillPackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillDataC2SPacket {
    private final int Sword;
    private final int Bow;
    private final int Mana;

    public SkillDataC2SPacket(int Sword, int Bow, int Mana)
    {
        this.Sword = Sword;
        this.Bow = Bow;
        this.Mana = Mana;

    }
    public SkillDataC2SPacket(FriendlyByteBuf buf)
    {
        this.Sword = buf.readInt();
        this.Bow = buf.readInt();
        this.Mana = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.Sword);
        buf.writeInt(this.Bow);
        buf.writeInt(this.Mana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.Skill.SwordBase,data.getInt(StringUtils.Skill.SwordBase) + Sword);
            data.putInt(StringUtils.Skill.BowBase,data.getInt(StringUtils.Skill.BowBase) + Bow);
            data.putInt(StringUtils.Skill.ManaBase,data.getInt(StringUtils.Skill.ManaBase) + Mana);
            data.putInt(StringUtils.SkillPoint_Used,data.getInt(StringUtils.SkillPoint_Used) + Sword + Bow + Mana);
        });
        return true;
    }
}
