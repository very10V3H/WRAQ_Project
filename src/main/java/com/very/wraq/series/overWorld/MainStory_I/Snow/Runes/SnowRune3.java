package com.very.wraq.series.overWorld.MainStory_I.Snow.Runes;

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

public class SnowRune3 extends Item {
    public SnowRune3(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("每过").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("你的下次箭矢将对目标周围造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("250%")).
                append(Component.literal("的物理伤害并附带").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("3s减速效果").withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-Snow").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt("snowRune",3);
            player.getPersistentData().putInt("snowRune3",100);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("冰川符石-冰霜箭矢").withStyle(ChatFormatting.AQUA).append(Component.literal("已激活").withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString(InventoryCheck.owner,player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
