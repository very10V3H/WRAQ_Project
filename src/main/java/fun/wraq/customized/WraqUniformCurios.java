package fun.wraq.customized;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public abstract class
WraqUniformCurios extends Item implements ICurioItem {

    public WraqUniformCurios(Properties properties) {
        super(properties.stacksTo(1));
        Utils.customizedList.add(this);
        Utils.uniformList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = hoverMainStyle();
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!additionHoverText(stack).isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            ComponentUtils.descriptionPassive(components, getFirstPassiveName());
            components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
            components.addAll(additionHoverText(stack));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (getName() != null) {
            components.add(Te.m("来自", CustomStyle.styleOfWorld).
                    append(Te.m("开拓者", CustomStyle.styleOfWorld)).
                    append(Te.m("「", CustomStyle.styleOfWorld)).
                    append(Te.m(getName(), ChatFormatting.AQUA)).
                    append(Te.m("」", CustomStyle.styleOfWorld)));
        } else {
            components.add(suffix());
        }
        super.appendHoverText(stack, level, components, flag);
    }

    public abstract List<Component> additionHoverText(ItemStack stack);

    public abstract Style hoverMainStyle();

    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public String getName() {
        return null;
    }

    public abstract Component getFirstPassiveName();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Compute.AddCuriosToList((Player) slotContext.entity(), stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Compute.RemoveCuriosInList((Player) slotContext.entity(), stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean isOn(Class<? extends WraqUniformCurios> clazz, Player player) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return false;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        return curiosList.stream().anyMatch(itemStack -> itemStack.getItem().getClass() == clazz);
    }
}