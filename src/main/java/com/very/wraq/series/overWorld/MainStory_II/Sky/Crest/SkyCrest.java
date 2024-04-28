package com.very.wraq.series.overWorld.MainStory_II.Sky.Crest;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.CrestPackets.CrestStatusS2CPacket;
import com.very.wraq.series.overWorld.MainStory_II.Sky.SkySuitDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class SkyCrest extends Item implements ICurioItem {

    private final int Level;
    private final String CrestName = StringUtils.Crest.Sky.Crest;

    public SkyCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.CritDamage.put(this,SkyCrestAttributes.CritDamage[Level]);
        Utils.ManaPenetration0.put(this,SkyCrestAttributes.ManaDefencePenetration0[Level]);
        Utils.CuriosTag.put(this,1d);
        Utils.CuriosList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style MainStyle = CustomStyle.styleOfSky;
        stack.setHoverName(Component.literal("天空纹章" + "(" + SkyCrestAttributes.LevelName[Level] + ")").
                withStyle(SkyCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);

        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        SkySuitDescription.SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal(CrestName + "-I").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(CrestName + Level,data.getInt(CrestName + Level) + 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(7,true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putInt(CrestName + Level,data.getInt(CrestName + Level) - 1);
        ModNetworking.sendToClient(new CrestStatusS2CPacket(7,false), (ServerPlayer) player);
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
