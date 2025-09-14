package fun.wraq.series.comsumable.passive.mixture;

import fun.wraq.common.equip.SceptreAttribute;
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

public class MixtureAttack extends CountPassiveComsumableItem {

    public final double rate;

    public MixtureAttack(Properties properties, int maxCount, double rate) {
        super(properties, maxCount);
        this.rate = rate;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 提升",
                ComponentUtils.getManaDamageEnhance(String.format("%.0f%%", rate * 100)),
                "与", ComponentUtils.getManaAttackDamageEnhance(String.format("%.0f%%", rate * 100))));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 在", "释放法术/法球命中", CustomStyle.styleOfMana,
                "时", "消耗可用次数", CustomStyle.styleOfStone));
    }

    public static double getManaDamageEnhance(Player player) {
        if (!SceptreAttribute.isHandling(player)) {
            return 0;
        }
        MixtureAttack mixture = (MixtureAttack) getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof MixtureAttack)
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(null);
        if (mixture != null) {
            return mixture.rate;
        }
        return 0;
    }

    public static void addCount(Player player) {
        getItemList(player).stream()
                .filter(stack -> stack.getItem() instanceof MixtureAttack)
                .findFirst()
                .ifPresent(stack -> addCount(stack, player));
    }
}
