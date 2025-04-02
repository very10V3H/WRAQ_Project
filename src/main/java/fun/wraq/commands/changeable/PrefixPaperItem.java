package fun.wraq.commands.changeable;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrefixPaperItem extends WraqItem {

    private final String tag;
    private final String prefix;
    private final Style style;
    public PrefixPaperItem(Properties properties, String tag, String prefix, Style style) {
        super(properties);
        PrefixCommand.getSimplePrefixTypeList().add(new PrefixCommand.SimpleFlagPrefixType(tag, prefix, style));
        this.tag = tag;
        this.prefix = prefix;
        this.style = style;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (!PrefixCommand.activePrefix(player, tag)) {
                PrefixCommand.sendFormatMSG(player,
                        Component.literal("已经拥有该称号了").withStyle(ChatFormatting.WHITE));
            } else {
                Compute.playerItemUseWithRecord(player);
                PrefixCommand.sendFormatGetMSG(player, Component.literal(prefix).withStyle(style));
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键使用以获取称号「", ChatFormatting.AQUA, prefix, style, "」", ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
