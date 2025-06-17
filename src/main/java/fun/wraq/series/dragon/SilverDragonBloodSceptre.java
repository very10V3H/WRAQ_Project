package fun.wraq.series.dragon;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SilverDragonBloodSceptre extends WraqSceptre implements SilverDragonBloodWeapon, Decomposable {

    public final int tier;
    public SilverDragonBloodSceptre(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, 20d);
        Utils.percentManaDamageEnhance.put(this, 0.2);
        Utils.manaPenetration.put(this, 0.3);
        Utils.xpLevelManaPenetration0.put(this, 0.3);
        Utils.manaHealthSteal.put(this, 0.08);
        Utils.manaRecover.put(this, 40d);
        Utils.coolDownDecrease.put(this, 0.3);
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
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_WORLD.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Sea;
    }

    @Override
    public ItemStack getProduct() {
        if (tier > 0) {
            return null;
        }
        return new ItemStack(SilverDragonItems.SILVER_DRAGON_WEAPON_PIECE.get(), 3);
    }
}
