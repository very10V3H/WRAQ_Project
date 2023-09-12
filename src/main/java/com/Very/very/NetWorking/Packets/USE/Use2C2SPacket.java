package com.Very.very.NetWorking.Packets.USE;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Use2C2SPacket {
    public Use2C2SPacket()
    {

    }
    public Use2C2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            Inventory inventory = player.getInventory();
            ItemStack ToolStack = inventory.getItem(6);
            Item Tool = ToolStack.getItem();
            CompoundTag data = player.getPersistentData();
            if(player.getCooldowns().isOnCooldown(Tool))
            {
                if(!data.contains("IgnoreUse") || (!data.getBoolean("IgnoreUse"))) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("物品冷却中。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else
            {
                if(!ToolStack.isEmpty())
                {
                    Compute.USE(player,Tool);
                }
                else if(!data.contains("IgnoreUse") || (!data.getBoolean("IgnoreUse"))) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("物品不可用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
        });
        return true;
    }
}
