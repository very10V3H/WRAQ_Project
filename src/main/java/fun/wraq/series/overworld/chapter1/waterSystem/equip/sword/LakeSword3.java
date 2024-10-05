package fun.wraq.series.overworld.chapter1.waterSystem.equip.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.USE.UtilsLakeSwordS2CPacket;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class LakeSword3 extends SwordItem implements ActiveItem, OnHitEffectEquip {
    public LakeSword3(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties().rarity(CustomStyle.WaterItalic));
        Utils.attackDamage.put(this, 80.0d);
        Utils.defencePenetration0.put(this, 3d);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.45);
        Utils.movementSpeedWithoutBattle.put(this, 0.75);
        Element.WaterElementValue.put(this, 0.8);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.swordTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长柄武器").withStyle(ChatFormatting.BLUE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("潜泳").withStyle(ChatFormatting.BLUE));
        ComponentUtils.descriptionNum(components, "攻击后获得持续3秒的", ComponentUtils.AttributeDescription.movementSpeed("50%"), "");
        Compute.DescriptionActive(components, Component.literal("出水").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("右键向前冲刺"));
        ComponentUtils.coolDownTimeDescription(components, 8);
        ComponentUtils.manaCostDescription(components, 60);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
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
