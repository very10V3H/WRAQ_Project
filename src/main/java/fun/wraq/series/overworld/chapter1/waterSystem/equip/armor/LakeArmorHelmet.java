package fun.wraq.series.overworld.chapter1.waterSystem.equip.armor;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.waterSystem.LakeSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LakeArmorHelmet extends ArmorItem {
    public LakeArmorHelmet(ModArmorMaterials modArmorMaterials, Type Slots) {
        super(modArmorMaterials, Slots, new Properties().rarity(CustomStyle.WaterItalic));
        Utils.coolDownDecrease.put(this, 0.1);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(CustomStyle.styleOfWater)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        LakeSuitDescription.LakeArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static double exCooldown(Player player) {
        return SuitCount.getLakeSuitCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 0.0025 : 0;
    }
}
