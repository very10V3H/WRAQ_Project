package com.very.wraq.series.overWorld.MainStory_I.Plain.Runes_Plain;

import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GreenRunes_0 extends Item{
    public GreenRunes_0(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.RuneAttributeDescription(components);
        components.add(Component.literal("每5秒回复自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.LossHealth("10%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt("Green",0);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("平原符石-复苏之风").withStyle(ChatFormatting.GREEN).append(Component.literal("已激活").withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString(InventoryCheck.owner,player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
