package fun.wraq.series.dragon;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SilverDragonBloodSword extends WraqSword implements SilverDragonBloodWeapon, Decomposable {

    public final int tier;
    public SilverDragonBloodSword(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, new double[]{10d, 12d, 15d}[tier]);
        Utils.percentAttackDamageEnhance.put(this, new double[]{0.2, 0.25, 0.3}[tier]);
        Utils.defencePenetration.put(this, new double[]{0.3, 0.35, 0.4}[tier]);
        Utils.xpLevelDefencePenetration0.put(this, new double[]{0.3, 0.35, 0.4}[tier]);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, new double[]{0.35, 0.4, 0.45}[tier]);
        Utils.critDamage.put(this, new double[]{0.05, 0.15, 0.25}[tier]);
        this.tier = tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.SILVER_DRAGON_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return SilverDragonBloodWeapon.getCommonDescription(getMaxCount(), stack.getItem());
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSilverDragon();
    }

    @Override
    public int getMaxCount() {
        return (tier + 1) * 20;
    }

    @Override
    public void active(Player player) {
        SilverDragonBloodWeapon.commonActive(player);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        SilverDragonBloodWeapon.onCauseDamage(player, mob);
    }

    @Override
    public void tick(Player player) {
        SilverDragonBloodWeapon.handleTick(player);
    }

    @Override
    public ItemStack getProduct() {
        if (tier > 0) {
            return null;
        }
        return new ItemStack(SilverDragonItems.SILVER_DRAGON_WEAPON_PIECE.get(), 3);
    }
}
