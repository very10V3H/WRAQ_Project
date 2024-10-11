package fun.wraq.series.overworld.chapter2.spider.Ointment;

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

public class SnowOintment0 extends Item {
    public SnowOintment0(Properties p_41383_) {
        super(p_41383_);
        this.levelNum = 0;
    }

    private final int levelNum;

    public SnowOintment0(Properties properties, int levelNum) {
        super(properties);
        this.levelNum = levelNum;
    }

    private final String[] NameString = {
            "一般", "优良", "极好"
    };

    private final ChatFormatting[] chatFormatting = {
            ChatFormatting.GREEN,
            ChatFormatting.AQUA,
            ChatFormatting.LIGHT_PURPLE
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·涂料").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" "));
        SnowOintmentCommonDescription(components);
        components.add(Component.literal(NameString[this.levelNum]).withStyle(chatFormatting[this.levelNum]).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冰川能量涂料。").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Ointment").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    private void SnowOintmentCommonDescription(List<Component> components) {
        double Max = 15;
        if (this.levelNum == 0) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(CustomStyle.styleOfSpider)).
                    append(ComponentUtils.AttributeDescription.healthSteal(Max * 0.25 + "%~" + Max * 0.75 + "%")));
        } else if (this.levelNum == 1) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(CustomStyle.styleOfSpider)).
                    append(ComponentUtils.AttributeDescription.healthSteal(Max * 0.5 + "%~" + Max + "%")));
        } else if (this.levelNum == 2) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(CustomStyle.styleOfSpider)).
                    append(ComponentUtils.AttributeDescription.healthSteal(Max * 0.75 + "%~" + Max + "%")));
        }
    }
}
