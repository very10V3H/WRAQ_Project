package fun.wraq.series.nether.Equip.Armor;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class NetherSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfNether, ChatFormatting.WHITE);
        components.add(Component.literal("NetherArmor").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterII(components);
    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfNether;
        Item[] items = {
                ModItems.NetherArmorHelmet.get(),
                ModItems.NetherArmorChest.get(),
                ModItems.NetherArmorLeggings.get(),
                ModItems.NetherArmorBoots.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
        };
        for (int i = 0; i < items.length; i++) {
            count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        Compute.DescriptionPassive(components, Component.literal("迸骸成末").withStyle(CustomStyle.styleOfNether));
        components.add(Te.m(" 普通近战攻击", CustomStyle.styleOfPower).
                append(Te.m("与")).
                append(Te.m("普通箭矢攻击", CustomStyle.styleOfFlexible)).
                append(Te.m("会击碎目标")).
                append(ComponentUtils.AttributeDescription.defence(count + "%")));
        components.add(Te.m(" 至多叠加至6层，每层持续5s"));
        components.add(Te.m(" 套装数量对应每层提供的百分比", ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
    }
}
