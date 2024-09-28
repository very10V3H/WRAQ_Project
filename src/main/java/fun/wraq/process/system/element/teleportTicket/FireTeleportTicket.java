package fun.wraq.process.system.element.teleportTicket;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
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

public class FireTeleportTicket extends Item {

    public FireTeleportTicket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && player.experienceLevel >= 200) {
            ServerLevel overWorld = level.getServer().getLevel(Level.NETHER);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld, 86.5, 105, 544.5, -45, -45);
            player.getCooldowns().addCooldown(this, 2400);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以前往 - ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("炽焰终焉之境").withStyle(CustomStyle.styleOfFire)));
        ComponentUtils.coolDownTimeDescription(components, 120);
        Compute.LevelRequire(components, 200);
        ComponentUtils.suffixOfElement(components);
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
