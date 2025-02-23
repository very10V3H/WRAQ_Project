package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DivineSword extends WraqSword implements DivineWeaponCommon {

    private final double transformRate;
    private final double upperLimitRate;
    private final int maxCount;
    private final double maxActiveDistance;
    public DivineSword(Properties properties, double transformRate, double upperLimitRate, int maxCount,
                       double maxActiveDistance) {
        super(properties);
        this.transformRate = transformRate;
        this.upperLimitRate = upperLimitRate;
        this.maxCount = maxCount;
        this.maxActiveDistance = maxActiveDistance;
        Utils.attackDamage.put(this, 3000d);
        Utils.defencePenetration0.put(this, 60d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.levelRequire.put(this, 230);
        DivineWeaponCommon.weaponList.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineWeaponCommon.getCommonDescription(stack, upperLimitRate, maxCount, true);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        List<Attribute> attributes = new ArrayList<>();
        ItemStack stack = player.getMainHandItem();
        int count = DivineWeaponCommon.getDivineCount(stack);
        double rate = (double) count / maxCount;
        attributes.addAll(List.of(
                new Attribute(Utils.elementStrength, upperLimitRate * rate),
                new Attribute(Utils.percentAttackDamageEnhance, upperLimitRate * rate)
        ));
        return attributes;
    }

    @Override
    public void onKill(Player player, Mob mob) {
        ItemStack stack = player.getMainHandItem();
        DivineWeaponCommon.addDivineCount(stack);
        DivineWeaponCommon.onKill(player);
    }

    @Override
    public void active(Player player) {
        DivineWeaponCommon.active(player, maxActiveDistance);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public double getTransformRate() {
        return transformRate;
    }
}
