package fun.wraq.render.hud.networking;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.render.hud.main.ItemAndExpGetHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemGetS2CPacket {

    private final ItemStack stack;

    public ItemGetS2CPacket(ItemStack stack) {
        this.stack = stack;
    }

    public ItemGetS2CPacket(FriendlyByteBuf buf) {
        this.stack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.stack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ItemAndExpGetHud.displayStacks.stream().anyMatch(stack -> stack.is(this.stack.getItem()))) {
                ItemAndExpGetHud.displayStacks
                        .stream().filter(stack -> stack.is(this.stack.getItem()))
                        .findAny()
                        .ifPresentOrElse(stack -> stack.setCount(stack.getCount() + this.stack.getCount()), () -> {
                            ItemAndExpGetHud.getItemStacks.add(stack);
                        });
                ItemAndExpGetHud.displayStartTick = ClientUtils.clientPlayerTick;
            } else {
                ItemAndExpGetHud.getItemStacks
                        .stream().filter(stack -> stack.is(this.stack.getItem()))
                        .findAny()
                        .ifPresentOrElse(stack -> stack.setCount(stack.getCount() + this.stack.getCount()), () -> {
                            ItemAndExpGetHud.getItemStacks.add(stack);
                        });
            }
        });
        return true;
    }
}
