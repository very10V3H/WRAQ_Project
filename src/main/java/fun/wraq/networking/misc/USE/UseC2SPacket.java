package fun.wraq.networking.misc.USE;

import fun.wraq.common.Compute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseC2SPacket {

    private final int num;
    public UseC2SPacket(int num) {
        this.num = num;
    }

    public UseC2SPacket(FriendlyByteBuf buf) {
        this.num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Inventory inventory = player.getInventory();
            ItemStack toolStack = inventory.getItem(num);
            Item tool = toolStack.getItem();
            if (!player.getCooldowns().isOnCooldown(tool) && !toolStack.isEmpty()) {
                Compute.use(player, tool);
            }
        });
        return true;
    }
}
