package fun.wraq.networking.unSorted;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public class PlayerClickSpaceC2SPacket {
    public PlayerClickSpaceC2SPacket() {

    }

    public PlayerClickSpaceC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public static Map<Player, Integer> nextAllowJumpTickMap = new WeakHashMap<>();

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (nextAllowJumpTickMap.getOrDefault(player, 0) < Tick.get()) {
                nextAllowJumpTickMap.put(player, Tick.get() + 50);
                Compute.sendCoolDownTime(player, ModItems.windBottle.get(), 50);
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                        new ClientboundSetEntityMotionPacket(player.getId(), player.getDeltaMovement().add(0, 1, 0));
                player.connection.send(clientboundSetEntityMotionPacket);
                MySound.soundToPlayer(player, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
                ParticleProvider.DisperseParticle(player.position(),
                        (ServerLevel) player.level(), 0, 1, 16, ParticleTypes.FIREWORK, 1);
            }
        });
        return true;
    }
}
