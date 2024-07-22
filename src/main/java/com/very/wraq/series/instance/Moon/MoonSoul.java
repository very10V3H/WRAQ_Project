package com.very.wraq.series.instance.Moon;

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

public class MoonSoul extends Item {
    public MoonSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {

/*        components.add(Component.literal(" 右键以前往:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("[尘月之梦]").withStyle(CustomStyle.styleOfMoon1)));*/

        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        if (!level.isClientSide) {

            Compute.playerItemUseWithRecord(player);

            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,1046.5,227.5,1260.5,0,0);
            player.getCooldowns().addCooldown(this,200);

        }*/
        return super.use(level, player, interactionHand);
    }
}
