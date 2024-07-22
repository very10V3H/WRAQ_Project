package com.very.wraq.process.system.missions.series.labourDay.netWorking;

import com.very.wraq.process.system.missions.Mission;
import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LabourDayMissionStatusS2CPacket {

    private final int mineCount;
    private final int lopCount;
    private final int cropCount;
    private final int fishCount;

    public LabourDayMissionStatusS2CPacket(int mineCount, int lopCount, int cropCount, int fishCount) {
        this.mineCount = mineCount;
        this.lopCount = lopCount;
        this.cropCount = cropCount;
        this.fishCount = fishCount;
    }

    public LabourDayMissionStatusS2CPacket(FriendlyByteBuf buf) {
        this.mineCount = buf.readInt();
        this.lopCount = buf.readInt();
        this.cropCount = buf.readInt();
        this.fishCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(mineCount);
        buf.writeInt(lopCount);
        buf.writeInt(cropCount);
        buf.writeInt(fishCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LabourDayMission.clientMineCounts = mineCount;
            LabourDayMission.clientLopCounts = lopCount;
            LabourDayMission.clientCropCounts = cropCount;
            LabourDayMission.clientFishCounts = fishCount;
            Mission.setDetailContent();
        });
        return true;
    }

}

