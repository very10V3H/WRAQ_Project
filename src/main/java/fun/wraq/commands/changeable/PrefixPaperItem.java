package fun.wraq.commands.changeable;

import fun.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PrefixPaperItem extends Item {

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
            if (!fun.wraq.commands.changeable.PrefixCommand.activePrefix(player, tag)) {
                fun.wraq.commands.changeable.PrefixCommand.sendFormatMSG(player, Component.literal("已经拥有该称号了").withStyle(ChatFormatting.WHITE));
            } else {
                Compute.playerItemUseWithRecord(player);
                PrefixCommand.sendFormatGetMSG(player, Component.literal(prefix).withStyle(style));
            }
        }
        return super.use(level, player, interactionHand);
    }
}
