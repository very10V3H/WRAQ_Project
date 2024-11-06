package fun.wraq.series.overworld.chapter1.snow.Runes;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
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

public class SnowRune0 extends Item {
    public SnowRune0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔法攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("命中一个目标后，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("15%")).
                append(Component.literal("加成。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("持续").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("，至多叠加").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("5层").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("，当未命中时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("清空层数").withStyle(ChatFormatting.AQUA)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-Snow").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
