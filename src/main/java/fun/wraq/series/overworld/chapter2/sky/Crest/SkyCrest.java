package fun.wraq.series.overworld.chapter2.sky.Crest;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.CrestPackets.CrestStatusS2CPacket;
import fun.wraq.common.equip.impl.CrestItem;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrestAttributes;
import fun.wraq.series.overworld.chapter2.sky.SkySuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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

public class SkyCrest extends Item implements ICurioItem, CrestItem {

    private final int Level;
    private final String CrestName = StringUtils.Crest.Sky.Crest;

    public SkyCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.critDamage.put(this, SkyCrestAttributes.CritDamage[Level]);
        Utils.manaPenetration0.put(this, SkyCrestAttributes.ManaDefencePenetration0[Level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style MainStyle = CustomStyle.styleOfSky;
        stack.setHoverName(Component.literal("天空纹章" + "(" + SkyCrestAttributes.LevelName[Level] + ")").
                withStyle(SkyCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);

        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) SkySuitDescription.SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        components.add(Component.literal(CrestName + "-I").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        ModNetworking.sendToClient(new CrestStatusS2CPacket(7, true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        ModNetworking.sendToClient(new CrestStatusS2CPacket(7, false), (ServerPlayer) player);
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