package com.very.wraq.Items.LevelReward.LevelReward;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class LevelReward60 extends Item implements ICurioItem {
    public LevelReward60(Properties p_41383_) {
        super(p_41383_);
        int level = 60;
        Utils.AttackDamage.put(this,level / 5.0);
        Utils.ManaDamage.put(this,level / 5.0);
        Utils.DefencePenetration.put(this,level / (500.0 * 2));
        Utils.ManaPenetration.put(this,level / (500.0 * 2));
        Utils.CritRate.put(this,level / (500.0 * 2));
        Utils.CritDamage.put(this,level / 500.0);
        Utils.HealthSteal.put(this,level / (500.0 * 4));
        Utils.ManaRecover.put(this,level / 500.0);
        Utils.MovementSpeed.put(this,level / (500.0 * 2));
        Utils.MaxHealth.put(this,level * 2d);
        Utils.HealthRecover.put(this,level / 500.0);
        Utils.CoolDownDecrease.put(this,level / 1000.0);
        Utils.ExpUp.put(this,level / 500.0);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        int rate = 12;
        Compute.ComponentAddLevelReward(components,rate);
        itemStack.getOrCreateTagElement(Utils.MOD_ID);
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        if(player.experienceLevel >= 60) data.putBoolean("LevelReward60",true);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("LevelReward60",false);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

}
