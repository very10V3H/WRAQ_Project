package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.OldMission;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionInfoS2CPacket {
    private final String Title;
    private final String Content;
    private final double DesX;
    private final double DesY;
    private final double DesZ;

    public MissionInfoS2CPacket(String Title, String Content, double DesX, double DesY, double DesZ) {
        this.Title = Title;
        this.Content = Content;
        this.DesX = DesX;
        this.DesY = DesY;
        this.DesZ = DesZ;
    }

    public MissionInfoS2CPacket(FriendlyByteBuf buf) {
        this.Title = buf.readUtf();
        this.Content = buf.readUtf();
        this.DesX = buf.readDouble();
        this.DesY = buf.readDouble();
        this.DesZ = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.Title);
        buf.writeUtf(this.Content);
        buf.writeDouble(this.DesX);
        buf.writeDouble(this.DesY);
        buf.writeDouble(this.DesZ);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            OldMission oldMission = new OldMission(Title, Content, new Vec3(DesX, DesY, DesZ));
            ClientUtils.oldMissionList.add(oldMission);
            ClientUtils.Mission = true;
        });
        return true;
    }
}
