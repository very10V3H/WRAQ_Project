package fun.wraq.series.dragon;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SilverDragonBloodBow extends WraqBow implements SilverDragonBloodWeapon, Decomposable {

    public final int tier;
    public SilverDragonBloodBow(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 10d);
        Utils.percentAttackDamageEnhance.put(this, 0.2);
        Utils.defencePenetration.put(this, 0.3);
        Utils.xpLevelDefencePenetration0.put(this, 0.3);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, 0.15);
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
