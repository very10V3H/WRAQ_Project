package com.Very.very.Blocks.Brewing;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrewingNote extends Item {

    public BrewingNote(Properties p_41383_) {
        super(p_41383_);
        Utils.MainHandTag.put(this,1f);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
        components.add(Component.literal("酿造师的酿造笔记。"));
        components.add(Component.literal("在笔记中记录了酿造师的酿造尝试。"));

        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getPersistentData();
            data.putInt("BrewingLevel", Compute.BrewingLevel(player.getItemInHand(InteractionHand.MAIN_HAND)));
        }
        return super.use(level, player, interactionHand);
    }
}
