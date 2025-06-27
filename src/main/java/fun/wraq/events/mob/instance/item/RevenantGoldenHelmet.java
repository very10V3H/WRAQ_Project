package fun.wraq.events.mob.instance.item;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RevenantGoldenHelmet extends WraqArmor implements Decomposable {
    public RevenantGoldenHelmet(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        Utils.defence.put(this, 10d);
        Utils.healthRecover.put(this, 15d);
        Element.fireElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 90);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("焰烬光环", getMainStyle()));
        components.add(Te.s(" 免疫灼烧.", CustomStyle.styleOfStone));
        components.add(Te.s(" 提升", Element.Description.FireElementValue("50%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.NETHER_IMPRINT.get(), 1);
    }

    public static double getFireElementValueEnhanceRate(Player player) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.REVENANT_GOLDEN_HELMET.get())) {
            return 0.5;
        }
        return 0;
    }
}
