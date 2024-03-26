package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Crest;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.CrestPackets.CrestStatusS2CPacket;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.PlainSuitDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class PlainCrest extends Item implements ICurioItem {

    private final int Level;
    public PlainCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.HealthRecover.put(this,PlainCrestAttributes.HealthRecover[Level]);
        Utils.CuriosTag.put(this,1d);
        Utils.CuriosList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting MainStyle = ChatFormatting.GREEN;
        stack.setHoverName(Component.literal("平原纹章" + "(" + PlainCrestAttributes.LevelName[Level] + ")").
                withStyle(PlainCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);

        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        PlainSuitDescription.SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("PlainCrest-I").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Crest.Plain.Crest + Level,data.getInt(StringUtils.Crest.Plain.Crest + Level) + 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(1,true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Crest.Plain.Crest + Level,data.getInt(StringUtils.Crest.Plain.Crest + Level) - 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(1,false), (ServerPlayer) player);
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
