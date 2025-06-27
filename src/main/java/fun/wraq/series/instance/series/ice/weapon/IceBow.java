package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onhit.OnCritHitEffectMainHandWeapon;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceBow extends WraqBow implements OnHitEffectEquip, OnCritHitEffectMainHandWeapon, ForgeItem, EnhancedForgedItem {

    private final int tier;
    public IceBow(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, 700d);
        Utils.defencePenetration0.put(this, 21d);
        Utils.critRate.put(this, 0.25);
        Element.iceElementValue.put(this, 1.25);
        Utils.levelRequire.put(this, 135);
        this.tier = tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        IceWeaponHelper.description(components, tier);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        IceWeaponHelper.onHit(player, mob);
    }

    @Override
    public void onCritHit(Player player, Mob mob) {
        IceWeaponHelper.onCritHit(player, mob, ModItems.ICE_BOW.get(), 5, tier);
    }

    @Override
    public int getEnhanceTier() {
        return tier;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 8),
                    new ItemStack(ModItems.GOLD_COIN.get(), 256),
                    new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8)
            );
        }
        return List.of(
                new ItemStack(ModItems.ICE_BOW.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 6),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 2)
        );
    }
}
