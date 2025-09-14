package fun.wraq.series.comsumable.passive.quiver;

import fun.wraq.common.equip.BowAttribute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.passive.CountPassiveComsumableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuiverPenetration extends CountPassiveComsumableItem {

    public final double value;

    public QuiverPenetration(Properties properties, int maxCount, double value) {
        super(properties, maxCount);
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                ComponentUtils.AttributeDescription.defencePenetration(String.format("%.0f%%", value * 100))));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "普通箭矢攻击", CustomStyle.styleOfFlexible,
                "命中敌人时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getExPenetration(Player player) {
        if (!BowAttribute.isHandling(player)) {
            return 0;
        }
        QuiverPenetration quiver = (QuiverPenetration) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof QuiverPenetration)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (quiver != null) {
            return quiver.value;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof QuiverPenetration)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
