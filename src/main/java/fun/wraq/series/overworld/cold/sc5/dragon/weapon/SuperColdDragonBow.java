package fun.wraq.series.overworld.cold.sc5.dragon.weapon;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SuperColdDragonBow extends WraqBow implements SuperColdDragonWeaponCommon, ActiveItem {

    public final int tier;

    public SuperColdDragonBow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, 2500d);
        Utils.defencePenetration0.put(this, 50d);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.05);
        Element.iceElementValue.put(this, 2.5);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public int getWeaponTier() {
        return tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (tier >= 2) {
            SuperColdDragonWeaponCommon.handleCommonPassive1Description(components);
        }
        if (tier >= 1) {
            SuperColdDragonWeaponCommon.handleBowPassive2Description(components);
        }
        SuperColdDragonWeaponCommon.handleCommonActiveDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSuperCold();
    }

    @Override
    public void tick(Player player) {
        if (tier >= 2) {
            SuperColdDragonWeaponCommon.handleTick(player);
        }
    }

    @Override
    public void active(Player player) {
        SuperColdDragonWeaponCommon.active(player);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public ItemStack getProduct() {
        if (tier == 0) {
            return new ItemStack(SuperColdItems.WEAPON_CORE.get());
        }
        return null;
    }
}
