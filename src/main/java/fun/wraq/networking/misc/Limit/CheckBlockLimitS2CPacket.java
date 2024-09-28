package fun.wraq.networking.misc.Limit;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.RemoveBlockLimitC2SPacket;
import fun.wraq.render.gui.blocks.BrewingMenu;
import fun.wraq.render.gui.blocks.ForgingBlockMenu;
import fun.wraq.render.gui.blocks.FurnaceMenu;
import fun.wraq.render.gui.blocks.InjectBlockMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CheckBlockLimitS2CPacket {
    public CheckBlockLimitS2CPacket() {

    }

    public CheckBlockLimitS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractContainerMenu abstractContainerMenu = Minecraft.getInstance().player.containerMenu;
            if (abstractContainerMenu instanceof ForgingBlockMenu
                    || abstractContainerMenu instanceof InjectBlockMenu
                    || abstractContainerMenu instanceof BrewingMenu
                    || abstractContainerMenu instanceof FurnaceMenu) {

            } else ModNetworking.sendToServer(new RemoveBlockLimitC2SPacket());
        });
        return true;
    }
}
