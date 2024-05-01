package com.very.wraq.Items.KillPaper;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Struct.Drops;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class KillPaper extends Item {
    private final String MobName;
    public KillPaper(Properties p_41383_, String MobName) {
        super(p_41383_);
        this.MobName = MobName;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            try {
                for (int i = 0; i < 64; i ++) Drops.KillPaper(player,MobName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("使用以征讨64只:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(MobName).withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
