package fun.wraq.networking.misc.AnimationPackets;

import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AnimationS2CPacket {
    private final int playerId;
    private final String animationId;
    private final int lastTick;
    private final float speed;

    public AnimationS2CPacket(int playerID, String animationId, int lastTick, float speed) {
        this.playerId = playerID;
        this.animationId = animationId;
        this.lastTick = lastTick;
        this.speed = speed;
    }

    public AnimationS2CPacket(FriendlyByteBuf buf) {
        this.playerId = buf.readInt();
        this.animationId = buf.readUtf();
        this.lastTick = buf.readInt();
        this.speed = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.playerId);
        buf.writeUtf(this.animationId);
        buf.writeInt(this.lastTick);
        buf.writeFloat(this.speed);
    }

    @SuppressWarnings("ALL")
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().player.level().getEntity(playerId);
            if (entity instanceof Player player1) {
                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess
                        .getPlayerAssociatedData((AbstractClientPlayer) player1)
                        .get(new ResourceLocation(Utils.MOD_ID, "animation"));
                if (animation != null) {
                    while (animation.size() > 0) {
                        animation.removeModifier(0);
                    }
                    animation.addModifierLast(new SpeedModifier(speed));
                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry
                            .getAnimation(new ResourceLocation(Utils.MOD_ID, animationId))));
                }
            }
            if (Minecraft.getInstance().player.equals(entity)) {
                DelayOperationWithAnimation.clientPlayerAnimationPlayingLeftTick = lastTick;
            }
        });
        return true;
    }
}
