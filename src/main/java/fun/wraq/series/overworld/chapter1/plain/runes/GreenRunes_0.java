package fun.wraq.series.overworld.chapter1.plain.runes;

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

public class GreenRunes_0 extends Item {
    public GreenRunes_0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        Compute.RuneAttributeDescription(components);
        components.add(Component.literal("每5秒回复自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.lossHealth("10%")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
