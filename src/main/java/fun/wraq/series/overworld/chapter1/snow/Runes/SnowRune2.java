package fun.wraq.series.overworld.chapter1.snow.Runes;

import fun.wraq.common.util.ComponentUtils;
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

public class SnowRune2 extends Item {
    public SnowRune2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("每过").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("，你的下次攻击将使目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冻结1.5s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("并使该目标在被").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冻结").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("时的护甲降低").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%").withStyle(ChatFormatting.AQUA)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-Snow").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
