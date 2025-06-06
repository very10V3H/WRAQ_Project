package fun.wraq.series.instance.series.devil;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DevilManaArmor extends WraqArmor implements ForgeItem {

    public DevilManaArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.defence.put(this, 15d);
        Utils.percentHealthRecover.put(this, 0.005);
        Utils.healthRecover.put(this, 45d);
        Utils.manaRecover.put(this, 8d);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("唤魔者之威").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("25%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 48),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }
}
