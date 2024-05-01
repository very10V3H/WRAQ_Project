package com.very.wraq.netWorking.misc.AnimationPackets;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class BowAnimationRequestC2SPacket {
    private final int count;
    public BowAnimationRequestC2SPacket(int count)
    {
        this.count = count;
    }
    public BowAnimationRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            CompoundTag data = serverPlayer.getPersistentData();
            int TickCount = serverPlayer.getServer().getTickCount();
            data.putInt(StringUtils.BowAttackSlowDown,TickCount + 5);
            if (!data.contains(StringUtils.BowSlowDown) || data.getBoolean(StringUtils.BowSlowDown))
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,3,false,false));
            List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
            playerList.forEach(serverPlayer1 -> {
                ModNetworking.sendToClient(new BowAnimationS2CPacket(serverPlayer.getId(),count),serverPlayer1);
            });
        });
        return true;
    }
}
