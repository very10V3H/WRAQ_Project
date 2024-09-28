package fun.wraq.Items.DevelopmentTools;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class quartzsabrecheck extends Item {

    public quartzsabrecheck(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            player.sendSystemMessage(Component.literal(String.valueOf(data.getInt("QuartzSabre"))));
            player.sendSystemMessage(Component.literal(String.valueOf(data.getInt("QuartzSabrePos"))));
            player.sendSystemMessage(Component.literal(String.valueOf(ClientUtils.QuartzSabreParticlePos)));
            player.sendSystemMessage(Component.literal(String.valueOf(ClientUtils.QuartzSabreParticleIndexX)));
            player.sendSystemMessage(Component.literal(String.valueOf(ClientUtils.QuartzSabreParticleIndexY)));
            player.sendSystemMessage(Component.literal(String.valueOf(ClientUtils.QuartzSabreParticleIndexZ)));

        }
        return super.use(level, player, interactionHand);
    }
}
