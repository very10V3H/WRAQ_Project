package fun.wraq.common.equip;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.impl.display.ForgeItem;
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
import java.util.Map;

public abstract class WraqCurios extends Item implements ICurioItem {

    public WraqCurios(Properties properties) {
        super(properties.stacksTo(1));
        Utils.curiosList.add(this);
        Utils.curiosTag.put(this, 1d);
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = hoverMainStyle();
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        if (getTypeDescription() != null) components.add(getTypeDescription());
        int levelRequirement = Utils.levelRequire.getOrDefault(stack.getItem(), 0);
        if (levelRequirement != 0) {
            components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!additionHoverText(stack).isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            components.addAll(additionHoverText(stack));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
        components.add(suffix());
        super.appendHoverText(stack, level, components, flag);
    }

    public abstract Component getTypeDescription();

    public abstract List<Component> additionHoverText(ItemStack stack);

    public abstract Style hoverMainStyle();

    public abstract Component suffix();

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

    public static boolean isOn(Class<? extends WraqCurios> clazz, Player player) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return false;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        return curiosList.stream().anyMatch(itemStack -> itemStack.getItem().getClass() == clazz);
    }

    public static boolean coolDownOver(Map<String, Integer> map, Player player) {
        return !map.containsKey(player.getName().getString())
                || map.get(player.getName().getString()) < player.getServer().getTickCount();
    }

    public static boolean inLastTime(Map<String, Integer> map, Player player) {
        return map.containsKey(player.getName().getString())
                && map.get(player.getName().getString()) > player.getServer().getTickCount();
    }
}