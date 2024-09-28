package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.util.Utils;
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

public class ManaAttackAnimationS2CPacket {
    private final int playerId;
    private final int count;

    public ManaAttackAnimationS2CPacket(int playerID, int count) {
        this.playerId = playerID;
        this.count = count;
    }

    public ManaAttackAnimationS2CPacket(FriendlyByteBuf buf) {
        this.playerId = buf.readInt();
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.playerId);
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().player.level().getEntity(playerId);
            if (entity instanceof Player) {
                Player player1 = (Player) entity;
                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player1).get(new ResourceLocation(Utils.MOD_ID, "animation"));
                if (animation != null) {
                    switch (count) {
                        case 0 -> {
                            animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(Utils.MOD_ID, "mana_attack1"))));
                        }
                    }
                }
            }
        });
        return true;
    }
}
