package com.very.wraq.series.springFes;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.projectiles.firework.Firework;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class FireCracker extends Item {

    public FireCracker(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 新年快乐！").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 右键点燃一枚鞭炮").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 命中").withStyle(ChatFormatting.WHITE).
                append(Component.literal("年兽").withStyle(CustomStyle.styleOfSpring)).
                append(Component.literal("会造成禁锢并对其造成2024 * 10^3 魔法伤害").withStyle(ChatFormatting.GOLD)));
        components.add(Component.literal("Idea From:Guang_Yi & LengCheng").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

/*    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @Nullable Font getFont(ItemStack stack, FontContext context) {
                return IClientItemExtensions.super.getFont(stack, context);
            }
        });
        super.initializeClient(consumer);
    }*/


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.playerItemUseWithRecord(player);

            Firework firework = new Firework(ModEntityType.FIRE_WORK.get(), player, level);
            firework.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1f, 1);
            firework.setSilent(true);
            level.addFreshEntity(firework);
            ModNetworking.sendToClient(new SoundsS2CPacket(7), (ServerPlayer) player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
