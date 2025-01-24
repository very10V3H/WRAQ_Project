package fun.wraq.series.overworld.sakura.Scarecrow;

import fun.wraq.common.Compute;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wheat extends Item {
    public Wheat(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("一捆稻草").withStyle(CustomStyle.styleOfWheat));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Wheat").withStyle(CustomStyle.styleOfWheat).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
