package fun.wraq.series.overworld.sakuraSeries.EarthMana;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class EarthManaArmor extends WraqArmor implements ForgeItem {
    private static final Style style = CustomStyle.styleOfBloodMana;

    public EarthManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 800d);
        Utils.manaDamage.put(this, 400d);
        Utils.defence.put(this, 4d);
        Utils.manaDefence.put(this, 4d);
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.4);
            Utils.maxHealth.put(this, 400d);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("旧世斩魔遗怒").withStyle(style));
        components.add(Component.literal(" 将你消耗的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxMana("100%")).
                append(Component.literal("转化为").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.healValue("")));
        ComponentUtils.descriptionPassive(components, Component.literal("新世唤魔复生").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将根据目标的当前生命值附带至多").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("额外魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" -多件地蕴魔力防具可以线性增长被动效能。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
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
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.EarthManaRune.get(), 8));
            add(new ItemStack(ModItems.wolfLeather.get(), 320));
            add(new ItemStack(Items.LEATHER, 192));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }
}
