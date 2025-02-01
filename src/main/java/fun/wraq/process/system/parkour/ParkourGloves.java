package fun.wraq.process.system.parkour;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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

public class ParkourGloves extends Item implements ICurioItem {

    public ParkourGloves(Properties p_41383_) {
        super(p_41383_);
        Utils.swiftnessUp.put(this, 1.5);
        Utils.coolDownDecrease.put(this, 0.15);
        Utils.critDamage.put(this, 0.05);
        Utils.expUp.put(this, 0.5);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfFlexible;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 给予跑酷高手或坚持不懈者的嘉奖").withStyle(CustomStyle.styleOfFlexible));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
