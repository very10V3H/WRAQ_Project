package fun.wraq.common.equip;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
        if (this instanceof RuneItem) {
            Display.runeList.add(this);
        }
    }

    public WraqCurios(Properties properties, int maxSlotSize) {
        super(properties.stacksTo(maxSlotSize));
        Utils.curiosList.add(this);
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = hoverMainStyle();
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        Component type = RandomCurios.getTypeDescriptionByTag(stack);
        if (type != null) {
            components.add(Te.s(type, " v = ", hoverMainStyle(),
                    String.format("%.1f", RandomCurios.getFullRateByTag(stack)), hoverMainStyle()));
        } else {
            if (getTypeDescription() != null) {
                components.add(getTypeDescription());
            }
        }
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
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return !(this instanceof MoontainCurios);
    }

    public static boolean isOn(Class<? extends Item> clazz, Player player) {
        List<ItemStack> curiosList = Compute.CuriosAttribute.getDistinctCuriosList(player);
        return curiosList.stream().anyMatch(itemStack -> itemStack.getItem().getClass() == clazz);
    }

    public static ItemStack isOnWithStack(Class<? extends Item> clazz, Player player) {
        List<ItemStack> curiosList = Compute.CuriosAttribute.getDistinctCuriosList(player);
        return curiosList.stream()
                .filter(itemStack -> itemStack.getItem().getClass() == clazz)
                .findFirst()
                .orElse(new ItemStack(Items.AIR));
    }

    public static boolean coolDownOver(Map<String, Integer> map, Player player) {
        return !map.containsKey(player.getName().getString())
                || map.get(player.getName().getString()) < Tick.get();
    }

    public static boolean inLastTime(Map<String, Integer> map, Player player) {
        return map.containsKey(player.getName().getString())
                && map.get(player.getName().getString()) > Tick.get();
    }

    public void tick(Player player) {}
    public void clientTick(Player player) {}

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if (player.level().isClientSide) {
            clientTick(player);
        }
        else {
            tick(player);
        }
        ICurioItem.super.curioTick(slotContext, stack);
    }
}
