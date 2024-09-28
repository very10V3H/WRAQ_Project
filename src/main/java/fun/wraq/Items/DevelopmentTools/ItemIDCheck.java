package fun.wraq.Items.DevelopmentTools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemIDCheck extends Item {
    public ItemIDCheck(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (interactionHand.equals(InteractionHand.OFF_HAND)) {
                player.sendSystemMessage(Component.literal(" " + getId(player.getHandSlots().iterator().next().getItem())));
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
