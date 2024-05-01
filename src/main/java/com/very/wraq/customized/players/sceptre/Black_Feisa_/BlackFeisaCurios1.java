package com.very.wraq.customized.players.sceptre.Black_Feisa_;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class BlackFeisaCurios1 extends Item implements ICurioItem {

    public static Player onPlayer;
    public static boolean IsOn;
    public static int Passive1Count = 0;

    public BlackFeisaCurios1(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfField;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("闪电五连鞭").withStyle(style));
        components.add(Component.literal(" 释放法术").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("额外释放一枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("年轻人不讲武德").withStyle(style));
        components.add(Component.literal(" 攻击/受击为你提供持续1s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("100%")).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，护盾持续时间结束后，为你提供持续5s的等同于你当前护盾值的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚太极宗师马保国，授予对维瑞阿契做出了杰出贡献的 - Black_FeiSa_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = null;
        IsOn = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && IsOn;
    }

    public static void Shoot(Player player) {
        if (!IsOn(player)) return;
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level, PlayerAttributes.PlayerManaDamage(player),
                PlayerAttributes.PlayerManaPenetration(player), PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.EndParticle);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);

        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
        Compute.SoundToAll(player, ModSounds.Mana.get());
    }

    public static int ShieldEndTick = 0;
    public static int DamageEnhanceTick = 0;
    public static double DamageEnhanceValue = 0;
    public static int CoolDownTick = 0;
    public static void Passive2(Player player) {
        if (IsOn(player)) {
            int TickCount = player.getServer().getTickCount();
            if (TickCount > CoolDownTick) {
                CoolDownTick = TickCount + 100;
                ShieldEndTick = TickCount + 15;
                Compute.PlayerShieldProvider(player,20,PlayerAttributes.PlayerManaDamage(player));
                Compute.EffectLastTimeSend(player,ModItems.BlackFeisaCurios21.get().getDefaultInstance(),15);
            }
        }
    }

    public static void Tick(Player player) {
        if (IsOn(player)) {
            int TickCount = player.getServer().getTickCount();
            if (TickCount == ShieldEndTick) {
                DamageEnhanceTick = TickCount + 85;
                DamageEnhanceValue = Compute.PlayerShieldCompute(player);
                Compute.EffectLastTimeSend(player,ModItems.BlackFeisaCurios21.get().getDefaultInstance(),85);
            }
        }
    }


}
