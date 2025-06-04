package fun.wraq.series.overworld.chapter1.snow;

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

import java.util.List;

public class SnowBoss {
    public static Style chatFormatting = CustomStyle.styleOfSnow;
    public static Component SeriesTag = Component.literal("Item_SnowBoss").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC);
    public static String Name = "冰川";

    public static class SnowBossArmorChest extends ArmorItem {
        private static final Style style = CustomStyle.styleOfSnow;

        public SnowBossArmorChest(ModArmorMaterials Material, Type Slots) {
            super(Material, Slots, new Properties().rarity(CustomStyle.EntropyItalic));
            Utils.maxHealth.put(this, 1200d);
            Utils.defence.put(this, 1d);
            Utils.armorTag.put(this, 1d);
            Utils.armorList.add(this);
        }

        @Override
        public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> components, TooltipFlag flag) {
            Compute.forgingHoverName(stack);
            components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(style)));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            ComponentUtils.descriptionOfBasic(components);
            BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
            ComponentUtils.descriptionOfAddition(components);
            Compute.DescriptionPassive(components, Component.literal("寒玉皑皑").withStyle(style));
            components.add(Component.literal("每过3s，削减周围所有单位至多").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.defence("50%")).
                    append(Component.literal("并造成").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("缓速").withStyle(style)).
                    append(Component.literal("效果。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("护甲削减与缓速效果持续2s,并且效果不可叠加。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            SnowSuitDescription.SuitDescription(components);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("Dimension-Snow").withStyle(style).withStyle(ChatFormatting.ITALIC));
            ComponentUtils.suffixOfChapterI(components);
            super.appendHoverText(stack, level, components, flag);
        }
    }
}

