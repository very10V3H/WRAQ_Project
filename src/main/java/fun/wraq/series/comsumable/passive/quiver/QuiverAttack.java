package fun.wraq.series.comsumable.passive.quiver;

import fun.wraq.common.equip.WraqBow;
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

public class QuiverAttack extends CountPassiveComsumableItem {

    public final double rate;

    public QuiverAttack(Properties properties, int maxCount, double rate) {
        super(properties, maxCount);
        this.rate = rate;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                String.format("%.0f%%", rate * 100) + "箭矢攻击倍率", CustomStyle.styleOfFlexible));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "普通箭矢攻击", CustomStyle.styleOfFlexible,
                "命中敌人时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getExAttackRate(Player player) {
        if (!(player.getMainHandItem().getItem() instanceof WraqBow)) {
            return 0;
        }
        QuiverAttack quiver = (QuiverAttack) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof QuiverAttack)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (quiver != null) {
            return quiver.rate;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof QuiverAttack)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
