package com.very.wraq.process.system.parkour;

import com.very.wraq.render.toolTip.CustomStyle;
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

import java.util.List;

public class ParkourTicket extends Item {
    public ParkourTicket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Parkour.TpToCurrentParkourPointPos(player);
            player.getCooldowns().addCooldown(this, 100);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以回到跑酷记录点/出生点").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" - 跑酷内容完全由LXYZO提供支持！向他致敬！").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
