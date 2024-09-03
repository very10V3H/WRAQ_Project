package com.very.wraq.series.overworld.chapter2.sky;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
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

public class WindBottle extends Item {

    public WindBottle(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        ComponentUtils.descriptionPassive(components, Component.literal("释风放流").withStyle(CustomStyle.styleOfWind));
        components.add(Component.literal(" 冲上天空！").withStyle(CustomStyle.styleOfSky));
        ComponentUtils.coolDownTimeDescription(components, 30);
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!player.level().isClientSide()) {
            Compute.playerItemCoolDown(player, this, 30);
            player.setDeltaMovement(player.getDeltaMovement());
            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                    new ClientboundSetEntityMotionPacket(player.getId(), player.getDeltaMovement().add(0, 15, 0));
            ((ServerPlayer) player).connection.send(clientboundSetEntityMotionPacket);
        }
        return super.use(level, player, interactionHand);
    }
}
