package com.Very.very.NetWorking.Packets.USE;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MoveToC2SPacket {
    private final double x;
    private final double y;
    private final double z;
    public MoveToC2SPacket(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public MoveToC2SPacket(FriendlyByteBuf buf)
    {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            HitResult hitResult = player.pick(10,0,true);
            player.teleportTo(hitResult.getLocation().x,hitResult.getLocation().y,hitResult.getLocation().z);
            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                    new ClientboundSetEntityMotionPacket(player.getId(),new Vec3(x,y,z));
            player.connection.send(clientboundSetEntityMotionPacket);
        });
        return true;
    }
}
