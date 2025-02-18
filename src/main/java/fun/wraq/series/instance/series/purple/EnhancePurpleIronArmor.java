package fun.wraq.series.instance.series.purple;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnhancePurpleIronArmor extends WraqArmor implements Decomposable {

    public EnhancePurpleIronArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        Utils.defence.put(this, 25d);
        Utils.maxHealth.put(this, 5000d);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("紫晶能量屏障", getMainStyle()));
        components.add(Te.s(" 每过10s，提供持续5s的",
                ComponentUtils.AttributeDescription.maxHealth("20%"), "护盾。", CustomStyle.styleOfStone));
        ComponentUtils.descriptionPassive(components, Te.s("紫晶心流", getMainStyle()));
        components.add(Te.s(" 每拥有",
                ComponentUtils.AttributeDescription.maxHealth("10%"), "护盾值", CustomStyle.styleOfStone,
                "提供", ComponentUtils.getCommonDamageEnhance("5%")));
        components.add(Te.s(" 至多可获得", ComponentUtils.getCommonDamageEnhance("25%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }

    public static double getCommonDamageEnhanceRate(Player player) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ENHANCE_PURPLE_IRON_CHEST.get())) {
            double shieldValue = Shield.computePlayerShield(player);
            return Math.min (0.25, shieldValue * 0.5 / player.getMaxHealth());
        }
        return 0;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void tick(Player player) {
        super.tick(player);
        if (player.tickCount % 200 == 0) {
            Shield.providePlayerShield(player, Tick.s(5), player.getMaxHealth() * 0.2);
            Compute.sendEffectLastTime(player, this, 100);
        }
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PurpleIronBud2.get(), 2);
    }
}
