package com.Very.very.Series.OverWorldSeries.MainStory_II.Dimension;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverFishSoul extends Item {
    public SilverFishSoul(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料/道具").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("前往终末之地。").withStyle(Utils.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("等级需求:75").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE));
        components.add(Component.literal("Items-End").withStyle(Utils.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (player.experienceLevel >= 75) {
                ServerLevel end = level.getServer().getLevel(Level.END);
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.teleportTo(end,100.5,50,0.5,0,0);
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                itemStack.setCount(itemStack.getCount() - 1);
                player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
