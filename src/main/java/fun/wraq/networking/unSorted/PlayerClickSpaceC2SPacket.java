package fun.wraq.networking.unSorted;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
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
    public static Map<Player, Integer> jumpTimes = new WeakHashMap<>();

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (nextAllowJumpTickMap.getOrDefault(player, 0) < Tick.get()) {
                nextAllowJumpTickMap.put(player, Tick.get() + 50);
                Compute.sendCoolDownTime(player, "item/wind_bottle", 50);

                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                        new ClientboundSetEntityMotionPacket(player.getId(), player.getDeltaMovement().add(0, 1, 0));
                player.connection.send(clientboundSetEntityMotionPacket);

                MySound.soundToPlayer(player, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
                ParticleProvider.DisperseParticle(player.position(),
                        (ServerLevel) player.level(), 0, 1, 16, ParticleTypes.FIREWORK, 1);

                jumpTimes.compute(player, (k, v) -> v == null ? 0 : v + 1);
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket;
                if (jumpTimes.get(player) % 2 == 0) {
                    clientboundSetActionBarTextPacket =
                            new ClientboundSetActionBarTextPacket(Te.s(
                                    "处于", "低重力", CustomStyle.styleOfFlexible, "区域时",
                                    "，间隔一定时间按下", "空格", CustomStyle.styleOfFlexible, "以", "再次跳跃", CustomStyle.styleOfFlexible));
                } else {
                    clientboundSetActionBarTextPacket =
                            new ClientboundSetActionBarTextPacket(Te.s(
                                    "处于", "低重力", CustomStyle.styleOfFlexible, "区域时",
                                    "，按下", "左shift", ChatFormatting.AQUA, "以", "快速下降", CustomStyle.styleOfFlexible));
                }
                player.connection.send(clientboundSetActionBarTextPacket);
            }
        });
        return true;
    }
}
