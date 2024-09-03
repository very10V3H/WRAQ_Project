package com.very.wraq.networking.misc.SkillPackets;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.common.util.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillRequestC2SPacket {
    public SkillRequestC2SPacket() {

    }

    public SkillRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            int XpLevel = player.experienceLevel;

            if (!data.contains(StringUtils.SkillPoint_Total)) {
                data.putInt(StringUtils.SkillPoint_Total, XpLevel);
            }

            if (!data.contains(StringUtils.AbilityPoint_Total)) {
                data.putInt(StringUtils.AbilityPoint_Total, XpLevel);
            }

            if (!data.contains(StringUtils.SkillPoint_Used)) {
                data.putInt(StringUtils.SkillPoint_Used, 0);
            }

            if (!data.contains(StringUtils.AbilityPoint_Used)) {
                data.putInt(StringUtils.AbilityPoint_Used, 0);
            }

            for (int i = 0; i < 5; i++) {
                if (!data.contains(StringUtils.AbilityArray[i])) {
                    data.putInt(StringUtils.AbilityArray[i], 0);
                }
            }

            for (int i = 0; i < 3; i++) {
                if (!data.contains(StringUtils.SkillArray[i])) {
                    data.putInt(StringUtils.SkillArray[i], 0);
                }
            }

            ModNetworking.sendToClient(new SkillPointS2CPacket(data.getInt(StringUtils.SkillPoint_Total), data.getInt(StringUtils.SkillPoint_Used)), player);
            ModNetworking.sendToClient(new AbilityPointS2CPacket(data.getInt(StringUtils.AbilityPoint_Total), data.getInt(StringUtils.AbilityPoint_Used)), player);

            ModNetworking.sendToClient(new AbilityDataS2CPacket(data.getInt(StringUtils.AbilityArray[0]), data.getInt(StringUtils.AbilityArray[1]),
                    data.getInt(StringUtils.AbilityArray[2]), data.getInt(StringUtils.AbilityArray[3]), data.getInt(StringUtils.AbilityArray[4])), player);

            ModNetworking.sendToClient(new SkillDataS2CPacket(data.getInt(StringUtils.SkillArray[0]), data.getInt(StringUtils.SkillArray[1]), data.getInt(StringUtils.SkillArray[2])), player);

            if (!data.contains(StringUtils.SkillData.Sword)) {
                StringBuilder SwordData = new StringBuilder();
                for (int i = 0; i < 15; i++) {
                    SwordData.append('0');
                }
                data.putString(StringUtils.SkillData.Sword, SwordData.toString());
            }
            if (!data.contains(StringUtils.SkillData.Bow)) {
                StringBuilder BowData = new StringBuilder();
                for (int i = 0; i < 15; i++) {
                    BowData.append('0');
                }
                data.putString(StringUtils.SkillData.Bow, BowData.toString());
            }
            if (!data.contains(StringUtils.SkillData.Mana)) {
                StringBuilder ManaData = new StringBuilder();
                for (int i = 0; i < 15; i++) {
                    ManaData.append('0');
                }
                data.putString(StringUtils.SkillData.Mana, ManaData.toString());
            }

            ModNetworking.sendToClient(new SkillSaveS2CPacket(data.getString(StringUtils.SkillData.Sword),
                    data.getString(StringUtils.SkillData.Bow), data.getString(StringUtils.SkillData.Mana)), player);
        });
        return true;
    }
}
