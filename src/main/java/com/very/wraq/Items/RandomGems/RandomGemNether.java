package com.very.wraq.Items.RandomGems;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.CrestPackets.CrestStatusS2CPacket;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class RandomGemNether extends Item implements ICurioItem {

    public RandomGemNether(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        if (player.experienceLevel >= 50) {
            Compute.AttributeProvider(player, stack);
            data.putInt(StringUtils.Crest.Nether, data.getInt(StringUtils.Crest.Nether) + 1);
            ModNetworking.sendToClient(new CrestStatusS2CPacket(9, true), (ServerPlayer) player);
        }
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        if (player.experienceLevel >= 50) {
            Compute.AttributeDecrease(player, stack);
            data.putInt(StringUtils.Crest.Nether, data.getInt(StringUtils.Crest.Nether) - 1);
            ModNetworking.sendToClient(new CrestStatusS2CPacket(9, false), (ServerPlayer) player);
        }
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
