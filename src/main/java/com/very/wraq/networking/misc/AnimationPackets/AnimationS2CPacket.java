package com.very.wraq.networking.misc.AnimationPackets;

import com.very.wraq.common.Utils.Utils;
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

    public AnimationS2CPacket(int playerID, String animationId) {
        this.playerId = playerID;
        this.animationId = animationId;
    }

    public AnimationS2CPacket(FriendlyByteBuf buf) {
        this.playerId = buf.readInt();
        this.animationId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.playerId);
        buf.writeUtf(this.animationId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().player.level().getEntity(playerId);
            if (entity instanceof Player player1) {
                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player1).get(new ResourceLocation(Utils.MOD_ID, "animation"));
                if (animation != null) {
                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(Utils.MOD_ID, animationId))));
                }
            }
        });
        return true;
    }
}
