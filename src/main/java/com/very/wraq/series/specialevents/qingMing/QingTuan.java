package com.very.wraq.series.specialevents.qingMing;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QingTuan extends Item {

    public QingTuan(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        QingMingSuffix(components);
        components.add(Component.literal(" "));
        components.add(Component.literal(" 捣青草为汁，和粉作团，色如碧玉。").withStyle(CustomStyle.styleOfHealth));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static void QingMingSuffix(List<Component> components) {
        components.add(Component.literal(" 活动").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" · ").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("清明").withStyle(CustomStyle.styleOfForest)));
    }
}
