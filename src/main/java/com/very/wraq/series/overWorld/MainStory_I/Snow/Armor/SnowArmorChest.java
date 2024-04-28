package com.very.wraq.series.overWorld.MainStory_I.Snow.Armor;

import com.very.wraq.series.overWorld.MainStory_I.Snow.SnowSuitDescription;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SnowArmorChest extends ArmorItem {
    private final double MaxHealth = 150;
    private final double Defence = 50;
    private static final Style style = CustomStyle.styleOfSnow;
    public SnowArmorChest(ItemMaterial Material, Type Slots)
    {
        super(Material,Slots,new Properties().rarity(Utils.SnowItalic));
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.Defence.put(this, Defence);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("玉山胸甲").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        SnowSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
