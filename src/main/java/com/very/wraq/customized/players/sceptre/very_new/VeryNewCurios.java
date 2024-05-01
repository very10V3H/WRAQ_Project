package com.very.wraq.customized.players.sceptre.very_new;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class VeryNewCurios extends Item implements ICurioItem {

    public VeryNewCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("佛怒火莲/天陨").withStyle(style));
        components.add(Component.literal(" 每三次施放").withStyle(ChatFormatting.WHITE).
                append(ModItems.VolcanoPower.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("都会释放红莲绽放").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("红莲绽放").withStyle(style)));
        components.add(Component.literal(" 每五次施放").withStyle(ChatFormatting.WHITE).
                append(ModItems.VolcanoPower.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("都会召唤").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("天陨").withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("红莲燃烧").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会对目标施加持续2s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("引燃效果").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，每0.5s造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 黄昏沉溺温柔，落日伴你余晖").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚红莲绽，授予对维瑞阿契做出了杰出贡献的 - very_new").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.VeryNew = (Player) slotContext.entity();
        Utils.VeryNewCurios = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.VeryNew = null;
        Utils.VeryNewCurios = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return Utils.VeryNew != null && Utils.VeryNew.equals(player) && Utils.VeryNewCurios;
    }

    public static void ExManaEffect(Player player, Mob mob) {
        if (!IsOn(player)) return ;
        Compute.IgniteMob(player,mob,40);
        Compute.Damage.LastXpStrengthDamageToMob(player,mob,0.5,40,10,false);
    }
}
