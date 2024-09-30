package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class PlainRing extends Item implements ICurioItem {

    public PlainRing(Properties p_41383_) {
        super(p_41383_);
        Utils.critDamage.put(this, 0.1);
        Utils.manaPenetration0.put(this, 10d);
        Utils.expUp.put(this, 0.1);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        stack.setHoverName(Component.literal("平原戒指").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        components.add(Component.literal("增加0.5攀登高度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.YELLOW));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(ComponentUtils.getSuffixOfChapterI());
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.0);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
