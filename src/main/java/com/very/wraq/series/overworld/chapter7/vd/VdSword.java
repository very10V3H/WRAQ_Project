package com.very.wraq.series.overworld.chapter7.vd;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.MainHandTickItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VdSword extends WraqSword implements ForgeItem, ActiveItem, MainHandTickItem, VdWeaponCommon {

    public VdSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1800d);
        Utils.defencePenetration0.put(this, 4000d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.30d);
        Utils.critDamage.put(this, 1d);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return VdWeaponCommon.additionalDescriptions(stack);
    }

    @Override
    public Component oneLineDescription() {
        return Component.literal("「至高科技结晶」").withStyle(getMainStyle()).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Wraq();
    }

    @Override
    public void active(Player player) {
        VdWeaponCommon.active(player, this);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilSword.get(), 1));
            add(new ItemStack(C7Items.vdSoul.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }};
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void tick(Player player) {
        VdWeaponCommon.tick(player);
    }
}
