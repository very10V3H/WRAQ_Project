package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.PreventLeftClickShoot;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DivineBow extends WraqBow implements DivineWeaponCommon, PreventLeftClickShoot {

    private final double transformRate;
    private final double upperLimitRate;
    private final int maxCount;
    private final double maxActiveDistance;
    private final int tier;
    public DivineBow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        this.transformRate = new double[]{0.2, 0.5, 0.8}[tier];
        this.upperLimitRate = new double[]{0.2, 0.5, 0.8}[tier];
        this.maxCount = new int[]{3000, 5000, 8000}[tier];
        this.maxActiveDistance = new double[]{20, 32, 48}[tier];
        Utils.attackDamage.put(this, new double[]{3000, 3500, 4000}[tier]);
        Utils.defencePenetration0.put(this, new double[]{60, 70, 80}[tier]);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.levelRequire.put(this, new int[]{230, 240, 250}[tier]);
        DivineWeaponCommon.weaponList.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineWeaponCommon.getCommonDescription(stack, upperLimitRate, maxCount, true, maxActiveDistance);
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
        double rate = Math.min(1, count * 1.0 / maxCount);
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
        DivineWeaponCommon.onKillMob(player, mob);
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

    @Override
    public List<ItemStack> forgeRecipe() {
        return DivineWeaponCommon.getForgeRecipe(tier, DivineIslandItems.DIVINE_BOW_0.get());
    }

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        DivineWeaponCommon.onCauseDamageCommon(player, mob);
    }
}
