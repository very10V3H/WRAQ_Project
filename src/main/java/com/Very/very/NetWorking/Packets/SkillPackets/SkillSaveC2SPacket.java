package com.Very.very.NetWorking.Packets.SkillPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillSaveC2SPacket {
    private final String SwordSkills;
    private final String BowSkills;
    private final String ManaSkills;
    public SkillSaveC2SPacket(String swordSkills, String bowSkills, String manaSkills)
    {
        SwordSkills = swordSkills;
        BowSkills = bowSkills;
        ManaSkills = manaSkills;
    }
    public SkillSaveC2SPacket(FriendlyByteBuf buf)
    {
        SwordSkills = buf.readUtf();
        BowSkills = buf.readUtf();
        ManaSkills = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(SwordSkills);
        buf.writeUtf(BowSkills);
        buf.writeUtf(ManaSkills);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            data.putString(StringUtils.SkillData.Sword,SwordSkills);
            data.putString(StringUtils.SkillData.Bow,BowSkills);
            data.putString(StringUtils.SkillData.Mana,ManaSkills);
        });
        return true;
    }
}
