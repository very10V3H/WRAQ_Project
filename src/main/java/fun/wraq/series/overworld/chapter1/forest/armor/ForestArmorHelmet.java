package fun.wraq.series.overworld.chapter1.forest.armor;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.forest.ForestSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestArmorHelmet extends ArmorItem {
    public ForestArmorHelmet(ItemMaterial Materrial, Type Slots) {
        super(Materrial, Slots, new Properties().rarity(CustomStyle.ForestItalic));
        Utils.defence.put(this, 1d);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.DARK_GREEN)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ForestSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static double exDefence(Player player) {
        return SuitCount.getForestSuitCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 0.4 : 0;

    }
}