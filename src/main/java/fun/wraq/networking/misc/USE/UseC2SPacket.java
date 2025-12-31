package fun.wraq.networking.misc.USE;

import fun.wraq.common.Compute;
import fun.wraq.process.system.expired.ExpiredSystem;
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
            // 判断物品是否过期
            if (!ExpiredSystem.checkValid(toolStack)) {
                return;
            }
            Item tool = toolStack.getItem();
            if (!player.getCooldowns().isOnCooldown(tool) && !toolStack.isEmpty()) {
                Compute.use(player, tool);
            }
        });
        return true;
    }
}
