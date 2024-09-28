package fun.wraq.series.overworld.sakuraSeries.Scarecrow;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WheatReForge extends Item {
    public WheatReForge(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Epic(components);
        components.add(Component.literal("·改造图").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("稻草改造图纸").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("对装备性能进行调整。"));
        components.add(Component.literal("调整属性与范围:"));
        components.add(Component.literal("1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("-20% ~ 20%")));
        components.add(Component.literal("2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("-20% ~ 20%")));
        components.add(Component.literal("3.").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("-50% ~ 100%")));
        components.add(Component.literal("4.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("-20% ~ 20%")));
        components.add(Component.literal("5.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("-10% ~ 10%")));
        components.add(Component.literal("6.").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("-25% ~ 25%")));
        components.add(Component.literal(" "));
        components.add(Component.literal("ReForge-Wheat").withStyle(CustomStyle.styleOfWheat).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
