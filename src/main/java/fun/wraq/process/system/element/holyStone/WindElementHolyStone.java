package fun.wraq.process.system.element.holyStone;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class WindElementHolyStone extends Item implements ICurioItem {

    public WindElementHolyStone(Properties p_41383_, int type) {
        super(p_41383_);
        Utils.curiosList.add(this);
        switch (type) {
            case 0 -> Utils.attackDamage.put(this, 200d);
            case 1 -> Utils.swiftnessUp.put(this, 1.5);
            case 2 -> Utils.manaDamage.put(this, 400d);
        }
        Element.WindElementValue.put(this, 0.7);
        Utils.levelRequire.put(this, 200);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        Style style = CustomStyle.styleOfWind;
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        Compute.LevelRequire(components, 200);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfElement(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
