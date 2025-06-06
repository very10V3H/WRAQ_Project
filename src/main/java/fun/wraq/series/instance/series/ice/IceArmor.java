package fun.wraq.series.instance.series.ice;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceArmor extends WraqArmor implements ForgeItem {

    public IceArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 40d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 35d);
            Utils.manaDefence.put(this, 15d);
            Utils.maxHealth.put(this, 5000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 10000d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 5000d);
        }
        Utils.levelRequire.put(this, 135);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("寒意释放").withStyle(style));
        components.add(Component.literal(" 每3s对周围单位造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("15%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法伤害").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.YELLOW)));
        components.add(Component.literal("并施加持续2s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("减速效果").withStyle(style)));
        Compute.DescriptionPassive(components, Component.literal("雪上覆霜").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移动速度受损").withStyle(style)).
                append(Component.literal("的敌人造成的伤害受").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getCommonDamageEnhance("15%")));
        components.add(Component.literal(" -多件冰霜骑士装备能够线性提升伤害值").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 3),
                new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 1)
        );
    }
}
