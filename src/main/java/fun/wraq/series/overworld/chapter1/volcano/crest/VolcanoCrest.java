package fun.wraq.series.overworld.chapter1.volcano.crest;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.CrestPackets.CrestStatusS2CPacket;
import fun.wraq.common.equip.impl.CrestItem;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoSuitDescription;
import fun.wraq.series.overworld.chapter1.volcano.crest.VolcanoCrestAttributes;
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

public class VolcanoCrest extends Item implements ICurioItem, CrestItem {

    private final int Level;
    private final String CrestName = "VolcanoCrest";

    public VolcanoCrest(Properties p_41383_, int Level) {
        super(p_41383_);
        this.Level = Level;
        Utils.attackDamage.put(this, VolcanoCrestAttributes.ExAttackDamage[Level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting MainStyle = ChatFormatting.YELLOW;
        stack.setHoverName(Component.literal("火山纹章" + "(" + VolcanoCrestAttributes.LevelName[Level] + ")").
                withStyle(VolcanoCrestAttributes.LevelColor[Level]).withStyle(ChatFormatting.BOLD));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);

        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) VolcanoSuitDescription.VolcanoSuitDescription(components);
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
        ModNetworking.sendToClient(new CrestStatusS2CPacket(4, true), (ServerPlayer) player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        ModNetworking.sendToClient(new CrestStatusS2CPacket(4, false), (ServerPlayer) player);
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
