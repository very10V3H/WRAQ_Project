package fun.wraq.series.comsumable.passive.mixture;

import fun.wraq.common.equip.WraqSceptre;
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

public class MixturePenetration0 extends CountPassiveComsumableItem {

    public final double value;

    public MixturePenetration0(Properties properties, int maxCount, double value) {
        super(properties, maxCount);
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                ComponentUtils.AttributeDescription.manaPenetration(String.format("%.0f", value))));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "释放法术/法球命中", CustomStyle.styleOfMana,
                "时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getManaPenetration0(Player player) {
        if (!(player.getMainHandItem().getItem() instanceof WraqSceptre)) {
            return 0;
        }
        MixturePenetration0 mixture = (MixturePenetration0) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof MixturePenetration0)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (mixture != null) {
            return mixture.value;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof MixturePenetration0)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
