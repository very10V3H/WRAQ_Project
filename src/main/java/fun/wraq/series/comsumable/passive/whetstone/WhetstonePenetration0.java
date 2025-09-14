package fun.wraq.series.comsumable.passive.whetstone;

import fun.wraq.common.equip.SwordAttribute;
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

public class WhetstonePenetration0 extends CountPassiveComsumableItem {

    public final double value;

    public WhetstonePenetration0(Properties properties, int maxCount, double value) {
        super(properties, maxCount);
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                ComponentUtils.AttributeDescription.defencePenetration(String.format("%.0f", value))));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "普通近战攻击", CustomStyle.styleOfPower,
                "命中敌人时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getExPenetration(Player player) {
        if (!(SwordAttribute.isHandling(player))) {
            return 0;
        }
        WhetstonePenetration0 whetStone = (WhetstonePenetration0) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof WhetstonePenetration0)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (whetStone != null) {
            return whetStone.value;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof WhetstonePenetration0)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
