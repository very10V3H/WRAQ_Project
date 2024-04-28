package com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Crest;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.CrestPackets.CrestStatusS2CPacket;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.LakeSuitDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class LakeCrest extends Item implements ICurioItem {

    private final int Level;
    private final String CrestName = "LakeCrest";
    public LakeCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.CoolDownDecrease.put(this,LakeCrestAttributes.CoolDown[Level]);
        Utils.CuriosTag.put(this,1d);
        Utils.CuriosList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting MainStyle = ChatFormatting.BLUE;
        stack.setHoverName(Component.literal("湖泊纹章" + "(" + LakeCrestAttributes.LevelName[Level] + ")").
                withStyle(LakeCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);

        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        LakeSuitDescription.LakeSuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal(CrestName + "-I").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Crest.Lake.Crest + Level,data.getInt(StringUtils.Crest.Lake.Crest + Level) + 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(3,true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Crest.Lake.Crest + Level,data.getInt(StringUtils.Crest.Lake.Crest + Level) - 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(3,false), (ServerPlayer) player);
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
