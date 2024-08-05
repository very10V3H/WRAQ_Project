package com.very.wraq.process.system.endlessinstance.item;

import com.very.wraq.process.system.endlessinstance.instance.EasternTower;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.MaterialItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EasternTowerPaper extends MaterialItem {

    public EasternTowerPaper(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level,
                                @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(EasternTower.name).
                append(Component.literal("挑战点附近使用，开启").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 无尽熵增 - ").withStyle(CustomStyle.styleOfWorld).
                append(EasternTower.name).
                append(Component.literal("挑战").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.sendSystemMessage(Component.literal("此物品bug临时禁用"));
/*            for (DailyEndlessInstance instance : DailyEndlessInstanceEvent.getEndlessInstanceList()) {
                if (instance.active(player)) {
                    Compute.playerItemUseWithRecord(player);
                }
            }*/
        }
        return super.use(level, player, interactionHand);
    }
}
