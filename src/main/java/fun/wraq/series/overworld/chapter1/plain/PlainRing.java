package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;

public class PlainRing extends WraqCurios {

    public PlainRing(Properties p_41383_) {
        super(p_41383_);
        Utils.critDamage.put(this, 0.08);
        Utils.manaPenetration0.put(this, 2d);
        Utils.expUp.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("增加0.5攀登高度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.YELLOW));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.0);
    }
}
