package fun.wraq.process.system.forge;

import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgeTemplate extends WraqItem {

    public ForgeTemplate(Properties properties) {
        super(properties, false, false);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        Style style = CustomStyle.styleOfWorld;
        if (ForgeEquipUtils.getForgeLevel(itemStack) > 0) {
            components.add(Te.s(" 粘贴强化等级", style));
            components.add(Te.s(" - 将装备置于锻造台", "左上", style));
            components.add(Te.s(" - 将该物品置于锻造台", "左下", style));
        } else {
            components.add(Te.s("将装备的", "强化等级", style, "'剪切'到此物品中"));
            components.add(Te.s("即可将", "强化等级", style, "在不同装备间转移"));
            components.add(Te.s("剪切强化等级:", style));
            components.add(Te.s(" - 将装备置于锻造台", "右上", style));
            components.add(Te.s(" - 将该物品置于锻造台", "右下", style));
            components.add(Te.s(" 卷轴提供的强化等级与上限也将被转移", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ForgeEquipUtils.getForgeQualityOnEquip(stack) > 0;
    }
}
