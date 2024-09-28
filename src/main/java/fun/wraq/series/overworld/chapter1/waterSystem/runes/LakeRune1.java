package fun.wraq.series.overworld.chapter1.waterSystem.runes;

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

public class LakeRune1 extends Item {
    public LakeRune1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components, Component.literal("水之张力").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal(" 将你").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("50%")).
                append(Component.literal("转化为等量").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        components.add(Component.literal("LakeRunes").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}
