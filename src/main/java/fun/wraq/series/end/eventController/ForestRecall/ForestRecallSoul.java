package fun.wraq.series.end.eventController.ForestRecall;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForestRecallSoul extends Item {
    public ForestRecallSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("森林的意志残片").withStyle(CustomStyle.styleOfForest));
        components.add(Component.literal(" "));
        components.add(Component.literal("Recall-Forest").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
