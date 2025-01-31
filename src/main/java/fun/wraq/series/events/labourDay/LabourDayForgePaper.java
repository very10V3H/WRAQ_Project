package fun.wraq.series.events.labourDay;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LabourDayForgePaper extends Item {

    public LabourDayForgePaper(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("维瑞阿契锻造台").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("上使用，可以将装备的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("强化等级").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("强化等级上限").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal(" +1").withStyle(CustomStyle.styleOfForest)));
        components.add(Component.literal(" 每件装备仅能被该物品提升一次").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        OldCoin.LabourDaySuffix(components);
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
