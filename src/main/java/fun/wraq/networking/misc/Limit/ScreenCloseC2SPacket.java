package fun.wraq.networking.misc.Limit;

import fun.wraq.blocks.entity.ForgingBlockEntity;
import fun.wraq.blocks.entity.FurnaceEntity;
import fun.wraq.blocks.entity.HBrewingEntity;
import fun.wraq.blocks.entity.InjectBlockEntity;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.unSorted.PlayerCallBack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenCloseC2SPacket {

    public ScreenCloseC2SPacket() {

    }

    public ScreenCloseC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            for (PlayerCallBack playerCallBack : Utils.playerCallBackList) {
                if (playerCallBack.getPlayer().getName().getString().equals(player.getName().getString())) {
                    BlockEntity blockEntity = player.level().getBlockEntity(playerCallBack.getBlockPos());
                    if (blockEntity instanceof ForgingBlockEntity forgingBlockEntity) forgingBlockEntity.drops(player);
                    if (blockEntity instanceof HBrewingEntity hBrewingEntity) hBrewingEntity.drops(player);
                    if (blockEntity instanceof InjectBlockEntity injectBlockEntity) injectBlockEntity.drops(player);
                    if (blockEntity instanceof FurnaceEntity furnaceEntity) furnaceEntity.drops(player);
                    Utils.playerCallBackList.remove(playerCallBack);
                    break;
                }
            }
        });
        return true;
    }
}
