package fun.wraq.series.overworld.chapter1.forest.crest;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.CrestPackets.CrestStatusS2CPacket;
import fun.wraq.common.equip.impl.CrestItem;
import fun.wraq.series.overworld.chapter1.forest.ForestSuitDescription;
import fun.wraq.series.overworld.chapter1.forest.crest.ForestCrestAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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

public class ForestCrest extends Item implements ICurioItem, CrestItem {

    private final int Level;
    private final String CrestName = "ForestCrest";

    public ForestCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.maxHealth.put(this, ForestCrestAttributes.MaxHealth[Level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting MainStyle = ChatFormatting.DARK_GREEN;
        stack.setHoverName(Component.literal("森林纹章" + "(" + ForestCrestAttributes.LevelName[Level] + ")").
                withStyle(ForestCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);

        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) ForestSuitDescription.SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        components.add(Component.literal(CrestName + "-I").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        ModNetworking.sendToClient(new CrestStatusS2CPacket(2, true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        ModNetworking.sendToClient(new CrestStatusS2CPacket(2, false), (ServerPlayer) player);
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
