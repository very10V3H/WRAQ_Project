package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.equip.WraqSceptre;
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

public class DivineSceptre extends WraqSceptre implements DivineWeaponCommon {

    private final double transformRate;
    private final double upperLimitRate;
    private final int maxCount;
    private final double maxActiveDistance;
    public DivineSceptre(Properties properties, double transformRate, double upperLimitRate, int maxCount,
                         double maxActiveDistance) {
        super(properties);
        this.transformRate = transformRate;
        this.upperLimitRate = upperLimitRate;
        this.maxCount = maxCount;
        this.maxActiveDistance = maxActiveDistance;
        Utils.manaDamage.put(this, 6000d);
        Utils.manaRecover.put(this, 50d);
        Utils.manaPenetration0.put(this, 70d);
        Utils.coolDownDecrease.put(this, 0.3);
        Utils.levelRequire.put(this, 230);
        DivineWeaponCommon.weaponList.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineWeaponCommon.getCommonDescription(stack, upperLimitRate, maxCount, false);
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
                new Attribute(Utils.percentManaDamageEnhance, upperLimitRate * rate)
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
