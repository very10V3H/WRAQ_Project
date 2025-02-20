package fun.wraq.series.overworld.divine.equip;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DivineSceptre extends WraqSceptre implements DivineEquipCommon {

    private final double upperLimitRate;
    private final int maxCount;
    public DivineSceptre(Properties properties, double upperLimitRate, int maxCount) {
        super(properties);
        this.upperLimitRate = upperLimitRate;
        this.maxCount = maxCount;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineEquipCommon.getCommonDescription(stack, upperLimitRate, maxCount, false);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        ItemStack stack = player.getMainHandItem();
        int count = DivineEquipCommon.getDivineCount(stack);
        double rate = (double) count / maxCount;
        return List.of(
                new Attribute(Utils.elementStrength, upperLimitRate * rate),
                new Attribute(Utils.percentManaDamageEnhance, upperLimitRate * rate)
        );
    }

    @Override
    public void onKill(Player player, Mob mob) {
        ItemStack stack = player.getMainHandItem();
        DivineEquipCommon.addDivineCount(stack);
    }
}
