package fun.wraq.series.overworld.cold.sc5.dragon.weapon;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SuperColdDragonSceptre extends WraqSceptre implements SuperColdDragonWeaponCommon, ActiveItem {

    public final int tier;

    public SuperColdDragonSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, 5000d);
        Utils.manaRecover.put(this, 40d);
        Utils.manaPenetration0.put(this, 50d);
        Utils.coolDownDecrease.put(this, 0.25);
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
            SuperColdDragonWeaponCommon.handleSceptrePassive2Description(components);
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
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_SNOW.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Snow;
    }
}
