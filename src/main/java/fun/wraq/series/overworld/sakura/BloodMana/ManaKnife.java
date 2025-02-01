package fun.wraq.series.overworld.sakura.BloodMana;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ManaKnife extends WraqOffHandItem implements ForgeItem {

    public ManaKnife(Properties properties) {
        super(properties, Component.literal("匕首").withStyle(CustomStyle.styleOfBloodMana));
        Utils.attackDamage.put(this, 40d);
        Utils.defencePenetration0.put(this, 4d);
        Utils.critRate.put(this, 0.12);
        Utils.critDamage.put(this, 0.05);
        Utils.healthSteal.put(this, 0.04);
        Utils.expUp.put(this, 0.65);
        Utils.levelRequire.put(this, 125);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("旧世猎魔遗忆").withStyle(style));
        components.add(Component.literal(" 当你的箭矢命中目标后，为你恢复").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("5%")).
                append(ComponentUtils.AttributeDescription.health("")));
        Compute.DescriptionPassive(components, Component.literal("新世猎魔传技").withStyle(style));
        components.add(Component.literal(" 将你的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.healthSteal("")).
                append(Component.literal("以1:1全部转化为").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.critDamage("")));
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
            add(new ItemStack(ModItems.BloodManaRune.get(), 8));
            add(new ItemStack(ModItems.EarthManaRune.get(), 8));
            add(new ItemStack(Items.IRON_INGOT, 64));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
        }};
    }
}
