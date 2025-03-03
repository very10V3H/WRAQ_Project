
package fun.wraq.series.instance.series.purple;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.common.registry.ModItems;
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

public class PurpleIronSword extends WraqPassiveEquip implements PurpleIronCommon, OnHitEffectPassiveEquip, Decomposable {

    private final int tier;

    public PurpleIronSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{100, 150, 200, 250}[tier]);
        Utils.xpLevelAttackDamage.put(this, new double[]{1, 2, 3, 4}[tier]);
        Utils.defence.put(this, new double[]{2, 3, 5, 6}[tier]);
        Utils.maxHealth.put(this, new double[]{1600, 2400, 3200, 4000}[tier]);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        List<Component> components = new ArrayList<>();
        PurpleIronCommon.setDescription(components, tier);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }

    @Override
    public Component getType() {
        return Component.literal("长剑").withStyle(CustomStyle.styleOfPurpleIron);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        PurpleIronCommon.onHit(player, mob, this);
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get());
    }
}
