package com.very.wraq.series.overworld.chapter1.waterSystem.equip.sword;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.USE.UtilsLakeSwordS2CPacket;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.StableAttributesModifier;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class LakeSword3 extends SwordItem implements ActiveItem, OnHitEffectMainHandWeapon {
    public LakeSword3(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties().rarity(CustomStyle.WaterItalic));
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration0.put(this, this.DefencePenetration0);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.attackSpeedUp.put(this, AttackSpeedUp);
        Element.WaterElementValue.put(this, 0.8);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.swordTag.put(this, 1d);
    }

    private final double BaseDamage = 80.0d;
    private final double DefencePenetration0 = 300;
    private final double CriticalHitRate = 0.3F;
    private final double CHitDamage = 0.45;
    private final double SpeedUp = 0.75F;
    private final double AttackSpeedUp = -2f;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长柄武器").withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("潜泳").withStyle(ChatFormatting.BLUE));
        Compute.DescriptionNum(components, "攻击后获得持续3秒的", ComponentUtils.AttributeDescription.movementSpeed("50%"), "");
        Compute.DescriptionActive(components, Component.literal("出水").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("右键向前冲刺"));
        Compute.CoolDownTimeDescription(components, 8);
        Compute.ManaCostDescription(components, 60);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        components.add(Component.literal("Lake-Spear-III").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            ModNetworking.sendToClient(new UtilsLakeSwordS2CPacket(true), (ServerPlayer) player);
            player.getCooldowns().addCooldown(ModItems.LakeSword3.get(), (int) (160 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        }
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                new StableAttributesModifier("lakeSwordPassiveExMovementSpeed", 0.5, player.getServer().getTickCount() + 60));
    }
}
