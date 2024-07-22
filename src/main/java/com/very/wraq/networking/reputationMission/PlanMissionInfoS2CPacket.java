package com.very.wraq.networking.reputationMission;

import com.very.wraq.render.gui.mission.OldMissionScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlanMissionInfoS2CPacket {

    private final ItemStack planMissionItem;
    private final int planMissionItemCount;
    private final String planMissionStartTime;
    private final String planMissionAllowRequestTime;

    public PlanMissionInfoS2CPacket(ItemStack planMissionItem, int planMissionItemCount,
                                    String planMissionStartTime, String planMissionAllowRequestTime) {
        this.planMissionItem = planMissionItem;
        this.planMissionItemCount = planMissionItemCount;
        this.planMissionStartTime = planMissionStartTime;
        this.planMissionAllowRequestTime = planMissionAllowRequestTime;
    }

    public PlanMissionInfoS2CPacket(FriendlyByteBuf buf) {
        this.planMissionItem = buf.readItem();
        this.planMissionItemCount = buf.readInt();
        this.planMissionStartTime = buf.readUtf();
        this.planMissionAllowRequestTime = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.planMissionItem);
        buf.writeInt(this.planMissionItemCount);
        buf.writeUtf(this.planMissionStartTime);
        buf.writeUtf(this.planMissionAllowRequestTime);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            OldMissionScreen.planMissionItem = planMissionItem;
            OldMissionScreen.planMissionItemCount = planMissionItemCount;
            OldMissionScreen.planMissionStartTime = planMissionStartTime;
            OldMissionScreen.planMissionAllowRequestTime = planMissionAllowRequestTime;
        });
        return true;
    }
}
