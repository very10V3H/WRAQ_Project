package fun.wraq.series.instance.series.moon;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoonSoul extends WraqItem {

    public MoonSoul(Properties properties) {
        super(properties, true, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Te.s(" 使用以", ChatFormatting.AQUA, "沐浴尘月", CustomStyle.styleOfMoon));
        components.add(Te.s(" 前往: ", "尘月之梦", CustomStyle.styleOfMoon1));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,1049, 217, 631,0,0);
            player.getCooldowns().addCooldown(this,200);
        }
        return super.use(level, player, interactionHand);
    }
}
