package fun.wraq.series.nether.material;

import fun.wraq.common.Compute;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.gui.illustrate.Display;
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

public class ToNether extends Item {

    public ToNether(Properties p_41383_) {
        super(p_41383_);
        Display.materialList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                append(Component.literal("碾碎").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("此物，前往").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("下界").withStyle(ChatFormatting.RED)));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (player.experienceLevel >= 75) {
                Compute.playerItemUseWithRecord(player);
                ServerLevel nether = level.getServer().getLevel(Level.NETHER);
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.teleportTo(nether, 591, 78, -619, 90, 0);
                Guide.trigV2(serverPlayer, Guide.StageV2.TO_NETHER);
            } else {
                Compute.sendFormatMSG(player, Component.literal("下界").withStyle(ChatFormatting.RED),
                        Component.literal("需要达到75级才能前往").withStyle(ChatFormatting.WHITE));
            }
            player.getCooldowns().addCooldown(this, 40);
        }
        return super.use(level, player, interactionHand);
    }
}
