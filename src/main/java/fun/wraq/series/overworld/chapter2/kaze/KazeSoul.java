package fun.wraq.series.overworld.chapter2.kaze;

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

public class KazeSoul extends Item {
    public KazeSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Low(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("好像马上就要飘走。").withStyle(CustomStyle.styleOfKaze));
        components.add(Component.literal("可在锻造台中与6个").withStyle(ChatFormatting.WHITE).
                append(Component.literal("水凝核").withStyle(ChatFormatting.BLUE)).
                append(Component.literal("合成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("狂风意志").withStyle(CustomStyle.styleOfKaze)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Kaze").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
