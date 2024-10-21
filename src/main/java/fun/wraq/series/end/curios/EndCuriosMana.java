package fun.wraq.series.end.curios;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EndCuriosMana extends WraqCurios implements ForgeItem {

    public EndCuriosMana(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, 194d);
        Utils.manaPenetration.put(this, 0.1);
        Utils.maxMana.put(this, 20d);
        Utils.manaRecover.put(this, 10d);
        Utils.movementSpeedWithoutBattle.put(this, 0.3);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfEnd;
        Compute.DescriptionPassive(components, Component.literal("跃迁").withStyle(style));
        components.add(Component.literal(" 使你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法球").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("能够").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("穿透").withStyle(style)).
                append(Component.literal("敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，每穿过一个敌人，使接下来造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("33%").withStyle(style)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfEnd;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfEnd();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.EndCrystal.get(), 12),
                new ItemStack(ModItems.goldCoin.get(), 320),
                new ItemStack(ModItems.completeGem.get(), 8),
                new ItemStack(ModItems.ReputationMedal.get(), 32),
                new ItemStack(ModItems.RefiningGold.get(), 3),
                new ItemStack(ModItems.WorldSoul3.get(), 3)
        );
    }
}
