package fun.wraq.series.events;

import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ForgePaper extends WraqItem {

    public static final List<ForgePaper> forgePapers = new ArrayList<>();

    public final String tag;
    public final Component suffix;
    public final Component exForgeLevelDescription;
    public ForgePaper(Properties properties, String tag, Component suffix, Component exForgeLevelDescription) {
        super(properties);
        forgePapers.add(this);
        this.tag = tag;
        this.suffix = suffix;
        this.exForgeLevelDescription = exForgeLevelDescription;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 在", "维瑞阿契锻造台", ChatFormatting.AQUA,
                "上使用，可以将装备的", "强化等级", ChatFormatting.AQUA,
                "与", "强化等级上限", ChatFormatting.LIGHT_PURPLE, " +1", CustomStyle.styleOfRed));
        components.add(Te.s(" 每件装备仅能被该物品提升一次", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public String getTag() {
        return tag;
    }
    public Component getSuffix() {
        return suffix;
    }
    public Component getExForgeLevelDescription() {
        return exForgeLevelDescription;
    }
}
