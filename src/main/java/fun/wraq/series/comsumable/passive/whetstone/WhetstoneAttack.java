package fun.wraq.series.comsumable.passive.whetstone;

import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.passive.CountPassiveComsumableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WhetstoneAttack extends CountPassiveComsumableItem {

    public final double rate;

    public WhetstoneAttack(Properties properties, int maxCount, double rate) {
        super(properties, maxCount);
        this.rate = rate;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                String.format("%.0f%%", rate * 100) + "普通近战攻击倍率", CustomStyle.styleOfPower));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "普通近战攻击", CustomStyle.styleOfPower,
                "命中敌人时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getExAttackRate(Player player) {
        if (!(SwordAttribute.isHandling(player))) {
            return 0;
        }
        WhetstoneAttack whetStoneAttack = (WhetstoneAttack) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof WhetstoneAttack)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (whetStoneAttack != null) {
            return whetStoneAttack.rate;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof WhetstoneAttack)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
