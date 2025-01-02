package fun.wraq.series.end.citadel;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CitadelArmor extends WraqArmor implements InCuriosOrEquipSlotAttributesModify {

    public CitadelArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.015);
            Utils.healthRecover.put(this, 100d);
        }
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 125d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 60000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.1);
        Utils.levelRequire.put(this, 220);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfEnd;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.solePassiveDescription(components, Te.s("影遁", getMainStyle()));
        components.add(Te.s(" 按住shift时", CustomStyle.styleOfStone, "为你提供:"));
        components.add(Te.s(" ", ComponentUtils.AttributeDescription.toughness("30%")));
        components.add(Te.s(" ", ComponentUtils.AttributeDescription.healAmplification("25%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfEnd();
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        if (!player.isShiftKeyDown()) {
            return List.of();
        }
        return List.of(
                new Attribute(Utils.toughness, 0.3),
                new Attribute(Utils.healingAmplification, 0.25)
        );
    }

    @Override
    public void tick(Player player) {
        if (player.isShiftKeyDown()) {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10));
            if (player.tickCount % 10 == 0) {
                Compute.sendEffectLastTime(player, "item/citadel_armor_enhancer", 20, 0, false);
            }
        }
        super.tick(player);
    }
}
