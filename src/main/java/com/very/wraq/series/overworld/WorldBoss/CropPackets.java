package com.very.wraq.series.overworld.WorldBoss;

import com.very.wraq.common.Compute;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CropPackets extends Item {

    public CropPackets(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以获取各种农作物各32个").withStyle(CustomStyle.styleOfField));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            ItemStack[] itemStacks = {
                    new ItemStack(Items.CARROT, 32),
                    new ItemStack(Items.POTATO, 32),
                    new ItemStack(Items.WHEAT, 32),
                    new ItemStack(Items.TORCHFLOWER, 32),
                    new ItemStack(Items.SWEET_BERRIES, 32),
                    new ItemStack(Items.BEETROOT, 32)
            };
            for (ItemStack itemStack : itemStacks) {
                Compute.itemStackGive(player, itemStack);
            }
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}
