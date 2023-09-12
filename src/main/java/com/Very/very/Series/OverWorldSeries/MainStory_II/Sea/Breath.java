package com.Very.very.Series.OverWorldSeries.MainStory_II.Sea;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class Breath extends Item implements ICurioItem {

    public Breath(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("获得水下呼吸能力并提高游泳速度！").withStyle(Utils.styleOfSea));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Sea").withStyle(Utils.styleOfSea).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(2.0F);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(1.0F);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,30,1,true,true));
        ICurioItem.super.curioTick(slotContext, stack);
    }
}
