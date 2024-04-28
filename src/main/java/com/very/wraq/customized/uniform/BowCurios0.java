package com.very.wraq.customized.uniform;

import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowCurios0 extends Item implements ICurioItem {

    public BowCurios0(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this,Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this,Attributes.DefencePenetration0);
        Utils.CritDamage.put(this,Attributes.CritDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.CritRate.put(this,Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfFlexible;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("自然选择").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("80%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components,Component.literal("闪避突袭").withStyle(style));
        components.add(Component.literal(" 使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("翻滚").withStyle(style)).
                append(Component.literal("后，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Swiftness("15%总")).
                append(Component.literal("，并使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(style)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1.5倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("基础伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("uniform - bow - 300vp").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        onPlayerMap.put(player,true);
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        onPlayerMap.put(player,false);
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Map<Player,Boolean> onPlayerMap = new HashMap<>();

    public static boolean IsOn(Player player) {
        return onPlayerMap.containsKey(player) && onPlayerMap.get(player);
    }

    public static double PlayerFinalDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return 0.8;
    }

    public static int activeLastTick = 0;
    public static void Active(Player player) {
        if (!IsOn(player)) return;
        activeLastTick += player.getServer().getTickCount() + 60;
        Compute.EffectLastTimeSend(player, ModItems.BowCurios0.get().getDefaultInstance(), 60);
    }

    public static double BaseDamageEnhance(Player player) {
        if (!IsOn(player)) return 1;
        if (activeLastTick > player.getServer().getTickCount()) return 1.5;
        return 1;
    }

    public static double SwiftnessUp(Player player) {
        if (!IsOn(player)) return 1;
        if (activeLastTick > player.getServer().getTickCount()) return 1.15;
        return 1;
    }
}
