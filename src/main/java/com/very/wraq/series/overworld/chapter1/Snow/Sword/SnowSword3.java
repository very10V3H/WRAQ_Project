package com.very.wraq.series.overworld.chapter1.Snow.Sword;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.USE.UtilsSnowSwordS2CPacket;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class SnowSword3 extends PickaxeItem implements ActiveItem {

    public SnowSword3(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration0.put(this, this.DefencePenetration0);
        Utils.healthSteal.put(this, this.HealSteal);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.attackSpeedUp.put(this, AttackSpeedUp);
        Element.IceElementValue.put(this, 0.8);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.swordTag.put(this, 1d);
    }

    private final double BaseDamage = 120;
    private final double DefencePenetration0 = 800;
    private final double CriticalHitRate = 0.4F;
    private final double CHitDamage = 0.6;
    private final double HealSteal = 0.04F;
    private final double SpeedUp = 0.4F;
    private final double AttackSpeedUp = -2f;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("镐子").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_AQUA, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_AQUA, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("凿击-Ex").withStyle(ChatFormatting.AQUA));
        ComponentUtils.descriptionNum(components, "攻击将会大幅降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.AQUA), "");
        components.add(Component.literal("主动:").withStyle(ChatFormatting.AQUA).
                append(Component.literal("冰川攀登！").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("向前闪现一小段距离"));
        components.add(Component.literal("冷却时间: 10s"));
        components.add(Component.literal("法力消耗:").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(" 20").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("Snow-Pickaxe-X").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal(" "));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
            ModNetworking.sendToClient(new UtilsSnowSwordS2CPacket(true), (ServerPlayer) player);
            player.getCooldowns().addCooldown(ModItems.SnowSword3.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.SnowSword4.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        }
    }

}
