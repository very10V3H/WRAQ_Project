package fun.wraq.series.specialevents.springFes;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SpringSwiftArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfSpring;
    private String type = "";

    public SpringSwiftArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties, int type) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 1024d);
        Utils.attackDamage.put(this, 224d);
        Utils.defence.put(this, 1d);
        Utils.swiftnessUp.put(this, 2.4);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("金龙怒吼").withStyle(style));
        components.add(Component.literal("每过5s,你命中目标的箭矢会释放一个").withStyle(ChatFormatting.WHITE).
                append(Component.literal("烟花").withStyle(style)).
                append(Component.literal("使得一定范围内的怪物禁锢并降低").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.defence("20%~40%")).
                append(Component.literal("持续3s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
