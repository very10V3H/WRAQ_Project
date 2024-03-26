package com.Very.very.NetWorking;

import com.Very.very.NetWorking.DailyMission.DailyMissionContentS2CPacket;
import com.Very.very.NetWorking.DailyMission.DailyMissionFinishedTimeS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.Calendar;
import java.util.function.Supplier;

public class PlayerIsNearbyCampfireC2SPacket {
    public PlayerIsNearbyCampfireC2SPacket()
    {

    }
    public PlayerIsNearbyCampfireC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            Compute.PlayerColdNumAddOrCost(serverPlayer,-2);
        });
        return true;
    }
}
