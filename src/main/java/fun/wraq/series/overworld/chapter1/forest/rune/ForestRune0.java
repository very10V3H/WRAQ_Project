package fun.wraq.series.overworld.chapter1.forest.rune;

import fun.wraq.common.Compute;
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

public class ForestRune0 extends Item {
    public ForestRune0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        Compute.RuneAttributeDescription(components);
        components.add(Component.literal("获得你").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("2.5%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.exAttackDamage("")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-II").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
