package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.impl.onhit.OnCritHitEffectMainHandWeapon;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceSword extends WraqSword implements OnHitEffectEquip, OnCritHitEffectMainHandWeapon {
    public IceSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 700d);
        Utils.defencePenetration0.put(this, 21d);
        Utils.healthSteal.put(this, 0.3);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Element.IceElementValue.put(this, 1.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        IceWeaponPassiveHelper.description(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        IceWeaponPassiveHelper.onHit(player, mob);
    }

    @Override
    public void onCritHit(Player player, Mob mob) {
        IceWeaponPassiveHelper.onCritHit(player, mob, ModItems.IceSword.get(), 5);
    }
}
