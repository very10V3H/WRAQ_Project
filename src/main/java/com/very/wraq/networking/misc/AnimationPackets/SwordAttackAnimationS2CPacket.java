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

public class SwordAttackAnimationS2CPacket {
    private final int PlayerID;

    public SwordAttackAnimationS2CPacket(int playerID) {
        this.PlayerID = playerID;
    }

    public SwordAttackAnimationS2CPacket(FriendlyByteBuf buf) {
        this.PlayerID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.PlayerID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().player.level().getEntity(PlayerID);
            if (entity instanceof Player) {
                Player player1 = (Player) entity;
                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player1).get(new ResourceLocation(Utils.MOD_ID, "animation"));
                if (animation != null) {
                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(Utils.MOD_ID, "sword_attack"))));
                }
            }
        });
        return true;
    }
}
