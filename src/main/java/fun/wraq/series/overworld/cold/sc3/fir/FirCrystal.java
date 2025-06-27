package fun.wraq.series.overworld.cold.sc3.fir;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceEquip;
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

public class FirCrystal extends WraqOffHandItem implements OnHitDamageInfluenceEquip {

    private final int tier;
    public FirCrystal(Properties properties, Component type, int tier) {
        super(properties, type);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{277d, 377d, 477d, 577d}[tier] * 2);
        Utils.manaPenetration0.put(this, new double[]{57d, 67d, 87d, 107d}[tier]);
        Utils.maxMana.put(this, 87d);
        Utils.coolDownDecrease.put(this, 0.23);
        Utils.expUp.put(this, 0.88);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.FIR_STYLE;
    }

    public double getRate() {
        return new double[]{0.15, 0.35, 0.65, 1}[tier];
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("刺骨冷杉", getMainStyle()));
        components.add(Te.s(" 对于可以使用",
                ComponentUtils.AttributeDescription.manaPenetration(""),
                "完全穿透的", CustomStyle.styleOfRed, "敌人"));
        components.add(Te.s(" 造成的伤害获得",
                ComponentUtils.getCommonDamageEnhance(String.format("%.0f%%", getRate() * 100))));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSuperCold();
    }

    @Override
    public double onHitDamageInfluence(Player player, Mob mob) {
        if (MobAttributes.manaDefence(mob)
                * (1 - PlayerAttributes.manaPenetration(player)) - PlayerAttributes.manaPenetration0(player) <= 0) {
            return getRate();
        }
        return 0;
    }
}
